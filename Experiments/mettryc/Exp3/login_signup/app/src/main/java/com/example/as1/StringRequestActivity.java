package com.example.as1;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class StringRequestActivity {

    public static String sendSignUpRequest(String email, String username, String password) throws IOException {
        String url = "http://coms-309-033.class.las.iastate.edu/signup"; // Replace with your actual endpoint

        String jsonInputString = String.format("{\"email\": \"%s\", \"username\": \"%s\", \"password\": \"%s\"}", email, username, password);

        return sendRequest(url, jsonInputString);
    }

    public static String sendLoginRequest(String email, String password) throws IOException {
        String url = "http://coms-309-033.class.las.iastate.edu/login"; // Replace with your actual endpoint

        String jsonInputString = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        return sendRequest(url, jsonInputString);
    }

    private static String sendRequest(String url, String jsonInputString) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}

