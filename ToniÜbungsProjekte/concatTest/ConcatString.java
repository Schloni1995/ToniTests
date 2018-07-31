package concatTest;

class ConcatString implements Runnable
{
	private final int o;
	long start;
	private long conTime;

	public ConcatString(final int o)
	{
		this.o = o;
	}

	@Override
	public void run()
	{
		start = System.currentTimeMillis();
		for (int i = 0; i < o; i++)
		{
		}
		// System.out.println(s);
		conTime = System.currentTimeMillis() - start;
	}

	/** @return the conTime */
	public long getConTime()
	{
		return conTime;
	}
}