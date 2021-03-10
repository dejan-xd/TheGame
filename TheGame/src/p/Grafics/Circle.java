package p.Grafics;

import java.awt.Point;

public class Circle {

    private int radius;
    private Point point;

    public Circle() {
        setPoint(new Point(0, 0));
        radius = 1;
    }

    public Circle(int x, int y, int r) {
        setPoint(new Point(x, y));
        radius = r;
    }

    public int getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
}