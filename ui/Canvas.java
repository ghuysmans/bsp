package ui;
import core.*;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Panel displaying the {@link Painter}'s work
 */
public class Canvas extends JPanel implements PainterCallback {
	protected final Overview overview;
	protected final Painter painter;
	protected final boolean decomposed;
	protected Graphics g;
	private int y;
	private int zoom;
	private static final int MARGIN = 10;

	/**
	 * Draw the projected segment.
	 */
	@Override
	public void draw(Segment s) {
		Projection p = painter.project(s);
		if (p == null)
			return; //not seen
		g.setColor(s.color);
		int a = MARGIN + (int)(Point.to01(p.a) * zoom);
		int b = MARGIN + (int)(Point.to01(p.b) * zoom);
		g.drawLine(a, y, b, y);
		g.drawLine(a, y+1, b, y+1);
		if (decomposed) {
			g.setColor(Color.GRAY);
			g.drawLine(a, MARGIN, a, getHeight()-MARGIN);
			y += 3;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		//for the callback method
		y = MARGIN;
		this.g = g;
		zoom = getWidth() - 2*MARGIN;
		//ask the painter
		painter.work(this);
	}

	public Canvas(Overview overview, Painter painter, boolean decomposed) {
		this.overview = overview;
		this.painter = painter;
		this.decomposed = decomposed;
	}
}
