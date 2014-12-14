package brinnich;

import java.sql.ResultSet;

/**
 * Exportiert ein ResultSet einer Select-Abfrage an eine Datenbank entweder in ein externes File oder schreibt es in
 * die Konsole.
 * 
 * @author Selina Brinnich
 * @version 2014-12-14
 *
 */
public class Main {

	public static void main(String[] args) {
		//Init & verify CLI-Arguments
		ExporterOptions opt = new ExporterOptions(args);
		if(!opt.verifyOptions()){
			System.exit(1);
		}
		
		//Build DB-Connection
		DBConnection dbcon = new DBConnection();
		dbcon.connect(opt.getOption(ExporterOptions.SERVERNAME), opt.getOption(ExporterOptions.USERNAME), 
				opt.getOption(ExporterOptions.PASSWORD));
		
		//Build Query
		Exporter exp = new Exporter();
		String query = exp.createQuery(opt.getOption(ExporterOptions.SELECTCOLUMNS), 
				opt.getOption(ExporterOptions.SELECTTABLE),
				opt.getOption(ExporterOptions.FILTER), 
				opt.getOption(ExporterOptions.SORTCOLUMN), 
				opt.getOption(ExporterOptions.SORTDIR));
		
		//Execute Query
		ResultSet rs = dbcon.executeStatement(opt.getOption(ExporterOptions.DBNAME), query);
		
		//Output
		if(opt.getOption(ExporterOptions.OUTPUTFILE).isEmpty()){
			exp.cliOutput(rs, opt.getOption(ExporterOptions.DELIMITER));
		}else{
			exp.fileOutput(rs, opt.getOption(ExporterOptions.OUTPUTFILE), opt.getOption(ExporterOptions.DELIMITER));
		}
		
		//Close Connections
		dbcon.closeConnections();
	}
	
}
