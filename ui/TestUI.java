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

	protected final JMenuBar menuBar = new JMenuBar();
	protected final JMenu menuHelp = new JMenu("Help");
	protected final JMenuItem menuManual = new JMenuItem("Manual");
	protected final JMenuItem menuAbout = new JMenuItem("About...");

	/**
	 * Load and display a scene or display an error message
	 */
	public void loadScene(String filename, Heuristic heuristic) {
		try {
			scene = new Scene(filename);
			bsp = BSP.build(scene.segments, heuristic);
			JPanel main = new JPanel();
			Overview o = new Overview(this);
			main.setLayout(new BorderLayout());
			main.add(o);
			main.add(new Zoom(o, 1, 800, 10, 10), BorderLayout.NORTH);
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

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this,
			"By Guillaume Huysmans, 2016.\nBased on "+
			"Computational Geometry (Mark de Berg et al.), chapter 12.",
			"About...", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void initMenus() {
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
