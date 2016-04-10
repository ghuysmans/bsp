package ui;
import core.*;

/**
 * A fake callback class only counting {@link Segments} for benchmarks
 */
class EmptyCallback implements PainterCallback {
	protected int segments;

	@Override
	public void draw(Segment s) {
		segments++;
	}

	/**
	 * Count how many {@link Segments} have been "drawn"
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
