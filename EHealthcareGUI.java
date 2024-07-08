import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EHealthcareGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("E-Healthcare Patient Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel NAMELabel = new JLabel("NAME:");
        JTextField NAMEField = new JTextField();
        JLabel AGELabel = new JLabel("AGE:");
        JTextField AGEField = new JTextField();
        JLabel GENDERLabel = new JLabel("GENDER:");
        JTextField GENDERField = new JTextField();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String NAME = NAMEField.getText();
                int AGE = Integer.parseInt(AGEField.getText());
                String GENDER = GENDERField.getText();

                // Save data to the database
                saveToDatabase(NAME, AGE, GENDER);

                // Clear input fields
                AGEField.setText("");
                AGEField.setText("");
                GENDERField.setText("");
            }
        });

        panel.add(NAMELabel);
        panel.add(NAMEField);
        panel.add(AGELabel);
        panel.add(AGEField);
        panel.add(GENDERLabel);
        panel.add(GENDERField);
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void saveToDatabase(String NAME, int AGE, String GENDER) {
      try{
            String username = "venumadhav";
            String password = "venu24";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; // Change the database connection URL accordingly

            Connection con = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO patients (NAME, AGE, GENDER) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, NAME);
            ps.setInt(2, AGE);
            ps.setString(3, GENDER);

            ps.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(null, "Patient information saved successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saving patient information: " + ex.getMessage());
        }
    }
}
