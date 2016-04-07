package core;
import java.util.List;

public class First implements Heuristic {
	@Override public void init(List<Segment> segments) {}

	@Override
	public int choose(List<Segment> segments) {
		return 0;
	}
}
