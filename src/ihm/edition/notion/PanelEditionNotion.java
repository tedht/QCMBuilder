package ihm.edition.notion;

import controleur.Controleur;
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
	public PanelEditionNotion(Controleur ctrl)
	{
		super(ctrl);
	}

	public String getType()
	{
		return "Notion";
	}

	public void valider()
	{
		this.ctrl.creerNotion(this.txtNom.getText(), this.txtImage.getText());
	}
}