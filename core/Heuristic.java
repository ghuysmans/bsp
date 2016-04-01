package core;
import java.util.Set;

public interface Heuristic {
	public Segment choose(Set<Segment> segments);
}
