import utilities.Book;
import utilities.Library;
import utilities.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;

public class ClientHandler extends Thread {
    private Library library = null;
    private Socket socket;
    private InputStreamReader inputStreamReader;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public ClientHandler(Socket socket, InputStreamReader inputStreamReader, PrintWriter printWriter, BufferedReader bufferedReader) {
        library = new Library();
        this.socket = socket;
        this.inputStreamReader = inputStreamReader;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run() {
        try {
            createTask(socket);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook() throws Exception {
        String title, author, publisher;

        int choice;
        boolean run = true;
        int id, cost, qty;

        String[] args = bufferedReader.readLine().split(" ");
        choice =  Integer.parseInt(args[0]) ;

        title = args[1];
        author = args[2];
        publisher = args[3];
        cost = Integer.parseInt(args[4]);
        qty = Integer.parseInt(args[5]);

        String res = library.addBook(title, author, publisher, cost, qty);

        printWriter.println(res);
    }

    public void selectAdminOperation(String[] args) {
        String title,author,publisher;
        String username, password, firstname, lastname;
        int id, cost, qty, bookId, studentId, operation = Integer.parseInt(args[1]);
        Book book;
        Student student;
        String res;

        switch (operation) {
            case 1:
                System.out.println("Adding new Book...");
                title = args[2];
                author = args[3];
                publisher = args[4];
                cost = Integer.parseInt(args[5]);
                qty = Integer.parseInt(args[6]);

                res = library.addBook(title, author, publisher, cost, qty);

                printWriter.println(res);
                printWriter.flush();

                break;
            case 2:
                System.out.println("Adding new student...");
                username = args[2];
                password = args[3];
                firstname = args[4];
                lastname = args[5];

                res = library.addStudent(username, password, firstname, lastname);
                printWriter.println(res);
                printWriter.flush();

                break;
            case 3:
                System.out.println("Issuing Book...");
                bookId = Integer.parseInt(args[2]);
                studentId = Integer.parseInt(args[3]);

                res = library.issueBook(bookId, studentId);

                printWriter.println(res);

                printWriter.flush();
                break;
            case 4:
                System.out.println("Returning book...");
                bookId = Integer.parseInt(args[2]);
                studentId = Integer.parseInt(args[3]);

                int fine = library.returnBook(bookId, studentId);
                if(fine == -1) {
                    printWriter.println("Book not issued by student with id" + studentId);
                }
                else {
                    printWriter.println("FINE " + fine);
                    System.out.println("Book returned");
                }

                printWriter.flush();
                break;

            case 5:
                printWriter.println("Successfully logged out..");

                printWriter.flush();
                break;
        }
    }

    public void selectStudentOperaion(String[] args) throws Exception {
        int operation = Integer.parseInt(args[1]);
        String msg = "";
        switch (operation) {
            case 1:
                System.out.println("Returning all the books");

                ResultSet rs = library.getBooks();

                if(rs == null) {
                    msg = "Not found";
                }
                else {
                    while (rs.next()) {
                        msg += rs.getString(2) + "/" + rs.getString(3) + "/" + rs.getInt(6) + ",";
                    }
                    printWriter.println(msg);
                }

                printWriter.flush();
                break;
            case 2:
                int id = Integer.parseInt(args[2]);
                System.out.println("Returning issued books...");
                ResultSet rs1 = library.getIssuedBooksById(id);

                if(rs1 == null) {
                    msg = "Not found";
                }
                else {
                    while (rs1.next()) {
                        String returnDate = String.format("%tA, %<tB %<td", rs1.getDate(2));
                        msg += rs1.getString(1) + "/" + returnDate + "@";

                    }
                }

                printWriter.println(msg);
                printWriter.flush();
                break;
            case 3:
                System.out.println("Logging out...");
                library.logoutStudent();
                printWriter.println("Logged Out successfully");
                printWriter.flush();
                break;
        }
    }

    public void createTask(Socket socket) throws Exception {
        int choice, operation;
        String response, username, password, firstname, lastname;

        String data = bufferedReader.readLine();
        String[] args = data.split("/");
        choice = Integer.parseInt(args[0]);


        if(choice == 0) {
            operation = Integer.parseInt(args[1]);
            response  = "";
            switch (operation) {
                case 1:
                    System.out.println("Admin Login...");
                    username = args[2];
                    password = args[3];
                    if(library.loginAdmin(username, password)) {
                        response = "Y";
                    }
                    else {
                        response = "ND";
                    }
                    break;
                case 2:
                    System.out.println("Admin Signup...");
                    username = args[2];
                    password = args[3];
                    firstname = args[4];
                    lastname = args[5];
                    if(library.registerAdmin(username, password, firstname, lastname)){
                        response = "Y";
                    }
                    else {
                        response = "ND";
                    }

                    break;
                case 3:
                    System.out.println("Student Login...");
                    username = args[2];
                    password = args[3];
                    int token = library.loginStudent(username, password);
                    if(token != -1) {
                        response = "N/" + token;
                    }
                    else {
                        response = "ND";
                    }
                    break;

                case 4:
                    System.out.println("Closing connection with client " + socket);
                    printWriter.close();
                    socket.close();

            }
            if(!(operation == 4)) {
                printWriter.println(response);
                printWriter.flush();
            }
        }
        else if(choice == 1 || choice == 3) {
            if(choice == 1) {
                selectAdminOperation(args);
            }
            else {
                selectStudentOperaion(args);
            }
        }
    }

}
