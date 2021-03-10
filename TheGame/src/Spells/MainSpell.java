package Spells;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import p.Entities.Entity;
import p.Game.Handler;
import p.Grafics.Animation;
import p.Tiles.Tile;

public class MainSpell {

	private float deltaX, deltaY, angle;
	private double x, y, speed, mouseX, mouseY, xPlayer, yPlayer, widthPlayer, heightPlayer, xCenterPlayer,
			yCenterPlayer;;
	private int tx, ty, radius;
	private Animation image;
	private Handler handler;
	private boolean active = true;
	private Rectangle bounds;
	private AffineTransform at;

	public MainSpell(Handler handler, double x, double y, Animation image) {
		xPlayer = handler.getWorld().getEntityManager().getPlayer().getX();
		yPlayer = handler.getWorld().getEntityManager().getPlayer().getY();
		widthPlayer = handler.getWorld().getEntityManager().getPlayer().getWidth();
		heightPlayer = handler.getWorld().getEntityManager().getPlayer().getHeight();
		xCenterPlayer = xPlayer + (widthPlayer / 2);
		yCenterPlayer = yPlayer + (heightPlayer / 2);

		this.handler = handler;
		this.x = xPlayer;
		this.y = yPlayer;
		this.image = image;

		mouseX = MouseInfo.getPointerInfo().getLocation().getX() + handler.getGameCamera().getxOffset();
		mouseY = MouseInfo.getPointerInfo().getLocation().getY() + handler.getGameCamera().getyOffset();
		deltaX = (float) (mouseX - this.x - (widthPlayer / 2));
		deltaY = (float) (mouseY - this.y - (heightPlayer / 2));
		angle = (float) Math.atan2(deltaY, deltaX);

		radius = 200;
		speed = 4;
	}

	public void tick() {
		spellsDraw();
		move();
		colisionWithEntities();

	}

	private void spellsDraw() {
		if (handler.getWorld().getEntityManager().getPlayer().getPlayerType().equals("wind")) {
			bounds = new Rectangle((int) (x + 5), (int) (y + 5), 20, 20);
			at = AffineTransform.getTranslateInstance(x - 1 - handler.getGameCamera().getxOffset(),
					y + 5 - handler.getGameCamera().getyOffset());
			at.scale(0.8, 0.8);
		} else if (handler.getWorld().getEntityManager().getPlayer().getPlayerType().equals("earth")) {
			bounds = new Rectangle((int) (x + 5), (int) (y + 8), 20, 20);
			at = AffineTransform.getTranslateInstance(x + 2 - handler.getGameCamera().getxOffset(),
					y + 5 - handler.getGameCamera().getyOffset());
			at.scale(0.8, 0.8);
		} else if (handler.getWorld().getEntityManager().getPlayer().getPlayerType().equals("water")) {
			bounds = new Rectangle((int) (x + 6), (int) (y + 8), 20, 20);
			at = AffineTransform.getTranslateInstance(x - 3 - handler.getGameCamera().getxOffset(),
					y + 8 - handler.getGameCamera().getyOffset());
			at.scale(0.8, 0.8);
			float degree = (float) Math.toDegrees(angle - Math.PI);
			at.rotate(Math.toRadians(degree), image.getCurrentFrame().getWidth() / 2,
					image.getCurrentFrame().getHeight() / 2);
		} else if (handler.getWorld().getEntityManager().getPlayer().getPlayerType().equals("shadow")) {
			bounds = new Rectangle((int) (x - 2), (int) (y + 10), 20, 20);
			at = AffineTransform.getTranslateInstance(x - 5 - handler.getGameCamera().getxOffset(),
					y + 10 - handler.getGameCamera().getyOffset());
			at.scale(0.8, 0.8);
			float degree = (float) Math.toDegrees(angle - Math.PI);
			at.rotate(Math.toRadians(degree), image.getCurrentFrame().getWidth() / 2,
					image.getCurrentFrame().getHeight() / 2);
		} else if (handler.getWorld().getEntityManager().getPlayer().getPlayerType().equals("storm")) {
			bounds = new Rectangle((int) (x + 3), (int) (y + 10), 20, 20);
			at = AffineTransform.getTranslateInstance(x - 7 - handler.getGameCamera().getxOffset(),
					y + 8 - handler.getGameCamera().getyOffset());
			at.scale(0.8, 0.8);
			float degree = (float) Math.toDegrees(angle - Math.PI);
			at.rotate(Math.toRadians(degree), image.getCurrentFrame().getWidth() / 2,
					image.getCurrentFrame().getHeight() / 2);
		} else if (handler.getWorld().getEntityManager().getPlayer().getPlayerType().equals("fire")) {
			bounds = new Rectangle((int) (x + 5), (int) (y + 9), 20, 20);
			at = AffineTransform.getTranslateInstance(x - 7 - handler.getGameCamera().getxOffset(),
					y + 6 - handler.getGameCamera().getyOffset());
			at.scale(0.8, 0.8);
			float degree = (float) Math.toDegrees(angle - Math.PI);
			at.rotate(Math.toRadians(degree), image.getCurrentFrame().getWidth() / 2,
					image.getCurrentFrame().getHeight() / 2);
		}
	}

