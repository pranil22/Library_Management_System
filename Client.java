import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import utilities.*;

public class Client {
    private Socket socket;
    private PrintWriter printWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private Helper helper = new Helper();
    private String isAdmin = null;
    private int curentStudentId;


    public void startMenu() {
        System.out.println("1.Login as Admin");
        System.out.println("2.Create an Admin account");
        System.out.println("3.Login as Student");
        System.out.println("4.Exit");
    }

    public String loginAdminOrStudent() {
        String username, password;
        username = helper.getUserInput("Enter username : ");
        password = helper.getUserInput("Enter password : ");

        String res = username + "/" + password;

        return res;
    }

    public String createAdmin() {
        String id,username, password, firstname, lastname;
        username = helper.getUserInput("Enter username : ");
        password = helper.getUserInput("Enter password : ");
        firstname = helper.getUserInput("Enter firstname : ");
        lastname = helper.getUserInput("Enter lastname : ");

        String res = username + "/" + password + "/" + firstname + "/" + lastname;

        return res;
    }



    public void adminOperationsMenu() {
        System.out.println("1.Add a book");
        System.out.println("2.Add a Student");
        System.out.println("3.Issue book");
        System.out.println("4.Return book");
        System.out.println("5.Logout");
    }



    public void studentOperationsMenu() {
        System.out.println("1.Books available in Library");
        System.out.println("2.Books with issue and return dates");
        System.out.println("3.Logout");
    }


    public String addBookHelper() {
        String id,title,author,publisher,cost,qty;
        title = helper.getUserInput("Enter title: ");
        author = helper.getUserInput("Enter author : ");
        publisher = helper.getUserInput("Enter publisher : ");
        cost = helper.getUserInput("Enter cost : ");
        qty = helper.getUserInput("Enter Quantity : ");

        String res =  title + "/" + author + "/" + publisher + "/" + cost + "/" + qty;

        return res;
    }

    public String addStudentHelper() {

        String id, username, password, firstname, lastname;

        username = helper.getUserInput("Enter username : ");
        password = helper.getUserInput("Enter password : ");
        firstname = helper.getUserInput("Enter firstname : ");
        lastname = helper.getUserInput("Enter lastname : ");

        String res = username + "/" + password + "/" + firstname + "/" + lastname;

        return res;
    }

    public void start() {
        try {
            System.out.println("Connecting to Server...");

            boolean run = true;

            String res, data, bookId, studentId;
            new MainScreen();


//            while (run) {
//                socket = new Socket("localhost", 5000);
//                int choice;
//                printWriter = new PrintWriter(socket.getOutputStream());
//                inputStreamReader = new InputStreamReader(socket.getInputStream());
//                bufferedReader = new BufferedReader(inputStreamReader);
//
//                String res, data, bookId, studentId;
//
//                if(isAdmin == null) {
//                }
//                else if(isAdmin.equals("N")) {
//                    studentOperationsMenu();
//                    choice = Integer.parseInt(helper.getUserInput("Enter ur choice : "));
//                    res = "3" + "/" + choice + "/";
//                    if(choice == 2) {
//                        res += curentStudentId + "/";
//                    }
//                    printWriter.println(res);
//                    printWriter.flush();
//                    System.out.println("------------------------------------------------------");
//                    String msg1 = bufferedReader.readLine();
//                    if(msg1.equals("Logged Out successfully")) {
//                        System.out.println(msg1);
//                        isAdmin = null;
//                    }
//                    else {
//                        String[] messages = msg1.split("/");
//                        for(String m1: messages) {
//                            System.out.println(m1);
//                        }
//                    }
//                    System.out.println("------------------------------------------------------");
//                }
//                else {
//                    adminOperationsMenu();
//                    choice = Integer.parseInt(helper.getUserInput("Enter ur choice : "));
//                    System.out.println("-------------------------------------------------");
//                    res = "1" + "/" + choice + "/";
//                    switch (choice) {
//                        case 1:
//                            res += addBookHelper();
//                            break;
//                        case 2:
//                            res += addStudentHelper();
//                            break;
//                        case 3:
//                            bookId = helper.getUserInput("Enter book Id : ");
//                            studentId = helper.getUserInput("Enter Student Id : ");
//                            res += bookId + "/" + studentId;
//                            break;
//                        case 4:
//                            bookId = helper.getUserInput("Enter book Id : ");
//                            studentId = helper.getUserInput("Enter Student Id : ");
//                            res += bookId + "/" + studentId;
//                            break;
//                        case 5:
//                            isAdmin = null;
//                            System.out.println("Successfully logged out");
//                            break;
//                    }
//                    printWriter.println(res);
//                    printWriter.flush();
//                    System.out.println("--------------------------------------------------------------");
//                    String msg = bufferedReader.readLine();
//                    System.out.println(msg);
//                    System.out.println("--------------------------------------------------------------");
//                }
//
//
//                bufferedReader.close();
//                inputStreamReader.close();
//                printWriter.close();
//                socket.close();
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.start();

    }
}
