package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge4 extends Tile {

	public RockTileEdge4(int id) {
		super(Assets.rockEdge4, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
