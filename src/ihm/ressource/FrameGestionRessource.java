package ihm.ressource;

import controleur.Controleur;
import ihm.IHM;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *  Classe représentant la fenêtre de gestion des ressources.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class FrameGestionRessource extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelGestionRessource panelGestionRessource;

	/**
	 * Constructeur de la classe FrameGestionRessource.
	 * 
	 * @param ctrl le controleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public FrameGestionRessource(Controleur ctrl, IHM ihm) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setTitle("Gestion ressource");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setResizable(false);

		// Création et ajout du panneau de menu.
		this.panelGestionRessource = new PanelGestionRessource(this.ctrl, this.ihm, this);
		this.add(this.panelGestionRessource);

		// Définition des dimensions.
		this.setSize(new Dimension(IHM.LARGEUR_GESTION, IHM.HAUTEUR_GESTION));
	}

	/**
	 * Réinitialise l'affichage du panel de gestion des ressources.
	 */
	public void reinitAffichage() 
	{
		this.panelGestionRessource.afficher();
	}
}
