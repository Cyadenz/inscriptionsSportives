package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(800, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
		setVisible(true);
		
		tableau = new JTable(modele);
//		JTable tableau = new JTable(new ModeleStatiqueObjet(inscriptions));
		 
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);       
        JPanel boutons = new JPanel();
        
        boutons.add(new JButton(new AddAction()));
        boutons.add(new JButton(new RemoveAction()));
        
        getContentPane().add(boutons, BorderLayout.SOUTH);
		pack();
	}
	
    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter");
        }
        @Override
        public void actionPerformed(ActionEvent e) {
        	String date_s = " 2011-01-18 00:00:00.0"; 
            SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
            Date date;
			try {
				date = dt.parse(date_s);
				 modele.addCompetition("test04", date, true);
			} catch (ParseException e1) {
				System.out.println("AHhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
				e1.printStackTrace();
			}
        }
    }
 
    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimer");
        }
 
        public void actionPerformed(ActionEvent e) {
            int[] selection = tableau.getSelectedRows();
 
            for(int i = selection.length - 1; i >= 0; i--){
                modele.removeCompetition(selection[i]);
            }
        }
    }
}
