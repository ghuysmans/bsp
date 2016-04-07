package core;
import java.util.List;
import java.util.Collections;

public class Random extends First {
	@Override public void init(List<Segment> segments) {
		Collections.shuffle(segments);
	}
}
