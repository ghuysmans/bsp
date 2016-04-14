package ui;
import core.*;

/**
 * A fake callback class only counting {@link core.Segment} for benchmarks
 */
public class EmptyCallback implements PainterCallback {
	protected int segments;

	@Override
	public void draw(Segment s) {
		segments++;
	}

	/**
	 * Count how many {@link core.Segment} have been "drawn"
	 */
	public int getCount() {
		return segments;
	}

	/**
	 * Reset the counter
	 */
	public void reset() {
		segments = 0;
	}
}
