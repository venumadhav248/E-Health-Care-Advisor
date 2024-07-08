import java.sql.*;

class ConnectDB {
    Statement stmt;
    Connection con;

    void createUsers() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE users (user_id INT PRIMARY KEY, username VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoUsers(int user_id, String username, String email) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO users (user_id, username, email) VALUES (" +
                user_id + ", '" + username + "', '" + email + "')";
        stmt.executeUpdate(query);
    }

    void createMedicalProviders() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE medical_providers (provider_id INT PRIMARY KEY, name VARCHAR(50) NOT NULL, specialization VARCHAR(50) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoMedicalProviders(int provider_id, String name, String specialization) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO medical_providers (provider_id, name, specialization) VALUES (" +
                provider_id + ", '" + name + "', '" + specialization + "')";
        stmt.executeUpdate(query);
    }

    void createMedicalRecords() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE medical_records (record_id INT PRIMARY KEY, user_id INT NOT NULL, provider_id INT NOT NULL, description VARCHAR(500) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoMedicalRecords(int record_id, int user_id, int provider_id, String description) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO medical_records (record_id, user_id, provider_id, description) VALUES (" +
                record_id + ", " + user_id + ", " + provider_id + ", '" + description + "')";
        stmt.executeUpdate(query);
    }

    void createSymptoms() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE symptoms (symptom_id INT PRIMARY KEY, name VARCHAR(50) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoSymptoms(int symptom_id, String name) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO symptoms (symptom_id, name) VALUES (" +
                symptom_id + ", '" + name + "')";
        stmt.executeUpdate(query);
    }

    void createResources() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE resources (resource_id INT PRIMARY KEY, name VARCHAR(50) NOT NULL, url VARCHAR(100) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoResources(int resource_id, String name, String url) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO resources (resource_id, name, url) VALUES (" +
                resource_id + ", '" + name + "', '" + url + "')";
        stmt.executeUpdate(query);
    }

    ConnectDB() throws SQLException, ClassNotFoundException {
        String username = "venumadhav";
        String password = "venu24";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
        stmt = con.createStatement();
    }
}

public class EHealthCareAdvisor {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectDB obj = new ConnectDB();
        obj.createUsers();
        obj.createMedicalProviders();
        obj.createMedicalRecords();
        obj.createSymptoms();
        obj.createResources();

        /*obj.insertIntoUsers(1, "JohnDoe", "john.doe@example.com");
        obj.insertIntoMedicalProviders(1, "Dr. Smith", "Cardiology");
        obj.insertIntoMedicalRecords(1, 1, 1, "Patient experienced chest pain.");
        obj.insertIntoSymptoms(1, "Chest pain");
        obj.insertIntoResources(1, "Heart Health Guide", "https://www.example.com/heart-health-guide");*/
    }
}