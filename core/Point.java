package core;

public class Point {
	public static final float EPSILON = 5E-4F;
	public static final Point ORIGIN = new Point(0, 0);

	public final float x;
	public final float y;

	public String toString() {
		return "("+Float.toString(x)+", "+Float.toString(y)+")";
	}

	public static boolean close(float a, float b) {
		return Math.abs(a-b)<EPSILON;
	}

	/**
	 * Restrict x to the [0,1] interval.
	 */
	public static float to01(float x) {
		return Math.min(1, Math.max(0, x));
	}

	public boolean equals(Object other) {
		Point p;
		return other instanceof Point &&
			close((p=(Point)other).x, x) && close(p.y, y);
	}

	/**
	 * @return Euclidean distance to the given point
	 */
	public float distance(Point to) {
		float dx=to.x-x, dy=to.y-y;
		return (float)Math.sqrt((dx*dx+dy*dy));
	}

	public float norm() {
		return distance(ORIGIN);
	}

	public Point rotate(Point around, float by) {
		//let's rotate the point in a translated coordinate system
		float rx=x-around.x, ry=y-around.y;
		float x = (float)(rx*Math.cos(by) - ry*Math.sin(by));
		float y = (float)(rx*Math.sin(by) + ry*Math.cos(by));
		//... then undo the previous translation
		return new Point(x+around.x, y+around.y);
	}

	/**
	 * @return vector from p to this
	 */
	public Point from(Point p) {
		return new Point(x-p.x, y-p.y);
	}

	public Point move(Point v) {
		return new Point(x+v.x, y+v.y);
	}

	public float dot(Point vector) {
		return x*vector.x + y*vector.y;
	}

	/**
	 * @return angle in radians between u and v
	 */
	public float angle(Point vector) {
		return (float)Math.acos(dot(vector) / norm() / vector.norm());
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
