package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class DBConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/authdb";

	    private static final String USER = "root";
	    private static final String PASSWORD = "root"; // or empty if no password

	    public static Connection getConnection() {
	        Connection con = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("DB Connected Successfully!");
	        } catch (Exception e) {
	            System.out.println("DB Connection Failed!");
	            e.printStackTrace();
	        }
	        return con;
	    }

}
