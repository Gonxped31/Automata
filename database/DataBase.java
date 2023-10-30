package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import logic.Automate;

public class DataBase {

    private static Connection connect() {
        // SQLite connection string  
        String url = "jdbc:sqlite:C:/Users/Samir/Documents/GitHub/Automate/database/automates.db";
        Connection connection = null;  
        try {  
            connection = DriverManager.getConnection(url);  
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {  
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
            pstmt.setString(2, new StringBuilder().append(automate.getQ_0()).toString());
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

    /*private static void removeAutomate(String automName){
        Connection connection = connect();
    }*/

}
