package ihm.edition.notion;

import controleur.Controleur;
import ihm.IHM;
import ihm.edition.PanelEditEntite;


/** Classe JPanel de la fenêtre d'édition d'une notion
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelEditNotion extends PanelEditEntite
{
	/**
	 * Constructeur de la classe PanelEditRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditNotion(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.panelInfo.add(this.panelInfoNom);
	}

	public String getType()
	{
		return "Notion";
	}

	public boolean valider()
	{
		if(this.ctrl.getRessourceActive().getNotion(this.txtNom.getText()) == null)
		{
			this.ctrl.creerNotion(this.txtNom.getText());
			this.ihm.reinitAffichage();
			return true;
		}
		return false;
	}
}