package core;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class UiCanvas extends JPanel implements PainterCallback {
	protected final UiOverview overview;
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
		float pa = s.p.project(overview.dir, overview.pov, overview.angle);
		float pb = s.q.project(overview.dir, overview.pov, overview.angle);
		int x1, x2;
		if (Math.abs(pa)==Float.POSITIVE_INFINITY &&
				Math.abs(pb)==Float.POSITIVE_INFINITY)
			return; //not seen
		x1 = (int)(10 + zoom*Point.to01(pa));
		x2 = (int)(10 + zoom*Point.to01(pb));
		g.setColor(s.color);
		g.drawLine(x1, y, x2, y);
		if (decomposed) {
			g.setColor(Color.GRAY);
			g.drawLine(x1, 30, x1, 30+3*10);
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

	public UiCanvas(UiOverview overview, Painter painter, boolean decomposed) {
		this.overview = overview;
		this.painter = painter;
		this.decomposed = decomposed;
	}
}
