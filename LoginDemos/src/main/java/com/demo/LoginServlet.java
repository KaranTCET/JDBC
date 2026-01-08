package com.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch login details from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Get database connection
            Connection con = DBConnection.getConnection();

            // SQL query using PreparedStatement
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            // Execute query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Valid user → create session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                // Redirect to welcome page
                RequestDispatcher rd =
                        request.getRequestDispatcher("welcome.jsp");
                rd.forward(request, response);

            } else {
                // Invalid user
                request.setAttribute("error", "Invalid Credentials!");
                RequestDispatcher rd =
                        request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
