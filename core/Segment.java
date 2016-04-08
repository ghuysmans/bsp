package core;
import java.awt.Color;

/**
 * Simple geometry object: ax+by+c=0 between p and q
 */
public class Segment extends Line {
	public final Point p, q;
	public final Color color;

	/**
	 * Compute whether r lies in the associated min-max rectangle
	 */
	public boolean contains(Point r) {
		float x1 = Math.min(p.x, q.x) - Point.EPSILON;
		float x2 = Math.max(p.x, q.x) + Point.EPSILON;
		float y1 = Math.min(p.y, q.y) - Point.EPSILON;
		float y2 = Math.max(p.y, q.y) + Point.EPSILON;
		return x1<=r.x && r.x<=x2 && y1<=r.y && r.y<=y2;
	}

	/**
	 * Compute intersection between a Segment and a Line
	 * Beware, it isn't implemented in Line hence not symmetric:
	 * l.intersection(s) won't return null when the intersection lies outside s.
	 */
	public Point intersection(Line l) {
		Point i = super.intersection(l);
		if (i==null || !contains(i))
			return null;
		else
			return i;
	}

	/**
	 * Compute intersection between two segments
	 */
	public Point intersection(Segment s) {
		Point i = intersection((Line)s);
		if (i==null || !s.contains(i))
			return null;
		else
			return i;
	}

	/**
	 * Create a segment with a new destination.
	 */
	public Segment to(Point q) {
		return new Segment(p, q, color);
	}

	/**
	 * Create a segment with a new origin.
	 */
	public Segment from(Point p) {
		return new Segment(p, q, color);
	}

	public String toString() {
		return p+" -- "+q+": "+super.toString();
	}

	public boolean equals(Object other) {
		Segment s;
		return other instanceof Segment && (
			((s=(Segment)other).p.equals(p) && s.q.equals(q)) ||
			(s.q.equals(p) && s.p.equals(q)));
	}

	/**
	 * Constructs a Segment ensuring that Segment(p,q)==Segment(q,p).
	 */
	public Segment(Point p, Point q, Color color) {
		super(p.y-q.y, q.x-p.x, -(q.x-p.x)*p.y - (p.y-q.y)*p.x);
		if (p.equals(q))
			throw new InvalidSegmentException("couldn't create [p,q] | p=q="+p);
		else {
			this.p = p;
			this.q = q;
			this.color = color;
		}
	}

	/**
	 * Parse a Segment description from a {@link Scene} file
	 */
	public static Segment load(String s) throws FormatException {
		String[] data = s.split(" ");
		Point p=new Point(Scene.getFloat(data[0]), Scene.getFloat(data[1]));
		Point q=new Point(Scene.getFloat(data[2]), Scene.getFloat(data[3]));
		return new Segment(p, q, Scene.getColor(data[4]));
	}
}
