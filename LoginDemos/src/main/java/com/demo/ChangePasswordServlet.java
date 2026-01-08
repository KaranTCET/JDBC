package com.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        if (username == null) {
            request.setAttribute("error", "Session expired");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match!");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            // Verify old password
            String checkSql =
                    "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps1 = con.prepareStatement(checkSql);
            ps1.setString(1, username);
            ps1.setString(2, oldPassword);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                // Update password
                String updateSql =
                        "UPDATE users SET password=? WHERE username=?";
                PreparedStatement ps2 = con.prepareStatement(updateSql);
                ps2.setString(1, newPassword);
                ps2.setString(2, username);
                ps2.executeUpdate();

                request.setAttribute("msg", "Password changed successfully!");
            } else {
                request.setAttribute("error", "Old password is incorrect!");
            }

            con.close();
            request.getRequestDispatcher("welcome.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
