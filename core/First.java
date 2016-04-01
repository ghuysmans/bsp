package core;
import java.util.Collection;

public class First implements Heuristic {
	@Override
	public Segment choose(Collection<Segment> segments) {
		return segments.iterator().next();
	}
}
