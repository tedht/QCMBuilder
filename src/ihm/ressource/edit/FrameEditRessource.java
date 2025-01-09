package ihm.ressource.edit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;


/** Classe représentant la fenêtre d'édition (création ou modification) d'une ressource.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-11 Norme ISO-8601
 */
public class FrameEditRessource extends JFrame
{
	private PanelEditRessource panelEditRessource;

	/**
	 * Constructeur de la classe FrameEditRessource utilisé lorsqu'on veut créer une ressource.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public FrameEditRessource(Controleur ctrl, IHM ihm)
	{
		this.setTitle("Créer une Ressource");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditRessource = new PanelCreationRessource(ctrl, ihm);
		this.add(this.panelEditRessource);

		this.pack();
	}

	/**
	 * Constructeur de la classe FrameEditRessource utilisé lorsqu'on veut modifier une ressource.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 * @param code le code de la ressource qu'on veut modifier.
	 */
	public FrameEditRessource(Controleur ctrl, IHM ihm, String code) 
	{
		this.setTitle("Modifier une Ressource");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditRessource = new PanelModifRessource(ctrl, ihm, code);
		this.add(this.panelEditRessource);

		this.pack();
	}
}
