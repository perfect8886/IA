package cn.gucas.ia.tmall;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class KeyPress {
	JFrame frame;
	Container container;
	JButton button;

	Robot robot4Key;
	Robot robot4Mouse;

	private static String START = "start";
	private static String STOP = "stop";

	public Thread keyPress;
	public boolean on;

	ImageRecognition recognition = new ImageRecognition();

	public KeyPress() {
		frame = new JFrame();
		frame.setTitle("Space Killer");
		frame.setSize(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		container = frame.getContentPane();

		button = new JButton();
		button.setText(START);

		container.add(button);

		try {
			robot4Key = new Robot();
			robot4Mouse = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		on = false;
		keyPress = new Thread() {
			public void run() {
				while (true) {
					if (on) {
						robot4Key.keyPress(KeyEvent.VK_SPACE);
						robot4Key.keyRelease(KeyEvent.VK_SPACE);
						try {
							Thread.sleep(30);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch bl ock
							e1.printStackTrace();
						}
					}
				}
			}
		};
		keyPress.start();
	}

	public void run() {
		showButton();
		// moveMouse();
	}

	private void showButton() {
		MouseListener listener = new SpacePressListener();

		button.addMouseListener(listener);

		frame.setVisible(true);
	}

	private void moveMouse() {
		try {
			while (true) {
				if (on) {
					if (!recognition.match()) {
						// System.out.println("match!");
						robot4Mouse.mouseMove(643, 600);
						// robot4Mouse.setAutoDelay(0);
						robot4Mouse.mousePress(InputEvent.BUTTON1_MASK);
						robot4Mouse.mouseRelease(InputEvent.BUTTON1_MASK);
						Thread.sleep(1000);
						robot4Mouse.mouseMove(710, 540);
						// robot4Mouse.setAutoDelay(0);
						robot4Mouse.mousePress(InputEvent.BUTTON1_MASK);
						robot4Mouse.mouseRelease(InputEvent.BUTTON1_MASK);
						Thread.sleep(1000);
					} else {
						robot4Mouse.mouseMove(710, 610);
						// robot4Mouse.setAutoDelay(0);
						robot4Mouse.mousePress(InputEvent.BUTTON1_MASK);
						robot4Mouse.mouseRelease(InputEvent.BUTTON1_MASK);
						Thread.sleep(1000);
						robot4Mouse.mouseMove(710, 540);
						robot4Mouse.mousePress(InputEvent.BUTTON1_MASK);
						robot4Mouse.mouseRelease(InputEvent.BUTTON1_MASK);
						Thread.sleep(1000);
					}
					robot4Mouse.mouseMove(710, 690);
					Thread.sleep(17000);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class SpacePressListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// System.out.println(e.getX());
			// System.out.println(e.getY());
			if (button.getText().equals(START)) {
				button.setText(STOP);
				button.repaint();

				on = true;
				recognition.generate();
			} else if (button.getText().equals(STOP)) {
				button.setText(START);
				button.repaint();

				on = false;
			} else {

			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String[] args) {
		KeyPress press = new KeyPress();
		press.run();
	}
}
