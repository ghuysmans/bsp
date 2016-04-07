package core;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class UiLoad extends JPanel implements ActionListener {
	protected final TestUI ui;
	protected BrowseBox scene;
	protected JButton load;
	protected ThinCombo heuristic;

	public void actionPerformed(ActionEvent e) {
		Heuristic h = (Heuristic)heuristic.getSelectedItem();
		ui.loadScene(scene.filename.getText(), h);
		//TODO handle errors
	}

	public UiLoad(TestUI ui) {
		Box row;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		add(Box.createVerticalGlue());

		row = Box.createHorizontalBox();
		row.add(new JLabel("Heuristic: "));
		Heuristic h[] = {new First(), new Random()};
		heuristic = new ThinCombo<Heuristic>(h);
		row.add(heuristic);
		row.add(Box.createHorizontalGlue());
		add(row);

		scene = new BrowseBox("Scene: ");
		add(scene);

		row = Box.createHorizontalBox();
		row.add(Box.createHorizontalGlue());
		load = new JButton("Load");
		load.addActionListener(this);
		row.add(load);
		row.add(Box.createHorizontalGlue());
		add(row);

		add(Box.createVerticalGlue());
		//for the callback method
		this.ui = ui;
	}
}
