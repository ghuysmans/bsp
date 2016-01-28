package core;

public class Point {
	public final float x;
	public final float y;

	public String toString() {
		return "("+Float.toString(x)+", "+Float.toString(y)+")";
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
