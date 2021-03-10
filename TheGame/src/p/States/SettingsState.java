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
import p.Utils.Utils;

public class SettingsState extends State {

	private String FLICKERING = "flickering", message = "", oldMusicOption, oldVolumeOption;
	private String PATH_CLICK_AUDIO = "source/audio/click.wav", PATH_WALKING_AUDIO = "source/audio/footsteps.wav";
	private String BACKGROUND_IMAGE_PATH_LOOP = "/images/background_images/loop.gif",
			BACKGROUND_IMAGE_PATH_FLICKERING = "/images/background_images/flickering.png",
			BACKGROUND_IMAGE_PATH_SWITCH_BACK = "/images/background_images/switch_back.gif",
			BACKGROUND_IMAGE_PATH_LOADING = "/images/background_images/loading.gif";
	private URL loop_url, switch_back_url, loading_url;
	private Music clickSound;
	private ImageIcon loop_icon, switch_back_icon, loading_icon;
	private BufferedImage flickering;
	private UIManager uiManager;
	private Dimension screenSize;
	private String file;
	private String[] tokens;

	public SettingsState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		uiManager = new UIManager(handler);
		clickSound = State.getMusic();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		flickering = ImageLoader.loadImage(BACKGROUND_IMAGE_PATH_FLICKERING);

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

		music();
		sound();

		file = Utils.loadFileAsString("source/game/accounts/" + State.getUsername() + "/settings/settings.txt");
		tokens = file.split("\\s+");
		oldMusicOption = tokens[1];
		oldVolumeOption = tokens[3];

		// BACK
		uiManager.addObject(new UIText(handler, "back", "Back", handler.getGame().getWidth() / 2 - 36,
				handler.getGame().getHeight() / 2 + 320, 75, 33, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						if (!State.getMusicOption().equals(oldMusicOption)
								|| !State.getVolumeOption().equals(oldVolumeOption)) {
							uiManager.addObject(new UIBackgroundImage(handler, 0, 0, handler.getGame().getWidth() - 67,
									handler.getGame().getHeight() - 95, loading_icon));
							message = "save";
							try {
								Thread.sleep((long) (1000));
							} catch (InterruptedException e) {
							}
						}
						uiManager.addObject(new UIBackgroundImage(handler, 0, 0, 0, 0, switch_back_icon));
						message = "";
						try {
							Thread.sleep((long) (1000));
						} catch (InterruptedException e) {
						}
						State.setRepaint(true);
						if (!State.isPauseSettings()) {
							State.setState(handler.getGame().menuState);
						} else {
							State.setState(handler.getGame().pauseState);
						}
					}
				}));
	}

	private void music() {
		uiManager.addObject(new UIText(handler, "music", "Music", handler.getGame().getWidth() / 2 - 39,
				handler.getGame().getHeight() / 2 - 50, 0, 0, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
					}
				}));

		uiManager.addObject(new UIText(handler, "music off", "Off", handler.getGame().getWidth() / 2 - 149,
				handler.getGame().getHeight() / 2, 40, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setMusicOption("off");
						State.getMusic().menuSoundVol();
					}
				}));

		uiManager.addObject(new UIText(handler, "music quiet", "Quiet", handler.getGame().getWidth() / 2 - 92,
				handler.getGame().getHeight() / 2, 60, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setMusicOption("quiet");
						State.getMusic().menuSoundVol();
					}
				}));

		uiManager.addObject(new UIText(handler, "music medium", "Medium", handler.getGame().getWidth() / 2 + -11,
				handler.getGame().getHeight() / 2, 75, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setMusicOption("medium");
						State.getMusic().menuSoundVol();
					}
				}));

		uiManager.addObject(new UIText(handler, "music loud", "Loud", handler.getGame().getWidth() / 2 + 83,
				handler.getGame().getHeight() / 2, 55, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setMusicOption("loud");
						State.getMusic().menuSoundVol();
					}
				}));
	}

	private void sound() {
		uiManager.addObject(new UIText(handler, "sound", "Sound", handler.getGame().getWidth() / 2 - 45,
				handler.getGame().getHeight() / 2 + 70, 0, 0, Assets.papyrus28, new ClickListener() {
					@Override
					public void onClick() {
					}
				}));

		uiManager.addObject(new UIText(handler, "sound off", "Off", handler.getGame().getWidth() / 2 - 149,
				handler.getGame().getHeight() / 2 + 120, 40, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setVolumeOption("off");
						State.getMusic().singleLoopSound(PATH_CLICK_AUDIO);
						State.setPlayWalkingsound(false);
						State.getMusic().walkingSound(PATH_WALKING_AUDIO);

					}
				}));

		uiManager.addObject(new UIText(handler, "sound quiet", "Quiet", handler.getGame().getWidth() / 2 - 92,
				handler.getGame().getHeight() / 2 + 120, 60, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setVolumeOption("quiet");
						State.getMusic().singleLoopSound(PATH_CLICK_AUDIO);
						State.setPlayWalkingsound(false);
						State.getMusic().walkingSound(PATH_WALKING_AUDIO);

					}
				}));

		uiManager.addObject(new UIText(handler, "sound medium", "Medium", handler.getGame().getWidth() / 2 + -11,
				handler.getGame().getHeight() / 2 + 120, 75, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setVolumeOption("medium");
						State.getMusic().singleLoopSound(PATH_CLICK_AUDIO);
						State.setPlayWalkingsound(false);
						State.getMusic().walkingSound(PATH_WALKING_AUDIO);

					}
				}));

		uiManager.addObject(new UIText(handler, "sound loud", "Loud", handler.getGame().getWidth() / 2 + 83,
				handler.getGame().getHeight() / 2 + 120, 55, 27, Assets.papyrus22, new ClickListener() {
					@Override
					public void onClick() {
						clickSound.singleLoopSound(PATH_CLICK_AUDIO);
						State.setVolumeOption("loud");
						State.getMusic().singleLoopSound(PATH_CLICK_AUDIO);
						State.setPlayWalkingsound(false);
						State.getMusic().walkingSound(PATH_WALKING_AUDIO);

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

		if (message.equals("save"))
			Text.drawString(g, "Saving", handler.getGame().getWidth() - 45, handler.getGame().getHeight() - 25, true,
					Color.white, Assets.papyrus22);
	}
}
