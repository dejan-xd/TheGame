package p.States;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

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

public class LoadGameState extends State {
	private String FLICKERING = "flickering";
	private String PATH_CLICK_AUDIO = "source/audio/click.wav";
	private String BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif",
			BACKGROUND_IMAGE_PATH_LOADING = "/images/background_images/loading.gif";
	private String file, character, switchPage, message = "";
	private String[] tokens;
	private URL loop_url, switch_back_url, loading_url;
	private Music clickSound;
	private ImageIcon loop_icon, switch_back_icon, loading_icon;
	private BufferedImage flickering, scroll;
	private UIManager uiManager;
	private Dimension screenSize;
	private int counter, displayNumOfLoadGames, quotient, remainder, textWidth, textHeight;
	private ArrayList<String> al, alSublist;

	public LoadGameState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		State.setLoadGameState(true);
		State.setLoadGameCounter(0);
		State.setPage(0);
		State.setLoadGameTxtName("");
		switchPage = "";
		uiManager = new UIManager(handler);
		clickSound = State.getMusic();
		al = new ArrayList<String>();
		alSublist = new ArrayList<String>();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		file = Utils.loadFileAsString("source/game/accounts/" + State.getUsername() + "/settings/settings.txt");
		tokens = file.split("\\s+");
		character = tokens[5];
		if (!State.getUsername().equals("new game"))
			Utils.sortSavedFiles("source/game/accounts/" + State.getUsername() + "/load game", al);
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

		repaint();

