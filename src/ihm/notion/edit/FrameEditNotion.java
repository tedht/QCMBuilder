package ihm.notion.edit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/** Classe représentant la fenêtre d'édition (création ou modification) d'une notion.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class FrameEditNotion extends JFrame
{
	private PanelEditNotion panelEditNotion;

	/**
	 * Constructeur de la classe PaneleditRessource() utilisé lorsqu'on veut créer une notion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditNotion(Controleur ctrl, IHM ihm)
	{
		this.setTitle("Créer une Notion");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditNotion = new PanelCreationNotion(ctrl, ihm);
		this.add(this.panelEditNotion);

		this.pack();
	}

	/**
	 * Constructeur de la classe PaneleditRessource() utilisé lorsqu'on veut modifier une notion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditNotion(Controleur ctrl, IHM ihm, Integer idNot) 
	{
		this.setTitle("Modifier une Notion");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditNotion = new PanelModifNotion(ctrl, ihm, idNot);
		this.add(this.panelEditNotion);

		this.pack();
	}
}
