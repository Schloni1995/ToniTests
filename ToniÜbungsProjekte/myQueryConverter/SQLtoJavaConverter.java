package myQueryConverter;

import javax.swing.JOptionPane;

public class SQLtoJavaConverter
{
	String nSQL = "", nJava = "";

	public SQLtoJavaConverter(final QueryKonverterDialog dialog, final String SQL, final String Java)
	{
		if (SQL.trim().equals("") && Java.trim().equals(""))
			JOptionPane.showMessageDialog(null, "Keine Eingaben getätigt", "FEHLER", JOptionPane.ERROR_MESSAGE);
		else
		{
			if (SQL.trim().equals(""))
			{
				nSQL = JavatoSQL(Java);
				dialog.getSqlArea().setText(nSQL);
			}

			if (Java.trim().equals(""))
			{
				nJava = SQLtoJava(SQL);
				dialog.getJavaArea().setText(nJava);
			}
		}
	}

	public String JavatoSQL(final String Java)
	{
		nSQL = Java.replace("\"", "").replace("+", "");
		return nSQL;
	}

	public String SQLtoJava(final String SQL)
	{
		final String[] zeilenSQL = SQL.split("\n");// Array mit jeder Zeile
		for (final String element : zeilenSQL)
			nJava = nJava + "\"" + element + " \"+\n";
		if (nJava.endsWith("+\n")) nJava = nJava.substring(0, nJava.length() - 2);

		return nJava;
	}
}
