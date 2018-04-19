package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import hibernate.Passerelle;
import inscriptions.Competition;
import inscriptions.Inscriptions;

public class Fenetre2 extends JFrame  {
	
	public JFrame frame = new JFrame();	
    private JTable tableau;
    
    Inscriptions inscriptions = Inscriptions.reinitialiser();
    private ModeleStatiqueObjet modele = new ModeleStatiqueObjet(inscriptions);
	public Fenetre2() throws ParseException {	
		super();
		
		setTitle("Interface Graphique");
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(800, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
		setVisible(true);
		
		tableau = new JTable(modele);
//		JTable tableau = new JTable(new ModeleStatiqueObjet(inscriptions));
		tableau.setDefaultEditor(Boolean.class, new BooleanCellEditor());	
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableau.getModel());  
		sorter.setComparator(1, new DateComparator());
		tableau.setRowSorter(sorter);
			
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);       
        JPanel boutons = new JPanel();
        
        boutons.add(new JButton(new AddAction()));
        boutons.add(new JButton(new RemoveAction()));
//        boutons.add(new JButton(new FilterAction()));
        
        getContentPane().add(boutons, BorderLayout.SOUTH);
		pack();
	}
	
    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Date date = new Date();
            modele.addCompetition("Default", date, true);
        }
    }
 
    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimer");
        }
 
        public void actionPerformed(ActionEvent e) {
            int[] selection = tableau.getSelectedRows();
            int[] modelIndexes = new int[selection.length];
     
            for(int i = 0; i < selection.length; i++){
                modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
            }
     
            Arrays.sort(modelIndexes);
     
            for(int i = modelIndexes.length - 1; i >= 0; i--){
                modele.removeCompetition(modelIndexes[i]);
            }
        }
    }
    
    public class DateComparator implements Comparator<Date> {
    	@Override
        public int compare(Date c1, Date c2) {
    		return new Integer((c1).compareTo(c2));  		
    	}
    }
//    private class FilterAction extends AbstractAction {
//        private FilterAction() {
//            super("Filtrer");
//        }
//     
//        public void actionPerformed(ActionEvent e) {
//        	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableau.getModel());   	
//            String regex = JOptionPane.showInputDialog("Regex de filtre : (Nom/Date)");
//     
//            sorter.setRowFilter(RowFilter.regexFilter(regex, 0, 1));
//            tableau.setRowSorter(sorter);
//        }
//    }
}
