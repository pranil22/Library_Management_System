package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CreateScreen extends JFrame implements ActionListener {

    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    private int role;
    JLabel l1,l2,l3,l4,l5;
    JTextField t1,t3,t4;
    JPasswordField t2;
    JButton b1,b2;


    public CreateScreen(int role) {

        setBounds(500,150,300,400);

        this.role = role;
        setLayout(new FlowLayout());

        l1 = new JLabel("Username");
        t1 = new JTextField();
        t1.setColumns(20);

        l2 = new JLabel("Password");
        t2 = new JPasswordField();
        t2.setColumns(20);


        l3 = new JLabel("Firstname");
        t3 = new JTextField();
        t3.setColumns(20);

        l4 = new JLabel("Lastname");
        t4 = new JTextField();
        t4.setColumns(20);

        l5 = new JLabel("");

        if(role == 1) {
            b1 = new JButton("Create New Account");
        }
        else {
            b1 = new JButton("Add Student");
        }

        b2 = new JButton("Back");


        b1.addActionListener(this);
        b2.addActionListener(this);

        add(l1);
        add(t1);

        add(l2);
        add(t2);

        add(l3);
        add(t3);

        add(l4);
        add(t4);

        add(b1);
        add(b2);

        add(l5);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == b2) {
            setVisible(false);
            new AdminScreen();
        }
        else {

            String res = "";

            if(role == 1) {
                res = "0" + "/" + 2 + "/";

            }
            else {
                res = "1" + "/" + 2 + "/";
            }

            res += t1.getText() + "/" + t2.getText() + "/" + t3.getText() + "/" + t4.getText();

            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");

            System.out.println(res);


            try {

                socket = new Socket("localhost", 5000);

                printWriter = new PrintWriter(socket.getOutputStream());
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);

                printWriter.println(res);
                printWriter.flush();

                String[] strs = bufferedReader.readLine().split("/");

                String ans = strs[0];

                System.out.println(ans);

                if(role == 1) {
                    if(ans.equals("Y")) {
                        l5.setText("Succesfully Created Account");
                        System.out.println("Succesfully Created Account");
                        setVisible(false);
                        new LoginScreen(1);
                    }
                    else {
                        l5.setText("Invalid Credientials");
                    }
                }
                else {
                    l5.setText(ans);
                }


            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }



    }

    public static void main(String[] args) {
        CreateScreen createScreen = new CreateScreen(1);

    }
}
