package core;
import java.util.List;
import java.util.Collections;

/**
 * Trivial heuristic chosing random segments by shuffling the list once.
 */
public class Random extends First {
	@Override public void init(List<Segment> segments) {
		Collections.shuffle(segments);
	}
}
