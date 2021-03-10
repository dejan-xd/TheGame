package p.Game;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Launcher {

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Game game = new Game("The Game!", (int) screenSize.getWidth(), (int) screenSize.getHeight());
		game.start();
	}
}