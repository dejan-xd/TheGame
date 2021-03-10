package p.Audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import p.States.State;
import p.Utils.Utils;

public class Music {
	private File musicPath;
	private AudioInputStream ai;
	private Clip clipLoop, clipWalking;
	private FloatControl volumeMenuSound, volumeSingleLoop;
	private float off, quiet, medium, loud;
	private long clipTime;
	private String settingsPath;
	private String settingsString, option;
	private String[] tokens;

	public Music() {
		off = -80.0f;
		quiet = -40.0f;
		medium = -30.0f;
		loud = -10.0f;
		settingsPath = "source/game/accounts/" + State.getUsername() + "/settings/settings.txt";
		settingsString = Utils.loadFileAsString(settingsPath);
		tokens = settingsString.split("\\s+");
	}

	public void menuSounds(String path, String option, boolean loop) {
		try {
			if (musicPath != null && ai != null && loop == false) {
				ai.close();
				clipLoop.stop();
				clipLoop.close();
			}
			musicPath = new File(path);
			if (musicPath.exists()) {
				ai = AudioSystem.getAudioInputStream(musicPath);
				clipLoop = AudioSystem.getClip();
				clipLoop.open(ai);
				volumeMenuSound = (FloatControl) clipLoop.getControl(FloatControl.Type.MASTER_GAIN);
				if (tokens[1].equals("off")) {
					volumeMenuSound.setValue(off);
				} else if (tokens[1].equals("quiet")) {
					volumeMenuSound.setValue(quiet);
				} else if (tokens[1].equals("medium")) {
					volumeMenuSound.setValue(medium);
				} else if (tokens[1].equals("loud")) {
					volumeMenuSound.setValue(loud);
				}
				clipLoop.start();
				clipLoop.loop(Clip.LOOP_CONTINUOUSLY);
				loop = true;
			}
		} catch (

		Exception ex) {
			ex.printStackTrace();
		}
		return;
	}

	public void walkingSound(String path) {
		try {
			musicPath = new File(path);
			if (musicPath.exists()) {
				ai = AudioSystem.getAudioInputStream(musicPath);
				clipWalking = AudioSystem.getClip();
				clipWalking.open(ai);
				volumeSingleLoop = (FloatControl) clipWalking.getControl(FloatControl.Type.MASTER_GAIN);
				soundVolume();
				if (State.isPlayWalkingsound()) {
					clipWalking.start();
					clipWalking.loop(Clip.LOOP_CONTINUOUSLY);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return;
	}

	public void singleLoopSound(String path) {
		try {
			musicPath = new File(path);
			if (musicPath.exists()) {
				ai = AudioSystem.getAudioInputStream(musicPath);
				clipWalking = AudioSystem.getClip();
				clipWalking.open(ai);
				volumeSingleLoop = (FloatControl) clipWalking.getControl(FloatControl.Type.MASTER_GAIN);
				soundVolume();
				clipWalking.start();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return;
	}

	public void loadMenuSoundVolFromTxt() {
		if (State.getMusicOption().equals("off")) {
			option = "off";
			volumeMenuSound.setValue(off);
		} else if (State.getMusicOption().equals("quiet")) {
			option = "quiet";
			volumeMenuSound.setValue(quiet);
		} else if (State.getMusicOption().equals("medium")) {
			option = "medium";
			volumeMenuSound.setValue(medium);
		} else if (State.getMusicOption().equals("loud")) {
			option = "loud";
			volumeMenuSound.setValue(loud);
		}
	}

	public void menuSoundVol() {
		if (State.getMusicOption().equals("off")) {
			option = "off";
			volumeMenuSound.setValue(off);
		} else if (State.getMusicOption().equals("quiet")) {
			option = "quiet";
			volumeMenuSound.setValue(quiet);
		} else if (State.getMusicOption().equals("medium")) {
			option = "medium";
			volumeMenuSound.setValue(medium);
		} else if (State.getMusicOption().equals("loud")) {
			option = "loud";
			volumeMenuSound.setValue(loud);
		}
		settingsString = Utils.loadFileAsString(settingsPath);
		tokens = settingsString.split("\\s+");
		Utils.modifyFile(settingsPath, tokens[0] + " " + tokens[1], tokens[0] + " " + option);
	}

	private void soundVolume() {
		settingsPath = "source/game/accounts/" + State.getUsername() + "/settings/settings.txt";
		settingsString = Utils.loadFileAsString(settingsPath);
		tokens = settingsString.split("\\s+");
		if (State.getVolumeOption().equals("")) {
			if (tokens[3].equals("off")) {
				volumeSingleLoop.setValue(off);
				option = "off";
			} else if (tokens[3].equals("quiet")) {
				volumeSingleLoop.setValue(quiet);
				option = "quiet";
			} else if (tokens[3].equals("medium")) {
				volumeSingleLoop.setValue(medium);
				option = "medium";
			} else if (tokens[3].equals("loud")) {
				volumeSingleLoop.setValue(loud);
				option = "loud";
			}
		} else if (State.getVolumeOption().equals("off")) {
			option = "off";
			volumeSingleLoop.setValue(off);
		} else if (State.getVolumeOption().equals("quiet")) {
			option = "quiet";
			volumeSingleLoop.setValue(quiet);
		} else if (State.getVolumeOption().equals("medium")) {
			option = "medium";
			volumeSingleLoop.setValue(medium);
		} else if (State.getVolumeOption().equals("loud")) {
			option = "loud";
			volumeSingleLoop.setValue(loud);
		}
		settingsString = Utils.loadFileAsString(settingsPath);
		tokens = settingsString.split("\\s+");
		Utils.modifyFile(settingsPath, tokens[2] + " " + tokens[3], tokens[2] + " " + option);

	}

	public void stopBackgroundSound() {
		clipTime = clipLoop.getMicrosecondPosition();
		clipLoop.stop();
	}

	public void resumeBackgroundSound() {
		clipLoop.setMicrosecondPosition(clipTime);
		clipLoop.start();
	}

	public Clip getClipWalking() {
		return clipWalking;
	}

	public void setClipWalking(Clip clipWalking) {
		this.clipWalking = clipWalking;
	}
}
