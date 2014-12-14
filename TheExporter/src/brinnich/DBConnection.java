package brinnich;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Stellt eine Datenbankverbindung dar. Man kann sich mit Servername, Username und Passwort an einer Datenbank anmelden 
 * und eine Select-Abfrage an diese Datenbank schicken.
 * 
 * @author Selina Brinnich
 * @version 2014-12-14
 *
 */
public class DBConnection {
	
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	/**
	 * Stellt eine Verbindung zur Datenbank her
	 * @param servername Name des Servers, auf dem die Datenbank liegt
	 * @param user Der Username, der zum Anmelden verwendet werden soll
	 * @param password Das Passwort, das zum Anmelden verwendet werden soll
	 */
	public void connect(String servername, String user, String password){
		// Datenquelle erzeugen und konfigurieren
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(servername);
		ds.setUser(user);
		ds.setPassword(password);
		
		// Verbindung herstellen
		try {
			con = ds.getConnection();
		} catch (SQLException e1) {
			System.err.println(e1.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Fuehrt eine Anfrage an die Datenbank aus
	 * @param database die Datenbank, an die die Query geschickt werden soll
	 * @param query ein String mit der Anfrage
	 * @return ein ResultSet, das die Ergebnisse der Select-Abfrage enthaelt
	 */
	public ResultSet executeStatement(String database, String query){
		// Abfrage vorbereiten und ausführen
		try {
			st = con.createStatement();
			// Datenbank auswaehlen
			rs = st.executeQuery("USE " + database + ";");
			// SELECT ausfuehren
			rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Schliesst alle Verbindungen
	 */
	public void closeConnections(){
		try {
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
