package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eventi.Accadimento;
import eventi.AccadimentoTerremoto;
import serverSistemaCentrale.ConnectionPoolException;
import serverSistemaCentrale.DataBase;

class AccadimentoTerremotoTest {

	private static AccadimentoTerremoto accadimentoTerremotoTest;

	private static Integer capTest;
	private static String sorgenteTest;
	private static String tipoTest;
	private static Timestamp tempoTest;
	private static Double magnitudoTest;

	@BeforeAll
	public static void setUp() throws SQLException, ClassNotFoundException {
		tempoTest = new Timestamp(System.currentTimeMillis());
		capTest = 23895;
		tipoTest = "Terremoto";
		sorgenteTest = "Istituto Nazionale di Geofisica e Vulcanologia";
		magnitudoTest = 3.2;

		accadimentoTerremotoTest = new AccadimentoTerremoto(sorgenteTest);
		accadimentoTerremotoTest.setCap(capTest);
		accadimentoTerremotoTest.setMagnitudo(magnitudoTest);
		accadimentoTerremotoTest.setTipo(tipoTest);

		assertEquals(sorgenteTest, accadimentoTerremotoTest.getSorgente());
		assertEquals(capTest, accadimentoTerremotoTest.getCap());
		assertSame(tipoTest, accadimentoTerremotoTest.getTipo());
		assertEquals(magnitudoTest, accadimentoTerremotoTest.getMagnitudo());
		System.out.println("Accadimento terremoto creato con successo");
	}

	private static Boolean accadimentoNellaLista(List<Accadimento> risultati) {
		for (Accadimento a : risultati) {
			if (a.getCap().equals(capTest) 
					&& a.getSorgente().equals(sorgenteTest)
					&& (a.getTempo().getTime()/60000) == (tempoTest.getTime()/60000)
					&& a.getTipo().equals(tipoTest))
				return true;
		}
		return false;
	}

	@Test
	void test() throws SQLException {
		// inserimento accadimento nel db
		try {
			DataBase.inserimentoAccTerremoto(accadimentoTerremotoTest);


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		System.out.println("Accadimento terremoto inserito con successo");

		// controllo se l'accadimento terremoto è stato memorizzato correttamente nel db
		// utilizzando la ricerca per cap
		List<Accadimento> risultatiPerCap = null;
		try {
			risultatiPerCap = DataBase.cercaAccByCap(capTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(accadimentoNellaLista(risultatiPerCap));
		System.out.println("Accadimento terremoto trovato tramite ricerca per cap");

		// controllo se l'accadimento terremoto è stato memorizzato correttamente nel db
		// utilizzando la ricerca per tempo
		List<Accadimento> risultatiPerTempo = null;
		long tempoIniz = tempoTest.getTime() - 900000;
		long tempoFin = tempoTest.getTime() + 90000;
		Timestamp tIniz = new Timestamp(tempoIniz);
		Timestamp tFin = new Timestamp(tempoFin);
		try {
			risultatiPerTempo = DataBase.cercaAccByTempo(tIniz, tFin);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(accadimentoNellaLista(risultatiPerTempo));
		System.out.println("Accadimento terremoto trovato tramite ricerca per tempo");

		// controllo se l'accadimento terremoto è stato memorizzato correttamente nel db
		// utilizzando la ricerca per tipo
		List<Accadimento> risultatiPerTipo = null;
		try {
			risultatiPerTipo = DataBase.cercaAccByTipo(tipoTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(accadimentoNellaLista(risultatiPerTipo));
		System.out.println("Accadimento terremoto trovato tramite ricerca per tipo");

	}
	@AfterAll
	public static void tearDown() throws SQLException {
		// rimozione dell'accadimento di test dal db e controllo che sia stato
		// effettivamente cancellato
		try {
			DataBase.cancellaDuplicatiAcc(capTest, tipoTest, tempoTest);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ConnectionPoolException e1) {
			e1.printStackTrace();
		}

		List<Accadimento> risultatiPerCap = null;
		try {
			risultatiPerCap = DataBase.cercaAccByCap(capTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertFalse(accadimentoNellaLista(risultatiPerCap));
		System.out.println("Accadimento terremoto correttamente cancellato dal db");

	}

}