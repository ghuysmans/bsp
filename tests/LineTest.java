package tests;

import core.*;
import java.awt.Color;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class LineTest {
	private final static Color C = Color.BLACK;

	@Test
	public void noInter() {
		Line s = (Line)new Segment(new Point(0, 0), new Point(1, 1), C);
		assertNull(s.intersection(s));
		Line s2 = (Line)new Segment(new Point(3, 3), new Point(2, 2), C);
		assertNull(s.intersection(s2));
	}

	@Test
	public void inter() {
		Point d = new Point(0.5F, 0.5F);
		Line s = (Line)new Segment(new Point(0, 0), new Point(1, 1), C);
		Line s2 = (Line)new Segment(new Point(0, 1), new Point(1, 0), C);
		assertEquals(d, s.intersection(s2));
	}

	@Test
	public void ppd() {
		Point one = new Point(1, 1);
		Segment s = new Segment(Point.ORIGIN, one, C);
		Line t = s.perpendicular(Point.ORIGIN);
		Line u = t.perpendicular(Point.ORIGIN);
		assertEquals(s.position(one), u.position(one), Point.EPSILON);
		assertNotEquals(s.position(one), t.position(one), Point.EPSILON);
		assertEquals(0, t.position(new Point(-1, 1)), Point.EPSILON);
	}
}
