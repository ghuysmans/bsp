package core;

/**
 * Projection of a {@link Segment}
 */
public class Projection {
	/**
	 * Projection of {@link Segment#p}
	 */
	public final float a;
	/**
	 * Projection of {@link Segment#q}
	 */
	public final float b;

	public String toString() {
		return "("+Float.toString(a)+", "+Float.toString(b)+")";
	}

	public Projection(float a, float b) {
		this.a = a;
		this.b = b;
	}
}
