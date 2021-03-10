package p.Entities.Statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import p.Game.Handler;
import p.Grafics.Animation;
import p.Grafics.Assets;
//import p.Items.Item;

public class Chest extends StaticEntity {

	private static final int DEFAULT_CHEST_WIDTH = 40, DEFAULT_CHEST_HEIGHT = 40;
	private static final float DEFAULT_CHEST_SPEED = 0.0f;
	private static final String NAME = "chest";

	private Animation animationChest;

	public Chest(Handler handler, float x, float y, int id) {
		super(handler, x, y, DEFAULT_CHEST_WIDTH, DEFAULT_CHEST_HEIGHT, id, NAME, DEFAULT_CHEST_SPEED);
		this.id = id;
		bounds.x = 3;
		bounds.y = 19;
		bounds.width = 32;
		bounds.height = 12;
		health = 10;
		animationChest = new Animation(100, Assets.chest);
	}

	@Override
	public void tick() {
		chestOpened();
	}

	@Override
	public void die() {
//		handler.getWorld().getItemManager().addItem(Item.swordItem.createNew((int) x, (int) y));
	}

	public void chestOpened() {
		if (health <= 9) {
			animationChest.singleLoopTick();
			if (animationChest.getCurrentFrame() == animationChest.restartCurrentFrame()) {
				active = false;
				health = 0;
				die();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimaitionFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	private BufferedImage getCurrentAnimaitionFrame() {
		return animationChest.getCurrentFrame();
	}

}
