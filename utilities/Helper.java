package utilities;

import java.io.Serializable;
import java.util.Scanner;

public class Helper implements Serializable {

    public String getUserInput(String msg) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print(msg);

        String str = myObj.nextLine();
        return str;
    }

    public String encrypt(int bookId, int studentId) {
        String id = bookId + ":" + studentId;
        return id;
    }
}
