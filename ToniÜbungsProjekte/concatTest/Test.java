package concatTest;

public class Test
{
	public int o = 10000;
	public static final double MILLI = Math.pow(10, -3);

	public static void main(final String[] args)
	{
		try
		{
			new Test();
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public Test() throws InterruptedException
	{
		final ConcatString cs = new ConcatString(o);
		final BuildString bs = new BuildString(o);
		final Thread concat = new Thread(cs);
		final Thread build = new Thread(bs);
		concat.start();
		build.start();

		// while (!concat.isInterrupted() || !build.isInterrupted())
		// Thread.sleep(1000);
		while (concat.isAlive() || build.isAlive())
			Thread.sleep(1000);

		System.out.println("Concattime = " + cs.getConTime() * MILLI + " s");
		System.out.println("Buildingtime = " + bs.getSbTime() * MILLI + " s");
	}
}
