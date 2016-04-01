package core;
import java.util.Collection;

public interface Heuristic {
	public Segment choose(Collection<Segment> segments);
}
