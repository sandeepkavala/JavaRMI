package rmisubmission;

import java.rmi.*;
import java.util.*;
import java.text.DecimalFormat;

public class EvaluatorImp extends java.rmi.server.UnicastRemoteObject implements EvaluatorInt {

    //Variables to Store Data on the Server
    static ArrayList<Integer> arr = new ArrayList();
    static int counter = 0;
    static int fail;
    static String studentID;

    //Calling the Super Constructor
    public EvaluatorImp() throws RemoteException {
        super();
    }

    //Method to Authenticate user into the RMI server
    public boolean Authenticate(String username, String password) throws RemoteException {
        if (username.equals("csi3344") && password.equals("admin")) {
            //Store the StudentID on the Server and send confirmation
            studentID = username;
            return true;
        } else {
            return false;
        }
    }

    //Method to calculate the results
    public String[] Evaluate() throws RemoteException {
        //Call to helper function
        String[] results = serEvaluate();
        return results;
    }

    //Static function to use the static varaibles stored on the Server
    public static String[] serEvaluate() {

        //Initialise Variables required
        double total, top8total, average, top8average;
        String[] results = new String[2];
        DecimalFormat twodigits = new DecimalFormat("0.00");
        String avg, topavg;
        total = 0;
        top8total = 0;
        average = 0;
        top8average = 0;

        StringBuilder sc = new StringBuilder();

        //Check for minimum number of subjects entered
        if (counter < 12) {
            clearAll();
            results[0] = "Minimum 12 Subjects Required to Calculate Results";
            results[1] = "";
            return results;
        }

        //Student Failing in 6 or more subjects results in disqualification
        if (fail > 5) {
            clearAll();
            results[0] = "<html>Failed more than 6 subects <br> Disqualified</html>";
            results[1] = "";
            return results;
        }

        //Calculate the Grades and append the results 
        sc.append("<html>");
        sc.append("Grades ");
        for (int i = 0; i < arr.size(); i++) {
            total = total + arr.get(i);
            sc.append("<br>").append(i + 1).append(". ").append(arr.get(i).toString());
        }

        //Sort the ArrayList to obtain best 8 marks
        Collections.sort(arr, Collections.reverseOrder());

        for (int i = 0; i < 8; i++) {
            top8total = top8total + arr.get(i);
        }

        //Calculate Course Average
        average = total / counter;
        top8average = top8total / 8;

        //Calculate Best Average
        avg = twodigits.format(average);
        topavg = twodigits.format(top8average);

        results[0] = sc.toString();

        sc.setLength(0);
        sc.append("<html> Student ID: ");
        sc.append(studentID);
        sc.append("<br> <br>Course Average: ");
        sc.append(avg);
        if (average >= 70) {

            sc.append("<br> <br> QUALIFIED FOR HONOURS STUDY!");
        } else if (average < 70 && top8average >= 80) {
            sc.append("<br> <br>Best 8 Average: ");
            sc.append(topavg);
            sc.append("<br> <br> MAY HAVE GOOD CHANCE! Need further assessment!");
        } else if (average < 70 && top8average >= 70) {
            sc.append("<br> <br>Best 8 Average: ");
            sc.append(topavg);
            sc.append("<br> <br> MAY HAVE A CHANCE! Must be carefully reassessed and get the coordinator’s special permission!");
        } else {
            sc.append("<br> <br>Best 8 Average: ");
            sc.append(topavg);
            sc.append("<br> <br> DOES NOT QUALIFY FOR HONORS STUDY! Try Masters by course work.");
        }
        results[1] = sc.toString();
        //Clear static variabels in the server
        clearAll();
        //Return results
        return results;

    }

    //Process the grade data recieved from the client
    public boolean addGrades(String grades) throws RemoteException {

        int newGrade;
        //Convert the grades to Integer
        try {
            newGrade = Integer.parseInt(grades);
            //If grades are outside the valid range return error
            if (newGrade > 100 || newGrade < -1) {
                return false;
            }
            if (newGrade != -1) {
                //Store the grades on the Server
                addList(newGrade);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    //Static Method to Store Grades on the Server
    public static void addList(int grades) {

        arr.add(grades);
        if (grades < 40) {
            fail++;
        }
        counter++;

    }
    
    //Clearing Local Variables when grades are sent
    public static void clearAll() {
        arr.clear();
        fail = 0;
        counter = 0;

    }

}