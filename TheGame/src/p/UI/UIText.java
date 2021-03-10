package p.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import p.Game.Handler;
import p.Grafics.Assets;
import p.Grafics.Text;
import p.States.State;
import p.Utils.Utils;

public class UIText extends UIObject {
	private String PATH_SETTINGS = "source/game/accounts/" + State.getUsername() + "/settings/settings.txt";
	private Handler handler;
	private ClickListener clicker;
	private String name, text, file;
	private Color color;
	private Font font;
	private String[] tokens;
	private int correction;
	private boolean clickable;

	public UIText(Handler handler, String name, String text, float x, float y, int width, int height, Font font,
			ClickListener clicker) {
		super(x, y, width, height);
		this.handler = handler;
		this.name = name;
		this.text = text;
		this.font = font;
		this.clicker = clicker;
		file = Utils.loadFileAsString(PATH_SETTINGS);
		tokens = file.split("\\s+");
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		if (!hovering)
			color = Color.black;
		else
			color = new Color(117, 58, 0);

		if (name.equals(State.getLoadGameTxtName()))
			color = new Color(117, 58, 0);
		if (name.equals("music") || name.equals("sound") || name.equals("difficulty")
				|| name.equals("usernameUnderscore") || name.equals("passwordUnderscore")
				|| name.equals("passwordUnderscoreConfirm"))
			color = Color.black;

		// first settings opening without changiing options
		if (State.getMusicOption().isEmpty() && name.equals("music " + tokens[1]))
			color = new Color(117, 58, 0);
		if (State.getVolumeOption().isEmpty() && name.equals("sound " + tokens[3]))
			color = new Color(117, 58, 0);

		if (name.equals("music " + State.getMusicOption()) || name.equals("sound " + State.getVolumeOption())
				|| name.equals(State.getDifficultyOption()) || name.equals(State.getInputName()))
			color = new Color(117, 58, 0);

		if (font == Assets.papyrus22)
			correction = 22;
		else if (font == Assets.papyrus28)
			correction = 28;
		else if (font == Assets.papyrus36)
			correction = 36;

		if (State.isLoadGameState() || State.getState().equals(handler.getGame().newGame)) {
			if (font == Assets.papyrus22)
				correction = 17;
			if (name.equals("next") || name.equals("previous") || name.equals("page"))
				correction = 22;
		}
		Text.drawString(g, text, (int) (x), (int) (y + correction), false, color, font);
	}

	@Override
	public void onClick() {
		if (name.equals("yes") || name.equals("no") || name.equals("ok") || name.equals("save")
				|| name.equals("cancel"))
			State.setFocusable(false);

		if (!State.getState().equals(handler.getGame().gameState))
			clickable = true;
		else
			clickable = false;

		if (State.isPlayerDead())
			clickable = true;

		if (clickable && !State.isFocusable()) {
			clicker.onClick();
		}
//		if (name.equals("saveGame")) {
//			try {
//				Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//				BufferedImage capture = new Robot().createScreenCapture(screenRect);
//				ImageIO.write(capture, "png", new File("asd.png"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (AWTException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
