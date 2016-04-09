package core;
import java.util.List;

/**
 * Free split heuristic as described on page 257 in
 * Computational Geometry (Mark de Berg et al.), chapter 12
 */
public class FreeSplit extends Random {
	@Override
	public int choose(List<Segment> segments) {
		for (int i=0; i<segments.size(); i++) {
			Segment s = segments.get(i);
			if (s.p.intersection && s.q.intersection)
				return i;
		}
		return 0; //random since the original list got shuffled
	}
}