	private void colisionWithEntities() {
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.getCollisionBounds(0f, 0f).intersects(bounds)) {
				if (!e.getName().equals("player"))
					active = false;
				if (e.getName().equals("zombie"))
					e.hurt(5);
			}
		}
	}

	private void move() {
		if (Math.pow(x + (widthPlayer / 2) - xCenterPlayer, 2)
				+ Math.pow(y + (heightPlayer / 2) - yCenterPlayer, 2) < Math.pow(radius, 2)) {
			if (mouseX > x) { // move right
				tx = (int) (bounds.x + bounds.width) / Tile.TILEWIDTH;
				if (!handler.getWorld().getTile(tx, (int) (bounds.y) / Tile.TILEHEIGHT).isSolid() && !handler.getWorld()
						.getTile(tx, (int) (int) (bounds.y + bounds.height) / Tile.TILEHEIGHT).isSolid())
					x += speed * Math.cos(angle);
				else
					active = false;
			} else if (mouseX < x) { // move left
				tx = (int) (bounds.x) / Tile.TILEWIDTH;
				if (!handler.getWorld().getTile(tx, (int) (bounds.y) / Tile.TILEHEIGHT).isSolid() && !handler.getWorld()
						.getTile(tx, (int) (bounds.y + bounds.height) / Tile.TILEHEIGHT).isSolid())
					x += speed * Math.cos(angle);
				else
					active = false;
			}

			if (mouseY > y) { // move down
				ty = (int) (bounds.y + bounds.height) / Tile.TILEHEIGHT;
				if (!handler.getWorld().getTile((int) (bounds.x) / Tile.TILEWIDTH, ty).isSolid()
						&& !handler.getWorld().getTile((int) (bounds.x + bounds.width) / Tile.TILEWIDTH, ty).isSolid())
					y += speed * Math.sin(angle);
				else
					active = false;
			} else if (mouseY < y) { // move up
				ty = (int) (bounds.y) / Tile.TILEHEIGHT;
				if (!handler.getWorld().getTile((int) (bounds.x) / Tile.TILEWIDTH, ty).isSolid()
						&& !handler.getWorld().getTile((int) (bounds.x + bounds.width) / Tile.TILEWIDTH, ty).isSolid())
					y += speed * Math.sin(angle);
				else
					active = false;
			}
			image.move();
		} else
			active = false;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image.getCurrentFrame(), at, null);
		//g.drawRect((int) (x+5), (int) (y), 20, 40);
	}

	// GETTERS AND SETTERS
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Rectangle getCollisionBounds(int width, int height) {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
