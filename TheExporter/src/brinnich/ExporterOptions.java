package brinnich;

import org.apache.commons.cli.*;

/**
 * Verwaltet CLI-Argumente. Eingegebene CLI-Argumente des Users sind validierbar und koennen abgefragt werden. Bei
 * nicht vorhandenen Argumenten werden Standardwerte vergeben.
 * 
 * @author Selina Brinnich
 * @version 2014-12-14
 *
 */
public class ExporterOptions {
	
	private CommandLine cmd = null;
	
	public static final int SERVERNAME = 0;
	public static final int USERNAME = 1;
	public static final int PASSWORD = 2;
	public static final int DBNAME = 3;
	public static final int SORTCOLUMN = 4;
	public static final int SORTDIR = 5;
	public static final int FILTER = 6;
	public static final int DELIMITER = 7;
	public static final int SELECTCOLUMNS = 8;
	public static final int OUTPUTFILE = 9;
	public static final int SELECTTABLE = 10;

	/**
	 * Initialisiert die CLI-Argumente
	 * @param args die eingegebenen CLI-Argumente des Users
	 */
	public ExporterOptions(String[] args) {		
		initOptions(args);
	}
	
	/**
	 * Initialisiert alle moeglichen CLI-Argumente und parsed die eingegebenen Argumente des Users
	 * @param args
	 */
	public void initOptions(String[] args){
		Options options = new Options();

		options.addOption("h", true, "Hostname des DBMS");
		options.addOption("u", true, "Benutzername");
		options.addOption("p", true, "Passwort");
		options.addOption("d", true, "Name der Datenbank");
		options.addOption("s", true, "Feld, nach dem sortiert werden soll.");
		options.addOption("r", true, "Sortierrichtung");
		options.addOption("w", true, "eine Bedingung in SQL-Syntax, die zum Filtern der Tabelle verwendet wird.");
		options.addOption("t", true, "Trennzeichen, dass für die Ausgabe verwendet werden soll.");
		options.addOption("f", true, "Kommagetrennte Liste (ohne Leerzeichen) der Felder, die im Ergebnis enthalten sein sollen.");
		options.addOption("o", true, "Name der Ausgabedatei");
		options.addOption("T", true, "Tabellenname");
		
		CommandLineParser parser = new BasicParser();
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Prueft die eingegebenen CLI-Argumente. (Alle notwendigen Parameter muessen angegeben sein; -r macht ohne -s keinen Sinn)
	 * @return true, wenn alle Argumente valide sind<br>
	 * 			false, wenn ein oder mehrere Argumente fehlen/falsch sind
	 */
	public boolean verifyOptions(){
		boolean err = false;
		if(!cmd.hasOption("d")){
			System.err.println("Name der Datenbank wird zur Abfrage benötigt! (Parameter: -d)");
			err = true;
		}
		if(!cmd.hasOption("f")){
			System.err.println("Liste der Felder, die im Ergebnis enthalten sein sollen, wird zur Abfrage benötigt! (Parameter: -f)");
			err = true;
		}
		if(!cmd.hasOption("T")){
			System.err.println("Tabellenname wird zur Abfrage benötigt! (Parameter: -T)");
			err = true;
		}
		if(cmd.hasOption("r") && !cmd.hasOption("s")){
			System.err.println("Sortierrichtung ohne Spaltenname, nach dem sortiert werden soll, nicht verwendbar! (Parameter: -s)");
			err = true;
		}
		
		return !err;
	}
	
	/**
	 * Gibt ein CLI-Argument zurueck
	 * @param opt die Option, die zurueckgegeben werden soll
	 * @return einen String, der entweder das eingegebene Argument des Users enthaelt, oder einen Default-Wert fuer diese
	 * 			Option
	 */
	public String getOption(int opt){
		if (opt == SERVERNAME) {
			if (cmd.hasOption("h")) {
				return cmd.getOptionValue("h");
			} else {
				return "localhost";
			}
		}
		if (opt == USERNAME) {
			if (cmd.hasOption("u")) {
				return cmd.getOptionValue("u");
			} else {
				return System.getProperty("user.name");
			}
		}
		if (opt == PASSWORD) {
			if (cmd.hasOption("p")) {
				return cmd.getOptionValue("p");
			} else {
				return "";
			}
		}
		if (opt == DBNAME) {
			if (cmd.hasOption("d")) {
				return cmd.getOptionValue("d");
			} else {
				return "";
			}
		}
		if (opt == SORTCOLUMN) {
			if (cmd.hasOption("s")) {
				return cmd.getOptionValue("s");
			} else {
				return "";
			}
		}
		if (opt == SORTDIR) {
			if (cmd.hasOption("r")) {
				return cmd.getOptionValue("r");
			} else {
				return "";
			}
		}
		if (opt == FILTER) {
			if (cmd.hasOption("w")) {
				return cmd.getOptionValue("w");
			} else {
				return "";
			}
		}
		if (opt == DELIMITER) {
			if (cmd.hasOption("t")) {
				return cmd.getOptionValue("t");
			} else {
				return ";";
			}
		}
		if (opt == SELECTCOLUMNS) {
			if (cmd.hasOption("f")) {
				if (cmd.getOptionValue("f").equals(".classpath")) {
					return "*";
				}else{
					return cmd.getOptionValue("f");
				}
			} else {
				return "";
			}
		}
		if (opt == OUTPUTFILE) {
			if (cmd.hasOption("o")) {
				return cmd.getOptionValue("o");
			} else {
				return "";
			}
		}
		if (opt == SELECTTABLE) {
			if (cmd.hasOption("T")) {
				return cmd.getOptionValue("T");
			} else {
				return "";
			}
		}
		return null;
	}

}
