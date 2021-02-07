package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AdminScreen extends JFrame implements ActionListener {

    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    JLabel l1,l2,l3,l4,l5;
    JTextField t1,t3,t4;
    JPasswordField t2;
    JButton b1,b2,b3,b4,b5;

    public AdminScreen() {

        setBounds(400,150,500,250);

        setLayout(new FlowLayout());

        b1 = new JButton("Add Book");
        b1.setSize(120, 40);

        b2 = new JButton("Add Student");
        b2.setSize(120, 40);

        b3 = new JButton("Issue Book");
        b3.setSize(120, 40);

        b4 = new JButton("Return Book");
        b4.setSize(120, 40);

        b5 = new JButton("Logout");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);

        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);

        setVisible(true);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == b1) {
            System.out.println("Add Book");
            setVisible(false);
            new AddBookScreen();
        }
        else if(ae.getSource() == b2) {
            setVisible(false);
            new CreateScreen(2);
        }
        else if(ae.getSource() == b3) {
            setVisible(false);
            System.out.println("Issued");
            new BookTransferScreen(1);
        }
        else if(ae.getSource() == b4) {
            setVisible(false);
            System.out.println("Returned");
            new BookTransferScreen(2);
        }
        else if(ae.getSource() == b5){
            setVisible(false);
            new MainScreen();
        }
    }

    public static void main(String[] args) {
        AdminScreen adminScreen = new AdminScreen();
    }
}
