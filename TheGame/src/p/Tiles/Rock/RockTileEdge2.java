package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge2 extends Tile {

	public RockTileEdge2(int id) {
		super(Assets.rockEdge2, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
