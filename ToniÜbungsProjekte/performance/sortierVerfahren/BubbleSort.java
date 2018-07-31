package performance.sortierVerfahren;

public class BubbleSort
{
	public static final double MILLI = Math.pow(10, -3);

	public static void main(final String[] args)
	{
		final int[] liste = new ArrayCreator(2000, 1, Integer.MAX_VALUE).getNewArray();
		final long start = System.currentTimeMillis();
		bubblesort(liste);
		System.out.println((System.currentTimeMillis() - start) * MILLI);

		for (final int element : liste)
			System.out.println(element + " ");
	}

	public static void bubblesort(final int[] x)
	{
		boolean unsortiert = true;
		int temp;

		while (unsortiert)
		{
			unsortiert = false;
			for (int i = 0; i < x.length - 1; i++)
				if (x[i] > x[i + 1])
				{
					temp = x[i];
					x[i] = x[i + 1];
					x[i + 1] = temp;
					unsortiert = true;
				}
		}
	}
}