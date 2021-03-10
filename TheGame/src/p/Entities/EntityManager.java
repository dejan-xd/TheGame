package p.Entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import p.Game.Handler;

public class EntityManager {

	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private Comparator<Entity> entityRenderOrder = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}
	};
	private Comparator<Entity> entitySaveOrder = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if (a.equals(b))
				return 0;
			if (a.name.equals("player"))
				return -1;
			if (b.name.equals("player"))
				return 1;
			int nameCompare = a.name.compareTo(b.name);
			if (nameCompare != 0) {
				return nameCompare;
			}
			Integer ida = ((Entity) a).getId();
			Integer idb = ((Entity) b).getId();
			return ida.compareTo(idb);

		}
	};

	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
	}

	public void tick() {
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			e.tick();
			if (!e.isActive())
				it.remove();
		}
		entities.sort(entityRenderOrder);
	}

	public void render(Graphics g) {
		for (Entity e : entities) {
			e.render(g);
		}
	}

	public void sortEntitiesByNameAndID() {
		entities.sort(entitySaveOrder);
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	// GETTERS AND SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}
