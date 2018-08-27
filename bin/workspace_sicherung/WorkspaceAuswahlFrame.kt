package workspace_sicherung

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionListener
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.JTextField
import rtModuls.de.rtObjects.gui.RTButton
import java.awt.event.ActionEvent

class WorkspaceAuswahlFrame : JFrame() {
	private var mainPane: JPanel? = null
	private var progressPanel: JPanel? = null
	private var pathLabel: JLabel? = null
	private var pathTextField: JTextField? = null
	private var searchButton: RTButton? = null
	private var startButton: RTButton? = null
	private var gbl: GridBagLayout? = null
	private var gbc: GridBagConstraints? = null
	private var progressBar: JProgressBar? = null
	internal var dz: Dateizaehler? = null
	internal var s: Sicherung? = null
	internal var copying: Thread? = null
	internal var counting: Thread? = null
	private val serialVersionUID = -3249122680536387379L
	private val SOURCEPATH = "\\\\rs5\\RimeTool\\SoftwareEntwicklung"
	

	init {
		init()
		setSize(900, 500)
		setContentPane(mainPane)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		setVisible(true)
		pack()
	}

	private fun init() {
		createPathLabel()
		createPathTextField()
		createSearchButton()
		createStartButton()
		createProgressBar()
		createProgressPanel()
		createGridBagLayout()
		createGridBagConstraints()
		createMainPane()
	}

	fun createMainPane() {
		if (mainPane == null) {
			mainPane = JPanel()
			mainPane!!.setLayout(gbl)
			gbcXY(0, 0)
			mainPane!!.add(pathLabel, gbc)
			gbcXY(1, 0)
			mainPane!!.add(pathTextField, gbc)
			gbcXY(2, 0)
			mainPane!!.add(searchButton, gbc)
			gbc!!.gridwidth = GridBagConstraints.REMAINDER
			gbcXY(0, 1)
			mainPane!!.add(startButton, gbc)
			gbcXY(0, 2)
			mainPane!!.add(progressPanel, gbc)
		}
	}

	fun createPathLabel() {
		if (pathLabel == null) {
			pathLabel = JLabel("Zu sichernde Workspace:")
		}
	}

	/**
	 *
	 */
	fun createPathTextField() {
		if (pathTextField == null) {
			pathTextField = JTextField(SOURCEPATH, 30)
		}
	}

	/**
	 *
	 */
	fun createSearchButton()
	{
		if (searchButton == null)
		{
			searchButton = RTButton("Durchsuchen", 150, 35, {
				var toSavePath = Paths.get(pathTextField!!.getText())
				val fc: JFileChooser?
				if (Files.exists(toSavePath)) fc = JFileChooser(toSavePath!!.toFile())
				else fc = JFileChooser()
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
				if (fc.showOpenDialog(this@WorkspaceAuswahlFrame) == JFileChooser.APPROVE_OPTION)
				{
					toSavePath = fc.getSelectedFile().toPath()
					pathTextField!!.setText(toSavePath!!.toAbsolutePath().toString())
				}
			})		
		}
	}

	/**
	 *
	 */
	fun createStartButton()
	{
		if (startButton == null)
		{
			startButton = RTButton("Sicherung starten", 150, 35, {
				dz = Dateizaehler(pathTextField!!.text)
				s = Sicherung(pathTextField!!.text, JOptionPane.showInputDialog("Zahl"))
				counting = Thread(dz)
				copying = Thread(s)
				counting!!.start()
				copying!!.start()
				progressBar!!.setIndeterminate(true)
				Thread({
					while ((copying!!.isAlive() || counting!!.isAlive()))
					 {
						print(counting!!.isAlive())
						println(" " + copying!!.isAlive())
						try
						{
							Thread.sleep(1000 * 5)
						}
						catch (e1: InterruptedException)
						{
							e1.printStackTrace()
						}
						println("${dz!!.resultTime / 1000.0}  Sekunden")
						println("${s!!.resultTime / 1000.0}  Sekunden")
						progressBar!!.setIndeterminate(false)
					}
				}).start()
			})
		}
	}

	/**
	 *
	 */
	fun createGridBagLayout() {
		gbl = GridBagLayout()
	}

	fun createGridBagConstraints() {
		gbc = GridBagConstraints()
	}

	/** Setzt Zelle mit den Parametern beginnend bei 0
	 *
	 * @param x
	 * @param y
	 */
	fun gbcXY(x: Int, y: Int) {
		gbc!!.gridx = x
		gbc!!.gridy = y
	}

	fun createProgressPanel() {
		progressPanel = JPanel()
		progressPanel!!.add(progressBar)
	}

	fun createProgressBar() {
		progressBar = JProgressBar()
		progressBar!!.setStringPainted(false)
		progressBar!!.setIndeterminate(false)
	}
	
	
}
			fun main(args: Array<String>)
			{
				WorkspaceAuswahlFrame()
			}
