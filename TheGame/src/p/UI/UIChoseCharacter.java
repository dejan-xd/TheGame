package p.UI;

import java.awt.Graphics;

import p.Game.Handler;
import p.Grafics.Assets;
import p.States.State;

public class UIChoseCharacter extends UIObject {

	public UIChoseCharacter(Handler handler, float x, float y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		switch (State.getCounter()) {
		case 1:
			g.drawImage(Assets.wind_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 2:
			g.drawImage(Assets.earth_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 3:
			g.drawImage(Assets.water_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 4:
			g.drawImage(Assets.shadow_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 5:
			g.drawImage(Assets.storm_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 6:
			g.drawImage(Assets.fire_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 7:
			g.drawImage(Assets.arcane_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 8:
			g.drawImage(Assets.hunter_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 9:
			g.drawImage(Assets.nature_down[0], (int) (x), (int) (y), width, height, null);
			break;
		case 10:
			g.drawImage(Assets.light_down[0], (int) (x), (int) (y), width, height, null);
			break;
		}
	}

	@Override
	public void onClick() {

	}

}
