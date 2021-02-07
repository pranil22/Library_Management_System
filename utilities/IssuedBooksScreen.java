package utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IssuedBooksScreen extends JFrame implements ActionListener {
    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    DefaultListModel<String> l1,l2,l3;
    JLabel label;
    JList<String> list,list1,list2;

    JButton b1;

    int id;

    public IssuedBooksScreen(int id) {

        this.id = id;
        setBounds(400,150,500, 400);
        String data = "";

        try {
            socket = new Socket("localhost", 5000);

            printWriter = new PrintWriter(socket.getOutputStream());
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            printWriter.println("3/2/" + id);
            printWriter.flush();

            data = bufferedReader.readLine();

            System.out.println(data);

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        label = new JLabel("ISSUED BOOKS");
        label.setBounds(100, 30, 400, 20);
        add(label);

        l1 = new DefaultListModel<>();
        l1.addElement("Book Title");


        l2 = new DefaultListModel<>();
        l2.addElement("Return Date");


        String[] strs = data.split("@");

        for(String str:strs) {
            String[] info = str.split("/");
            l1.addElement(info[0]);
            l2.addElement(info[1]);
        }

        list1 = new JList<>(l1);
        list2 = new JList<>(l2);

        list1.setBounds(100,50, 100,250);
        add(list1);

        list2.setBounds(210,50, 150,250);
        add(list2);


        b1 = new JButton("Back");
        b1.addActionListener(this);
        b1.setBounds(180, 310, 90, 40);


        add(b1);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new StudentScreen(id);
    }

    public static void main(String[] args) {
        IssuedBooksScreen i = new IssuedBooksScreen(1);
    }
}
