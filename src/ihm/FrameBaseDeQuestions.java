package ihm;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;

/**
 * Classe représentant la fenêtre de la base de questions de l'application.
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class FrameBaseDeQuestions extends JFrame
{
	private PanelBaseDeQuestions panelBaseDeQuestions;

	/**
     * Constructeur du frame.
     * 
     * @param ctrl Le contrôleur principal de l'application.
     */
	public FrameBaseDeQuestions(Controleur ctrl)
	{
		this.setTitle("Base de Questions");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelBaseDeQuestions = new PanelBaseDeQuestions(ctrl);
        this.add(this.panelBaseDeQuestions);

        // Définition des dimensions.
        this.setSize(new Dimension(800, 800));

		// Positionnement de la fenêtre au centre de l'écran.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

		this.setVisible(true);
	}
}