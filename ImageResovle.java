package com.djl;

import javax.swing.*;

public class ImageResovle {

    public static void main(String[] args) {
        String pathname = "f:/aa";
        MyPanel fileResovle = new MyPanel();
        Thread panelThread = new Thread();
        JFrame frame = new JFrame();
        frame.add(fileResovle);
        frame.setSize(800, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
        FileListener.listenFileDir(pathname, fileResovle);
        Thread.yield();

    }
}
