package bildkompressor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class Gui extends JDialog
{
	private static final long serialVersionUID = 1L;

	/** Launch the application. */
	public static void main(final String[] args)
	{
		try
		{
			final Gui dialog = new Gui();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.pack();
			dialog.setVisible(true);

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	private final JPanel contentPanel = new JPanel();
	private JProgressBar progressBar;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JButton btnStart;
	private JSlider slider;

	private JLabel sliderValueLabel;
	private JLabel lblQualitt;

	private JPanel panel_1;

	/** Create the dialog. */
	public Gui()
	{
		setTitle("ImageCompressor");
		final BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(15, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		{
			progressBar = new JProgressBar();
			contentPanel.add(progressBar);
		}
		{
			lblNewLabel = new JLabel("                                                                          ");
			contentPanel.add(lblNewLabel);
		}
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			{
				slider = new JSlider();
				slider.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				slider.setSnapToTicks(true);
				slider.addChangeListener(arg0 -> sliderValueLabel.setText((slider.getValue() / 100.0d) + ""));
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
				{
					lblQualitt = new JLabel("Qualit\u00E4t:");
					panel.add(lblQualitt);
				}
				slider.setPaintTicks(true);
				slider.setMajorTickSpacing(50);
				slider.setMinorTickSpacing(10);
				panel.add(slider);
			}
			{
				{
					sliderValueLabel = new JLabel((slider.getValue() / 100.0d) + "");
					sliderValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
					panel.add(sliderValueLabel);
				}
			}
		}
		{
			panel_1 = new JPanel();
			getContentPane().add(panel_1, BorderLayout.SOUTH);
			btnStart = new JButton("Start");
			panel_1.add(btnStart);
			btnStart.setAlignmentX(Component.RIGHT_ALIGNMENT);
			btnStart.addActionListener(arg0 ->
			{
				btnStart.setEnabled(false);
				try
				{
					final Thread compressionThread = new Thread(new JPEGCompression(Gui.this));
					compressionThread.start();
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			});
		}
	}

	public JButton getBtnStart()
	{
		if (btnStart == null) btnStart = new JButton("Start");
		return btnStart;
	}

	public JLabel getLblNewLabel()
	{
		if (lblNewLabel == null) lblNewLabel = new JLabel();
		return lblNewLabel;
	}

	public JProgressBar getProgressBar()
	{
		if (progressBar == null) progressBar = new JProgressBar();
		return progressBar;
	}

	public JSlider getSlider()
	{
		if (slider == null) slider = new JSlider();
		return slider;
	}

	public JLabel getSliderValueLabel()
	{
		if (sliderValueLabel == null) sliderValueLabel = new JLabel("");
		return sliderValueLabel;
	}

	public void setBtnStart(final JButton btnStart)
	{
		this.btnStart = btnStart;
	}

	public void setLblNewLabel(final JLabel lblNewLabel)
	{
		this.lblNewLabel = lblNewLabel;
	}

	public void setPanel(final JPanel panel)
	{
		this.panel = panel;
	}

	public void setProgressBar(final JProgressBar progressBar)
	{
		this.progressBar = progressBar;
	}

	public void setSlider(final JSlider slider)
	{
		this.slider = slider;
	}

	public void setSliderValueLabel(final JLabel sliderValueLabel)
	{
		this.sliderValueLabel = sliderValueLabel;
	}

}
