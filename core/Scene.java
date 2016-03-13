package core;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.Color;
import java.io.IOException;

public class Scene {
	public final List<Segment> segments;
	public final int maxX, maxY;

	protected static final Hashtable<String, Color> colors =
		new Hashtable<String, Color>();

	static {
		colors.put("Bleu", Color.BLUE);
	}

	public static Color getColor(String s) throws FormatException {
		Color c = colors.get(s);
		if (c == null)
			throw new FormatException("Expected a color, got: "+s);
		else
			return c;
	}

	protected static float getFloat(String s) throws FormatException {
		Float f = Float.parseFloat(s);
		if (f == null)
			throw new FormatException("Expected a float, got: "+s);
		else
			return f;
	}

	protected static int getInt(String s) throws FormatException {
		Integer i = Integer.parseInt(s);
		if (i == null)
			throw new FormatException("Expected an int, got: "+s);
		else
			return i;
	}

	public Scene(String fn) throws IOException, FormatException {
		BufferedReader r = new BufferedReader(new FileReader(fn));
		//header
		String[] info = r.readLine().split(" ");
		if (info.length!=4 || !info[0].equals(">"))
			throw new FormatException("Invalid header: expected >");
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