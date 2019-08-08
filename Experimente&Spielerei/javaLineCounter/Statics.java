package javaLineCounter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Statics
{
	final static String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	final static String timeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	final static double NANO = 0.000000001;
	final static String startPath = "F:\\workspace";
	// final static String javaPath = "F:\\AndroidProjects\\Java";
	// final static String kotlinPath = "F:\\AndroidProjects\\Kotlin";
}
