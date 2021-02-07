package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BookTransferScreen extends JFrame implements ActionListener {
    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    private int type;

    private int role;
    JLabel l1, l2, l3, l4, l5, l6;
    JTextField t1, t2, t3, t4, t5, t6;
    JButton b1,b2;

    public BookTransferScreen(int type) {
        this.type = type;

        setBounds(500,150,300,400);


        setLayout(new FlowLayout());

        l1 = new JLabel("Book ID");
        t1 = new JTextField("");
        t1.setColumns(25);


        l2 = new JLabel("Student ID");
        t2 = new JTextField("");
        t2.setColumns(25);

        l3 = new JLabel("");
        if(type == 1) {
            b1 = new JButton("Issue Book");
        }
        else {
            b1 = new JButton("Return Book");
        }

        b2 = new JButton("Back");

        b1.addActionListener(this);
        b2.addActionListener(this);

        add(l1);
        add(t1);

        add(l2);
        add(t2);

        add(b1);
        add(b2);
        add(l3);

        setVisible(true);
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == b2) {
            setVisible(false);
            new AdminScreen();
        }
        else {

            String res = "";


            if(type == 1) {
                res = "1/3/" + t1.getText() + "/" + t2.getText();

            }
            else {
                res = "1/4/" + t1.getText() + "/" + t2.getText();
            }

            System.out.println(res);


            try {

                socket = new Socket("localhost", 5000);

                printWriter = new PrintWriter(socket.getOutputStream());
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);

                printWriter.println(res);
                printWriter.flush();


                String ans = bufferedReader.readLine();

                System.out.println(ans);

                l3.setText(ans);

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }



    }

    public static void main(String[] args) {
        BookTransferScreen bookTransferScreen = new BookTransferScreen(2);
    }


}
