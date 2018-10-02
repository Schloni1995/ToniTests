package arbeitsZeiten;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import rtModuls.de.rtObjects.gui.RTColors;

public class TimeTableRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 8227457272794433834L;

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column)
	{
		table.getModel();
		setBackground(Color.WHITE);
		if ((row % 2) == 0) setBackground(new Color(202, 216, 241));
		setText((value != null) ? value.toString() : "");
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
		final JTableHeader header = table.getTableHeader();
		header.setOpaque(false);
		header.setForeground(RTColors.getRimeDunkelBlau());
		header.setBorder(BorderFactory.createLineBorder(RTColors.getRimeDunkelBlau(), 1));

		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);

		if (column == table.getColumnModel().getColumnIndex("Überstunden")
				|| column == table.getColumnModel().getColumnIndex("in Minuten"))
			setColors(value);
		else
			setForeground(Color.black);

		if (isSelected) setBackground(new Color(130, 170, 237));
		return this;
	}

	private void setColors(final Object value)
	{
		final double ueberstunden = Double.parseDouble(value.toString());
		if (ueberstunden < 0.0) setForeground(RTColors.getRot().darker());
		else if (ueberstunden > 0.0) setForeground(RTColors.getGruen().darker());
		else
			setForeground(Color.BLUE);

	}

}
