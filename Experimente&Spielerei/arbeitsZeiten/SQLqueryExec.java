package arbeitsZeiten;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rtModuls.de.rtObjects.gui.RTConnection;

public class SQLqueryExec
{
	public static void main(final String[] args)
	{
		new SQLqueryExec().getTimeTableValuesByTimeIX(12, 135, 2017);
	}

	public String[] getPersonToNumber(final int nr)
	{
		final Connection con = new RTConnection().getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		String name = "";
		String vorname = "";
		try
		{
			final String qry = "use AFPS; " + "select top 1 Name, Vorname from Personal where Nr = " + nr;

			System.out.println("getPersonToNumber" + "TableValues: " + qry);
			stmt = con.createStatement();
			rs = stmt.executeQuery(qry);
			while (rs.next())
			{
				name = rs.getString("Name");
				vorname = rs.getString("Vorname");
			}
			rs.close();
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}
		return new String[] { name, vorname };
	}

	public String[][] getTimeTableValues(final int mon, final int pers, final int year)
	{
		final Connection con = new RTConnection().getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		final ArrayList<Date> datum = new ArrayList<>();
		final ArrayList<String> wochentag = new ArrayList<>();
		final ArrayList<String> ueberstunden = new ArrayList<>();
		final ArrayList<String> ueberstundeninMin = new ArrayList<>();
		try
		{
			final String qry = "use RimeTool;" + " SELECT" + "        Datum,"
					+ "        DATENAME(WEEKDAY,Datum) as Wochentag," + "        Zu25UeS as Überstunden,"
					+ "        ROUND(Zu25UeS*(0.6),2)*100 as ÜberstundenInMin" + "    FROM" + "        RimeLohn "
					+ "    where" + "        Monat = '" + new DecimalFormat("00").format(mon) + "' "
					+ "        and Jahr = '" + year + "' " + "        and Persnr = '" + pers + "' "
					+ "        and Zeit1 <> '_null' " + "    order by Datum";

			System.out.println("getTime" + "TableValues: " + qry);
			stmt = con.createStatement();
			rs = stmt.executeQuery(qry);
			while (rs.next())
			{
				datum.add(rs.getDate("Datum"));
				wochentag.add(rs.getString("Wochentag"));
				ueberstunden.add(rs.getString("Überstunden"));
				ueberstundeninMin.add(rs.getString("ÜberstundenInMin"));
			}
			rs.close();
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		final String[][] values = new String[datum.size()][10];
		for (int i = 0; i < values.length; i++)
		{
			values[i][0] = sdf.format(datum.get(i));
			values[i][1] = wochentag.get(i);
			values[i][2] = ueberstunden.get(i);
			values[i][3] = ueberstundeninMin.get(i);
		}

		return values;
	}

	public String[][] getTimeTableValuesByTimeIX(final int mon, final int pers, final int year)
	{
		final Connection con = new RTConnection().getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		final ArrayList<Date> datum = new ArrayList<>();
		final ArrayList<String> kommtArt = new ArrayList<>();
		final ArrayList<String> aKommt = new ArrayList<>();
		final ArrayList<String> gehtArt = new ArrayList<>();
		final ArrayList<String> aGeht = new ArrayList<>();
		final ArrayList<String> Abwesenheit = new ArrayList<>();
		final ArrayList<String> ueberstunden = new ArrayList<>();
		final ArrayList<String> ueberstundenInMin = new ArrayList<>();
		try
		{
			final String qry = "use Time; \r\n" + "select distinct \r\n" + "		Z.Datum,\r\n"
					+ "        B.kommtart,\r\n" + "		AKommt,\r\n" + "        B.gehtart,\r\n" + "        AGeht,\r\n"
					+ "        case \r\n"
					+ "            when U.AbwesenheitTyp is null then (case when F.Bezeichnung is null then '' else F.Bezeichnung end)  collate Latin1_General_CS_AS else U.AbwesenheitTyp end\r\n"
					+ "			as Abwesenheit,\r\n" + "        case \r\n"
					+ "            when RL.Zu25UeS is null then 9999 else RL.Zu25UeS end as Überstunden,\r\n"
					+ "			ROUND(RL.Zu25UeS*(0.6),2)*100 as ÜberstundenInMin\r\n"
					+ "    from Mitarbeiter as M \r\n" + "    left join ZK_Head as Z on M.persnr = Z.Persnr   \r\n"
					+ "    inner join Zeitkonto as B on Z.id = B.idZK_Head   \r\n"
					+ "    left join Afps.dbo.Urlaub as U on U.Personal_Nr = M.altpersnr and  U.Datum = Z.Datum  \r\n"
					+ "    left join RimeTool.dbo.Feiertage as F on F.Datum  = Z.Datum  \r\n"
					+ "    left join Afps.dbo.Personal as P on P.Nr = M.altpersnr  \r\n"
					+ "    left join RimeTool.dbo.SchichtplanUeberstunden as SU on SU.PersNr = M.altpersnr and Z.Datum = SU.Datum and SU.UeberstundenGenehmigt = 1  \r\n"
					+ "    left join RimeTool.dbo.RimeLohn as RL on RL.PersNr = M.altpersnr and RL.Datum = Z.Datum  \r\n"
					+ "    where\r\n" + "        M.altpersnr =" + pers + " \r\n" + "		and DATEPART(MM,Z.Datum) ="
					+ mon + "\r\n" + "		and DATENAME(YEAR,Z.Datum) =" + year + "\r\n"
					+ "	order by Z.Datum, AKommt";

			System.out.println("getTimeTableValuesByTimeIX: " + qry);
			stmt = con.createStatement();
			rs = stmt.executeQuery(qry);
			while (rs.next())
			{
				datum.add(rs.getDate("Datum"));
				kommtArt.add(rs.getString("kommtart"));
				aKommt.add(rs.getString("AKommt"));
				gehtArt.add(rs.getString("gehtart"));
				aGeht.add(rs.getString("AGeht"));
				Abwesenheit.add(rs.getString("Abwesenheit"));
				ueberstunden.add(rs.getString("Überstunden"));
				ueberstundenInMin.add(rs.getString("ÜberstundenInMin"));
			}
			rs.close();
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		final String[][] values = new String[datum.size()][8];
		for (int i = 0; i < values.length; i++)
		{
			values[i][0] = sdf.format(datum.get(i));
			values[i][1] = kommtArt.get(i);
			values[i][2] = aKommt.get(i);
			values[i][3] = gehtArt.get(i);
			values[i][4] = aGeht.get(i);
			values[i][5] = Abwesenheit.get(i);
			values[i][6] = ueberstunden.get(i);
			values[i][7] = ueberstundenInMin.get(i);
		}

		return values;
	}

	public String[][] getTimexTagValues(final String datum, final int persNr)
	{
		final ArrayList<String> v1 = new ArrayList<>();
		final ArrayList<String> v2 = new ArrayList<>();
		final ArrayList<String> v3 = new ArrayList<>();
		final ArrayList<String> v4 = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		final Connection con = new RTConnection().getConnection();

		final String qry = "use Time; " + "select B.kommtart, B.AKommt,B.gehtart,B.AGeht from "
				+ "Mitarbeiter as M left join ZK_Head as Z on M.persnr = Z.Persnr "
				+ "inner join Zeitkonto as B on Z.id = B.idZK_Head " + "where M.altpersnr = " + persNr
				+ " and Z.Datum = '" + datum + "'" + "order by B.Akommt";

		try
		{
			System.out.println();
			stmt = con.createStatement();
			rs = stmt.executeQuery(qry);
			while (rs.next())
			{
				v1.add(rs.getString(1));
				v2.add(rs.getString(2));
				v3.add(rs.getString(3));
				v4.add(rs.getString(4));
			}
			rs.close();
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}
		final String[][] values = new String[v1.size()][4];
		for (int i = 0; i < values.length; i++)
		{
			values[i][0] = v1.get(i);
			values[i][1] = v2.get(i);
			values[i][2] = v3.get(i);
			values[i][3] = v4.get(i);
		}
		return values;
	}

}
