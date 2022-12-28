package lomapaivalaskuri;

public class Henkilo {

	 private long id;
	 private String nimi;
	 private int saldo;
	 
	// Constructorit
	private Henkilo() {		
	}
	
	public Henkilo(long id, String nimi, int saldo) {
		this.id = id;
		this.nimi = nimi;
		this.saldo = saldo;
	}
	 
	// Henkilön tunniste
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	// Henkilön etunimi.
	public String getNimi() {
		return nimi;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	
	
	// Henkilön tiedoissa oleva saldo jota vähennetään tai lisätään.
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
}
