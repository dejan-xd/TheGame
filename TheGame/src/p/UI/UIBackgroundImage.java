package p.UI;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import p.Game.Handler;

public class UIBackgroundImage extends UIObject {

	private ImageIcon image;

	public UIBackgroundImage(Handler handler, float x, float y, int width, int height, ImageIcon image) {
		super(x, y, width, height);
		this.image = image;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		image.paintIcon(null, g, width, height);
	}

	@Override
	public void onClick() {
		
	}

}
