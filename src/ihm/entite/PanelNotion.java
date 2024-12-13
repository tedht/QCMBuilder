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
public class PanelNotion extends PanelEntite
{	
	/**
	 * Constructeur de la classe PanelNotion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelNotion(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		super(ctrl, ihm, titre, sousTitre);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisNotion());
	}

	private class GereSourisNotion extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelNotion.this.ctrl.setNotionActive(
				PanelNotion.this.ctrl.getRessourceActive().getNotion(PanelNotion.this.lblTitre.getText())
			);
			PanelNotion.this.ihm.reinitAffichage();
		}
	}
}
