package p.UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import p.Game.Handler;

public class UIImage extends UIObject {

	private BufferedImage image;
	private Dimension screenSize;
	private String name;

	public UIImage(Handler handler, float x, float y, int width, int height, BufferedImage image, String name) {
		super(x, y, width, height);
		this.image = image;
		this.name = name;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		if (name.equals("flickering"))
			g.drawImage(image, 0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight(), null);
		if (name.equals("scroll"))
			g.drawImage(image, (int) screenSize.getWidth() / 2 - 350, (int) screenSize.getHeight() / 2 - 200, 700, 500,
					null);
	}

	@Override
	public void onClick() {
	}

}
