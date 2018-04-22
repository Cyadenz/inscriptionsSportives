package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import hibernate.*;
import inscriptions.*;

public class Fenetre2 extends JFrame  {
	
	public JFrame frame = new JFrame();	
    private JTable tableauC;  
    private JTable tableauE;
    private JTable tableauP;
    
    Inscriptions inscriptions = Inscriptions.getInscriptions();
    
    private ModeleDynaObjetCompetition modeleC = new ModeleDynaObjetCompetition(inscriptions);
    private ModeleDynaObjetEquipe modeleE = new ModeleDynaObjetEquipe(inscriptions);
    private ModeleDynaObjetCandidat modeleP = new ModeleDynaObjetCandidat(inscriptions); 
    
    public static List<Equipe> eqs = new ArrayList<Equipe>();
    public static List<Personne> pers = new ArrayList<Personne>();
    
	public Fenetre2() throws ParseException {	
		super();
		
		setTitle("Application de la M2L (Maison des ligues de Lorraine)");
		setAlwaysOnTop(false);
		setPreferredSize(new Dimension(1200, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getMainPanel());
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JPanel getMainPanel() {
		JPanel panel = new JPanel();
		
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.WHITE);		
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;
		
		JLabel label = new JLabel();	
		label.setText("<html><h1><i><font color='red'>Inscriptions sportives de la Maison des ligues de Lorraine<br /></font></i></h1></html>");
		
		panel.add(label, gbc);

		try {
			BufferedImage myPicture;
			myPicture = ImageIO.read(new File("C:\\Users\\Ugo\\git\\inscriptionsSportives\\InscriptionsSportives\\src\\logoM2L.png")); 
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			panel.add(picLabel, gbc);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Problème avec le logo de la M2L\n Code de l'erreur :"+e
					+"\n Veuillez vérifier le chemin \\inscriptionsSportives\\InscriptionsSporftives\\src\\logoM2L.png \n La page d'accueil va se charger sans le logo."
					+ "", "Problème d'image", JOptionPane.ERROR_MESSAGE);
		}
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel buttons = new JPanel(new GridBagLayout());
		
		buttons.add(new JButton(new ButtonCompet("Compétitions")), gbc);
		buttons.add(new JButton(new ButtonEquipe("Equipes")), gbc);
		buttons.add(new JButton(new ButtonPersonne("Candidats")), gbc);
		buttons.add(new JButton("Contact"), gbc);
		buttons.add(new JButton(new ButtonQuitter()), gbc);
		
		gbc.weighty = 1;
		panel.add(buttons, gbc);
		
		return panel;
	}
	
	private void AddTabCompet() {		
		tableauC = new JTable(modeleC);
//		JTable tableauC = new JTable(new ModeleStatiqueObjet(inscriptions));
		tableauC.setDefaultEditor(Boolean.class, new BooleanCellEditor());	
		tableauC.setDefaultEditor(Eq.class, new EqCellEditorAdd());
		
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
		tableauE = new JTable(modeleE);
		tableauE.setDefaultEditor(Pers.class, new PersCellEditorAdd());
		
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
	
	private void AddTabPersonne() {		
		tableauP = new JTable(modeleP);		
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauP.getModel()); 
		tableauP.setRowSorter(sorter);			
        getContentPane().add(new JScrollPane(tableauP), BorderLayout.CENTER);
        
        JPanel boutons = new JPanel();        
        boutons.add(new JButton(new AddActionP()));
        boutons.add(new JButton(new RemoveActionP()));
        boutons.add(new JButton(new Retour("retour")));
        getContentPane().add(boutons, BorderLayout.SOUTH);
	}
		
    private class AddActionC extends AbstractAction {
        private AddActionC() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
        		JPanel jop = new JPanel();       		
       
	    	    JTextField NomField = new JTextField(30);
	    	    JTextField DateField = new JTextField(30);
	    	    
	    	    jop.add(new JLabel("Nom de la compétition :"));
	    	    jop.add(NomField);
	    	    
	    	    jop.add(new JLabel("Date de la compétition :"));
	    	    jop.add(DateField);	  	    	    
	    	    
	    	    jop.add(new JLabel("Compétition réservé aux équipes ?"));
	    	    JComboBox<String> combo = new JComboBox<String>();
        		combo.addItem("Oui");
        		combo.addItem("Non");
        		jop.add(combo);	
        		
	    	    int result = JOptionPane.showConfirmDialog(null, jop, "Veuillez entrer les données de la compétition.", JOptionPane.OK_CANCEL_OPTION);	    	    		
	    	    if (result == JOptionPane.OK_OPTION) {	 	    	    	
	    			Object getTypeCompet = combo.getSelectedItem();  		
	    	    	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
	    	    	
	    	    	if(!NomField.getText().isEmpty())
		    	    	try {
		    	    		Date date = formatter.parse(DateField.getText());
		    	    		boolean enEquipe;		
							if(getTypeCompet.equals("Non"))
								enEquipe = false;
							else
								enEquipe = true;
		    	    		modeleC.addCompetition(NomField.getText(), date, enEquipe);
	            		} 
	            		catch(ParseException e2){
	                		JOptionPane.showMessageDialog(frame, "Veuillez saisir une date valide de format dd-MM-yyyy ou dd-MM-yyyy-hh-mm, ex : 20-11-2019 ou 20-11-2019-12-30.", "Erreur de saisie d'une date.", JOptionPane.ERROR_MESSAGE);
	                	}
	    	    	else
	    	    		JOptionPane.showMessageDialog(frame, "Veuillez saisir un Nom de compétition valide.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
	    	      }    	     			
    		}
        }
    
