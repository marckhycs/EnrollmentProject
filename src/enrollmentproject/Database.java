/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentproject;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author markc
 */
public class Database {
    
    private static String jdbcURL = "jdbc:mysql://localhost:3306/enrollmentdb";
    private static String username = "root";
    private static String password = "root";
    
    
    
    public static String insertToDB(String[] information){
        int row;
        
        try {
            Connection connection = DriverManager.getConnection(jdbcURL,username,password);
            String insertCommand = "INSERT INTO studentdirectory (studentLastName,studentFirstName,studentMiddleName,"
                    + "studentGender,studentSection,studentYear,studentCourse,studentStatus) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareCall(insertCommand);
            
            for (int i = 0; i < information.length; i++) {
                statement.setString(i+1,information[i]);
            }
            row = statement.executeUpdate();

        } catch (SQLException ex) {
            return ex.toString();
        }
        if (row>0) {
            return "Successfully enrolled the student\n";
        }
        else{
            return "There's a problem while processing the data";
        }
        
        
    }
    
    public static void searchToDB(String sName) throws InterruptedException{
        try{
            String variableSample = "%"+sName+"%";
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = String.format("SELECT studentID, studentLastName, studentFirstName, studentMiddleName, studentCourse FROM studentdirectory WHERE studentLastName LIKE '%s'", variableSample);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(command);
            
            
            String retrievedID = "";
            String retrievedLN = "";
            String retrievedFN = "";
            String retrievedMN = "";
            String retrievedCs = "";
            String retrievedInfo = "";
            System.out.println("+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            System.out.printf("|%-12s|%-30s|%-30s|%-30s|%-30s|","Student ID","Last Name", "First Name", "Middle Name", "Course");
            System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            
            while(result.next()){
                retrievedID = result.getString(1);
                retrievedLN = result.getString(2);
                retrievedFN = result.getString(3);
                retrievedMN = result.getString(4);
                retrievedCs = result.getString(5);
                
                retrievedInfo = retrievedID;
                System.out.printf("|%-12s|%-30s|%-30s|%-30s|%-30s|",retrievedID, retrievedLN, retrievedFN, retrievedMN, retrievedCs);
                System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            }
            connection.close();
            
            if (retrievedInfo.isEmpty()) {
                System.out.println("No Results found!");
                System.out.println("Please wait.....");
                GUI.cls();
            }
            
            
            
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public static void deleteToDB(int SID) throws InterruptedException{
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = "DELETE from studentdirectory WHERE studentID = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setInt(1, SID);
            
            int rows = statement.executeUpdate();
            if (rows>0) {
                System.out.println("Student information has been deleted from the database..");
                System.out.println("Going back to the main page...");
                GUI.cls();
                ApplicationRun.Process();
            }
            else{
                System.out.println("No record has been deleted");
                System.out.println("Going back to the main page...");
                GUI.cls();
                ApplicationRun.Process();
            }
            connection.close();
            
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public static void showStudent(String program, String year, String section) throws InterruptedException{
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = String.format("SELECT studentID, studentLastName, studentFirstName, studentMiddleName, studentCourse"
                    + " FROM studentdirectory WHERE studentSection = \"%s\" OR studentYear = \"%s\" OR studentCourse = \"%s\" ORDER by studentLastName ASC", section, year, program);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(command);
            String retrievedID = "";
            String retrievedLN = "";
            String retrievedFN = "";
            String retrievedMN = "";
            String retrievedCs = "";
            String retrievedInfo = "";
            System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            System.out.printf("|%-12s|%-30s|%-30s|%-30s|%-30s|","Student ID","Last Name", "First Name", "Middle Name", "Course");
            System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            
            while(result.next()){
                retrievedID = result.getString(1);
                retrievedLN = result.getString(2);
                retrievedFN = result.getString(3);
                retrievedMN = result.getString(4);
                retrievedCs = result.getString(5);
                
                retrievedInfo = retrievedID;
                System.out.printf("|%-12s|%-30s|%-30s|%-30s|%-30s|",retrievedID, retrievedLN, retrievedFN, retrievedMN, retrievedCs);
                System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            }
            connection.close();
            
            if (retrievedInfo.isEmpty()) {
                System.out.println("No Results found!");
                System.out.println("Please wait.....");
                GUI.cls();
            }
            
            
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
    }
    
    public static void searchByID(int SID) throws InterruptedException{
        try{
            
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = String.format("SELECT studentID, studentLastName, studentFirstName, studentMiddleName, studentCourse FROM studentdirectory WHERE studentID = %d", SID);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(command);
            
            
            String retrievedID = "";
            String retrievedLN = "";
            String retrievedFN = "";
            String retrievedMN = "";
            String retrievedCs = "";
            String retrievedInfo = "";
            System.out.println("+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            System.out.printf("|%-12s|%-30s|%-30s|%-30s|%-30s|","Student ID","Last Name", "First Name", "Middle Name", "Course");
            System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            
            while(result.next()){
                retrievedID = result.getString(1);
                retrievedLN = result.getString(2);
                retrievedFN = result.getString(3);
                retrievedMN = result.getString(4);
                retrievedCs = result.getString(5);
                
                retrievedInfo = retrievedID;
                System.out.printf("|%-12s|%-30s|%-30s|%-30s|%-30s|",retrievedID, retrievedLN, retrievedFN, retrievedMN, retrievedCs);
                System.out.println("\n+------------+------------------------------+------------------------------+"
                    + "------------------------------+------------------------------+");
            }
            connection.close();
            
            if (retrievedInfo.isEmpty()) {
                System.out.println("No Results found!");
                System.out.println("Please wait.....");
                GUI.cls();
            }
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
    }
    
    public static void updateLastName(String lastName, int SID){
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = "UPDATE studentdirectory SET studentLastName = ? WHERE studentID = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, lastName);
            statement.setInt(2, SID);
            
            int rows = statement.executeUpdate();
            if (rows>0) {
                System.out.println("Student last name has been updated...");
            }
            
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public static void updateFirstName(String firstName, int SID){
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = "UPDATE studentdirectory SET studentFirstName = ? WHERE studentID = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, firstName);
            statement.setInt(2, SID);
            
            int rows = statement.executeUpdate();
            if (rows>0) {
                System.out.println("Student first name has been updated...");
            }
            
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public static void updateMiddleName(String middleName, int SID){
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = "UPDATE studentdirectory SET studentMiddleName = ? WHERE studentID = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, middleName);
            statement.setInt(2, SID);
            
            int rows = statement.executeUpdate();
            if (rows>1) {
                System.out.print("Student middle name has been updated.");
            }
            connection.close();
        }
        catch(SQLException e){
            System.out.print(e);
        }
    }
    
    public static void updateSection(String section, int SID){
        try{
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String command = "UPDATE studentdirectory SET studentSection = ? WHERE studentID = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, section);
            statement.setInt(2, SID);
            
            int rows = statement.executeUpdate();
            if (rows>0) {
                System.out.println("Student section has been updated");
            }
            connection.close();
        }
        catch(SQLException ex){
            System.out.println(ex.toString());
        }
    }
}
