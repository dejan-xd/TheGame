package p.Game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import p.Audio.Music;
import p.Grafics.Assets;
import p.Grafics.FPS;
import p.Grafics.GameCamera;
import p.Input.KeyManager;
import p.Input.MouseManager;
import p.States.GameState;
import p.States.LoadGameState;
import p.States.LogInState;
import p.States.MenuState;
import p.States.NewGameState;
import p.States.PauseState;
import p.States.RegisterState;
import p.States.SettingsState;
import p.States.StartGameState;
import p.States.State;
import p.UI.UIManager;
import p.Windows.Display;

public class Game implements Runnable {

	private static final int FPS = 60;
	private Display display;
	private int width, height;
	private boolean running = false;
	private Thread thread;
	private BufferStrategy bs;
	private Graphics g;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private GameCamera gameCamera;
	private Handler handler;

	public UIManager uiManager;
	public String title;
	public boolean pause = false;
	public State gameState, menuState, settingsState, startGameState, newGame, pauseState, newGameState, loadGameState,
			logInState, registerState;
	private Music music;
	private CreateSource creatSource;

	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		creatSource = new CreateSource();
	}

	private void init() {
		creatSource.create();
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		uiManager = new UIManager(handler);
		handler.getGame().getMouseManager().setUIManager(uiManager);
		music = new Music();
		State.setMusic(music);

		logInState = new LogInState(handler);
		registerState = new RegisterState(handler);
		menuState = new MenuState(handler);
		settingsState = new SettingsState(handler);
		startGameState = new StartGameState(handler);
		newGame = new NewGameState(handler);
		gameState = new GameState(handler);
		newGameState = new NewGameState(handler);
		loadGameState = new LoadGameState(handler);
		pauseState = new PauseState(handler);
		State.setState(logInState);
	}


	private void tick() {
		keyManager.tick();
		if (State.getState() != null && !pause) {
			State.getState().tick();
		} else if (!State.getState().equals(State.getResumeGameState())) {
			State.getState().tick();
		}
		if (State.isGameState())
			music.stopBackgroundSound();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// brisanje ekrana
		g.clearRect(0, 0, width, height);
		// poèetak crtanja
		if (State.getState() != null) {
			State.getState().render(g);
		}
		// kraj crtanja
		bs.show();
		g.dispose();
	}

	public void run() {
		init();
		FPS timer = new FPS();
		timer.FpsTimer(FPS);
		while (running) {
			if (timer.check()) {
				tick();
				render();
			}
		}
		stop();
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		pause = true;
	}

	public void resume() {
		pause = false;
	}
}
