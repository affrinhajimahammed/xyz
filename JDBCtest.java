package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCtest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Using try-with-resources
            try (Connection con = DriverManager.getConnection(
                     "jdbc:mysql://localhost:3306/testdb", "root", "Mysql");
                 PreparedStatement pstmt = con.prepareStatement("REPLACE INTO students VALUES(?, ?)");
                 Statement stmt = con.createStatement()) {

                Object[][] students = {
                    {1, "Alice"},
                    {2, "Bob"},
                    {3, "Can"},
                    {4, "David"},
                    {5, "Eve"},
                    {6, "Fimona"},
                };

                for (Object[] student : students) {
                    pstmt.setInt(1, (int) student[0]);
                    pstmt.setString(2, (String) student[1]);
                    pstmt.executeUpdate();
                }

                try (ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
                    System.out.println("Student List:");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " " + rs.getString("name"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
