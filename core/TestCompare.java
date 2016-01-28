package core;

class TestCompare {
	public static void main(String[] args) {
		if (args.length == 6) {
			Segment s;
			float a, b, c, d, e, f;
			a = Float.parseFloat(args[0]);
			b = Float.parseFloat(args[1]);
			c = Float.parseFloat(args[2]);
			d = Float.parseFloat(args[3]);
			e = Float.parseFloat(args[4]);
			f = Float.parseFloat(args[5]);
			s = new Segment(new Point(a,b), new Point(c,d));
			System.out.println(s);
			System.out.println(s.position(new Point(e,f)));
		}
		else
			System.out.println("order: (a,b)--(c,d) (e,f)");
	}
}
