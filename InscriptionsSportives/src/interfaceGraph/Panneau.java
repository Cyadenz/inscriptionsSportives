package interfaceGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import hibernate.Passerelle;
import inscriptions.Competition;

public class Panneau {

    public Panneau() {

        JFrame frame = new JFrame();
        
        JPanel panelN = new JPanel();
        panelN.setBackground(Color.black);
        
        
		JPanel panelC = new JPanel();	
		panelC.setBackground(Color.BLUE);
		


		Object data[][] = new Object[2][4];;
		System.out.println("creation ok");
		for (int i = 0 ; i != 2; i++)			
			{
			System.out.println("boucle D "+i);
			data[i][0] = "ntm";
			data[i][1] = "ntm";
			data[i][2] = "ntm";
			data[i][3] = "ntm";		
			System.out.println("boucle F "+i);
			}	
		String  title[] = {"Compétition", "Candidats", "Date de cloture", "Inscriptions ouvertes ?"};
		JTable tableau = new JTable(data, title);
		
		panelC.add(new JScrollPane(tableau), BorderLayout.CENTER);
		
		
        
        JPanel panelS = new JPanel();
        panelS.setBackground(Color.RED);
        
        JLabel jlabel = new JLabel("Application Festival de la M2L");
        jlabel.setFont(new Font("Verdana",1,20));
        panelN.add(jlabel);
        JButton button = new JButton("Button");
        panelS.add(button);
        JButton button1 = new JButton("Button");
        JButton button2 = new JButton("Button");
        JButton button3 = new JButton("Button");
        panelC.add(button1);
        panelC.add(button2);
        panelC.add(button3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.getContentPane().add(panelN, BorderLayout.NORTH);
        frame.getContentPane().add(panelC, BorderLayout.CENTER);
        frame.getContentPane().add(panelS, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Panneau();
    }

    public void addColoredText(JTextPane pane, String text, Color color) {
        StyledDocument doc = pane.getStyledDocument();

        Style style = pane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } 
        catch (BadLocationException e) {
            e.printStackTrace();
        }           
    }
    
}