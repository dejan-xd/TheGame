package p.Items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import p.Audio.Music;
import p.Game.Handler;
import p.States.State;

public class ItemManager {

	private Handler handler;
	private ArrayList<Item> items;
	private Music music;
	String pathSwordPickUpAudio;

	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();
		music = State.getMusic();
		pathSwordPickUpAudio = "source/audio/sword_pick_up.wav";
	}

	public void tick() {
		Iterator<Item> it = items.iterator();
		while (it.hasNext()) {
			Item i = it.next();
			i.tick();
			if (i.pickedUp) {
				pickUpAudio();
				it.remove();
			}
		}
	}

	public void render(Graphics g) {
		for (Item i : items)
			i.render(g);
	}

	public void addItem(Item i) {
		i.setHandler(handler);
		items.add(i);
	}

	public void pickUpAudio() {
		for (Item i : items) {
			if (i.getId() == 5)
				music.singleLoopSound(pathSwordPickUpAudio);
		}
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
