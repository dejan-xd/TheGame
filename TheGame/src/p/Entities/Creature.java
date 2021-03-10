package p.Entities;

import p.Game.Handler;
import p.Tiles.Tile;

public abstract class Creature extends Entity {
	protected float xMove, yMove;

	public Creature(Handler handler, float x, float y, int width, int height, int id, String name, float speed) {
		super(handler, x, y, width, height, id, name, speed);
		xMove = 0;
		yMove = 0;
	}

	public void move() {
		if (!checkEntityCollision(xMove, 0f))
			moveX();
		if (!checkEntityCollision(0f, yMove))
			moveY();
	}

	// left and right collision, x collision
	public void moveX() {
		if (xMove > 0) { // moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}
		} else if (xMove < 0) { // moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}
		}
	}

	// up and down collision, y collision
	public void moveY() {
		if (yMove < 0) { // moving up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
		} else if (yMove > 0) { // moving down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}

	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}

	// GETTERS AND SETTERS
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}
}
