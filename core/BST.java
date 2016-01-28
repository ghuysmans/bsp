package core;
import java.util.LinkedList;

public class BST {
	public Segment separator;
	public BST negative;
	public BST positive;
	public LinkedList<Segment> items;

	public boolean contains(Segment segment) {
		if (separator == null)
			//leaf
			return items.contains(segment);
		else {
			float a = separator.position(segment.p);
			float b = separator.position(segment.q);
			if (a==0 && b==0)
				//completely inside of the separator's line
				//FIXME ensure that contains uses equals
				return items.contains(segment);
			else if (a*b >= 0) {
				//completely on either side
				if (a < 0)
					return negative.contains(segment);
				else
					return positive.contains(segment);
			}
			else {
				//split (worst case)
				Point inter = segment.intersection(separator);
				//inter isn't null since p and q are on different sides
				if (a < 0)
					return negative.contains(segment.to(inter)) &&
						positive.contains(segment.from(inter));
				else
					return positive.contains(segment.to(inter)) &&
						negative.contains(segment.from(inter));
			}
		}
	}

	/**
	 * Internal node constructor
	 */
	public BST(Segment separator) {
		this.separator = separator;
		items = new LinkedList<Segment>();
	}

	/**
	 * Leaf constructor
	 */
	public BST() {
		items = new LinkedList<Segment>();
	}
}
