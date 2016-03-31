package core;

public class Line {
	/**
	 * Coefficients in ax+by+c=0
	 */
	public final float a, b, c;

	/**
	 * Get the position of p relative to this line.
	 * The return value is used to browse BSP trees.
	 */
	public float position(Point p) {
		return a*p.x + b*p.y + c;
	}

	/**
	 * Compute a perpendicular line passing through p.
	 */
	public Line perpendicular(Point p) {
		return new Line(-b, a, b*p.x - a*p.y);
	}

	public Line(float a, float b, float c) {
		//ensure segments uniqueness
		this.a = a<0 ? a : -a;
		this.b = a<0 ? b : -b;
		this.c = a<0 ? c : -c;
	}
}
