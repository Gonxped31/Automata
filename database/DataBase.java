package database;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import UI.AutomatonInputGUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import logic.Automaton;

public class DataBase {

    private static String tableName = "automates_table";

    public static Connection connect() {
        Connection connection = null;
        try {
            String jarPath = new File(AutomatonInputGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            String dbFilePath = jarPath + File.separator + "automates.db";
            // Check if the database file exists
            File dbFile = new File(dbFilePath);
            if (!dbFile.exists()) {
                System.out.println("Database file does not exist. Creating a new database.");

                // Create a new database file (empty) if it doesn't exist
                if (dbFile.createNewFile()) {
                    System.out.println("Database file created successfully.");
                } else {
                    System.out.println("Failed to create the database file.");
                }
            }

            // JDBC URL for connecting to the SQLite database
            String url = "jdbc:sqlite:" + dbFilePath;
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | URISyntaxException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static String saveAutomate(String automName, Automaton automaton){
        
        // Table colums SQL statement
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (")
            .append("id TEXT, ")
            .append("states TEXT, ")
            .append("alphabet TEXT, ")
            .append("transitions TEXT, ")
            .append("starting_state TEXT, ")
            .append("ending_states TEXT")
            .append(")");

        // Insert elements SQL statement
        StringBuilder saveAutomateSql = new StringBuilder();
        saveAutomateSql.append("INSERT INTO ").append(tableName).append("(")
            .append("id, ").append("states, ").append("alphabet, ").append("transitions, ")
            .append("starting_state, ").append("ending_states").append(") ")
            .append("VALUES (?,?,?,?,?,?)");
        

        try {

            Connection connection = connect();

            
            // Create a table if there is no table yet
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql.toString());
            System.out.println("Table created succesfully.");

            if (verifyAutomatonExistence(automName)) {
                return "Error: This automaton already exists";
            } else {
                PreparedStatement pstmt = connection.prepareStatement(saveAutomateSql.toString());
                pstmt.setString(1, automName);
                pstmt.setString(2, new StringBuilder().append(automaton.getQ()).toString());
                pstmt.setString(3, new StringBuilder().append(automaton.getSigma()).toString());
                pstmt.setString(4, new StringBuilder().append(automaton.getDelta()).toString());
                pstmt.setString(5, automaton.getQ_0());
                pstmt.setString(6, new StringBuilder().append(automaton.getF()).toString());
                pstmt.executeUpdate();
                System.out.println("Automaton saved successfully.");
                return "Automaton saved successfully.";
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Error while saving data.";
        }
    }

    private static boolean verifyAutomatonExistence(String automatonName){
        Connection connection = connect();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ").append(tableName).append(" WHERE id = ?");

            PreparedStatement pstmt = connection.prepareStatement(sql.toString());
            pstmt.setString(1, automatonName);
            ResultSet rSet = pstmt.executeQuery();

            ArrayList<String> id = new ArrayList<>();

            while (rSet.next()) {
                id.add(rSet.getString("id"));
            }
            return id.size() == 1;

        } catch (SQLException e) {
           e.printStackTrace();
            return false;
        }
    }

    public static String removeAutomate(String automName){
        Connection connection = connect();

        try {
            if (verifyAutomatonExistence(automName)){
                StringBuilder sql = new StringBuilder();
                sql.append("DELETE FROM ").append(tableName).append(" WHERE id = ?");

                PreparedStatement pstmt = connection.prepareStatement(sql.toString());
                pstmt.setString(1, automName);
                pstmt.executeUpdate();

                System.out.println("Automaton " + automName +  " deleted successfully.");
                return "Automaton deleted successfully.";
            } else {
                System.out.println("Automaton " + automName +  " doesn't exist");
                return "Automaton " + automName +  " doesn't exist";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error while checking data.";
        }

    }

    public static ArrayList<ArrayList<String>> getAutomates(){
        Connection connection = connect();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> states = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        ArrayList<String> transitions = new ArrayList<>();
        ArrayList<String> startingState = new ArrayList<>();
        ArrayList<String> endingStates = new ArrayList<>();

        result.add(names);
        result.add(states);
        result.add(alphabet);
        result.add(transitions);
        result.add(startingState);
        result.add(endingStates);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(tableName);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rSet = stmt.executeQuery(sql.toString());

            while (rSet.next()) {
                names.add(rSet.getString("id"));
                states.add(rSet.getString("states"));
                alphabet.add(rSet.getString("alphabet"));
                transitions.add(rSet.getString("transitions"));
                startingState.add(rSet.getString("starting_state"));
                endingStates.add(rSet.getString("ending_states"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}