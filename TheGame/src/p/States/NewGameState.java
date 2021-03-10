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
import p.UI.UIChoseCharacter;
import p.UI.UIManager;
import p.UI.UIText;
import p.Utils.Utils;

public class NewGameState extends State {
	private String FLICKERING = "flickering";
	private String PATH_CLICK_AUDIO = "source/audio/click.wav";
	private String BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif",
			BACKGROUND_IMAGE_PATH_LOADING = "/images/background_images/loading.gif";
	private String file, message = "";
	private String[] tokens;
	private URL loop_url, switch_back_url, loading_url;
	private Music clickSound;
	private ImageIcon loop_icon, switch_back_icon, loading_icon;
	private BufferedImage flickering, scroll;
	private UIManager uiManager;
	private Dimension screenSize;

	public NewGameState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		uiManager = new UIManager(handler);
		file = Utils.loadFileAsString("source/game/accounts/" + State.getUsername() + "/settings/settings.txt");
		tokens = file.split("\\s+");
		clickSound = State.getMusic();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		flickering = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_FLICKERING);
		scroll = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_SCROLL);

		loop_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOOP);
		loop_icon = new ImageIcon(loop_url);
		loop_icon.setImage(loop_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		loading_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOADING);
		loading_icon = new ImageIcon(loading_url);
		loading_icon.setImage(loading_icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		switch_back_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_SWITCH_BACK);
		switch_back_icon = new ImageIcon(switch_back_url);
		switch_back_icon.setImage(switch_back_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, flickering, FLICKERING));
		uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, loop_icon));

		uiManager.addObject(new UIText(handler, "difficulty", "Difficulty", handler.getGame().getWidth() / 2 - 65,
				handler.getGame().getHeight() / 2 - 50, 130, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
					}
				}));

		uiManager.addObject(new UIText(handler, "easy", "Easy", handler.getGame().getWidth() / 2 - 137,
				handler.getGame().getHeight() / 2, 60, 22, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setDifficultyOption("easy");
					}
				}));

		uiManager.addObject(new UIText(handler, "normal", "Normal", handler.getGame().getWidth() / 2 - 37,
				handler.getGame().getHeight() / 2, 75, 22, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setDifficultyOption("normal");
					}
				}));

		uiManager.addObject(new UIText(handler, "hard", "Hard", handler.getGame().getWidth() / 2 + 73,
				handler.getGame().getHeight() / 2, 55, 22, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setDifficultyOption("hard");
					}
				}));

		uiManager.addObject(new UIText(handler, "previous", "<", handler.getGame().getWidth() / 2 - 74,
				handler.getGame().getHeight() / 2 + 85, 28, 30, Assets.papyrus36, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setCounter(State.getCounter() - 1);
						if (State.getCounter() <= 0)
							State.setCounter(10);
					}
				}));

		uiManager.addObject(new UIChoseCharacter(handler, handler.getGame().getWidth() / 2 - 30,
				handler.getGame().getHeight() / 2 + 60, 60, 65));

		uiManager.addObject(new UIText(handler, "next", ">", handler.getGame().getWidth() / 2 + 42,
				handler.getGame().getHeight() / 2 + 85, 28, 30, Assets.papyrus36, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setCounter(State.getCounter() + 1);
						if (State.getCounter() >= 11)
							State.setCounter(1);
					}
				}));

		uiManager.addObject(new UIText(handler, "play", "Play", handler.getGame().getWidth() / 2 + 54,
				handler.getGame().getHeight() / 2 + 320, 60, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						if (!State.getDifficultyOption().equals("")) {
							message = "loading";
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, handler.getGame().getWidth() - 67,
									handler.getGame().getHeight() - 95, loading_icon));
							try {
								Thread.sleep((long) (5000));
							} catch (InterruptedException e) {
							}
							Utils.modifyFile("source/game/accounts/" + State.getUsername() + "/settings/settings.txt",
									tokens[7], State.getDifficultyOption());
							State.setGameType("new game");
							Utils.modifyFile("source/game/accounts/" + State.getUsername() + "/settings/settings.txt",
									tokens[5], Integer.toString(State.getCounter()));
							State.setGameState(true);
							State.getMusic().stopBackgroundSound();
							State.setState(handler.getGame().gameState);
						} else {
							State.setFocusable(true);
							message = "play";

							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "ok", "Ok", handler.getGame().getWidth() / 2 - 30,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											State.setRepaint(true);
											State.setState(handler.getGame().newGameState);
										}
									}));
						}
					}
				}));

		uiManager.addObject(new UIText(handler, "back", "Back", handler.getGame().getWidth() / 2 - 118,
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
						State.setState(handler.getGame().startGameState);
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

		if (message.equals("play"))
			Text.drawString(g, "Chose difficulty first.", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		else if (message.equals("loading")) {
			Text.drawString(g, "Loading", handler.getGame().getWidth() - 45, handler.getGame().getHeight() - 25, true,
					Color.white, Assets.papyrus22);
		}
	}

}
