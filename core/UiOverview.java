package core;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class UiOverview extends JPanel implements MouseListener {
	protected TestUI ui;
	float zoom = 40;
	Point dir;
	Point pov;
	Point prev;
	float angle = (float)Math.toRadians(60);

	//TODO drawSegment, drawPoint... abstractions

	protected void draw(Graphics g, Segment s) {
		int x1=(int)(zoom*s.p.x), y1=(int)(zoom*s.p.y);
		int x2=(int)(zoom*s.q.x), y2=(int)(zoom*s.q.y);
		g.setColor(s.color);
		g.drawLine(x1, y1, x2, y2);
	}

	protected void drawCamera(Graphics g) {
		Point p = new Point(pov.x+dir.x, pov.y+dir.y);
		g.setColor(Color.GRAY.brighter());
		g.drawLine((int)pov.x, (int)pov.y, (int)p.x, (int)p.y);
		Point p1 = p.rotate(pov, angle/2);
		Point p2 = p.rotate(pov, -angle/2);
		g.setColor(Color.RED);
		g.drawLine((int)pov.x, (int)pov.y, (int)p1.x, (int)p1.y);
		g.drawLine((int)pov.x, (int)pov.y, (int)p2.x, (int)p2.y);
		g.setColor(Color.GRAY.brighter());
		g.fillOval((int)pov.x-5, (int)pov.y-5, 10, 10);
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
			if (dir != null)
				drawCamera(g);
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
			if (prev == null)
				prev = new Point((float)p.getX()/zoom,(float)p.getY()/zoom);
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
