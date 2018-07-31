package uhr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import rtModuls.de.rtObjects.gui.RTColors;

public class UhrPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static void main(final String[] args)
	{
		new UhrPanel();
	}

	boolean pause1, pause2, frei;

	public UhrPanel()
	{
		setPreferredSize(new Dimension(500, 250));

		final JLabel daTum = new JLabel();
		final JLabel uhrZeit = new JLabel();

		daTum.setForeground(RTColors.getRimeWeiss());
		uhrZeit.setForeground(RTColors.getRimeWeiss());
		daTum.setHorizontalAlignment(SwingConstants.CENTER);
		daTum.setVerticalAlignment(SwingConstants.BOTTOM);
		if (getPreferredSize().width < 600)
		{
			daTum.setFont(new Font("Comic Sans MS", 0, 40));
			uhrZeit.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
		}
		else
		{
			daTum.setFont(new Font("Comic Sans MS", 0, 80));
			uhrZeit.setFont(new Font("Comic Sans MS", Font.BOLD, 200));
		}
		uhrZeit.setHorizontalAlignment(SwingConstants.CENTER);
		uhrZeit.setVerticalAlignment(SwingConstants.CENTER);
		final Timer timer = new Timer(1000, e ->
		{
			// aktuelleZeit
			final SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm:ss");
			// aktuellesDatum
			final SimpleDateFormat dateSDF = new SimpleDateFormat("EEEE, dd.MM.yyyy");
			final SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");

			final SimpleDateFormat dateNtime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

			// aktuellerTag
			final SimpleDateFormat day = new SimpleDateFormat("EEEE");

			final String dayNameString = day.format(new Date());
			final String aktuelleZeitString = timeSDF.format(new Date());
			final String dayString = dateSDF.format(new Date());

			Date aktuelleZeit = null;
			Date um6Uhr = null;
			Date halb7Uhr = null;
			Date um3Uhr = null;
			Date halb4Uhr = null;
			Date pause1Beginn = null;
			Date pause1Ende = null;
			Date pause2Beginn = null;
			Date pause2Ende = null;

			try
			{
				aktuelleZeit = new Date();
				um6Uhr = dateNtime.parse(date.format(new Date()) + " 06:00:00");
				halb7Uhr = dateNtime.parse(date.format(new Date()) + " 06:30:00");
				um3Uhr = dateNtime.parse(date.format(new Date()) + " 15:00:00");
				halb4Uhr = dateNtime.parse(date.format(new Date()) + " 15:30:00");

				pause1Beginn = dateNtime.parse(date.format(new Date()) + " 08:30:00");
				pause1Ende = dateNtime.parse(date.format(new Date()) + " 09:00:00");
				pause2Beginn = dateNtime.parse(date.format(new Date()) + " 12:30:00");
				pause2Ende = dateNtime.parse(date.format(new Date()) + " 13:00:00");
			}
			catch (final ParseException e1)
			{
				e1.printStackTrace();
			}

			uhrZeit.setText(aktuelleZeitString);
			daTum.setText(dayString);

			if (aktuelleZeit.after(pause1Beginn) && aktuelleZeit.before(pause1Ende)) pause1 = true;
			else
				pause1 = false;
			if (aktuelleZeit.after(pause2Beginn) && aktuelleZeit.before(pause2Ende)) pause2 = true;
			else
				pause2 = false;

			if (dayNameString.equals("Montag") ^ dayNameString.equals("Mittwoch") ^ dayNameString.equals("Freitag"))
			{
				if (aktuelleZeit.before(um6Uhr) || aktuelleZeit.after(um3Uhr)) frei = true;
				else
					frei = false;
				if (frei ^ pause1 ^ pause2)
				{
					daTum.setForeground(Color.red);
					uhrZeit.setForeground(Color.red);
				}
				else
				{
					daTum.setForeground(RTColors.getRimeWeiss());
					uhrZeit.setForeground(RTColors.getRimeWeiss());
				}
			}
			else if (dayNameString.equals("Dienstag") ^ dayNameString.equals("Donnerstag"))
			{
				if (aktuelleZeit.before(halb7Uhr) || aktuelleZeit.after(halb4Uhr)) frei = true;
				else
					frei = false;
				if (frei ^ pause1 ^ pause2)
				{
					daTum.setForeground(Color.red);
					uhrZeit.setForeground(Color.red);
				}
				else
				{
					daTum.setForeground(RTColors.getRimeWeiss());
					uhrZeit.setForeground(RTColors.getRimeWeiss());
				}
			}
		});
		timer.start();
		add(uhrZeit, BorderLayout.CENTER);
		add(daTum, BorderLayout.SOUTH);

		setVisible(true);
	}
}