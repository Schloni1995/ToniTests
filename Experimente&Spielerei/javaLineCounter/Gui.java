package javaLineCounter;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static DebugConsole dc;
	public static boolean debugMode;

	/** Launch the application. */
	public static void main(final String[] args)
	{
		final Runnable run = () ->
		{
			final Gui frame = new Gui();
			frame.setVisible(true);
		};

		EventQueue.invokeLater(run);
	}

	private JPanel contentPanel;
	private JPanel inputPanel;
	private JPanel outputPanel;
	private JTextField pathTF;
	private JButton browseButton;
	private JLabel pathLabel;
	private JButton countButton;
	private JLabel outputLabel;
	private JButton saveTXTButton;
	private PfadDurchsucher pd;
	private JScrollBar scrollBar;
	private JPanel scrollTfPanel;

	/** Create the frame. */
	public Gui()
	{
		setTitle("JavaProjektStatist - LineCounter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(getContentPanel());
		pack();

		final KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new HotKeyManager(Gui.this));

	}

	public long directoryCount(final Path dir) throws IOException
	{
		return Files.walk(dir).parallel().filter(p -> p.toFile().isDirectory()).count() - 1;
	}

	public long fileCount(final Path dir) throws IOException
	{
		return Files.walk(dir).parallel().filter(p -> p.toFile().isFile()).count();
	}

	private JButton getBrowseButton()
	{
		if (browseButton == null)
		{
			browseButton = new JButton("Durchsuchen");
			browseButton.addActionListener((event) ->
			{
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setMultiSelectionEnabled(false);
				if (pathTF.getText().trim().isEmpty() || !new File(pathTF.getText()).exists())
					fc.setCurrentDirectory(new File(Statics.startPath));
				else
					fc.setCurrentDirectory(new File(pathTF.getText()));

				final int option = fc.showOpenDialog(Gui.this);
				String newPath = "";
				if (option == JFileChooser.APPROVE_OPTION) newPath = fc.getSelectedFile().getAbsolutePath();
				else if (option == JFileChooser.CANCEL_OPTION) newPath = Statics.startPath;
				else
					newPath = "Fehler";

				pathTF.setText(newPath);
			});
		}
		return browseButton;
	}

	public JPanel getContentPanel()
	{
		if (contentPanel == null)
		{
			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.add(getInputPanel());
			contentPanel.add(getOutputPanel());
		}
		return contentPanel;
	}

	private JButton getCountButton()
	{
		if (countButton == null)
		{
			countButton = new JButton("Z\u00E4hlen");
			countButton.setToolTipText("Startet den Vorgang");
			countButton.addActionListener(ev ->
			{
				if (debugMode) Gui.dc.toConsole("Start pressed");
				else
					System.out.println("Start pressed");

				outputPanel.setVisible((outputPanel.isVisible() ? false : true));
				pd = new PfadDurchsucher(pathTF.getText());
				outputLabel.setText("<html>Gesamtanzahl Zeilen: " + pd.getGesamtAnzZeilen() + "<br>"
						+ "Gesamtanzahl Zeichen: " + pd.getGesamtAnzZeichen() + "<br>" + "Anzahl Dateien: "
						+ pd.getAnzDateien() + "<br>" + "Anzahl Ordner: " + pd.getAnzOrdner() + "</html>");
				outputPanel.setVisible(true);
				pack();

				if (debugMode) Gui.dc.toConsole("Counter finished");
				else
					System.out.println("Counter finished");
			});
		}
		return countButton;
	}

	public JPanel getInputPanel()
	{
		if (inputPanel == null)
		{
			inputPanel = new JPanel();
			inputPanel.add(getPathLabel());
			inputPanel.add(getScrollTfPanel());
			inputPanel.add(getBrowseButton());
			inputPanel.add(getCountButton());
		}
		return inputPanel;
	}

	private JLabel getOutputLabel()
	{
		if (outputLabel == null) outputLabel = new JLabel("");
		return outputLabel;
	}

	public JPanel getOutputPanel()
	{
		if (outputPanel == null)
		{
			outputPanel = new JPanel();
			outputPanel.setLayout(new BorderLayout(0, 0));
			outputPanel.add(getOutputLabel());
			outputPanel.add(getSaveTXTButton(), BorderLayout.SOUTH);
			outputPanel.setVisible(false);
		}
		return outputPanel;
	}

	private JLabel getPathLabel()
	{
		if (pathLabel == null) pathLabel = new JLabel("Pfad:");
		return pathLabel;
	}

	private JTextField getPathTF()
	{
		if (pathTF == null)
		{
			pathTF = new JTextField(20);
			pathTF.setText(Statics.startPath);

		}

		return pathTF;
	}

	private JButton getSaveTXTButton()
	{
		if (saveTXTButton == null)
		{
			saveTXTButton = new JButton("Als *txt speichern");
			saveTXTButton.addActionListener(e ->
			{
				if (pd != null)
				{
					final JFileChooser fc = new JFileChooser("M:\\Softwareentwicklung");
					final int option = fc.showSaveDialog(Gui.this);

					if (option == JFileChooser.APPROVE_OPTION)
					{
						final File fileToSave = fc.getSelectedFile();
						FileCreator.createTxtFile(fileToSave.getAbsolutePath(), pathTF.getText(),
								pd.getGesamtAnzZeilen(), pd.getGesamtAnzZeichen(), pd.getAnzDateien(),
								pd.getAnzOrdner());
						if (debugMode)

							dc.toConsole("File  " + fileToSave.getName() + " saved");
						else
							System.out.println("File  " + fileToSave.getName() + " saved");
					}

				}

			});
		}
		return saveTXTButton;
	}

	private JScrollBar getScrollBar()
	{
		if (scrollBar == null)
		{
			scrollBar = new JScrollBar();
			scrollBar.setOrientation(Adjustable.HORIZONTAL);
			final BoundedRangeModel brm = pathTF.getHorizontalVisibility();
			scrollBar.setModel(brm);
		}
		return scrollBar;
	}

	private JPanel getScrollTfPanel()
	{
		if (scrollTfPanel == null)
		{
			scrollTfPanel = new JPanel();
			scrollTfPanel.setLayout(new BoxLayout(scrollTfPanel, BoxLayout.Y_AXIS));
			scrollTfPanel.add(getPathTF());
			scrollTfPanel.add(getScrollBar());
		}
		return scrollTfPanel;
	}

	@Override
	public void pack()
	{
		super.pack();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (getSize().width / 2),
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (getSize().height / 2));
	}

	public void setDebugConsole(final DebugConsole dc)
	{
		final Thread dcThread = new Thread(dc);
		dcThread.start();
		debugMode = true;
		if (dc == null) Gui.dc = dc;
		final Point loc = getLocation();
		dc.setLocation(loc.x, loc.y + getSize().height);
		dc.setVisible(debugMode);
	}
}
