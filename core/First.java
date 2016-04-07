package core;
import java.util.List;

/**
 * Trivial heuristic always chosing the first segment in the list
 */
public class First implements Heuristic {
	@Override public void init(List<Segment> segments) {}

	@Override
	public int choose(List<Segment> segments) {
		return 0;
	}
}
