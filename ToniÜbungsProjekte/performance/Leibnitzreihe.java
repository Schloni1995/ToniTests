package performance;

public class Leibnitzreihe
{

	static long loopMax = 10000000;
	final static double nano = Math.pow(10, -9);

	public static void main(final String[] args)
	{
		long start, end;
		System.out.println("Die Leibnizsche Zahlenreihe");
		start = System.nanoTime();
		leibnitz1();
		end = System.nanoTime();
		System.out.println("1. " + ((end - start) * nano) + " s");
		/****************************************/

		start = System.nanoTime();
		leibnitz2();
		end = System.nanoTime();
		System.out.println("2. " + ((end - start) * nano) + " s");
	}

	private static void leibnitz1()
	{
		double zahl = 0;
		double x = 1;
		boolean b = false;

		int i = 1;
		while (i <= loopMax)
		{
			if (b == false)
			{
				zahl = zahl + (1 / x);
				b = true;
				// System.out.println("zahl + (1/" + x + ")" + " PI=zahl*4 == "
				// + zahl * 4);
			}
			else if (b == true)
			{
				zahl = zahl - (1 / x);
				b = false;
				// System.out.println("zahl - (1/" + x + ")" + " PI=zahl*4 == "
				// + zahl * 4);

			}
			i++;
			x = x + 2;
		}
		System.out.println("Pi ist ungefaehr " + (4.0 * zahl));
	}

	private static void leibnitz2()
	{
		/*
		 * Die unendliche (Leibnitz-)Reihe 1 - 1/3 + 1/5 - 1/7 + 1/9 - ... hat
		 * den Wert Pi/4. Schreiben Sie ein Programm, das für ein festes n mit
		 * Hilfe der Formel einen Näherungswert für Pi berechnet.
		 */

		// uebrigens: der Mann hiess Leibniz und nicht, wie der Keks, Leibnitz

		final long n = loopMax;
		double pi4tel = 0.0;

		// fuer jedes i von 1 bis n bilde 1.0 / (2 * i - 1) und addiere
		// bzw. subtrahiere, je nachdem ob i ungerade oder gerade ist
		for (int i = 1; i <= n; ++i)
			if (i % 2 == 1) pi4tel += 1.0 / (2 * i - 1);
			else
				pi4tel -= 1.0 / (2 * i - 1);

		// die Reihe ergibt Pi/4 nicht Pi!
		System.out.println("Pi ist ungefaehr " + (4.0 * pi4tel));
	}

}
