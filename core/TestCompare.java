package core;
import java.util.Scanner;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Main class for the text-based heuristic comparison tool
 */
class TestCompare {
	protected static final NANO_TO_SEC = 1E9;
	protected final ThreadMXBean timer;

	protected void report(String descr, long ref_cpu, long ref_wallclock) {
		double cpu = (timer.getCurrentThreadCpuTime()-ref_cpu) / NANO_TO_SEC;
		double wallclock = (System.nanoTime()-ref_wallclock) / NANO_TO_SEC;
		System.out.printf("%-20s%.2f\t%.2f\n", descr, cpu, wallclock);
	}

	public TestCompare(String scene) {
		Scanner sc = new Scanner(System.in);
		timer = ManagementFactory.getThreadMXBean();
		try {
			while (true) {
				System.out.print(">>> ");
				sc.next();
				long t = timer.getCurrentThreadCpuTime();
				long w = System.nanoTime();
				Thread.currentThread().sleep(2000);
				report("sleep 2K", t, w);
			}
		}
		catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		new TestCompare(args.length==1 ? args[0] : null);
	}
}
