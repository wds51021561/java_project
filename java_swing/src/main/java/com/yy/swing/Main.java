package com.yy.swing;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    
    private static void createAndShowGUI(){
//        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame helloWorldSwing = new JFrame("helloWorldSwing");
        helloWorldSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("hello world");
        helloWorldSwing.getContentPane().add(label);
        helloWorldSwing.pack();
        helloWorldSwing.setVisible(true);
    }
}
