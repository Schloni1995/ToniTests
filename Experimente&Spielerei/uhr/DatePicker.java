package uhr;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public class DatePicker extends JPanel
{

	private static final long serialVersionUID = 1L;

	public static void main(final String[] args)
	{
		final JFrame frame = new JFrame("Date Picker");
		final Container pane = frame.getContentPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(450, 250));
		pane.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(new JLabel("Datum: "));
		final JTextField testDate = new JTextField(10);
		pane.add(testDate);
		final DatePicker dp = new DatePicker();
		final ImageIcon ii = dp.getImage();
		// System.out.println(ii.getIconWidth());
		// System.out.println(ii.getIconHeight());
		final JButton datePicker = new JButton(ii);
		pane.add(datePicker);
		datePicker.setPreferredSize(new Dimension(30, 24));
		datePicker.setMargin(new Insets(0, 0, 0, 0));
		datePicker.addActionListener(e ->
		{
			dp.setDate(testDate.getText());
			dp.popupShow(datePicker);
		});
		dp.addPopupListener(e ->
		{
			testDate.setText(dp.getFormattedDate());
			dp.popupHide();
		});
		frame.pack();
		frame.setFocusable(true);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	protected boolean controlsOnTop;

	protected boolean removeOnDaySelection;

	protected Calendar currentDisplayDate;
	protected JButton prevMonth;
	protected JButton nextMonth;
	protected JButton prevYear;

	protected JButton nextYear;

	protected JTextField textField;

	protected List<ActionListener> popupListeners = new ArrayList<>();

	protected Popup popup;
	protected SimpleDateFormat dayName = new SimpleDateFormat("d");

	protected SimpleDateFormat monthName = new SimpleDateFormat("MMMM");
	protected String iconFile = "datepicker.gif";

	protected String[] weekdayNames = { "Son", "Mon", "Die", "Mit", "Don", "Fre", "Sam" };

	public DatePicker()
	{
		super();
		currentDisplayDate = Calendar.getInstance();
		controlsOnTop = true;
		removeOnDaySelection = true;
		createPanel();
	}

	public DatePicker(final Calendar date)
	{
		super();
		setDate(date);
		controlsOnTop = true;
		removeOnDaySelection = true;
		createPanel();
	}

	public DatePicker(final int month, final int day, final int year)
	{
		super();
		setDate(month, day, year);
		controlsOnTop = true;
		removeOnDaySelection = true;
		createPanel();
	}

	public void addMonth(final int month)
	{
		currentDisplayDate.add(Calendar.MONTH, month);
		createPanel();
		validate();
		repaint();
	}

	public void addPopupListener(final ActionListener l)
	{
		popupListeners.add(l);
	}

	public void addYear(final int year)
	{
		currentDisplayDate.add(Calendar.YEAR, year);
		createPanel();
		validate();
		repaint();
	}

	public void changeDay(final String day)
	{
		currentDisplayDate.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day.trim()));
		if (removeOnDaySelection)
		{
			firePopupEvent(new ActionEvent(this, 1, "hide"));
			popup = null;
		}
		else
		{
			createPanel();
			validate();
			repaint();
		}
	}

	protected JPanel createCalendar()
	{
		final JPanel x = new JPanel();
		final GridBagLayout gridbag = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();

		x.setFocusable(true);
		x.setLayout(gridbag);

		final String month = monthName.format(currentDisplayDate.getTime());
		final String year = Integer.toString(getYear());

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		final JLabel title = new JLabel(month + " " + year);
		x.add(title, c);
		final Font font = title.getFont();
		// Font titleFont = new Font(font.getName(), font.getStyle(),
		// font.getSize() + 2);
		final Font weekFont = new Font(font.getName(), font.getStyle(), font.getSize() - 2);
		title.setFont(font);

		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		for (c.gridx = 0; c.gridx < 7; c.gridx++)
		{
			final JLabel label = new JLabel(weekdayNames[c.gridx]);
			x.add(label, c);
			label.setFont(weekFont);
		}

		final Calendar draw = (Calendar) currentDisplayDate.clone();
		draw.set(Calendar.DATE, 1);
		draw.add(Calendar.DATE, -draw.get(Calendar.DAY_OF_WEEK) + 1);
		final int monthInt = currentDisplayDate.get(Calendar.MONTH);
		// monthInt = 0;
		// System.out.println("Current month: " + monthInt);

		c.gridwidth = 1;
		c.gridheight = 1;
		final int width = getFontMetrics(weekFont).stringWidth(" Wed ");
		final int width1 = getFontMetrics(weekFont).stringWidth("Wed");
		final int height = getFontMetrics(weekFont).getHeight() + (width - width1);

		for (c.gridy = 2; c.gridy < 8; c.gridy++)
			for (c.gridx = 0; c.gridx < 7; c.gridx++)
			{
				JButton dayButton;
				// System.out.print("Draw month: " + draw.get(Calendar.MONTH));
				if (draw.get(Calendar.MONTH) == monthInt)
				{
					String dayString = dayName.format(draw.getTime());
					if (draw.get(Calendar.DAY_OF_MONTH) < 10) dayString = " " + dayString;
					dayButton = new JButton(dayString);
				}
				else
				{
					dayButton = new JButton();
					dayButton.setEnabled(false);
				}
				// System.out.println(", day: " +
				// dayName.format(draw.getTime()));
				x.add(dayButton, c);
				final Color color = dayButton.getBackground();
				if ((draw.get(Calendar.DAY_OF_MONTH) == getDay()) && (draw.get(Calendar.MONTH) == monthInt))
					dayButton.setBackground(Color.yellow);
				// dayButton.setFocusPainted(true);
				// dayButton.setSelected(true);
				else
					dayButton.setBackground(color);
				dayButton.setFont(weekFont);
				dayButton.setFocusable(true);
				dayButton.setPreferredSize(new Dimension(width, height));
				dayButton.setMargin(new Insets(0, 0, 0, 0));
				dayButton.addActionListener(e -> changeDay(e.getActionCommand()));
				draw.add(Calendar.DATE, +1);
			}
		// if (draw.get(Calendar.MONTH) != monthInt) break;
		return x;
	}

	protected JPanel createControls()
	{
		final JPanel c = new JPanel();
		c.setBorder(BorderFactory.createRaisedBevelBorder());
		c.setFocusable(true);
		c.setLayout(new FlowLayout(FlowLayout.CENTER));

		prevYear = new JButton("<<");
		c.add(prevYear);
		prevYear.setMargin(new Insets(0, 0, 0, 0));
		prevYear.addActionListener(arg0 -> addYear(-1));

		prevMonth = new JButton("<");
		c.add(prevMonth);
		prevMonth.setMargin(new Insets(0, 0, 0, 0));
		prevMonth.addActionListener(arg0 -> addMonth(-1));

		textField = new JTextField(getFormattedDate(), 10);
		c.add(textField);
		textField.setEditable(true);
		textField.setEnabled(true);
		textField.addActionListener(e -> editDate(textField.getText()));

		nextMonth = new JButton(">");
		c.add(nextMonth);
		nextMonth.setMargin(new Insets(0, 0, 0, 0));
		nextMonth.addActionListener(arg0 -> addMonth(+1));

		nextYear = new JButton(">>");
		c.add(nextYear);
		nextYear.setMargin(new Insets(0, 0, 0, 0));
		nextYear.addActionListener(arg0 -> addYear(+1));

		return c;
	}

	/*
	 * Returns an ImageIcon, or null if the path was invalid.
	 */
	protected ImageIcon createImageIcon(final String path, final String description)
	{
		final URL imgURL = getClass().getResource(path);
		String fileName = imgURL.getFile().replace("bin/", "src/");
		fileName = fileName.replace("%20", " ").substring(1);
		final ImageIcon icon = new ImageIcon(fileName, description);
		return icon;
	}

	protected void createPanel()
	{
		removeAll();
		setBorder(BorderFactory.createLineBorder(Color.black, 3));
		setFocusable(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		if (controlsOnTop)
		{
			add(createControls());
			add(createCalendar());
		}
		else
		{
			add(createCalendar());
			add(createControls());
		}
		final Dimension d = getPreferredSize();
		setPreferredSize(new Dimension(d.width, d.height + 8));
	}

	public void editDate(final String date)
	{
		parseDate(date);
		createPanel();
		validate();
		repaint();
	}

	protected int expandYear(int year)
	{
		if (year < 100)
		{ // 2 digit year
			final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			final int current2DigitYear = currentYear % 100;
			final int currentCentury = (currentYear / 100) * 100;
			// set 2 digit year range +20 / -80 from current year
			final int high2DigitYear = (current2DigitYear + 20) % 100;
			if (year <= high2DigitYear) year += currentCentury;
			else
				year += (currentCentury - 100);
		}
		return year;
	}

	public void firePopupEvent(final ActionEvent e)
	{
		for (int i = popupListeners.size() - 1; i >= 0; i--)
		{
			final ActionListener l = popupListeners.get(i);
			l.actionPerformed(e);
		}
	}

	public Calendar getCalendarDate()
	{
		return currentDisplayDate;
	}

	public Date getDate()
	{
		return currentDisplayDate.getTime();
	}

	public int getDay()
	{
		return currentDisplayDate.get(Calendar.DAY_OF_MONTH);
	}

	public String getFormattedDate()
	{
		return Integer.toString(getMonth()) + "/" + Integer.toString(getDay()) + "/" + Integer.toString(getYear());
	}

	public ImageIcon getImage()
	{
		return createImageIcon(iconFile, "datepicker.gif");
	}

	public int getMonth()
	{
		return currentDisplayDate.get(Calendar.MONTH) + 1;
	}

	public Popup getPopup(final Container c)
	{
		if (popup == null)
		{
			final Point p = c.getLocation();
			final PopupFactory factory = PopupFactory.getSharedInstance();
			popup = factory.getPopup(c, this, p.x, p.y);
		}
		return popup;
	}

	public int getYear()
	{
		return currentDisplayDate.get(Calendar.YEAR);
	}

	protected void parseDate(final String date)
	{
		final String[] parts = date.split("/");
		if (parts.length == 3)
		{
			currentDisplayDate.set(Calendar.DAY_OF_MONTH, Integer.valueOf(parts[0]) - 1);
			currentDisplayDate.set(Calendar.MONTH, Integer.valueOf(parts[1]));
			currentDisplayDate.set(Calendar.YEAR, expandYear(Integer.valueOf(parts[2])));
		}
		else if (parts.length == 2)
		{
			currentDisplayDate = Calendar.getInstance();
			currentDisplayDate.set(Calendar.DAY_OF_MONTH, Integer.valueOf(parts[0]) - 1);
			currentDisplayDate.set(Calendar.MONTH, Integer.valueOf(parts[1]));
		}
		else
			// invalid date
			currentDisplayDate = Calendar.getInstance();
	}

	public void popupHide()
	{
		popup.hide();
	}

	public void popupShow(final Container c)
	{
		getPopup(c);
		popup.show();
	}

	public void removePopupListener(final ActionListener l)
	{
		popupListeners.remove(l);
	}

	public void setControlsOnTop(final boolean flag)
	{
		controlsOnTop = flag;
		createPanel();
		validate();
		repaint();
	}

	public void setDate(final Calendar date)
	{
		currentDisplayDate = date;
		createPanel();
		validate();
		repaint();
	}

	public void setDate(final int month, final int day, final int year)
	{
		currentDisplayDate = Calendar.getInstance();
		currentDisplayDate.set(expandYear(year), month - 1, day);
		createPanel();
		validate();
		repaint();
	}

	public void setDate(final String date)
	{
		currentDisplayDate = Calendar.getInstance();
		editDate(date);
	}

	public void setRemoveOnDaySelection(final boolean flag)
	{
		removeOnDaySelection = flag;
	}
}
