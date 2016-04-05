package core;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;

class UiOverview extends JPanel implements MouseListener, PainterCallback {
	protected TestUI ui;
	float zoom = 40;
	Point dir; //vector
	Point pov;
	Point prev;
	Point ofs = new Point(0, 0);
	float angle = (float)Math.toRadians(60);
	Painter painter;
	private Graphics g;
	BSP bsp;

	/**
	 * Convert scene to screen coordinates
	 */
	protected java.awt.Point convert(Point p) {
		int x = (int)(zoom*(p.x-ofs.x));
		int y = (int)(zoom*(p.y-ofs.y));
		return new java.awt.Point(x, y);
	}

	/**
	 * Convert screen to scene coordinates
	 */
	protected Point convert(java.awt.Point p) {
		float x = (float)p.x/zoom + ofs.x;
		float y = (float)p.y/zoom + ofs.y;
		return new Point(x, y);
	}

	/**
	 * Draw a point.
	 */
	protected void draw(Point p) {
		java.awt.Point s = convert(p);
		g.fillOval(s.x-5, s.y-5, 10, 10);
	}

	/**
	 * Draw a segment.
	 */
	public void draw(Segment s) {
		java.awt.Point p = convert(s.p);
		java.awt.Point q = convert(s.q);
		g.setColor(s.color);
		g.drawLine(p.x, p.y, q.x, q.y);
		g.drawLine(p.x, p.y+1, q.x, q.y+1);
	}

	/**
	 * Draw an ugly camera.
	 */
	protected void drawCamera() {
		Point p = new Point(pov.x+dir.x, pov.y+dir.y);
		draw(new Segment(pov, p, Color.GRAY.brighter()));
		Point p1 = p.rotate(pov, angle/2);
		Point p2 = p.rotate(pov, -angle/2);
		draw(new Segment(pov, p1, Color.RED));
		draw(new Segment(pov, p2, Color.RED));
		g.setColor(Color.GRAY.brighter());
		draw(pov);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (painter == null) {
			g.setColor(Color.RED);
			g.drawLine(0, 0, getWidth(), getHeight());
			g.drawLine(0, getHeight(), getWidth(), 0);
		}
		else {
			this.g = g;
			painter.work(this);
			if (dir != null)
				drawCamera();
		}
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = convert(e.getPoint());
		if (e.getButton() == 1) {
			if (pov==null || dir!=null) {
				pov = p;
				dir = null;
			}
			else {
				dir = new Point(p.x-pov.x, p.y-pov.y);
				//we've just moved the camera: display it
				revalidate();
				repaint();
				//paint what we can see
				painter = new RealPainter(dir, pov, angle, bsp);
				JPanel c = new UiCanvas(this, painter, true);
				JFrame f = new JFrame();
				f.setTitle("Painter's View");
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setSize(200, 140);
				f.setContentPane(c);
				f.setLocationByPlatform(true);
				f.setVisible(true);
			}
		}
		else { //let's not rely too much on a third button...
			if (prev == null)
				prev = p;
			else {
				System.out.println(new Segment(prev, p, Color.RED));
				prev = null;
			}
		}
	}

	public UiOverview(TestUI ui) {
		this.ui = ui;
		painter = new Noobie(ui.scene.segments);
		bsp = BSP.build(ui.scene.segments, new First());
		System.out.println(bsp.nodes()+" nodes, height="+bsp.height());
		addMouseListener(this);
	}
}
