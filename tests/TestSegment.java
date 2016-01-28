package tests;

import core.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestSegment {
	public static final double EPSILON = 0.000000001;

	@Test
	public void identity() {
		Segment s = new Segment(new Point(0, 0), new Point(1, 1));
		assertEquals("a", -1, s.a, EPSILON);
		assertEquals("b", 1, s.b, EPSILON);
		assertEquals("c", 0, s.c, EPSILON);
		assertEquals("p+", 2, s.position(new Point(0,2)), EPSILON);
		assertEquals("p-", -2, s.position(new Point(0,-2)), EPSILON);
	}

	@Test
	public void vertical() {
		Segment s = new Segment(new Point(1, 5), new Point(1, -1));
		assertEquals("a", 6, s.a, EPSILON);
		assertEquals("b", 0, s.b, EPSILON);
		assertEquals("c", -6, s.c, EPSILON);
		assertEquals("p+", 6, s.position(new Point(2,0)), EPSILON);
		assertEquals("p-", -6, s.position(new Point(0,0)), EPSILON);
	}
}
