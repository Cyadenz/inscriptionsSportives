package inscriptions;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJUnit{

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Competition flechettes = inscriptions.createCompetition("Mondial de fléchettes", null, false);
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");
	Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
	@Test
	public final void testDelete() 
	{
		Equipe Test = inscriptions.createEquipe("Test");
		Test.delete();
	}

	@Test
	public final void testToString() {
		Equipe Test = inscriptions.createEquipe("Test");
		System.out.println("\n--"+Test.toString()+"--\n");
		if(Test.toString() == "Test -> inscrit à []");
	}

	@Test
	public final void testPersonne() {
		String nom = tony.getNom();
		String prenom = tony.getPrenom();
		String mail = tony.getMail();

		assertEquals(nom, "Tony");
		assertEquals(prenom, "Dent de plomb");
		assertEquals(mail, "azerty");

		System.out.println("Personne : " + nom + " , " + prenom + " , " + mail);
	}

	@Test
	public final void testGetPrenom() {
		String PrenomTony = tony.getPrenom();
		
		assertEquals(PrenomTony, "Dent de plomb");
		System.out.println("GetPrenom : " + PrenomTony + " , Dent de plomb");
	}

	@Test
	public final void testSetPrenom() {
		String prenom = "Dent d'or";
		
		Personne tony = inscriptions.createPersonne("tony", prenom, "azerty");
		assertEquals(tony.getPrenom(), prenom);
		System.out.println("Set : " + prenom + " , " + tony.getPrenom());
	}

	@Test
	public final void testGetMail() {
		String mail = tony.getMail();

		assertEquals(mail, "azerty");
		System.out.println("GetMail : " + mail + " , " + tony.getMail());
	}

	@Test
	public final void testSetMail() {
		String mail = "1234";

		Personne tony = inscriptions.createPersonne("tony", "Dent de cuivre", mail);
		assertEquals(tony.getMail(), mail);
	    System.out.println("SetMail : " + mail + " , " + tony.getMail());
	}

	@Test
	public final void testGetEquipes()
	{
		
	}

	@Test
	public final void testAddEquipe() {
		Equipe Test = inscriptions.createEquipe("Test");
		System.out.println("\n--"+Test.toString()+"--\n");
	}

	@Test
	public final void testRemoveEquipe() 
	{
		Equipe slt = inscriptions.createEquipe("slt");
		//remove(slt);
	}

}
