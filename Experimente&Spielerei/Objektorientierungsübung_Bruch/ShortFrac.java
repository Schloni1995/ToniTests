package Objektorientierungs�bung_Bruch;

public class ShortFrac
{
	public int ggT(int a, int b)
	{
		while (b != 0)
			if (a > b) a = a - b;
			else
				b = b - a;
		return a;
	}

	void kurzAusgabe(final int a, final int b)
	{
		final int ergebnis = ggT(a, b);// berechne ggT mit der Funktion "ggt()"
		System.out.println("Der ggT von " + a + " und " + b + " ist: " + ergebnis);
		if (ergebnis != 1) System.out.println("Gek�rzt: " + (a / ergebnis) + "/" + (b / ergebnis));// Fehler
																									// division
																									// durch
																									// 0
																									// beim
																									// zweiten
																									// brcuh
	}
}
// Man teilt die gr��ere durch die kleinere Zahl.
// Geht die Division auf, ist der Divisor der ggT.
// Geht die Division nicht auf, bleibt ein Rest. Dieser Rest ist der neue
// Divisor.
// Der alte Divisor wird zum Dividenden. Nun setzt man das Verfahren fort.
// Nach endlich vielen Schritten erh�lt man den ggT.
// In manchen F�llen ist dies die Zahl 1, dann sind die Ausgangszahlen
// teilerfremd