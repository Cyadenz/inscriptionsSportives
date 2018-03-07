package dialogueUtilisateur;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
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
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		menuPrincipal(inscriptions).start();
	}
	
	private Menu menuPrincipal(Inscriptions inscriptions)
	{
		Menu menu = new Menu("Gestion du personnel");
		menu.add(editerEmploye(gestionPersonnel.getRoot()));
		menu.add(Compet(inscriptions));
		menu.add(Equipe(inscriptions));
		menu.add(Personne(inscriptions));
		menu.add(menuQuitter(inscriptions));
		return menu;
	}

	private Menu menuQuitter(Inscriptions inscriptions)
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer(inscriptions));
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Menu Compet(Inscriptions inscriptions)
	{
		Menu menu = new Menu("Gérer les Compétitions", "l");
		menu.add(afficher(inscriptions));
		menu.add(ajoutercompetition(inscriptions));
		menu.add(selectionnercompetition(inscriptions));
		menu.addBack("q");
		return menu;
	}
	
	private Menu Personne(Inscriptions inscriptions)
	{
		Menu menu = new Menu("Gérer les personnes", "a");
		menu.add(afficherPersonnes(inscriptions));
		menu.add(ajouterPersonne(inscriptions));
		menu.add(selectionnerPersonne(inscriptions));
		menu.addBack("q");
		return menu;
	}
	
	private Menu Equipe(Inscriptions inscriptions)
	{
		Menu menu = new Menu("Gérer les équipes", "e");
		menu.add(afficherEquipe(inscriptions));
		menu.add(ajouterEquipe(inscriptions));
		menu.add(selectionnerEquipe(inscriptions));
		menu.addBack("q");
		return menu;
	}
	private Option afficher(Inscriptions inscriptions)
	{
		return new Option("Afficher les competitions", "l", () -> {System.out.println(inscriptions.getCompetitions());});
	}
	private Option afficherEquipe(Inscriptions inscriptions)
	{
		return new Option("Afficher les équipes", "l", () -> {System.out.println(inscriptions.getEquipes());});
	}
	private Option afficherCandidats(final Competition competition)
	{
		return new Option("Afficher les candidats", "l", () -> {System.out.println(competition.getCandidats());});
	}
	private Option afficherPersonnes(Inscriptions inscriptions)
	{
		return new Option("Afficher les personnes", "l", () -> {System.out.println(inscriptions.getPersonnes());});
	}
	private Option afficher(final Competition competition)
	{
		return new Option("Afficher la competition", "l", 
				() -> 
				{
					System.out.println("Compétition : "+competition+", Etat des inscriptions : "+competition.inscriptionsOuvertes()+", Date de cloture : "
				+competition.getDateCloture()+", Reserve aux equipes ? "+competition.estEnEquipe()+", Candidats de cette compétition :"+competition.getCandidats());
				}
		);
	}

	private Option afficher(final Employee employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}
	
	private Option afficherEquipe(final Equipe equipe)
	{
		return new Option("Afficher l'équipe", "l", () -> {System.out.println(equipe);});
	}
	private Option afficherPersonne(Personne personne)
	{
		return new Option("Afficher les informations", "l", () -> {System.out.println("Nom : "+personne.getNom()+" Prenom : "+personne.getPrenom()+" Mail : "+personne.getMail());});
	}
	private LocalDate recupDateLocalFormat(String input)
	{
		final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final LocalDate localDate = LocalDate.parse(input, DATE_FORMAT);
        return localDate;
	}
	private Option ajoutercompetition(Inscriptions inscriptions)
	{
		return new Option("Ajouter une competition", "a", () -> 
		{
			inscriptions.createCompetition((getString("nom : ")), null, false);
		} );
//AJOUT DE 2 OPTIONS POUR L4EQUIPE		
	}
	private Option ajouterEquipe(Inscriptions inscriptions)
	{
		return new Option("Ajouter une équipe", "a", () -> 
		{
			inscriptions.createEquipe(getString("nom : "));
		} );	
	}
	private Option ajouterPersonne(Inscriptions inscriptions)
	{
		return new Option("Ajouter une personne", "a",
				() -> 
				{
					inscriptions.createPersonne(getString("nom : "), 
						getString("prenom : "), getString("mail : "));
				}	
		);
	}
	private Option selectionnerPersonne(Equipe equipe, Inscriptions inscriptions)
	{
		return new List<Personne>("Sélectionner une personne", "e", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonnes(element)
				);
	}
	private Menu editercompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficher(competition));
		menu.add(changerNom(competition));
		menu.add(gererDatecloture(competition)); // NE FONCTIONNE PAS
		menu.add(gererCandidats(competition));
		menu.add(supprimer(competition));
		menu.addBack("q");
		return menu;
	}
	private Menu editerEquipes(Equipe equipe, Inscriptions inscriptions)
	{
		Menu menu = new Menu("Editer " + equipe.getNom());
		menu.add(afficherEquipe(equipe));
		menu.add(selectionnerPersonne(equipe, inscriptions));
		menu.add(changerNomEquipe(equipe));
		menu.add(supprimerEquipe(equipe));
		menu.addBack("q");
		return menu;
	}
	private Menu editerPersonnes(Personne personne)
	{
		Menu menu = new Menu("Editer " + personne.getNom()+" "+personne.getPrenom());
		menu.add(afficherPersonne(personne));
		menu.add(changerNomPersonne(personne));
		menu.add(changerPrenomPersonne(personne));
		menu.add(changerMailPersonne(personne));
		menu.add(supprimerPersonne(personne));
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

	private Menu gererCandidats(Competition competition)
	{
		Menu menu = new Menu("Gérer les candidats de " + competition.getNom(), "e");
		menu.add(afficherCandidats(competition));
//		menu.add(ajouterEmploye(competition));
//		menu.add(modifierEmploye(competition));
//		menu.add(supprimerCandidatCompet(competition));
		menu.addBack("q");
		return menu;
	}
	
	private Option gererDatecloture(final Competition competition)
	{        
		return new Option("Changer la date de cloture de la compétition", "c", 
				() -> {
					final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			        final String input = getString("Nouvelle date : ");
			        final LocalDate dateChanger = LocalDate.parse(input, DATE_FORMAT);
			        
					competition.setDateCloture(dateChanger);
					});
	}		
	
	private Option changerNom(final Competition competition)
	{
		return new Option("Renommer le nom de la compétition", "r", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}
	
	private Option changerNomEquipe(final Equipe equipe)
	{
		return new Option("Renommer le nom de l'équipe", "r", 
				() -> {equipe.setNom(getString("Nouveau nom : "));});
	}

	private Option changerNomPersonne(final Personne personne)
	{
		return new Option("Renommer le nom de la personne", "rn", 
				() -> {personne.setNom(getString("Nouveau nom : "));});
	}
	private Option changerPrenomPersonne(final Personne personne)
	{
		return new Option("Renommer le prenom de la personne", "rp", 
				() -> {personne.setPrenom(getString("Nouveau prenom : "));});
	}
	private Option changerMailPersonne(final Personne personne)
	{
		return new Option("Renommer le mail de la personne", "rm", 
				() -> {personne.setMail(getString("Nouveau mail : "));});
	}
	private List<Competition> selectionnercompetition(Inscriptions inscriptions)
	{
		return new List<Competition>("Sélectionner une competition", "e", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editercompetition(element)
				);
	}
	private List<Equipe> selectionnerEquipe(Inscriptions inscriptions)
	{
		return new List<Equipe>("Sélectionner une équipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> editerEquipes(element, inscriptions)
				);
	}
	
	private List<Personne> selectionnerPersonne(Inscriptions inscriptions)
	{
		return new List<Personne>("Sélectionner une personne", "e", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonnes(element)
				);
	}
	private Option supprimer(Competition competition)
	{
		return new Option("Supprimer", "d", () -> {competition.delete();});
	}
	
	private Option supprimerEquipe(Equipe equipe)
	{
		return new Option("Supprimer", "d", () -> {equipe.delete();});
	}
	private Option supprimerPersonne(Personne personne)
	{
		return new Option("Supprimer", "d", () -> {personne.delete();});
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
	
	private Option quitterEtEnregistrer(Inscriptions inscriptions)
	{
		return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						gestionPersonnel.sauvegarder();
						inscriptions.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (ImpossibleToSaveException | IOException e)
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
