package p.Worlds;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import p.Entities.EntityManager;
import p.Entities.Player;
import p.Entities.Zombie;
import p.Entities.Statics.Chest;
import p.Game.Handler;
import p.Items.ItemManager;
import p.States.State;
import p.Tiles.Tile;
import p.Utils.Utils;

public class World {

	private Handler handler;
	private int width, height, spawnX, spawnY, id;
	private float speed, health;
	private int[][] tiles;
	private String name, objectsPath, worldPath;
	private boolean isFirstLoad = true;

	// Entities
	private EntityManager entityManager;
	// Item
	private ItemManager itemManager;

	public World(Handler handler, String path) {
		this.handler = handler;
		itemManager = new ItemManager(handler);
		worldPath = "source/game/worlds/world.txt";
	}

	public void tick() {
		if (isFirstLoad) {
			isFirstLoad = false;
			loadObjects();
			loadWorld(worldPath);
		}
		itemManager.tick();
		entityManager.tick();
	}

	public void render(Graphics g) {
		// Tiles
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width,
				(handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height,
				(handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		// Item
		itemManager.render(g);

		// Entity
		if (entityManager != null)
			entityManager.render(g);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.rock;
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null)
			return Tile.rock;
		return t;
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		tiles = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 2]);
			}
		}
	}

	private void loadObjects() {
		if (State.getGameType().equals("new game"))
			objectsPath = "source/game/accounts/new game/new_game.txt";
		else if (State.getGameType().equals("load game"))
			objectsPath = State.getLoadGamePath();
		try {
			Scanner scanner = new Scanner(new File(objectsPath));
			String data;
			while (scanner.hasNextLine() && !(data = scanner.nextLine()).isEmpty()) {
				String[] tokens = data.split(" ");
				name = tokens[0];
				width = Utils.parseInt(tokens[1]);
				height = Utils.parseInt(tokens[2]);
				id = Utils.parseInt(tokens[3]);
				speed = Utils.parseFloat(tokens[4]);
				health = Utils.parseFloat(tokens[5]);
				if (name.equals("player")) {
					spawnX = Utils.parseInt(tokens[1]);
					spawnY = Utils.parseInt(tokens[2]);
					entityManager = new EntityManager(handler, new Player(handler, spawnX, spawnY, health));
				}
				if (name.equals("chest"))
					entityManager.addEntity(new Chest(handler, width, height, id));
				if (name.equals("zombie")) {
					entityManager.addEntity(new Zombie(handler, width, height, id, speed, health));
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

}
