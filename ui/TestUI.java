package ui;
import core.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.io.IOException;

/**
 * Main class for the Swing GUI
 */
public class TestUI extends JFrame implements ActionListener {
	public Scene scene;
	public BSP bsp;
	protected Zoom zoom;
	protected Overview overview;

	protected final JMenuBar menuBar = new JMenuBar();
	protected final JMenu menuHelp = new JMenu("Help");
	protected final JMenuItem menuManual = new JMenuItem("Manual");
	protected final JMenuItem menuAbout = new JMenuItem("About...");
	protected final JMenu menuView = new JMenu("View");
	protected final JMenuItem menuRC = new JMenuItem("Reset Center");
	protected final JMenuItem menuRZ = new JMenuItem("Reset Zoom");
	protected final JMenuItem menuR = new JMenuItem("Reset");

	/**
	 * Load and display a scene or display an error message
	 */
	public void loadScene(String filename, Heuristic heuristic) {
		try {
			scene = new Scene(filename);
			bsp = BSP.build(scene.segments, heuristic);
			JPanel main = new JPanel();
			overview = new Overview(this);
			main.setLayout(new BorderLayout());
			main.add(overview);
			zoom = new Zoom(overview, 1, 800, 10, 10);
			main.add(zoom, BorderLayout.NORTH);
			setContentPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(new Tree(filename, bsp)), main));
			revalidate();
			repaint();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void reset(boolean center, boolean z) {
		if (center)
			overview.center();
		if (z)
			zoom.reset();
		else if (center) {
			revalidate();
			repaint();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuAbout)
			about();
		else if (overview == null)
			;
		else if (e.getSource() == menuR)
			reset(true, true);
		else if (e.getSource() == menuRC)
			reset(true, false);
		else if (e.getSource() == menuRZ)
			reset(false, true);
		else
			assert(false);
	}

	public void about() {
		JOptionPane.showMessageDialog(this,
			"By Guillaume Huysmans, 2016.\nBased on "+
			"Computational Geometry (Mark de Berg et al.), chapter 12.",
			"About...", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void initMenus() {
		menuBar.add(menuView);
		menuView.add(menuR);
		menuR.addActionListener(this);
		menuView.add(menuRC);
		menuRC.addActionListener(this);
		menuView.add(menuRZ);
		menuRZ.addActionListener(this);
		menuBar.add(menuHelp);
		menuHelp.add(menuAbout);
		menuAbout.addActionListener(this);
		setJMenuBar(menuBar);
	}

	/**
	 * Ask something to the user (just a shortcut)
	 */
	public String ask(String title, String dflt) {
		return JOptionPane.showInputDialog(this, title, dflt);
	}

	public TestUI(String initialScene) {
		setTitle("BSP Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationByPlatform(true);
		initMenus();
		setContentPane(new Load(this, initialScene));
		setVisible(true);
	}

	public static void main(String[] args) {
		new TestUI(args.length==1 ? args[0] : null);
	}
}
