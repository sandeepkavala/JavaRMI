/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmisubmission;

import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.net.*;
import java.rmi.*;
import javax.swing.*;

public class EvaluatorClient {

    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {

        //Instantiating Client and connecting to the Server object;
        EvaluatorInt client;
        client = (EvaluatorInt) Naming.lookup("rmi://localhost/Evaluator");
        String username, password;
        ArrayList<Integer> Marks = new ArrayList();

        Scanner in = new Scanner(System.in);
        //Start Authentication Process
        boolean auth = false;
        while (!auth) {
            //Get usrname and password from the client
            username = JOptionPane.showInputDialog("Enter Student ID: ");
            password = JOptionPane.showInputDialog("Enter Password: ");
            
            //Calling function on the Server to validate username and password
            auth = client.Authenticate(username, password);
            if (auth) {
                JOptionPane.showMessageDialog(null, "Login Success", "Login", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed", "Login", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        int enterGrades = 0;
        String currentGrade = "0";
        
        //Initiate User Input for Grades
        while (!currentGrade.equals("-1") && enterGrades <= 30) {
            currentGrade = JOptionPane.showInputDialog("Enter Grade (0-100)(-1 to Finish): ");
            enterGrades++;
            //Send the grades to the server to be stored
            boolean confirm = client.addGrades(currentGrade);
            if (!confirm) {
                JOptionPane.showMessageDialog(null, "Invalid Entry", "Grades", JOptionPane.ERROR_MESSAGE);
                enterGrades--;
            }

        }
        
        //Get the results from the Server
        String[] result = client.Evaluate();
        
        //Display total Marks
        JOptionPane.showMessageDialog(null, result[0], "Course Grades", JOptionPane.INFORMATION_MESSAGE);
        
        if(!result[1].isEmpty()){
            JOptionPane.showMessageDialog(null, result[1], "Result", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
