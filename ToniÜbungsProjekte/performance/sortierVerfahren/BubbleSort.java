package performance.sortierVerfahren;

public class BubbleSort
{
	public static void sortiere(final int[] x)
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

	public static void main(final String[] args)
	{
		final int[] liste = new ArrayCreator(1000, 1, Integer.MAX_VALUE).getNewArray();
//		final int[] liste = { 0, 9, 4, 6, 2, 8, 5, 1, 7, 3 };
		
		sortiere(liste);
		for (final int element : liste)
			System.out.print(element + " ");
	}
}