package p.Entities.Statics;

import p.Entities.Entity;
import p.Game.Handler;

public abstract class StaticEntity extends Entity {

	public StaticEntity(Handler handler, float x, float y, int width, int height, int id, String name, float speed) {
		super(handler, x, y, width, height, id, name, speed);
	}

}
