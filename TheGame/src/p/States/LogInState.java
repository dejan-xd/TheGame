package p.States;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

import p.Audio.Music;
import p.Game.Handler;
import p.Grafics.Assets;
import p.Grafics.ImageLoader;
import p.Grafics.Text;
import p.UI.ClickListener;
import p.UI.UIBackgroundImage;
import p.UI.UIImage;
import p.UI.UIManager;
import p.UI.UIText;
import p.Utils.Utils;

public class LogInState extends State {
	private String PATH_MENU_AUDIO = "source/audio/menu_song.wav", PATH_CLICK_AUDIO = "source/audio/click.wav";
	private String LOOP_INFINITY = "loop", BACKGROUND_IMAGE_PATH_OPENING = "/images/background_images/open.gif",
			BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_CLOSING = "/images/background_images/close.gif",
			BACKGROUND_IMAGE_PATH_SWITCH = "/images/background_images/switch.gif",
			BACKGROUND_IMAGE_PATH_LOADING = "/images/background_images/loading.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png";
	private String PATH_USERS = "source/game/users/users.txt";
	private String message = "", message_info = "", file;
	private UIManager uiManager;
	private Music backgroundSong, clickSound;
	private Dimension screenSize;
	private URL opening_url, loop_url, switch_url, closing_url, loading_url;
	private ImageIcon opening_icon, loop_icon, switch_icon, closing_icon, loading_icon;
	private BufferedImage flickering, scroll;
	private int timer = 0;
	private String[] tokens;
	private boolean readyToClick, checkInput;

	public LogInState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		clickSound = State.getMusic();
		uiManager = new UIManager(handler);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		file = Utils.loadFileAsString(PATH_USERS);
		tokens = file.split("\\n+");
		checkInput = false;

		flickering = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_FLICKERING);
		scroll = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_SCROLL);

		opening_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_OPENING);
		opening_icon = new ImageIcon(opening_url);
		opening_icon.setImage(opening_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		loop_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOOP);
		loop_icon = new ImageIcon(loop_url);
		loop_icon.setImage(loop_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		switch_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_SWITCH);
		switch_icon = new ImageIcon(switch_url);
		switch_icon.setImage(switch_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		closing_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_CLOSING);
		closing_icon = new ImageIcon(closing_url);
		closing_icon.setImage(closing_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		loading_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOADING);
		loading_icon = new ImageIcon(loading_url);
		loading_icon.setImage(loading_icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		if (!State.isBackToLogIn()) {
			uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, opening_icon));
			backgroundSong = State.getMusic();
			backgroundSong.menuSounds(PATH_MENU_AUDIO, LOOP_INFINITY, true);
			State.setMusic(backgroundSong);
		} else {
			uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, flickering, "flickering"));
		}

		uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, loop_icon));

		uiManager.addObject(
				new UIText(handler, "usernameUnderscore", "________________", handler.getGame().getWidth() / 2 - 136,
						handler.getGame().getHeight() / 2 - 55, 272, 33, Assets.papyrus28, new ClickListener() {
							@Override
							public void onClick() {
								State.setInputName("usernameUnderscore");
								message = "input";
							}
						}));

		uiManager.addObject(new UIText(handler, "username", "username", handler.getGame().getWidth() / 2 - 44,
				handler.getGame().getHeight() / 2 - 15, 0, 0, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
					}
				}));

		uiManager.addObject(
				new UIText(handler, "passwordUnderscore", "________________", handler.getGame().getWidth() / 2 - 136,
						handler.getGame().getHeight() / 2 + 20, 272, 33, Assets.papyrus28, new ClickListener() {
							@Override
							public void onClick() {
								State.setInputName("passwordUnderscore");
								message = "input";
							}
						}));

		uiManager.addObject(new UIText(handler, "password", "password", handler.getGame().getWidth() / 2 - 45,
				handler.getGame().getHeight() / 2 + 60, 0, 0, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
					}
				}));

		uiManager.addObject(new UIText(handler, "logIn", "Log In", handler.getGame().getWidth() / 2 - 43,
				handler.getGame().getHeight() / 2 + 100, 90, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setInputName("");
						checkInput();
					}
				}));

		uiManager.addObject(new UIText(handler, "register", "Register", handler.getGame().getWidth() / 2 - 56,
				handler.getGame().getHeight() / 2 + 220, 115, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						message = "";
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setRepaint(true);
						handler.getGame().getKeyManager().resetKeyTyped();
						State.setState(handler.getGame().registerState);
					}
				}));

		uiManager.addObject(new UIText(handler, "exit", "Exit Game", handler.getGame().getWidth() / 2 - 73,
				handler.getGame().getHeight() / 2 + 290, 150, 33, Assets.papyrus28, new ClickListener() {
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
										State.setBackToLogIn(true);
										State.setRepaint(true);
										message = "input";
										State.setState(handler.getGame().logInState);
									}
								}));
					}
				}));
	}

	private void checkInput() {
		for (int i = 0; i < tokens.length; i++) {
			if ((handler.getGame().getKeyManager().getUsername() + " "
					+ handler.getGame().getKeyManager().getPassword()).equals(tokens[i])) {
				checkInput = true;
				break;
			}
		}
		if (!checkInput) {
			State.setFocusable(true);
			uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));
			message = "wrong input";
			uiManager.addObject(new UIText(handler, "ok", "Ok", handler.getGame().getWidth() / 2 - 30,
					handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							message = "input";
							State.setRepaint(true);
							State.setBackToLogIn(true);
							readyToClick = true;
							State.setState(handler.getGame().logInState);
						}
					}));
		} else {
			message = "input";
			uiManager.addObject(new UIBackgroundImage(handler, 0, 0, handler.getGame().getWidth() - 67,
					handler.getGame().getHeight() - 95, loading_icon));
			message_info = "logging";
			State.setInputName("");
			//
			try {
				Thread.sleep((long) (5000));
			} catch (InterruptedException e) {
			}
			//
			message = "";
			message_info = "";
			uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
//
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
//			
			State.setRepaint(true);
			State.setUsername(handler.getGame().getKeyManager().getUsername());
			file = Utils.loadFileAsString("source/game/accounts/" + State.getUsername() + "/settings/settings.txt");
			tokens = file.split("\\s+");
			State.setMusicOption(tokens[1]);
			State.setVolumeOption(tokens[3]);
			State.getMusic().loadMenuSoundVolFromTxt();
			State.setState(handler.getGame().menuState);
		}
	}

	@Override
	public void tick() {
		if (State.isRepaint()) {
			State.setRepaint(false);
			init();
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER) && readyToClick) {
			checkInput();
			readyToClick = false;
		}
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		if (!State.isBackToLogIn()) {
			while (timer < 155) {
				uiManager.getObjects().get(0).render(g);
				timer++;
				return;
			}
			uiManager.getObjects().stream().skip(0);
		}
		uiManager.render(g);

		if (message.equals("input")) {
			Text.drawString(g, handler.getKeyManager().getUsername(), handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 - 35, true, Color.black, Assets.papyrus22);
			Text.drawString(g, handler.getKeyManager().getDisplayPassword(), handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 40, true, Color.black, Assets.papyrus22);
		} else if (message.equals("wrong input")) {
			Text.drawString(g, "Wrong username or password!", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		} else if (message.equals("exit")) {
			Text.drawString(g, "Do you want to exit the game?", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		}

		if (message_info.equals("logging")) {
			Text.drawString(g, "Logging In", handler.getGame().getWidth() - 52, handler.getGame().getHeight() - 25,
					true, Color.white, Assets.papyrus22);
		}
	}
}
