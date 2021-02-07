package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainScreen extends JFrame implements ActionListener {

    private JButton b1,b2,b3,b4;
    Helper helper = new Helper();

    public MainScreen() {

        setBounds(500,150,400,400);

        setLayout(new FlowLayout());

        b1 = new JButton("LOGIN AS ADMIN");
        b2 = new JButton("CREATE NEW ACCOUNT");
        b3 = new JButton("LOGIN AS STUDENT");
        b4 = new JButton("EXIT");
        add(b1);
        add(b2);
        add(b3);
        add(b4);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public void actionPerformed(ActionEvent ae) {
        int choice = -1;
        String res = "";

        if(ae.getSource() == b1) {
            setVisible(false);
            new LoginScreen(1);

        }
        else if(ae.getSource() == b2) {
            setVisible(false);
            new CreateScreen(1);
        }
        else if(ae.getSource() == b3) {
            setVisible(false);
            new LoginScreen(2);
        }
        else if(ae.getSource() == b4) {
            this.dispose();
        }

    }

    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
    }
}
