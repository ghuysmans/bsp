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

	protected void indent(StringBuilder sb, int amount) {
		for (int i=0; i<amount; i++)
			sb.append('\t');
	}

	protected void append(StringBuilder sb, BST bst, int indent) {
		if (bst == null) {
			indent(sb, indent);
			sb.append("null\n");
		}
		else {
			if (bst.separator != null) {
				append(sb, bst.negative, indent+1);
				indent(sb, indent);
				sb.append("SEP ");
				sb.append(bst.separator);
				sb.append('\n');
			}
			for (Segment s: bst.items) {
				indent(sb, indent);
				sb.append(s);
				sb.append('\n');
			}
			if (bst.separator != null)
				append(sb, bst.positive, indent+1);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		append(sb, this, 0);
		return sb.toString();
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
