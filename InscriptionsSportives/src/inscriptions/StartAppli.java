package inscriptions;

import java.io.IOException;

import dialogueUtilisateur.MenuUtil;
import hibernate.Passerelle;
import interfaceGraph.Fenetre;

public class StartAppli {

	public static void main(String[] args) {
		Passerelle back = new Passerelle();
		back.open();
		Inscriptions inscriptions = Inscriptions.reinitialiser();
//		MenuUtil personnelConsole = new MenuUtil(inscriptions);
//			personnelConsole.start();
		new Fenetre(inscriptions);
//		back.close();
		try
		{
			inscriptions.sauvegarder();
		} 
		catch (IOException e)
		{
			System.out.println("Sauvegarde impossible." + e);
		}

	}

}