    private class AddActionE extends AbstractAction {
        private AddActionE() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
        	modeleE.addEquipe("Equipe");
        }
    }
    
    private class AddActionP extends AbstractAction {
        private AddActionP() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
        	modeleP.addPersonne("Personne", "Personne", "Personne@mail");
        }
    }
 
    private class RemoveActionC extends AbstractAction {
        private RemoveActionC() {
            super("Supprimer");
        }
 
        public void actionPerformed(ActionEvent e) {     
        	JPanel PanelAlert = new JPanel();
        	
        	int result = JOptionPane.showConfirmDialog(PanelAlert, "Êtes-vous sur de votre choix ?", "Message de confirmation", JOptionPane.YES_NO_OPTION);
        	if (result == JOptionPane.YES_OPTION) 
	        	{	
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
    }
    
    private class RemoveActionE extends AbstractAction {
        private RemoveActionE() {
            super("Supprimer");
        }
 
        public void actionPerformed(ActionEvent e) {
        	
        	JPanel PanelAlert = new JPanel();      	
        	int result = JOptionPane.showConfirmDialog(PanelAlert, "Êtes-vous sur de votre choix ?", "Message de confirmation", JOptionPane.YES_NO_OPTION);
        	
        	if (result == JOptionPane.YES_OPTION) 
        	{
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
    }
    
    private class RemoveActionP extends AbstractAction {
        private RemoveActionP() {
            super("Supprimer");
        }
        public void actionPerformed(ActionEvent e) {
        	JPanel PanelAlert = new JPanel();      	
        	int result = JOptionPane.showConfirmDialog(PanelAlert, "Êtes-vous sur de votre choix ?", "Message de confirmation", JOptionPane.YES_NO_OPTION);
        	
        	if (result == JOptionPane.YES_OPTION) 
        	{
	            int[] selection = tableauP.getSelectedRows();
	            int[] modelIndexes = new int[selection.length];
	     
	            for(int i = 0; i < selection.length; i++){
	                modelIndexes[i] = tableauP.getRowSorter().convertRowIndexToModel(selection[i]);
	            }   
	            Arrays.sort(modelIndexes);   
	            for(int i = modelIndexes.length - 1; i >= 0; i--){
	                modeleP.removePersonne(modelIndexes[i]);
	            }
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
    
    private class ButtonPersonne extends AbstractAction {
        private ButtonPersonne(String Nom) {
            super(Nom);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           getContentPane().removeAll();
           AddTabPersonne();
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
    
    private class ButtonQuitter extends AbstractAction {
        private ButtonQuitter() {
            super("Quitter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           dispose();
           System.exit(0);
        }
    }  

    private class DateComparator implements Comparator<Date> {
    	@Override
        public int compare(Date c1, Date c2) {
    		return new Integer((c1).compareTo(c2));  		
    	}
    }
    
	public enum Eq {
		
	}
	
	public enum Pers {
		
	}
	
	public static class EqCellEditorAdd extends DefaultCellEditor {
	    public EqCellEditorAdd() {
	        super(PopulateEqAdd());
	    }
	    
	    private static JComboBox PopulateEqAdd() {
	    	
	    	JComboBox<Equipe> combo = new JComboBox<Equipe>();
	    	eqs = (ArrayList) Passerelle.getData("Equipe");
			for (Equipe eq : eqs)
			{
				combo.addItem(eq);
			}
	        return combo;
	    }
	}
	
	public static class EqCellEditorSupp extends DefaultCellEditor {
	    public EqCellEditorSupp() {
	        super(PopulateEqSupp());
	    }
	    
	    private static JComboBox PopulateEqSupp() {	
	    	JComboBox<Equipe> combo = new JComboBox<Equipe>();
	    	eqs = (ArrayList) Passerelle.getData("Equipe");
			for (Equipe eq : eqs)
			{				
				combo.addItem(eq);
			}
	        return combo;
	    }
	}
	
	public static class PersCellEditorAdd extends DefaultCellEditor {
	    public PersCellEditorAdd() {
	        super(PopulatePersAdd());
	    }
	    
	    private static JComboBox PopulatePersAdd() {
	    	
	    	JComboBox<Personne> combo = new JComboBox<Personne>();
	    	pers = (ArrayList) Passerelle.getData("Personne");
			for (Personne p : pers)
			{
				combo.addItem(p);
			}
	        return combo;
	    }
	}
	
	public static class PersoCellEditorSupp extends DefaultCellEditor {
	    public PersoCellEditorSupp() {
	        super(PopulatePersSupp());
	    }
	    
	    private static JComboBox PopulatePersSupp() {	
	    	JComboBox<Personne> combo = new JComboBox<Personne>();
	    	pers = (ArrayList) Passerelle.getData("Personne");
			for (Personne p : pers)
			{				
				combo.addItem(p);
			}
	        return combo;
	    }
	}

}
