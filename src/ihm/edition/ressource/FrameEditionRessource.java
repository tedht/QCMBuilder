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
public class FrameEditionRessource extends JFrame
{
	private PanelEditionRessource panelEditionRessource;

	/**
	 * Constructeur de la classe PanelEditionRessource utilisé lorsqu'on veut créer une ressource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditionRessource(Controleur ctrl, IHM ihm)
	{
		this.setTitle("Créer une Ressource");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditionRessource = new PanelEditionRessource(ctrl, ihm);
		this.add(this.panelEditionRessource);

		this.pack();
	}
}
