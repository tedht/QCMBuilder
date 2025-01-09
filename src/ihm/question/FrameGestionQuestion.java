package ihm.question;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 *  Classe représentant la fenêtre de gestion des questions.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-10 Norme ISO-8601
 */
public class FrameGestionQuestion extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelGestionQuestion panelGestionQuestion;

	/**
	 * Constructeur de la classe FrameGestionQuestion.
	 * 
	 * @param ctrl Le controleur.
	 * @param ihm  Le gestionnaire des fenêtres de l'application.
	 */
	public FrameGestionQuestion(Controleur ctrl, IHM ihm) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setTitle("Gestion Question");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelGestionQuestion = new PanelGestionQuestion(this.ctrl, this.ihm);
        this.add(this.panelGestionQuestion);

        // Définition des dimensions.
        this.setSize(new Dimension(IHM.LARGEUR_GESTION, IHM.HAUTEUR_GESTION));
	}

	/**
     * Réinitialise l'affichage du panel de gestion des questions.
     */
	public void reinitAffichage() 
	{
		this.panelGestionQuestion.reinitDdlsts();	
		this.panelGestionQuestion.afficher();
	}

}
