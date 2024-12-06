package ihm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;

/**
 * Classe JPanel qui contient les composents de la fenêtre de la base de questions.
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class PanelBaseDeQuestions extends JPanel implements ActionListener
{
	private Controleur ctrl;

	private JPanel  panelEntete, panelContenu, panelNouvelleRessource;
	private JButton btnRetour, btnNouvelleRessource, btnGenererQuestionnaire;
	private JLabel  lblTitre;

	/**
	 * Constructeur de la classe PanelBaseDeQuestions.
	 *
	 * @param ctrl le contrôleur
	 */
	public PanelBaseDeQuestions(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());
		
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.setBorder(new EmptyBorder(5,30,30,30));
		this.panelEntete            = new JPanel(new GridLayout(0,2, 10,10));
		this.panelContenu           = new JPanel(new GridLayout(0,2, 30,30));
		this.panelNouvelleRessource = new JPanel();

		this.btnRetour               = new JButton("Retour");
		this.btnNouvelleRessource    = new JButton("Nouvelle Ressource");
		this.btnGenererQuestionnaire = new JButton("Générer Questionnaire");

		this.lblTitre = new JLabel("Ressources");
		this.lblTitre.setFont(new Font("Arial", Font.PLAIN, 32));

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.add(this.panelEntete, BorderLayout.NORTH);
		this.add(this.panelContenu, BorderLayout.CENTER);
		
		this.panelNouvelleRessource.add(this.btnNouvelleRessource);

		// Entête
		this.panelEntete.add(this.btnRetour);                
		this.panelEntete.add(this.btnGenererQuestionnaire); 
		this.panelEntete.add(this.lblTitre);

		// Contenu
		this.panelContenu.add(this.panelNouvelleRessource);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnRetour              .addActionListener(this); 
		this.btnNouvelleRessource   .addActionListener(this); 
		this.btnGenererQuestionnaire.addActionListener(this); 
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}
}