package performance.sortierVerfahren;

public class ArrayCreator
{

	int[] newArray;

	public ArrayCreator(final int length, final int min, final int max)
	{
		newArray = new int[length];
		for (int i = 0; i < newArray.length; i++)
			newArray[i] = (int) (Math.random() * max) + min;
	}

	public int[] getNewArray()
	{
		return newArray;
	}

}
