package performance.primzahlTest;

import java.math.BigInteger;

public class MillerRabin
{

	public static void main(String[] args)
	{
		args = new String[] { "64646612454013", "100000" };
		final BigInteger n = new BigInteger(args[0]);
		final int certainty = Integer.parseInt(args[1]);
		System.out.println(n.toString() + " is " + (n.isProbablePrime(certainty) ? "probably prime" : "composite"));
	}

	// this is the RabinMiller test, deterministically correct for n <
	// 341,550,071,728,321
	// http://rosettacode.org/wiki/Miller-Rabin_primality_test#Python:_Proved_correct_up_to_large_N
	public static boolean isPrime(final BigInteger n, final int precision)
	{

		if (n.compareTo(new BigInteger("341550071728321")) >= 0) return n.isProbablePrime(precision);

		final int intN = n.intValue();
		if (intN == 1 || intN == 4 || intN == 6 || intN == 8) return false;
		if (intN == 2 || intN == 3 || intN == 5 || intN == 7) return true;

		final int[] primesToTest = getPrimesToTest(n);
		if (n.equals(new BigInteger("3215031751"))) return false;
		BigInteger d = n.subtract(BigInteger.ONE);
		BigInteger s = BigInteger.ZERO;
		while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
		{
			d = d.shiftRight(1);
			s = s.add(BigInteger.ONE);
		}
		for (final int a : primesToTest)
			if (try_composite(a, d, n, s)) return false;
		return true;
	}

	public static boolean isPrime(final BigInteger n)
	{
		return isPrime(n, 100);
	}

	public static boolean isPrime(final int n)
	{
		return isPrime(BigInteger.valueOf(n), 100);
	}

	public static boolean isPrime(final long n)
	{
		return isPrime(BigInteger.valueOf(n), 100);
	}

	private static int[] getPrimesToTest(final BigInteger n)
	{
		if (n.compareTo(new BigInteger("3474749660383")) >= 0) return new int[] { 2, 3, 5, 7, 11, 13, 17 };
		if (n.compareTo(new BigInteger("2152302898747")) >= 0) return new int[] { 2, 3, 5, 7, 11, 13 };
		if (n.compareTo(new BigInteger("118670087467")) >= 0) return new int[] { 2, 3, 5, 7, 11 };
		if (n.compareTo(new BigInteger("25326001")) >= 0) return new int[] { 2, 3, 5, 7 };
		if (n.compareTo(new BigInteger("1373653")) >= 0) return new int[] { 2, 3, 5 };
		return new int[] { 2, 3 };
	}

	private static boolean try_composite(final int a, final BigInteger d, final BigInteger n, final BigInteger s)
	{
		final BigInteger aB = BigInteger.valueOf(a);
		if (aB.modPow(d, n).equals(BigInteger.ONE)) return false;
		for (int i = 0; BigInteger.valueOf(i).compareTo(s) < 0; i++)
			// if pow(a, 2**i * d, n) == n-1
			if (aB.modPow(BigInteger.valueOf(2).pow(i).multiply(d), n).equals(n.subtract(BigInteger.ONE))) return false;
		return true;
	}
}