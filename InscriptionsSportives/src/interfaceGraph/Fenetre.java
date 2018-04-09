package interfaceGraph;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class Fenetre extends JFrame implements ActionListener {
	
	JFrame frame = new JFrame();
	
	public Fenetre() {
		
		frame.setTitle("Interface Graphique");
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(new Dimension(1200, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(getMainPanel());
		frame.setVisible(true);
		frame.pack();
	}

	private JPanel getMainPanel() {
		JPanel panel = new JPanel();

		ArrayList<JButton> jButtons = new ArrayList<>();
		jButtons.add(new JButton("Compétitions"));
		jButtons.add(new JButton("Equipes"));
		jButtons.add(new JButton("Candidats"));
		for (JButton jButton : jButtons) {
			panel.add(jButton);
			jButton.setPreferredSize(new Dimension(200, 100));
			jButton.addActionListener(this);
		}
		return panel;
	}

	private JPanel PanelCompet() {
		JPanel panelC = new JPanel();
		
		ArrayList<JButton> jButtons = new ArrayList<>();
		
		jButtons.add(new JButton("Afficher les compétitions"));
		
		jButtons.add(new JButton("Ajouter une compétition d'équipes"));
		
		jButtons.add(new JButton("Ajouter une compétition de personnes"));

		jButtons.add(new JButton("Sélectionner une compétition"));

		JButton retour = new JButton(new AbstractAction("retour") {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.setContentPane(getMainPanel());
				frame.invalidate();
				frame.validate();
			}
		});

		for (JButton jButton : jButtons) {
			panelC.add(jButton);
			jButton.setPreferredSize(new Dimension(280, 100));
		}
		retour.setPreferredSize(new Dimension(120, 70));
		panelC.add(retour);

		return panelC;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText())
		{
		case "Compétitions":
			System.out.println("Compétitions");
			frame.getContentPane().removeAll();
			frame.setContentPane(PanelCompet());
			frame.invalidate();
			frame.validate();
			break;
			
		case "Equipes":
			System.out.println("Equipes");
			break;
		default:
			System.out.println("default");
		}
		
	}
	
	public static void main(String[] args) {
		new Fenetre();
	}

}