package tests;

import core.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PainterTest {
	@Test
	public void project() {
		Painter pt = new Noobie(new Point(4.94F, 1.06F),
			new Point(1.52F, 3.08F), (float)Math.toRadians(60), null);
		Point p = new Point(4.7F, 1.37F);
		assertEquals(Float.NEGATIVE_INFINITY, pt.project(p), 1);
		Point q = new Point(5.34F, 9.95F);
		assertEquals(Float.POSITIVE_INFINITY, pt.project(q), 1);
		Point r = new Point(5.74F, 3.99F);
		assertEquals(0.5, pt.project(r), 0.1);
	}

	@Test
	public void project2() {
		Painter pt = new Noobie(new Point(-.95F, -3.36F),
			new Point(1.52F, 3.08F), (float)Math.toRadians(60), null);
		Point p = new Point(4.7F, 1.37F);
		assertEquals(Float.POSITIVE_INFINITY, pt.project(p), 1);
		Point q = new Point(1.46F, .26F);
		assertEquals(.75, pt.project(q), 0.1);
		Point r = new Point(-.07F, .57F);
		assertEquals(.25, pt.project(r), 0.1);
		Point s = new Point(-.61F, 1.63F);
		assertEquals(Float.NEGATIVE_INFINITY, pt.project(s), 1);
		assertNull(pt.project(new Segment(
			new Point(3.2F, 3.1F), new Point(0.8F, 4.2F), null)));
		assertNotNull(pt.project(new Segment(q, r, null)));
	}
}
