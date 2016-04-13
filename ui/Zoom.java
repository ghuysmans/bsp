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

	public void stateChanged(ChangeEvent e) {
		overview.zoom = (float)getValue()/denominator;
		overview.revalidate();
		overview.repaint();
	}

	public Zoom(Overview o, int min, int max, int value, int denom) {
		super(min, max, value);
		overview = o;
		denominator = denom;
		addChangeListener(this);
		stateChanged(null);
		setToolTipText("Zoom Factor");
	}
}
