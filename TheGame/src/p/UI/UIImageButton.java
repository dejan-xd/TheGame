package p.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import p.Game.Handler;
import p.States.State;
import p.Utils.Utils;

public class UIImageButton extends UIObject {

	private BufferedImage[] images;
	private String buttonName;
	private ClickListener clicker;
	private Handler handler;
	private String path = "source/game/accounts/" + State.getUsername() + "/settings/settings.txt";
	private String file;
	private String[] tokens;
	private Image img;

	public UIImageButton(Handler handler, String buttonName, float x, float y, int width, int height,
			BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.buttonName = buttonName;
		this.images = images;
		this.clicker = clicker;
		this.handler = handler;
		file = Utils.loadFileAsString(path);
		tokens = file.split("\\s+");
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
//		if (!hovering)
//			g.drawImage(images[0], (int) (x), (int) (y), width, height, null);
//		else
//			g.drawImage(images[1], (int) (x), (int) (y), width, height, null);
//
//		// first settings opening without changiing options
//		if (State.getMusicOption().isEmpty() && buttonName.equals("music " + tokens[1]))
//			g.drawImage(images[1], (int) (x), (int) (y), width, height, null);
//		if (State.getVolumeOption().isEmpty() && buttonName.equals("sound " + tokens[3]))
//			g.drawImage(images[1], (int) (x), (int) (y), width, height, null);
//
//		if (buttonName.equals("music " + State.getMusicOption())
//				|| buttonName.equals("sound " + State.getVolumeOption())
//				|| buttonName.equals(State.getDifficultyOption()))
//			g.drawImage(images[1], (int) (x), (int) (y), width, height, null);

		if (!hovering)
			img = images[0];
		else
			img = images[1];

		// first settings opening without changiing options
		if (State.getMusicOption().isEmpty() && buttonName.equals("music " + tokens[1]))
			img = images[1];
		if (State.getVolumeOption().isEmpty() && buttonName.equals("sound " + tokens[3]))
			img = images[1];

		if (buttonName.equals("music " + State.getMusicOption())
				|| buttonName.equals("sound " + State.getVolumeOption())
				|| buttonName.equals(State.getDifficultyOption()))
			img = images[1];

		g.drawImage(img, (int) (x), (int) (y), width, height, null);
	}

	@Override
	public void onClick() {
		if (buttonName.equals("yes") || buttonName.equals("no") || buttonName.equals("ok"))
			State.setFocusable(false);
		if (!State.getState().equals(handler.getGame().gameState) && !State.isFocusable()) {
			clicker.onClick();
		}
	}
}
