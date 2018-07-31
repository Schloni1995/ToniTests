package spaceschaufel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KopierenPs1
{
	public KopierenPs1(final String zielPfad) throws IOException
	{
		// Ziel
		final String datum = LocalDate.now().toString();
		final Path sicherungsLaufwerk = Paths.get(zielPfad);
		Paths.get(sicherungsLaufwerk.toString(), datum);

		Paths.get("L:\\_geo");
		Paths.get("L:\\_Zeichnung");
		Paths.get("L:\\CAD-Tools");

		// Freien Speicherplatz abfragen
		final long freeSpace = sicherungsLaufwerk.toFile().getFreeSpace() / 1024l / 1024l / 1024l;

		// Ältesten Ordner suchen
		final List<LocalDate> daten = new ArrayList<>();
		Files.walk(sicherungsLaufwerk).filter(p -> p.toFile().getName().matches("([0-9]{4})(-[0-9]{2}){2}"))
				.forEach(e -> daten.add(LocalDate.parse(e.getFileName().toString())));
		Collections.sort(daten, (o1, o2) -> o1.compareTo(o2));
		final String ordername = daten.get(0).toString();
		final Path loeschpfad = Paths.get(sicherungsLaufwerk.toString(), ordername);

		// Ausgabe der Informationen
		System.out.println(String.format("Auf dem Laufwerk sind %d GB frei", freeSpace));
		System.out.println(String.format("Der älteste Ordner ist %s", loeschpfad.toString()));
	}

	public static void main(final String[] args)
	{
		Paths.get("\\\\rs5\\RimeTool\\Softwareentwicklung\\DatumTest\\");
		try
		{
			// new KopierenPs1(sicherungspfad.toString());
			new KopierenPs1("H:\\");
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

}
