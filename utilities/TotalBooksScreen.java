package utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TotalBooksScreen extends JFrame implements ActionListener {

    Socket socket;
    InputStreamReader inputStreamReader;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    JButton b1;
    DefaultListModel<String> l1,l2,l3;
    JLabel label;
    JList<String> list,list1,list2;
    private int id;

    public TotalBooksScreen(int id) {

        this.id = id;

        setBounds(400,150,500, 400);

        String data = "";

        try {

            socket = new Socket("localhost", 5000);

            printWriter = new PrintWriter(socket.getOutputStream());
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            printWriter.println("3/1");
            printWriter.flush();

            data = bufferedReader.readLine();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        label = new JLabel("LIST OF AVAILABLE BOOKS");
        label.setBounds(100, 30, 400, 20);
        add(label);

        l1 = new DefaultListModel<>();
        l1.addElement("Book Title");


        l2 = new DefaultListModel<>();
        l2.addElement("Author");


        l3 = new DefaultListModel<>();
        l3.addElement("Quantity");

        String[] strs = data.split(",");

        for(String q:strs) {
            String[] info = q.split("/");
            l1.addElement(info[0]);
            l2.addElement(info[1]);
            l3.addElement(info[2]);
        }

//        for(String x : data.split("$")) {
//            System.out.println("abagaagagag");
//            System.out.println(x);
//        }

        b1 = new JButton("Back");

        b1.addActionListener(this);

        list = new JList<>(l1);

        list1 = new JList<>(l2);

        list2 = new JList<>(l3);

        list.setBounds(60,50, 120,250);
        add(list);

        list1.setBounds(190,50, 100,250);
        add(list1);

        list2.setBounds(300,50, 70,250);
        add(list2);

        b1.setBounds(150, 310, 120, 30);
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
        TotalBooksScreen t1 = new TotalBooksScreen(1);
    }
}
