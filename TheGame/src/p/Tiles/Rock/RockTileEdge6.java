package p.Tiles.Rock;

import p.Grafics.Assets;
import p.Tiles.Tile;

public class RockTileEdge6 extends Tile {

	public RockTileEdge6(int id) {
		super(Assets.rockEdge6, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
