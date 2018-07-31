package javaLineCounter;

import java.io.File;

public class PfadDurchsucher
{
	public long anzDateien, anzOrdner;
	public long gesamtAnzZeilen, gesamtAnzZeichen;
	private final String quellpfad;

	public static void main(final String[] args)
	{
		new PfadDurchsucher(Statics.startPath);
	}

	public PfadDurchsucher(final String quellpfad)
	{
		long start, end;
		start = System.nanoTime();

		this.quellpfad = quellpfad;
		getDir(new File(this.quellpfad).listFiles());

		end = System.nanoTime();

		if (Gui.debugMode) Gui.dc.toConsole("PfadDurchsucher: " + (end - start) * Statics.NANO);
		else
			System.out.println("PfadDurchsucher: " + (end - start) * Statics.NANO);
	}

	private void getDir(final File[] path)
	{
		int zeilenANZ, zeichenANZ;
		for (final File f : path)
			if (f.isDirectory())
			{
				if (Gui.debugMode) Gui.dc.toConsole("Ordner: " + f.getName());
				else
					System.out.println("Ordner: " + f.getName());
				anzOrdner++;
				getDir(f.listFiles());
			}
			else
			{
				if (Gui.debugMode)
				{
					Gui.dc.toConsole("Datei: " + f.getName());
					Gui.dc.toConsole("--> " + f.getPath() + " <--");
				}

				else
				{
					System.out.println("Datei: " + f.getName());
					System.out.println("--> " + f.getPath() + " <--");
				}

				anzDateien++;
				if (f.getName().endsWith(".java") || f.getName().endsWith(".kt"))
				{
					if (Gui.debugMode) Gui.dc.toConsole(f.getName());
					else
						System.err.println(f.getName());
					zeilenANZ = TextCounter.getZeilenAnzahl(f.getPath());
					zeichenANZ = TextCounter.getZeichenAnzahl(f.getPath());
				}
				else
				{
					zeilenANZ = 0;
					zeichenANZ = 0;
				}
				gesamtAnzZeilen += zeilenANZ;
				gesamtAnzZeichen += zeichenANZ;
			}
	}

	/** @return the anzDateien */
	public long getAnzDateien()
	{
		return anzDateien;
	}

	/** @return the anzOrdner */
	public long getAnzOrdner()
	{

		return anzOrdner;
	}

	/** @return the gesamtAnzZeilen */
	public long getGesamtAnzZeilen()
	{

		return gesamtAnzZeilen;
	}

	/** @return the gesamtAnzZeichen */
	public long getGesamtAnzZeichen()
	{

		return gesamtAnzZeichen;
	}

	/** @return the quellpfad */
	public String getQuellpfad()
	{
		return quellpfad;
	}

}
