package p.States;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
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

public class MenuState extends State {
	private String PATH_CLICK_AUDIO = "source/audio/click.wav";
	private String BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_CLOSING = "/images/background_images/close.gif",
			BACKGROUND_IMAGE_PATH_SWITCH = "/images/background_images/switch.gif",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png";
	private String message = "";
	private UIManager uiManager;
	private Music clickSound;
	private Dimension screenSize;
	private URL loop_url, switch_url, switch_back_url, closing_url;
	private ImageIcon loop_icon, switch_icon, switch_back_icon, closing_icon;
	private BufferedImage flickering, scroll;

	public MenuState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		clickSound = State.getMusic();
		uiManager = new UIManager(handler);
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

		closing_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_CLOSING);
		closing_icon = new ImageIcon(closing_url);
		closing_icon.setImage(closing_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, flickering, "flickering"));

		uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, loop_icon));

		uiManager.addObject(new UIText(handler, "start", "Start Game", handler.getGame().getWidth() / 2 - 81,
				handler.getGame().getHeight() / 2, 165, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setRepaint(true);
						State.setState(handler.getGame().startGameState);
					}
				}));

		uiManager.addObject(new UIText(handler, "settings", "Settings", handler.getGame().getWidth() / 2 - 58,
				handler.getGame().getHeight() / 2 + 60, 120, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setRepaint(true);
						State.setState(handler.getGame().settingsState);
					}
				}));

		uiManager.addObject(new UIText(handler, "logOut", "Log Out", handler.getGame().getWidth() / 2 - 60,
				handler.getGame().getHeight() / 2 + 180, 122, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setFocusable(true);

						uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));
						message = "log out";
						uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
								handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36, new ClickListener() {
									@Override
									public void onClick() {
										clickSound.singleLoopSound(PATH_CLICK_AUDIO);
										message = "";
										uiManager.addObject(
												new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
										State.setMusicOption("off");
										State.getMusic().loadMenuSoundVolFromTxt();
										try {
											Thread.sleep((long) (1000));
										} catch (InterruptedException e) {
										}
										State.setBackToLogIn(true);
										State.setRepaint(true);
										handler.getKeyManager().resetKeyTyped();
										State.setState(handler.getGame().logInState);
									}
								}));

						uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
								handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
									@Override
									public void onClick() {
										clickSound.singleLoopSound(PATH_CLICK_AUDIO);
										message = "";
										State.setRepaint(true);
										State.setState(handler.getGame().menuState);
									}
								}));

					}
				}));

		uiManager.addObject(new UIText(handler, "exit", "Exit Game", handler.getGame().getWidth() / 2 - 73,
				handler.getGame().getHeight() / 2 + 240, 150, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setFocusable(true);
						message = "exit";

						uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

						uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
								handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36, new ClickListener() {
									@Override
									public void onClick() {
										clickSound.singleLoopSound(PATH_CLICK_AUDIO);
										message = "";
										uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, closing_icon));
										try {
											Thread.sleep((long) (2900));
										} catch (InterruptedException e) {
										}
										State.getMusic().stopBackgroundSound();
										System.exit(0);
									}
								}));

						uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
								handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
									@Override
									public void onClick() {
										clickSound.singleLoopSound(PATH_CLICK_AUDIO);
										message = "";
										State.setRepaint(true);
										State.setState(handler.getGame().menuState);
									}
								}));
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

		if (message.equals("log out"))
			Text.drawString(g, "Do you want to log out?", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		else if (message.equals("exit"))
			Text.drawString(g, "Do you want to exit the game?", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
	}

}
