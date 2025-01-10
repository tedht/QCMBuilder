package QCMBuilder.ihm.notion.edit;


import QCMBuilder.controleur.Controleur;

import QCMBuilder.ihm.IHM;
import QCMBuilder.ihm.shared.PanelEditNom;


/** 
 * Classe JPanel générique dédiée à l'édition (création ou modification) d'une notion.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class PanelEditNotion extends PanelEditNom
{
	/**
	 * Constructeur de la classe PaneEditNotion.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
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

	/**
	 * Méthode abstraite pour définir ce qu'on fait lorsqu'on clique sur le bouton "Enregistrer".
	 */
	public abstract boolean enregistrer();
}