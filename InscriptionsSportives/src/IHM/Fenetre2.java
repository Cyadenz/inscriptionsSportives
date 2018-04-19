package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import hibernate.*;
import inscriptions.*;

public class Fenetre2 extends JFrame  {
	
	public JFrame frame = new JFrame();	
    private JTable tableauC;  
    private JTable tableauE;
    private JTable tableauCan;
    
    Inscriptions inscriptions = Inscriptions.getInscriptions();
    private ModeleDynaObjetCompetition modeleC = new ModeleDynaObjetCompetition(inscriptions);
    private ModeleDynaObjetEquipe modeleE = new ModeleDynaObjetEquipe(inscriptions);
    
	public Fenetre2() throws ParseException {	
		super();
		
		setTitle("Application de la M2L (Maison des ligues de Lorraine)");
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(800, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
		setVisible(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getMainPanel());

		pack();
	}
	
	private JPanel getMainPanel() {
		JPanel panel = new JPanel();	
//		panel.setBackground(Color.WHITE);
		ArrayList<JButton> jButtons = new ArrayList<>();
		
		jButtons.add(new JButton(new ButtonCompet("Compétitions")));
		jButtons.add(new JButton(new ButtonEquipe("Equipes")));
		jButtons.add(new JButton("Candidats"));
		for (JButton jButton : jButtons) {
			panel.add(jButton);
		}
		return panel;
	}
	
	private void AddTabCompet() {		
		tableauC = new JTable(modeleC);
//		JTable tableauC = new JTable(new ModeleStatiqueObjet(inscriptions));
		tableauC.setDefaultEditor(Boolean.class, new BooleanCellEditor());	
		tableauC.setDefaultEditor(Sport.class, new SportCellEditor());
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauC.getModel());  
		sorter.setComparator(1, new DateComparator());
		tableauC.setRowSorter(sorter);			
        getContentPane().add(new JScrollPane(tableauC), BorderLayout.CENTER);
        
        JPanel boutons = new JPanel();        
        boutons.add(new JButton(new AddActionC()));
        boutons.add(new JButton(new RemoveActionC()));
        boutons.add(new JButton(new Retour("retour")));
        getContentPane().add(boutons, BorderLayout.SOUTH);
	}
	
	private void AddTabEquipe() {		
		tableauE = new JTable(modeleE);;
		tableauE.setDefaultEditor(Boolean.class, new BooleanCellEditor());	
		tableauE.setDefaultEditor(Sport.class, new SportCellEditor());
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauE.getModel());  
		sorter.setComparator(1, new DateComparator());
		tableauE.setRowSorter(sorter);			
        getContentPane().add(new JScrollPane(tableauE), BorderLayout.CENTER);
        
        JPanel boutons = new JPanel();        
        boutons.add(new JButton(new AddActionE()));
        boutons.add(new JButton(new RemoveActionE()));
        boutons.add(new JButton(new Retour("retour")));
        getContentPane().add(boutons, BorderLayout.SOUTH);
	}
		
    private class AddActionC extends AbstractAction {
        private AddActionC() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Date date = new Date();
            modeleC.addCompetition("Default", date, true);
        }
    }
    
    private class AddActionE extends AbstractAction {
        private AddActionE() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
        	modeleE.addEquipe("Default");
        }
    }
 
    private class RemoveActionC extends AbstractAction {
        private RemoveActionC() {
            super("Supprimer");
        }
 
        public void actionPerformed(ActionEvent e) {
            int[] selection = tableauC.getSelectedRows();
            int[] modelIndexes = new int[selection.length];
     
            for(int i = 0; i < selection.length; i++){
                modelIndexes[i] = tableauC.getRowSorter().convertRowIndexToModel(selection[i]);
            }
     
            Arrays.sort(modelIndexes);
     
            for(int i = modelIndexes.length - 1; i >= 0; i--){
                modeleC.removeCompetition(modelIndexes[i]);
            }
        }
    }
    
    private class RemoveActionE extends AbstractAction {
        private RemoveActionE() {
            super("Supprimer");
        }
 
        public void actionPerformed(ActionEvent e) {
            int[] selection = tableauE.getSelectedRows();
            int[] modelIndexes = new int[selection.length];
     
            for(int i = 0; i < selection.length; i++){
                modelIndexes[i] = tableauE.getRowSorter().convertRowIndexToModel(selection[i]);
            }
     
            Arrays.sort(modelIndexes);
     
            for(int i = modelIndexes.length - 1; i >= 0; i--){
                modeleE.removeEquipe(modelIndexes[i]);
            }
        }
    }
    
    private class ButtonCompet extends AbstractAction {
        private ButtonCompet(String Nom) {
            super(Nom);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           getContentPane().removeAll();
           AddTabCompet();
           getContentPane().repaint();
           validate();
        }
    }
    
    private class ButtonEquipe extends AbstractAction {
        private ButtonEquipe(String Nom) {
            super(Nom);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           getContentPane().removeAll();
           AddTabEquipe();
           getContentPane().repaint();
           validate();
        }
    }
    
    private class Retour extends AbstractAction {
        private Retour(String Nom) {
            super(Nom);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           getContentPane().removeAll();
           getContentPane().add(getMainPanel());
           getContentPane().repaint();
           validate();
        }
    }  

    private class DateComparator implements Comparator<Date> {
    	@Override
        public int compare(Date c1, Date c2) {
    		return new Integer((c1).compareTo(c2));  		
    	}
    }
    
	public enum Sport {
	    TENNIS,
	    FOOTBALL,
	    NATATION,
	    RIEN;
	}
	
	public class SportCellEditor extends DefaultCellEditor {
	    public SportCellEditor() {
	        super(new JComboBox(Sport.values()));
	    }
	}
}
