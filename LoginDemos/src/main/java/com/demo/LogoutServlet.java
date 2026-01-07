	package com.demo;
	
	import java.io.IOException;
	import javax.servlet.RequestDispatcher;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	
	@WebServlet("/logout")
	public class LogoutServlet extends HttpServlet {
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	
	        // Get existing session (do not create new one)
	        HttpSession session = request.getSession(false);
	
	        // Invalidate session if exists
	        if (session != null) {
	            session.invalidate();
	        }
	
	        // Redirect back to login page
	        request.setAttribute("error", "Logged out successfully");
	        RequestDispatcher rd =
	                request.getRequestDispatcher("login.jsp");
	        rd.forward(request, response);
	    }
	}
