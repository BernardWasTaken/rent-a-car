package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class connectionBase {
    private final String url = "jdbc:postgresql://rogue.db.elephantsql.com/fatthupg";
    private final String user = "fatthupg";
    private final String password = "Irg29IwQslA9grsy56gAY5mpmUs6SN-U";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    
}
