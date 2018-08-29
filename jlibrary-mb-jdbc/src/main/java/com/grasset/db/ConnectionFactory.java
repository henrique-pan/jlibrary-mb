package com.grasset.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class ConnectionFactory {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rootroot";

    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {

        log.info("Creating database connection.");

        Connection connection;
        try {
            Class.forName(DB_DRIVER);
            log.info("JDBC Driver Registered.");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            return connection;
        } catch (ClassNotFoundException e) {
            log.error("Error to load the driver.", e);
            throw e;
        } catch (SQLException e) {
            log.error("Error to get the connection.", e);
            throw e;
        }
    }

}
