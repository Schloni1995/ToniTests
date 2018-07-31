package myQueryConverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import rtModuls.de.rtObjects.gui.RTButton;
import rtModuls.de.rtObjects.gui.RTColors;
import rtModuls.de.rtObjects.gui.RTDialog;
import rtModuls.de.rtObjects.gui.RTMenuItem;

public class QueryKonverterDialog extends RTDialog
{
	private static final long serialVersionUID = 3731754834571601205L;

	static JComponent createHorizontalSeparator()
	{
		final JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
		x.setPreferredSize(new Dimension(1280, 5));
		return x;
	}

	public static void main(final String[] args)
	{
		new QueryKonverterDialog();
	}

	private final Color javaColor, sqlColor;
	private JPanel mainPanel, buttonPanel;
	private JButton convButton, clearSQLButton, clearJavaButton;
	private JScrollPane javaSCP, sqlSCP;
	private JLabel sqlLabel, javaLabel;
	private JTextArea javaCodeArea, sqlCodeArea;
	private JPanel sqlPanel;
	private JPanel javaPanel;

	private JSeparator separator;

	private JPopupMenu popM;

	public QueryKonverterDialog()
	{
		sqlColor = new Color(0, 255, 204);
		javaColor = Color.orange;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(new Point(100, 100));
		setTitle("QueryConverter");
		setBackground(Color.darkGray);
		getContentPane().add(getMainPanel(), BorderLayout.NORTH);
		getContentPane().add(getButtonPanel(), BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	public JPanel getButtonPanel()
	{
		if (buttonPanel == null)
		{
			buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.white);
			buttonPanel.add(getConvButton());
			buttonPanel.add(getClearSQLButton());
			buttonPanel.add(getClearJavaButton());
		}
		return buttonPanel;
	}

	public JButton getClearJavaButton()
	{
		if (clearJavaButton == null)
		{
			final ActionListener al = arg0 -> getJavaArea().setText(null);
			clearJavaButton = new RTButton("Clear Java", 150, 30, javaColor, al);
		}
		return clearJavaButton;
	}

	public JButton getClearSQLButton()
	{
		if (clearSQLButton == null)
		{
			final ActionListener al = arg0 -> getSqlArea().setText(null);
			clearSQLButton = new RTButton("Clear SQL", 150, 30, sqlColor, al);
		}
		return clearSQLButton;
	}

	public JButton getConvButton()
	{
		if (convButton == null)
		{
			final ActionListener al = arg0 -> new SQLtoJavaConverter(QueryKonverterDialog.this, sqlCodeArea.getText(),
					javaCodeArea.getText());
			convButton = new RTButton("Konvertieren", 300, 30, Color.WHITE, al);
		}
		return convButton;
	}

	public JTextArea getJavaArea()
	{
		if (javaCodeArea == null)
		{
			javaCodeArea = new JTextArea();
			javaCodeArea.setName("javaCodeArea");
			javaCodeArea.setLineWrap(true);
			javaCodeArea.setWrapStyleWord(true);
			javaCodeArea.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseReleased(final MouseEvent e)
				{
					if (e.isPopupTrigger() && (MouseEvent.BUTTON3 == e.getButton()))
						getPopM(javaCodeArea.getName()).show(e.getComponent(), e.getX() + 10, e.getY());

				}

			});
			javaCodeArea.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyPressed(final KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_ENTER) getConvButton().doClick();
				}

				@Override
				public void keyReleased(final KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_V) getConvButton().doClick();
				}
			});
		}
		return javaCodeArea;
	}

	public JLabel getJavaLabel()
	{
		if (javaLabel == null)
		{
			javaLabel = new JLabel("JavaCode:");
			javaLabel.setForeground(javaColor);
		}
		return javaLabel;
	}

	private JPanel getJavaPanel()
	{
		if (javaPanel == null)
		{
			javaPanel = new JPanel();
			javaPanel.setOpaque(false);
			javaPanel.add(getJavaLabel());
			javaPanel.add(getJavaSCP());
		}
		return javaPanel;
	}

	public JScrollPane getJavaSCP()
	{
		if (javaSCP == null)
		{
			javaSCP = new JScrollPane(getJavaArea());
			javaSCP.setPreferredSize(new Dimension(800, 200));
		}
		return javaSCP;
	}

	public JPanel getMainPanel()
	{
		if (mainPanel == null)
		{
			mainPanel = new JPanel();
			mainPanel.setBackground(RTColors.getRimeDunkelBlau());
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			mainPanel.add(getSqlPanel());
			mainPanel.add(getSeparator());
			mainPanel.add(getJavaPanel());
		}
		return mainPanel;
	}

	public JPopupMenu getPopM(final String areaName)
	{
		popM = new JPopupMenu("Beautifier");
		final RTMenuItem itemSQL = new RTMenuItem("Beautify SQL");
		final RTMenuItem itemJAVA = new RTMenuItem("Beautify Java");
		final RTMenuItem itemBOTH = new RTMenuItem("Beautify Both");

		itemSQL.addActionListener(
				e -> sqlCodeArea.setText(new SQL_Beautifier(sqlCodeArea.getText()).returnFormattedQuery()));

		itemJAVA.addActionListener(e ->
		{
			// TODO JAVA_Beautifier
		});
		itemBOTH.addActionListener(
				e -> sqlCodeArea.setText(new SQL_Beautifier(sqlCodeArea.getText()).returnFormattedQuery()));

		if (areaName.toLowerCase().contains("sql")) popM.add(itemSQL);
		else if (areaName.toLowerCase().contains("java")) popM.add(itemJAVA);
		popM.add(itemBOTH);
		return popM;
	}

	private JSeparator getSeparator()
	{
		if (separator == null) separator = new JSeparator();
		return separator;
	}

	public JTextArea getSqlArea()
	{
		if (sqlCodeArea == null)
		{
			sqlCodeArea = new JTextArea();
			sqlCodeArea.setName("sqlCodeArea");
			sqlCodeArea.setLineWrap(true);
			// Zeilenumbruch wird eingeschaltet
			sqlCodeArea.setWrapStyleWord(true);
			// Zeilenumbrüche erfolgen nur nach ganzen Wörtern

			sqlCodeArea.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseReleased(final MouseEvent e)
				{
					if (e.isPopupTrigger() && (MouseEvent.BUTTON3 == e.getButton()))
						getPopM(sqlCodeArea.getName()).show(e.getComponent(), e.getX() + 10, e.getY());

				}

			});

			sqlCodeArea.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyPressed(final KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_ENTER) getConvButton().doClick();
				}

				@Override
				public void keyReleased(final KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_V) getConvButton().doClick();
				}
			});
		}
		return sqlCodeArea;
	}

	public JLabel getSQLLabel()
	{
		if (sqlLabel == null)
		{
			sqlLabel = new JLabel("SQLCode:");
			sqlLabel.setForeground(sqlColor);
		}
		return sqlLabel;
	}

	private JPanel getSqlPanel()
	{
		if (sqlPanel == null)
		{
			sqlPanel = new JPanel();
			sqlPanel.setOpaque(false);
			sqlPanel.add(getSQLLabel());
			sqlPanel.add(getSQLSCP());
		}
		return sqlPanel;
	}

	public JScrollPane getSQLSCP()
	{
		if (sqlSCP == null)
		{
			sqlSCP = new JScrollPane(getSqlArea());
			sqlSCP.setPreferredSize(new Dimension(800, 200));
		}
		return sqlSCP;
	}

}
