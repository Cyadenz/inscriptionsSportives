package inscriptions;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import hibernate.Passerelle;

/**
 * Représente une Equipe. C'est-à-dire un ensemble de personnes pouvant 
 * s'inscrire à une compétition.
 * 
 */
@Entity
public class Equipe extends Candidat
{	
	private static final long serialVersionUID = 4147819927233466035L;
	
	@ManyToOne
	@Cascade(value = { CascadeType.SAVE_UPDATE})
	private Personne personne;
	
	@ManyToOne
	@Cascade(value = { CascadeType.SAVE_UPDATE})
	private Equipe equipe;
	
	@OneToMany(mappedBy = "equipe")
	@Cascade(value = { CascadeType.ALL })
	@SortNatural
	private SortedSet<Personne> membres = new TreeSet<>();
	
	Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */
	
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	
	/**
	 * Ajoute une personne dans l'équipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre)
	{
		membre.add(this);
		Passerelle.save(membre);
		return membres.add(membre);
	}

	/**
	 * Supprime une personne de l'équipe. 
	 * @param membre
	 * @return
	 */
	
	public boolean remove(Personne membre)
	{
		membre.remove(this);
		Passerelle.delete(membre);
		return membres.remove(membre);
	}

	@Override
	public void delete()
	{
		super.delete();
	}
	
	@Override
	public String toString()
	{
		return "Equipe " + super.toString();
	}
}
