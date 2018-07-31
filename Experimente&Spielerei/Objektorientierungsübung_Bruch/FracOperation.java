package Objektorientierungsübung_Bruch;

import javax.swing.JOptionPane;

public class FracOperation
{

	static void extendAusgabe(int a, int b)
	{
		int fak;
		final int dialogExtend = JOptionPane.showConfirmDialog(null, "Möchtest du deinen Bruch erweitern?", null,
				JOptionPane.YES_NO_OPTION);
		if (dialogExtend == JOptionPane.YES_OPTION)
		{
			fak = Integer.parseInt(JOptionPane.showInputDialog(null, "Faktor zum Erweitern  eingeben: "));
			a *= fak;
			b *= fak;
			System.out.println("Bruch multipliziert mit " + fak + " = " + a + "/" + b);
		}

		else
		{
			fak = 1;
			System.out.println("Standardfaktor = " + fak);
		}
	}

	void multAusgabe(final int d1z, final int d1n)
	{
		final FractionDefine d1 = new FractionDefine();

		final int dialogMult = JOptionPane.showConfirmDialog(null,
				"Möchtest du mit einem anderen Bruch multiplizieren?", null, JOptionPane.YES_NO_OPTION);
		if (dialogMult == JOptionPane.YES_OPTION)
		{
			System.out.println("___________________________________________________________");
			final FractionDefine d2 = new FractionDefine();
			d2.zaehler = Integer.parseInt(JOptionPane.showInputDialog(null, "2. Zähler eingeben: ", "Überschrift2",
					JOptionPane.OK_CANCEL_OPTION));// Wertzuweisung
			d2.nenner = Integer.parseInt(JOptionPane.showInputDialog(null, "2. Nenner eingeben: ", "Überschrift2",
					JOptionPane.OK_CANCEL_OPTION)); // Variablen+Konvertierung
			FractionDefine.anz++;
			d2.nenner = Fractiontest.loesbarcheck(d2.nenner);
			d2.ausgabe();
			FracOperation.extendAusgabe(d2.zaehler, d2.nenner);// Bruch
																// erweitern +
																// Ausgabe
			final ShortFrac SF = new ShortFrac();
			SF.kurzAusgabe(d2.zaehler, d2.nenner);// Bruchkürzung(wenn möglich)
													// + Ausgabe
			Fractiontest.dezAusgabe(d2.zaehler, d2.nenner);// Ausgabe als
															// Dezimalzahl
			System.out.println("___________________________________________________________");
			d1.zaehler = d2.zaehler * d1z;
			d1.nenner = d2.nenner * d1n;
			SF.kurzAusgabe(d1.zaehler, d1.nenner);
			Fractiontest.dezAusgabe(d1.zaehler, d1.nenner);
			SF.kurzAusgabe(d2.zaehler, d2.nenner);// Bruchkürzung(wenn möglich)
													// + Ausgabe
			System.out.println("Neuer Bruch durch Multiplikation von " + d1z + "/" + d1n + " * " + d2.zaehler + "/"
					+ d2.nenner + " = " + d1.zaehler + "/" + d1.nenner);
		}
	}
}
