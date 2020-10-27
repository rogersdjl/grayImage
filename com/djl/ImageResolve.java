package com.djl;

import javax.swing.*;

public class ImageResolve {

    public static void main(String[] args) throws Exception {
        String pathname = "File_path"; // Saved figures location
        // One should set the square position in advance.
        // The coordinates format should be leftup_X, Leftup_Y, Rightdown_X, Rightdown_Y
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathname, "cOordinates.txt"))));
		String s = br.readLine();
		String[] split = s.split(",");
		MyPanel fileResolve = new MyPanel(Integer.valueOf(split[0]),Integer.valueOf(split[1]),Integer.valueOf(split[2]),Integer.valueOf(split[3]));
        Thread panelThread = new Thread();
        JFrame frame = new JFrame();
        frame.add(fileResolve);
        frame.setSize(800, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
        FileListener.listenFileDir(pathname, fileResolve);
        Thread.yield();

    }
}
