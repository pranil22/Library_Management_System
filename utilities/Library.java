package utilities;

import java.io.Serializable;
import java.util.*;
import java.sql.*;


public class Library implements Serializable {
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Admin> admins = new ArrayList<Admin>();
    private HashSet<Integer> forbiddenBookIds = new HashSet<Integer>();
    private HashSet<Integer> forbiddenAdminIds = new HashSet<Integer>();
    private HashSet<Integer> forbiddenStudentIds = new HashSet<Integer>();
    private HashMap<String, Calendar> issuedate = new HashMap<String, Calendar>();
    private Admin currentAdmin;
    private Student currentStudent;
    private final int FINE_PER_DAY = 10;
    private final long MILISEONDS_IN_ONE_DAY = 24 * 60 * 60 * 1000;
    private int currentStudentId;


    public boolean registerAdmin(String username, String password, String firstname, String lastname) {
        Conn conn = new Conn();

        int row;
        boolean res = false;
        String sql = "CREATE TABLE IF NOT EXISTS admins(" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(20) NOT NULL," +
                    "password VARCHAR(20) NOT NULL," +
                    "firstname VARCHAR(20) NOT NULL," +
                    "lastname VARCHAR(20) NOT NULL," +
                    "PRIMARY KEY(id)" +
                    ")";
        try {
            Statement statement = conn.getConnection().createStatement();
            statement.executeUpdate(sql);

            String sql1 = "SELECT * FROM admins WHERE username = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(sql1);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                res = false ;
            }
            else {

                String sql2 = "INSERT INTO admins(username, password, firstname, lastname) VALUES(?,?,?,?)";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql2);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, firstname);
                preparedStatement.setString(4, lastname);


                row = preparedStatement.executeUpdate();

