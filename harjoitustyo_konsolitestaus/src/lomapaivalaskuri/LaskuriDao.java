package lomapaivalaskuri;

import java.util.List;




//aloitetaan aina avainsanalla interface ei class
//ei sisalla mita itse metodi tekee,
//vaan vain mita se saa parametrina ja palauttaa

public interface LaskuriDao {

	public List<Henkilo> getAllHenkilot();
	
	public Henkilo getHenkilo(long id);
	
	public boolean addHenkilo(Henkilo newHenkilo);

    public boolean removeHenkilo(Henkilo henkilo);

    public boolean addToSaldo(long id, int saldo);

    public boolean removeFromSaldo(long id, int saldo);
	
}
