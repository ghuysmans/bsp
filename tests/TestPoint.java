package tests;

import core.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestPoint {
	@Test
	public void distance() {
		Point p = new Point(3, 14);
		Point p2 = new Point(15, 92);
		assertEquals(78.917, p.distance(p2), 0.01);
	}

	@Test
	public void equals() {
		Point p = new Point(0, 0);
		Point p2 = new Point(Point.EPSILON, 0);
		Point p3 = new Point(0, Point.EPSILON);
		Point p4 = new Point(0, Point.EPSILON/2);
		assertTrue(p.equals(p));
		assertFalse(p2.equals(p)); assertFalse(p.equals(p2));
		assertFalse(p3.equals(p)); assertFalse(p.equals(p3));
		assertTrue(p4.equals(p)); assertTrue(p.equals(p4));
	}

	@Test
	public void rotate() {
		Point o = new Point(0, 0);
		Point a = new Point(1, 1);
		Point ap = new Point((float)Math.sqrt(2), 0);
		Point b = ap.rotate(o, (float)Math.toRadians(60));
		Point bp = b.rotate(ap, (float)Math.toRadians(60));
		assertTrue(a.rotate(o, (float)Math.toRadians(-45)).equals(ap));
		System.out.println(bp);
		System.out.println(o);
		assertTrue(bp.equals(o));
	}
}
