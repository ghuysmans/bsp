package core;

/**
 * Callback interface used by {@link Painter#work}
 */
public interface PainterCallback {
	/**
	 * Callback method fired for each Segment
	 */
	void draw(Segment s);
}
