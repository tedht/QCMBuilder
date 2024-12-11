package ihm.carte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import controleur.Controleur;

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
	public PanelCarteRessource(Controleur ctrl, String titre, String sousTitre, String cheminImg)
	{
		super(ctrl, titre, sousTitre, cheminImg);
		
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
			PanelCarteRessource.this.setBackground(Color.LIGHT_GRAY);
		}
	}
}
