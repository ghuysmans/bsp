package core;

class TestUI {
	public static void main(String[] args) {
		//TODO convert this to a unit test (and add cases!)
		BST a = new BST();
		BST b = new BST(new Segment(new Point(0,0), new Point(1,1)));
		BST c = new BST();
		a.items.add(new Segment(new Point(0,-1), new Point(1,0)));
		c.items.add(new Segment(new Point(0,1), new Point(1,2)));
		b.negative = a;
		b.positive = c;
		System.out.print(b);
		//Segment s = new Segment(new Point(0,1), new Point(1,2));
		Segment s = new Segment(new Point(0,2), new Point(2,0));
		System.out.println("looking for "+s);
		System.out.println(b.contains(s));
	}
}
