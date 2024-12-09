package ihm.composants;

import java.awt.Color;
import java.awt.event.MouseEvent;

import controleur.Controleur;

public class PanelCarteRessource extends PanelCarte
{
	public PanelCarteRessource(Controleur ctrl, String titre, String sousTitre, String cheminImg)
	{
		super(ctrl, titre, sousTitre, cheminImg);
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisRessource());
	}

	private class GereSourisRessource extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelCarteRessource.this.ctrl.setRessourceActive(PanelCarteRessource.this.lblTitre.getText());
			setBackground(Color.LIGHT_GRAY);
		}
	}
}
