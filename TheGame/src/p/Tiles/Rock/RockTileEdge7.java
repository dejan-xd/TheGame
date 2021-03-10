package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge7 extends Tile {

	public RockTileEdge7(int id) {
		super(Assets.rockEdge7, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
