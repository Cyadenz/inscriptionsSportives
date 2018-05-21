package IHM;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import hibernate.Passerelle;

public class Methodes extends FenêtrePrincipal{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Methodes() throws ParseException {
		super();
	}

	public static void Lancement() throws ParseException
	{
    	// Mot de passe : Bonjour
		Boolean x = true;
		
		JPanel DemandeMdp = new JPanel(new GridBagLayout());
		DemandeMdp.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		try {
			BufferedImage myPicture;
			myPicture = ImageIO.read(
					new File("C:\\Users\\Ugo\\git\\inscriptionsSportives\\InscriptionsSportives\\src\\cadena.png"));
			
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			DemandeMdp.add(picLabel, gbc);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Problème avec le logo de la M2L\n Code de l'erreur :" + e
							+ "\n Veuillez vérifier le chemin \\inscriptionsSportives\\InscriptionsSportives\\src\\cadena.png \n La page de connexion va se charger sans le logo."							
					,"Problème d'image", JOptionPane.ERROR_MESSAGE);
		}
		JPasswordField mdp = new JPasswordField(15);

		DemandeMdp.add(new JLabel("Mot de passe : "), gbc);
		DemandeMdp.add(mdp, gbc);
		
		do{
			int result = JOptionPane.showConfirmDialog(null, DemandeMdp,
					"Veuillez saisir le mot de passe de l'application pour vous connecter",
					JOptionPane.OK_CANCEL_OPTION);
			
			if (result == JOptionPane.OK_OPTION) {
				if((String.valueOf(mdp.getPassword())).equals(Fonctions.decrypt("r_^Z_EB")))
				{
					x = false;
					try {
						Passerelle.open();
						new FenêtrePrincipal();
									
					} catch (java.lang.RuntimeException e) {
						System.out.println("Erreur de connexion à la base de données, \nCode de l'erreur : " + e);
					}
				}
				else
					JOptionPane.showMessageDialog(DemandeMdp, "Veuillez saisir un Mot de passe valide.",
							"Erreur de saisie", JOptionPane.ERROR_MESSAGE);
			}
			else
				x = false;
		}while(x);
	}

}
