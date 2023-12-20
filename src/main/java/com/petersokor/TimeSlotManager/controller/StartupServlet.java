package com.petersokor.TimeSlotManager.controller;

import com.petersokor.TimeSlotManager.util.PropertiesLoader;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;
@Component
public class StartupServlet extends HttpServlet implements PropertiesLoader {
    protected final Logger logger = LogManager.getLogger(this.getClass());
    private static String CLIENT_SECRET = null;
    private static String OAUTH_URL = null;
    private static String REGION = null;
    private static String POOL_ID = null;
    private static String CLIENT_ID;
    private static String LOGIN_URL;
    private static String REDIRECT_URL;
    private Properties properties;

    public void init() throws ServletException {
        try {
            super.init();
            loadProperties();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");
        } catch (IOException ioException) {
//            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
//            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }

    protected Properties getCognitoProperties() {
        return properties;
    }

    // Getters for the properties
    public static String getClientSecret() {
        return CLIENT_SECRET;
    }

    public static String getOauthUrl() {
        return OAUTH_URL;
    }

    public static String getRegion() {
        return REGION;
    }

    public static String getPoolId() {
        return POOL_ID;
    }

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getRedirectUrl() {
        return REDIRECT_URL;
    }
}