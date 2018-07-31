package performance.sortierVerfahren;

public class Mergesort
{

	static int[] liste;

	public static void main(final String[] args)
	{
		liste = new ArrayCreator(2000, 1, Integer.MAX_VALUE).getNewArray();
		final long start = System.currentTimeMillis();
		Mergesort.sort(0, liste.length - 1);
		System.out.println((System.currentTimeMillis() - start) * MILLI);
		//
		// for (final int element : liste)
		// System.out.println(element + " ");
	}

	private static int[] sort(final int l, final int r)
	{

		if (l < r)
		{
			final int q = (l + r) / 2;

			sort(l, q);
			sort(q + 1, r);
			merge(l, q, r);
		}
		return liste;
	}

	private static void merge(final int l, final int q, final int r)
	{
		final int[] arr = new int[liste.length];
		int i, j;
		for (i = l; i <= q; i++)
			arr[i] = liste[i];
		for (j = q + 1; j <= r; j++)
			arr[r + q + 1 - j] = liste[j];
		i = l;
		j = r;
		for (int k = l; k <= r; k++)
			if (arr[i] <= arr[j])
			{
				liste[k] = arr[i];
				i++;
			}
			else
			{
				liste[k] = arr[j];
				j--;
			}
	}

	public static final double MILLI = Math.pow(10, -3);
}
