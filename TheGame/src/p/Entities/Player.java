package p.Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Spells.MainSpell;
import Spells.SpellManager;
import p.Audio.Music;
import p.Game.Handler;
import p.Grafics.Animation;
import p.Grafics.Assets;
import p.States.State;

public class Player extends Creature {

	private static final int DEFAULT_PLAYER_WIDTH = 30, DEFAULT_PLAYER_HEIGHT = 30, id = 0, DEFAULT_PLAYER_HEALTH = 5,
			DEFAULT_PLAYER_BAR_WIDTH = 50, DEFAULT_PLAYER_MANA = 100;
	private static final float DEFAULT_PLAYER_SPEED = 2.0f;
	private static final String NAME = "player";

	private String stand = "", playerType, pathChestAudio = "source/audio/chest.wav";
	private Animation animationUp, animationDown, animationLeft, animationRight;
	private Animation fire, wind, earth, water, shadow, storm;
	private SpellManager spellManager;
	private Music music;
	private boolean firstLoad = true, firstPress = true;
	private int mana;
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown,
			lastTurn = System.currentTimeMillis(), manaRegenTimer = System.currentTimeMillis();

	public Player(Handler handler, float x, float y, float health) {
		super(handler, x, y, DEFAULT_PLAYER_WIDTH, DEFAULT_PLAYER_HEIGHT, id, NAME, DEFAULT_PLAYER_SPEED);
		this.health = DEFAULT_PLAYER_HEALTH;
		mana = 100;
		bounds.x = 5;
		bounds.y = 15;
		bounds.width = 15;
		bounds.height = 15;
		music = State.getMusic();
		spellManager = new SpellManager(handler);
	}

	// Animations
	private void loadPlayerAsset() {
		switch (State.getCounter()) {
		case 1:
			// energy
			playerType = "wind";
			animationUp = new Animation(100, Assets.wind_up);
			animationDown = new Animation(100, Assets.wind_down);
			animationLeft = new Animation(100, Assets.wind_left);
			animationRight = new Animation(100, Assets.wind_right);
			wind = new Animation(50, Assets.windspell);
			break;
		case 2:
			// rage
			playerType = "earth";
			animationUp = new Animation(100, Assets.earth_up);
			animationDown = new Animation(100, Assets.earth_down);
			animationLeft = new Animation(100, Assets.earth_left);
			animationRight = new Animation(100, Assets.earth_right);
			earth = new Animation(100, Assets.rockspell);
			break;
		case 3:
			// mana
			playerType = "water";
			animationUp = new Animation(100, Assets.water_up);
			animationDown = new Animation(100, Assets.water_down);
			animationLeft = new Animation(100, Assets.water_left);
			animationRight = new Animation(100, Assets.water_right);
			water = new Animation(50, Assets.waterspell);
			break;
		case 4:
			// void
			playerType = "shadow";
			animationUp = new Animation(100, Assets.shadow_up);
			animationDown = new Animation(100, Assets.shadow_down);
			animationLeft = new Animation(100, Assets.shadow_left);
			animationRight = new Animation(100, Assets.shadow_right);
			shadow = new Animation(80, Assets.shadowspell);
			break;
		case 5:
			// energy
			playerType = "storm";
			animationUp = new Animation(100, Assets.storm_up);
			animationDown = new Animation(100, Assets.storm_down);
			animationLeft = new Animation(100, Assets.storm_left);
			animationRight = new Animation(100, Assets.storm_right);
			storm = new Animation(40, Assets.stormspell);
			break;
		case 6:
			// mana
			playerType = "fire";
			animationUp = new Animation(100, Assets.fire_up);
			animationDown = new Animation(100, Assets.fire_down);
			animationLeft = new Animation(100, Assets.fire_left);
			animationRight = new Animation(100, Assets.fire_right);
			fire = new Animation(50, Assets.firespell);
			break;
		case 7:
			// mana
			playerType = "arcane";
			animationUp = new Animation(100, Assets.arcane_up);
			animationDown = new Animation(100, Assets.arcane_down);
			animationLeft = new Animation(100, Assets.arcane_left);
			animationRight = new Animation(100, Assets.arcane_right);
			break;
		case 8:
			// focus
			playerType = "hunter";
			animationUp = new Animation(100, Assets.hunter_up);
			animationDown = new Animation(100, Assets.hunter_down);
			animationLeft = new Animation(100, Assets.hunter_left);
			animationRight = new Animation(100, Assets.hunter_right);
			break;
		case 9:
			// mana
			playerType = "nature";
			animationUp = new Animation(100, Assets.nature_up);
			animationDown = new Animation(100, Assets.nature_down);
			animationLeft = new Animation(100, Assets.nature_left);
			animationRight = new Animation(100, Assets.nature_right);
			break;
		case 10:
			// mana
			playerType = "light";
			animationUp = new Animation(100, Assets.light_up);
			animationDown = new Animation(100, Assets.light_down);
			animationLeft = new Animation(100, Assets.light_left);
			animationRight = new Animation(100, Assets.light_right);
			break;
		}
	}