                if(row > 0) {
                    res = true;
                }
                else {
                    res = false;
                }

            }

        }
        catch (Exception e) {
            res = false;
            e.printStackTrace();
        }

        return res;
    }

    public boolean loginAdmin(String username, String password) {
        boolean success = false;
        try {
            Conn conn = new Conn();
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement("SELECT * FROM admins WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                success = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public void logoutAdmin() {
        currentAdmin = null;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public int loginStudent(String username, String password) {
        int token = -1;
        try {
            Conn conn = new Conn();
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement("SELECT * FROM students WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                currentStudentId = rs.getInt(1);
                System.out.println(currentStudentId);
                token = currentStudentId;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    public void logoutStudent() {
        currentStudentId = -1;
    }


    public String addBook(String title, String author, String publisher, int cost, int qty) {
        Conn conn = new Conn();
        String res = "";

        String sql = "CREATE TABLE IF NOT EXISTS books(" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "title VARCHAR(20) NOT NULL," +
                "author VARCHAR(20) NOT NULL," +
                "publisher VARCHAR(20) NOT NULL," +
                "cost INT NOT NULL," +
                "qty INT NOT NULL," +
                "PRIMARY KEY(id)" +
                ")";

        try {
            Statement st = conn.getStatement();
            st.executeUpdate(sql);

            PreparedStatement ps = conn.getConnection().prepareStatement("SELECT * FROM books WHERE title = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                res = "Book is alreday added";
            }
            else {
                ps = conn.getConnection().prepareStatement("INSERT INTO books(title, author, publisher, cost, qty) VALUES(?,?,?,?,?)");
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, publisher);
                ps.setInt(4, cost);
                ps.setInt(5, qty);

                int rows = ps.executeUpdate();
                if(rows > 0) {
                    res = "Added book successfully";
                }
                else {
                    res = "Not added";
                }
            }

        }
        catch (Exception e) {
            res = "Something went wrong";
            e.printStackTrace();
        }
        return res;
    }




    public String addStudent(String username, String password, String firstname, String lastname) {
        Conn conn = new Conn();

        int row;
        String res = "";
        String sql = "CREATE TABLE IF NOT EXISTS students(" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "username VARCHAR(20) NOT NULL," +
                "password VARCHAR(20) NOT NULL," +
                "firstname VARCHAR(20) NOT NULL," +
                "lastname VARCHAR(20) NOT NULL," +
                "PRIMARY KEY(id)" +
                ")";
        try {
            Statement statement = conn.getConnection().createStatement();
            statement.executeUpdate(sql);

            String sql1 = "SELECT * FROM students WHERE username = ?";
            PreparedStatement ps = conn.getConnection().prepareStatement(sql1);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                res = "Duplicate username found";
            }
            else {

                String sql2 = "INSERT INTO students(username, password, firstname, lastname) VALUES(?,?,?,?)";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql2);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, firstname);
                preparedStatement.setString(4, lastname);


                row = preparedStatement.executeUpdate();

                if(row > 0) {
                    res = "Successfully added";
                }
                else {
                    res = "Something went wrong";
                }

            }

        }
        catch (Exception e) {
            res = "Something went wrong";
            e.printStackTrace();
        }

        return res;
    }

    public String issueBook(int bookId, int studentId) {
        String res = "";
        try {

            Conn conn = new Conn();

            Statement statement = conn.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM issuedBooks WHERE bookId=" + bookId + " AND studentId=" + studentId);
            if(rs.next()) {
                return "Book already issued to you";
            }


            PreparedStatement preparedStatement1 = conn.getConnection().prepareStatement("SELECT qty FROM books WHERE id = " + bookId);

            ResultSet rs1 = preparedStatement1.executeQuery();
            rs1.next();
            if(rs1.getInt(1) == 0) {
                return "Book not available";
            }

            String sql1 = "INSERT INTO issuedBooks VALUES(?,?,CURDATE())";

            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql1);
            preparedStatement.setInt(1,bookId);
            preparedStatement.setInt(2, studentId);

            preparedStatement.executeUpdate();

            sql1 = "UPDATE books SET qty = qty - 1 WHERE id = " + bookId;

            PreparedStatement preparedStatement2 = conn.getConnection().prepareStatement(sql1);
            preparedStatement2.executeUpdate();

            res = "Book issued to student with id "  + studentId;

        }
        catch(Exception e) {
            res = "Something went wrong";
            e.printStackTrace();
        }

        return res;
    }

    public int returnBook(int bookId, int studentId) {
        int fine = 0;

        try {
            Conn conn = new Conn();
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement("SELECT * FROM issuedBooks WHERE bookId = "+ bookId + " AND studentId=" + studentId);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){

                String sql = "SELECT DATEDIFF(CURDATE(),date) as days FROM issuedbooks WHERE bookId=" + bookId + " AND studentId=" + studentId;
                Statement statement = conn.getConnection().createStatement();
                ResultSet rs1 = statement.executeQuery(sql);
                rs1.next();
                int days = rs1.getInt(1);
                if(rs1.getInt(1) > 7) {
                    fine = FINE_PER_DAY * (days - 7);
                }
                statement.executeUpdate("DELETE FROM issuedBooks WHERE bookId="+ bookId + " AND studentId="+studentId);

            }
            else {
                return -1;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return fine;

    }

    public ResultSet getBooks() {
        ResultSet rs=null;
        try {
            Conn conn = new Conn();
            Statement statement = conn.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM books");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getIssuedBooksById(int id) throws Exception {
        ResultSet rs = null;
        System.out.println(currentStudentId);
        Conn conn = new Conn();
        Statement statement = conn.getConnection().createStatement();
        String sql = "SELECT b.title ,DATE_ADD(i.date, INTERVAL 7 DAY) as return_date FROM issuedBooks i " +
                    "JOIN books b " +
                    " ON b.id = i.bookId"+
                    " WHERE i.studentId = " + id;

        System.out.println(sql);
        rs = statement.executeQuery(sql);

        return rs;
    }

    public void displayBooks() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.format("%10s %30s %25s %30s %10s %10s\n", "ID", "TITLE", "AUTHOR", "PUBLISHER", "COST", "QUANTITY");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        for(Book book: books) {
            System.out.format("%10s %30s %25s %30s %10s %10s\n", book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(),book.getCost(),book.getQty());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }


    public Book getBookById(int id) {
        for(Book book1:books) {
            if(book1.getId() == id) {
                return book1;
            }
        }
        return null;
    }

    public Student getStudentById(int id) {
        for (Student student:students) {
            if(student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public ArrayList<Book> getIssuedBooks(int studentId) {
        ArrayList<Book> books = new ArrayList<Book>();

        Student student = getStudentById(studentId);

        if(student != null) {
            Set<Integer> keys = student.getBookIds();
            Iterator<Integer> iterator = keys.iterator();

            while (iterator.hasNext()) {
                Book book = getBookById(iterator.next());
                books.add(book);
            }
        }

        return books;
    }
}
