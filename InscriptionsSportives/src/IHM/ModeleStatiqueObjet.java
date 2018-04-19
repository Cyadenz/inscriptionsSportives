package IHM;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

import hibernate.Passerelle;
import inscriptions.Competition;
import inscriptions.Inscriptions;

public class ModeleStatiqueObjet extends AbstractTableModel {
	private final List<Competition> competitions = new ArrayList<Competition>();
    private final String[] entetes = {"Nom", "Date cloture", "Réservé aux équipes ?", "Candidats", "Ouverte ?", "Ajouter une Equipes"};
    
    private Inscriptions inscriptions;
    public ModeleStatiqueObjet(Inscriptions inscriptions) throws ParseException { 
    	super();
    	this.inscriptions = inscriptions;
    	
//        String date_s = " 2011-01-18 00:00:00.0"; 
//        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
//        Date date = dt.parse(date_s);
    	
//		inscriptions.createCompetition("test1", date , false);
//		inscriptions.createCompetition("test2", date , true);
//		inscriptions.createCompetition("test3", date , true);
			
		
//        this.inscriptions = inscriptions;           
        ArrayList<Competition> compets = new ArrayList<Competition>();
		compets = (ArrayList) Passerelle.getData("Competition");
		
		competitions.addAll(compets);
		System.out.println(competitions.toString());
//        for (int i = 0 ; i != compets.size(); i++)			
//		{      	
//        	inscriptions.createCompetition(compets.get(i).getNom(), compets.get(i).getDateCloture(), compets.get(i).estEnEquipe());
//		}
//        String date_s = " 2011-01-18 00:00:00.0"; 
//        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
//        Date date = dt.parse(date_s);
        
//        inscriptions.createCompetition("tes1", date, true);
//        inscriptions.createCompetition("test2", date, false);
                


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
                return competitions.get(rowIndex).getDateCloture();
            case 2:
                return competitions.get(rowIndex).estEnEquipe();
            case 3:
                return competitions.get(rowIndex).getCandidats();
            case 4:
                return competitions.get(rowIndex).inscriptionsOuvertes();
            case 5:
                return new JComboBox(new String[]{"toto", "titi", "tata"});
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
                	formatter = new SimpleDateFormat("dd-MM-yyyy");
                	try {
                			date = formatter.parse(str_date);
                			competition.setDateCloture(date);
                		} 
                	catch (ParseException e) {
                		System.out.println("Veuillez saisir une date valable de format dd-MM-yyyy. Code de l'erreur :"+e);
                	}
                break;
                case 2:
                	String enEquipe = "" + aValue; 
                    competition.setEnEquipe(Boolean.valueOf(enEquipe));
                    break;
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
    public Class getColumnClass(int columnIndex){
        switch(columnIndex){
            case 2:
                return Boolean.class;
            case 4:
                return Boolean.class;
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