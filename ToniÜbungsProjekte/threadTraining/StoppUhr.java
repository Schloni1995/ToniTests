package threadTraining;

public class StoppUhr extends Thread
{
	@Override
	public void run()
	{
		long zeit;
		final long startzeit = System.currentTimeMillis() / 1000;
		while (true)
		{
			try
			{
				Thread.sleep(1000);/* 1Sekunde */
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
			zeit = System.currentTimeMillis() / 1000 - startzeit;
			System.out.println(zeit);
		}

	}

	public static void main(final String[] args)
	{
		new StoppUhr().start();
	}

}
