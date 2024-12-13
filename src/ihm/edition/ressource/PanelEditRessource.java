package ihm.edition.ressource;

import controleur.Controleur;
import ihm.IHM;
import ihm.edition.PanelEditEntite;

/**
 * Classe JPanel de la fenêtre d'édition d'une ressource.
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class PanelEditRessource extends PanelEditEntite
{
	/**
	 * Constructeur de la classe PanelEditRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditRessource(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
	}

	public String getType()
	{
		return "Ressource";
	}

	public boolean valider()
	{
		if(this.ctrl.getRessource(this.txtNom.getText()) == null)
		{
			this.ctrl.creerRessource(this.txtNom.getText());
			this.ihm.reinitAffichage();
			return true;
		}
		return false;
	}
}
