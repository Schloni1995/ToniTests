package workspace_sicherung

import java.io.File

class Dateizaehler(quellpfad: String?) : Runnable {
	/** @return the anzDateien */
	var anzDateien: Long = 0
	/** @return the anzOrdner */
	var anzOrdner: Long = 0
	/** @return the quellpfad */
	var quellpfad: String? = null
	/** @return the running */
	var isRunning: Boolean = false
	private var start: Long = 0
	private var end: Long = 0
	/** @return the resultTime */
	var resultTime: Long = 0

	init {
		this.quellpfad = quellpfad
	}

	
	override fun run() {
		isRunning = true
		start = System.currentTimeMillis()
		getDir(File(this.quellpfad).listFiles())
		end = System.currentTimeMillis()
		resultTime = end - start
		isRunning = false
	}

	private fun getDir(path: Array<File>?) {
		for (f in path!!)
			if (f.isDirectory()) {
				System.out.println("Ordner: " + f.name)
				anzOrdner++
				getDir(f.listFiles())
			} else {
				System.out.println("Datei: " + f.name)
				System.out.println("--> " + f.path + " <--")
				anzDateien++
			}
	}
}