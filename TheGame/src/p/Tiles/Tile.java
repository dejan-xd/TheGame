package p.Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import p.Tiles.Rock.RockTile;
import p.Tiles.Rock.RockTileEdge1;
import p.Tiles.Rock.RockTileEdge2;
import p.Tiles.Rock.RockTileEdge3;
import p.Tiles.Rock.RockTileEdge4;
import p.Tiles.Rock.RockTileEdge5;
import p.Tiles.Rock.RockTileEdge6;
import p.Tiles.Rock.RockTileEdge7;
import p.Tiles.Rock.RockTileEdge8;

public class Tile {

	// Static stuff
	public static Tile[] tiles = new Tile[256];
	public static Tile rock = new RockTile(9);
	public static Tile rockTile1 = new RockTileEdge1(1);
	public static Tile rockEdge2 = new RockTileEdge2(2);
	public static Tile rockEdge3 = new RockTileEdge3(3);
	public static Tile rockEdge4 = new RockTileEdge4(4);
	public static Tile rockEdge5 = new RockTileEdge5(5);
	public static Tile rockEdge6 = new RockTileEdge6(6);
	public static Tile rockEdge7 = new RockTileEdge7(7);
	public static Tile rockEdge8 = new RockTileEdge8(8);

	// Class

	public static final int TILEWIDTH = 50, TILEHEIGHT = 50;

	protected BufferedImage texture;
	protected final int id;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		tiles[id] = this;
	}

	public void tick() {

	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}

	public boolean isSolid() {
		return false;
	}

	public int getId() {
		return id;
	}
}
