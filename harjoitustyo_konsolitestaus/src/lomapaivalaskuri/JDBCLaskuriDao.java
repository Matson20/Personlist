package lomapaivalaskuri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;




public class JDBCLaskuriDao implements LaskuriDao {
	
	@Override
	public List<Henkilo> getAllHenkilot() {
		
		// Yhteys stringin m‰‰ritys
		String URL = "jdbc:sqlite:.\\henkilosaldo.sqlite";
		
		try {
			// Avataan yhteys
			Connection yhteys = DriverManager.getConnection(URL);
			// Luodaan SQL-kysely, joka hakee kaikki tuotteet tietokannasta
			PreparedStatement kysely = yhteys.prepareStatement("SELECT * FROM henkilo");
			// Suoritetaan SQL-kysely, joka ei ole tietokantaa p‰ivitt‰v‰
			ResultSet tulokset = kysely.executeQuery();
			
			// Luodaan lista johon laitetaan kyselyn tulokset
			ArrayList<Henkilo> henkilot = new ArrayList<Henkilo>();
			
			// K‰yd‰‰n kantaa l‰vitse
			while (tulokset.next()) {
	
	            // Merkkijonot haetaan aina getstringill‰
				long id = tulokset.getLong("id");
	            String henkilonimi = tulokset.getString("nimi");
	            int saldo = tulokset.getInt("saldo");
	            
	            // Tuote voidaan lis‰t‰ parametrillisell‰ konstruktorilla
	            Henkilo henkilo = new Henkilo(id, henkilonimi, saldo);
	            // Lis‰t‰‰n listaan yksitellen tuotteita
	            henkilot.add(henkilo);
	            
	        }
			
			// Suljetaan yhteys
			tulokset.close();
			kysely.close();
			yhteys.close();
			
			return henkilot;
		
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	
		
	} // get AllHenkilot loppu
	
	@Override
	public Henkilo getHenkilo(long id) {
		
		// Yhteys stringin m‰‰ritys
		String URL = "jdbc:sqlite:.\\henkilosaldo.sqlite";
		
		try {
			// Avataan yhteys
    		Connection yhteys = DriverManager.getConnection(URL);
    		
    		PreparedStatement henkiloKysely = yhteys.prepareStatement("SELECT id, nimi, saldo FROM henkilo WHERE id = (?);");
    		
	        // Asetetaan ensimm‰iseen kysymysmerkkin arvo tyyppitarkastuksen l‰pi
	        henkiloKysely.setLong(1, id);

	        ResultSet tulokset = henkiloKysely.executeQuery();
	        
	        long henkiloID = tulokset.getLong("id");
	        String henkilonimi = tulokset.getString("nimi");
	        int henkilosaldo = tulokset.getInt("saldo");
	        
	        
	        // Tuote voidaan lis‰t‰ parametrillisell‰ konstruktorilla
            Henkilo henkilo = new Henkilo(henkiloID, henkilonimi, henkilosaldo);
	        
	        tulokset.close();
	        henkiloKysely.close();
	        yhteys.close();
	        
	        return henkilo;
			
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	} // getHenkilo loppu
	
	@Override
    public boolean addHenkilo(Henkilo newHenkilo) {
    	
		// Yhteys stringin m‰‰ritys
    	String URL = "jdbc:sqlite:.\\henkilosaldo.sqlite";
    	
    	// Haetaan newHenkilo ja lis‰t‰‰n muuttujaan
    	String nimi = newHenkilo.getNimi();
    	int saldo = newHenkilo.getSaldo();
    	try {
    		Connection yhteys = DriverManager.getConnection(URL);
    		
    		// Ostoslistaan lis‰ys
    		PreparedStatement insertLisaa = yhteys.prepareStatement("INSERT INTO henkilo (nimi, saldo) VALUES (?, ?)");
            // Asetetaan ensimm‰iseen kysymysmerkkin arvo tyyppitarkastuksen l‰pi
    		
    		insertLisaa.setString(1, nimi);
    		insertLisaa.setInt(2, saldo);
     		insertLisaa.executeUpdate();
        
            insertLisaa.close();
            yhteys.close();
            
            System.out.println("Adding executed!");
            return true;
            
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    }

    @Override
    public boolean removeHenkilo(Henkilo henkilo) {
    	
    	// Yhteys stringin m‰‰ritys
    	String URL = "jdbc:sqlite:.\\henkilosaldo.sqlite";
    	
    	String nimi = henkilo.getNimi();
    	try {
    		Connection yhteys = DriverManager.getConnection(URL);
    		PreparedStatement insertPoisto = yhteys.prepareStatement("DELETE FROM henkilo WHERE nimi = (?)");
            
            insertPoisto.setString(1, nimi);

            // Insert, update ja delete tapauksissa pit‰‰ surittaa executeUpdate EI
            // executeQuery
            insertPoisto.executeUpdate();
            
            insertPoisto.close();
            yhteys.close();
            
            System.out.println("Remove executed!");
            return true;
            
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    
    }

    @Override
    public boolean addToSaldo(long id, int saldo) {
    	
    	// Yhteys stringin m‰‰ritys
    	String URL = "jdbc:sqlite:.\\henkilosaldo.sqlite";
    	
    	// 
    	try {
    		// Avataan yhteys
    		Connection yhteys = DriverManager.getConnection(URL);
    		// TUTKI MITEN UPDATE LAUSE MENEE
    		PreparedStatement saldoLisaa = yhteys.prepareStatement("UPDATE henkilo SET saldo = saldo + (?) WHERE id = (?)");
    		
    		saldoLisaa.setInt(1, saldo);
    		saldoLisaa.setLong(2, id);
    		saldoLisaa.executeUpdate();
    		
    		saldoLisaa.close();
    		yhteys.close();
    		
    		System.out.println("Update executed!");
    		return true;
    		
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return false;
    	}

    }; // addToSaldo loppu

    @Override
    public boolean removeFromSaldo(long id, int saldo) {
    	
    	// Yhteys stringin m‰‰ritys
    	String URL = "jdbc:sqlite:.\\henkilosaldo.sqlite";
    	
    	// 
    	try {
    		// Avataan yhteys
    		Connection yhteys = DriverManager.getConnection(URL);
    		// TUTKI MITEN UPDATE LAUSE MENEE
    		PreparedStatement saldoVahenna = yhteys.prepareStatement("UPDATE henkilo SET saldo = saldo - (?) WHERE id = (?)");
    		
    		saldoVahenna.setInt(1, saldo);
    		saldoVahenna.setLong(2, id);
    		saldoVahenna.executeUpdate();
    		
    		saldoVahenna.close();
    		yhteys.close();
    		
    		System.out.println("Update executed!");
    		return true;
    		
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    }; // removeFromSaldo loppu

}
