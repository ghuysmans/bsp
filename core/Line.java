package core;

/**
 * Simple geometry object: ax+by+c=0
 */
public class Line {
	public final float a, b, c;

	/**
	 * Get the position of p relative to this line.
	 * The return value is used to browse BSP trees.
	 */
	public float position(Point p) {
		return a*p.x + b*p.y + c;
	}

	/**
	 * Compute the intersection between lines using Cramer's method
	 * @return null if there isn't any.
	 */
	public Point intersection(Line s) {
		float delta = a*s.b - s.a*b;
		if (Point.close(delta, 0))
			return null;
		else
			return new Point((s.c*b-c*s.b)/delta, (s.a*c-a*s.c)/delta, true);
	}

	/**
	 * Compute a perpendicular line passing through p.
	 */
	public Line perpendicular(Point p) {
		return new Line(-b, a, b*p.x - a*p.y);
	}

	public String toString() {
		return a+"x + "+b+"y + "+c+" = 0";
	}

	public Line(float a, float b, float c) {
		//ensure segments uniqueness
		this.a = a<0 ? a : -a;
		this.b = a<0 ? b : -b;
		this.c = a<0 ? c : -c;
	}
}
