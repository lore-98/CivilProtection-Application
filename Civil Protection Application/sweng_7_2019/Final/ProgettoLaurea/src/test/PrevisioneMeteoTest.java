package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eventi.Previsione;
import eventi.PrevisioneMeteo;
import serverSistemaCentrale.ConnectionPoolException;
import serverSistemaCentrale.DataBase;

class PrevisioneMeteoTest {

	private static PrevisioneMeteo previsioneMeteoTest;

	private static Integer capTest;
	private static String sorgenteTest;
	private static String tipoTest;
	private static Integer livelloGravit‡Test;
	private static Timestamp tempoTest;
	private static Double precipitazioneTest;

	@BeforeAll
	public static void setUp() throws SQLException, ClassNotFoundException {
		livelloGravit‡Test = 7;
		tempoTest = new Timestamp(System.currentTimeMillis() + 900000);
		capTest = 22030;
		tipoTest = "Pioggia";
		sorgenteTest = "3bMeteo";
		precipitazioneTest = 31.0;

		previsioneMeteoTest = new PrevisioneMeteo(sorgenteTest);
		previsioneMeteoTest.setCap(capTest);
		previsioneMeteoTest.setLivelloGravit‡(livelloGravit‡Test);
		previsioneMeteoTest.setPrecipitazione(precipitazioneTest);
		previsioneMeteoTest.setTempo(tempoTest);
		previsioneMeteoTest.setTipo(tipoTest);

		assertSame(livelloGravit‡Test, previsioneMeteoTest.getLivelloGravit‡());
		assertEquals(sorgenteTest, previsioneMeteoTest.getSorgente());
		assertEquals(tempoTest, previsioneMeteoTest.getTempo());
		assertEquals(capTest, previsioneMeteoTest.getCap());
		assertSame(tipoTest, previsioneMeteoTest.getTipo());
		assertEquals(precipitazioneTest, previsioneMeteoTest.getPrecipitazione());
		System.out.println("Previsione creata con successo");
	}


	private static Boolean previsioneNellaLista(List<Previsione> risultati){      
		for(Previsione p : risultati) {
			if(p.getCap().equals(capTest) 
					&& p.getLivelloGravit‡() == livelloGravit‡Test.intValue() 
					&& p.getSorgente().equals(sorgenteTest) 
					&& (p.getTempo().getTime()/60000) == (tempoTest.getTime()/60000)
					&& p.getTipo().equals(tipoTest))
				return true;
		}      
		return false;
	}
	@Test
	void test() throws SQLException{
		//inserimento previsione nel db
		try {
			DataBase.inserimentoPrevMeteo(previsioneMeteoTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		System.out.println("Previsione meteo inserita con successo");

		//controllo se la previsione meteo Ë stata memorizzata correttamente nel db utilizzando la ricerca per cap
		List<Previsione> risultatiPerCap = null;
		try {
			risultatiPerCap = DataBase.cercaPrevByCap(capTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(previsioneNellaLista(risultatiPerCap));  
		System.out.println("Previsione meteo trovata tramite ricerca per cap");


		//controlla se la previsione meteo Ë stata memorizzata correttamente nel db utilizzando la ricerca per tempo    
		List<Previsione> risultatiPerTempo = null;    
		long tempoIniz = tempoTest.getTime()-900000;
		long tempoFin = tempoTest.getTime()+90000;
		Timestamp tIniz= new Timestamp(tempoIniz);
		Timestamp tFin= new Timestamp(tempoFin);
		try {
			risultatiPerTempo = DataBase.cercaPrevByTempo(tIniz, tFin);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(previsioneNellaLista(risultatiPerTempo));
		System.out.println("Previsione meteo trovata tramite ricerca per tempo");
		//controlla se la previsione meteo Ë stata memorizzata correttamente nel db utilizzando la ricerca per tipo
		List<Previsione> risultatiPerTipo = null;
		try {
			risultatiPerTipo = DataBase.cercaPrevByTipo(tipoTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(previsioneNellaLista(risultatiPerTipo));
		System.out.println("Previsione meteo trovata tramite ricerca per tipo");


		//controlla se la previsione meteo Ë restituita come previsione da notificare come allerta (livello gravit‡ >= 6)
		List<Previsione> risultatiPerAllerte = null;
		try {
			risultatiPerAllerte = DataBase.trovaAllerta(capTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertTrue(previsioneNellaLista(risultatiPerAllerte));
		System.out.println("Previsione meteo trovata tramite la ricerca allerte");


	}

	@AfterAll
	public static void tearDown() throws SQLException {
		//rimozione della previsione di test dal db e controllo che sia stata effettivamente cancellata
		try {
			DataBase.cancellaDuplicatiPrev(capTest, tipoTest, tempoTest);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ConnectionPoolException e1) {
			e1.printStackTrace();
		}

		List<Previsione> risultatiPerCap = null;
		try {
			risultatiPerCap = DataBase.cercaPrevByCap(capTest);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		assertFalse(previsioneNellaLista(risultatiPerCap));  
		System.out.println("Previsione meteo correttamente cancellata dal db");

	}

}