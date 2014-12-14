package brinnich;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Exporter kann eine Query laut Parametern erstellen und ein ResultSet formatiert sowohl auf der Konsole, als auch
 * in einem externen File ausgeben.
 * 
 * @author Selina Brinnich
 * @version 2014-12-14
 *
 */
public class Exporter {
	
	/**
	 * Erstellt eine Select-Abfrage laut uebergebenen Parametern.
	 * @param columns die Spalten, die im Ergebnis angezeigt werden sollen
	 * @param table die Tabelle, die angezeigt werden soll
	 * @param filter eine Filterbedingung in SQL-Syntax
	 * @param orderColumn ein Spaltenname, nach dem sortiert werden soll
	 * @param orderDir Sortierrichtung (ASC oder DESC)
	 * @return ein String mit der fertigen Query
	 */
	public String createQuery(String columns, String table, String filter, String orderColumn, String orderDir){
		String query = "SELECT " + columns + " FROM " + table;
		if (!filter.isEmpty()) {
			query += " WHERE " + filter;
		}
		if (!orderColumn.isEmpty()) {
			query += " ORDER BY " + orderColumn;
			if (!orderDir.isEmpty()) {
				query += " " + orderDir;
			}
		}
		query += ";";
		return query;
	}
	
	/**
	 * Gibt ein ResultSet auf der Konsole aus. Dabei wird jede Row im ResultSet in einer eigenen Zeile dargestellt und
	 * die Columns werden mit einem uebergebenen Trennzeichen getrennt.
	 * @param rs ResultSet, das dargestellt werden soll
	 * @param delimiter zu verwendendes Trennzeichen
	 */
	public void cliOutput(ResultSet rs, String delimiter){
		try {
			while (rs.next()) {
				for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
					try {
						if(j != 1){
							System.out.print(delimiter + " ");
						}
						Object wert = rs.getObject(j);
						if(wert == null){
							System.out.print("NULL");
						}else{
							System.out.print(wert.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schreibt ein ResultSet in ein externes File. Dabei wird jede Row im ResultSet in einer eigenen Zeile dargestellt und
	 * die Columns werden mit einem uebergebenen Trennzeichen getrennt.
	 * @param rs ResultSet, das dargestellt werden soll
	 * @param filename der Name des Files, in dem das ResultSet ausgegeben werden soll
	 * @param delimiter zu verwendendes Trennzeichen
	 */
	public void fileOutput(ResultSet rs, String filename, String delimiter) {
		BufferedWriter file = null;

		try {
			file = new BufferedWriter(new PrintWriter(filename, "UTF-8"));
		} catch (Exception e) {
			System.err.println("Could not create output-file '" + filename + "'.");
		}
		
		try {
			while (rs.next()) {
				for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
					try {
						if(j != 1){
							file.write(delimiter + " ");
						}
						Object wert = rs.getObject(j);
						if(wert == null){
							file.write("NULL");
						}else{
							file.write(wert.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					file.newLine();
				} catch (IOException e) {
					System.err.println("Failed to write newLine in output-file.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			file.close();
		} catch (IOException e) {
			System.err.println("Could not close output-file.");
		}
	}
	
}
