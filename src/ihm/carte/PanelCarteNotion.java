package ihm.carte;

import java.awt.Color;
import java.awt.event.MouseEvent;

import controleur.Controleur;
import ihm.FrameQCMBuilder;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelCarteNotion extends PanelCarte
{	
	/**
	 * Constructeur de la classe PanelCarteNotion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelCarteNotion(Controleur ctrl, FrameQCMBuilder frame, String titre, String sousTitre, String cheminImg)
	{
		super(ctrl, frame, titre, sousTitre, cheminImg);

		// Activation des composants
		this.addMouseListener(new GereSourisNotion());
	}

	private class GereSourisNotion extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelCarteNotion.this.ctrl.setNotionActive(
				PanelCarteNotion.this.ctrl.getRessourceActive().getNotion(PanelCarteNotion.this.lblTitre.getText())
			);
			PanelCarteNotion.this.framePrincipale.afficherQuestions();
		}
	}
}
