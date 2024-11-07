import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddEmployees {
    // JDBC URL, username, and password of HeidiSQL database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lecture";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                // SQL query to insert records into the employees table
                String sql = "INSERT INTO employee (emp_id, name_en, name_fa, name_ja, age, salary) VALUES (?, ?, ?, ?, ?, ?)";

                // Prepare the SQL statement
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Insert employee records
                    statement.setInt(1, 56);  // employee ID
                    statement.setString(2, "Abdullahi123");  // English name
                    statement.setString(3, "امیر دیرین");    // Farsi name
                    statement.setString(4, "アミール・ディリン");  // Japanese name
                    statement.setInt(5, 30);  // age
                    statement.setDouble(6, 50000.00);  // salary
                    statement.executeUpdate();

                    // Add more employee records as needed
                    // statement.setInt(...);
                    // statement.setString(...);
                    // statement.executeUpdate();

                    System.out.println("Records inserted successfully.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}