package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteBook") // URL pattern to access this servlet
public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        // Get the book ID from the request
        String bookId = req.getParameter("bookId");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "root", "root")) {
                // Prepare the SQL DELETE statement
                PreparedStatement ps = con.prepareStatement("DELETE FROM BOOKS WHERE ID = ?");
                ps.setInt(1, Integer.parseInt(bookId));

                // Execute the query
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    pw.println("<h3 style='color: green;'>Book deleted successfully</h3>");
                } else {
                    pw.println("<h3 style='color: red;'>No book found with ID: " + bookId + "</h3>");
                }
            }
        } catch (Exception e) {
            // Handle errors and display an error message
            pw.println("<h3 style='color: red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}

