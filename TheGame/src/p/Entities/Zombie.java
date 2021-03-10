package p.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import p.Game.Handler;
import p.Grafics.Animation;
import p.Grafics.Assets;
import p.States.State;
import p.Utils.Utils;

public class Zombie extends Creature {

	private static final int DEFAULT_ZOMBIE_WIDTH = 40, DEFAULT_ZOMBIE_HEIGHT = 40, DEFAULT_ZOMBIE_SPEED = 1,
			DEFAULT_ZOMBIE_HEALTH = 30, DEFAULT_ZOMBIE_HEALTH_BAR_WIDTH = 45;
	private static final String NAME = "zombie";

	private Animation animationUp, animationDown, animationLeft, animationRight;
	private int xCenterPlayer, yCenterPlayer, xCenterEnemy, yCenterEnemy, xPlayer, yPlayer, widthPlayer, heightPlayer,
			radius, enemyHit, xCenterEnemyDefault, yCenterEnemyDefault;
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown,
			lastTurn = System.currentTimeMillis();
	private float deltaX, deltaY, angle;
	private boolean runback, wander, firstLoad = true;
	private double randomValue, distance, newX, newY;
	private Rectangle player, enemy;

	public Zombie(Handler handler, float x, float y, int id, float speed, float health) {
		super(handler, x, y, DEFAULT_ZOMBIE_WIDTH, DEFAULT_ZOMBIE_HEIGHT, id, NAME, DEFAULT_ZOMBIE_SPEED);
		this.speed = speed;
		this.id = id;
		this.health = health;
		bounds.x = 8;
		bounds.y = 20;
		bounds.width = 20;
		bounds.height = 20;
		xCenterEnemyDefault = (int) (x + DEFAULT_ZOMBIE_WIDTH / 2);
		yCenterEnemyDefault = (int) (y + DEFAULT_ZOMBIE_HEIGHT / 2);
		radius = 150;
		animationUp = new Animation(300, Assets.enemy_up);
		animationDown = new Animation(300, Assets.enemy_down);
		animationLeft = new Animation(300, Assets.enemy_left);
		animationRight = new Animation(300, Assets.enemy_right);

		if (State.getGameType().equals("load game")) {
			try {
				Scanner scanner = new Scanner(new File(State.getLoadGamePath()));
				String data;
				while (scanner.hasNextLine() && !(data = scanner.nextLine()).isEmpty()) {
					String[] tokens = data.split(" ");
					String name = tokens[0];
					if (name.equals("zombie")) {
						health = (int) Utils.parseFloat(tokens[5]);
					}
				}
				scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void tick() {
		getInput();
		checkIfWander();
		chasePlayer();
		restartCombat();
		enemyWandering();
	}

	private void getInput() {
		xPlayer = (int) handler.getWorld().getEntityManager().getPlayer().getX();
		yPlayer = (int) handler.getWorld().getEntityManager().getPlayer().getY();
		widthPlayer = handler.getWorld().getEntityManager().getPlayer().width;
		heightPlayer = handler.getWorld().getEntityManager().getPlayer().height;

		xCenterPlayer = xPlayer + (widthPlayer / 2);
		yCenterPlayer = yPlayer + (heightPlayer / 2);
		xCenterEnemy = (int) (x + DEFAULT_ZOMBIE_WIDTH / 2);
		yCenterEnemy = (int) (y + DEFAULT_ZOMBIE_HEIGHT / 2);

		player = new Rectangle(xPlayer + 5, yPlayer + 15, widthPlayer - 10, heightPlayer - 15);
		enemy = new Rectangle((int) x + 5, (int) y + 15, DEFAULT_ZOMBIE_WIDTH - 13, DEFAULT_ZOMBIE_HEIGHT - 12);
		enemyHit = 0;
	}

	private void checkIfWander() {
		if (!aggro && !runback)
			wander = true;
		else
			wander = false;
	}

	private void chasePlayer() {
		if (Math.pow(xCenterPlayer - xCenterEnemy, 2) + Math.pow(yCenterPlayer - yCenterEnemy, 2) < Math.pow(radius, 2)
				&& !handler.getWorld().getEntityManager().getPlayer().isDead()) {
			deltaX = xCenterPlayer - xCenterEnemy;
			deltaY = yCenterPlayer - yCenterEnemy;
			angle = (float) Math.atan2(deltaY, deltaX);
			aggro = true;
			if (!player.intersects(enemy)) {
				xMove = 0;
				yMove = 0;
				if (yCenterPlayer < yCenterEnemy) {
					yMove += speed * Math.sin(angle);
					animationUp.move();
				}
				if (yCenterPlayer > yCenterEnemy) {
					yMove += speed * Math.sin(angle);
					animationDown.move();
				}
				if (xCenterPlayer < xCenterEnemy) {
					xMove += speed * Math.cos(angle);
					animationLeft.move();
				}
				if (xCenterPlayer > xCenterEnemy) {
					xMove += speed * Math.cos(angle);
					animationRight.move();
				}
				move();
			} else {
				attackTimer += System.currentTimeMillis() - lastAttackTimer;
				lastAttackTimer = System.currentTimeMillis();
				if (attackTimer < attackCooldown)
					return;
				attackTimer = 0;
				enemyHit = enemyHit + 1;
				handler.getWorld().getEntityManager().getPlayer().hurt(enemyHit);
			}
		} else {
			aggro = false;
		}
	}

	private void restartCombat() {
		if (!aggro && !wander) {
			health = DEFAULT_ZOMBIE_HEALTH;
			aggro = false;
			deltaX = (float) (xCenterEnemyDefault - xCenterEnemy);
			deltaY = (float) (yCenterEnemyDefault - yCenterEnemy);
			angle = (float) Math.atan2(deltaY, deltaX);
			double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
			xMove = (float) (speed / distance * deltaX);
			yMove = (float) (speed / distance * deltaY);
			double newX = xCenterEnemy + xMove;
			double newY = yCenterEnemy + yMove;
			if (distance >= 2) {
				if (newY < yCenterEnemy) {
					yMove += speed * Math.sin(angle);
					animationUp.move();
				}
				if (newY > yCenterEnemy) {
					yMove += speed * Math.sin(angle);
					animationDown.move();
				}
				if (newX < xCenterEnemy) {
					xMove += speed * Math.cos(angle);
					animationLeft.move();
				}
				if (newX > xCenterEnemy) {
					xMove += speed * Math.cos(angle);
					animationRight.move();
				}
				move();
				runback = true;
			} else
				runback = false;
		}
	}

	private void enemyWandering() {
		if (wander) {
			if (System.currentTimeMillis() - lastTurn >= 3000 || firstLoad) {
				Random rand = new Random();
				randomValue = 0.0 + (1.0 - 0.0) * rand.nextDouble();
				lastTurn = System.currentTimeMillis();
				firstLoad = false;
			}

			double alpha = 2 * Math.PI * randomValue;
			double r = 50 * Math.sqrt(randomValue);
			int x1 = (int) (r * Math.cos(alpha) + xCenterEnemyDefault);
			int y1 = (int) (r * Math.sin(alpha) + yCenterEnemyDefault);
			deltaX = (float) (x1 - xCenterEnemy);
			deltaY = (float) (y1 - yCenterEnemy);
			angle = (float) Math.atan2(deltaY, deltaX);

			distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
			xMove = (float) (speed / 10 / distance * deltaX);
			yMove = (float) (speed / 10 / distance * deltaY);

			newX = xCenterEnemy + xMove;
			newY = yCenterEnemy + yMove;
			if (distance >= 2.0) {
				if (newY < yCenterEnemy) {
					yMove += speed / 10 * Math.sin(angle);
					animationUp.move();
				}
				if (newY > yCenterEnemy) {
					yMove += speed / 10 * Math.sin(angle);
					animationDown.move();
				}
				if (newX < xCenterEnemy) {
					xMove += speed / 10 * Math.cos(angle);
					animationLeft.move();
				}
				if (newX > xCenterEnemy) {
					xMove += speed / 10 * Math.cos(angle);
					animationRight.move();
				}
				move();
				wander = true;
			} else
				wander = false;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimaitionFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		healthBar(g);
	}

	private void healthBar(Graphics g) {
		for (int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++) {
			if (handler.getWorld().getEntityManager().getEntities().get(i).getName().equals("zombie")) {

				g.setColor(Color.gray);
				g.fillRect((int) (x - handler.getGameCamera().getxOffset() - 5),
						(int) (y - handler.getGameCamera().getyOffset() - 10), width + 5, 5);

				if ((float) health > (float) DEFAULT_ZOMBIE_HEALTH * (75.0f / 100.0f)
						&& (float) health <= (float) DEFAULT_ZOMBIE_HEALTH * (100.0f / 100.0f))
					g.setColor(Color.green);
				else if ((float) health > (float) DEFAULT_ZOMBIE_HEALTH * (50.0f / 100.0f)
						&& (float) health <= (float) DEFAULT_ZOMBIE_HEALTH * (75.0f / 100.0f))
					g.setColor(Color.yellow);
				else if ((float) health > (float) DEFAULT_ZOMBIE_HEALTH * (25.0f / 100.0f)
						&& (float) health <= (float) DEFAULT_ZOMBIE_HEALTH * (50.0f / 100.0f))
					g.setColor(Color.orange);
				else if ((float) health > (float) DEFAULT_ZOMBIE_HEALTH * (0.0f / 100.0f)
						&& (float) health <= (float) DEFAULT_ZOMBIE_HEALTH * (25.0f / 100.0f))
					g.setColor(Color.red);

				// OLD PERCENT = (x - OLD MIN) / (OLD MAX - OLD MIN)
				// NEW X = ((NEW MAX - NEW MIN) * OLD PERCENT) + NEW MIN
				float oldPercentage = (float) health / DEFAULT_ZOMBIE_HEALTH;
				float newPercentage = DEFAULT_ZOMBIE_HEALTH_BAR_WIDTH * oldPercentage;
				g.fillRect((int) (x - handler.getGameCamera().getxOffset() - 5),
						(int) (y - handler.getGameCamera().getyOffset() - 10), (int) (newPercentage), 5);

				if (!aggro)
					g.setColor(Color.white);
				else
					g.setColor(Color.red);
				g.drawRect((int) (x - handler.getGameCamera().getxOffset() - 5),
						(int) (y - handler.getGameCamera().getyOffset() - 10), width + 5, 5);
			}
		}
	}

	private BufferedImage getCurrentAnimaitionFrame() {
		double angleDouble = -1 * angle;
		double degrees = Math.toDegrees(angleDouble);
		double angleRound = Math.round(degrees * 100.0) / 100.0;

		if (firstLoad)
			return animationDown.getCurrentFrame();
		else if (angleRound >= 45 && angleRound <= 135)
			return animationUp.getCurrentFrame();
		else if (angleRound >= 135 && angleRound <= 180)
			return animationLeft.getCurrentFrame();
		else if (angleRound >= -180 && angleRound <= -135)
			return animationLeft.getCurrentFrame();
		else if (angleRound >= -135 && angleRound <= -45)
			return animationDown.getCurrentFrame();
		else if (angleRound >= -45 && angleRound <= 0)
			return animationRight.getCurrentFrame();
		else if (angleRound >= 0 && angleRound <= 45)
			return animationRight.getCurrentFrame();
		return animationDown.restartCurrentFrame();

//		if (firstLoad)
//			return animationDown.getCurrentFrame();
//		else if (angleDouble >= Math.PI / 4 && angleDouble < ((3 * Math.PI) / 4))
//			return animationUp.getCurrentFrame();
//		else if (angleDouble >= ((3 * Math.PI) / 4) && angleDouble <= Math.PI)
//			return animationLeft.getCurrentFrame();
//		else if (angleDouble >= -1 * (Math.PI) && angleDouble < -1 * ((3 * Math.PI) / 4))
//			return animationLeft.getCurrentFrame();
//		else if (angleDouble >= -1 * ((3 * Math.PI) / 4) && angleDouble < -1 * (Math.PI / 4))
//			return animationDown.getCurrentFrame();
//		else if (angleDouble >= -1 * (Math.PI / 4) && angleDouble <= 0.0)
//			return animationRight.getCurrentFrame();
//		else if (angleDouble >= 0.0 && angleDouble < Math.PI / 4)
//			return animationRight.getCurrentFrame();
//		return animationDown.restartCurrentFrame();
	}

	@Override
	public void die() {
//		String file = Utils.loadFileAsString("source/game/accounts/new game/new_game.txt");
//		String[] tokens = file.split("\n");
//		for (int i = 0; i < tokens.length; i++) {
//			String[] a = tokens[i].split("\\s+");
//			if (a[0].equals("zombie") && a[3].equals("" + id)) {
//				Utils.deleteRowFromFile("source/game/accounts/new game/new_game.txt", "source/game/objects/new_gameTemp.txt",
//						tokens[i]);
//				System.out.println("Ubio si zombija!");
//			}
//
//		}
	}
}
