package IHM;

import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import IHM.Fenetre2.Eq;
import java.util.List;
import hibernate.Passerelle;
import inscriptions.Personne;
import inscriptions.Inscriptions;

public class ModeleDynaObjetCandidat extends AbstractTableModel {
	private final List<Personne> personnes = new ArrayList<Personne>();
    private final String[] entetes = {"Nom", "Prénom", "Mail", "Equipe", "Compétition"};
    
    private Inscriptions inscriptions;
    
    public ModeleDynaObjetCandidat(Inscriptions inscriptions) throws ParseException { 
    	super();  	
    	this.inscriptions = inscriptions;
        ArrayList<Personne> pers = new ArrayList<Personne>();
		pers = (ArrayList) Passerelle.getData("Personne");
		
		personnes.addAll(pers);          
    }
 
    public int getRowCount() {
        return personnes.size();
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
                return personnes.get(rowIndex).getNom();
            case 1:
                return personnes.get(rowIndex).getPrenom();
            case 2:
                return personnes.get(rowIndex).getMail();
            case 3:
            	return personnes.get(rowIndex).getEquipes();
            case 4:
            	return personnes.get(rowIndex).getCompetitions();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(aValue != null){
            Personne personne = personnes.get(rowIndex);
            switch(columnIndex){
                case 0:
                    personne.setNom((String)aValue);
                break;
                case 1:
                	personne.setPrenom((String)aValue);
                break;
                case 2:
                	personne.setMail((String)aValue);
                break;
            }
        }
    }    
    public void addPersonne(String nom, String prenom, String mail) {
    	
    	inscriptions.createPersonne(nom, prenom, mail);
    	
    	ArrayList<Personne> pers = new ArrayList<Personne>();
    	pers = (ArrayList) Passerelle.getData("Personne");
		
		System.out.println(pers.size());
    	personnes.add(pers.get(pers.size()-1));
 
        fireTableRowsInserted(personnes.size() -1, personnes.size() -1);
    }
 
    public void removePersonne(int rowIndex) {
    	System.out.println(personnes.get(rowIndex));
    	inscriptions.remove(personnes.get(rowIndex));
    	
    	personnes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
        switch(columnIndex){
            default:
                return Object.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(columnIndex == 3 || columnIndex == 4)
    		return false;
        return true; //Editable
    }       
}