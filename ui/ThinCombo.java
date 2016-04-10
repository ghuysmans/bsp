package ui;

import java.awt.Dimension;
import javax.swing.JComboBox;

/**
 * ComboBox with the smallest possible height.
 */

public class ThinCombo<T> extends JComboBox {
	@Override
	public Dimension getMaximumSize() {
		Dimension ret = getPreferredSize();
		ret.width = super.getMaximumSize().width;
		return ret;
	}

	@SuppressWarnings("unchecked")
	public ThinCombo(T[] arr) {
		super(arr);
	}
}
