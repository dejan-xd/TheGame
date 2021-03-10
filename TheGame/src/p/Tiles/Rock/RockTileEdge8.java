package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge8 extends Tile {

	public RockTileEdge8(int id) {
		super(Assets.rockEdge8, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
