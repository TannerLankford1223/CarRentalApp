package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Class holds information important for connecting to the database
public class DBManager {

    private static final String URL_ROOT = "jdbc:h2:./src/carsharing/db/";
    private static final String DEFAULT_DB_NAME = "carsharing";
    private static String url;

    // Statement Strings
    private static final String createCompanyTable = "CREATE TABLE IF NOT EXISTS company (\n" +
            "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "name VARCHAR(50) NOT NULL UNIQUE)\n";

    private static final String createCarTable = "CREATE TABLE IF NOT EXISTS car (\n" +
            "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "name VARCHAR(50) UNIQUE NOT NULL,\n" +
            "company_id INT NOT NULL,\n" +
            "is_rented BOOLEAN,\n" +
            "CONSTRAINT fk_id FOREIGN KEY (company_id)\n" +
            "REFERENCES company(id)\n" +
            "ON DELETE CASCADE)";

    private static final String createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (\n " +
            "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "name VARCHAR(50) UNIQUE NOT NULL,\n" +
            "rented_car_id INT DEFAULT NULL,\n " +
            "CONSTRAINT fk_rented_car_id FOREIGN KEY (rented_car_id)\n" +
            "REFERENCES car(id))";

    // Build The three necessary tables
    public static void initialize(String[] args) {
        setUrl(args);

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createCompanyTable);
            stmt.execute(createCarTable);
            stmt.execute(createCustomerTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        return url;
    }

    // Retrieves the database name from the args provided by the user
    private static void setUrl(String[] args) {
        StringBuilder urlBuilder = new StringBuilder(URL_ROOT);
        if (args.length < 2 || !"-databaseFileName".equals(args[0])) {
            url = urlBuilder.append(DEFAULT_DB_NAME).toString();
        } else {
            url = urlBuilder.append(args[1]).toString();
        }
    }
}

