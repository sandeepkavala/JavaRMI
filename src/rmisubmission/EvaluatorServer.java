package rmisubmission;

import java.rmi.registry.*;
import java.rmi.*;
import javax.swing.*;

public class EvaluatorServer {

    public static void main(String[] args) throws RemoteException {
        
        //Initialing the Server
        try {
            //Create Registry Port for the Server
            Registry registry = LocateRegistry.createRegistry(1099);
            
            //Bind the server name and start the server class
            registry.rebind("Evaluator", new EvaluatorImp());
            
            //Display Server Success Message
            JOptionPane.showMessageDialog(null, "Server Started");
        } catch (Exception e) {
            
           //Display Error Message
            JOptionPane.showMessageDialog(null, "Server not running!");
        }

    }
}
