package dialogueUtilisateur;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import commandLineMenus.examples.employees.core.Department;
import commandLineMenus.examples.employees.core.Employee;
import commandLineMenus.examples.employees.core.ImpossibleToSaveException;
import commandLineMenus.examples.employees.core.ManageEmployees;
import inscriptions.*;

public class MenuUtil {

private ManageEmployees gestionPersonnel;
	
	public MenuUtil(ManageEmployees gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
	}
	
	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel");
		menu.add(editerEmploye(gestionPersonnel.getRoot()));
		menu.add(Compet());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Menu Compet()
	{
		Menu menu = new Menu("Gérer les Competitions", "l");
		menu.add(afficher());
		menu.add(ajoutercompetition());
		menu.add(selectionnercompetition());
		menu.addBack("q");
		return menu;
	}
	
	private Menu Candidat()
	{
		Menu menu = new Menu("Gérer les Competitions", "l");
		menu.add(afficher());
		menu.add(ajoutercompetition());
		menu.add(selectionnercompetition());
		menu.addBack("q");
		return menu;
	}
	

	private Option afficher()
	{
		return new Option("Afficher les competitions", "l", () -> {System.out.println(gestionPersonnel.getDepartments());});
	}
	
	private Option afficherEmployes(final Department competition)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(competition.getEmployes());});
	}
	
	private Option afficher(final Department competition)
	{
		return new Option("Afficher la competition", "l", 
				() -> 
				{
					System.out.println(competition);
					System.out.println("administrée par " + competition.getAdministrator());
				}
		);
	}

	private Option afficher(final Employee employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	private Option ajoutercompetition()
	{
		return new Option("Ajouter une competition", "a", () -> {new Department (getString("nom : "));});
	}
	
	private Option ajouterEmploye(final Department competition)
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					competition.addEmploye(getString("nom : "), 
						getString("prenom : "), getString("mail : "), 
						getString("password : "));
				}
		);
	}
	
	private Menu editercompetition(Department competition)
	{
		Menu menu = new Menu("Editer " + competition.getName());
		menu.add(afficher(competition));
		menu.add(gererEmployes(competition));
		menu.add(changerAdministrateur(competition));
		menu.add(changerNom(competition));
		menu.add(supprimer(competition));
		menu.addBack("q");
		return menu;
	}

	private Menu editerEmploye(Employee employe)
	{
		Menu menu = new Menu("Gérer le compte " + employe.getLastName(), "c");
		menu.add(afficher(employe));
		menu.add(changerNom(employe));
		menu.add(changerPrenom(employe));
		menu.add(changerMail(employe));
		menu.add(changerPassword(employe));
		menu.addBack("q");
		return menu;
	}

	private Menu gererEmployes(Department competition)
	{
		Menu menu = new Menu("Gérer les employés de " + competition.getName(), "e");
		menu.add(afficherEmployes(competition));
		menu.add(ajouterEmploye(competition));
		menu.add(modifierEmploye(competition));
		menu.add(supprimerEmploye(competition));
		menu.addBack("q");
		return menu;
	}

	private List<Employee> modifierEmploye(final Department competition)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(competition.getEmployes()),
				(index, element) -> {editerEmploye(element);}
				);
	}
	
	private List<Employee> supprimerEmploye(final Department competition)
	{
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(competition.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}
	
	private List<Employee> changerAdministrateur(final Department competition)
	{
		return new List<Employee>("Changer d'administrateur", "c", 
				() -> new ArrayList<>(competition.getEmployes()), 
				(index, element) -> {competition.setAdministrator(element);}
				);
	}		
	
	private Option changerNom(final Department competition)
	{
		return new Option("Renommer", "r", 
				() -> {competition.setName(getString("Nouveau nom : "));});
	}

	private List<Department> selectionnercompetition()
	{
		return new List<Department>("Sélectionner une competition", "e", 
				() -> new ArrayList<>(gestionPersonnel.getDepartments()),
				(element) -> editercompetition(element)
				);
	}
	
	private Option supprimer(Department competition)
	{
		return new Option("Supprimer", "d", () -> {competition.remove();});
	}
	
	private Option changerNom(final Employee employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setLastName(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employee employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setFirstName(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employee employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employee employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						gestionPersonnel.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (ImpossibleToSaveException e)
					{
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
			);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}
	
	private boolean verifiePassword()
	{
		boolean ok = gestionPersonnel.getRoot().checkPassword(getString("password : "));
		if (!ok)
			System.out.println("Password incorrect.");
		return ok;
	}
	
	public static void main(String[] args)
	{
		MenuUtil personnelConsole = 
				new MenuUtil(ManageEmployees.getManageEmployees());
//		if (personnelConsole.verifiePassword())
			personnelConsole.start();
		
	}
}
