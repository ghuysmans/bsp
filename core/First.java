package core;
import java.util.Set;

public class First implements Heuristic {
	@Override
	public Segment choose(Set<Segment> segments) {
		return segments.iterator().next();
	}
}
