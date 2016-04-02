package core;
import java.util.List;

/**
 * A painter not aware of depth.
 */
public class Noobie extends Painter {
	protected List<Segment> segments;

	@Override
	public void work(PainterCallback cb) {
		for (Segment s: segments)
			cb.draw(s);
	}

	/**
	 * @param v direction vector
	 * @param p camera position
	 * @param f field of view (strictly positive, in radians)
	 * @param s segment list
	 */
	public Noobie(Point v, Point p, float f, List<Segment> s) {
		super(v, p, f);
		segments = s;
	}

	/**
	 * @param s segment list
	 */
	public Noobie(List<Segment> s) {
		this(new Point(1, 1), Point.ORIGIN, 1, s);
	}
}
