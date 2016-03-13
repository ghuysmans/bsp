package core;

public class Point {
	public static final float EPSILON = 0.00001F;

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

	public float distance(Point to) {
		float dx=to.x-x, dy=to.y-y;
		return (float)Math.sqrt((dx*dx+dy*dy));
	}

	public Point rotate(Point around, float by) {
		//let's rotate the point in a translated coordinate system
		float rx=x-around.x, ry=y-around.y;
		float x = (float)(rx*Math.cos(by) - ry*Math.sin(by));
		float y = (float)(rx*Math.sin(by) + ry*Math.cos(by));
		//... then undo the previous translation
		return new Point(x+around.x, y+around.y);
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
