package IHM;

import java.text.ParseException;

import hibernate.Passerelle;

public class StartAppli {

	public static void main(String[] args) throws ParseException {
		try {
			Passerelle back = new Passerelle();
			back.open();
			new FenêtrePrincipal();

		} catch (java.lang.RuntimeException e) {
			System.out.println("Erreur de connexion à la base de données, \nCode de l'erreur : " + e);
		}

	}

}
