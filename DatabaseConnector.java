package it.santosalvatore;

/* La classe "DatabaseConnector" gestisce la connessione al database MySQL.
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://87.10.158.46/e-commerce";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    //Il metodo connect() restituisce una connessione al database utilizzando il DriverManager di JDBC.
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    //Il metodo getConnection() è simile a connect(), ma include anche una chiamata a Class.forName("com.mysql.cj.jdbc.Driver") per caricare il driver JDBC MySQL
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connessione al database stabilita!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //Il metodo closeConnection(Connection connection) chiude la connessione al database se non è nulla.
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione al database chiusa!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}