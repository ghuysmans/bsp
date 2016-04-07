package core;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Graphical BSP tree
 */
class UiTree extends JTree {
	protected static void populate(DefaultMutableTreeNode node, BSP bsp) {
		DefaultMutableTreeNode n;
		if (bsp.negative != null) {
			n = new DefaultMutableTreeNode("-");
			node.add(n);
			populate(n, bsp.negative);
		}
		n = new DefaultMutableTreeNode(bsp.items.size());
		node.add(n);
		if (bsp.positive != null) {
			n = new DefaultMutableTreeNode("+");
			node.add(n);
			populate(n, bsp.positive);
		}
	}

	protected static DefaultMutableTreeNode createTree(String caption, BSP bsp) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(caption);
		populate(root, bsp);
		return root;
	}

	public UiTree(String caption, BSP bsp) {
		super(createTree(caption, bsp));
	}
}
