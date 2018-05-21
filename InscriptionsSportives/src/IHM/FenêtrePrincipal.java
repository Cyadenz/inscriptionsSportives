package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import hibernate.*;
import inscriptions.*;

public class FenêtrePrincipal extends JFrame {

	/**
	 * Fenêtre principal de l'IHM
	 */
	private static final long serialVersionUID = 1L;

	public static JFrame frame = new JFrame();
	private JTable tableauC;
	private JTable tableauE;
	private JTable tableauP;

	Inscriptions inscriptions = Inscriptions.getInscriptions();

	private ModeleDynaObjetCompetition modeleC = new ModeleDynaObjetCompetition(inscriptions);
	private ModeleDynaObjetEquipe modeleE = new ModeleDynaObjetEquipe(inscriptions);
	private ModeleDynaObjetCandidat modeleP = new ModeleDynaObjetCandidat(inscriptions);

//	public static List<Equipe> eqs = new ArrayList<Equipe>();
	public static List<Personne> pers = new ArrayList<Personne>();

	public FenêtrePrincipal() throws ParseException {
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
			myPicture = ImageIO.read(
					new File("C:\\Users\\Ugo\\git\\inscriptionsSportives\\InscriptionsSportives\\src\\logoM2L.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			panel.add(picLabel, gbc);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Problème avec le logo de la M2L\n Code de l'erreur :" + e
							+ "\n Veuillez vérifier le chemin \\inscriptionsSportives\\InscriptionsSporftives\\src\\logoM2L.png \n La page d'accueil va se charger sans le logo."							
					,"Problème d'image", JOptionPane.ERROR_MESSAGE);
		}

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel buttons = new JPanel(new GridBagLayout());
		buttons.setBackground(Color.WHITE);
		buttons.add(new JButton(new ButtonCompet("Compétitions")), gbc);
		buttons.add(new JButton(new ButtonEquipe("Equipes")), gbc);
		buttons.add(new JButton(new ButtonPersonne("Candidats")), gbc);
		buttons.add(new JButton(new ButtonContact("Contact")), gbc);

		JPanel quitter = new JPanel();
		quitter.setBorder(new EmptyBorder(8, 0, 0, 0));
		quitter.setLayout(new GridLayout(1, 1));
		quitter.setBackground(Color.WHITE);
		quitter.add((new JButton(new ButtonQuitter())));

		buttons.add(quitter, gbc);

		gbc.weighty = 1;
		panel.add(buttons, gbc);

		return panel;
	}

	private void AddTabCompet() {
		tableauC = new JTable(modeleC);
		tableauC.setDefaultEditor(Boolean.class, new BooleanCellEditor());

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauC.getModel());
		// sorter.setComparator(1, new DateComparator());
		sorter.setSortable(1, false);
		tableauC.setRowSorter(sorter);
		getContentPane().add(new JScrollPane(tableauC), BorderLayout.CENTER);

