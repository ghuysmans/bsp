package ui;
import core.*;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Panel displaying the {@link Painter}'s work
 */
class Canvas extends JPanel implements PainterCallback {
	protected final Overview overview;
	protected final Painter painter;
	protected final boolean decomposed;
	protected Graphics g;
	private int y;
	float zoom = 120;

	/**
	 * Draw the projected segment.
	 */
	@Override
	public void draw(Segment s) {
		Projection p = painter.project(s);
		if (p == null)
			return; //not seen
		g.setColor(s.color);
		int a = 40 + (int)(Point.to01(p.a) * zoom);
		int b = 40 + (int)(Point.to01(p.b) * zoom);
		g.drawLine(a, y, b, y);
		g.drawLine(a, y+1, b, y+1);
		if (decomposed) {
			g.setColor(Color.GRAY);
			g.drawLine(a, 30, a, 30+3*10);
			y += 3;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		//for the callback method
		y = 30;
		this.g = g;
		//ask the painter
		painter.work(this);
	}

	public Canvas(Overview overview, Painter painter, boolean decomposed) {
		this.overview = overview;
		this.painter = painter;
		this.decomposed = decomposed;
	}
}
