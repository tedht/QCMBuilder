package ihm.ressource.edit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;


/** Classe représentant la fenêtre d'édition (création ou modification) d'une ressource.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class FrameEditRessource extends JFrame
{
	private PanelEditRessource panelEditRessource;

	/**
	 * Constructeur de la classe PanelEditRessource utilisé lorsqu'on veut créer une ressource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditRessource(Controleur ctrl, IHM ihm)
	{
		this.setTitle("Créer une Ressource");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditRessource = new PanelEditRessource(ctrl, ihm);
		this.add(this.panelEditRessource);

		this.pack();
	}

	public FrameEditRessource(Controleur ctrl, IHM ihm, String code) 
	{
		this.setTitle("Modifier une Ressource");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditRessource = new PanelEditRessource(ctrl, ihm, code);
		this.add(this.panelEditRessource);

		this.pack();
	}
}
