package p.States;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import p.UI.UIObject;
import p.UI.UIText;
import p.Utils.Utils;

public class PauseState extends State {

	private static final int SPACING = 25;
	private String FLICKERING = "flickering", message = "", message2 = "", text;
	private String BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SCROLL = "/images/background_images/scroll.png",
			BACKGROUND_IMAGE_PATH_CLOSING = "/images/background_images/close.gif",
			BACKGROUND_IMAGE_PATH_SWITCH = "/images/background_images/switch.gif",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif",
			BACKGROUND_IMAGE_PATH_LOADING = "/images/background_images/loading.gif";
	private String PATH_CLICK_AUDIO = "source/audio/click.wav";
	private UIManager uiManager;
	private UIObject cancelUIObject, scrollUIObject, yesUIObject, noUIObject, fileNameUIObject, saveUIObject,
			loadingUIObject;
	private Music clickSound;
	private BufferedImage flickering, scroll;
	private Dimension screenSize;
	private URL loop_url, closing_url, switch_url, switch_back_url, loading_url;
	private ImageIcon loop_icon, closing_icon, switch_icon, switch_back_icon, loading_icon;

	public PauseState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		State.setPauseSettings(false);
		State.setInputName("");
		clickSound = State.getMusic();
		uiManager = new UIManager(handler);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		message = "";
		message2 = "";

