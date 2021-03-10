package Spells;

import java.awt.Graphics;
import java.util.LinkedList;

import p.Game.Handler;

public class SpellManager {

	private LinkedList<MainSpell> linkedList = new LinkedList<MainSpell>();
	private MainSpell mainSpell;
	public Handler handler;

	public SpellManager(Handler handler) {
		this.handler = handler;
	}

	public void tick() {
		for (int i = 0; i < linkedList.size(); i++) {
			mainSpell = linkedList.get(i);
			if (!mainSpell.isActive())
				removeMainSpell(mainSpell);
			mainSpell.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < linkedList.size(); i++) {
			mainSpell = linkedList.get(i);
			mainSpell.render(g);
		}
	}

	public void addMainSpell(MainSpell mainspell) {
		linkedList.add(mainspell);
	}

	public void removeMainSpell(MainSpell mainspell) {
		linkedList.remove(mainspell);
	}

	public LinkedList<MainSpell> getLinkedList() {
		return linkedList;
	}

	public void setLinkedList(LinkedList<MainSpell> linkedList) {
		this.linkedList = linkedList;
	}
}
