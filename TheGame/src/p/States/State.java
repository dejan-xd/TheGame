package p.States;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import p.Audio.Music;
import p.Game.Handler;

public abstract class State {

	private static State currentState = null, resumeGameState;
	private static boolean backToLogIn, playWalkingsound, gameState, loadGameState, pauseSettings, focusable, saved,
			repaint, playerDead;
	private static Music music = null;
	private static String volumeOption = "", musicOption = "", difficultyOption = "", gameType = "", inputName = "",
			loadGameTxtName = "", loadGamePath = "", username = "";
	private static int counter = 1, loadGameCounter = 0, page = 0;

	public static boolean isPlayerDead() {
		return playerDead;
	}

	public static void setPlayerDead(boolean playerDead) {
		State.playerDead = playerDead;
	}

	public static String getUsername() {
		if (!username.equals(""))
			return username;
		else
			return "new game";
	}

	public static void setUsername(String username) {
		State.username = username;
	}

	public static String getInputName() {
		return inputName;
	}

	public static void setInputName(String inputName) {
		State.inputName = inputName;
	}

	public static boolean isRepaint() {
		return repaint;
	}

	public static void setRepaint(boolean repaint) {
		State.repaint = repaint;
	}

	public static boolean isLoadGameState() {
		return loadGameState;
	}

	public static void setLoadGameState(boolean loadGameState) {
		State.loadGameState = loadGameState;
	}

	public static String getLoadGamePath() {
		return loadGamePath;
	}

	public static void setLoadGamePath(String loadGamePath) {
		State.loadGamePath = loadGamePath;
	}

	public static String getLoadGameTxtName() {
		return loadGameTxtName;
	}

	public static void setLoadGameTxtName(String loadGameTxtName) {
		State.loadGameTxtName = loadGameTxtName;
	}

	public static State getResumeGameState() {
		return resumeGameState;
	}

	public static void setResumeGameState(State resumeGameState) {
		State.resumeGameState = resumeGameState;
	}

	public static boolean isSaved() {
		return saved;
	}

	public static void setSaved(boolean saved) {
		State.saved = saved;
	}

	public static boolean isFocusable() {
		return focusable;
	}

	public static void setFocusable(boolean focusable) {
		State.focusable = focusable;
	}

	public static String getGameType() {
		return gameType;
	}

	public static void setGameType(String gameType) {
		State.gameType = gameType;
	}

	public static boolean isPauseSettings() {
		return pauseSettings;
	}

	public static void setPauseSettings(boolean pauseSettings) {
		State.pauseSettings = pauseSettings;
	}

	public static boolean isGameState() {
		return gameState;
	}

	public static void setGameState(boolean gameState) {
		State.gameState = gameState;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		State.counter = counter;
	}

	public static int getLoadGameCounter() {
		return loadGameCounter;
	}

	public static void setLoadGameCounter(int loadGameCounter) {
		State.loadGameCounter = loadGameCounter;
	}

	public static int getPage() {
		return page;
	}

	public static void setPage(int page) {
		State.page = page;
	}

	public static boolean isPlayWalkingsound() {
		return playWalkingsound;
	}

	public static void setPlayWalkingsound(boolean playWalkingsound) {
		State.playWalkingsound = playWalkingsound;
	}

	public static boolean isBackToLogIn() {
		return backToLogIn;
	}

	public static void setBackToLogIn(boolean backToLogIn) {
		State.backToLogIn = backToLogIn;
	}

	public static State getState() {
		return currentState;
	}

	public static void setState(State state) {
		State.currentState = state;
	}

	public static Music getMusic() {
		return music;
	}

	public static void setMusic(Music music) {
		State.music = music;
	}

	public static String getVolumeOption() {
		return volumeOption;
	}

	public static void setVolumeOption(String volumeOption) {
		State.volumeOption = volumeOption;
	}

	public static String getMusicOption() {
		return musicOption;
	}

	public static void setMusicOption(String musicOption) {
		State.musicOption = musicOption;
	}

	public static String getDifficultyOption() {
		return difficultyOption;
	}

	public static void setDifficultyOption(String difficultyOption) {
		State.difficultyOption = difficultyOption;
	}

	// CLASS
	protected Handler handler;

	public State(Handler handler) {
		this.handler = handler;
		new ArrayList<Clip>();
	}

	public abstract void tick();

	public abstract void render(Graphics g);

}
