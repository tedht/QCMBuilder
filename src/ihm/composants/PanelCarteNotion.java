package ihm.composants;

import java.awt.Color;
import java.awt.event.MouseEvent;

import controleur.Controleur;

public class PanelCarteNotion extends PanelCarte
{	
	public PanelCarteNotion(Controleur ctrl, String titre, String sousTitre, String cheminImg)
	{
		super(ctrl, titre, sousTitre, cheminImg);

		// Activation des composants
		this.addMouseListener(new GereSourisNotion());
	}

	private class GereSourisNotion extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelCarteNotion.this.ctrl.setNotionActive(PanelCarteNotion.this.lblTitre.getText());
			setBackground(Color.LIGHT_GRAY);
		}
	}
}
