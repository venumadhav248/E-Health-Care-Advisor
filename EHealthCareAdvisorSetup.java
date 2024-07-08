import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class EHealthCareAdvisorSetup {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "madhav";
    private static final String DB_PASSWORD = "venu24";

    public static void main(String[] args) {
        // Register the JDBC driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            insertValuesIntoUsers(connection);
            insertValuesIntoHealthcareProfessionals(connection);
            insertValuesIntoMedicalFacilities(connection);
            insertValuesIntoMedicalConditions(connection);
            insertValuesIntoSymptoms(connection);
            insertValuesIntoMedications(connection);
            //insertValuesIntoAppointments(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private static void insertValuesIntoUsers(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User details:");
        System.out.print("User ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

       System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Medical History: ");
        String medicalHistory = scanner.nextLine();

        System.out.print("Contact Information: ");
        String contactInformation = scanner.nextLine();

        String[] columns = {"User_ID", "Name", "Age", "Gender", "Email", "Password", "Medical_History", "Contact_Information"};
        String[] values = {String.valueOf(userID), name, String.valueOf(age), gender, email, password, medicalHistory, contactInformation};
        insertValues(connection, "Users", columns, values);
    }

    private static void insertValuesIntoHealthcareProfessionals(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Healthcare Professional details:");
        System.out.print("Professional ID: ");
        int professionalID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();

        System.out.print("Experience: ");
        int experience = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Contact Information: ");
        String contactInformation = scanner.nextLine();

        String[] columns = {"Professional_ID", "Name", "Specialization", "Experience", "Contact_Information"};
        String[] values = {String.valueOf(professionalID), name, specialization, String.valueOf(experience), contactInformation};
        insertValues(connection, "Healthcare_Professionals", columns, values);
    }

    private static void insertValuesIntoMedicalFacilities(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Medical Facility details:");
        System.out.print("Facility ID: ");
        int facilityID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Facility Name: ");
        String facilityName = scanner.nextLine();

        System.out.print("Location: ");
        String location = scanner.nextLine();

        System.out.print("Contact Information: ");
        String contactInformation = scanner.nextLine();

        System.out.print("Services Offered: ");
        String servicesOffered = scanner.nextLine();

        String[] columns = {"Facility_ID", "Facility_Name", "Location", "Contact_Information", "Services_Offered"};
        String[] values = {String.valueOf(facilityID), facilityName, location, contactInformation, servicesOffered};
        insertValues(connection, "Medical_Facilities", columns, values);
    }

    private static void insertValuesIntoMedicalConditions(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Medical Condition details:");
        System.out.print("Condition ID: ");
        int conditionID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Condition Name: ");
        String conditionName = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Treatment Options: ");
        String treatmentOptions = scanner.nextLine();

        String[] columns = {"Condition_ID", "Condition_Name", "Description", "Treatment_Options"};
        String[] values = {String.valueOf(conditionID), conditionName, description, treatmentOptions};
        insertValues(connection, "Medical_Conditions", columns, values);
    }

    private static void insertValuesIntoSymptoms(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Symptom details:");
        System.out.print("Symptom ID: ");
        int symptomID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Symptom Name: ");
        String symptomName = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Possible Causes: ");
        String possibleCauses = scanner.nextLine();

        String[] columns = {"Symptom_ID", "Symptom_Name", "Description", "Possible_Causes"};
        String[] values = {String.valueOf(symptomID), symptomName, description, possibleCauses};
        insertValues(connection, "Symptoms", columns, values);
    }

    private static void insertValuesIntoMedications(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Medication details:");
        System.out.print("Medication ID: ");
        int medicationID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Medication Name: ");
        String medicationName = scanner.nextLine();

        System.out.print("Purpose: ");
        String purpose = scanner.nextLine();

        System.out.print("Dosage: ");
        String dosage = scanner.nextLine();

        System.out.print("Side Effects: ");
        String sideEffects = scanner.nextLine();

        String[] columns = {"Medication_ID", "Medication_Name", "Purpose", "Dosage", "Side_Effects"};
        String[] values = {String.valueOf(medicationID), medicationName, purpose, dosage, sideEffects};
        insertValues(connection, "Medications", columns, values);
    }

    


}
