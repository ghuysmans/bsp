package core;

import java.awt.Dimension;
import javax.swing.JTextField;

/**
 * TextField with the smallest possible height
 */

@SuppressWarnings("serial")
public class ThinTextField extends JTextField {
	@Override
	public Dimension getMaximumSize() {
		Dimension ret = super.getMaximumSize();
		ret.height = super.getPreferredSize().height;
		return ret;
	}

	public ThinTextField(String s) {
		super(s);
	}

	public ThinTextField(int i) {
		super(i);
	}

	public ThinTextField(String s, int max) {
		super(s, max);
	}
}
