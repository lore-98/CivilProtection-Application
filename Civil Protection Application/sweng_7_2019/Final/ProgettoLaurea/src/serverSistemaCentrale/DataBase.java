package serverSistemaCentrale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import eventi.Accadimento;
import eventi.AccadimentoMeteo;
import eventi.AccadimentoTerremoto;
import eventi.Previsione;
import eventi.PrevisioneMeteo;
import eventi.PrevisioneTerremoto;


public final class DataBase {
	private static ConnectionPool conPool;
	private static Connection connection;
	private static PreparedStatement statement;
	private static ResultSet resultSet;


	public static void inserimentoPrevMeteo(PrevisioneMeteo previsioneMeteo)throws SQLException,ConnectionPoolException {
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();

			String query = " INSERT INTO previsione (cap, tipo, sorgente, tempo, livelloGravità, precipitazione) values (?, ?, ?, ?, ?, ?)";
			statement=connection.prepareStatement(query);

			// create the mysql insert preparedstatement

			statement.setInt(1, previsioneMeteo.getCap());
			statement.setString(2, previsioneMeteo.getTipo());
			statement.setString(3, previsioneMeteo.getSorgente());
			statement.setTimestamp(4, previsioneMeteo.getTempo());
			statement.setInt(5, previsioneMeteo.getLivelloGravità());
			statement.setDouble(6, previsioneMeteo.getPrecipitazione());

			statement.executeUpdate();


		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
	}

	public static void inserimentoPrevTerremoto(PrevisioneTerremoto previsioneTerremoto)throws SQLException,ConnectionPoolException {
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();

			String query = " INSERT INTO previsione (cap, tipo, sorgente, tempo, livelloGravità, magnitudo) values (?, ?, ?, ?, ?, ?)";
			statement=connection.prepareStatement(query);

			// create the mysql insert preparedstatement

			statement.setInt(1, previsioneTerremoto.getCap());
			statement.setString(2, previsioneTerremoto.getTipo());
			statement.setString(3, previsioneTerremoto.getSorgente());
			statement.setTimestamp(4, previsioneTerremoto.getTempo());
			statement.setInt(5, previsioneTerremoto.getLivelloGravità());
			statement.setDouble(6, previsioneTerremoto.getMagnitudo());

			statement.executeUpdate();


		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
	}

