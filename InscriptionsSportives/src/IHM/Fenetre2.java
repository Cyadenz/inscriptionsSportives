package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
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
    
    Inscriptions inscriptions = Inscriptions.reinitialiser();
    private ModeleStatiqueObjet modele = new ModeleStatiqueObjet(inscriptions);
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
//		AddTabCompet();

		pack();
	}
	
	private void AddTabCompet() {		
		tableauC = new JTable(modele);
//		JTable tableauC = new JTable(new ModeleStatiqueObjet(inscriptions));
		tableauC.setDefaultEditor(Boolean.class, new BooleanCellEditor());	
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauC.getModel());  
		sorter.setComparator(1, new DateComparator());
		tableauC.setRowSorter(sorter);			
        getContentPane().add(new JScrollPane(tableauC), BorderLayout.CENTER);
        
        JPanel boutons = new JPanel();        
        boutons.add(new JButton(new AddAction()));
        boutons.add(new JButton(new RemoveAction()));
        boutons.add(new JButton(new Retour("retour")));
//        boutons.add(new JButton(new FilterAction()));
        getContentPane().add(boutons, BorderLayout.SOUTH);
}
	
	private JPanel getMainPanel() {
		JPanel panel = new JPanel();	
		panel.setBackground(Color.WHITE);
		ArrayList<JButton> jButtons = new ArrayList<>();
		
		jButtons.add(new JButton(new ButtonCompet("Compétitions")));
		jButtons.add(new JButton("Equipes"));
		jButtons.add(new JButton("Candidats"));
		for (JButton jButton : jButtons) {
			panel.add(jButton);
//			jButton.addActionListener(this);
		}
		return panel;
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
            int[] selection = tableauC.getSelectedRows();
            int[] modelIndexes = new int[selection.length];
     
            for(int i = 0; i < selection.length; i++){
                modelIndexes[i] = tableauC.getRowSorter().convertRowIndexToModel(selection[i]);
            }
     
            Arrays.sort(modelIndexes);
     
            for(int i = modelIndexes.length - 1; i >= 0; i--){
                modele.removeCompetition(modelIndexes[i]);
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
    
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText())
		{
		case "Compétitions":
			System.out.println("Compétitions");
//			changePanel(1);
			break;
			
		case "Equipes":
			System.out.println("Equipes");
			break;
		default:
			System.out.println("default");
		}	
	}
	
//
////	public void changePanel(int x, Competition competition)
//	{
//		frame.invalidate();
//		frame.getContentPane().removeAll();
//		frame.getContentPane().add(createJPN(), BorderLayout.NORTH);
//		switch (x)
//		{
//			case 0 : 
//				frame.getContentPane().add(getMainPanel(), BorderLayout.CENTER); 
//				frame.getContentPane().add(createJPS(false, 0), BorderLayout.SOUTH);
//			break;
//			case 1 : 
//				frame.getContentPane().add(PanelCompet(), BorderLayout.CENTER);
//				frame.getContentPane().add(createJPS(true, 0), BorderLayout.SOUTH);
//			break;
//		}
//		frame.validate();
//	}
//	
//	public void changePanel(int x)
//	{
//		changePanel(x, null);
//	}
//    private class FilterAction extends AbstractAction {
//        private FilterAction() {
//            super("Filtrer");
//        }
//     
//        public void actionPerformed(ActionEvent e) {
//        	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauC.getModel());   	
//            String regex = JOptionPane.showInputDialog("Regex de filtre : (Nom/Date)");
//     
//            sorter.setRowFilter(RowFilter.regexFilter(regex, 0, 1));
//            tableauC.setRowSorter(sorter);
//        }
//    }
}
