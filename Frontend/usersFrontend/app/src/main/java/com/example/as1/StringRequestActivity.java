package com.example.as1;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StringRequestActivity {

    public static String sendLoginRequest(String email, String password) throws Exception {
        // Construct the URL for your login endpoint
        String url = "http://coms-309-033.class.las.iastate.edu:8080/users/login";

        // Create a connection
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");

        // Enable input and output streams
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // Construct the request body (email and password)
        String requestBody = "email=" + email + "&password=" + password;

        // Write the request body to the connection
        connection.getOutputStream().write(requestBody.getBytes());

        // Get the response from the server
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        // Return the response
        return response.toString();
    }
    public static String sendSignUpRequest(String email, String username, String password) throws Exception {
        String url = "http://coms-309-033.class.las.iastate.edu:8080/users/register";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // Construct the request body (email, username, and password)
        String requestBody = "email=" + email + "&username=" + username + "&password=" + password;

        connection.getOutputStream().write(requestBody.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }
}
