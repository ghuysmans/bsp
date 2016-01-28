package core;

public class Segment {
	/**
	 * Line coefficients.
	 */
	public final float a, b, c;

	/**
	 * Both ends.
	 */
	public final Point p, q;

	/**
	 * Get the position of p relative to this segment.
	 * The return value is used to browse BSP trees.
	 */
	public float position(Point p) {
		return a*p.x + b*p.y + c;
	}

	/**
	 * Create a segment with a new origin.
	 */
	public Segment from(Point p) {
		return new Segment(p, q);
	}

	/**
	 * Create a segment with a new destination.
	 */
	public Segment to(Point q) {
		return new Segment(p, q);
	}

	/**
	 * Compute the intersection between lines, not segments.
	 * @return null if there isn't any.
	 */
	public Point intersection(Segment s) {
		return null; //FIXME
	}

	public String toString() {
		return p+" -- "+q+": "+a+"x + "+b+"y + "+c+" = 0";
	}

	public Segment(Point p, Point q) {
		this.p = p;
		this.q = q;
		a = p.y - q.y;
		b = q.x - p.x;
		c = -b*p.y - a*p.x;
	}
}
