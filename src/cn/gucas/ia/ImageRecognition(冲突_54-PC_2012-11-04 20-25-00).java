package cn.gucas.ia;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRecognition {
	Robot robot;
	private static int X = 660;
	private static int Y = 462;
	private static int W = 100;
	private static int H = 105;
	private static String PATH = "G:/test.jpg";

	public ImageRecognition() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage cut() {
		Rectangle screenRect = new Rectangle(X, Y, W, H);
		return robot.createScreenCapture(screenRect);
	}

	public boolean match() {
		try {
			BufferedImage originImage = ImageIO.read(new File(PATH));
			int originRGB = originImage.getRGB(99, 104);
			// System.out.println("originRGB = " + originRGB);

			BufferedImage currentImage = cut();
			int currentRGB = currentImage.getRGB(99, 104);
			// System.out.println("currentRGB = " + currentRGB);

			if (originRGB == currentRGB) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void generate() {
		try {
			ImageIO.write(cut(), "jpg", new File(PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
