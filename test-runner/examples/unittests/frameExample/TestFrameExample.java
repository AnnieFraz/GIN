package frameExample;

import java.awt.FlowLayout;

import javax.swing.*;

public class TestFrameExample {

    public static void main(String s[]) {

        JFrame frame = new JFrame("JFrame Example");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("This is a label!");

        JButton button = new JButton();
        button.setText("Press me");

        panel.add(label);
        panel.add(button);

        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
