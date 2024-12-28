package com.idiot.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // Updated query to include BOOKNAME, BOOKEDITION, and BOOKPRICE
    private static final String query = "INSERT INTO BOOKS (BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root")) {
                // Prepare statement
                PreparedStatement ps = con.prepareStatement(query);
                
                // Set parameters from request
                String bookName = req.getParameter("bookName");
                String bookEdition = req.getParameter("bookEdition");
                String bookPrice = req.getParameter("bookPrice");
                
                // Set the parameters for the prepared statement
                ps.setString(1, bookName);
                ps.setString(2, bookEdition);
                ps.setDouble(3, Double.parseDouble(bookPrice)); // Assuming the price is a numeric value

                // Execute the query
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    pw.println("<h1>Data inserted successfully</h1>");
                } else {
                    pw.println("<h1>Failed to insert data</h1>");
                }
            } catch (SQLException se) {
                se.printStackTrace();
                pw.println("<h1>" + se.getMessage() + "</h1>");
            }
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>JDBC Driver not found</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
    }
}
