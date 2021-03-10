package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge5 extends Tile {

	public RockTileEdge5(int id) {
		super(Assets.rockEdge5, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
