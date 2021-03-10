package p.Windows;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;

	private String title;
	private int width, height;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		createDisplay();
	}

	private void createDisplay() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(100, 100));
		canvas.setMaximumSize(new Dimension(100, 100));
		canvas.setMinimumSize(new Dimension(100, 100));
		canvas.setFocusable(false);

		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		

//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Image image = toolkit.getImage(getClass().getResource("/a.png"));
//		Cursor c = toolkit.createCustomCursor(image, new Point(frame.getX(), frame.getY()), "img");
//		canvas.setCursor(c);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
}
