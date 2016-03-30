package core;
import java.util.List;

/**
 * A painter not aware of depth.
 */
public class Noobie implements Painter {
	protected List<Segment> segments;

	@Override
	public void work(PainterCallback cb) {
		for (Segment s: segments)
			cb.draw(s);
	}

	public Noobie(List<Segment> segs) {
		segments = segs;
	}
}
