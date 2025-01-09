package ihm.questionnaire.export;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe représentant la fenêtre qui permet d'exporter un questionnaire.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-18 Norme ISO-8601
 */
public class FrameExport extends JFrame
{
	private PanelExport panelExport;

	/**
	 * Constructeur de la classe FrameExport.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public FrameExport(Controleur ctrl, IHM ihm)
	{
		this.setTitle("Générer une évaluation");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelExport = new PanelExport(ctrl, ihm, this);
		this.add(this.panelExport);

		this.pack();
	}
}