		JPanel boutons = new JPanel();
		boutons.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 2));
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2));
		JPanel p3 = new JPanel();

		p1.add(new JButton(new AddActionC()));
		p1.add(new JButton(new RemoveActionC()));
		p2.add(new JButton(new AddEquipePFromC()));
		p2.add(new JButton(new RemoveEquipePFromC()));
		p3.add(new JButton(new Retour("retour")), gbc);

		boutons.add(p1, gbc);
		boutons.add(p2, gbc);
		boutons.add(p3, gbc);

		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	private void AddTabEquipe() {

		tableauE = new JTable(modeleE);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauE.getModel());
		tableauE.setRowSorter(sorter);

		getContentPane().add(new JScrollPane(tableauE), BorderLayout.CENTER);

		JPanel boutons = new JPanel();
		boutons.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 2));
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2));
		JPanel p3 = new JPanel();

		p1.add(new JButton(new AddActionE()));
		p1.add(new JButton(new RemoveActionE()));
		p2.add(new JButton(new AddPersonneFromE()));
		p2.add(new JButton(new RemovePersonneFromE()));
		p3.add(new JButton(new Retour("retour")), gbc);

		boutons.add(p1, gbc);
		boutons.add(p2, gbc);
		boutons.add(p3, gbc);

		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	private void AddTabPersonne() {
		tableauP = new JTable(modeleP);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauP.getModel());
		tableauP.setRowSorter(sorter);
		getContentPane().add(new JScrollPane(tableauP), BorderLayout.CENTER);

		JPanel boutons = new JPanel();
		boutons.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 2));
		JPanel p3 = new JPanel();

		p1.add(new JButton(new AddActionP()), gbc);
		p1.add(new JButton(new RemoveActionP()), gbc);
		p3.add(new JButton(new Retour("Retour")), gbc);

		boutons.add(p1, gbc);
		boutons.add(p3, gbc);

		getContentPane().add(boutons, BorderLayout.SOUTH);
	}

	private class AddActionC extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton Ajouter, permettant d'ajouter une
		 * compétition
		 */
		private static final long serialVersionUID = 1L;

		private AddActionC() {
			super("Créer une compétition");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel jop = new JPanel();

			jop.setBorder(new EmptyBorder(10, 10, 10, 10));
			jop.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTH;

			JLabel label = new JLabel();
			label.setText("<html><h3><i>Ajout d'une compétition<br /></i></h3></html>");
			jop.add(label, gbc);

			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			JPanel TextField = new JPanel(new GridBagLayout());

			JTextField NomField = new JTextField(30);
			JTextField DateField = new JTextField(30);

			TextField.add(new JLabel("Nom de la compétition :"), gbc);
			TextField.add(NomField, gbc);

			TextField.add(new JLabel("Date de la compétition (format dd-MM-yyyy HH:mm)"), gbc);
			TextField.add(DateField, gbc);

			TextField.add(new JLabel("Compétition réservé aux équipes ?"), gbc);
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("Oui");
			combo.addItem("Non");
			TextField.add(combo, gbc);

			gbc.weighty = 1;

			jop.add(TextField, gbc);

			int result = JOptionPane.showConfirmDialog(null, jop, "Veuillez entrer les données de la compétition.",
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				Object getTypeCompet = combo.getSelectedItem();
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

				if (!NomField.getText().isEmpty())
					try {
						Date date = formatter.parse(DateField.getText());
						boolean enEquipe;
						if (getTypeCompet.equals("Non"))
							enEquipe = false;
						else
							enEquipe = true;
						modeleC.addCompetition(NomField.getText(), date, enEquipe);
					} catch (ParseException e2) {
						JOptionPane.showMessageDialog(frame,
								"Veuillez saisir une date valide de format dd-MM-yyyy HH:mm, ex : 20-11-2019 12:30.",
								"Erreur de saisie d'une date.", JOptionPane.ERROR_MESSAGE);
					}
				else
					JOptionPane.showMessageDialog(frame, "Veuillez saisir un Nom de compétition valide.",
							"Erreur de saisie", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class AddActionE extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton Ajouter, permettant d'ajouter une
		 * équipe
		 */
		private static final long serialVersionUID = 1L;

		private AddActionE() {
			super("Ajouter");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel jop = new JPanel();

			jop.setBorder(new EmptyBorder(10, 10, 10, 10));
			jop.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTH;

			JLabel label = new JLabel();
			label.setText("<html><h3><i>Ajout d'une équipe<br /></i></h3></html>");
			jop.add(label, gbc);

			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			JPanel TextField = new JPanel(new GridBagLayout());

			JTextField NomField = new JTextField(30);

			TextField.add(new JLabel("Nom de l'équipe :"), gbc);
			TextField.add(NomField, gbc);

			gbc.weighty = 1;

			jop.add(TextField, gbc);

			int result = JOptionPane.showConfirmDialog(null, jop, "Veuillez entrer le nom de l'équipe à créer.",
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if (!NomField.getText().isEmpty())
					modeleE.addEquipe(NomField.getText());
				else
					JOptionPane.showMessageDialog(frame, "Veuillez saisir un Nom d'équipe valide.", "Erreur de saisie",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class AddActionP extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton Ajouter, permettant d'ajouter une
		 * personne
		 */
		private static final long serialVersionUID = 1L;

		private AddActionP() {
			super("Ajouter");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel jop = new JPanel();

			jop.setBorder(new EmptyBorder(10, 10, 10, 10));
			jop.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTH;

			JLabel label = new JLabel();
			label.setText("<html><h3><i>Ajout d'une personne<br /></i></h3></html>");
			jop.add(label, gbc);

			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			JPanel TextField = new JPanel(new GridBagLayout());

			JTextField NomField = new JTextField(30);
			JTextField PrenomField = new JTextField(30);
			JTextField MailField = new JTextField(30);

			TextField.add(new JLabel("Nom de la personne :"), gbc);
			TextField.add(NomField, gbc);

			TextField.add(new JLabel("Prénom de la personne :"), gbc);
			TextField.add(PrenomField, gbc);

			TextField.add(new JLabel("Adresse E-mail de la personne :"), gbc);
			TextField.add(MailField, gbc);

			gbc.weighty = 1;

			jop.add(TextField, gbc);

			int result = JOptionPane.showConfirmDialog(null, jop, "Veuillez saisir les informations de la personnes.",
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if (!NomField.getText().isEmpty() && !PrenomField.getText().isEmpty() && !MailField.getText().isEmpty())
					modeleP.addPersonne(NomField.getText(), PrenomField.getText(), MailField.getText());
				else
					JOptionPane.showMessageDialog(frame, "Veuillez remplir tout les champs.", "Erreur de saisie",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class RemoveActionC extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une compétition
		 */
		private static final long serialVersionUID = 1L;

		private RemoveActionC() {
			super("Supprimer une compétition");
		}

		public void actionPerformed(ActionEvent e) {
			JPanel PanelAlert = new JPanel();
			int[] selection = tableauC.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			if (modelIndexes.length != 0 && selection.length != 0) {
				int result = JOptionPane.showConfirmDialog(PanelAlert, "Êtes-vous sur de votre choix ?",
						"Message de confirmation", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					for (int i = 0; i < selection.length; i++) {
						modelIndexes[i] = tableauC.getRowSorter().convertRowIndexToModel(selection[i]);
					}
					Arrays.sort(modelIndexes);
					for (int i = modelIndexes.length - 1; i >= 0; i--) {
						modeleC.removeCompetition(modelIndexes[i]);
					}
				}

			}
		}
	}

	private class AddPersonneFromE extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une équipe
		 */
		private static final long serialVersionUID = 1L;

		private AddPersonneFromE() {
			super("Inscrire une personne");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = tableauE.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			JPanel jop = new JPanel();
			JComboBox<Personne> combo = new JComboBox<Personne>();

			jop.setBorder(new EmptyBorder(10, 10, 10, 10));
			jop.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTH;

			JLabel label = new JLabel("<html><h3><i>Sélectionner la personne à inscrire.<br /></i></h3></html>");
			jop.add(label, gbc);

			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			JPanel center = new JPanel(new GridBagLayout());

			pers.clear();
			pers = (ArrayList) Passerelle.getData("Personne");
			
			for (Personne p : pers)
				combo.addItem(p);

			center.add(combo, gbc);

			gbc.weighty = 1;

			jop.add(center, gbc);

			if (modelIndexes.length != 0 && selection.length != 0) {

				int result = JOptionPane.showConfirmDialog(null, jop,
						"Selectionner la personne à inscrire dans l'équipe/les équipes.", JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION && combo.getItemCount() != 0) {
					for (int i = 0; i < selection.length; i++)
						modelIndexes[i] = tableauE.getRowSorter().convertRowIndexToModel(selection[i]);
					Arrays.sort(modelIndexes);
					for (int i = modelIndexes.length - 1; i >= 0; i--)
						modeleE.addPersonne(modelIndexes[i], ((Personne) combo.getSelectedItem()));
				}

			}
		}

	}

	private class AddEquipePFromC extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une équipe
		 */
		private static final long serialVersionUID = 1L;

		private AddEquipePFromC() {
			super("Inscrire une équipe/personne");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = tableauC.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			if (modelIndexes.length != 0 && selection.length != 0) {
				for (int i = 0; i < selection.length; i++)
					modelIndexes[i] = tableauC.getRowSorter().convertRowIndexToModel(selection[i]);
				Arrays.sort(modelIndexes);

				JPanel jop = new JPanel();

				JComboBox<Candidat> combo = new JComboBox<Candidat>();

				jop.setBorder(new EmptyBorder(10, 10, 10, 10));
				jop.setLayout(new GridBagLayout());

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.anchor = GridBagConstraints.NORTH;

				JLabel label = new JLabel(
						"<html><h3><i>Sélectionner l'équipe/la personnne à inscrire dans la/les compétition(s).<br /></i></h3></html>");
				jop.add(label, gbc);

				gbc.anchor = GridBagConstraints.CENTER;
				gbc.fill = GridBagConstraints.HORIZONTAL;

				JPanel center = new JPanel(new GridBagLayout());

				ArrayList<Candidat> candidats = new ArrayList<Candidat>();

				for (int i = modelIndexes.length - 1; i >= 0; i--) {
					if (modeleC.getCompet(modelIndexes[i]).estEnEquipe()) {
						ArrayList<Equipe> transitE = new ArrayList<Equipe>();
						transitE = (ArrayList) Passerelle.getData("Equipe");
						for (Equipe candE : transitE)
							combo.addItem((Candidat) candE);
					} else {
						ArrayList<Personne> transitP = new ArrayList<Personne>();
						transitP = (ArrayList) Passerelle.getData("Personne");
						for (Personne candP : transitP)
							combo.addItem((Candidat) candP);
					}
				}

				center.add(combo, gbc);
				gbc.weighty = 1;
				jop.add(center, gbc);

				int result = JOptionPane.showConfirmDialog(null, jop,
						"Selectionner l'équipe/la personnne à inscrire dans la/les compétition(s)",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION && combo.getItemCount() != 0) {
					for (int i3 = modelIndexes.length - 1; i3 >= 0; i3--)
						modeleC.addCandidat(modelIndexes[i3], (Candidat) combo.getSelectedItem());
				}
			}

		}

	}

	private class RemovePersonneFromE extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une équipe
		 */
		private static final long serialVersionUID = 1L;

		private RemovePersonneFromE() {
			super("Désinscrire un candidat");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = tableauE.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			JPanel jop = new JPanel();
			JComboBox<Personne> combo = new JComboBox<Personne>();

			if (modelIndexes.length != 0 && selection.length != 0) {

				jop.setBorder(new EmptyBorder(10, 10, 10, 10));
				jop.setLayout(new GridBagLayout());

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.anchor = GridBagConstraints.NORTH;

				JLabel label = new JLabel(
						"<html><h3><i>Sélectionner la personne à désinscrire de l'équipe/les équipes.<br /></i></h3></html>");
				jop.add(label, gbc);

				gbc.anchor = GridBagConstraints.CENTER;
				gbc.fill = GridBagConstraints.HORIZONTAL;

				for (int i = 0; i < selection.length; i++)
					modelIndexes[i] = tableauE.getRowSorter().convertRowIndexToModel(selection[i]);

				Arrays.sort(modelIndexes);

				JPanel center = new JPanel(new GridBagLayout());

				List<Personne> al = new ArrayList<>();
				Set<Personne> hs = new LinkedHashSet<>();

				for (int i = modelIndexes.length - 1; i >= 0; i--) {
					al.addAll(modeleE.getEq(modelIndexes[i]).getMembres());
				}

				hs.addAll(al);
				al.clear();
				al.addAll(hs);

				for (Personne p : al)
					combo.addItem(p);

				center.add(combo, gbc);
				gbc.weighty = 1;
				jop.add(center, gbc);

				int result = JOptionPane.showConfirmDialog(null, jop,
						"Selectionner la personne à désinscrire dans l'équipe/les équipes.",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.YES_OPTION && combo.getItemCount() != 0) {
					for (int i = modelIndexes.length - 1; i >= 0; i--)
						modeleE.RemovePersonne(modelIndexes[i], ((Personne) combo.getSelectedItem()));
				}
			}

		}
	}

	private class RemoveEquipePFromC extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une équipe
		 */
		private static final long serialVersionUID = 1L;

		private RemoveEquipePFromC() {
			super("Désinscrire une équipe/personne");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = tableauC.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			JPanel jop = new JPanel();

			if (modelIndexes.length != 0 && selection.length != 0) {

				jop.setBorder(new EmptyBorder(10, 10, 10, 10));
				jop.setLayout(new GridBagLayout());

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.anchor = GridBagConstraints.NORTH;

				JLabel label = new JLabel(
						"<html><h3><i>Sélectionner la personne à désinscrire de l'équipe/les équipes.<br /></i></h3></html>");
				jop.add(label, gbc);

				gbc.anchor = GridBagConstraints.CENTER;
				gbc.fill = GridBagConstraints.HORIZONTAL;

				for (int i = 0; i < selection.length; i++)
					modelIndexes[i] = tableauC.getRowSorter().convertRowIndexToModel(selection[i]);

				Arrays.sort(modelIndexes);

				JPanel center = new JPanel(new GridBagLayout());

				List<Candidat> al = new ArrayList<>();
				Set<Candidat> hs = new LinkedHashSet<>();

				JComboBox<Candidat> combo = new JComboBox<Candidat>();
				for (int i = modelIndexes.length - 1; i >= 0; i--) {
					al.addAll(modeleC.getCompet(modelIndexes[i]).getCandidats());
				}

				hs.addAll(al);
				al.clear();
				al.addAll(hs);

				for (Candidat p : al)
					combo.addItem(p);

				center.add(combo, gbc);
				gbc.weighty = 1;
				jop.add(center, gbc);

				int result = JOptionPane.showConfirmDialog(null, jop,
						"Selectionner la personne à désinscrire dans l'équipe/les équipes.",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.YES_OPTION && combo.getItemCount() != 0) {
					for (int i = modelIndexes.length - 1; i >= 0; i--)
						modeleC.RemoveCandidat(modelIndexes[i], ((Candidat) combo.getSelectedItem()));
				}
			}

		}
	}

	private class RemoveActionE extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une équipe
		 */
		private static final long serialVersionUID = 1L;

		private RemoveActionE() {
			super("Supprimer");
		}

		public void actionPerformed(ActionEvent e) {
			JPanel PanelAlert = new JPanel();
			int[] selection = tableauE.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			if (modelIndexes.length != 0 && selection.length != 0) {
				int result = JOptionPane.showConfirmDialog(PanelAlert, "Êtes-vous sur de votre choix ?",
						"Message de confirmation", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					for (int i = 0; i < selection.length; i++) {
						modelIndexes[i] = tableauE.getRowSorter().convertRowIndexToModel(selection[i]);
					}

					Arrays.sort(modelIndexes);

					for (int i = modelIndexes.length - 1; i >= 0; i--) {
						modeleE.removeEquipe(modelIndexes[i]);
					}
				}
			}
		}
	}

	private class RemoveActionP extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton supprimer, permettant de supprimer
		 * une personne
		 */
		private static final long serialVersionUID = 1L;

		private RemoveActionP() {
			super("Supprimer");
		}

		public void actionPerformed(ActionEvent e) {
			JPanel PanelAlert = new JPanel();
			int[] selection = tableauP.getSelectedRows();
			int[] modelIndexes = new int[selection.length];

			if (modelIndexes.length != 0 && selection.length != 0) {
				int result = JOptionPane.showConfirmDialog(PanelAlert, "Êtes-vous sur de votre choix ?",
						"Message de confirmation", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					for (int i = 0; i < selection.length; i++) {
						modelIndexes[i] = tableauP.getRowSorter().convertRowIndexToModel(selection[i]);
					}
					Arrays.sort(modelIndexes);
					for (int i = modelIndexes.length - 1; i >= 0; i--) {
						modeleP.removePersonne(modelIndexes[i]);
					}
				}
			}
		}
	}

	private class ButtonCompet extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton Compétition, permettant de lancer
		 * la JTable compétition
		 */
		private static final long serialVersionUID = 1L;

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
		/**
		 * AbstractAction répondant au bouton Equipe, permettant de lancer la
		 * JTable Equipe
		 */
		private static final long serialVersionUID = 1L;

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
		/**
		 * AbstractAction répondant au bouton Personne, permettant de lancer la
		 * JTable Personne
		 */
		private static final long serialVersionUID = 1L;

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

	private class ButtonContact extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton Personne, permettant de lancer la
		 * JTable Personne
		 */
		private static final long serialVersionUID = 1L;

		private ButtonContact(String Nom) {
			super(Nom);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getContentPane().removeAll();
			Contact();
			getContentPane().repaint();
			validate();
		}
	}

	private class Retour extends AbstractAction {
		/**
		 * AbstractAction répondant au bouton Retour, retourner au menu
		 * principal
		 */
		private static final long serialVersionUID = 1L;

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
		/**
		 * AbstractAction répondant au bouton Quitter, permettant de fermer
		 * l'application & la console
		 */
		private static final long serialVersionUID = 1L;

		private ButtonQuitter() {
			super("Quitter");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				inscriptions.sauvegarder();
			} catch (IOException e5) {
				System.out.println("Sauvegarde impossible." + e5);
			}

			dispose();
			System.exit(0);
		}
	}

	private void Contact() {
		pers.clear();
		pers = (ArrayList) Passerelle.getData("Personne");
		String[] mail = new String[pers.size()];
		int i = 0;

		for (Personne p : pers) {
			mail[i] = pers.get(i).getMail();
			i++;
		}

		JPanel Panel = new JPanel();
		Panel.setLayout(null);
		Color color = new Color(245, 245, 245);
		Panel.setBackground(color);

		JLabel Titre = new JLabel("Envoyer un Email");
		Titre.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel EmailLab = new JLabel("Email :");
		JLabel ObjLab = new JLabel("Objet :");
		JLabel MsgLab = new JLabel("Message :");

		JScrollPane scrollPane = new JScrollPane();
		JList<String> list = new JList<String>(mail);
		scrollPane.setViewportView(list);

		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);

		JTextField FieldObj = new JTextField();

		JScrollPane scrollPane2 = new JScrollPane();
		JTextPane FieldTxt = new JTextPane();
		scrollPane2.setViewportView(FieldTxt);

		Titre.setBounds(525, 120, 400, 30);
		EmailLab.setBounds(320, 173, 200, 30);
		ObjLab.setBounds(320, 253, 200, 30);
		MsgLab.setBounds(320, 293, 200, 30);
		scrollPane.setBounds(420, 180, 450, 70);
		FieldObj.setBounds(420, 260, 450, 30);
		scrollPane2.setBounds(420, 300, 450, 180);

		Panel.add(Titre);
		Panel.add(EmailLab);
		Panel.add(ObjLab);
		Panel.add(MsgLab);
		Panel.add(scrollPane);
		Panel.add(FieldObj);
		Panel.add(scrollPane2);

		JButton btn1 = new JButton("Envoyer");
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!FieldTxt.getText().isEmpty() && !FieldObj.getText().isEmpty()
						&& !list.getSelectedValuesList().isEmpty()) {
					Object[] mails = list.getSelectedValuesList().toArray();
					for (int i = 0; i != mails.length; i++)
						Mail.sendMail(mails[i].toString(), FieldObj.getText(), FieldTxt.getText());
					// System.out.println("mail: "+(mails[i].toString())+"
					// object : "+FieldObj.getText()+" Content :
					// "+FieldTxt.getText());
					FieldObj.setText("");
					FieldTxt.setText("");
					list.clearSelection();
					JOptionPane.showMessageDialog(frame, mails.length + " Email(s) envoyée(s) !");
				}
			}
		});

		JButton btn2 = new JButton(new Retour("Retour"));

		btn1.setBounds(500, 500, 100, 30);
		btn2.setBounds(620, 500, 100, 30);

		Panel.add(btn1);
		Panel.add(btn2);

		getContentPane().add(Panel);
	}
}
