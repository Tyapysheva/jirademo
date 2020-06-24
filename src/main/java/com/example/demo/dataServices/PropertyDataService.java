package com.example.demo.dataServices;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class PropertyDataService {
    private static final int timeout = 1000;

    public boolean saveProperties(String address, String username, String token) {
        boolean result = urlExists(address);
        if (result) {
            try (InputStream inputStream = new FileInputStream("target/classes/application.properties")) {
                Properties props = new Properties();
                props.load(inputStream);
                props.setProperty("jira.address", address);
                props.setProperty("credentials.username", username);
                props.setProperty("credentials.token", token);
                OutputStream outputStream = new FileOutputStream("target/classes/application.properties");
                props.store(outputStream, null);
            } catch (IOException ex) {
                System.out.format("Alarm, error! %s", ex.getMessage());
                result = false;
            }
        }
        return result;
    }

    private boolean urlExists(String address) {
        boolean result;
        try {
            URL url = new URL(address);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            huc.setConnectTimeout(timeout);

            int responseCode = huc.getResponseCode();

            result = HttpURLConnection.HTTP_OK == responseCode;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
