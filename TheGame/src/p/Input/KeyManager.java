package p.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import p.Audio.Music;
import p.States.State;

public class KeyManager implements KeyListener {
	private String pathWalkingAudio, saveName = "", username = "", displayPassword = "", password = "",
			confirmPassword = "", displayConfirmPassword = "";
	private boolean[] keys, justPressed, cantPress;
	private boolean play = true;
	private final Set<Character> pressed;
	private int countSaveName = 0, countUserName = 0, countPassword = 0, countConfirmPassword = 0;
	private Music music;
	public boolean up, down, left, right, esc, open, pause, one;

	public KeyManager() {
		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
		pressed = new HashSet<Character>();
		play = true;
		pathWalkingAudio = "source/audio/footsteps.wav";
	}

	public void tick() {
		keyPressedOnce();
		keys();
	}

	public boolean keyJustPressed(int keyCode) {
		if (keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}

	public void keyPressedOnce() {
		for (int i = 0; i < keys.length; i++) {
			if (cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			} else if (justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if (!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
		}
	}

	public void keys() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		open = keys[KeyEvent.VK_F];
		esc = keys[KeyEvent.VK_ESCAPE];
		one = keys[KeyEvent.VK_1];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = true;
		pressed.add(e.getKeyChar());

		if (State.isGameState()) {
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_A
					|| e.getKeyCode() == KeyEvent.VK_D) {
				if (e.getKeyCode() == KeyEvent.VK_ALT)
					return;
				if (play) {
					play = false;
					music = State.getMusic();
					State.setPlayWalkingsound(true);
					music.walkingSound(pathWalkingAudio);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		keys[e.getKeyCode()] = false;
		pressed.remove(e.getKeyChar());
		if (State.isGameState()) {
			if (pressed.isEmpty()) {
				music = State.getMusic();
				if (music.getClipWalking() != null) {
					music.getClipWalking().stop();
					music.getClipWalking().close();
					play = true;
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (State.getInputName().equals("save file"))
			savenameInput(e);
		else if (State.getInputName().equals("usernameUnderscore"))
			usernameInput(e);
		else if (State.getInputName().equals("passwordUnderscore"))
			passwordInput(e);
		else if (State.getInputName().equals("passwordUnderscoreConfirm"))
			confirmPasswordInput(e);
	}

	private void savenameInput(KeyEvent e) {
		char key = e.getKeyChar();
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && saveName.length() != 0) {
			saveName = saveName.substring(0, saveName.length() - 1);
			countSaveName--;
		} else if (countSaveName < 10) {
			if (countSaveName == 0) {
				if (Character.isLetter(key) || Character.isDigit(key)) {
					saveName += key;
					countSaveName++;
				}
			} else if (Character.isSpaceChar(key) || Character.isLetter(key) || Character.isDigit(key)) {
				saveName += key;
				countSaveName++;
			}
		}
	}

	private void usernameInput(KeyEvent e) {
		char key = e.getKeyChar();
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && username.length() != 0) {
			username = username.substring(0, username.length() - 1);
			countUserName--;
		} else if (countUserName < 10) {
			if (countUserName == 0) {
				if (Character.isLetter(key) || Character.isDigit(key)) {
					username += key;
					countUserName++;
				}
			} else if (Character.isSpaceChar(key) || Character.isLetter(key) || Character.isDigit(key)) {
				username += key;
				countUserName++;
			}
		}
	}

	private void passwordInput(KeyEvent e) {
		char key = e.getKeyChar();
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && password.length() != 0) {
			password = password.substring(0, password.length() - 1);
			displayPassword = displayPassword.substring(0, displayPassword.length() - 1);
			countPassword--;
		} else if (countPassword < 10) {
			if (countPassword == 0) {
				if (Character.isLetter(key) || Character.isDigit(key)) {
					password += key;
					displayPassword += "*";
					countPassword++;
				}
			} else if (Character.isSpaceChar(key) || Character.isLetter(key) || Character.isDigit(key)) {
				password += key;
				displayPassword += "*";
				countPassword++;
			}
		}
	}

	private void confirmPasswordInput(KeyEvent e) {
		char key = e.getKeyChar();
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && confirmPassword.length() != 0) {
			confirmPassword = confirmPassword.substring(0, confirmPassword.length() - 1);
			displayConfirmPassword = displayConfirmPassword.substring(0, displayConfirmPassword.length() - 1);
			countConfirmPassword--;
		} else if (countConfirmPassword < 10) {
			if (countConfirmPassword == 0) {
				if (Character.isLetter(key) || Character.isDigit(key)) {
					confirmPassword += key;
					displayConfirmPassword += "*";
					countConfirmPassword++;
				}
			} else if (Character.isSpaceChar(key) || Character.isLetter(key) || Character.isDigit(key)) {
				confirmPassword += key;
				displayConfirmPassword += "*";
				countConfirmPassword++;
			}
		}
	}

	public void resetKeyTyped() {
		setCountSaveName(0);
		setCountUserName(0);
		setCountPassword(0);
		setSaveName("");
		setUsername("");
		setPassword("");
		setDisplayPassword("");
		setDisplayConfirmPassword("");
	}

	public String getSaveName() {
		return saveName + "|";
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getUsername() {
		if (State.getInputName().equals("usernameUnderscore"))
			return username + "|";
		else
			return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayPassword() {
		if (State.getInputName().equals("passwordUnderscore"))
			return displayPassword + "|";
		else
			return displayPassword;
	}

	public void setDisplayPassword(String displayPassword) {
		this.displayPassword = displayPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDisplayConfirmPassword() {
		if (State.getInputName().equals("passwordUnderscoreConfirm"))
			return displayConfirmPassword + "|";
		else
			return displayConfirmPassword;
	}

	public void setDisplayConfirmPassword(String displayConfirmPassword) {
		this.displayConfirmPassword = displayConfirmPassword;
	}

	public int getCountSaveName() {
		return countSaveName;
	}

	public void setCountSaveName(int countSaveName) {
		this.countSaveName = countSaveName;
	}

	public int getCountUserName() {
		return countUserName;
	}

	public void setCountUserName(int countUserName) {
		this.countUserName = countUserName;
	}

	public int getCountPassword() {
		return countPassword;
	}

	public void setCountPassword(int countPassword) {
		this.countPassword = countPassword;
	}

	public int getCountConfirmPassword() {
		return countConfirmPassword;
	}

	public void setCountConfirmPassword(int countConfirmPassword) {
		this.countConfirmPassword = countConfirmPassword;
	}

}
