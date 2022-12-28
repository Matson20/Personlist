package lomapaivalaskuri;

import java.util.List;
import java.util.Scanner;



public class LomapaivalaskuriApp {
	public static void main(String[] args) {
		
		// Kutsutaan oliota Laskuri-luokasta
		// Saadaan kutsuttua metodeja
		JDBCLaskuriDao dao = new JDBCLaskuriDao();
		
		// Aplikaatio
		Scanner input = new Scanner(System.in);
		String command = "";
		
		System.out.println("Laskuri testailu aplikaatio");
		
		// Komentojen testailua
		
while (!command.equals("quit")) {
			
			// Merkki käyttäjän syötteelle.
			System.out.println("\n");
			System.out.print("> ");
			
			// Tallennetaan käyttäjän syöte muuttujiin.
			command = input.next(); 
			String parameter = input.nextLine();
			
			// Siistitään tuotteen parametrin alusta välilyönti pois.
			String noSpaceParameter = parameter.replaceFirst("\\s", "");
			System.out.println("\n");
			
			switch (command) {
			case "quit":
				System.out.println("Bye!");
				break;
				
			case "list":
				try {
					List<Henkilo> henkiloLista = dao.getAllHenkilot();
					
					for (int i = 0; i<henkiloLista.size(); i++) {
						System.out.println(henkiloLista.get(i).getId() + " " + henkiloLista.get(i).getNimi() + " "
								+ henkiloLista.get(i).getSaldo());
					}
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				
				
			case "get":
				// Tuotteen haku ID avulla
				try {
					int nimiID = Integer.parseInt(noSpaceParameter);
					
					System.out.println(dao.getHenkilo(nimiID).getId() + " " + dao.getHenkilo(nimiID).getNimi() +
							" " + dao.getHenkilo(nimiID).getSaldo());
					break;
					
				} catch(Exception e) {
					System.out.println("Syötä haettava ID");
					System.out.println(e.getMessage());
					break;
				}
				
			case "add":
				//tuotteen lisäys
				System.out.println("Syötä nimi");
				String tekstiNimi = input.nextLine();
				
				System.out.println("Syötä lomapäivämäärä.");
				String uusiSaldo = input.nextLine();
				
				try {
					
					int lukuSaldo = Integer.parseInt(uusiSaldo);
					
					Henkilo henkilo = new Henkilo(1, tekstiNimi, lukuSaldo);
					dao.addHenkilo(henkilo);
					
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				
			case "remove":
				//Tuotteen poisto
				System.out.println("Syötä nimi");
				tekstiNimi = input.nextLine();
				try {
					Henkilo tuotePois = new Henkilo(1, tekstiNimi, 1);
					dao.removeHenkilo(tuotePois);
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				
			
			case "saldoAdd":
				//saldon päivitys / lisäys
				System.out.println("Syötä ID");
				String tekstiID = input.nextLine();
				
				System.out.println("Syötä lisätävä lomapäivämäärä.");
				String tekstiSaldo = input.nextLine();
				
				try {
					
					int henkiloID = Integer.parseInt(tekstiID);
					int saldoAdd = Integer.parseInt(tekstiSaldo);
					
					if (saldoAdd > 0) {
						dao.addToSaldo(henkiloID, saldoAdd);
					} else {
						System.out.println("Lisäys operaatio. Lisää positiivinen kokonaisluku.");
					}
					
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				
				
			case "saldoRemove":
				//saldon päivitys / vähennys
				System.out.println("Syötä ID");
				tekstiID = input.nextLine();
				
				System.out.println("Syötä vähennettävä lomapäivämäärä.");
				tekstiSaldo = input.nextLine();
				
				try {
					int henkiloID = Integer.parseInt(tekstiID);
					int saldoRemove = Integer.parseInt(tekstiSaldo);
					
					if (saldoRemove < 0) {
						dao.removeFromSaldo(henkiloID, saldoRemove);
					} else {
						System.out.println("Vähennys operaatio. Postiivinen kokonaisluku ei käy.");
					}
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				/*
			case "help":
				// Printaa ohjeet
				printInfo();
			    break;
			    */
			} // switch loppu
			
		}
		
	}
	

}