	@Override
	public void tick() {
		handler.getGameCamera().centerOnEntity(this);
		if (firstLoad) {
			loadPlayerAsset();
			firstLoad = false;
		}
		getInput();
		move();
		checkAttacks();
		manaRegen();
		spellManager.tick();
	}

	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCooldown)
			return;

		Rectangle attackRec = new Rectangle();
		Rectangle collisionRec = getCollisionBounds(0, 0);
		int attackRecSize = 15;
		attackRec.width = attackRecSize;
		attackRec.height = attackRecSize;

		if (handler.getKeyManager().open) {
			if (stand.equals("up")) {
				attackRec.x = collisionRec.x + collisionRec.width / 2 - attackRecSize / 2;
				attackRec.y = collisionRec.y - attackRecSize;
			}
		} else {
			return;
		}

		// attackTimer = 0;
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(attackRec)) {
				for (int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++) {
					if (e.getName().equals("chest")) {
						e.hurt(1);
						music.singleLoopSound(pathChestAudio);
					} else {
						e.hurt(10);
						return;
					}
				}
			}
		}
	}

	private void getInput() {
		xMove = 0;
		yMove = 0;
		if (handler.getKeyManager().up) {
			yMove = -speed;
			animationUp.move();
			stand = "up";
			if (handler.getKeyManager().up && handler.getKeyManager().down) {
				xMove = 0;
				yMove = 0;
				stand = "down";
			}
		}
		if (handler.getKeyManager().down) {
			yMove = speed;
			animationDown.move();
			stand = "down";
			if (handler.getKeyManager().up && handler.getKeyManager().down) {
				xMove = 0;
				yMove = 0;
				stand = "down";
			}
		}
		if (handler.getKeyManager().left) {
			xMove = -speed;
			animationLeft.move();
			stand = "left";
			if (handler.getKeyManager().left && handler.getKeyManager().right) {
				xMove = 0;
				yMove = 0;
				stand = "down";
			}
		}
		if (handler.getKeyManager().right) {
			xMove = speed;
			animationRight.move();
			stand = "right";
			if (handler.getKeyManager().left && handler.getKeyManager().right) {
				xMove = 0;
				yMove = 0;
				stand = "down";
			}
		}
		if (handler.getMouseManager().isLeftPressed()) {
			if (System.currentTimeMillis() - lastTurn >= 1000 || firstPress) {
				firstPress = false;
				lastTurn = System.currentTimeMillis();
				if (mana >= 10) {
					mana -= 10;
					if (playerType.equals("wind"))
						spellManager.addMainSpell(new MainSpell(handler, x, y, wind));
					if (playerType.equals("fire"))
						spellManager.addMainSpell(new MainSpell(handler, x, y, fire));
					if (playerType.equals("earth"))
						spellManager.addMainSpell(new MainSpell(handler, x, y, earth));
					if (playerType.equals("water"))
						spellManager.addMainSpell(new MainSpell(handler, x, y, water));
					if (playerType.equals("shadow"))
						spellManager.addMainSpell(new MainSpell(handler, x, y, shadow));
					if (playerType.equals("storm"))
						spellManager.addMainSpell(new MainSpell(handler, x, y, storm));
				}
			}
		}
	}

	private void manaRegen() {
		if (System.currentTimeMillis() - manaRegenTimer >= 1000) {
			manaRegenTimer = System.currentTimeMillis();
			if (mana != DEFAULT_PLAYER_MANA)
				mana += 5;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimaitionFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		healthBar(g);
		manaBar(g);

	}

	private void healthBar(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset() - 10),
				(int) (y - handler.getGameCamera().getyOffset() - 10), width + 20, 5);

		if ((float) health > (float) DEFAULT_PLAYER_HEALTH * (75.0f / 100.0f)
				&& (float) health <= (float) DEFAULT_PLAYER_HEALTH * (100.0f / 100.0f))
			g.setColor(Color.green);
		else if ((float) health > (float) DEFAULT_PLAYER_HEALTH * (50.0f / 100.0f)
				&& (float) health <= (float) DEFAULT_PLAYER_HEALTH * (75.0f / 100.0f))
			g.setColor(Color.yellow);
		else if ((float) health > (float) DEFAULT_PLAYER_HEALTH * (25.0f / 100.0f)
				&& (float) health <= (float) DEFAULT_PLAYER_HEALTH * (50.0f / 100.0f))
			g.setColor(Color.orange);
		else if ((float) health > (float) DEFAULT_PLAYER_HEALTH * (0.0f / 100.0f)
				&& (float) health <= (float) DEFAULT_PLAYER_HEALTH * (25.0f / 100.0f))
			g.setColor(Color.red);

