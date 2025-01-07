package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Classe PanelQCMBuilder qui gère l'interface de gestion des QCM.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelQCMBuilder extends JPanel implements ActionListener
{
	private IHM        ihm;	

	private JButton    btnRessourceNotion, btnQuestion, btnQuestionnaire, btnQuitter;

	/** 
	 * Constructeur du panel de gestion des QCM.
	 * 
	 * @param ihm L'interface homme-machine.
	 */
	public PanelQCMBuilder(IHM ihm) 
	{
		this.ihm  = ihm;

		this.setLayout(new GridLayout(0,1,10,10));
		this.setBorder(new EmptyBorder(20,20,20,20));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.btnRessourceNotion = new JButton("Gérer les Ressource/Notions");
		this.btnQuestion        = new JButton("Gérer les Question");
		this.btnQuestionnaire   = new JButton("Générer un Questionnaire");
		this.btnQuitter         = new JButton("Quitter");
		
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.add(this.btnRessourceNotion);
		this.add(this.btnQuestion);
		this.add(this.btnQuestionnaire);
		this.add(new JLabel(" "));
		this.add(this.btnQuitter);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/

		this.btnRessourceNotion.addActionListener(this); 
		this.btnQuestion       .addActionListener(this); 
		this.btnQuestionnaire  .addActionListener(this); 
		this.btnQuitter        .addActionListener(this); 


	}

	/**
	 * Méthode qui gère les actions des boutons.
	 * 
	 * @param e L'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnRessourceNotion) { this.ihm.afficherRessourceNotion(); }
		if(e.getSource() == this.btnQuestion)        { this.ihm.afficherQuestion       (); }
		if(e.getSource() == this.btnQuestionnaire)   { this.ihm.afficherQuestionnaire  (); }
		if(e.getSource() == this.btnQuitter)         { this.ihm.quitter                (); }
 	}
	
}
