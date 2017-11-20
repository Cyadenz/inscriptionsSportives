package JUnit;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class TestJUnit{

	Inscriptions inscriptions = Inscriptions.getInscriptions();
	Competition flechettes = inscriptions.createCompetition("Mondial de fléchettes", null, false);
	Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");
	Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
	Personne Charles = inscriptions.createPersonne("Charles", "slt", "zsxaqw");
	Personne Soso = inscriptions.createPersonne("Soso", "sltt", "awdzzx");
	@Test
	public final void testDelete() 
	{
		
		Equipe test = inscriptions.createEquipe("test");
		Set<Equipe> abc = new TreeSet<Equipe>();
		abc.add(test);
		test.add(Charles);
		test.add(Soso);
		Charles.delete();
		
		assertEquals(abc, Soso.getEquipes());
	}

	@Test
	public final void testToString() {
//		String contenue = "Equipe [] Test -> inscrit à []";
//		Equipe Test = inscriptions.createEquipe("Test");
//		System.out.println(Test.toString());
//		assertEquals(Test.toString(), contenue);	
	}

	@Test
	public final void testGetPersonne() {
		
		String nom = tony.getNom();
		String prenom = tony.getPrenom();
		String mail = tony.getMail();

		assertEquals("Getnom" + nom + " , " + tony.getNom(), tony.getNom(), nom);
		assertEquals("Getnom" + prenom + " , " + tony.getPrenom(), tony.getPrenom(), prenom);
		assertEquals("Getnom" + mail + " , " + tony.getMail(), tony.getMail(), mail);
		
	}

	@Test
	public final void testGetPrenom() {
		String PrenomTony = tony.getPrenom();
		
		assertEquals("Getprenom" + PrenomTony + " , " + tony.getPrenom(), tony.getPrenom(), PrenomTony);
	}

	@Test
	public final void testSetPrenom() {
		String prenom = "Dent d'or";
		
		Personne tony = inscriptions.createPersonne("tony", prenom, "azerty");
		assertEquals("Set : " + prenom + " , " + tony.getPrenom(), tony.getPrenom(), prenom);
	}

	@Test
	public final void testGetMail() {
		String mail = tony.getMail();

		assertEquals("Get : " + mail + " , " + tony.getMail(), tony.getMail(), mail);
	}

	@Test
	public final void testSetMail() {
		String mail = "1234";

		Personne tony = inscriptions.createPersonne("tony", "Dent de cuivre", mail);
		
	    assertEquals("SetMail : " + mail + " , " + tony.getMail(), tony.getMail(), mail);
	}

	@Test
	public final void testGetEquipes()
	{
	
		Equipe slt = inscriptions.createEquipe("slt");
		slt.add(tony);
		Set<Equipe> abc = new TreeSet<Equipe>();
		abc.add(slt);
		
		assertEquals(tony.getEquipes(), abc);
	}

	@Test
	public final void testAddEquipe() {
		Equipe test = inscriptions.createEquipe("test");
		Equipe slt = inscriptions.createEquipe("slt");
		Set<Equipe> abc = new TreeSet<Equipe>();
		abc.add(test);
		abc.add(slt);
		
		assertTrue ("Abc contient test : "+abc.contains(test), abc.contains(test));
		assertTrue ("Abc contient slt : "+abc.contains(slt), abc.contains(slt));
	}

	@Test
	public final void testRemoveEquipe() 
	{
		Equipe slt = inscriptions.createEquipe("slt");
		Equipe salut = inscriptions.createEquipe("salut");
		Set<Equipe> abc = new TreeSet<Equipe>();
		abc.add(slt);
		abc.add(salut);
		
		abc.remove(slt);
		inscriptions.remove(slt);
		assertFalse("Abc contient slt : "+abc.contains(slt), abc.contains(slt));
	}

}
