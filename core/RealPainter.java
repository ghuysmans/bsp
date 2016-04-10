package core;
import java.util.List;

/**
 * A decent painter
 * @see "Computational Geometry (Mark de Berg et al.), page 255"
 */
public class RealPainter extends Painter {
	protected final BSP bsp;

	//The callback class is stored here to avoid pushing useless refs.
	private PainterCallback callback;

	@Override
	public void work(PainterCallback cb) {
		callback = cb;
		work(bsp);
	}

	private void work(BSP t) {
		if (t == null)
			; //don't crash
		else if (t.positive==null && t.negative==null)
			scanConvert(t.items);
		else {
			float position = t.separator.position(pov);
			if (position > 0) {
				work(t.negative);
				scanConvert(t.items);
				work(t.positive);
			}
			else if (position < 0) {
				work(t.positive);
				scanConvert(t.items);
				work(t.negative);
			}
			else {
				work(t.positive);
				work(t.negative);
			}
		}
	}

	private void scanConvert(List<Segment> segments) {
		//this should not happen since we only use auto-partitions
		assert(segments != null);

		for (Segment s: segments)
			callback.draw(s);
	}

	/**
	 * @param v direction vector
	 * @param p camera position
	 * @param f field of view (strictly positive, in radians)
	 * @param b BSP
	 */
	public RealPainter(Point v, Point p, float f, BSP b) {
		super(v, p, f);
		bsp = b;
	}
}