		uiManager.addObject(new UIText(handler, "play", "Play", handler.getGame().getWidth() / 2 + 54,
				handler.getGame().getHeight() / 2 + 320, 60, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						if (!State.getLoadGameTxtName().equals("")) {
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, handler.getGame().getWidth() - 67,
									handler.getGame().getHeight() - 95, loading_icon));
							message = "loadign";
//
							try {
								Thread.sleep((long) (5000));
							} catch (InterruptedException e) {
							}
//
							message = "";
							State.setGameType("load game");
							State.setCounter(Integer.parseInt(character));
							State.setGameState(true);
							State.setLoadGameState(false);
							State.getMusic().stopBackgroundSound();
							State.setRepaint(true);
							State.setState(handler.getGame().gameState);
						} else {
							message = "choose";
							State.setFocusable(true);
							uiManager.getLoadGameObjects().removeAll(uiManager.getLoadGameObjects());
							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "ok", "Ok", handler.getGame().getWidth() / 2 - 30,
									handler.getGame().getHeight() / 2 + 140, 65, 45, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											State.setRepaint(true);
											State.setState(handler.getGame().loadGameState);
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
						uiManager.getLoadGameObjects().removeAll(uiManager.getLoadGameObjects());
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setLoadGameState(false);
						State.setLoadGameTxtName("");
						State.setRepaint(true);
						if (!State.isPauseSettings()) {
							State.setState(handler.getGame().startGameState);
						} else {
							State.setState(handler.getGame().pauseState);
						}
					}
				}));
	}

	private void repaint() {
		uiManager.getLoadGameObjects().removeAll(uiManager.getLoadGameObjects());
		counter = 0;
		displayNumOfLoadGames = 10;
		if (!State.getUsername().equals("new game")) {
			quotient = Utils.findFilesInDir("source/game/accounts/" + State.getUsername() + "/load game").length
					/ displayNumOfLoadGames;
			remainder = Utils.findFilesInDir("source/game/accounts/" + State.getUsername() + "/load game").length
					% displayNumOfLoadGames;
		}

		if (switchPage.equals("")) {
			if (quotient == 0)
				displayNumOfLoadGames = remainder;
			else
				displayNumOfLoadGames = 10;

			alSublist = new ArrayList<String>(al.subList(0, displayNumOfLoadGames + State.getLoadGameCounter()));

			for (int i = 0; i < alSublist.size(); i++) {
				String save_file_name = alSublist.get(i);
				String[] tokens = save_file_name.split("--");
				String display_name = tokens[1];
				textWidth = Utils.textWidth(display_name, Assets.papyrus22);
				textHeight = Utils.textHeight(display_name, Assets.papyrus22) - 7;
				uiManager.addLoadGameObject(new UIText(handler, save_file_name, display_name,
						(handler.getGame().getWidth() - textWidth) / 2,
						handler.getGame().getHeight() / 2 - 50 + counter * 32, textWidth, textHeight, Assets.papyrus22,
						new ClickListener() {
							@Override
							public void onClick() {
								clickSound.singleLoopSound(PATH_CLICK_AUDIO);
								State.setLoadGameTxtName(save_file_name);
								State.setLoadGamePath(
										"source/game/accounts/" + State.getUsername() + "/load game/" + save_file_name);
							}
						}));
				State.setLoadGameCounter(State.getLoadGameCounter() + 1);
				counter++;
			}
		}

		else if (switchPage.equals("next")) {
			if (quotient == State.getPage())
				displayNumOfLoadGames = remainder;
			else
				displayNumOfLoadGames = 10;

			alSublist = new ArrayList<String>(
					al.subList(State.getLoadGameCounter(), displayNumOfLoadGames + State.getLoadGameCounter()));

			for (int i = 0; i < alSublist.size(); i++) {
				String save_file_name = alSublist.get(i);
				String[] tokens = save_file_name.split("--");
				String display_name = tokens[1];
				textWidth = Utils.textWidth(display_name, Assets.papyrus22);
				textHeight = Utils.textHeight(display_name, Assets.papyrus22) - 7;
				uiManager.addLoadGameObject(new UIText(handler, save_file_name, display_name,
						(handler.getGame().getWidth() - textWidth) / 2,
						handler.getGame().getHeight() / 2 - 50 + counter * 32, textWidth, textHeight, Assets.papyrus22,
						new ClickListener() {
							@Override
							public void onClick() {
								clickSound.singleLoopSound(PATH_CLICK_AUDIO);
								State.setLoadGameTxtName(save_file_name);
								State.setLoadGamePath(
										"source/game/accounts/" + State.getUsername() + "/load game/" + save_file_name);
							}
						}));
				State.setLoadGameCounter(State.getLoadGameCounter() + 1);
				counter++;
			}
		}

		else if (switchPage.equals("previous")) {
			if (quotient + 1 == State.getPage())
				alSublist = new ArrayList<String>(
						al.subList(State.getLoadGameCounter() - displayNumOfLoadGames - remainder,
								State.getLoadGameCounter() - remainder));
			else
				alSublist = new ArrayList<String>(al.subList(State.getLoadGameCounter() - displayNumOfLoadGames * 2,
						State.getLoadGameCounter() - displayNumOfLoadGames));

			for (int i = 0; i < alSublist.size(); i++) {
				String save_file_name = alSublist.get(i);
				String[] tokens = save_file_name.split("--");
				String display_name = tokens[1];
				textWidth = Utils.textWidth(display_name, Assets.papyrus22);
				textHeight = Utils.textHeight(display_name, Assets.papyrus22) - 7;
				uiManager.addLoadGameObject(new UIText(handler, save_file_name, display_name,
						(handler.getGame().getWidth() - textWidth) / 2,
						handler.getGame().getHeight() / 2 - 50 + counter * 32, textWidth, textHeight, Assets.papyrus22,
						new ClickListener() {
							@Override
							public void onClick() {
								clickSound.singleLoopSound(PATH_CLICK_AUDIO);
								State.setLoadGameTxtName(save_file_name);
								State.setLoadGamePath(
										"source/game/accounts/" + State.getUsername() + "/load game/" + save_file_name);
							}
						}));
				State.setLoadGameCounter(State.getLoadGameCounter() - 1);
				counter++;
			}
			if (quotient + 1 == State.getPage())
				State.setLoadGameCounter(State.getLoadGameCounter() + displayNumOfLoadGames - remainder);
		}

		if (switchPage.equals("next") || switchPage.equals("")) {
			State.setPage(State.getPage() + 1);
		} else if (switchPage.equals("previous"))
			State.setPage(State.getPage() - 1);

		uiManager.addLoadGameObject(
				new UIText(handler, "page", Integer.toString(State.getPage()), handler.getGame().getWidth() / 2 - 9,
						handler.getGame().getHeight() / 2 + 270, 0, 0, Assets.papyrus28, new ClickListener() {
							@Override
							public void onClick() {
							}
						}));

		if (State.getPage() != 1) {
			uiManager.addLoadGameObject(new UIText(handler, "previous", "<", handler.getGame().getWidth() / 2 - 50,
					handler.getGame().getHeight() / 2 + 270, 20, 25, Assets.papyrus28, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							switchPage = "previous";
							repaint();
						}
					}));
		}

		if (remainder != 0)
			quotient = quotient + 1;

		if (quotient != State.getPage())
			if (!State.getUsername().equals("new game")) {
				if (Utils.findFilesInDir("source/game/accounts/" + State.getUsername() + "/load game").length > 7)

				{
					uiManager.addLoadGameObject(new UIText(handler, "next", ">", handler.getGame().getWidth() / 2 + 28,
							handler.getGame().getHeight() / 2 + 270, 20, 25, Assets.papyrus28, new ClickListener() {
								@Override
								public void onClick() {
									clickSound.singleLoopSound(PATH_CLICK_AUDIO);
									switchPage = "next";
									repaint();
								}
							}));
				}
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

	@Override
	public void render(Graphics g) {
		uiManager.render(g);

		if (message.equals("choose"))
			Text.drawString(g, "Choose saved file first.", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		else if (message.equals("loadign"))
			Text.drawString(g, "Loading", handler.getGame().getWidth() - 45, handler.getGame().getHeight() - 25, true,
					Color.white, Assets.papyrus22);
	}
}