		flickering = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_FLICKERING);
		scroll = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_SCROLL);

		loop_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOOP);
		loop_icon = new ImageIcon(loop_url);
		loop_icon.setImage(loop_icon.getImage().getScaledInstance((int) screenSize.getWidth(),
				(int) screenSize.getHeight(), Image.SCALE_DEFAULT));

		loading_url = this.getClass().getResource(BACKGROUND_IMAGE_PATH_LOADING);
		loading_icon = new ImageIcon(loading_url);
		loading_icon.setImage(loading_icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

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

		uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, flickering, FLICKERING));

		uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, loop_icon));

		uiManager.addObject(new UIText(handler, "resume", "Resume", handler.getGame().getWidth() / 2 - 50,
				handler.getGame().getHeight() / 2 - SPACING, 100, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						UIObject loading = uiManager.addObject(new UIBackgroundImage(handler, 0, 0,
								handler.getGame().getWidth() - 67, handler.getGame().getHeight() - 95, loading_icon));
						message = "resume";
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						message = "";
						uiManager.removeObject(loading);
						State.getMusic().stopBackgroundSound();
						State.setGameState(true);
						State.setPauseSettings(false);
						handler.getGame().resume();
						State.setState(State.getResumeGameState());
					}
				}));

		uiManager.addObject(new UIText(handler, "saveGame", "Save Game", handler.getGame().getWidth() / 2 - 78,
				handler.getGame().getHeight() / 2 + SPACING, 160, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						saveGameOptions();
					}
				}));

		uiManager.addObject(new UIText(handler, "loadGame", "Load Game", handler.getGame().getWidth() / 2 - 78,
				handler.getGame().getHeight() / 2 + SPACING * 3, 160, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setPauseSettings(true);
						handler.getGame().resume();
						if (!State.isSaved()) {
							message = "load";
							State.setFocusable(true);
							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
									handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											uiManager
													.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
											try {
												Thread.sleep((long) (1000));
											} catch (InterruptedException e) {
											}
											State.setRepaint(true);
											State.setState(handler.getGame().loadGameState);
										}
									}));

							uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											State.setRepaint(true);
											State.setState(handler.getGame().pauseState);
										}
									}));
						} else {
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
							try {
								Thread.sleep((long) (1000));
							} catch (InterruptedException e) {
							}
							State.setRepaint(true);
							State.setState(handler.getGame().loadGameState);
						}
					}
				}));

		uiManager.addObject(new UIText(handler, "settings", "Settings", handler.getGame().getWidth() / 2 - 58,
				handler.getGame().getHeight() / 2 + SPACING * 5, 120, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_icon));
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setPauseSettings(true);
						State.setRepaint(true);
						State.setState(handler.getGame().settingsState);
					}
				}));

		uiManager.addObject(new UIText(handler, "main_menu", "Back to Main Menu",
				handler.getGame().getWidth() / 2 - 132, handler.getGame().getHeight() / 2 + SPACING * 10, 265, 33,
				Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setPauseSettings(false);
						if (!State.isSaved()) {
							message = "!saved_main_many";
							State.setFocusable(true);
							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
									handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											handler.getGame().resume();
											message = "";
											uiManager.addObject(
													new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
											try {
												Thread.sleep((long) (1000));
											} catch (InterruptedException e) {
											}
											State.setRepaint(true);
											State.setState(handler.getGame().menuState);
										}
									}));

							uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											State.setRepaint(true);
											State.setState(handler.getGame().pauseState);
										}
									}));
						} else {
							message = "saved_main_many";
							State.setFocusable(true);
							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
									handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											handler.getGame().resume();
											message = "";
											uiManager.addObject(
													new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
											try {
												Thread.sleep((long) (1000));
											} catch (InterruptedException e) {
											}
											State.setRepaint(true);
											State.setState(handler.getGame().menuState);
										}
									}));

							uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											State.setRepaint(true);
											State.setState(handler.getGame().pauseState);
										}
									}));
						}
					}
				}));

		uiManager.addObject(new UIText(handler, "exit", "Exit Game", handler.getGame().getWidth() / 2 - 73,
				handler.getGame().getHeight() / 2 + SPACING * 12, 150, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setFocusable(true);
						if (!State.isSaved()) {
							message = "!saved_exit";

							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
									handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											uiManager.addObject(
													new UIBackgroundImage(handler, 0, 0, 0, 0, closing_icon));
											try {
												Thread.sleep((long) (2900));
											} catch (InterruptedException e) {
											}
											State.getMusic().stopBackgroundSound();
											System.exit(0);
										}
									}));

							uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											State.setRepaint(true);
											State.setState(handler.getGame().pauseState);
										}
									}));
						} else {
							message = "saved_exit";
							uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

							uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
									handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36,
									new ClickListener() {

										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											message = "";
											uiManager.addObject(
													new UIBackgroundImage(handler, 0, 0, 0, 0, closing_icon));
											try {
												Thread.sleep((long) (2900));
											} catch (InterruptedException e) {
											}
											State.getMusic().stopBackgroundSound();
											System.exit(0);
										}
									}));

							uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
									handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36,
									new ClickListener() {
										@Override
										public void onClick() {
											clickSound.singleLoopSound(PATH_CLICK_AUDIO);
											State.setRepaint(true);
											State.setState(handler.getGame().pauseState);
										}
									}));
						}
					}
				}));
	}

	private void repaint() {
		uiManager.removeObject(scrollUIObject);
		uiManager.removeObject(yesUIObject);
		uiManager.removeObject(noUIObject);
		uiManager.removeObject(fileNameUIObject);
		uiManager.removeObject(saveUIObject);
		uiManager.removeObject(cancelUIObject);
	}

	private void saveGameOptions() {
		repaint();
		clickSound.singleLoopSound(PATH_CLICK_AUDIO);
		State.setFocusable(true);
		State.setInputName("save file");

		if (Utils.findFilesInDir("source/game/accounts/" + State.getUsername() + "/load game/").length >= 50) {
			message = "save limit";

			scrollUIObject = uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

			yesUIObject = uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
					handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							message2 = "saving";
							saveGame();
						}
					}));

			noUIObject = uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
					handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							State.setRepaint(true);
							State.setState(handler.getGame().pauseState);
						}
					}));
		} else {
			message = "save";
			scrollUIObject = uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

			fileNameUIObject = uiManager
					.addObject(new UIText(handler, "name", "File name:", handler.getGame().getWidth() / 2 - 200,
							handler.getGame().getHeight() / 2 + 20, 0, 0, Assets.papyrus28, new ClickListener() {
								@Override
								public void onClick() {
								}
							}));

			saveUIObject = uiManager
					.addObject(new UIText(handler, "save", "Save", handler.getGame().getWidth() / 2 - 121,
							handler.getGame().getHeight() / 2 + 140, 95, 41, Assets.papyrus36, new ClickListener() {
								@Override
								public void onClick() {
									clickSound.singleLoopSound(PATH_CLICK_AUDIO);
									saveGame();
								}
							}));

			cancelUIObject = uiManager
					.addObject(new UIText(handler, "cancel", "Cancel", handler.getGame().getWidth() / 2 + 27,
							handler.getGame().getHeight() / 2 + 140, 125, 41, Assets.papyrus36, new ClickListener() {
								@Override
								public void onClick() {
									clickSound.singleLoopSound(PATH_CLICK_AUDIO);
									State.setRepaint(true);
									handler.getKeyManager().resetKeyTyped();
									State.setState(handler.getGame().pauseState);
								}
							}));
		}
	}

	private void saveGame() {
		if (Utils.findFilesInDir("source/game/accounts/" + State.getUsername() + "/load game/").length >= 50) {
			Utils.deleteLastFileInDir("source/game/accounts/" + State.getUsername() + "/load game/");
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH-mm-ss");
		Date date = new Date();
		String save_game_path = Paths
				.get("G:/workspace/TheGame/source/game/accounts/" + State.getUsername() + "/load game/"
						+ formatter.format(date) + "--"
						+ handler.getKeyManager().getSaveName().substring(0,
								handler.getKeyManager().getSaveName().length() - 1)
						+ "--" + ".txt")
				.toAbsolutePath().toString();

		if (!Utils.checkIfSaveFileExists(
				"G:/workspace/TheGame/source/game/accounts/" + State.getUsername() + "/load game/", handler)) {
			message2 = "saving";
			saveTxt(save_game_path);
			State.setSaved(true);
			uiManager.removeObject(loadingUIObject);
			loadingUIObject = uiManager.addObject(new UIBackgroundImage(handler, 0, 0,
					handler.getGame().getWidth() - 67, handler.getGame().getHeight() - 95, loading_icon));

			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
			}
			message = "";
			handler.getKeyManager().resetKeyTyped();
			State.setRepaint(true);
			State.setState(handler.getGame().pauseState);

		} else {
			repaint();
			State.setFocusable(true);
			scrollUIObject = uiManager.addObject(new UIImage(handler, 0, 0, 0, 0, scroll, "scroll"));

			message = "save exists";
			State.setInputName("");
			yesUIObject = uiManager.addObject(new UIText(handler, "yes", "Yes", handler.getGame().getWidth() / 2 - 84,
					handler.getGame().getHeight() / 2 + 140, 70, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							clickSound.singleLoopSound(PATH_CLICK_AUDIO);
							message2 = "saving";
							Utils.deleteSavedFile("source/game/accounts/" + State.getUsername() + "/load game/",
									handler.getKeyManager().getSaveName().substring(0,
											handler.getKeyManager().getSaveName().length() - 1));
							saveTxt(save_game_path);
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, handler.getGame().getWidth() - 67,
									handler.getGame().getHeight() - 95, loading_icon));

							try {
								Thread.sleep((long) (1000));
							} catch (InterruptedException e) {
							}
							message = "";
							message2 = "";
							handler.getKeyManager().resetKeyTyped();
							State.setRepaint(true);
							State.setSaved(true);
							State.setState(handler.getGame().pauseState);
						}
					}));

			noUIObject = uiManager.addObject(new UIText(handler, "no", "No", handler.getGame().getWidth() / 2 + 20,
					handler.getGame().getHeight() / 2 + 140, 60, 41, Assets.papyrus36, new ClickListener() {
						@Override
						public void onClick() {
							saveGameOptions();
						}
					}));
		}
	}

	public void saveTxt(String path) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
			handler.getWorld().getEntityManager().sortEntitiesByNameAndID();
			for (int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++) {
				writer.write(handler.getWorld().getEntityManager().getEntities().get(i).getName() + " "
						+ (int) (handler.getWorld().getEntityManager().getEntities().get(i).getX()) + " "
						+ (int) (handler.getWorld().getEntityManager().getEntities().get(i).getY()) + " "
						+ handler.getWorld().getEntityManager().getEntities().get(i).getId() + " "
						+ handler.getWorld().getEntityManager().getEntities().get(i).getSpeed() + " "
						+ handler.getWorld().getEntityManager().getEntities().get(i).getHealth() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void tick() {
		if (State.isRepaint()) {
			State.setRepaint(false);
			init();
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			State.getMusic().stopBackgroundSound();
			handler.getGame().resume();
			State.setGameState(true);
			State.setState(State.getResumeGameState());
		}
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();

	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
		int lineHeight = g.getFontMetrics().getHeight() / 2 + 10;

		if (message.equals("resume")) {
			Text.drawString(g, "Loading", handler.getGame().getWidth() - 45, handler.getGame().getHeight() - 25, true,
					Color.white, Assets.papyrus22);
		} else if (message.equals("save")) {
			g.setColor(Color.black);
			g.drawLine(handler.getGame().getWidth() / 2 - 50, handler.getGame().getHeight() / 2 + 55,
					handler.getGame().getWidth() / 2 + 200, handler.getGame().getHeight() / 2 + 55);

			Text.drawString(g, handler.getKeyManager().getSaveName(), handler.getGame().getWidth() / 2 + 75,
					handler.getGame().getHeight() / 2 + 40, true, Color.black, Assets.papyrus22);
		} else if (message.equals("save limit")) {
			text = "You have reached the maximum\nnumber of saved files.\nDo you want to override\nthe oldest one?";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 - 30 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		} else if (message.equals("save exists")) {
			text = "File '"
					+ handler.getKeyManager().getSaveName().substring(0,
							handler.getKeyManager().getSaveName().length() - 1)
					+ "'\n already exists.\nDo you want to override it?";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		} else if (message.equals("load")) {
			text = "Do you want to load new game?\nAll unsaved progress will be lost!";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		} else if (message.equals("!saved_main_many")) {
			text = "Back to main menu?\nAll unsaved progress will be lost!";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		} else if (message.equals("saved_main_many")) {
			Text.drawString(g, "Back to main menu?", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + lineHeight, true, Color.black, Assets.papyrus28);
		} else if (message.equals("!saved_exit")) {
			text = "Do you want to exit the game?\nAll unsaved progress will be lost!";
			String[] tokens = text.split("\n");
			for (int i = 0; i < tokens.length; i++) {
				Text.drawString(g, tokens[i], handler.getGame().getWidth() / 2,
						handler.getGame().getHeight() / 2 + lineHeight * i, true, Color.black, Assets.papyrus28);
			}
		} else if (message.equals("saved_exit")) {
			Text.drawString(g, "Do you want to exit the game?", handler.getGame().getWidth() / 2,
					handler.getGame().getHeight() / 2 + 20, true, Color.black, Assets.papyrus28);
		}

		if (message2.equals("saving")) {
			Text.drawString(g, "Saving", handler.getGame().getWidth() - 45, handler.getGame().getHeight() - 25, true,
					Color.white, Assets.papyrus22);
		}
	}
}
