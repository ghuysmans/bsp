package core;
import java.util.List;

/**
 * A decent painter
 * @see "Computational Geometry (Mark de Berg et al.), page 255"
 */
public class RealPainter implements Painter {
	protected final BSP bsp;
	protected final Point pov;

	/** The callback class is stored here to avoid pushing useless refs. */
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

	public RealPainter(BSP bsp, Point pov) {
		this.bsp = bsp;
		this.pov = pov;
	}
}
