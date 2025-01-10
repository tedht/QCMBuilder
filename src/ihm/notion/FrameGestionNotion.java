package ihm.notion;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 *  Classe représentant la fenêtre de gestion des notions.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-10 Norme ISO-8601
 */
public class FrameGestionNotion  extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelGestionNotion panelGestionNotion;

	/**
	 * Constructeur de la classe FrameGestionNotion.
	 * 
	 * @param ctrl Le controleur.
	 * @param ihm  Le gestionnaire des fenêtres de l'application.
	 */
	public FrameGestionNotion(Controleur ctrl, IHM ihm) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setTitle("Gestion Notion");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setResizable(false);

		// Création et ajout du panneau de menu.
		this.panelGestionNotion = new PanelGestionNotion(this.ctrl, this.ihm, this);
		this.add(this.panelGestionNotion);

		// Définition des dimensions.
		this.setSize(new Dimension(IHM.LARGEUR_GESTION, IHM.HAUTEUR_GESTION));
	}

	/**
	 * Réinitialise l'affichage du panel de gestion des notions.
	 */
	public void reinitAffichage() 
	{
		this.panelGestionNotion.afficher();
	}
}
