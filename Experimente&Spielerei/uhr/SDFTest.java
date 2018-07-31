package uhr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class SDFTest
{
	public static void main(final String[] args)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1900);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		final Date date = cal.getTime();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		System.out.println("Converted Year: " + sdf.format(date));

		final String yourDateString = "1900-01-01";
		try
		{
			final Date myDate = sdf.parse(yourDateString);
			System.out.println(myDate);
			JOptionPane.showMessageDialog(null, sdf.format(date) + "\r\n" + myDate);
		}
		catch (final ParseException e)
		{

			e.printStackTrace();
		}
	}
}
