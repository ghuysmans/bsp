package ui;
import core.*;
import java.io.StreamTokenizer;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;

/**
 * Main class for the text-based heuristic comparison tool
 */
public class TestCompare {
	protected static final double NANO_TO_MSEC = 1E6;
	protected final ThreadMXBean timer;
	protected final StreamTokenizer st;
	protected Scene scene;

	/**
	 * Report a single CPU time delta
	 * @param ref_cpu reference time (start)
	 */
	protected void report(long ref_cpu) {
		double cpu = (timer.getCurrentThreadCpuTime()-ref_cpu) / NANO_TO_MSEC;
		System.out.printf("%9.2f", cpu);
	}

	/**
	 * Report a single value
	 */
	protected void report(int value) {
		System.out.printf("%7d", value);
	}

	/**
	 * Display a description
	 */
	protected void description(String d) {
		System.out.printf("%-18s", d);
	}

	/**
	 * Report a CPU time measure with a description
	 * Even though we measure a bit more than the real process,
	 * the delta is constant so we can still compare different algorithms.
	 * @param ref_cpu reference time (start)
	 * @param single not in a table row
	 */
	protected void report(String descr, long ref_cpu, boolean single) {
		if (single) {
			description(descr);
			report(ref_cpu);
			System.out.println("");
		}
		else
			report(ref_cpu);
	}

	/**
	 * Report a value
	 * @param single not in a table row
	 */
	protected void report(String descr, int value, boolean single) {
		if (single) {
			description(descr);
			report(value);
			System.out.println("");
		}
		else
			report(value);
	}

	/**
	 * Discard the current line
	 */
	protected void discardLine() throws IOException {
		while (st.ttype!=StreamTokenizer.TT_EOL &&
				st.nextToken()!=StreamTokenizer.TT_EOF);
	}

	/**
	 * Try to match the next token with the given type.
	 * If it fails, discard the whole line.
	 * @return true when they match
	 */
	protected boolean match(int ttype) throws IOException {
		if (st.nextToken() == ttype)
			return true;
		else {
			System.out.println("syntax error");
			//System.out.println("expected type "+ttype+", got "+st);
			discardLine();
			return false;
		}
	}

	/**
	 * Display a help message
	 */
	protected void help(boolean err) {
		System.out.println("available commands:");
		for (Method m: getClass().getDeclaredMethods()) {
			String name = m.getName();
			if (name.startsWith("do_"))
				System.out.println(name.substring(3));
		}
	}

	/**
	 * Load a scene or display an error message
	 */
	public void loadScene(String filename) {
		try {
			scene = new Scene(filename);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	protected void do_help() throws IOException {
		help(false);
		match(StreamTokenizer.TT_EOL);
	}

	protected void do_exit() throws IOException {
		System.exit(0);
	}

	protected void do_quit() throws IOException {
		System.exit(0);
	}

	protected void do_load() throws IOException {
		if (!match('"'))
			return;
		loadScene(st.sval);
		match(StreamTokenizer.TT_EOL);
	}

	/**
	 * Ensure that a scene has been loaded
	 * @return true if loaded
	 */
	protected boolean checkScene() {
		if (scene == null) {
			System.out.println("Please load a scene first.");
			return false;
		}
		else
			return true;
	}

	/**
	 * Test a heuristic against the current scene
	 * @param single not in a table row
	 */
	protected void test(Heuristic h, boolean single) {
		if (single && !checkScene())
			return;
		long t = timer.getCurrentThreadCpuTime();
		BSP bsp = BSP.build(scene.segments, h);
		if (single) {
			description("Algorithm");
			System.out.printf("%9s\n", "Time (ms)");
		}
		else
			System.out.printf("%-18s", h.toString());
		report("BSP construction", t, single);
		EmptyCallback e = new EmptyCallback();
		Point v = new Point(1, 1);
		Painter painter = new RealPainter(v, Point.ORIGIN, 1, bsp);
		e.reset();
		t = timer.getCurrentThreadCpuTime();
		painter.work(e);
		report("Painter", t, single);
		if (single) {
			System.out.println("");
			description("Stats");
			System.out.println("Count");
		}
		report("Height", bsp.height(), single);
		report("Nodes", bsp.nodes(), single);
		report("Segments", e.getCount(), single);
		System.out.println("");
	}

	protected void do_first() throws IOException {
		match(StreamTokenizer.TT_EOL);
		test(new First(), true);
	}

	protected void do_random() throws IOException {
		match(StreamTokenizer.TT_EOL);
		test(new Random(), true);
	}

	protected void do_free() throws IOException {
		match(StreamTokenizer.TT_EOL);
		test(new FreeSplit(), true);
	}

	protected void do_test() throws IOException {
		if (!match(StreamTokenizer.TT_NUMBER))
			return;
		int ct = (int)st.nval;
		if (!match(StreamTokenizer.TT_EOL))
			;
		else if (ct <= 0)
			System.out.println("count must be a positive integer.");
		else if (!checkScene())
			;
		else {
			Heuristic first = new First();
			Heuristic random = new Random();
			Heuristic free = new FreeSplit();
			description("Heuristic");
			System.out.printf("%9s%9s%7s%7s%7s\n",
				"BSP", "Painter", "H", "N", "S");
			while (ct-- > 0) {
				test(first, false);
				test(random, false);
				test(free, false);
			}
		}
	}

	private void prompt() {
		if (System.console() != null)
			System.out.print(">>> ");
	}

	/**
	 * Command interpreter loop
	 */
	public void loop() {
		prompt();
		try {
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				if (st.ttype != StreamTokenizer.TT_WORD) {
					System.out.println("expected a command!");
					discardLine();
				}
				else {
					String name = "do_"+st.sval.toLowerCase();
					try {
						//getMethod won't find protected methods
						getClass().getDeclaredMethod(name).invoke(this);
					}
					catch (NoSuchMethodException e) {
						System.out.println("unknown command: "+st.sval);
						help(true);
						discardLine();
					}
					catch (Exception e) {
						e.printStackTrace();
						assert(false);
					}
				}
				prompt();
			}
			System.out.println("");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TestCompare(String scene) {
		Reader r = new BufferedReader(new InputStreamReader(System.in));
		timer = ManagementFactory.getThreadMXBean();
		st = new StreamTokenizer(r);
		st.eolIsSignificant(true);
		if (scene != null)
			loadScene(scene);
	}

	public static void main(String[] args) {
		new TestCompare(args.length==1 ? args[0] : null).loop();
	}
}
