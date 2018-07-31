package performance.sortierVerfahren;

public class Selectionsort
{
	public static final double MILLI = Math.pow(10, -3);

	public static void main(final String[] args)
	{
		final int[] liste = new ArrayCreator(2000, 1, Integer.MAX_VALUE).getNewArray();
		final long start = System.currentTimeMillis();
		selectionsort(liste);
		System.out.println((System.currentTimeMillis() - start) * MILLI);

		for (final int element : liste)
			System.out.println(element + " ");

	}

	public static int[] selectionsort(final int[] sortieren)
	{
		for (int i = 0; i < sortieren.length - 1; i++)
			for (int j = i + 1; j < sortieren.length; j++)
				if (sortieren[i] > sortieren[j])
				{
					final int temp = sortieren[i];
					sortieren[i] = sortieren[j];
					sortieren[j] = temp;
				}

		return sortieren;
	}
}