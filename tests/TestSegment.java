package tests;

import core.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestSegment {
	@Test
	public void identity() {
		Segment s = new Segment(new Point(0, 0), new Point(1, 1));
		assertEquals("a", -1, s.a, Point.EPSILON);
		assertEquals("b", 1, s.b, Point.EPSILON);
		assertEquals("c", 0, s.c, Point.EPSILON);
		assertEquals("p+", 2, s.position(new Point(0,2)), Point.EPSILON);
		assertEquals("p-", -2, s.position(new Point(0,-2)), Point.EPSILON);
	}

	@Test
	public void vertical() {
		Segment s = new Segment(new Point(1, 5), new Point(1, -1));
		assertEquals("a", 6, s.a, Point.EPSILON);
		assertEquals("b", 0, s.b, Point.EPSILON);
		assertEquals("c", -6, s.c, Point.EPSILON);
		assertEquals("p+", 6, s.position(new Point(2,0)), Point.EPSILON);
		assertEquals("p-", -6, s.position(new Point(0,0)), Point.EPSILON);
	}
}
