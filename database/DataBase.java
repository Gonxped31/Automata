package database;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import UI.AutomatonInputGUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import logic.Automate;

public class DataBase {

    public static Connection connect() {
        // Construct the full URL based on the application's location

        Connection connection = null;  
        try {
            String jarPath = new File(AutomatonInputGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            String url = "jdbc:sqlite:" + jarPath + File.separator + "automates.db";
            connection = DriverManager.getConnection(url);  
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | URISyntaxException e) {  
            System.out.println(e.getMessage());  
        }
        return connection;  
    }

    public static boolean saveAutomate(String automName, Automate automate){
        
        // Table colums SQL statement
        StringBuilder creaateTableSql = new StringBuilder();
        creaateTableSql.append("CREATE TABLE IF NOT EXISTS automates_table (")
            .append("id TEXT, ")
            .append("states TEXT, ")
            .append("alphabet TEXT, ")
            .append("transitions TEXT, ")
            .append("starting_state TEXT, ")
            .append("ending_states TEXT")
            .append(")");

        // Insert elements SQL statement
        StringBuilder saveAutomateSql = new StringBuilder();
        saveAutomateSql.append("INSERT INTO automates_table (")
            .append("id, ").append("states, ").append("alphabet, ").append("transitions, ")
            .append("starting_state, ").append("ending_states").append(") ")
            .append("VALUES (?,?,?,?,?,?)");
        

        try {

            Connection connection = connect();

            // Create a table if there is no table yet
            Statement stmt = connection.createStatement();
            stmt.execute(creaateTableSql.toString());
            System.out.println("Table created succesfully.");
            
            PreparedStatement pstmt = connection.prepareStatement(saveAutomateSql.toString());
            pstmt.setString(1, automName);
            pstmt.setString(2, new StringBuilder().append(automate.getQ()).toString());
            pstmt.setString(3, new StringBuilder().append(automate.getSigma()).toString());
            pstmt.setString(4, new StringBuilder().append(automate.getDelta()).toString());
            pstmt.setString(5, automate.getQ_0());
            pstmt.setString(6, new StringBuilder().append(automate.getF()).toString());
            pstmt.executeUpdate();
            System.out.println("Automate saved successfully.");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean removeAutomate(String automName){
        Connection connection = connect();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM automates_table WHERE id = ?");

            PreparedStatement pstmt = connection.prepareStatement(sql.toString());
            pstmt.setString(1, automName);
            pstmt.executeUpdate();

            System.out.println("Automate " + automName +  " deleted successfully.");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
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
        sql.append("SELECT * FROM automates_table ");

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