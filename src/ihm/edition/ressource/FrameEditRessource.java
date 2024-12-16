package ihm.edition.ressource;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe représentant la fenêtre d'édition (création ou modification) d'une ressource.
 * 
 * @author Ted Herambert
 * @date 2024/12/09
 * @version 1.0
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
}