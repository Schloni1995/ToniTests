package performance;

import java.io.IOException;

public class Ackermann
{
	final static double NANO = 0.000000001d;

	public static long ackITT(long n, long m)
	{
		while (n != 0)
		{
			if (m == 0) m = 1;
			else
				m = ackITT(n, m - 1);
			n--;
		}
		return m + 1;
	}

	public static long ackREK(final long n, final long m)
	{
		if (n == 0) return m + 1;
		else

		if (m == 0) return ackREK(n - 1, 1);
		else
			return ackREK(n - 1, ackREK(n, m - 1));

	}

	public static void main(final String args[])
	{

		try
		{
			test();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

	}

	private static void test() throws IOException
	{
		final int x = 3, y = 0;
		long end, start = System.nanoTime();
		ackREK(x, y);
		end = System.nanoTime();
		System.out.println("REK " + (end - start) * NANO);

		start = System.nanoTime();
		ackITT(x, y);
		end = System.nanoTime();
		System.out.println("ITT " + (end - start) * NANO);
	}

}
