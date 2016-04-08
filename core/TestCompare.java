package core;
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
class TestCompare {
	protected static final double NANO_TO_SEC = 1E9;
	protected final ThreadMXBean timer;
	protected final StreamTokenizer st;
	protected Scene scene;

	/**
	 * Report a result
	 */
	protected void report(String descr, long ref_cpu, long ref_wallclock) {
		double cpu = (timer.getCurrentThreadCpuTime()-ref_cpu) / NANO_TO_SEC;
		double wallclock = (System.nanoTime()-ref_wallclock) / NANO_TO_SEC;
		System.out.printf("%-20s%.2f\t%.2f\n", descr, cpu, wallclock);
	}

	/**
	 * Discard the current line
	 */
	protected void discardLine() throws IOException {
		while (st.nextToken()!=StreamTokenizer.TT_EOF &&
				st.ttype!=StreamTokenizer.TT_EOL);
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

	protected void do_test() throws IOException {
		if (!match('"'))
			return;
		String heuristic = st.sval;
		match(StreamTokenizer.TT_EOL);
		if (scene == null) {
			System.out.println("Please load a scene first.");
			return;
		}
		Heuristic l[] = {new First(), new Random()};
		for (Heuristic h: l) {
			if (h.toString().toLowerCase().equals(heuristic)) {
				for (int i=0; i<20; i++)
					BSP.build(scene.segments, h);
				long t = timer.getCurrentThreadCpuTime();
				long w = System.nanoTime();
				BSP bsp = BSP.build(scene.segments, h);
				report("BSP", t, w);
				//TODO paint
				//TODO compare with theoretical complexity analysis
				System.out.printf("%-20s%d\n", "Height", bsp.height());
				System.out.printf("%-20s%d\n", "Nodes", bsp.nodes());
				return;
			}
		}
		System.out.println("Couldn't find "+heuristic);
	}

	/**
	 * Command interpreter loop
	 */
	public void loop() {
		System.out.print(">>> ");
		try {
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				if (st.ttype != StreamTokenizer.TT_WORD) {
					System.out.println("expected a command!");
					discardLine();
					continue;
				}
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
				System.out.print(">>> ");
			}
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
