package com.petersokor.TimeSlotManager.controller;

import com.petersokor.TimeSlotManager.util.PropertiesLoader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@WebServlet(urlPatterns = {"/login"}
)

/** Begins the authentication process using AWS Cognito
 *
 */
@Component
public class LogIn extends HttpServlet implements PropertiesLoader {

    Properties properties;
    private final Logger logger = LogManager.getLogger(LogIn.class);


    public static String CLIENT_ID;
    public static String LOGIN_URL;
    public static String REDIRECT_URL;

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("method init() is working");
        loadProperties();
    }

    /**
     * Read in the cognito props file and get the client id and required urls
     * for authenticating a user.
     */
    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
        } catch (IOException ioException) {
            logger.info("Cannot load properties..." + ioException.getMessage(),
                    ioException);

        } catch (Exception e) {
            logger.info("Error loading properties" + e.getMessage(), e);

        }
    }

    /**
     * Route to the AWS-hosted Cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (CLIENT_ID == null || LOGIN_URL == null || REDIRECT_URL == null) {
            logger.info("Properties are null, redirecting to error page");
            resp.sendRedirect("error.html");
        } else {
            String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
            resp.sendRedirect(url);
        }
    }
}