package core;
import java.awt.Color;

/**
 * A segment is something more precise than a line,
 * not mathematically a line... (just to be clear)
 */
public class Segment extends Line {
	public final Point p, q;
	public final Color color;

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

	/**
	 * Compute the intersection between lines, not segments.
	 * @return null if there isn't any.
	 */
	public Point intersection(Segment s) {
		float d1 = b*s.a - a*s.b;
		if (Point.close(d1, 0) || Point.close(s.a, 0))
			return null;
		else {
			float y = (-c*s.a+a*s.c)/d1;
			return new Point((-s.c-s.b*y)/s.a, y);
		}
	}

	public String toString() {
		return p+" -- "+q+": "+a+"x + "+b+"y + "+c+" = 0";
	}

	public boolean equals(Object other) {
		Segment s;
		return other instanceof Segment &&
			(s=(Segment)other).p.equals(p) && s.q.equals(q);
	}

	/**
	 * Constructs a Segment ensuring that Segment(p,q)==Segment(q,p).
	 */
	public Segment(Point p, Point q, Color color) {
		super(p.y-q.y, q.x-p.x, -(q.x-p.x)*p.y - (p.y-q.y)*p.x);
		this.p = p;
		this.q = q;
		this.color = color;
	}

	public static Segment load(String s) throws FormatException {
		String[] data = s.split(" ");
		Point p=new Point(Scene.getFloat(data[0]), Scene.getFloat(data[1]));
		Point q=new Point(Scene.getFloat(data[2]), Scene.getFloat(data[3]));
		return new Segment(p, q, Scene.getColor(data[4]));
	}
}
