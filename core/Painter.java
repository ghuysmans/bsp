package core;

/**
 * Abstract object able to draw a scene
 */
public abstract class Painter {
	protected final Point v; //< direction vector
	protected final Point pov; //< point of view
	protected final float fov; //< field of view
	protected final Line ppd; //< perpendicular line
	protected final Point u; //< upper vector
	protected final int at; //< region of a point in the direction

	/**
	 * Traverse the data structure in the right order.
	 */
	public abstract void work(PainterCallback cb);

	/**
	 * Determine whether a point is behind the painter.
	 */
	public boolean isBehind(Point p) {
		return Math.signum(ppd.position(p)) == -at;
	}

	/**
	 * Projects p on a unit segment.
	 * @return position on a [0,1] segment or infinity when too far
	 */
	public float project(Point p) {
		Point d = p.from(pov); //distance vector
		float tu=d.angle(u), tv=d.angle(v);
		if (tv <= fov/2)
			return 1 - tu/fov;
		else if (Point.close(tv-tu, fov/2))
			return Float.POSITIVE_INFINITY;
		else
			return Float.NEGATIVE_INFINITY;
	}

	/**
	 * Projects s on a unit segment.
	 */
	public Projection project(Segment s) {
		if (isBehind(s.p) && isBehind(s.q))
			return null;
		else {
			float a = project(s.p);
			float b = project(s.q);
			if (Point.close(a, b)) //TODO test with infinity
				return null;
			else
				return new Projection(a, b);
		}
	}

	/**
	 * Construct a painter object.
	 * @param v direction vector
	 * @param pov camera position
	 * @param fov field of view (strictly positive, in radians)
	 */
	protected Painter(Point v, Point pov, float fov) {
		Point away = pov.move(v);
		this.v = v;
		this.pov = pov;
		this.fov = fov;
		u = v.rotate(Point.ORIGIN, fov/2);
		ppd = new Segment(pov, away, null).perpendicular(pov);
		at = (int)Math.signum(ppd.position(away));
	}
}
