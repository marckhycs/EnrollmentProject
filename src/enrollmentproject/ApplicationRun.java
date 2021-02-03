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
public class ApplicationRun {
    
    public static void Process() throws InterruptedException{
        Scanner choice = new Scanner(System.in);
        
        GUI.welcome();
        GUI.mainPage();
        System.out.print("Please enter number here: ");
        try{
            int action1 = choice.nextInt();
        
            switch(action1){
                case 1:
                    GUI.enrollAStudent();
                    break;
                case 2:
                    GUI.searchAStudent();
                    break;
                case 3:
                    GUI.updateAStudent();
                    break;
                case 4:
                    GUI.removeAStudent();
                    break;
                case 5:
                    GUI.showStudents();
                case 6:
                    System.exit(0);
                default:
                    ApplicationRun.Process();
                    break;
            }
        }
        catch(Exception e){
            System.out.println("Enter a appropriate choice");
            TimeUnit.SECONDS.sleep(3);
            Process();
        }
        
    }
    
}