	public static void inserimentoAccMeteo(AccadimentoMeteo accadimentoMeteo) throws SQLException,ConnectionPoolException {
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();


			String query = " INSERT INTO accadimento (cap, tipo, sorgente, tempo, precipitazione) values (?, ?, ?, ?, ?)";
			statement=connection.prepareStatement(query);

			// create the mysql insert preparedstatement

			statement.setInt(1, accadimentoMeteo.getCap());
			statement.setString(2, accadimentoMeteo.getTipo());
			statement.setString(3, accadimentoMeteo.getSorgente());
			statement.setTimestamp(4, accadimentoMeteo.getTempo());
			statement.setDouble(5, accadimentoMeteo.getPrecipitazione());

			statement.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}

	}

	public static void inserimentoAccTerremoto(AccadimentoTerremoto accadimentoTerremoto) throws SQLException,ConnectionPoolException {
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();


			String query = " INSERT INTO accadimento (cap, tipo, sorgente, tempo, magnitudo) values (?, ?, ?, ?, ?)";
			statement=connection.prepareStatement(query);
			statement.setInt(1, accadimentoTerremoto.getCap());
			statement.setString(2, accadimentoTerremoto.getTipo());
			statement.setString(3, accadimentoTerremoto.getSorgente());
			statement.setTimestamp(4, accadimentoTerremoto.getTempo());
			statement.setDouble(5, accadimentoTerremoto.getMagnitudo());

			statement.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}

	}

	public static List<Previsione> cercaPrevByCap(Integer idCap)throws SQLException,ConnectionPoolException  {

		List<Previsione> previsioni = new ArrayList<Previsione>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();
			Timestamp tc = new Timestamp(System.currentTimeMillis());

			String query = " SELECT * FROM previsione WHERE cap=? AND tempo > ?";
			statement=connection.prepareStatement(query);
			statement.setInt(1,idCap);
			statement.setTimestamp(2,tc);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					PrevisioneTerremoto pt = new PrevisioneTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					pt.setIdEvento(resultSet.getInt("idprevisione"));
					pt.setCap(resultSet.getInt("cap"));
					pt.setTipo(resultSet.getString("tipo"));
					pt.setTempo(resultSet.getTimestamp("tempo"));
					pt.setLivelloGravità(resultSet.getInt("livellogravità"));
					pt.setMagnitudo(resultSet.getDouble("magnitudo"));
					previsioni.add(pt);
				}
				else {
					PrevisioneMeteo pm = new PrevisioneMeteo("3bMeteo");
					pm.setIdEvento(resultSet.getInt("idprevisione"));
					pm.setCap(resultSet.getInt("cap"));
					pm.setTipo(resultSet.getString("tipo"));
					pm.setTempo(resultSet.getTimestamp("tempo"));
					pm.setLivelloGravità(resultSet.getInt("livellogravità"));
					pm.setPrecipitazione(resultSet.getDouble("precipitazione"));
					previsioni.add(pm);
				}
			}



		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return previsioni;


	}

	public static List<Accadimento> cercaAccByCap(Integer idCap)throws SQLException,ConnectionPoolException  {

		List<Accadimento> accadimenti = new ArrayList<Accadimento>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();
			Timestamp tc = new Timestamp(System.currentTimeMillis());

			String query = " SELECT * FROM accadimento WHERE cap=? AND tempo < ?";
			statement=connection.prepareStatement(query);
			statement.setInt(1,idCap);
			statement.setTimestamp(2,tc);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					AccadimentoTerremoto at = new AccadimentoTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					at.setIdEvento(resultSet.getInt("idaccadimento"));
					at.setCap(resultSet.getInt("cap"));
					at.setTipo(resultSet.getString("tipo"));
					at.setTempo(resultSet.getTimestamp("tempo"));
					at.setMagnitudo(resultSet.getDouble("magnitudo"));
					accadimenti.add(at);
				}
				else {
					AccadimentoMeteo am = new AccadimentoMeteo("3bMeteo");
					am.setIdEvento(resultSet.getInt("idaccadimento"));
					am.setCap(resultSet.getInt("cap"));
					am.setTipo(resultSet.getString("tipo"));
					am.setTempo(resultSet.getTimestamp("tempo"));
					am.setPrecipitazione(resultSet.getDouble("precipitazione"));
					accadimenti.add(am);
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return accadimenti;


	}

	public static List<Previsione> cercaPrevByTempo(Timestamp tempoIniz, Timestamp tempoFin)throws SQLException,ConnectionPoolException  {

		List<Previsione> previsioni = new ArrayList<Previsione>();
		try { 

			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();


			String query = " SELECT * FROM previsione WHERE tempo >= ? AND tempo <= ?";
			statement=connection.prepareStatement(query);
			statement.setTimestamp(1,tempoIniz);
			statement.setTimestamp(2,tempoFin);


			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					PrevisioneTerremoto pt = new PrevisioneTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					pt.setIdEvento(resultSet.getInt("idprevisione"));
					pt.setCap(resultSet.getInt("cap"));
					pt.setTipo(resultSet.getString("tipo"));
					pt.setTempo(resultSet.getTimestamp("tempo"));
					pt.setLivelloGravità(resultSet.getInt("livellogravità"));
					pt.setMagnitudo(resultSet.getDouble("magnitudo"));
					previsioni.add(pt);
				}
				else {
					PrevisioneMeteo pm = new PrevisioneMeteo("3bMeteo");
					pm.setIdEvento(resultSet.getInt("idprevisione"));
					pm.setCap(resultSet.getInt("cap"));
					pm.setTipo(resultSet.getString("tipo"));
					pm.setTempo(resultSet.getTimestamp("tempo"));
					pm.setLivelloGravità(resultSet.getInt("livellogravità"));
					pm.setPrecipitazione(resultSet.getDouble("precipitazione"));
					previsioni.add(pm);
				}
			}



		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return previsioni;

	}

	public static List<Accadimento> cercaAccByTempo(Timestamp tempoIniz, Timestamp tempoFin)throws SQLException,ConnectionPoolException {

		List<Accadimento> accadimenti = new ArrayList<Accadimento>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();


			String query = " SELECT * FROM accadimento WHERE tempo >= ? AND tempo <= ?";
			statement=connection.prepareStatement(query);
			statement.setTimestamp(1,tempoIniz);
			statement.setTimestamp(2,tempoFin);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					AccadimentoTerremoto at = new AccadimentoTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					at.setIdEvento(resultSet.getInt("idaccadimento"));
					at.setCap(resultSet.getInt("cap"));
					at.setTipo(resultSet.getString("tipo"));
					at.setTempo(resultSet.getTimestamp("tempo"));
					at.setMagnitudo(resultSet.getDouble("magnitudo"));
					accadimenti.add(at);
				}
				else {
					AccadimentoMeteo am = new AccadimentoMeteo("3bMeteo");
					am.setIdEvento(resultSet.getInt("idaccadimento"));
					am.setCap(resultSet.getInt("cap"));
					am.setTipo(resultSet.getString("tipo"));
					am.setTempo(resultSet.getTimestamp("tempo"));
					am.setPrecipitazione(resultSet.getDouble("precipitazione"));
					accadimenti.add(am);
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return accadimenti;
	}

	public static List<Previsione> cercaPrevByTipo(String tipo)throws SQLException,ConnectionPoolException  {

		List<Previsione> previsioni = new ArrayList<Previsione>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();
			Timestamp tc = new Timestamp(System.currentTimeMillis());

			String query = " SELECT * FROM previsione WHERE tipo=? AND tempo > ?";
			statement=connection.prepareStatement(query);
			statement.setString(1,tipo);
			statement.setTimestamp(2,tc);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					PrevisioneTerremoto pt = new PrevisioneTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					pt.setIdEvento(resultSet.getInt("idprevisione"));
					pt.setCap(resultSet.getInt("cap"));
					pt.setTipo(resultSet.getString("tipo"));
					pt.setTempo(resultSet.getTimestamp("tempo"));
					pt.setLivelloGravità(resultSet.getInt("livellogravità"));
					pt.setMagnitudo(resultSet.getDouble("magnitudo"));
					previsioni.add(pt);
				}
				else {
					PrevisioneMeteo pm = new PrevisioneMeteo("3bMeteo");
					pm.setIdEvento(resultSet.getInt("idprevisione"));
					pm.setCap(resultSet.getInt("cap"));
					pm.setTipo(resultSet.getString("tipo"));
					pm.setTempo(resultSet.getTimestamp("tempo"));
					pm.setLivelloGravità(resultSet.getInt("livellogravità"));
					pm.setPrecipitazione(resultSet.getDouble("precipitazione"));
					previsioni.add(pm);
				}
			}



		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return previsioni;


	}

	public static List<Accadimento> cercaAccByTipo(String tipo)throws SQLException,ConnectionPoolException  {

		List<Accadimento> accadimenti = new ArrayList<Accadimento>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();
			Timestamp tc = new Timestamp(System.currentTimeMillis());

			String query = " SELECT * FROM accadimento WHERE tipo=? AND tempo < ?";
			statement=connection.prepareStatement(query);
			statement.setString(1,tipo);
			statement.setTimestamp(2,tc);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					AccadimentoTerremoto at = new AccadimentoTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					at.setIdEvento(resultSet.getInt("idaccadimento"));
					at.setCap(resultSet.getInt("cap"));
					at.setTipo(resultSet.getString("tipo"));
					at.setTempo(resultSet.getTimestamp("tempo"));
					at.setMagnitudo(resultSet.getDouble("magnitudo"));
					accadimenti.add(at);
				}
				else {
					AccadimentoMeteo am = new AccadimentoMeteo("3bMeteo");
					am.setIdEvento(resultSet.getInt("idaccadimento"));
					am.setCap(resultSet.getInt("cap"));
					am.setTipo(resultSet.getString("tipo"));
					am.setTempo(resultSet.getTimestamp("tempo"));
					am.setPrecipitazione(resultSet.getDouble("precipitazione"));
					accadimenti.add(am);
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return accadimenti;


	}

	public static List<Timestamp> cercaDuplicatiPrevByCapTipo(int cap, String tipo)throws SQLException,ConnectionPoolException  {

		List<Timestamp> tempiPrevisioni = new ArrayList<Timestamp>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();

			String query = " SELECT idprevisione, cap, tipo, tempo FROM previsione WHERE cap=? AND tipo=?";
			statement=connection.prepareStatement(query);

			// create the mysql insert preparedstatement

			statement.setInt(1,cap);
			statement.setString(2,tipo);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				Timestamp t = resultSet.getTimestamp("tempo");

				tempiPrevisioni.add(t);
			}


		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return tempiPrevisioni;
	}

	public static List<Timestamp> cercaDuplicatiAccByCapTipo(int cap, String tipo)throws SQLException,ConnectionPoolException  {

		List<Timestamp> tempiAccadimenti = new ArrayList<Timestamp>();
		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();

			String query = " SELECT idaccadimento, cap, tipo, tempo FROM accadimento WHERE cap=? AND tipo=?";
			statement=connection.prepareStatement(query);

			// create the mysql insert preparedstatement

			statement.setInt(1,cap);
			statement.setString(2,tipo);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				Timestamp t = resultSet.getTimestamp("tempo");

				tempiAccadimenti.add(t);
			}


		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return tempiAccadimenti;
	}

	public static void cancellaDuplicatiPrev(int cap, String tipo, Timestamp tempo) throws SQLException,ConnectionPoolException {
		long t1 = tempo.getTime() - 60000;
		long t2 = tempo.getTime() + 60000;
		Timestamp tempo1 = new Timestamp(t1);
		Timestamp tempo2 = new Timestamp(t2);

		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();


			String query = "DELETE FROM previsione WHERE cap=? AND tipo=? AND tempo BETWEEN ? AND ?";
			statement=connection.prepareStatement(query);

			statement.setInt(1, cap);
			statement.setString(2, tipo);
			statement.setTimestamp(3, tempo1);
			statement.setTimestamp(4, tempo2);

			statement.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}

	}


	public static void cancellaDuplicatiAcc(int cap, String tipo, Timestamp tempo) throws SQLException,ConnectionPoolException {
		long t1 = tempo.getTime() - 60000;
		long t2 = tempo.getTime() + 60000;
		Timestamp tempo1 = new Timestamp(t1);
		Timestamp tempo2 = new Timestamp(t2);


		try { 
			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();


			String query = "DELETE FROM accadimento WHERE cap=? AND tipo=? AND tempo BETWEEN ? AND ?";
			statement=connection.prepareStatement(query);
			statement.setInt(1, cap);
			statement.setString(2, tipo);
			statement.setTimestamp(3, tempo1);
			statement.setTimestamp(4, tempo2);

			statement.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}

	}

	public static List<Previsione> trovaAllerta(int cap) throws SQLException,ConnectionPoolException{

		List<Previsione> previsioni = new ArrayList<Previsione>();
		try { 

			conPool= ConnectionPool.getConnectionPool();
			connection = conPool.getConnection();
			Timestamp tc = new Timestamp(System.currentTimeMillis());


			String query = " SELECT * FROM previsione WHERE cap = ? AND livellogravità >=6 AND tempo > ? ORDER BY tempo ASC";
			statement=connection.prepareStatement(query);
			statement.setInt(1,cap);
			statement.setTimestamp(2,tc);

			resultSet=statement.executeQuery();

			while(resultSet.next())
			{
				if(resultSet.getString("sorgente").equals("Istituto Nazionale di Geofisica e Vulcanologia")) {
					PrevisioneTerremoto pt = new PrevisioneTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
					pt.setIdEvento(resultSet.getInt("idprevisione"));
					pt.setCap(resultSet.getInt("cap"));
					pt.setTipo(resultSet.getString("tipo"));
					pt.setTempo(resultSet.getTimestamp("tempo"));
					pt.setLivelloGravità(resultSet.getInt("livellogravità"));
					pt.setMagnitudo(resultSet.getDouble("magnitudo"));
					previsioni.add(pt);
				}
				else {
					PrevisioneMeteo pm = new PrevisioneMeteo("3bMeteo");
					pm.setIdEvento(resultSet.getInt("idprevisione"));
					pm.setCap(resultSet.getInt("cap"));
					pm.setTipo(resultSet.getString("tipo"));
					pm.setTempo(resultSet.getTimestamp("tempo"));
					pm.setLivelloGravità(resultSet.getInt("livellogravità"));
					pm.setPrecipitazione(resultSet.getDouble("precipitazione"));
					previsioni.add(pm);
				}
			}



		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("error: "+ e.getMessage());
		}finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conPool.releaseConnection(connection);}
		return previsioni;
	}
}