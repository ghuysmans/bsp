package core;

public class Point {
	public static final float EPSILON = 0.00001F;
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

	public float dot(Point vector) {
		return x*vector.x + y*vector.y;
	}

	/**
	 * @return angle in radians between u and v
	 */
	public float angle(Point vector) {
		return (float)Math.acos(dot(vector) / norm() / vector.norm());
	}

	/**
	 * Projects p on a unit segment.
	 * @param v direction vector
	 * @param pov point of view
	 * @param fov field of view (strictly positive, in radians)
	 * @return position on a [0,1] segment or infty when too far on the L/R.
	 */
	public float project(Point v, Point pov, float fov) {
		return 1 - projectInv(v, pov, fov);
	}

	private float projectInv(Point v, Point pov, float fov) {
		Point u = v.rotate(Point.ORIGIN, fov/2); //upper vector
		Point d = from(pov); //distance vector
		float tu=d.angle(u), tv=d.angle(v);
		if (tv <= fov/2)
			return tu/fov;
		else if (close(tv-tu, fov/2))
			return Float.NEGATIVE_INFINITY;
		else
			return Float.POSITIVE_INFINITY;
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
