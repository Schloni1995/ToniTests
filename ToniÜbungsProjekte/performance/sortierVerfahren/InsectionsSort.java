package performance.sortierVerfahren;

public class InsectionsSort
{

	public static void main(final String[] args)
	{
		final int[] liste = new ArrayCreator(2000, 1, Integer.MAX_VALUE).getNewArray();
		final long start = System.currentTimeMillis();
		insertionSort(liste);
		System.out.println((System.currentTimeMillis() - start) * MILLI);
		//
		// for (final int element : liste)
		// System.out.println(element + " ");

	}

	public static final double MILLI = Math.pow(10, -3);

	public static int[] insertionSort(final int[] sortieren)
	{
		int temp;
		for (int i = 1; i < sortieren.length; i++)
		{
			temp = sortieren[i];
			int j = i;
			while (j > 0 && sortieren[j - 1] > temp)
			{
				sortieren[j] = sortieren[j - 1];
				j--;
			}
			sortieren[j] = temp;
		}
		return sortieren;
	}
}
