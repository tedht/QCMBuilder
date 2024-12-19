package ihm;

import javax.swing.*;

/**
 * Classe représentant la fenêtre principale de l'application.
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class FrameQCMBuilder extends JFrame
{
	private IHM ihm;

	private PanelQCMBuilder panelQCMBuilder;

	/**
     * Constructeur de la classe FrameQCMBuilder.
     * 
     * @param ctrl Le contrôleur
	 * @param ihm  L'ihm
     */
	public FrameQCMBuilder(IHM ihm)
	{
		this.ihm  = ihm;

		this.setTitle("QCMBuilder");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelQCMBuilder = new PanelQCMBuilder(this.ihm);
        this.add(this.panelQCMBuilder);
		this.pack();
	}
}