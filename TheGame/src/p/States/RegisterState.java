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
import p.UI.UIBackgroundImage;
import p.UI.UIImage;
import p.UI.UIManager;
import p.UI.UIObject;
import p.UI.UIText;
import p.Utils.Utils;

public class RegisterState extends State {

	private String PATH_CLICK_AUDIO = "source/audio/click.wav";
	private String BACKGROUND_IMAGE_PATH_OPENING = "/images/background_images/open.gif",
			BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_CLOSING = "/images/background_images/close.gif",
			BACKGROUND_IMAGE_PATH_SWITCH = "/images/background_images/switch.gif",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif",
			BACKGROUND_IMAGE_PATH_LOADING = "/images/background_images/loading.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png";
	private String PATH_USERS = "source/game/users/users.txt";
	private String message = "", message_info = "", file;
	private UIManager uiManager;
	private Music clickSound;
	private Dimension screenSize;
	private URL opening_url, loop_url, switch_url, switch_back_url, closing_url, loading_url;
	private ImageIcon opening_icon, loop_icon, switch_icon, switch_back_icon, closing_icon, loading_icon;
	private BufferedImage flickering, scroll;
	private String[] tokens;

	public RegisterState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		State.setInputName("");
		clickSound = State.getMusic();
		uiManager = new UIManager(handler);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		file = Utils.loadFileAsString(PATH_USERS);
		tokens = file.split("\\n+");

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

		switch_back_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_SWITCH_BACK);
		switch_back_icon = new ImageIcon(switch_back_url);
		switch_back_icon.setImage(switch_back_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		closing_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_CLOSING);
		closing_icon = new ImageIcon(closing_url);
		closing_icon.setImage(closing_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		loading_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOADING);
		loading_icon = new ImageIcon(loading_url);
		loading_icon.setImage(loading_icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, flickering, "flickering"));

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

		uiManager.addObject(new UIText(handler, "passwordUnderscoreConfirm", "________________",
				handler.getGame().getWidth() / 2 - 136, handler.getGame().getHeight() / 2 + 95, 272, 33,
				Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						State.setInputName("passwordUnderscoreConfirm");
						message = "input";
					}
				}));

		uiManager.addObject(
				new UIText(handler, "passwordConfirm", "confirm password", handler.getGame().getWidth() / 2 - 82,
						handler.getGame().getHeight() / 2 + 135, 0, 0, Assets.papyrus22, new ClickListener() {
							@Override
							public void onClick() {
							}
						}));

		uiManager.addObject(new UIText(handler, "signIn", "Sign In", handler.getGame().getWidth() / 2 - 49,
				handler.getGame().getHeight() / 2 + 190, 100, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						signIn();
					}
				}));

		uiManager.addObject(new UIText(handler, "back", "Back", handler.getGame().getWidth() / 2 - 36,
				handler.getGame().getHeight() / 2 + 320, 75, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						message = "";
						handler.getGame().getKeyManager().resetKeyTyped();
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setBackToLogIn(true);
						State.setRepaint(true);
						State.setState(handler.getGame().logInState);
					}
				}));

	}

	public void signIn() {
		clickSound.singleLoopSound(PATH_CLICK_AUDIO);

		for (int i = 0; i < tokens.length; i++) {
			if (!handler.getGame().getKeyManager().getPassword()
					.equals(handler.getGame().getKeyManager().getConfirmPassword())) {
				message = "confirm password";
				break;
			}
			String[] a = tokens[i].split("\\s+");
			if (handler.getGame().getKeyManager().getUsername().equals(a[0])) {
				message = "username exists";
				break;
			}
		}

		if (message.equals("confirm password")) {
			State.setFocusable(true);
			uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

			uiManager.addObject(new UIText(handler, "ok", "Ok", handler.getGame().getWidth() / 2 - 30,
					handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							message = "input";
							State.setRepaint(true);
							State.setState(handler.getGame().registerState);
						}
					}));
		}
		if (message.equals("username exists")) {
			State.setFocusable(true);
			uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

			uiManager.addObject(new UIText(handler, "ok", "Ok", handler.getGame().getWidth() / 2 - 30,
					handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							message = "input";
							State.setRepaint(true);
							State.setState(handler.getGame().registerState);
						}
					}));
		}
		if (message.equals("input")) {
			State.setInputName("");
			File usernameFolder = new File("G:\\workspace\\TheGame\\source\\game\\accounts\\"
					+ handler.getGame().getKeyManager().getUsername());
			File loadGame = new File(usernameFolder + "\\load game");
			File settings = new File(usernameFolder + "\\settings");
			usernameFolder.mkdir();
			loadGame.mkdir();
			settings.mkdir();
			File file = new File(settings + "/settings.txt");
			Utils.createSettingsTxt(file);
			Utils.appendStrToFile(PATH_USERS, handler.getGame().getKeyManager().getUsername() + " "
					+ handler.getGame().getKeyManager().getPassword());

			UIObject register = uiManager.addObject(new UIBackgroundImage(handler, 0, 0,
					handler.getGame().getWidth() - 67, handler.getGame().getHeight() - 95, loading_icon));
			message_info = "register";
			try {
				Thread.sleep((long) (3000));
			} catch (InterruptedException e) {
			}
			message_info = "";
			uiManager.removeObject(register);

			State.setFocusable(true);
			uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));
			message = "registered";
			uiManager.addObject(new UIText(handler, "ok", "Ok", handler.getGame().getWidth() / 2 - 30,
					handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							message = "";
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
							try {
								Thread.sleep((long) (1000));
							} catch (InterruptedException e) {
							}
							handler.getGame().getKeyManager().resetKeyTyped();
							State.setRepaint(true);
							State.setBackToLogIn(true);
							State.setState(handler.getGame().logInState);
						}
					}));
		}

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

	String text;

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
		int lineHeight = g.getFontMetrics().getHeight() / 2 + 10;
		if (message.equals("input")) {
			Text.drawString(g, handler.getKeyManager().getUsername(), handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 - 35, true, Color.black, Assets.papyrus22);
			Text.drawString(g, handler.getKeyManager().getDisplayPassword(), handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 40, true, Color.black, Assets.papyrus22);
			Text.drawString(g, handler.getKeyManager().getDisplayConfirmPassword(), handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 115, true, Color.black, Assets.papyrus22);
		} else if (message.equals("confirm password")) {
			Text.drawString(g, "Passwords don't match!", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		} else if (message.equals("username exists")) {
			Text.drawString(g, "Username '" + handler.getGame().getKeyManager().getUsername() + "' already exists.",
					handler.getGame().getWidth() / 2, handler.getGame().getHeight() / 2 + 20, true, Color.black,
					Assets.papyrus28);
		} else if (message.equals("registered")) {
			text = "Username '" + handler.getGame().getKeyManager().getUsername() + "'\n successfully registered.";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		}

		if (message_info.equals("register")) {
			Text.drawString(g, "Registering", handler.getGame().getWidth() - 54, handler.getGame().getHeight() - 25,
					true, Color.white, Assets.papyrus22);
		}
	}
}
