package javaLineCounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextCounter
{

	public static int getZeichenAnzahl(final String quellpfad)
	{
		String zeile;
		int anzahl_Zeichen = 0;
		try
		{
			final BufferedReader in = new BufferedReader(new FileReader(quellpfad));
			while ((zeile = in.readLine()) != null)
				if (zeile.trim().length() > 0) anzahl_Zeichen += zeile.length();
			in.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return anzahl_Zeichen;
	}

	public static int getZeilenAnzahl(final String quellpfad)// Methode f�r
																// Zeilenanzahl
	{
		String zeile;
		int anzahl_Zeilen = 0;
		try
		{
			final BufferedReader in = new BufferedReader(new FileReader(quellpfad));
			while ((zeile = in.readLine()) != null)
				if (zeile.trim().length() > 0) anzahl_Zeilen++;
			in.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return anzahl_Zeilen;
	}
}
