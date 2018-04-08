package inscriptions;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import hibernate.Passerelle;

/**
 * ReprÃ©sente une compÃ©tition, c'est-Ã -dire un ensemble de candidats 
 * inscrits Ã  un Ã©vÃ©nement, les inscriptions sont closes Ã  la date dateCloture.
 *
 */
@Entity
public class Competition implements Comparable<Competition>, Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private static final long serialVersionUID = -2882150118573759729L;
	@Transient
	private Inscriptions inscriptions;
	private String nom;
	
	@ManyToOne
	@Cascade(value = { CascadeType.SAVE_UPDATE})
	private Candidat candidat;
	
	@OneToMany(mappedBy = "competition")
	@Cascade(value = { CascadeType.ALL })
	@SortNatural
	private Set<Candidat> candidats;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCloture;
	
	@Column(columnDefinition="tinyint(1) default 0")
	private boolean enEquipe = false;

	Competition(Inscriptions inscriptions, String nom, Date dateCloture, boolean enEquipe)
	{
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
	}
	
	/**
	 * Retourne le nom de la compÃ©tition.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Modifie le nom de la compÃ©tition.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom ;
		Passerelle.save(this);
	}
	
	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, 
	 * faux si les inscriptions sont closes.
	 * @return
	 */
	
	public boolean inscriptionsOuvertes()
	{	
		// TODO retourner vrai si et seulement si la date système est antérieure Ã  la date de cloture.
		Date DateSys = new Date();
		try {
			if (DateSys.after(getDateCloture()))
				return false;
		} catch (Exception e) {
			System.out.println("La date n'est pas donnée");
		}
		return true;
	}
	
	/**
	 * Retourne la date de cloture des inscriptions.
	 * @return
	 */
	
	public Date getDateCloture()
	{	
		return dateCloture;
	}
	
	/**
	 * Est vrai si et seulement si les inscriptions sont rÃ©servÃ©es aux Ã©quipes.
	 * @return
	 */
	
	public boolean estEnEquipe()
	{
		return enEquipe;
	}
	
	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la reculer 
	 * mais pas de l'avancer.
	 * @param dateCloture
	 */
	
	public void setDateCloture(Date dateCloture)
	{
		// TODO vÃ©rifier que l'on avance pas la date.
		if (dateCloture.after(this.dateCloture))
			System.out.println("Vous ne pouvez pas avancer la date !");
		else
			this.dateCloture = dateCloture;
		Passerelle.save(this);
	}
	
	/**
	 * Retourne l'ensemble des candidats inscrits.
	 * @return
	 */
	
	public Set<Candidat> getCandidats()
	{
		return Collections.unmodifiableSet(candidats);
	}
	
	/**
	 * Inscrit un candidat de type Personne Ã  la compétition. Provoque une
	 * exception si la compétition est réservée aux équipes ou que les 
	 * inscriptions sont closes.
	 * @param personne
	 * @return
	 */
	
	public boolean add(Personne personne)
	{
		// TODO vÃ©rifier que la date de cloture n'est pas passée
		boolean inscriptions = inscriptionsOuvertes();
		if (enEquipe)
			throw new RuntimeException();
		else if(!inscriptions)
			throw new RuntimeException();
		personne.add(this);
		Passerelle.save(personne);
		return candidats.add(personne);
	}

	/**
	 * Inscrit un candidat de type Equipe Ã  la compÃ©tition. Provoque une
	 * exception si la compÃ©tition est rÃ©servÃ©e aux personnes ou que 
	 * les inscriptions sont closes.
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe)
	{
		// TODO vÃ©rifier que la date de clÃ´ture n'est pas passÃ©e
		boolean inscriptions = inscriptionsOuvertes();
		if (!enEquipe)
			throw new RuntimeException();
		else if(!inscriptions)
			throw new RuntimeException();
		equipe.add(this);
		Passerelle.save(equipe);
		return candidats.add(equipe);
	}

	/**
	 * DÃ©sinscrit un candidat.
	 * @param candidat
	 * @return
	 */
	
	public boolean remove(Candidat candidat)
	{
		candidat.remove(this);
		Passerelle.delete(candidat);
		return candidats.remove(candidat);
	}
	
	/**
	 * Supprime la compÃ©tition de l'application.
	 */
	
	public void delete()
	{
		for (Candidat candidat : candidats)
			remove(candidat);
		inscriptions.remove(this);
		Passerelle.delete(inscriptions);
	}
	
	@Override
	public int compareTo(Competition o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return getNom();
	}
}
