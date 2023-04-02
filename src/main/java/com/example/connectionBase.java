package com.example;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class connectionBase {

    static Connection conn = null;

    public static void connect() {
        try {
             String url = "jdbc:postgresql://rogue.db.elephantsql.com/fatthupg";
             String user = "fatthupg";
             String password = "Irg29IwQslA9grsy56gAY5mpmUs6SN-U";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }















    public String getAllUsers() throws IOException {
        String responseData = null;
        try {
            String url = "http://localhost:8080/users"; // Replace with your API endpoint URL

            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(connection.getInputStream());
                responseData = scanner.useDelimiter("\\A").next();
                scanner.close();
                System.out.println(responseData);
            } else {
                System.out.println("Error: API request failed with response code " + responseCode);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return responseData;
    }

        public int LogIn(String username, String password)
        {
            int successNumber = -1;
            String responseData = null;
            try{

                URL url = new URL("http://localhost:8080/users/checkLogin?username="+username+"&password="+password+""); // Replace with your API endpoint URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    responseData = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    System.out.println(responseData); // Print the response data to the console
                } else {
                    System.out.println("Error: API request failed with response code " + responseCode);
                }
                successNumber = -1;
                int startIndex = responseData.indexOf("success+");
                if (startIndex != -1) {
                    startIndex += "success+".length();
                    int endIndex = responseData.indexOf("\"", startIndex);
                    if (endIndex != -1) {
                        String successString = responseData.substring(startIndex, endIndex);
                        successNumber = Integer.parseInt(successString);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error parsing success number: " + e.getMessage());
            }
            System.out.println(successNumber);
            return successNumber;
        }

        public void decon(String jsonStr) {
            String newStr = jsonStr.replace("data", "");
            System.out.println(newStr);
        }

        public List<String> getUserInfo(String username, String password)
        {
            List<String> ret = new ArrayList<>();

            try
            {
                Statement stmt = conn.createStatement();

                ResultSet rst = stmt.executeQuery("SELECT * FROM getUserInfo2('" + username + "', '" + password + "')");

                while(rst.next())
                {
                    ret.add(rst.getString(1));
                    ret.add(rst.getString(2));
                    ret.add(rst.getString(3));
                    ret.add(rst.getString(4));
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            return ret;
        }

        public void updateData(String old_username, String new_username, String new_firstname, String new_surname, String new_password)
        {
            try
            {
                Statement stmt = conn.createStatement();

                stmt.executeQuery("SELECT updateData('"+old_username+"', '"+new_username+"', '"+new_password+"', '"+new_firstname+"', '"+new_surname+"');");
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }

    
}
