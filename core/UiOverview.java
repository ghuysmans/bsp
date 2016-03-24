package core;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class UiOverview extends JPanel implements MouseListener {
	protected TestUI ui;
	float zoom = 40;
	Point dir; //vector
	Point pov;
	Point prev;
	float angle = (float)Math.toRadians(60);

	/**
	 * Draw a point.
	 */
	protected void draw(Graphics g, Point p) {
		int x=(int)(zoom*p.x), y=(int)(zoom*p.y);
		g.fillOval(x-5, y-5, 10, 10);
	}

	/**
	 * Draw a segment.
	 */
	protected void draw(Graphics g, Segment s) {
		int x1=(int)(zoom*s.p.x), y1=(int)(zoom*s.p.y);
		int x2=(int)(zoom*s.q.x), y2=(int)(zoom*s.q.y);
		g.setColor(s.color);
		g.drawLine(x1, y1, x2, y2);
	}

	/**
	 * Draw an ugly camera.
	 */
	protected void drawCamera(Graphics g) {
		Point p = new Point(pov.x+dir.x, pov.y+dir.y);
		draw(g, new Segment(pov, p, Color.GRAY.brighter()));
		Point p1 = p.rotate(pov, angle/2);
		Point p2 = p.rotate(pov, -angle/2);
		draw(g, new Segment(pov, p1, Color.RED));
		draw(g, new Segment(pov, p2, Color.RED));
		g.setColor(Color.GRAY.brighter());
		draw(g, pov);
	}

	/**
	 * Draw segment projections.
	 */
	protected void drawProjections(Graphics g) {
		float y = 1;
		for (Segment s: ui.scene.segments) {
			float pa = s.p.project(dir, pov, angle);
			float pb = s.q.project(dir, pov, angle);
			if (Math.abs(pa)==Float.POSITIVE_INFINITY &&
					Math.abs(pb)==Float.POSITIVE_INFINITY)
				continue; //not seen
			Point p = new Point(8 + Point.to01(pa), y);
			Point q = new Point(8 + Point.to01(pb), y);
			draw(g, new Segment(p, q, s.color));
			y += 0.5;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (ui.scene == null) {
			g.setColor(Color.RED);
			g.drawLine(0, 0, getWidth(), getHeight());
			g.drawLine(0, getHeight(), getWidth(), 0);
		}
		else {
			for (Segment s: ui.scene.segments)
				draw(g, s);
			if (dir != null) {
				drawCamera(g);
				drawProjections(g);
			}
		}
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		java.awt.Point p = e.getPoint();
		if (e.getButton() == 1) {
			if (pov==null || dir!=null) {
				pov = new Point((float)p.getX()/zoom,(float)p.getY()/zoom);
				dir = null;
			}
			else {
				float x = (float)(p.getX()/zoom-pov.x);
				float y = (float)(p.getY()/zoom-pov.y);
				dir = new Point(x, y);
				//we've just moved the camera
				revalidate();
				repaint();
			}
		}
		else { //let's not rely too much on a third button...
			if (prev == null) {
				prev = new Point((float)p.getX()/zoom,(float)p.getY()/zoom);
				System.out.println(prev);
			}
			else {
				Point p2 = new Point((float)p.getX()/zoom,(float)p.getY()/zoom);
				ui.scene.segments.add(new Segment(prev,p2,Color.RED));
				revalidate();
				repaint();
				prev = null;
			}
		}
	}

	public UiOverview(TestUI ui) {
		this.ui = ui;
		addMouseListener(this);
	}
}
