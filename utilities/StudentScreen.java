package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentScreen extends JFrame implements ActionListener {

    JButton b1,b2,b3;
    private int id;

    public StudentScreen(int id) {
        this.id = id;
        setBounds(500,150,300, 300);
        setLayout(new FlowLayout());

        b1 = new JButton("ALL BOOKS");
        b2 = new JButton("MY ISSUED BOOKS");
        b3 = new JButton("LOGOUT");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        add(b1);
        add(b2);
        add(b3);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == b1) {
            setVisible(false);
            new TotalBooksScreen(id);
        }
        else if(ae.getSource() == b2) {
            setVisible(false);
            new IssuedBooksScreen(id);
        }
        else if(ae.getSource() == b3) {
            setVisible(false);
            new MainScreen();
        }
    }

    public static void main(String[] args) {
        StudentScreen s = new StudentScreen(1);
    }
}
