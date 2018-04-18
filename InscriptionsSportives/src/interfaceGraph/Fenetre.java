package interfaceGraph;

import javax.swing.*;
import java.util.ArrayList;

import hibernate.Passerelle;
import inscriptions.*;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;

public class Fenetre extends JFrame implements ActionListener {
	
	public JFrame frame = new JFrame();
	private Inscriptions inscriptions;
	
	public Fenetre(Inscriptions inscriptions) {		
		this.inscriptions = inscriptions;		
		frame.setTitle("Interface Graphique");
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(800, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        frame.getContentPane().add(createJPN(), BorderLayout.NORTH);        
        frame.getContentPane().add(getMainPanel(), BorderLayout.CENTER);   
        frame.getContentPane().add(createJPS(false, 0), BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.pack();
	}

	private JPanel getMainPanel() {
		JPanel panel = new JPanel();	
		panel.setBackground(Color.WHITE);
		ArrayList<JButton> jButtons = new ArrayList<>();
		
		jButtons.add(new JButton("Compétitions"));
		jButtons.add(new JButton("Equipes"));
		jButtons.add(new JButton("Candidats"));
		for (JButton jButton : jButtons) {
			panel.add(jButton);
			jButton.addActionListener(this);
		}
		return panel;
	}

	private JPanel PanelCompet() {
		JPanel panelC = new JPanel();
		panelC.setBackground(Color.WHITE);
		ArrayList<JButton> jButtons = new ArrayList<>();

		jButtons.add(new JButton(new AbstractAction("Afficher les compétitions") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(2);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Ajouter une compétition") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(3);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Sélectionner une compétition") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(4);
			}
		}));
		for (JButton jButton : jButtons) {
			panelC.add(jButton);
		}
		return panelC;
	}
	
	private JPanel AfficherCompet() {
		JPanel AfficherCompet = new JPanel();	

		ArrayList<Competition> competitions = new ArrayList<Competition>();
		competitions = (ArrayList) Passerelle.getData("Competition");
		
		Object data[][] = new Object[competitions.size()][6];
		for (int i = 0 ; i != competitions.size(); i++)			
			{
				data[i][0] = competitions.get(i).getNom();
				data[i][1] = competitions.get(i).getCandidats();
				data[i][2] = competitions.get(i).getDateCloture();
				if(competitions.get(i).estEnEquipe())
					data[i][3] = new Boolean(true);		
				else
					data[i][3] = new Boolean(false);
				
				if(competitions.get(i).inscriptionsOuvertes())
					data[i][4] = new Boolean(true);		
				else
					data[i][4] = new Boolean(false);
				data[i][5] = new JButton("Supprimer");
			}	
		String  title[] = {"Compétition", "Candidat(s)", "Date de cloture", "Réservé aux équipes ?", "Inscriptions ouvertes ?", "Supprimer"};
		MaJTable model = new MaJTable(data, title);
		JTable tableau = new JTable(model);	
		tableau.setDefaultRenderer(JButton.class, new TableComponent());

//		AfficherCompet.add(new JScrollPane(tableau));
		AfficherCompet.add(new JScrollPane(tableau), BorderLayout.CENTER);
		
		return AfficherCompet;
	}
	
	private JPanel AjoutCompet() {
		JPanel AjoutCompet = new JPanel();			
		ArrayList<JTextField> JTextFields = new ArrayList<>();	
		String[] labels = {"Nom : ", "Date (format : dd-MM-yyyy) : "};
		
		int numPairs = labels.length;
		for (int i = 0; i < numPairs; i++) {
		    JLabel l = new JLabel(labels[i], JLabel.TRAILING);
		    AjoutCompet.add(l);
		    JTextField textField = new JTextField(15);
		    JTextFields.add(textField);
		    l.setLabelFor(textField);
		    AjoutCompet.add(textField);
		}
		
		JLabel a = new JLabel("Type de compétition : ", JLabel.TRAILING);
		AjoutCompet.add(a);	    
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Equipe");
		combo.addItem("Individuelle");
		AjoutCompet.add(combo);
		JButton envoyer = new JButton(new AbstractAction("ajouter") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String getNom = JTextFields.get(0).getText();
				String getDate = JTextFields.get(1).getText();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				
				try {
					Date date = formatter.parse(getDate);
					Object getTypeCompet = combo.getSelectedItem(); 
					boolean enEquipe;		
					if(getTypeCompet.equals("Individuelle"))
						enEquipe = false;
					else
						enEquipe = true;			
					System.out.println(getNom + getDate + getTypeCompet);
					inscriptions.createCompetition(getNom, date , enEquipe);
					inscriptions.createCompetition("test1", date , false);
					inscriptions.createCompetition("test2", date , true);
					inscriptions.createCompetition("test3", date , true);
					
				} catch (ParseException e1) {
					System.out.println("Erreur de saisie de la date, format correct : (dd-MM-yyyy). Code de l'erreur : "+e1+". Veuillez réessayer.");
				}				
				changePanel(1);
			}
		});			
		AjoutCompet.add(envoyer);		
		System.out.println(Passerelle.getData("Competition"));
		
		return AjoutCompet;
	}
	
	private JPanel SelectionCompet(){
		JPanel SelectionCompet = new JPanel();
		
		ArrayList<Competition> competitions = new ArrayList<Competition>();
		competitions = (ArrayList) Passerelle.getData("Competition");	
		JComboBox combo = new JComboBox();
		
		for (Competition compet : competitions)
		{
			combo.addItem(compet);
		}
		
		SelectionCompet.add(combo);
		
		JButton selectionner = new JButton(new AbstractAction("selectionner") {
			@Override
			public void actionPerformed(ActionEvent e) {
				Competition CompetSelectionne = (Competition) combo.getSelectedItem();
				System.out.println("Date cloture : "+CompetSelectionne.getDateCloture());
				changePanel(5, CompetSelectionne);
			}
		});		
		SelectionCompet.add(selectionner);
		return SelectionCompet;
	}
	
	private JPanel CompetSelectionne(Competition competition) {
		JPanel CompetSelectionne = new JPanel();
		ArrayList<JButton> jButtons = new ArrayList<>();

		jButtons.add(new JButton(new AbstractAction("Afficher la compétition") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(6, competition);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Renommer la compétition") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(3);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Changer la date de cloture") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(4);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Ajouter une équipe/personne") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(4);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Selectionner une équipe/personne à supprimer") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(4);
			}
		}));
		jButtons.add(new JButton(new AbstractAction("Supprimer la compétition") {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePanel(4);
			}
		}));
		for (JButton jButton : jButtons) {
			CompetSelectionne.add(jButton);
		}	
		return CompetSelectionne;
	}
	
	private JPanel AfficheCompet(Competition competition) {
		JPanel AfficheCompet = new JPanel();
		
		Object[][] data = {   
			      {competition.getNom(), competition.getCandidats(), competition.getDateCloture(), competition.inscriptionsOuvertes()} };
			    String  title[] = {"Compétition", "Candidats", "Date de cloture", "Inscriptions ouvertes ?"};
			    JTable tableau = new JTable(data, title);
			    AfficheCompet.add(new JScrollPane(tableau), BorderLayout.CENTER);  		
			    
		return AfficheCompet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText())
		{
		case "Compétitions":
			System.out.println("Compétitions");
			changePanel(1);
			break;
			
		case "Equipes":
			System.out.println("Equipes");
			break;
		default:
			System.out.println("default");
		}	
	}
	
	public void changePanel(int x, Competition competition)
	{
		frame.invalidate();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(createJPN(), BorderLayout.NORTH);
		switch (x)
		{
			case 0 : 
				frame.getContentPane().add(getMainPanel(), BorderLayout.CENTER); 
				frame.getContentPane().add(createJPS(false, 0), BorderLayout.SOUTH);
			break;
			case 1 : 
				frame.getContentPane().add(PanelCompet(), BorderLayout.CENTER);
				frame.getContentPane().add(createJPS(true, 0), BorderLayout.SOUTH);
			break;
			case 2 : 
				frame.getContentPane().add(AfficherCompet(), BorderLayout.CENTER);
				frame.getContentPane().add(createJPS(true, 1), BorderLayout.SOUTH);
			break;
			case 3 : 
				frame.getContentPane().add(AjoutCompet(), BorderLayout.CENTER);
				frame.getContentPane().add(createJPS(true, 1), BorderLayout.SOUTH);
			break;
			case 4 : 
				frame.getContentPane().add(SelectionCompet(), BorderLayout.CENTER);
				frame.getContentPane().add(createJPS(true, 1), BorderLayout.SOUTH);
			break;
			case 5 :
				frame.getContentPane().add(CompetSelectionne(competition), BorderLayout.CENTER);
				frame.getContentPane().add(createJPS(true, 4), BorderLayout.SOUTH);
			break;
			case 6 :
				frame.getContentPane().add(AfficheCompet(competition), BorderLayout.CENTER);
				frame.getContentPane().add(createJPS(true, 4), BorderLayout.SOUTH);
			break;
		}
		frame.validate();
	}
	
	public void changePanel(int x)
	{
		changePanel(x, null);
	}
	
	public JPanel createJPN()
	{
		JPanel panelN = new JPanel();
		
		JLabel jlabel = new JLabel("Application Festival de la M2L");
		jlabel.setFont(new Font("Verdana",1,20));
        panelN.setBackground(Color.BLACK);
        panelN.add(jlabel); 
        
        return panelN;
	}
	
	
	public JPanel createJPS(boolean main, int anterieur)
	{
		JPanel panelS = new JPanel();
        panelS.setBackground(Color.RED); 
        if(main)
        	{
        		JButton retour = new JButton(new AbstractAction("retour") 
        		{
					@Override
					public void actionPerformed(ActionEvent e) {
						changePanel(anterieur);			
					}
        			
	        	});
        		panelS.add(retour);
			}
        return panelS;
	}
}