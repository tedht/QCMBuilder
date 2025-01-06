package ihm.question;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

public class FrameGestionQuestion extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelGestionQuestion panelGestionQuestion;

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

	public void reinitAffichage() 
	{
		this.panelGestionQuestion.afficher();
	}

	public void maj()
	{
		this.panelGestionQuestion.initDdlsts();	
	}

}
