package arbeitsZeiten;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import rtModuls.de.rtObjects.gui.RTButton;
import rtModuls.de.rtObjects.gui.RTConnection;
import rtModuls.de.rtObjects.gui.RTDialog;
import rtModuls.de.rtObjects.gui.RTTableModel;
import rtModuls.de.rtObjects.gui.RTTableRenderer;
import rtModuls.de.rtObjects.rtFunction.RTCloseDialogFunction;

public class OneDayWorkingTime extends RTDialog
{
	private static final long serialVersionUID = 1L;

	public static void main(final String[] args)
	{
		new OneDayWorkingTime("Toni Zinke", "15.12.2017", 135);
	}

	private JScrollPane scPAne;
	private JTable timexTagZeitenTable;
	private final String[] colTime = { "KommtArt", "Kommt", "GehtArt", "Geht" };
	private String[][] values;
	private final int loginNr;
	private final String datum;

	private JButton closeButton;

	public OneDayWorkingTime(final String name, final String datum, final int persNr)
	{
		loginNr = persNr;
		this.datum = datum;

		setTitle("gestempelte Zeiten von " + name + " am " + datum);
		setSizeRT(400, 200);
		add(getScPane(), BorderLayout.CENTER);
		add(getCloseButton(), BorderLayout.SOUTH);
		setModal(true);
		setVisible(true);
	}

	private JButton getCloseButton()
	{
		if (closeButton == null)
		{
			final ActionListener al = arg0 -> new RTCloseDialogFunction(OneDayWorkingTime.this);
			closeButton = new RTButton("Schlieﬂen", 400, 25, al);
		}
		return closeButton;
	}

	private JScrollPane getScPane()
	{
		if (scPAne == null) scPAne = new JScrollPane(getTimexTAgZeitenTAble());
		return scPAne;
	}

	public String[][] getTimexTagValues(final String datum, final String werker)
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
				+ "inner join Zeitkonto as B on Z.id = B.idZK_Head " + "where M.altpersnr = '" + werker
				+ "' and Z.Datum = '" + datum + "'" + "order by B.Akommt";

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
		values = new String[v1.size()][4];
		for (int i = 0; i < values.length; i++)
		{
			values[i][0] = v1.get(i);
			values[i][1] = v2.get(i);
			values[i][2] = v3.get(i);
			values[i][3] = v4.get(i);
		}
		return values;
	}

	private JTable getTimexTAgZeitenTAble()
	{
		if (timexTagZeitenTable == null)
		{
			timexTagZeitenTable = new JTable();
			timexTagZeitenTable.setName("Ueberstunden");
			timexTagZeitenTable.setRowHeight(22);
			timexTagZeitenTable.setDefaultRenderer(Object.class, new RTTableRenderer(""));

		}

		timexTagZeitenTable.setModel(new RTTableModel(colTime, getTimexTagValues(datum, loginNr + "")));

		return timexTagZeitenTable;
	}
}
