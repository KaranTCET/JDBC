<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.RequestDispatcher" %>

<html>
<body>

<%
    String user = (String) session.getAttribute("username");

    if (user == null) {
        request.setAttribute("error", "Session expired");
        RequestDispatcher rd = request.getRequestDispatcher("login.html");
        rd.forward(request, response);
        return;
    }
%>

<h2>Welcome <%= user %></h2>
<p>Login successful using JDBC</p>

<hr>

<h3>Change Password</h3>

<form action="changePassword" method="post">
    Old Password: <input type="password" name="oldPassword" required><br><br>
    New Password: <input type="password" name="newPassword" required><br><br>
    Confirm Password: <input type="password" name="confirmPassword" required><br><br>
    <input type="submit" value="Change Password">
</form>

<br>

<% if (request.getAttribute("msg") != null) { %>
    <p style="color:green;"><%= request.getAttribute("msg") %></p>
<% } %>

<% if (request.getAttribute("error") != null) { %>
    <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

<hr>

<a href="logout">Logout</a>

</body>
</html>
