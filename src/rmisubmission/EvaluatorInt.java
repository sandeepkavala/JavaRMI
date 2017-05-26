/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmisubmission;

/**
 *
 * @author Sandeep
 */
import java.rmi.*;

// Creating the Interface for the RMI Project
public interface EvaluatorInt extends Remote {
    
    //Method to Authenticate User
    public boolean Authenticate(String username, String password) throws RemoteException;
    
    //Method to Calcuate the results
    public String[] Evaluate() throws RemoteException;
    
    //Method to store grades on the Server
    public boolean addGrades(String grade) throws RemoteException;
}