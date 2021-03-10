package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge1 extends Tile {
	
	public RockTileEdge1(int id) {
		super(Assets.rockEdge1, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
