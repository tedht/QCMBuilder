package ihm.entite;

import java.awt.event.MouseEvent;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelRessource extends PanelEntite
{
	/**
	 * Constructeur de la classe PanelRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelRessource(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		super(ctrl, ihm, titre, sousTitre);
		
		// Activation des composants
		this.addMouseListener(new GereSourisRessource());
	}

	private class GereSourisRessource extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelRessource.this.ctrl.setRessourceActive(
				PanelRessource.this.ctrl.getRessource(PanelRessource.this.lblTitre.getText())
			);
			PanelRessource.this.ihm.reinitAffichage();
		}
	}
}
