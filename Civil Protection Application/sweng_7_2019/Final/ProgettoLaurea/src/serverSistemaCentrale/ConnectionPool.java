package serverSistemaCentrale;
import java.util.*;
import java.sql.*;

public class ConnectionPool {
	private static ConnectionPool connectionPool = null; // variabile che gestisce l'unica istanza di ConnectionPool
	private List <Connection> freeConnections; // La coda di connessioni libere
	private String dbUrl;           // Il nome del database
	private String dbDriver;        // Il driver del database
	private String dbUser;         // Il login per il database
	private String dbPassword; 		// La password per il database

	// Costruttore della classe ConnectionPool
	protected ConnectionPool() throws ConnectionPoolException {
		// Costruisce la coda delle connessioni libere
		freeConnections = new ArrayList<Connection>();
		// Carica I parametric per l'accesso alla base di dati
		loadParameters();
		// Carica il driver del database
		loadDriver();
	}

	// Funzione privata che carica i parametri per l'accesso al database
	private void loadParameters() {
		// Driver per database mysql
		dbDriver = "com.mysql.jdbc.Driver";
		// Url per un database locale
		dbUrl = "jdbc:mysql://localhost:3306/db_sistema_centrale?serverTimezone=UTC";
		// Login della base di dati
		dbUser = "root";
		// Password della base di dati
		dbPassword = "root";  //Password da cambiare

	}

	// Funzione privata che carica il driver per l'accesso al database.
	// In caso di errore durante il caricamento del driver solleva un'eccezione.
	private void loadDriver() throws ConnectionPoolException {
		try {
			java.lang.Class.forName(dbDriver);
		} catch (Exception e) {
			throw new ConnectionPoolException();
		}
	}


	public static synchronized ConnectionPool getConnectionPool() throws ConnectionPoolException {
		if(connectionPool == null) {
			connectionPool = new ConnectionPool();
		}
		return connectionPool;
	}

	// Il metodo getConnection restituisce una connessione libera prelevandola
	// dalla coda freeConnections oppure se non ci sono connessioni disponibili
	// creandone una nuova con una chiamata a newConnection
	public synchronized Connection getConnection()throws ConnectionPoolException {
		Connection con;
		if(freeConnections.size() > 0) {
			// Se la coda delle connessioni libere non è vuota
			// preleva il primo elemento e lo rimuove dalla coda
			con = (Connection) freeConnections.get(0);
			freeConnections.remove(0);
			try {
				// Verifica se la connessione non è più valida
				if(con.isClosed()) {
					// Richiama getConnection ricorsivamente
					con = getConnection();
				}
			} catch(SQLException e) {
				// Se c'è un errore richiama GetConnection
				// ricorsivamente
				con = getConnection();
			}
		} else {
			// se la coda delle connessioni libere è vuota
			// crea una nuova connessione
			con = newConnection();
		}
		// restituisce la connessione
		return con;
	}

	// Il metodo newConnection restituisce una nuova connessione
	private Connection newConnection() throws ConnectionPoolException {
		Connection con = null;
		try {
			// crea la connessione
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch(SQLException e) {
			// in caso di errore solleva un'eccezione
			throw new ConnectionPoolException();
		}
		// restituisce la nuova connessione
		return con;
	}


	// Il metodo releaseConnection rilascia una connessione inserendola
	// nella coda delle connessioni libere
	public synchronized void releaseConnection(Connection con) {
		// Inserisce la connessione nella coda
		freeConnections.add(con);
	}
}
