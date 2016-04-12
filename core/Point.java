package core;

/**
 * 2D point or vector
 */
public class Point {
	public static final float EPSILON = 9E-3F;
	public static final Point ORIGIN = new Point(0, 0);

	public final float x;
	public final float y;

	/**
	 * Whether it's a computed intersection
	 */
	public final boolean intersection;

	public String toString() {
		return "("+Float.toString(x)+", "+Float.toString(y)+")";
	}

	/**
	 * Determine whether a and b are almost equal.
	 * @return true iif almost equal
	 */
	public static boolean close(float a, float b) {
		return Math.abs(a-b)<EPSILON;
	}

	/**
	 * Restrict x to the [0,1] interval.
	 * @return [0,1]
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
	 * Compute the Euclidean distance to the given point
	 * @return Euclidean distance to the given point
	 */
	public float distance(Point to) {
		float dx=to.x-x, dy=to.y-y;
		return (float)Math.sqrt((dx*dx+dy*dy));
	}

	/**
	 * Compute the L^2-Norm of a vector (or the distance to the origin)
	 * @return L^2-Norm
	 */
	public float norm() {
		return distance(ORIGIN);
	}

	/**
	 * Rotate the point using a rotation matrix
	 * @param around rotation center
	 * @param by angle in radians
	 * @return new Point
	 */
	public Point rotate(Point around, float by) {
		//let's rotate the point in a translated coordinate system
		float rx=x-around.x, ry=y-around.y;
		float x = (float)(rx*Math.cos(by) - ry*Math.sin(by));
		float y = (float)(rx*Math.sin(by) + ry*Math.cos(by));
		//... then undo the previous translation
		return new Point(x+around.x, y+around.y);
	}

	/**
	 * Compute the vector from p to this
	 * @param p other Point
	 * @return vector difference
	 */
	public Point from(Point p) {
		return new Point(x-p.x, y-p.y);
	}

	/**
	 * Move a point by adding vectors
	 * @param v vector
	 * @return new Point
	 */
	public Point move(Point v) {
		return new Point(x+v.x, y+v.y);
	}

	/**
	 * Compute the dot product between two vectors
	 * @param vector other vector
	 * @return dot product
	 */
	public float dot(Point vector) {
		return x*vector.x + y*vector.y;
	}

	/**
	 * Compute the smallest positive angle between two vectors
	 * @param vector other vector
	 * @return angle in radians
	 */
	public float angle(Point vector) {
		return (float)Math.acos(dot(vector) / norm() / vector.norm());
	}

	public Point(float x, float y) {
		this(x, y, false);
	}

	Point(float x, float y, boolean inter) {
		this.x = x;
		this.y = y;
		intersection = inter;
	}
}
