package IHM;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import IHM.FenêtrePrincipal.Eq;
import java.util.Date;
import java.util.List;
import hibernate.Passerelle;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;

public class ModeleDynaObjetCompetition extends AbstractTableModel {
	/**
	 * Modèle de l'objet compétition
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<Competition> competitions = new ArrayList<Competition>();
    private final String[] entetes = {"Nom", "Date cloture", "Réservé aux équipes ?", "Candidats", "Ouverte ?", "Ajouter une equipe", "Supprimer une équipe"};
    
    public JFrame frame = new JFrame();
    
    private Inscriptions inscriptions;
    public ModeleDynaObjetCompetition(Inscriptions inscriptions) throws ParseException { 
    	super();
    	this.inscriptions = inscriptions;
      
        ArrayList<Competition> compets = new ArrayList<Competition>();
		compets = (ArrayList) Passerelle.getData("Competition");
		
		competitions.addAll(compets);
		System.out.println(competitions.toString());
    }
 
    public int getRowCount() {
        return competitions.size();
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return competitions.get(rowIndex).getNom();
            case 1:
            	SimpleDateFormat formater = new SimpleDateFormat("' Le' dd MMMM yyyy 'à' HH 'h' mm");      	
                return formater.format(competitions.get(rowIndex).getDateCloture());
            case 2:
                return competitions.get(rowIndex).estEnEquipe();
            case 3:
                return competitions.get(rowIndex).getCandidats();
            case 4:
                return competitions.get(rowIndex).inscriptionsOuvertes();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(aValue != null){
            Competition competition = competitions.get(rowIndex);
            switch(columnIndex){
                case 0:
                    competition.setNom((String)aValue);
                break;
                case 1:
                	String str_date = (String)aValue;
                	DateFormat formatter;
                	Date date;
                	formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                	try {
                			date = formatter.parse(str_date);
                			competition.setDateCloture(date);
                		} 
                		catch(ParseException e)
                    	{
                    		JOptionPane.showMessageDialog(frame, "Veuillez saisir une date valide de format dd-MM-yyyy-HH-mm ex : 20-11-2019 12:30.", "Erreur de saisie d'une date.", JOptionPane.ERROR_MESSAGE);
                    	}
                break;
                case 2:
                	if(competition.getCandidats().isEmpty())
                	{
                		String enEquipe = "" + aValue; 
                		competition.setEnEquipe(Boolean.valueOf(enEquipe));
                    }
                	else
                		JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas changer le type de la compétition lorsque celle-ci contient déjà des équipes/personnes ! Veuillez d'abord supprimer les équipes/personnes ou la compétition.", "Erreur de changement de type de compétition.", JOptionPane.ERROR_MESSAGE);
                break;
                case 5:
                	try
                	{
	                	competition.add((Equipe)aValue);
	                	System.out.println("Ajout de l'"+aValue);
                	}
                    catch(java.lang.RuntimeException e3)
                	{
                		JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas ajouter d'équipes ou de personnes lorsque la date d'inscription de la compétition est close \nou que la compétition est réservée à un autre type de candidat !", "Erreur lors de l'ajout d'une équipe/personne.", JOptionPane.ERROR_MESSAGE);
                	}
                break;
                case 6:
                	System.out.println("Suppresion de l'"+aValue);
                	competition.remove((Equipe)aValue);
                break;
//                	
            }
        }
    }    
    public void addCompetition(String nom, Date date, Boolean EnEquipe) {
    	
	    	inscriptions.createCompetition(nom, date, EnEquipe);    	
	    	ArrayList<Competition> compets = new ArrayList<Competition>();
			compets = (ArrayList) Passerelle.getData("Competition");		
			System.out.println(compets.size());
	    	competitions.add(compets.get(compets.size()-1)); 
	        fireTableRowsInserted(competitions.size() -1, competitions.size() -1);
    }
 
    public void removeCompetition(int rowIndex) {
    	System.out.println(competitions.get(rowIndex));
    	inscriptions.remove(competitions.get(rowIndex));
    	
    	competitions.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 2:
                return Boolean.class;
            case 4:
                return Boolean.class;
            case 5:
                return Eq.class;
            case 6:
                return Eq.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(columnIndex == 3 ||columnIndex == 4)
    		return false;
        return true; //Editable
    }
    
    
}