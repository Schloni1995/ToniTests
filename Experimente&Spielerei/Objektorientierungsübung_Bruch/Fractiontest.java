package Objektorientierungsübung_Bruch;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Fractiontest
{

	static void dezAusgabe(final int a, final int b)
	{
		final double dez = new Integer(a).doubleValue() / b;
		System.out.println("Dezimalzahl: " + dez);
	}

	static FractionDefine getfractionInput()// Methode vom defininierten
											// Typ/Objekt
	{
		final FractionDefine d1 = new FractionDefine();// Zugriff auf Klasse
														// "FractionDefine" -->
														// Erstellung der
														// Variablen
		final String v = JOptionPane.showInputDialog(null, "Zähler eingeben: ", "Überschrift",
				JOptionPane.OK_CANCEL_OPTION);
		final String q = JOptionPane.showInputDialog(null, "Nenner eingeben: ", "Überschrift",
				JOptionPane.OK_CANCEL_OPTION);
		FractionDefine.anz++;
		if ((v != null) && (q != null))
		{
			d1.zaehler = Integer.parseInt(v);// Wertzuweisung
			d1.nenner = Integer.parseInt(q); // Variablen+Konvertierung
			d1.nenner = loesbarcheck(d1.nenner);
			d1.ausgabe();// Zugriff auf Methode
			FracOperation.extendAusgabe(d1.zaehler, d1.nenner);// Bruch
																// erweitern +
																// Ausgabe
			new ShortFrac().kurzAusgabe(d1.zaehler, d1.nenner);// Bruchkürzung(wenn
																// möglich) +
																// Ausgabe
			dezAusgabe(d1.zaehler, d1.nenner);// Ausgabe als Dezimalzahl
		}
		else
		{
			d1.zaehler = 0;
			d1.nenner = 1;
			d1.ausgabe();// Zugriff auf Methode
			dezAusgabe(d1.zaehler, d1.nenner);// Ausgabe als Dezimalzahl
			System.exit(0);
		}
		return d1;
	}

	static int loesbarcheck(int nenner)
	{
		if (nenner == 0)
		{
			System.err.println("ACHTUNG!!!");
			System.err.println("Nenner = 0 --> Nicht definiert!!!");
			System.err.println("Nenner wird inkrementiert(+1).");// Exception
																	// selber
																	// machen
			return nenner++;
		}
		else
			return nenner;
	}

	public static void main(final String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		final FractionDefine d1 = getfractionInput();
		new FracOperation().multAusgabe(d1.zaehler, d1.nenner);// Multiplizieren
																// mit einem
																// anderen Bruch
		System.out.println("Anzahl der Brüche: " + FractionDefine.anz);
		System.exit(0);
	}

}
