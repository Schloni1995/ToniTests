package performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class CPUeater
{
	public static void main(String[] args) throws InterruptedException
	{
		args = new String[] { "4" };
		if (args.length != 1)
		{
			System.err.println("Please provide a single argument: the number of threads to run.");
			System.exit(1);
		}

		final int numThreads = Integer.parseInt(args[0]);

		if (numThreads < 1)
		{
			System.err.println("Number of threads should be at least 1");
			System.exit(1);
		}

		final LongAdder counter = new LongAdder();

		final List<CalculationThread> runningCalcs = new ArrayList<>();
		final List<Thread> runningThreads = new ArrayList<>();

		System.out.printf("Starting %d threads\n", numThreads);

		for (int i = 0; i < numThreads; i++)
		{
			final CalculationThread r = new CalculationThread(counter);
			final Thread t = new Thread(r);
			runningCalcs.add(r);
			runningThreads.add(t);
			t.start();
		}

		for (int i = 0; i < 15; i++)
		{
			counter.reset();
			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				break;
			}
			System.out.printf("[%d] Calculations per second: %d (%.2f per thread)\n", i, counter.longValue(),
					(double) (counter.longValue()) / numThreads);
		}

		for (int i = 0; i < runningCalcs.size(); i++)
		{
			runningCalcs.get(i).stop();
			runningThreads.get(i).join();
		}

	}

	public static class CalculationThread implements Runnable
	{
		private final Random rng;
		private final LongAdder calculationsPerformed;
		private boolean stopped;

		public CalculationThread(final LongAdder calculationsPerformed)
		{
			this.calculationsPerformed = calculationsPerformed;
			stopped = false;
			rng = new Random();
		}

		public void stop()
		{
			stopped = true;
		}

		@Override
		public void run()
		{
			while (!stopped)
			{
				final double r = rng.nextFloat();
				final double v = Math.sin(Math.cos(Math.sin(Math.cos(r))));
				calculationsPerformed.add(1);
			}
		}
	}
}
