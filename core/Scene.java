package core;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.Color;
import java.io.IOException;

/**
 * Scene data, loaded from a text file
 */
public class Scene {
	public final List<Segment> segments;
	public final int maxX, maxY;

	/**
	 * Colors used by the Swing GUI
	 */
	protected static final Hashtable<String, Color> colors =
		new Hashtable<String, Color>();

	static {
		colors.put("Bleu", Color.BLUE);
		colors.put("Rouge", Color.RED);
		colors.put("Orange", Color.ORANGE);
		colors.put("Jaune", Color.YELLOW);
		colors.put("Noir", Color.BLACK);
		colors.put("Violet", Color.PINK.darker()); //TODO compare
		colors.put("Marron", Color.ORANGE.darker()); //TODO compare
		colors.put("Vert", Color.GREEN);
		colors.put("Gris", Color.GRAY);
		colors.put("Rose", new Color(255, 0, 255));
	}

	//TODO maybe refactor into an enum
	public static Color getColor(String s) throws FormatException {
		Color c = colors.get(s);
		if (c == null)
			throw new FormatException("Expected a color, got: "+s);
		else
			return c;
	}

	/**
	 * Parse a float
	 * @throws FormatException for a user-friendly message
	 */
	protected static float getFloat(String s) throws FormatException {
		try {
			return Float.parseFloat(s);
		}
		catch (Exception e) {
			throw new FormatException("Expected a float, got: "+s);
		}
	}

	/**
	 * Parse an float
	 * @throws FormatException for a user-friendly message
	 */
	protected static int getInt(String s) throws FormatException {
		try {
			return Integer.parseInt(s);
		}
		catch (Exception e) {
			throw new FormatException("Expected an int, got: "+s);
		}
	}

	/**
	 * Load a scene file
	 */
	public Scene(String fn) throws IOException, FormatException {
		BufferedReader r = new BufferedReader(new FileReader(fn));
		//header
		String[] info = r.readLine().split(" ");
		if (info.length!=4 || !info[0].equals(">"))
			throw new FormatException("Invalid header: expected > and 3 values");
		maxX = getInt(info[1]);
		maxY = getInt(info[2]);
		int max = Integer.parseInt(info[3]);
		//body
		segments = new ArrayList<Segment>(max);
		String l;
		while ((l=r.readLine()) != null)
			segments.add(Segment.load(l));
		//checks
		if (segments.size() != max)
			throw new FormatException(
				"Got "+segments.size()+" segments, expected "+max);
	}

}
