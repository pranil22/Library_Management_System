package utilities;


import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Student implements Serializable {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private HashMap<Integer, Calendar> issueBooks = new HashMap<Integer, Calendar>();


    public Student(int id, String username, String password, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public boolean hasBook(int id) {
        Iterator<Integer> ids = getBookIds().iterator();
        while (ids.hasNext()) {
            if(ids.next() == id) {
                return true;
            }
        }
        return false;
    }

    public void addBook(int bookId, Calendar date) {
        issueBooks.put(bookId, date);
    }

    public void removeBook(int bookId) {
        issueBooks.remove(bookId);
    }

    public Calendar getIssueDate(int bookId) {
        return issueBooks.get(bookId);
    }

    public Calendar getReturnDate(int bookId) {
        Calendar returnDate = Calendar.getInstance();

        returnDate.setTime(getIssueDate(bookId).getTime());
        returnDate.add(Calendar.DATE, 7);

        return returnDate;
    }

    public Set<Integer> getBookIds() {
        return issueBooks.keySet();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public HashMap<Integer, Calendar> getIssuedBooks() {
        return this.issueBooks;
    }


}
