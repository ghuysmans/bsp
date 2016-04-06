package core;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class UiLoad extends JPanel {
	protected final TestUI ui;

	public UiLoad(TestUI ui) {
		this.ui = ui;
		try {
			Class<?> h[] = {First.class, null};
			System.out.println(h[0].getConstructor().newInstance().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
