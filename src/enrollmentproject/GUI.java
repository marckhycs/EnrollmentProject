/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentproject;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author markc
 */
public class GUI {
    public static String[] fillOut = {"Last Name: ", "First Name: ","Middle Name: ",
        "Gender: ", "Section: ", "Year: ", "Program: ", "Status: "};
    public static Scanner gui = new Scanner(System.in);
    
    public static void mainPage(){
        String[] actions = {"Enroll a student", "Search a student", "Update student information",
        "Unenroll a student", "Show all students", "Exit"};
        System.out.println("What do you want to do?");
        for (int i = 0; i < actions.length; i++) {
            System.out.format("%d. %s\n",i+1,actions[i]);
        }
        
    }
    
    public static void welcome(){
        
        System.out.println("                _ ");                         
        System.out.println("               | |");                         
        System.out.println("  __      _____| | ___ ___  _ __ ___   ___"); 
        System.out.println("  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\");
        System.out.println("   \\ V  V /  __/ | (_| (_) | | | | | |  __/");
        System.out.println("    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|");
        System.out.println("\n");
                                          
    }
    
    public static void enrollAStudent() throws InterruptedException{
        String[] studentInfo = new String[fillOut.length];
        
        for (int i = 0; i < fillOut.length; i++) {
            System.out.format("%s", fillOut[i]);
            studentInfo[i] = gui.nextLine();
        }
        System.out.println("Saving..........");
        TimeUnit.SECONDS.sleep(5);
        String flag = Database.insertToDB(studentInfo);
        System.out.print(flag);
        cls();
        ApplicationRun.Process();
    }
    
    public static void searchAStudent() throws InterruptedException{
        System.out.print("Enter student Last Name: ");
        String studentName = gui.nextLine();
        System.out.println("Searching.......");
        TimeUnit.SECONDS.sleep(3);
        Database.searchToDB(studentName);
        System.out.println("What do you want to do next? ");
        System.out.println("1. Update student information\n2. Unenroll a student\n3. Go back to main page");
        System.out.print("Enter here: ");
        int nextToDo = gui.nextInt();
        switch(nextToDo){
            case 1:
                updateAStudent();
                break;
            case 2:
                removeAStudent();
                break;
            case 3:
                ApplicationRun.Process();
                break;
            default:
                ApplicationRun.Process();
                break;
        }
        
    }
    
    public static void removeAStudent() throws InterruptedException{
        
        System.out.print("Enter student ID you want to remove: ");
        int SID = gui.nextInt();
        Database.searchByID(SID);
        System.out.println("REMEMBER!! Once you deleted the information, you cannot retrieved it anymore.");
        System.out.print("Do you want to continue? y/n: ");
        String continueToDelete = gui.next();
        if (continueToDelete.equalsIgnoreCase("y")) {
            System.out.println("Deleting....");
            cls();
            Database.deleteToDB(SID);
        }
        else{
            System.out.print("Cancelling the process.....");
            cls();
            ApplicationRun.Process();
        }
    }
    
    public static void showStudents() throws InterruptedException{
        System.out.print("Enter Program: ");
        String program = gui.nextLine();
        System.out.print("Enter Year: ");
        String year = gui.nextLine();
        System.out.print("Enter Section: ");
        String section = gui.nextLine();
        System.out.print("Searching......");
        cls();
        Database.showStudent(program,year,section);
        System.out.println("\nPress Enter key to continue...");
        try
        {
            System.in.read();
            ApplicationRun.Process();
        }  
        catch(Exception e)
        {} 
        
    }
    
    public static void updateAStudent() throws InterruptedException{
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.print("What do you want to update? \n");
        for (int i = 0; i < fillOut.length; i++) {
            System.out.format("%d. %s\n", i+1, fillOut[i]);
        }
        System.out.print("Enter here: ");
        int decide = gui.nextInt();
        System.out.print("Enter student id: ");
        int stdID = gui.nextInt();
        
        Database.searchByID(stdID);
        
        switch(decide){
            case 1:
                gui.nextLine();
                System.out.print("Enter new last name: ");
                String surname = gui.nextLine();
                Database.updateLastName(surname, stdID);
                Database.searchByID(stdID);
                System.out.println("Going back to the main page");
                cls();
                ApplicationRun.Process();
                break;
            case 2:
                gui.nextLine();
                System.out.print("Enter new first name: ");
                String firstname = gui.nextLine();
                Database.updateFirstName(firstname, stdID);
                Database.searchByID(stdID);
                System.out.println("Going back to the main page");
                cls();
                ApplicationRun.Process();
                break;
            case 3:
                gui.nextLine();
                System.out.print("Enter new middle name: ");
                String middlename = gui.nextLine();
                Database.updateMiddleName(middlename, stdID);
                Database.searchByID(stdID);
                System.out.println("Going back to the main page");
                cls();
                ApplicationRun.Process();
                break;
            case 4:
                gui.nextLine();
                System.out.print("Enter new middle name: ");
                String section = gui.nextLine();
                Database.updateSection(section, stdID);
                Database.searchByID(stdID);
                System.out.println("Going back to the main page");
                cls();
                ApplicationRun.Process();
                break;
            default:
                updateAStudent();
                break;
        }
        
        
    }
    
    public static void cls() throws InterruptedException{
        TimeUnit.SECONDS.sleep(3);
    }
}
