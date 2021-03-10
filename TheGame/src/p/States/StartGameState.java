package p.States;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import p.Audio.Music;
import p.Game.Handler;
import p.Grafics.Assets;
import p.Grafics.ImageLoader;
import p.Grafics.Text;
import p.UI.ClickListener;
import p.UI.UIImage;
import p.UI.UIBackgroundImage;
import p.UI.UIManager;
import p.UI.UIText;
import p.Utils.Utils;

public class StartGameState extends State {
	private String FLICKERING = "flickering", message = "";
	private String PATH_CLICK_AUDIO = "source/audio/click.wav";
	private String BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png",
			BACKGROUND_IMAGE_PATH_SWITCH = "/images/background_images/switch.gif",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif";
	private URL loop_url, switch_url, switch_back_url;
	private Music clickSound;
	private ImageIcon loop_icon, switch_icon, switch_back_icon;
	private BufferedImage flickering, scroll;
	private UIManager uiManager;
	private Dimension screenSize;

	public StartGameState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		uiManager = new UIManager(handler);
		clickSound = State.getMusic();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		flickering = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_FLICKERING);
		scroll = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_SCROLL);

		loop_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOOP);
		loop_icon = new ImageIcon(loop_url);
		loop_icon.setImage(loop_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		switch_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_SWITCH);
		switch_icon = new ImageIcon(switch_url);
		switch_icon.setImage(switch_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		switch_back_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_SWITCH_BACK);
		switch_back_icon = new ImageIcon(switch_back_url);
		switch_back_icon.setImage(switch_back_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, flickering, FLICKERING));
		uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, loop_icon));

		uiManager.addObject(new UIText(handler, "newGame", "New Game", handler.getGame().getWidth() / 2 - 73,
				handler.getGame().getHeight() / 2 + 20, 146, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setCounter(1);
						State.setDifficultyOption("");
						if (Utils.findFilesInDir(
								"source/game/accounts/" + State.getUsername() + "/load game").length != 0) {
							State.setFocusable(true);

							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));
							message = "new game";

							uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
									handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											File dir = new File(
													"source/game/accounts/" + State.getUsername() + "/load game");
											for (File file : dir.listFiles())
												if (!file.isDirectory())
													file.delete();
											uiManager
													.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
											try {
												Thread.sleep((long) (1000));
											} catch (InterruptedException e) {
											}
											State.setRepaint(true);
											State.setState(handler.getGame().newGame);
										}
									}));

							uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											State.setRepaint(true);
											State.setState(handler.getGame().startGameState);
										}
									}));
						} else {
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
							try {
								Thread.sleep((long) (1000));
							} catch (InterruptedException e) {
							}
							State.setRepaint(true);
							State.setState(handler.getGame().newGameState);
						}
					}
				}));

		if (!State.getUsername().equals("new game")) {
			if (Utils.findFilesInDir("source/game/accounts/" + State.getUsername() + "/load game").length != 0) {
				uiManager.addObject(new UIText(handler, "loadGame", "Load Game", handler.getGame().getWidth() / 2 - 78,
						handler.getGame().getHeight() / 2 + 80, 155, 33, Assets.papyrus28, new ClickListener() {
							@Override
							public void onClick() {
								clickSound.singleLoopSound(PATH_CLICK_AUDIO);
								uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
								try {
									Thread.sleep((long) (1000));
								} catch (InterruptedException e) {
								}
								State.setRepaint(true);
								State.setState(handler.getGame().loadGameState);
							}
						}));
			}
		}

		uiManager.addObject(new UIText(handler, "back", "Back", handler.getGame().getWidth() / 2 - 36,
				handler.getGame().getHeight() / 2 + 320, 75, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
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
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);

		if (message.equals("new game")) {
			int lineHeight = g.getFontMetrics().getHeight() / 2 + 10;
			String text = "Your current progression\nwill be lost. Are you sure\nyou want to start a new game?";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 - 30 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		}

	}

}
