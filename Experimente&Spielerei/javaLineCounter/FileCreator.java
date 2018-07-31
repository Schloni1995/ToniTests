package javaLineCounter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileCreator
{
	public static void createTxtFile(final String zielPfad, final String quellpfad, final long gesamtAnzZeilen,
			final long gesamtAnzZeichen, final long anzDateien, final long anzOrdner)
	{
		PrintWriter pWriter = null;
		try
		{
			pWriter = new PrintWriter(
					new BufferedWriter(new FileWriter(zielPfad + "Programmstatistik(" + Statics.dateString + ").txt")));
			pWriter.println("Stand: " + Statics.dateString + " " + Statics.timeString + "Uhr");
			pWriter.println();
			pWriter.println("\tDurchsuchter Pfad:\t " + quellpfad);
			pWriter.println("\tGesamtzeilen Quelltext:\t " + gesamtAnzZeilen);
			pWriter.println("\tGesamtzeichen Quelltext:\t " + gesamtAnzZeichen);
			pWriter.println("\tGesamtanzahl Dateien:\t " + anzDateien);
			pWriter.println("\tGesamtanzahl Ordner:\t " + anzOrdner);
		}
		catch (final IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			if (pWriter != null)
			{
				pWriter.flush();
				pWriter.close();
			}
		}
	}
}
