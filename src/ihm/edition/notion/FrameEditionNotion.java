package ihm.edition.notion;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;

/**
 * Classe représentant la fenêtre d'édition (création ou modification) d'une notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/09
 * @version 1.0
 */
public class FrameEditionNotion extends JFrame
{
	private PanelEditionNotion panelEditionNotion;

	/**
	 * Constructeur de la classe PanelEditionRessource utilisé lorsqu'on veut créer une notion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditionNotion(Controleur ctrl)
	{
		this.setTitle("Créer une Notion");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditionNotion = new PanelEditionNotion(ctrl);
		this.add(this.panelEditionNotion);

		this.pack();

		this.setVisible(true);
	}
}
