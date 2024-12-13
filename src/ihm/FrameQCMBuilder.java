package ihm;

import java.awt.*;
import javax.swing.*;

import controleur.Controleur;


/**
 * Classe représentant la fenêtre de la Banque de lstQuestions de l'application.
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class FrameQCMBuilder extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelQCMBuilder panelQCMBuilder;

	/**
     * Constructeur de la classe FrameQCMBuilder.
     * 
     * @param ctrl Le contrôleur
     */
	public FrameQCMBuilder(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setTitle("Banque de Questions");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelQCMBuilder = new PanelQCMBuilder(this.ctrl, this.ihm);
        this.add(this.panelQCMBuilder);

        // Définition des dimensions.
        this.setSize(new Dimension(IHM.LARGEUR_QCM_BUILDER, IHM.HAUTEUR_QCM_BUILDER));

		// Positionnement de la fenêtre au centre de l'écran.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width  - this.getWidth ()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

		this.setVisible(true);
	}

	public void reinitAffichage()
	{ 
		this.panelQCMBuilder.reinitAffichage();          
	}
}