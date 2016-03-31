package core;

public class Projection {
	public final float a;
	public final float b;

	public String toString() {
		return "("+Float.toString(a)+", "+Float.toString(b)+")";
	}

	public Projection(float a, float b) {
		this.a = a;
		this.b = b;
	}
}
