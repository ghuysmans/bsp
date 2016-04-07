package core;
import java.util.List;

/**
 * Generic heuristic used by {@link BSP#build}
 */
public interface Heuristic {
	/**
	 * Perform the required initialization steps on a segment list.
	 * @param segments mutable list
	 */
	public void init(List<Segment> segments);

	/**
	 * Choose a segment among the given list.
	 * @param segments previously initialized list
	 */
	public int choose(List<Segment> segments);
}
