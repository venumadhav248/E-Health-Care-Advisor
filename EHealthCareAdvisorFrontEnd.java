import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class EHealthCareAdvisorFrontEnd {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "madhav";
    private static final String DB_PASSWORD = "venu24";
    public static JFrame parentframe;
     public static JPanel panel;
 static class ResultSetTableModel extends AbstractTableModel {
        private List<String[]> data;
        private String[] columnNames;

        ResultSetTableModel(ResultSet resultSet) {
            data = new ArrayList<>();
            try {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                columnNames = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    columnNames[i] = metaData.getColumnName(i + 1);
                }

                while (resultSet.next()) {
                    String[] rowData = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = resultSet.getString(i + 1);
                    }
                    data.add(rowData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            JFrame mainFrame = new JFrame("EHealthCareAdvisor");
            mainFrame.setLayout(new GridBagLayout());
    
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 10, 5, 10);
    
            // Create input panels
            createUsersInputPanel(mainFrame, connection, gbc);
            createHealthcareProfessionalsInputPanel(mainFrame, connection, gbc);
            createMedicalFacilitiesInputPanel(mainFrame, connection, gbc);
            createMedicalConditionsInputPanel(mainFrame, connection, gbc);
            createSymptomsInputPanel(mainFrame, connection, gbc);
            createMedicationsInputPanel(mainFrame, connection, gbc);
    
            // Create display buttons
            createDisplayButton(mainFrame, connection, "Users", gbc);
            createDisplayButton(mainFrame, connection, "Healthcare_Professionals", gbc);
            createDisplayButton(mainFrame, connection, "Medical_Facilities", gbc);
            createDisplayButton(mainFrame, connection, "Medical_Conditions", gbc);
            createDisplayButton(mainFrame, connection, "Symptoms", gbc);
            createDisplayButton(mainFrame, connection, "Medications", gbc);
    
            mainFrame.setSize(800, 600);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    }
    

    private static void createDisplayButton(JFrame parentFrame, Connection connection, String tableName, GridBagConstraints gbc) {
        JPanel panel = new JPanel();
        JButton displayButton = new JButton("Display " + tableName + " Data");
        panel.add(displayButton);
    
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT * FROM " + tableName;
                    try (Statement stmt = connection.createStatement();
                         ResultSet resultSet = stmt.executeQuery(query)) {
    
                        ResultSetTableModel tableModel = new ResultSetTableModel(resultSet);
                        JTable table = new JTable(tableModel);
    
                        JScrollPane scrollPane = new JScrollPane(table);
                        JFrame displayFrame = new JFrame("Table Data: " + tableName);
                        displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        displayFrame.getContentPane().add(scrollPane);
                        displayFrame.pack();
                        displayFrame.setLocationRelativeTo(parentFrame);
                        displayFrame.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentFrame, "Failed to retrieve data. Error: " + ex.getMessage());
                }
            }
        });
    
        gbc.gridx = 1;
        parentFrame.add(panel, gbc);
    }
    
    private static void createUsersInputPanel(JFrame parentFrame, Connection connection, GridBagConstraints gbc) {
          //Frame parentframe = new JFrame();
        JPanel panel = new JPanel(new GridLayout(9,2,10,10));
        //frame.setLayout(new GridLayout(8, 2));

        JLabel userIDLabel = new JLabel("User ID:");
        JTextField userIDField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField();
        JLabel medicalHistoryLabel = new JLabel("Medical History:");
        JTextField medicalHistoryField = new JTextField();
        JLabel contactInformationLabel = new JLabel("Contact Information:");
        JTextField contactInformationField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] columns = {"User_ID", "Name", "Age", "Gender", "Email", "Password", "Medical_History", "Contact_Information"};
                    String[] values = {
                            userIDField.getText(),
                            nameField.getText(),
                            ageField.getText(),
                            genderField.getText(),
                            emailField.getText(),
                            passwordField.getText(),
                            medicalHistoryField.getText(),
                            contactInformationField.getText()
                    };
                    //JFrame parentframe = new JFrame(); // Replace this with your actual JFrame instance.

                    insertValues(connection, "Users", columns, values);
                    JOptionPane.showMessageDialog(parentframe, "User data inserted successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentframe, "Failed to insert user data.");
                }
            }
        });

        // Add components to the frame
        panel.add(userIDLabel);
        panel.add(userIDField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(genderLabel);
        panel.add(genderField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(medicalHistoryLabel);
        panel.add(medicalHistoryField);
        panel.add(contactInformationLabel);
        panel.add(contactInformationField);
        panel.add(submitButton);

        parentFrame.add(panel);
    }

   private static void createHealthcareProfessionalsInputPanel(JFrame parentFrame, Connection connection, GridBagConstraints gbc) {
  //  JFrame frame = new JFrame("Healthcare Professionals Input");
    JPanel panel = new JPanel(new GridLayout(6,2,10,10));
   // frame.setLayout(new GridLayout(5, 2));

    JLabel professionalIDLabel = new JLabel("Professional ID:");
    JTextField professionalIDField = new JTextField();
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    JLabel specializationLabel = new JLabel("Specialization:");
    JTextField specializationField = new JTextField();
    JLabel experienceLabel = new JLabel("Experience:");
    JTextField experienceField = new JTextField();
    JLabel contactInformationLabel = new JLabel("Contact Information:");
    JTextField contactInformationField = new JTextField();

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] columns = {"Professional_ID", "Name", "Specialization", "Experience", "Contact_Information"};
                String[] values = {
                        professionalIDField.getText(),
                        nameField.getText(),
                        specializationField.getText(),
                        experienceField.getText(),
                        contactInformationField.getText()
                };
             //   JFrame parentframe = new JFrame(); // Replace this with your actual JFrame instance.

                insertValues(connection, "Healthcare_Professionals", columns, values);
                JOptionPane.showMessageDialog(parentframe, "Healthcare Professional data inserted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentframe, "Failed to insert Healthcare Professional data.");
            }
        }
    });

    // Add components to the frame
    panel.add(professionalIDLabel);
    panel.add(professionalIDField);
    panel.add(nameLabel);
    panel.add(nameField);
    panel.add(specializationLabel);
    panel.add(specializationField);
    panel.add(experienceLabel);
    panel.add(experienceField);
    panel.add(contactInformationLabel);
    panel.add(contactInformationField);
    panel.add(submitButton);

    parentFrame.add(panel);
}

    


    private static void createMedicalFacilitiesInputPanel(JFrame parentFrame, Connection connection, GridBagConstraints gbc) {
  //  JFrame frame = new JFrame("Medical Facilities Input");
  JPanel panel = new JPanel(new GridLayout(6,2,10,10));
   // frame.setLayout(new GridLayout(5, 2));

    JLabel facilityIDLabel = new JLabel("Facility ID:");
    JTextField facilityIDField = new JTextField();
    JLabel facilityNameLabel = new JLabel("Facility Name:");
    JTextField facilityNameField = new JTextField();
    JLabel locationLabel = new JLabel("Location:");
    JTextField locationField = new JTextField();
    JLabel contactInformationLabel = new JLabel("Contact Information:");
    JTextField contactInformationField = new JTextField();
    JLabel servicesOfferedLabel = new JLabel("Services Offered:");
    JTextField servicesOfferedField = new JTextField();

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] columns = {"Facility_ID", "Facility_Name", "Location", "Contact_Information", "Services_Offered"};
                String[] values = {
                        facilityIDField.getText(),
                        facilityNameField.getText(),
                        locationField.getText(),
                        contactInformationField.getText(),
                        servicesOfferedField.getText()
                };
               // JFrame parentframe = new JFrame(); // Replace this with your actual JFrame instance.

                insertValues(connection, "Medical_Facilities", columns, values);
                JOptionPane.showMessageDialog(parentframe, "Medical Facility data inserted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentframe, "Failed to insert Medical Facility data.");
            }
        }
    });

    // Add components to the frame
    panel.add(facilityIDLabel);
    panel.add(facilityIDField);
    panel.add(facilityNameLabel);
    panel.add(facilityNameField);
    panel.add(locationLabel);
    panel.add(locationField);
    panel.add(contactInformationLabel);
    panel.add(contactInformationField);
    panel.add(servicesOfferedLabel);
    panel.add(servicesOfferedField);
    panel.add(submitButton);

    parentFrame.add(panel);
}


   private static void createMedicalConditionsInputPanel(JFrame parentFrame, Connection connection, GridBagConstraints gbc) {
   // JFrame frame = new JFrame("Medical Conditions Input");
     JPanel panel = new JPanel(new GridLayout(5,2,10,10));
    //frame.setLayout(new GridLayout(4, 2));

    JLabel conditionIDLabel = new JLabel("Condition ID:");
    JTextField conditionIDField = new JTextField();
    JLabel conditionNameLabel = new JLabel("Condition Name:");
    JTextField conditionNameField = new JTextField();
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField();
    JLabel treatmentOptionsLabel = new JLabel("Treatment Options:");
    JTextField treatmentOptionsField = new JTextField();

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] columns = {"Condition_ID", "Condition_Name", "Description", "Treatment_Options"};
                String[] values = {
                        conditionIDField.getText(),
                        conditionNameField.getText(),
                        descriptionField.getText(),
                        treatmentOptionsField.getText()
                };
               // JFrame parentframe = new JFrame(); // Replace this with your actual JFrame instance.

                insertValues(connection, "Medical_Conditions", columns, values);
                JOptionPane.showMessageDialog(parentframe, "Medical Condition data inserted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentframe, "Failed to insert Medical Condition data.");
            }
        }
    });

    // Add components to the frame
    panel.add(conditionIDLabel);
    panel.add(conditionIDField);
    panel.add(conditionNameLabel);
    panel.add(conditionNameField);
    panel.add(descriptionLabel);
    panel.add(descriptionField);
    panel.add(treatmentOptionsLabel);
    panel.add(treatmentOptionsField);
    panel.add(submitButton);

    parentFrame.add(panel);
}


   private static void createSymptomsInputPanel(JFrame parentFrame, Connection connection, GridBagConstraints gbc) {
  //  JFrame frame = new JFrame("Symptoms Input");
 JPanel panel = new JPanel(new GridLayout(5,2,10,10));
//    frame.setLayout(new GridLayout(4, 2));

    JLabel symptomIDLabel = new JLabel("Symptom ID:");
    JTextField symptomIDField = new JTextField();
    JLabel symptomNameLabel = new JLabel("Symptom Name:");
    JTextField symptomNameField = new JTextField();
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField();
    JLabel possibleCausesLabel = new JLabel("Possible Causes:");
    JTextField possibleCausesField = new JTextField();

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] columns = {"Symptom_ID", "Symptom_Name", "Description", "Possible_Causes"};
                String[] values = {
                        symptomIDField.getText(),
                        symptomNameField.getText(),
                        descriptionField.getText(),
                        possibleCausesField.getText()
                };
              //  JFrame parentframe = new JFrame(); // Replace this with your actual JFrame instance.

                insertValues(connection, "Symptoms", columns, values);
                JOptionPane.showMessageDialog(parentframe, "Symptom data inserted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentframe, "Failed to insert Symptom data.");
            }
        }
    });

    // Add components to the frame
    panel.add(symptomIDLabel);
    panel.add(symptomIDField);
    panel.add(symptomNameLabel);
    panel.add(symptomNameField);
    panel.add(descriptionLabel);
    panel.add(descriptionField);
    panel.add(possibleCausesLabel);
    panel.add(possibleCausesField);
    panel.add(submitButton);

    parentFrame.add(panel);
}


    private static void createMedicationsInputPanel(JFrame parentFrame, Connection connection, GridBagConstraints gbc) {
   // JFrame frame = new JFrame("Medications Input");
     JPanel panel = new JPanel(new GridLayout(6,2,10,10));
    //frame.setLayout(new GridLayout(5, 2));

    JLabel medicationIDLabel = new JLabel("Medication ID:");
    JTextField medicationIDField = new JTextField();
    JLabel medicationNameLabel = new JLabel("Medication Name:");
    JTextField medicationNameField = new JTextField();
    JLabel purposeLabel = new JLabel("Purpose:");
    JTextField purposeField = new JTextField();
    JLabel dosageLabel = new JLabel("Dosage:");
    JTextField dosageField = new JTextField();
    JLabel sideEffectsLabel = new JLabel("Side Effects:");
    JTextField sideEffectsField = new JTextField();

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] columns = {"Medication_ID", "Medication_Name", "Purpose", "Dosage", "Side_Effects"};
                String[] values = {
                        medicationIDField.getText(),
                        medicationNameField.getText(),
                        purposeField.getText(),
                        dosageField.getText(),
                        sideEffectsField.getText()
                };
              //  JFrame parentframe = new JFrame(); // Replace this with your actual JFrame instance.

                insertValues(connection, "Medications", columns, values);
                JOptionPane.showMessageDialog(parentframe, "Medication data inserted successfully!");
            } catch (SQLException ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(parentframe, "Failed to insert data. Error: " + ex.getMessage());
}

        }
    });

    // Add components to the frame
    panel.add(medicationIDLabel);
    panel.add(medicationIDField);
    panel.add(medicationNameLabel);
    panel.add(medicationNameField);
    panel.add(purposeLabel);
    panel.add(purposeField);
    panel.add(dosageLabel);
    panel.add(dosageField);
    panel.add(sideEffectsLabel);
    panel.add(sideEffectsField);
    panel.add(submitButton);

    parentFrame.add(panel);
}


    private static void insertValues(Connection connection, String tableName, String[] columnNames, String[] values) throws SQLException {
    StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
    queryBuilder.append(tableName).append(" (");
    for (String column : columnNames) {
        queryBuilder.append(column).append(", ");
    }
    queryBuilder.setLength(queryBuilder.length() - 2); // Remove the last comma and space
    queryBuilder.append(") VALUES (");
    for (int i = 0; i < values.length; i++) {
        queryBuilder.append("?, ");
    }
    queryBuilder.setLength(queryBuilder.length() - 2); // Remove the last comma and space
    queryBuilder.append(")");

    try (PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString())) {
        for (int i = 0; i < values.length; i++) {
            stmt.setString(i + 1, values[i]);
        }
        stmt.executeUpdate();
    }
}

}