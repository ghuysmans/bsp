package tests;

import core.*;
import java.awt.Color;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SegmentTest {
	private final static Color C = Color.BLACK;

	@Test
	public void identitySym() {
		Segment s = new Segment(new Point(1, 1), new Point(0, 0), C);
		assertEquals("a", -1, s.a, Point.EPSILON);
		assertEquals("b", 1, s.b, Point.EPSILON);
		assertEquals("c", 0, s.c, Point.EPSILON);
		assertEquals("p+", 2, s.position(new Point(0,2)), Point.EPSILON);
		assertEquals("p-", -2, s.position(new Point(0,-2)), Point.EPSILON);
	}

	@Test
	public void identity() {
		Segment s = new Segment(new Point(0, 0), new Point(1, 1), C);
		assertEquals("a", -1, s.a, Point.EPSILON);
		assertEquals("b", 1, s.b, Point.EPSILON);
		assertEquals("c", 0, s.c, Point.EPSILON);
		assertEquals("p+", 2, s.position(new Point(0,2)), Point.EPSILON);
		assertEquals("p-", -2, s.position(new Point(0,-2)), Point.EPSILON);
	}

	@Test
	public void vertical() {
		Segment s = new Segment(new Point(1, 5), new Point(1, -1), C);
		assertEquals("a", -6, s.a, Point.EPSILON);
		assertEquals("b", 0, s.b, Point.EPSILON);
		assertEquals("c", 6, s.c, Point.EPSILON);
		assertEquals("p+", -6, s.position(new Point(2,0)), Point.EPSILON);
		assertEquals("p-", 6, s.position(new Point(0,0)), Point.EPSILON);
	}

	@Test
	public void noInter() {
		Segment s = new Segment(new Point(0, 0), new Point(1, 1), C);
		assertNull(s.intersection(s));
		Segment s2 = new Segment(new Point(3, 3), new Point(2, 2), C);
		assertNull(s.intersection(s2));
	}

	@Test
	public void inter() {
		Point d = new Point(2, 2);
		Segment s = new Segment(new Point(0, 0), new Point(1, 1), C);
		Segment s2 = new Segment(new Point(0, 1), d, C);
		assertEquals(d, s.intersection(s2));
	}
}
