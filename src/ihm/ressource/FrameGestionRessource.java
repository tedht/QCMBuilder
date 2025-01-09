package ihm.ressource;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

public class FrameGestionRessource  extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelGestionRessource panelGestionRessource;

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

	public void reinitAffichage() 
	{
		this.panelGestionRessource.afficher();
	}
}
