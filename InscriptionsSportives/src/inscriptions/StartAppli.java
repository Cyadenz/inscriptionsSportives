package inscriptions;

import java.io.IOException;
import java.text.ParseException;

import IHM.FenêtrePrincipal;
import dialogueUtilisateur.MenuUtil;
import hibernate.Passerelle;

public class StartAppli {

	public static void main(String[] args) throws ParseException {
		Passerelle back = new Passerelle();
		back.open();
//		Inscriptions inscriptions = Inscriptions.reinitialiser();
//		MenuUtil personnelConsole = new MenuUtil(inscriptions);
//			personnelConsole.start();
		new FenêtrePrincipal();		
//		back.close();
//		try
//		{
//			inscriptions.sauvegarder();
//		} 
//		catch (IOException e)
//		{
//			System.out.println("Sauvegarde impossible." + e);
//		}

	}

}
