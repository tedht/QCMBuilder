package ihm.notion;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

public class FrameGestionNotion  extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelGestionNotion panelGestionNotion;

	public FrameGestionNotion(Controleur ctrl, IHM ihm) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setTitle("Gestion Notion");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelGestionNotion = new PanelGestionNotion(this.ctrl, this.ihm, this);
        this.add(this.panelGestionNotion);

        // Définition des dimensions.
        this.setSize(new Dimension(IHM.LARGEUR_GESTION, IHM.HAUTEUR_GESTION));
	}

	public void reinitAffichage() 
	{
		this.panelGestionNotion.afficher();
	}
}
