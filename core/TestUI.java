package core;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.IOException;

class TestUI extends JFrame implements ActionListener {
	public Scene scene;
	protected JPanel panel;
	protected final JMenuBar menuBar = new JMenuBar();
	protected final JMenu menuHelp = new JMenu("Help");
	protected final JMenuItem menuManual = new JMenuItem("Manual");
	protected final JMenuItem menuAbout = new JMenuItem("About...");

	public void loadScene(String filename) {
		try {
			scene = new Scene(filename);
			revalidate();
			repaint();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (FormatException e) {
			e.printStackTrace();
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

	public String ask(String title, String dflt) {
		return JOptionPane.showInputDialog(this, title, dflt);
	}

	public TestUI(String initialScene) {
		setTitle("BSP Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
		initMenus();
		setContentPane(panel = new UiOverview(this));
		if (initialScene != null)
			loadScene(initialScene);
	}

	public static void main(String[] args) {
		new TestUI(args.length==1 ? args[0] : null);
	}
}
