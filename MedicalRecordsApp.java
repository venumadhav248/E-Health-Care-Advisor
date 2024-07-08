import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class ConnectDB {
    Connection con;

    ConnectDB() throws SQLException, ClassNotFoundException {
        String username = "madhav";
        String password = "venu24";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
    }

    void insertDataIntoUsers(int userID, String name, int age, String gender, String email, String password,
            String medicalHistory, String contactInfo) throws SQLException {
        String query = "INSERT INTO Users (User_ID, Name, Age, Gender, Email, Password, Medical_History, Contact_Information) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, gender);
            pstmt.setString(5, email);
            pstmt.setString(6, password);
            pstmt.setString(7, medicalHistory);
            pstmt.setString(8, contactInfo);
            pstmt.executeUpdate();
        }
    }

    ResultSet retrieveDataFromUsers() throws SQLException {
        String query = "SELECT * FROM Users";
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }
}

public class MedicalRecordsApp extends JFrame {
    private ConnectDB obj;
    private JTextField userIDField, nameField, ageField, genderField, emailField;
    private JPasswordField passwordField;
    private JTextArea medicalHistoryArea, contactInfoArea, resultTextArea;
    private JButton submitButton, retrieveButton;

    public MedicalRecordsApp() throws SQLException, ClassNotFoundException {
        obj = new ConnectDB();
        initializeGUI();
    }

    private void initializeGUI() {
        // Create the GUI components (labels, text fields, text areas, buttons, etc.)
        JLabel userIDLabel = new JLabel("User ID:");
        userIDField = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        // Add other labels and text fields here

        // Create text areas
        medicalHistoryArea = new JTextArea(5, 20);
        medicalHistoryArea.setLineWrap(true);
        contactInfoArea = new JTextArea(5, 20);
        contactInfoArea.setLineWrap(true);

        resultTextArea = new JTextArea(10, 40);
        resultTextArea.setEditable(false);

        // Create buttons
        submitButton = new JButton("Submit");
        retrieveButton = new JButton("Retrieve");

        // Set up the main frame
        this.setTitle("Medical Records App");
        this.setLayout(new GridLayout(0, 2, 5, 5));
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the components to the frame
        this.add(userIDLabel);
        this.add(userIDField);
        this.add(nameLabel);
        this.add(nameField);
        // Add other labels and text fields here

        // Add text areas
        this.add(new JLabel("Medical History:"));
        this.add(new JScrollPane(medicalHistoryArea));

        this.add(new JLabel("Contact Information:"));
        this.add(new JScrollPane(contactInfoArea));

        // Add buttons and result text area
        this.add(submitButton);
        this.add(retrieveButton);
        this.add(new JLabel("Retrieved Data:"));
        this.add(new JScrollPane(resultTextArea));

        // Add ActionListener to the submitButton
       submitButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            int userID = Integer.parseInt(userIDField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String medicalHistory = medicalHistoryArea.getText();
            String contactInfo = contactInfoArea.getText();

            // Call the method to insert data into the Users table
            obj.insertDataIntoUsers(userID, name, age, gender, email, password, medicalHistory, contactInfo);

            JOptionPane.showMessageDialog(MedicalRecordsApp.this, "Data submitted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MedicalRecordsApp.this, "Error inserting data.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(MedicalRecordsApp.this, "Invalid input. Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

        // Add ActionListener to the retrieveButton
        retrieveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet resultSet = obj.retrieveDataFromUsers();
                    displayData(resultSet);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MedicalRecordsApp.this, "Error retrieving data.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set the frame to be visible
        this.setVisible(true);
    }

    private void displayData(ResultSet resultSet) throws SQLException {
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()) {
            int userID = resultSet.getInt("User_ID");
            String name = resultSet.getString("Name");
            // Get other fields similarly

            sb.append("User ID: ").append(userID).append(", Name: ").append(name).append(", Age: ").append(age)
                    .append(", Gender: ").append(gender).append(", Email: ").append(email).append(", Medical History: ")
                    .append(medicalHistory).append(", Contact Info: ").append(contactInfo).append("\n");
        }

        resultTextArea.setText(sb.toString());
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new MedicalRecordsApp();
    }
}
