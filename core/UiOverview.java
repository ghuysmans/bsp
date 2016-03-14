package core;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class UiOverview extends JPanel implements MouseListener {
	protected TestUI ui;
	float zoom = 40;

	protected void draw(Graphics g, Segment s) {
		int x1=(int)(zoom*s.p.x), y1=(int)(zoom*s.p.y);
		int x2=(int)(zoom*s.q.x), y2=(int)(zoom*s.q.y);
		g.setColor(s.color);
		g.drawLine(x1, y1, x2, y2);
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
		}
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		java.awt.Point p = e.getPoint();
		//1=left, 2=middle, 3=right
		System.out.println(e.getButton()+" at "+p.getX()+","+p.getY());
	}

	public UiOverview(TestUI ui) {
		this.ui = ui;
		addMouseListener(this);
		System.out.println("UiOverview: scene="+ui.scene);
	}
}
