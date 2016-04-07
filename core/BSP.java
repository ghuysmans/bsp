package core;
import java.util.List;
import java.util.ArrayList;

/**
 * Binary Space Partition as described in
 * Computational Geometry (Mark de Berg et al.), chapter 12
 */
public class BSP {
	public Line separator;
	public BSP negative;
	public BSP positive;
	public List<Segment> items;

	/**
	 * Test whether the given segment lies completely in the BSP.
	 * This is also useful for testing construction algorithms!
	 */
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

	//TODO del
	protected void indent(StringBuilder sb, int amount) {
		for (int i=0; i<amount; i++)
			sb.append('\t');
	}

	protected void append(StringBuilder sb, BSP bst, int indent) {
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
	 * Count nodes
	 * @return number of nodes
	 */
	public int nodes() {
		if (positive==null && negative==null)
			return 1;
		else if (positive == null)
			return 1 + positive.nodes();
		else if (negative == null)
			return 1 + negative.nodes();
		else
			return 1 + positive.nodes() + negative.nodes();
	}

	/**
	 * Compute height
	 * @return height in nodes
	 */
	public int height() {
		if (positive==null && negative==null)
			return 1;
		else if (positive == null)
			return 1 + positive.height();
		else if (negative == null)
			return 1 + negative.height();
		else
			return 1 + Math.max(positive.height(), negative.height());
	}

	/**
	 * Build a BSP from a collection of segments
	 * @param set segment collection
	 * @param h heuristic for separator selection
	 */
	public static BSP build(List<Segment> set, Heuristic h) {
		h.init(set);
		return build_(set, h);
	}

	protected static BSP build_(List<Segment> set, Heuristic h) {
		if (set.size() <= 1)
			return new BSP(set, null, null, null); //leaf
		else {
			Line separator = set.get(h.choose(set));
			List<Segment> items = new ArrayList<Segment>();
			List<Segment> positive = new ArrayList<Segment>();
			List<Segment> negative = new ArrayList<Segment>();
			for (Segment segment: set) {
				float a = separator.position(segment.p);
				float b = separator.position(segment.q);
				if (Point.close(a, 0) && Point.close(b, 0))
					//completely inside of the separator's line
					items.add(segment);
				else if (a>=0 && b>=0)
					positive.add(segment);
				else if (a<=0 && b<=0)
					negative.add(segment);
				else {
					//split (worst case)
					Point inter = segment.intersection(separator);
					//inter isn't null since p and q are on different sides
					if (a < 0) {
						if (!segment.p.equals(inter))
							negative.add(segment.to(inter));
						if (!segment.q.equals(inter))
							positive.add(segment.from(inter));
					}
					else {
						if (!segment.p.equals(inter))
							positive.add(segment.to(inter));
						if (!segment.q.equals(inter))
							negative.add(segment.from(inter));
					}
				}
			}
			return new BSP(items, separator, build(positive,h), build(negative,h));
		}
	}

	public BSP(List<Segment> s, Line sep, BSP pos, BSP neg) {
		items = s;
		separator = sep;
		positive = pos;
		negative = neg;
	}
}
