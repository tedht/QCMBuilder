package ihm.edition.notion;

import controleur.Controleur;
import ihm.FrameQCMBuilder;
import ihm.edition.PanelEditionEntite;

/**
 * Classe JPanel de la fenêtre d'édition d'une notion
 * 
 * @author Ted Herambert
 * @date 2024/12/09
 * @version 1.0
 */
public class PanelEditionNotion extends PanelEditionEntite
{
	/**
	 * Constructeur de la classe PanelEditionRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditionNotion(Controleur ctrl, FrameQCMBuilder framePrincipale)
	{
		super(ctrl, framePrincipale);
	}

	public String getType()
	{
		return "Notion";
	}

	public boolean valider()
	{
		if(this.ctrl.getRessourceActive().getNotion(this.txtNom.getText()) == null)
		{
			this.ctrl.creerNotion(this.ctrl.getRessourceActive(), this.txtNom.getText(), this.txtImage.getText());
			this.framePrincipale.reinitAffichage();
			return true;
		}
		return false;
	}
}