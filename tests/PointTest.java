package tests;

import core.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PointTest {
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
		assertTrue(bp.equals(o));
	}

	@Test
	public void project() {
		Point v = new Point(4.94F, 1.06F);
		Point pov = new Point(1.52F, 3.08F);
		float fov = (float)Math.toRadians(60);
		Point p = new Point(4.7F, 1.37F);
		assertEquals(Float.NEGATIVE_INFINITY, p.project(v, pov, fov), 1);
		Point q = new Point(5.34F, 9.95F);
		assertEquals(Float.POSITIVE_INFINITY, q.project(v, pov, fov), 1);
		Point r = new Point(5.74F, 3.99F);
		assertEquals(0.5, r.project(v, pov, fov), 0.1);
	}

	@Test
	public void project2() {
		Point v = new Point(-.95F, -3.36F);
		Point pov = new Point(1.52F, 3.08F);
		float fov = (float)Math.toRadians(60);
		Point p = new Point(4.7F, 1.37F);
		assertEquals(Float.POSITIVE_INFINITY, p.project(v, pov, fov), 1);
		Point q = new Point(1.46F, .26F);
		assertEquals(.75, q.project(v, pov, fov), 0.1);
		Point r = new Point(-.07F, .57F);
		assertEquals(.25, r.project(v, pov, fov), 0.1);
		Point s = new Point(-.61F, 1.63F);
		assertEquals(Float.NEGATIVE_INFINITY, s.project(v, pov, fov), 1);
	}
}
