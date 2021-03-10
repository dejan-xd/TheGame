package p.UI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import p.Game.Handler;

public class UIManager {

	private Handler handler;
	private ArrayList<UIObject> objects, loadGameObjects;

	public UIManager(Handler handler) {
		this.handler = handler;
		objects = new ArrayList<UIObject>();
		loadGameObjects = new ArrayList<UIObject>();
	}

	public void tick() {
// 		ConcurrentModificationException exception
//		for (UIObject o : objects)
//			o.tick();
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).tick();
		}
		for (int i = 0; i < loadGameObjects.size(); i++) {
			loadGameObjects.get(i).tick();
		}
	}

	public void render(Graphics g) {
// 		ConcurrentModificationException exception
//		for (UIObject o : objects)
//			o.render(g);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).render(g);
		}
		for (int i = 0; i < loadGameObjects.size(); i++) {
			loadGameObjects.get(i).render(g);
		}
	}

	public void onMouseMove(MouseEvent e) {
// 		ConcurrentModificationException exception
//		for (UIObject o : objects)
//			o.onMouseMove(e);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).onMouseMove(e);
		}
		for (int i = 0; i < loadGameObjects.size(); i++) {
			loadGameObjects.get(i).onMouseMove(e);
		}
	}

	public void onMouseRelease(MouseEvent e) {
// 		ConcurrentModificationException exception
//		for (UIObject o : objects)
//			o.onMouseRelease(e);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).onMouseRelease(e);
		}
		for (int i = 0; i < loadGameObjects.size(); i++) {
			loadGameObjects.get(i).onMouseRelease(e);
		}
	}

	public UIObject addObject(UIObject o) {
		objects.add(o);
		return o;
	}

	public void removeObject(UIObject o) {
		objects.remove(o);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

	public UIObject addLoadGameObject(UIObject o) {
		loadGameObjects.add(o);
		return o;
	}

	public ArrayList<UIObject> getLoadGameObjects() {
		return loadGameObjects;
	}

	public void setLoadGameObjects(ArrayList<UIObject> loadGameObjects) {
		this.loadGameObjects = loadGameObjects;
	}
}
