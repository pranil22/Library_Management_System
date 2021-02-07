package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AddBookScreen extends JFrame implements ActionListener {
    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    private int role;
    JLabel l1, l2, l3, l4, l5, l6;
    JTextField t1, t2, t3, t4, t5, t6;
    JButton b1,b2;

    public AddBookScreen() {

        setBounds(500,150, 300,400);

        setLayout(new FlowLayout());

        l1 = new JLabel("Title");
        t1 = new JTextField("");
        t1.setColumns(25);

        l2 = new JLabel("Author");
        t2 = new JTextField("");
        t2.setColumns(25);

        l3 = new JLabel("Publisher");
        t3 = new JTextField("");
        t3.setColumns(25);

        l4 = new JLabel("Cost");
        t4 = new JTextField("");
        t4.setColumns(25);

        l5 = new JLabel("Quantity");
        t5 = new JTextField("");
        t5.setColumns(25);

        l6 = new JLabel("");

        b1 = new JButton("Add Book");
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

        add(l5);
        add(t5);

        add(b1);
        add(b2);

        add(l6);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == b2) {
            setVisible(false);
            new AdminScreen();
        }
        else {
            String res = "1/1/" + t1.getText() + "/" + t2.getText() + "/" + t3.getText() + "/" + t4.getText() + "/" + t5.getText();

            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");

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

                l6.setText("ajajjajaj");
                l6.setText(ans);


            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public static void main(String[] args) {
        AddBookScreen addBookScreen = new AddBookScreen();
    }
}
