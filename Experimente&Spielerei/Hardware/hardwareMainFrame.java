package Hardware;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class hardwareMainFrame extends JFrame
{

	/**
	 *
	 */
	private static final long serialVersionUID = 467594158645756846L;

	/** Launch the application. */
	public static void main(final String[] args)
	{
		EventQueue.invokeLater(() ->
		{
			try
			{
				final hardwareMainFrame frame = new hardwareMainFrame();
				frame.setVisible(true);
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		});
	}

	private final JPanel contentPane;

	/** Create the frame. */
	public hardwareMainFrame()
	{
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Titel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		final GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		final JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		final GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
	}

}
