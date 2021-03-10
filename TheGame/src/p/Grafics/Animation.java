package p.Grafics;

import java.awt.image.BufferedImage;

public class Animation {

	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	private boolean singleLoop = false;

	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}

	public void move() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if (timer > speed) {
			index++;
			timer = 0;
			if (index >= frames.length)
				index = 0;
		}
	}

	public void singleLoopTick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if (timer > speed && index != frames.length && !singleLoop) {
			index++;
			timer = 0;
			while (index == frames.length && !singleLoop) {
				singleLoop = true;
				index = 0;
				break;
			}
		}
	}

	public BufferedImage getCurrentFrame() {
		if (index > frames.length)
			index = 0;
		return frames[index];
	}

	public BufferedImage restartCurrentFrame() {
		return frames[0];
	}

	public BufferedImage getLastFrame() {
		return frames[frames.length - 1];
	}
}
