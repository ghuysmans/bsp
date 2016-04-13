package ui;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Zoom Slider
 */
public class Zoom extends JSlider implements ChangeListener {
	private final Overview overview;
	private final int denominator;
	private final int initial;

	public void stateChanged(ChangeEvent e) {
		overview.zoom = (float)getValue()/denominator;
		overview.revalidate();
		overview.repaint();
	}

	public void reset() {
		setValue(initial);
		stateChanged(null);
	}

	public Zoom(Overview o, int min, int max, int value, int denom) {
		super(min, max, value);
		overview = o;
		initial = value;
		denominator = denom;
		addChangeListener(this);
		stateChanged(null);
		setToolTipText("Zoom Factor");
	}
}
