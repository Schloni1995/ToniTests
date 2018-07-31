package workspace_sicherung

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

class Sicherung(sourcePath: String?, i: String?) : Runnable {
	internal var oldWorkspace: Path? = null
	internal var newWorkspace: Path? = null
	internal var cd: CopyDir? = null
	var isRunning: Boolean = false
	private var start: Long = 0
	private var end: Long = 0
	/** @return the resultTime */
	var resultTime: Long = 0

	init {
		oldWorkspace = Paths.get(sourcePath)
		newWorkspace = Paths.get(DESTINATIONPATH + i!!)
	}

	override fun run() {
		try {
			isRunning = true
			start = System.currentTimeMillis()
			cd = CopyDir(oldWorkspace, newWorkspace)
			Files.walkFileTree(oldWorkspace, cd)
			end = System.currentTimeMillis()
			resultTime = end - start
			isRunning = false
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

	companion object {
		private var DESTINATIONPATH = "\\\\rs5\\RimeTool\\SoftwareEntwicklung\\Sicherungen\\testSicherung"
	}
}

internal class CopyDir(sourceDir: Path?, targetDir: Path?) : SimpleFileVisitor<Path>() {
	private var sourceDir: Path? = null
	private var targetDir: Path? = null

	init {
		this.sourceDir = sourceDir
		this.targetDir = targetDir
	}

	private fun printAttr(p: Path?, a: BasicFileAttributes?) {
		try {
			println("")
			print("Name: " + p!!.fileName + ", ")
			print("Creation time: " + a!!.creationTime() + ", ")
			print("Last modified: " + Files.getLastModifiedTime(p) + "")
			println("")
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

	override fun visitFile(file: Path?, attributes: BasicFileAttributes?): FileVisitResult? {
		try {
			printAttr(file, attributes)
			var targetFile = targetDir!!.resolve(sourceDir!!.relativize(file))
			Files.copy(file, targetFile)// Datei
		} catch (ex: IOException) {
			println(ex)
		}
		return FileVisitResult.CONTINUE
	}

	override fun preVisitDirectory(dir: Path?, attributes: BasicFileAttributes?): FileVisitResult? {
		try {
			printAttr(dir, attributes)
			var newDir = targetDir!!.resolve(sourceDir!!.relativize(dir))
			Files.createDirectory(newDir)// Ordner
		} catch (ex: IOException) {
			println(ex)
		}
		return FileVisitResult.CONTINUE
	}
}