package ihm.notion.edit;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;


/** Classe JPanel de la fenêtre d'édition d'une notion
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelEditNotion extends PanelEditNom
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
		this.lblNom.setText("Nom de la notion :");

		this.panelInfo.add(this.panelInfoNom);
	}

	public boolean valider()
	{
		if(this.ctrl.getRessourceActive().getNotion(this.txtNom.getText()) == null)
		{
			this.ctrl.creerNotion(this.txtNom.getText());
			this.ihm.reinitAffichageNotion();
			return true;
		}
		return false;
	}
}