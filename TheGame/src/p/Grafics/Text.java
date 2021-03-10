package p.Grafics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {

	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color color, Font font) {
		g.setColor(color);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if (center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
//			System.out.println(text +" " +fm.stringWidth(text));
//			System.out.println((fm.getHeight() / 2) + fm.getAscent());
		}
//		System.out.println(text +" " +g.getFontMetrics(font).stringWidth(text));
		g.drawString(text, x, y);

	}
}
