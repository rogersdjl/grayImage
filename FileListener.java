package com.djl;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileListener {


    private static Set<String> fileNameSet = new HashSet<>();

    private static List<String> picFileExts = new ArrayList<>();

    static {

        picFileExts.add("JPG");
        picFileExts.add("jpg");
        picFileExts.add("jpeg");
    }

    public static void listenFileDir(String dir, FileResolve fileResolve) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    do {
                        File file = new File(dir);
                        File[] files = file.listFiles();
                        for (File file1 : files) {
                            String name = file1.getName();
                            boolean canRead =false;
                            for (String picFileExt : picFileExts) {
                                if (name.toLowerCase().contains(picFileExt)) {
                                    canRead =true;
                                }
                            }
                            if (canRead) {
                                if (!fileNameSet.contains(name)) {
                                    try {
                                        FileInputStream fileInputStream = new FileInputStream(file1);
                                        fileResolve.resolve(fileInputStream);
                                        fileInputStream.close();
                                    } catch (Exception e) {
                                        System.out.println(file1.getName());
                                        e.printStackTrace();
                                    }
                                    fileNameSet.add(name);
                                }
                            }

                        }

                    }while (true);

                }
            }).start();


    }

}
