package com.djl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MyPanel extends JPanel implements FileResolve {
	
	
	MyPanel(int x1, int y1, int x2, int y2){
		POINT_1 = new Point(x1, y1);
		POINT_2 = new Point(x2, y2);
	}
	
	private Point POINT_1; //leftup
	private Point POINT_2; //rightdown

    private static volatile int TIME_UNIT = 1;

    private static long start =0;

    private static volatile int DEFAULT_MAX = 600;
    private static volatile int X_OFFSET = 50;
    private static volatile int Y_OFFSET = 610;
    private static volatile int X_DRAW_STRING_OFFSET = -20;
    private static volatile int Y_DRAW_STRING_OFFSET = 20;

    public static java.util.List<Point> pointList = new ArrayList<Point>();


    @Override
    public void resolve(FileInputStream file) throws IOException {
        BufferedImage read = ImageIO.read(file);
        Point point = getTimeGrayPoint(read , POINT_1.getX(), POINT_1.getY(), POINT_2.getX(), POINT_2.getY());

        synchronized (pointList) {
            pointList.add(point);

        }
        this.repaint();
    }

    private static Point getTimeGrayPoint(BufferedImage image, int x1, int y1, int x2, int y2) throws IOException {
        int sum = 0;
        long now = System.currentTimeMillis();
        if (start == 0) {
            start = now;
        }
        BigDecimal graySum = BigDecimal.ZERO;
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                Color c = new Color(image.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                sum++;
                double gray = (red * 0.299 + green * 0.587 + blue * 0.114);
                double x = gray / 255.0;
                graySum = graySum.add(new BigDecimal(x));
                red = green = blue = (int) gray;
                int rgb1 = (255 << 24) | (red << 16) | (green << 8) | blue;
            }
        }
        int originX = (int) ((now - start) / 100);
        for (int i = 1; i < 100; i++) {
            int max = DEFAULT_MAX * i;
            if (originX < max) {
                TIME_UNIT = i;
                break;
            }
        }
        int y = graySum.divide(new BigDecimal(sum), 20, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(600)).intValue();
        Point point = new Point();
        point.setX(originX);
        point.setY(600 - y);
        return point;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        int x1 = 0 + X_OFFSET;
        int y1 = 0 + Y_OFFSET;
        graphics.drawLine(x1, y1, x1, 0);
        graphics.drawLine(x1, y1, 800, y1);
        for (int i = 1; i <= 10; i++) {
            graphics.drawLine(x1 + i * 60, y1 + 5, x1 + i * 60, y1 - 5);
            graphics.drawString(6 * i * TIME_UNIT+"s",  x1 + i * 60, y1 + 15);
            graphics.drawLine(x1 + 5, y1 - i * 60, x1 -5, y1 - i * 60);
            graphics.drawString(10 * i +"%",  x1 -35, y1 - i * 60);
        }

        graphics.drawString("(0,0)", x1 + X_DRAW_STRING_OFFSET, y1 + Y_DRAW_STRING_OFFSET);
        graphics.drawString("time(s), now is " + TIME_UNIT + " min(s)", 100, 650);
        graphics.drawString("intensity(%)", 10, 600);

        if (pointList.size() == 0) {
            return;
        }
        synchronized (pointList) {
            for (Point point : pointList) {
                point.changeUnit(TIME_UNIT);
            }
            for (int i = 0; i < pointList.size(); i++) {
                Point point = pointList.get(i);
                int x = point.getX() + X_OFFSET;
                graphics.drawOval(x, point.getY(), 3, 3);
                if (i == pointList.size() - 1 ) {
                    String x_str = new BigDecimal(point.getX() * TIME_UNIT).divide(new BigDecimal("10"), 2 ,BigDecimal.ROUND_HALF_UP).toString();
                    graphics.drawString(String.format("(%s,%s)", x_str +"s", (600 - point.getY()) / 6+"%") , x - 20, point.getY() + 20);
					//recording
					try{
						File f = new File("intensityLog_path"); // 
						if(!f.exists()){
							f.createNewFile();
						}
						FileOutputStream fileOutputStream = new FileOutputStream(f, true);
						String format = String.format("%s\t%s\n", x_str, new BigDecimal((600 - point.getY())).divide(new BigDecimal("6"), 4, BigDecimal.ROUND_HALF_UP) + "%");
						System.out.println(format);
						fileOutputStream.write(format.getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
					} catch(Exception e){
						e.printStackTrace();
					}
                }
            }
            for (int i = 1; i < pointList.size(); i++) {
                Point point = pointList.get(i);
                Point point1 = pointList.get(i - 1);
                int x = point.getX() + X_OFFSET;
                Integer x2 = point1.getX() + X_OFFSET;
                graphics.drawLine(x, point.getY(), x2, point1.getY());
            }
        }


    }




}
