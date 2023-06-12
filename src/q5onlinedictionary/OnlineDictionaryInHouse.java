package q5onlinedictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class OnlineDictionaryInHouse {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;

    public static void findWord(String searchQuery){
        try {

            // Create a statement
            statement = connection.createStatement();
            // Execute a query
            String query = "SELECT * FROM entries WHERE word = '" + searchQuery + "'";
            resultSet = statement.executeQuery(query);
            // Process the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("id");
                String word = resultSet.getString("word");
                String wordType = resultSet.getString("wordType");
                String definition = resultSet.getString("definition");

                // Print the data
                System.out.println("\nWord: " + word);
                System.out.println("Word Type: " + wordType);
                System.out.println("Definition: " + definition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/dictionary";
        String username = "root";
        String password = "";

        try {
            // Step 1: Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Open a connection
            connection = DriverManager.getConnection(url, username, password);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the word you wish to search for: ");
            String searchQuery = inputReader.readLine();
            findWord(searchQuery);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Step 6: Clean up resources
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
