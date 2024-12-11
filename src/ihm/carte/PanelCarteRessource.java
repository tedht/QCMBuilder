package ihm.carte;

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
public class PanelCarteRessource extends PanelCarte
{
	/**
	 * Constructeur de la classe PanelCarteRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelCarteRessource(Controleur ctrl, IHM ihm, String titre, String sousTitre)
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
			PanelCarteRessource.this.ctrl.setRessourceActive(
				PanelCarteRessource.this.ctrl.getRessource(PanelCarteRessource.this.lblTitre.getText())
			);
			PanelCarteRessource.this.ihm.reinitAffichage();
		}
	}
}