//		OLD PERCENT = (x - OLD MIN) / (OLD MAX - OLD MIN)
//		NEW X = ((NEW MAX - NEW MIN) * OLD PERCENT) + NEW MIN
		float oldPercentage = (float) health / DEFAULT_PLAYER_HEALTH;
		float newPercentage = DEFAULT_PLAYER_BAR_WIDTH * oldPercentage;
		g.fillRect((int) (x - handler.getGameCamera().getxOffset() - 10),
				(int) (y - handler.getGameCamera().getyOffset() - 10), (int) (newPercentage), 5);

		g.setColor(Color.white);
		g.drawRect((int) (x - handler.getGameCamera().getxOffset() - 10),
				(int) (y - handler.getGameCamera().getyOffset() - 10), width + 20, 5);
		spellManager.render(g);
	}

	private void manaBar(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset() - 10),
				(int) (y - handler.getGameCamera().getyOffset() - 5), width + 20, 4);
		if (State.getCounter() == 1 || State.getCounter() == 5)
			g.setColor(Color.yellow);
		else if (State.getCounter() == 2)
			g.setColor(Color.red);
		else if (State.getCounter() == 4)
			g.setColor(new Color(140, 0, 150));
		else if (State.getCounter() == 8)
			g.setColor(new Color(230, 120, 0));
		else
			g.setColor(Color.blue);
//		OLD PERCENT = (x - OLD MIN) / (OLD MAX - OLD MIN)
//		NEW X = ((NEW MAX - NEW MIN) * OLD PERCENT) + NEW MIN
		float oldPercentage = (float) mana / DEFAULT_PLAYER_MANA;
		float newPercentage = DEFAULT_PLAYER_BAR_WIDTH * oldPercentage;
		g.fillRect((int) (x - handler.getGameCamera().getxOffset() - 10),
				(int) (y - handler.getGameCamera().getyOffset() - 5), (int) (newPercentage), 4);
		g.setColor(Color.white);
		g.drawRect((int) (x - handler.getGameCamera().getxOffset() - 10),
				(int) (y - handler.getGameCamera().getyOffset() - 5), width + 20, 4);
	}

	private BufferedImage getCurrentAnimaitionFrame() {
		if (xMove < 0) {
			return animationLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return animationRight.getCurrentFrame();
		} else if (yMove < 0) {
			return animationUp.getCurrentFrame();
		} else if (yMove > 0) {
			return animationDown.getCurrentFrame();
		} else {
			if (stand.equals("up"))
				return animationUp.restartCurrentFrame();
			if (stand.equals("down"))
				return animationDown.restartCurrentFrame();
			if (stand.equals("left"))
				return animationLeft.restartCurrentFrame();
			if (stand.equals("right"))
				return animationRight.restartCurrentFrame();
			return animationDown.restartCurrentFrame();
		}
	}

	public boolean isDead() {
		if (health <= 0)
			return true;
		else
			return false;
	}

	@Override
	public void die() {
		State.setPlayerDead(true);
		State.setGameState(false);
	}

	public String getPlayerType() {
		return playerType;
	}

	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}

	public SpellManager getSpellManager() {
		return spellManager;
	}

	public void setSpellManager(SpellManager spellManager) {
		this.spellManager = spellManager;
	}

//	INTERAKCIJA SA OBJEKTIMA
//	DOLE-> LEVO-> DESNO
//		else if (handler.getKeyManager().open) {
//			attackRec.x = collisionRec.x + collisionRec.width / 2 - attackRecSize / 2;
//			attackRec.y = collisionRec.y + attackRecSize;
//		} 
//		else if (handler.getKeyManager().open) {
//			attackRec.x = collisionRec.x - attackRecSize;
//			attackRec.y = collisionRec.y + collisionRec.height / 2 - attackRecSize / 2;
//		} 
//		else if (handler.getKeyManager().open) {
//			attackRec.x = collisionRec.x + attackRecSize;
//			attackRec.y = collisionRec.y + collisionRec.height / 2 - attackRecSize / 2;
//		} 
}
