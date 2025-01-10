package QCMBuilder.ihm;


import javax.swing.*;

/**
 * Classe représentant la fenêtre principale de l'application.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-06 Norme ISO-8601
 */
public class FrameQCMBuilder extends JFrame
{
	private IHM ihm;

	private PanelQCMBuilder panelQCMBuilder;

	/**
	 * Constructeur de la classe FrameQCMBuilder.
	 * 
	 * @param ihm le gestionnaire des fenêtres de l'application.
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