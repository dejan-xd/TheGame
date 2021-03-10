package p.Grafics;

public class FPS {

	private double timePerUpdate;
	private double delta;
	private long now;
	private long lastTime;

	public void FpsTimer(int fps) {
		timePerUpdate = 1e9 / fps;
		delta = 0;
		lastTime = System.nanoTime();
	}

	public boolean check() {
		now = System.nanoTime();
		delta = (now - lastTime) / timePerUpdate;
		if (delta >= 1) {
			lastTime = now;
			return true;
		} else
			return false;
	}

}
