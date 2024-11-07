import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeDateEntry {

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lecture";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter employee ID:");
            int empId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("Enter employee name in English:");
            String nameEn = scanner.nextLine();

            System.out.println("Enter employee name in Farsi:");
            String nameFa = scanner.nextLine();

            System.out.println("Enter employee name in Japanese:");
            String nameJa = scanner.nextLine();

            System.out.println("Enter employee age:");
            int age = scanner.nextInt();

            System.out.println("Enter employee salary:");
            double salary = scanner.nextDouble();

            // Insert data for English
            insertEmployee(conn, empId, "en", nameEn, age, salary);

            // Insert data for Farsi
            insertEmployee(conn, empId, "fa", nameFa, age, salary);

            // Insert data for Japanese
            insertEmployee(conn, empId, "ja", nameJa, age, salary);

            System.out.println("Employee data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertEmployee(Connection conn, int empId, String languageCode, String name, int age, double salary) throws SQLException {
        String sql = "INSERT INTO employees_row (emp_id, language_code, name, age, salary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empId);
            stmt.setString(2, languageCode);
            stmt.setString(3, name);
            stmt.setInt(4, age);
            stmt.setDouble(5, salary);
            stmt.executeUpdate();
        }
    }
}