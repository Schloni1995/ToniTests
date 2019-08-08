package arbeitsZeiten;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import rtModuls.de.basics.MeinRendererAllgemein;
import rtModuls.de.rtObjects.gui.RTDialog;
import rtModuls.de.rtObjects.gui.RTTableModel;

public class WorkingTime extends JFrame
{

	private static final long serialVersionUID = -4138254615678118062L;

	public static void main(final String[] args)
	{
		new WorkingTime(135);
	}

	private JPanel backgroundPanel;
	private JScrollPane scrollPane;
	private JTable rlTable;
	private String[] col;
	private Object[][] values;
	private JMenu menu;
	private JMenuBar menuBar;
	private JPanel filterPanel;
	private JSpinner monatSpinner;
	private JTextField persNrTextField;
	private JSpinner jahrSpinner;
	private JLabel persnrLabel;
	private int persNr;
	private JPopupMenu popUp;

	public WorkingTime(final int personalNummer)
	{
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("\\\\rs5\\RimeTool\\SoftwareEntwicklung\\workspaceToni\\RtErp\\src\\recourses\\time.png"));
		persNr = personalNummer;
		col = getCol();
		setTitle(getClass().getSimpleName() + " - WhoKehrs");
		setJMenuBar(getBar());
		setContentPane(getBackgroundPanel());
		setVisible(true);
		pack();
	}

	private JMenuItem getAVGUeberstundenItem()
	{
		final JMenuItem showUeberstundenItem = new JMenuItem("Überstunden im Mittel");
		showUeberstundenItem.addActionListener(e ->
		{
			float sumH = 0;
			float sumMin = 0;

			for (int i = 0; i < rlTable.getRowCount(); i++)
			{
				sumH += Float.parseFloat(values[i][rlTable.getColumnModel().getColumnIndex("Überstunden")] + "");
				sumMin += Float.parseFloat(values[i][rlTable.getColumnModel().getColumnIndex("in Minuten")] + "");
			}
			final float avgH = sumH / rlTable.getRowCount();
			final float avgMin = sumMin / rlTable.getRowCount();
			JOptionPane.showMessageDialog(WorkingTime.this,
					"Durchschnitt in h: " + avgH + "\r\n" + "Durchschnitt in min: " + avgMin, "Durchschnitt",
					JOptionPane.INFORMATION_MESSAGE);
		});
		return showUeberstundenItem;
	}

	public JPanel getBackgroundPanel()
	{
		if (backgroundPanel == null)
		{
			backgroundPanel = new JPanel();
			backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
			backgroundPanel.add(getScrollPane());
			backgroundPanel.add(getFilterPanel());
		}
		return backgroundPanel;
	}

	private JMenuBar getBar()
	{
		if (menuBar == null)
		{
			menuBar = new JMenuBar();
			menuBar.add(getMenu());
		}
		return menuBar;
	}

	private ChangeListener getCL()
	{
		final ChangeListener cl = e -> refreshTable(e);
		return cl;
	}

	public String[] getCol()
	{
		if (col == null) col = new String[] { "Datum", "Wochentag", "Überstunden", "in Minuten" };
		return col;
	}

	private JPanel getFilterPanel()
	{
		if (filterPanel == null)
		{
			filterPanel = new JPanel();
			filterPanel.add(new JLabel("Monat:"));
			filterPanel.add(getMonatSpinner());
			filterPanel.add(new JLabel("Jahr:"));
			filterPanel.add(getJahrSpinner());
			filterPanel.add(getPersNrTextField());
			filterPanel.add(getPersnrLabel());
		}
		return filterPanel;
	}

	private JSpinner getJahrSpinner()
	{
		if (jahrSpinner == null)
		{
			final int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			jahrSpinner = new JSpinner(new SpinnerNumberModel(year, year - 3, year, 1));
			jahrSpinner.addChangeListener(getCL());
		}
		return jahrSpinner;
	}

	private JMenu getMenu()
	{
		if (menu == null)
		{
			menu = new JMenu("Info");
			menu.add(getUeberstundenItem());
			menu.add(getAVGUeberstundenItem());
			menu.add(getTimeIXViewItem());
		}
		return menu;
	}

	private JSpinner getMonatSpinner()
	{
		if (monatSpinner == null)
		{
			final int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
			monatSpinner = new JSpinner(new SpinnerNumberModel(month, 1, 12, 1));
			monatSpinner.addChangeListener(getCL());
		}
		return monatSpinner;
	}

	private JLabel getPersnrLabel()
	{
		if (persnrLabel == null)
		{
			persnrLabel = new JLabel("");

			final String text = new String(persNrTextField.getText()).trim();
			if (text.matches("[0-9]+"))
			{
				new SQLqueryExec();
				final String[] nameVorname = SQLqueryExec.getPersonToNumber(Integer.parseInt(text));
				persnrLabel.setText(nameVorname[0] + " " + nameVorname[1]);
			}
		}
		return persnrLabel;
	}

	private JTextField getPersNrTextField()
	{
		if (persNrTextField == null)
		{
			persNrTextField = new JTextField(persNr + "");
			persNrTextField.setColumns(3);
			persNrTextField.setToolTipText("Personalnummer");
			persNrTextField.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyReleased(final KeyEvent e)
				{
					final String text = new String(((JTextField) (e.getSource())).getText());
					if (text.matches("[0-9]+"))
					{
						new SQLqueryExec();
						final String[] nameVorname = SQLqueryExec.getPersonToNumber(Integer.parseInt(text));
						persnrLabel.setText(nameVorname[0] + " " + nameVorname[1]);
					}
					refreshTable(e);

					if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					{
						rlTable.setModel(new DefaultTableModel(getRLValues(), col));
						jahrSpinner.setValue(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
						monatSpinner.setValue(Integer.parseInt(new SimpleDateFormat("MM").format(new Date())));
						persNrTextField.setText("");
					}
				}
			});
		}
		return persNrTextField;
	}

	private JPopupMenu getPopUp()
	{
		if (popUp == null)
		{
			popUp = new JPopupMenu();
			popUp.add(getZeitenItem());
		}
		return popUp;
	}

	public JTable getRLTable()
	{
		if (rlTable == null)
		{
			rlTable = new JTable();
			rlTable.setDefaultRenderer(Object.class, new TimeTableRenderer());
			rlTable.setModel(new DefaultTableModel(getRLValues(), col));
			rlTable.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseReleased(final MouseEvent e)
				{
					if (e.isPopupTrigger()) getPopUp().show(rlTable, e.getX(), e.getY());
				}
			});
		}
		return rlTable;
	}

	public Object[][] getRLValues()
	{
		final int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
		final int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		new SQLqueryExec();
		values = SQLqueryExec.getTimeTableValues(month, persNr, year);
		return values;
	}

	public Object[][] getRLValues(final int monat, final int persNr, final int years)
	{
		new SQLqueryExec();
		values = SQLqueryExec.getTimeTableValues(monat, persNr, years);
		return values;
	}

	public JScrollPane getScrollPane()
	{
		if (scrollPane == null)
		{
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getRLTable());
		}
		return scrollPane;
	}

	private JMenuItem getTimeIXViewItem()
	{
		final JMenuItem timeIxViewItem = new JMenuItem("TimeIX-View");
		timeIxViewItem.addActionListener(e ->
		{
			final int month = Integer.parseInt(monatSpinner.getValue() + "");
			final int year = Integer.parseInt(jahrSpinner.getValue() + "");
			final int persNr = Integer.parseInt(persNrTextField.getText());// WorkingTime.this.persNr

			final RTDialog dialog = new RTDialog();
			dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			final Point p = new Point(WorkingTime.this.getLocation().x + WorkingTime.this.getWidth(),
					WorkingTime.this.getLocation().y);
			dialog.setLocation(p);
			// dialog.setLocation(MouseInfo.getPointerInfo().getLocation());
			dialog.setVisible(true);

			final JTable timeIXTable = new JTable();
			new SQLqueryExec();
			final String[][] timeIXdata = SQLqueryExec.getTimeTableValuesByTimeIX(month, persNr, year);
			timeIXTable.setModel(new RTTableModel(new String[] { "Datum", "kommtart", "Akommt", "gehtart",
					"ÜberstundenInMin", "Überstunden", "Abwesenheit", "AGeht" }, timeIXdata));
			timeIXTable.setDefaultRenderer(Object.class, new MeinRendererAllgemein(""));
			// timeIXTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			final JScrollPane scP = new JScrollPane(timeIXTable);

			dialog.add(scP);
			dialog.pack();
		});
		return timeIxViewItem;
	}

	private JMenuItem getUeberstundenItem()
	{
		final JMenuItem showUeberstundenItem = new JMenuItem("Summe Überstunden");
		showUeberstundenItem.addActionListener(e ->
		{
			float sumH = 0;
			float sumMin = 0;

			for (int i = 0; i < rlTable.getRowCount(); i++)
			{
				sumH += Float.parseFloat(values[i][rlTable.getColumnModel().getColumnIndex("Überstunden")] + "");
				sumMin += Float.parseFloat(values[i][rlTable.getColumnModel().getColumnIndex("in Minuten")] + "");
			}
			JOptionPane.showMessageDialog(WorkingTime.this,
					"Summe in Stunden: " + sumH + "\r\n" + "Summe in Minuten: " + sumMin, "Summe",
					JOptionPane.INFORMATION_MESSAGE);
		});
		return showUeberstundenItem;
	}

	private JMenuItem getZeitenItem()
	{
		final JMenuItem showTestItem = new JMenuItem("Zeiten für den Tag anzeigen");
		showTestItem.addActionListener(e ->
		{
			final OneDayWorkingTime timeDialog = new OneDayWorkingTime("Toni Zinke", rlTable
					.getValueAt(rlTable.getSelectedRow(), rlTable.getColumnModel().getColumnIndex("Datum")).toString(),
					persNr);
			timeDialog.setVisible(true);
		});
		return showTestItem;
	}

	private void refreshTable(final ChangeEvent e)
	{
		final JSpinner spinner = (JSpinner) e.getSource();
		int monat = Integer.parseInt(monatSpinner.getValue() + "");
		int jahr = Integer.parseInt(jahrSpinner.getValue() + "");

		if (spinner.equals(monatSpinner))
		{
			monat = Integer.parseInt(spinner.getValue() + "");
			System.out.println("Monatspinner clicked --> " + monat);
		}
		if (spinner.equals(jahrSpinner))
		{
			jahr = Integer.parseInt(spinner.getValue() + "");
			System.out.println("Jahrspinner clicked --> " + jahr);
		}

		if (!persNrTextField.getText().trim().isEmpty())
		{
			final int persNr = Integer.parseInt(persNrTextField.getText());
			rlTable.setModel(new DefaultTableModel(getRLValues(monat, persNr, jahr), col));
		}
		else
			rlTable.setModel(new DefaultTableModel(getRLValues(monat, 135, jahr), col));
	}

	private void refreshTable(final KeyEvent e)
	{
		final JTextField textField = (JTextField) e.getSource();
		if (!textField.getText().trim().isEmpty())
		{
			final int jahr = Integer.parseInt(jahrSpinner.getValue() + "");
			final int monat = Integer.parseInt(monatSpinner.getValue() + "");
			persNr = Integer.parseInt(textField.getText());
			rlTable.setModel(new DefaultTableModel(getRLValues(monat, persNr, jahr), col));
		}
		else
			rlTable.setModel(new DefaultTableModel(getRLValues(), col));
	}
}
