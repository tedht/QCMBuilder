package ihm.notion.edit;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;


/** Classe JPanel de la fenêtre d'édition d'une notion
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class PanelEditNotion extends PanelEditNom
{
	/**
	 * Constructeur de la classe PaneleditRessource().
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

	public abstract boolean enregistrer();
}