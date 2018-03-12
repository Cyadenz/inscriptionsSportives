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
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.*;

public class MenuUtil {
	
	private Inscriptions inscriptions;
	final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public MenuUtil(Inscriptions inscriptions)
	{
		this.inscriptions = inscriptions;
	}	
	public void start()
	{
		menuPrincipal().start();
	}	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel");
		menu.add(competition());
		menu.add(equipe());
		menu.add(personne());
		menu.add(menuQuitter());
		return menu;
	}	
	private Menu competition()
	{
		Menu menu = new Menu("Gérer les compétitions", "l");
		menu.add(afficherLesCompetitions());
		menu.add(creerCompetitionEquipe());
		menu.add(creerCompetitionPersonne());
		menu.add(selectionnerCompetition());
		menu.addBack("q");
		return menu;
	}
	private Option afficherLesCompetitions()
	{
		return new Option("Afficher les competitions", "l", () -> {System.out.println(inscriptions.getCompetitions());});
	}
	private Option creerCompetitionEquipe()
	{
		return new Option("Ajouter une competition d'équipes", "a", () -> 
		{
			try
			{
				String dateCloture = InOut.getString("Entrer la date de clôture des inscriptions de la compétition ( format : jj-mm-aaaa ) : ");
	            final LocalDate localDate = LocalDate.parse(dateCloture, DATE_FORMAT);
				inscriptions.createCompetition((getString("nom : ")), localDate, true);
			}
			catch(java.time.format.DateTimeParseException e)
			{
				System.out.println("Vous avez une erreur de saisie dans votre date, code de l'erreur : "+e);
			}
		} );	
	}
	private Option creerCompetitionPersonne()
	{
		return new Option("Ajouter une competition de personnes", "b", () -> 
		{	
			try
			{
				String dateCloture = InOut.getString("Entrer la date de clôture des inscriptions de la compétition ( format : jj-mm-aaaa ) : ");
				final LocalDate localDate = LocalDate.parse(dateCloture, DATE_FORMAT);
				inscriptions.createCompetition((getString("nom : ")), localDate, false);
			}
			catch(java.time.format.DateTimeParseException e)
			{
				System.out.println("Vous avez une erreur de saisie dans votre date, code de l'erreur : "+e);
			}
		} );	
	}
	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Sélectionner une competition", "e", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editerCompetition(element)
				);
	}
	private Menu editerCompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficherUneCompetition(competition));
		menu.add(changerNomCompetition(competition));
		menu.add(gererDateCloture(competition));
		if(!competition.estEnEquipe())
		{
			menu.add(ajouterPersonneCompetition(competition));
			menu.add(supprimerPersonneCompetition(competition));
		}
		if(competition.estEnEquipe())
		{
			menu.add(ajouterEquipeCompetition(competition));
			menu.add(supprimerEquipeCompetition(competition));
		}
		menu.add(supprimerCompetition(competition));
		menu.addBack("q");
		return menu;
	}
	private Option afficherUneCompetition(final Competition competition)
	{
		return new Option("Afficher la competition", "l", 
				() -> 
				{
					System.out.println("Compétition : "+competition+", état des inscriptions : "+competition.inscriptionsOuvertes()+", Date de cloture des inscriptions : "
				+competition.getDateCloture()+", Reservé aux équipes ? "+competition.estEnEquipe()+"\nCandidats de cette compétition : "+competition.getCandidats());
				}
		);
	}
	private Option changerNomCompetition(final Competition competition)
	{
		return new Option("Renommer le nom de la compétition", "r", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}
	private Option gererDateCloture(final Competition competition)
	{        
		return new Option("Changer la date de cloture de la compétition ( format : jj-mm-aaaa )", "c", 
				() -> {
					try
					{
						String dateCloture = InOut.getString("Entrer la date de clôture : ");
			            final LocalDate localDate = LocalDate.parse(dateCloture, DATE_FORMAT);
				        
						competition.setDateCloture(localDate);
					}
					catch(java.time.format.DateTimeParseException e)
					{
						System.out.println("Vous avez une erreur de saisie dans votre date, code de l'erreur : "+e);
					}
					});
	}
	private Option ajouterPersonneCompetition(Competition competition)
	{
		return new List<Personne>("Sélectionner une personne à ajouter", "p", 
				() -> 
				new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {
					try {competition.add(element);}		
					catch (java.lang.RuntimeException e)
					{
						System.out.println("La date de clôture est antérieure à la date d'aujourd'hui code d'erreur :"+e);
					}
									}
				);	
	}
	private Option ajouterEquipeCompetition(Competition competition)
	{
		return new List<Equipe>("Sélectionner une équipe à ajouter", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {
				try{competition.add(element);}
				catch (java.lang.RuntimeException e)
				{
					System.out.println("La date de clôture est antérieure à la date d'aujourd'hui code d'erreur :"+e);
				}
									}
				);	
	}
	private Option supprimerPersonneCompetition(Competition competition)
	{
		return new List<Candidat>("Sélectionner une personne à supprimer de la compétition "+competition.getNom(), "x", 
				() -> new ArrayList<>(competition.getCandidats()),
				(index, element) -> {competition.remove(element);}
				);
	}
	private Option supprimerEquipeCompetition(Competition competition)
	{
		return new List<Candidat>("Sélectionner une équipe à supprimer de la compétition "+competition.getNom(), "s", 
				() -> new ArrayList<>(competition.getCandidats()),
				(index, element) -> {competition.remove(element);}
				);
	}
	private Option supprimerCompetition(Competition competition)
	{
		return new Option("Supprimer la compétition", "d", () -> {competition.delete();});
		//Exception java.util.ConcurrentModificationException ?
	}	
	private Menu equipe()
	{
		Menu menu = new Menu("Gérer les équipes", "e");
		menu.add(afficherEquipe());
		menu.add(creerEquipe());
		menu.add(selectionnerEquipe());
		menu.addBack("q");
		return menu;
	}
	private Option afficherEquipe()
	{
		return new Option("Afficher les équipes", "l", () -> {System.out.println(inscriptions.getEquipes());});
	}
	private Option creerEquipe()
	{
		return new Option("Créer une équipe", "a", () -> 
		{
			inscriptions.createEquipe(getString("nom : "));
		} );	
	}
	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Sélectionner une équipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> editerEquipes(element)
				);
	}
	private Menu editerEquipes(Equipe equipe)
	{
		Menu menu = new Menu("Editer " + equipe.getNom());
		menu.add(afficherEquipe(equipe));
		menu.add(selectionnerPersonneEquipe(equipe));
		menu.add(selectionnerPersonneAjoutEquipe(equipe));
		menu.add(selectionnerPersonneSupprimerEquipe(equipe));
		menu.add(changerNomEquipe(equipe));
		menu.add(supprimerEquipe(equipe));
		menu.addBack("q");
		return menu;
	}
	private Option afficherEquipe(final Equipe equipe)
	{
		return new Option("Afficher l'équipe", "l", () -> {System.out.println(equipe+", membres : "+equipe.getMembres());});
	}
	private Option selectionnerPersonneEquipe(Equipe equipe)
	{
		return new List<Personne>("Sélectionner une personne de l'équipe à éditer", "e", 
				() -> new ArrayList<>(equipe.getMembres()),
				(element) -> editerPersonnes(element)
				);
	}
	private Option selectionnerPersonneAjoutEquipe(Equipe equipe)
	{
		return new List<Personne>("Sélectionner une personne à ajouter dans l'équipe", "a", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {equipe.add(element);}
                );
	}
	private Option selectionnerPersonneSupprimerEquipe(Equipe equipe)
	{
		return new List<Personne>("Sélectionner une personne à supprimer de l'équipe "+equipe.getNom(), "n", 
				() -> new ArrayList<>(equipe.getMembres()),
				(index, element) -> {equipe.remove(element);}
                );
	}
	private Option changerNomEquipe(final Equipe equipe)
	{
		return new Option("Renommer le nom de l'équipe", "r", 
				() -> {equipe.setNom(getString("Nouveau nom : "));});
	}
	private Option supprimerEquipe(Equipe equipe)
	{
		return new Option("Supprimer", "d", () -> {equipe.delete();});
	}	
	
	private Menu personne()
	{
		Menu menu = new Menu("Gérer les personnes", "a");
		menu.add(afficherPersonnes());
		menu.add(creerPersonne());
		menu.add(selectionnerPersonne());
		menu.addBack("q");
		return menu;
	}	
	private Option afficherPersonnes()
	{
		return new Option("Afficher les personnes", "l", () -> {System.out.println(inscriptions.getPersonnes());});
	}
	private Option creerPersonne()
	{
		return new Option("Ajouter une personne", "a",
				() -> 
				{
					inscriptions.createPersonne(getString("nom : "), 
						getString("prenom : "), getString("mail : "));
				}	
		);
	}
	private List<Personne> selectionnerPersonne()
	{
		return new List<Personne>("Sélectionner une personne", "e", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonnes(element)
				);
	}
	private Menu editerPersonnes(Personne personne)
	{
		Menu menu = new Menu("Editer " + personne.getNom()+" "+personne.getPrenom());
		menu.add(afficherPersonneInscrites(personne));
		menu.add(changerNomPersonne(personne));
		menu.add(changerPrenomPersonne(personne));
		menu.add(changerMailPersonne(personne));
		menu.add(supprimerPersonne(personne));
		menu.addBack("q");
		return menu;
	}
	private Option afficherPersonneInscrites(Personne personne)
	{
		return new Option("Afficher les informations", "l", () -> {System.out.println("Nom : "+personne.getNom()+"\nPrenom : "+personne.getPrenom()+"\nMail : "+personne.getMail());});
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
	private Option supprimerPersonne(Personne personne)
	{
		return new Option("Supprimer", "d", () -> {personne.delete();});
	}
	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						inscriptions.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (IOException e)
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

	public static void main(String[] args)
	{		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		MenuUtil personnelConsole = new MenuUtil(inscriptions);
			personnelConsole.start();
		
	}
}
