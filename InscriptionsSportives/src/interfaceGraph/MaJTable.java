package interfaceGraph;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class MaJTable extends AbstractTableModel{
    private Object[][] data;
    private String[] title;
    
    public MaJTable(Object[][] data, String[] title){
      this.data = data;
      this.title = title;
    }

    //Retourne le nombre de colonnes
    public int getColumnCount() {
      return this.title.length;
    }

    //Retourne le nombre de lignes
    public int getRowCount() {
      return this.data.length;
    }
    
    //Retourne la valeur à l'emplacement spécifié
    public Object getValueAt(int row, int col) {
      return this.data[row][col];
    } 
    
    public String getColumnName(int col) {
    	  return this.title[col];
    }
    public Class getColumnClass(int col){
    	  //On retourne le type de la cellule à la colonne demandée + case cochée
    	  return this.data[0][col].getClass();
    }
    public boolean isCellEditable(int row, int col){
    	//Retourne vrai si la cellule est éditable : celle-ci sera donc éditable
    	if(getValueAt(0, col) instanceof JButton)
    		//On appelle la méthode getValueAt qui retourne la valeur d'une cellule
    		//Et on effectue un traitement spécifique si c'est un JButton
    	    return false;
    	  return true; 
    }
    
}