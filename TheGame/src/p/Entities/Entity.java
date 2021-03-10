package p.Entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import p.Game.Handler;

public abstract class Entity {

	protected static final int DEFAULT_HEALTH = 10;
	protected Handler handler;
	protected float x, y, speed, health;
	protected int width, height, id;
	protected boolean active = true, aggro;
	protected String name;
	protected Rectangle bounds;

	public Entity(Handler handler, float x, float y, int width, int height, int id, String name, float speed) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.speed = speed;
		health = DEFAULT_HEALTH;
		bounds = new Rectangle(0, 0, width, height);
		aggro = false;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void die();

	public void hurt(int dmg) {
		health -= dmg;
		if (health <= 0) {
			active = false;
			die();
		}
	}

	public boolean checkEntityCollision(float xOffset, float yOffset) {
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}

	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width,
				bounds.height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
