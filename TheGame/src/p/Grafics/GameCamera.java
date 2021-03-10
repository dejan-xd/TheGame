package p.Grafics;

import p.Entities.Entity;
import p.Game.Handler;
import p.Tiles.Tile;

public class GameCamera {

	private Handler handler;
	private float xOffset, yOffset;

	int xasd = 0;
	int yasd = 0;

	public GameCamera(Handler handler, float xOffset, float yOffset) {
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	private void checkBlankSpace() {
		if (xOffset <= 0)
			xOffset = 0;
		else if (xOffset >= handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth())
			xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth();

		if (yOffset <= 0)
			yOffset = 0;
		else if (yOffset >= handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight())
			yOffset = handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight();
	}

	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}

	public void move(float xAmat, float yAmt) {
		xOffset += xAmat;
		yOffset += yAmt;
		checkBlankSpace();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}
