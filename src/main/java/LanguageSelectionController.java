import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageSelectionController {

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button saveButton;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lecture?useUnicode=true&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @FXML
    public void initialize() {
        languageComboBox.getSelectionModel().select("English");
        updateLabels(Locale.ENGLISH);
    }

    @FXML
    private void onLanguageChange() {
        String selectedLanguage = languageComboBox.getSelectionModel().getSelectedItem();
        Locale locale;
        switch (selectedLanguage) {
            case "Farsi":
                locale = new Locale("fa");
                break;
            case "Japanese":
                locale = new Locale("ja");
                break;
            case "English":
            default:
                locale = new Locale("en");
                break;
        }
        updateLabels(locale);
    }

    private void updateLabels(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        nameTextField.setPromptText(bundle.getString("firstNameLabel"));
        lastNameTextField.setPromptText(bundle.getString("lastNameLabel"));
        emailTextField.setPromptText(bundle.getString("emailLabel"));
        saveButton.setText(bundle.getString("saveButton"));
    }

    @FXML
    private void onSaveButtonClick() {
        String firstName = nameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            insertEmployee(conn, firstName, lastName, email);
            System.out.println("Employee data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertEmployee(Connection conn, String firstName, String lastName, String email) throws SQLException {
        String[] tables = {"employee_en", "employee_fa", "employee_ja"};
        for (String table : tables) {
            String sql = "INSERT INTO " + table + " (first_name, last_name, email) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.executeUpdate();
            }
        }
    }
}