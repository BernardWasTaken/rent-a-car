package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
















        public int LogIn(String username, String password)
        {
            try
            {
                Statement stmt = conn.createStatement();

                ResultSet rst = stmt.executeQuery("SELECT login('" + username + "', '" + password + "')");

                if(rst.next())
                {
                    if (rst.getInt(1) == 1)
                    {
                        return 1;
                    }
                    else if (rst.getInt(1) == 0)
                    {
                        System.out.println("Wrong username or password. Please try again!");
                        return 0;
                    }
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
            return 0;
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


        public void getAllUsers() throws IOException {
            try {
                String url = "http://localhost:8080/users"; // Replace with your API endpoint URL

                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String responseData = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    System.out.println(responseData);
                } else {
                    System.out.println("Error: API request failed with response code " + responseCode);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    
}
