package com.soen387.db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class db_connection {
    // Log
    private static final Logger LOGGER = LogManager.getLogManager().getLogger(String.valueOf(db_connection.class));

    // JDBC driver name
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    // Database URL
    static final String DB_NAME = "soen387";
    // Database credentials
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "";
    // Database connection state
    static Connection conn = null;

    // Establish connection to database
    public static Connection connect() {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);

            return conn;
        } catch (SQLException error) {
            throw new RuntimeException("Error connecting to database: ", error);
        } catch (ClassNotFoundException error) {
            throw new RuntimeException("Error Driver Class Not Found: ", error);
        }
    }

    // Disconnect from database
    public static void disconnect(Connection conn){
        // Close connection
        try {
            if (conn!= null && !conn.isClosed()){
                if (!conn.getAutoCommit()) {
                    conn.commit();
                    conn.setAutoCommit(true);
                }
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException error) {
            LOGGER.log(Level.WARNING, error.getMessage());
        }
    }

    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        // close(empty) result set
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Result set closed.");
            }
        } catch (Exception error) {
            LOGGER.log(Level.WARNING, error.getMessage());
        }

        // close sql statement
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("Query Statement closed.");
            }
        } catch (Exception error) {
            LOGGER.log(Level.WARNING, error.getMessage());
        }

        // close connection to database
        disconnect(conn);
    }

}
