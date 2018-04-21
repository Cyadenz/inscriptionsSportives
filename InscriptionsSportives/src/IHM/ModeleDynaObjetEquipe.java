package IHM;

import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import IHM.Fenetre2.Eq;
import java.util.List;
import hibernate.Passerelle;
import inscriptions.Equipe;
import inscriptions.Inscriptions;

public class ModeleDynaObjetEquipe extends AbstractTableModel {
	private final List<Equipe> equipes = new ArrayList<Equipe>();
    private final String[] entetes = {"Nom d'équipe", "Membres", "Inscrit à", "Ajouter un membre", "Supprimer un membre"};
    
    private Inscriptions inscriptions;
    public ModeleDynaObjetEquipe(Inscriptions inscriptions) throws ParseException { 
    	super();
    	this.inscriptions = inscriptions;
    	
//        String date_s = " 2011-01-18 00:00:00.0"; 
//        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
//        Date date = dt.parse(date_s);
    	
//		inscriptions.createEquipe("test1", date , false);
//		inscriptions.createEquipe("test2", date , true);
//		inscriptions.createEquipe("test3", date , true);
			
		
//        this.inscriptions = inscriptions;           
        ArrayList<Equipe> eqs = new ArrayList<Equipe>();
		eqs = (ArrayList) Passerelle.getData("Equipe");
		
		equipes.addAll(eqs);
		System.out.println(equipes.toString());
//        for (int i = 0 ; i != compets.size(); i++)			
//		{      	
//        	inscriptions.createEquipe(compets.get(i).getNom(), compets.get(i).getDateCloture(), compets.get(i).estEnEquipe());
//		}
//        String date_s = " 2011-01-18 00:00:00.0"; 
//        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
//        Date date = dt.parse(date_s);
        
//        inscriptions.createEquipe("tes1", date, true);
//        inscriptions.createEquipe("test2", date, false);
                


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
//                case 1:
//                	String str_date = (String)aValue;
//                	DateFormat formatter;
//                	Date date;
//                	formatter = new SimpleDateFormat("dd-MM-yyyy");
//                	try {
//                			date = formatter.parse(str_date);
//                			equipe.setDateCloture(date);
//                		} 
//                	catch (ParseException e) {
//                		System.out.println("Veuillez saisir une date valable de format dd-MM-yyyy. Code de l'erreur :"+e);
//                	}
//                break;
//                case 2:
//                	String enEquipe = "" + aValue; 
//                    equipe.setEnEquipe(Boolean.valueOf(enEquipe));
//                    break;
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
    public Class getColumnClass(int columnIndex){
        switch(columnIndex){
//            case 2:
//                return Boolean.class;
//            case 4:
//                return Boolean.class;
            case 3:
                return Eq.class;
            case 4:
                return Eq.class;
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