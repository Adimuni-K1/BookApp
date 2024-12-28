package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/showBooks") // URL pattern to access this servlet
public class ShowBooksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root")) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKS");

                // Generate HTML output for the book list
                pw.println("<h2>Book List</h2>");
                pw.println("<table border='1' style='width: 100%; border-collapse: collapse;'>");
                pw.println("<tr style='background-color: #f2f2f2;'><th>ID</th><th>Name</th><th>Edition</th><th>Price</th></tr>");
                while (rs.next()) {
                    pw.println("<tr><td>" + rs.getInt("ID") + "</td><td>" +
                               rs.getString("BOOKNAME") + "</td><td>" +
                               rs.getString("BOOKEDITION") + "</td><td>" +
                               rs.getDouble("BOOKPRICE") + "</td></tr>");
                }
                pw.println("</table>");
            }
        } catch (Exception e) {
            pw.println("<h3 style='color: red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
