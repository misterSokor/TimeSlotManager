package com.petersokor.TimeSlotManager.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petersokor.TimeSlotManager.auth.CognitoJWTParser;
import com.petersokor.TimeSlotManager.auth.CognitoTokenHeader;
import com.petersokor.TimeSlotManager.auth.Keys;
import com.petersokor.TimeSlotManager.auth.TokenResponse;
import com.petersokor.TimeSlotManager.util.PropertiesLoader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet(
        urlPatterns = {"/auth"}
)
@Component
public class Auth extends HttpServlet implements PropertiesLoader {

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String OAUTH_URL;
    private String REDIRECT_URL;
    private String REGION;
    private String POOL_ID;
    private Keys jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
        loadKey();
        logger.info("Auth servlet initialized.");
    }

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String authCode = req.getParameter("code");
    // to see what came from Cognito
    logger.info("Starting doGet method with auth code: " + authCode); //

    String userName;

    try {
        if (authCode == null) {
            logger.info("NO PARAMETERS WERE FOUND");
            resp.sendRedirect("error.html");
        } else {
            logger.info("Received auth code: " + authCode);
            HttpRequest authRequest = buildAuthRequest(authCode);

            try {
                TokenResponse tokenResponse = getToken(authRequest);
                logger.info("Received token response: " + tokenResponse);
                userName = validate(tokenResponse);
                req.setAttribute("userName", userName);

                // This link is shown after successful authentication
                resp.sendRedirect("/Hello");
            } catch (IOException | InterruptedException e) {
                logger.error("Error getting or validating the token: " + e.getMessage(), e);
                resp.sendRedirect("error.html");
            }
        }
    } catch (Exception e) {
        logger.info("An unexpected error occurred: " + e.getMessage(), e);
        resp.sendRedirect("error.html");
    }
}

    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<?> response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());

            logger.info("Response headers: " + response.headers().toString());
            logger.info("Response body: " + response.body().toString());

            ObjectMapper mapper = new ObjectMapper();
            TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
            logger.info("Id token: " + tokenResponse.getIdToken());

            return tokenResponse;
        } catch (Exception e) {
            logger.info("Error while getting the token: " + e.getMessage(), e);
            throw e;
        }
    }

    private String validate(TokenResponse tokenResponse) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CognitoTokenHeader tokenHeader = mapper.readValue(
                    CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(),
                    CognitoTokenHeader.class
            );

            String keyId = tokenHeader.getKid();
            String alg = tokenHeader.getAlg();

            BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(jwks.getKeys().get(0).getN()));
            BigInteger exponent = new BigInteger(1, Base64.getDecoder().decode(jwks.getKeys().get(0).getE()));

            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

            String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(iss)
                    .withClaim("token_use", "id")
                    .build();

            DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());
            String userName = jwt.getClaim("cognito:username").asString();
            logger.info("Username: " + userName);
            logger.info("Claims: " + jwt.getClaims());

            return userName;
        } catch (Exception e) {
            logger.info("Error during token validation: " + e.getMessage(), e);
            throw e;
        }
    }


    private HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();

        return request;
    }

    private void loadKey() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("Keys are loaded. Here's e: " + jwks.getKeys().get(0).getE());
        } catch (IOException ioException) {
            logger.info("Cannot load JSON: " + ioException.getMessage(),
                    ioException);
        } catch (Exception e) {
            logger.info("Error loading JSON: " + e.getMessage(), e);
        }
    }

    private void loadProperties() {
        try {
            Properties properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            String LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");
        } catch (IOException ioException) {
            logger.error("Cannot load properties: " + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading properties: " + e.getMessage(), e);
        }
    }
}
