package core;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class BrowseBox extends Box implements ActionListener {
	public ThinTextField filename;

	public void actionPerformed(ActionEvent e) {
		JFileChooser ch = new JFileChooser();
		if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			filename.setText(ch.getSelectedFile().getPath());
	}

	public BrowseBox(String key) {
		super(BoxLayout.X_AXIS);
		add(new JLabel(key));
		filename = new ThinTextField("");
		add(filename);
		JButton browse = new JButton("Browse");
		browse.addActionListener(this);
		add(browse);
	}
}
