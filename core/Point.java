package core;

public class Point {
	public static final float EPSILON = 0.000000001F;

	public final float x;
	public final float y;

	public String toString() {
		return "("+Float.toString(x)+", "+Float.toString(y)+")";
	}

	public boolean close(float a, float b) {
		return Math.abs(a-b)<EPSILON;
	}

	public boolean equals(Object other) {
		Point p;
		return other instanceof Point &&
			close((p=(Point)other).x, x) && close(p.y, y);
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
