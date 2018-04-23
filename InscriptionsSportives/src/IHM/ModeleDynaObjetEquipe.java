package IHM;

import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import IHM.FenêtrePrincipal.Pers;
import java.util.List;
import hibernate.Passerelle;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class ModeleDynaObjetEquipe extends AbstractTableModel {
	/**
	 * Modèle dynamique de l'objet Equipe
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<Equipe> equipes = new ArrayList<Equipe>();
    private final String[] entetes = {"Nom d'équipe", "Membres", "Inscrit à", "Ajouter un membre", "Supprimer un membre"};
    public JFrame frame = new JFrame();
    
    private Inscriptions inscriptions;
    public ModeleDynaObjetEquipe(Inscriptions inscriptions) throws ParseException { 
    	super();
    	this.inscriptions = inscriptions;       
        ArrayList<Equipe> eqs = new ArrayList<Equipe>();
		eqs = (ArrayList) Passerelle.getData("Equipe");
		
		equipes.addAll(eqs);
		System.out.println(equipes.toString());
    }
 
    public int getRowCount() {
        return equipes.size();
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
                return equipes.get(rowIndex).getNom();
            case 1:
                return equipes.get(rowIndex).getMembres();
            case 2:
                return equipes.get(rowIndex).getCompetitions();
            case 3:
            	return "Default";
            case 4:
            	return "Default"; 
            default:
                return null; //Ne devrait jamais arriver
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(aValue != null){
            Equipe equipe = equipes.get(rowIndex);
            switch(columnIndex){
                case 0:
                    equipe.setNom((String)aValue);
                    break;
                case 3:
                	equipe.add((Personne)aValue);
	                System.out.println("Ajout de l'"+aValue);
                break;
                case 4:
                	System.out.println("Suppresion de l'"+aValue);
                	equipe.remove((Personne)aValue);
                break;
            }
        }
    }    
    public void addEquipe(String nom) {
    	
    	inscriptions.createEquipe(nom);
    	
    	ArrayList<Equipe> eqs = new ArrayList<Equipe>();
    	eqs = (ArrayList) Passerelle.getData("Equipe");
		
		System.out.println(eqs.size());
    	equipes.add(eqs.get(eqs.size()-1));
 
        fireTableRowsInserted(equipes.size() -1, equipes.size() -1);
    }
 
    public void removeEquipe(int rowIndex) {
    	System.out.println(equipes.get(rowIndex));
    	inscriptions.remove(equipes.get(rowIndex));
    	
    	equipes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
//            case 2:
//                return Boolean.class;
//            case 4:
//                return Boolean.class;
            case 3:
                return Pers.class;
            case 4:
                return Pers.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
//    	if(columnIndex == 3 ||columnIndex == 4)
//    		return false;
        return true; //Editable
    }       
}