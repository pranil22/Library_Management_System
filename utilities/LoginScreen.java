package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginScreen extends JFrame implements ActionListener {
    JTextField t1;
    JPasswordField t2;
    JButton b1;
    JLabel l1,l2,l3;

    int role;

    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;


    public LoginScreen(int role) {

        this.role = role;

        setBounds(500,150,300,400);

        setLayout(new FlowLayout());

        l1 = new JLabel("Username");
        t1 = new JTextField();
        t1.setColumns(20);
        l2 = new JLabel("Password");
        t2 = new JPasswordField();
        t2.setColumns(20);

        b1 = new JButton("Login");

        b1.addActionListener(this);

        l3 = new JLabel("");

        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(b1);
        add(l3);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String loginAdminOrStudent() {
        String username, password;
        username = t1.getText();
        password = t2.getText();

        String res = username + "/" + password;

        return res;
    }

    public void actionPerformed(ActionEvent ae) {
        String res = "0" + "/" ;
        if(role == 1) {
            res +=  1 +  "/";
        }
        else {
            res += 3 + "/";
        }

        res += loginAdminOrStudent();

        System.out.println(res);

        try {
            l3.setText("");

            socket = new Socket("localhost", 5000);

            printWriter = new PrintWriter(socket.getOutputStream());
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            printWriter.println(res);
            printWriter.flush();

            String[] strs = bufferedReader.readLine().split("/");

            String ans = strs[0];

            System.out.println(ans);

            if(ans.equals("Y")) {
                System.out.println("Admin logged in");
                setVisible(false);
                new AdminScreen();
            }
            else if(ans.equals("N")) {
                System.out.println("student logged in");
                setVisible(false);
                new StudentScreen(Integer.parseInt(strs[1]));
            }
            else {
                l3.setText("Invalid Credientials");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen(1);
    }
}
