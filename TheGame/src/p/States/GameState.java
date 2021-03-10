package p.States;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import p.Game.Handler;
import p.Grafics.Assets;
import p.Grafics.ImageLoader;
import p.Grafics.Text;
import p.UI.ClickListener;
import p.UI.UIImage;
import p.UI.UIManager;
import p.UI.UIText;
import p.Worlds.World;

public class GameState extends State {
	private String WORLD_PATH = "source/game/worlds/world.txt",
			//ACTION_BAR_IMAGE = "/images/background_images/action_bar.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png";
	private String message = "";
	private World world;
	private BufferedImage scroll;
	private UIManager uiManager;

	public GameState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		world = new World(handler, WORLD_PATH);
		handler.setWorld(world);
		//image = ImageLoader.loadImage(ACTION_BAR_IMAGE);
		uiManager = new UIManager(handler);
		scroll = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_SCROLL);

		uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

		uiManager
				.addObject(new UIText(handler, "main_menu", "Back to Main Menu", handler.getGame().getWidth() / 2 - 170,
						handler.getGame().getHeight() / 2 + 140, 340, 41, Assets.papyrus36, new ClickListener() {
							@Override
							public void onClick() {
								message = "";
								State.setRepaint(true);
								State.setState(handler.getGame().menuState);
							}
						}));
	}

	@Override
	public void tick() {
		if (State.isRepaint()) {
			State.setRepaint(false);
			init();
		}

		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)
				&& !handler.getWorld().getEntityManager().getPlayer().isDead()) {
			State.setRepaint(true);
			State.setResumeGameState(this);
			handler.getGame().pause();
			State.setGameState(false);
			State.setSaved(false);
			State.getMusic().resumeBackgroundSound();
			State.setState(handler.getGame().pauseState);
		}
		world.tick();
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		if (handler.getWorld().getEntityManager().getPlayer().isDead()) {
			uiManager.render(g);
			message = "dead";
		}

		int lineHeight = g.getFontMetrics().getHeight() / 2 + 10;
		if (message.equals("dead")) {
			String text = "Game over!\nYou died!";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		}

//		g.drawImage(image, (handler.getWidth() - image.getWidth()) / 2, handler.getHeight() - image.getHeight(),
//				image.getWidth(), image.getHeight(), null);
	}

}
