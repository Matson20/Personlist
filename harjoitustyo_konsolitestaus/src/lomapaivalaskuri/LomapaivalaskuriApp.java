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
			
			// Merkki k�ytt�j�n sy�tteelle.
			System.out.println("\n");
			System.out.print("> ");
			
			// Tallennetaan k�ytt�j�n sy�te muuttujiin.
			command = input.next(); 
			String parameter = input.nextLine();
			
			// Siistit��n tuotteen parametrin alusta v�lily�nti pois.
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
					System.out.println("Sy�t� haettava ID");
					System.out.println(e.getMessage());
					break;
				}
				
			case "add":
				//tuotteen lis�ys
				System.out.println("Sy�t� nimi");
				String tekstiNimi = input.nextLine();
				
				System.out.println("Sy�t� lomap�iv�m��r�.");
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
				System.out.println("Sy�t� nimi");
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
				//saldon p�ivitys / lis�ys
				System.out.println("Sy�t� ID");
				String tekstiID = input.nextLine();
				
				System.out.println("Sy�t� lis�t�v� lomap�iv�m��r�.");
				String tekstiSaldo = input.nextLine();
				
				try {
					
					int henkiloID = Integer.parseInt(tekstiID);
					int saldoAdd = Integer.parseInt(tekstiSaldo);
					
					if (saldoAdd > 0) {
						dao.addToSaldo(henkiloID, saldoAdd);
					} else {
						System.out.println("Lis�ys operaatio. Lis�� positiivinen kokonaisluku.");
					}
					
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
					break;
				}
				
				
			case "saldoRemove":
				//saldon p�ivitys / v�hennys
				System.out.println("Sy�t� ID");
				tekstiID = input.nextLine();
				
				System.out.println("Sy�t� v�hennett�v� lomap�iv�m��r�.");
				tekstiSaldo = input.nextLine();
				
				try {
					int henkiloID = Integer.parseInt(tekstiID);
					int saldoRemove = Integer.parseInt(tekstiSaldo);
					
					if (saldoRemove < 0) {
						dao.removeFromSaldo(henkiloID, saldoRemove);
					} else {
						System.out.println("V�hennys operaatio. Postiivinen kokonaisluku ei k�y.");
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
