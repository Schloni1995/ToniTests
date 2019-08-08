package imageWatermarker;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Verwanzer
{

	private static final String pfadJPEG = "C:\\Users\\michaelnitzsche\\Desktop\\Wasserzeichen\\";

	public static void main(final String args[])
	{
		final File oldFile = new File(pfadJPEG + "A.JPG");
		final File txtFile = new File(pfadJPEG + "text.txt");
		final File newFile = new File(pfadJPEG + "A.JPG");
		// Ist die Batchfile, die deinen cmd-Befehl ausführt
		final File BatFile = new File(pfadJPEG + "Bild.BAT");

		try
		{
			// wenn die Batchfile schon existiert, wird diese überschrieben
			if (BatFile.exists()) BatFile.delete();
			FileWriter fileWriter;
			fileWriter = new FileWriter(BatFile, true);

			// Runtime.getRuntime().exec("cmd /C COPY \""
			// +oldFile.getAbsolutePath() +"\" \""+txtFile.getAbsolutePath()+"\"
			// \"" +newFile.getAbsolutePath()+"\"");

			// Text für die Batchfile
			final String c = "cmd /C COPY/b  " + oldFile.getAbsolutePath() + " +  " + txtFile.getAbsolutePath() + " /b "
					+ newFile.getAbsolutePath() + "";

			// Hier wird es in die BatchFile geschrieben
			fileWriter.write(c);
			fileWriter.flush();
			fileWriter.close();

			System.out.println(c);

			// Batchfile wird ausgeführt
			Desktop.getDesktop().open(new File(pfadJPEG + "Bild.BAT"));
		}
		catch (final IOException e)
		{

			e.printStackTrace();
		}
	}
}
