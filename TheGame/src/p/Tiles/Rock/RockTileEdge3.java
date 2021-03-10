package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge3 extends Tile {

	public RockTileEdge3(int id) {
		super(Assets.rockEdge3, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
