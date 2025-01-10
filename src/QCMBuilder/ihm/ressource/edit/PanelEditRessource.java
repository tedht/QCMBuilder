package QCMBuilder.ihm.ressource.edit;


import QCMBuilder.controleur.Controleur;
import QCMBuilder.ihm.IHM;
import QCMBuilder.ihm.shared.PanelEditNom;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/** 
 * Classe JPanel générique dédiée à l'édition (création ou modification) d'une ressource.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class PanelEditRessource extends PanelEditNom
{
	protected JTextField txtCode;

	private JPanel     panelInfoCode;
	
	/**
	 * Constructeur de la classe PanelEditRessource.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public PanelEditRessource(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
		
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.lblNom.setText("Nom de la ressource :");

		this.panelInfoCode = new JPanel(new BorderLayout());
		this.txtCode       = new JTextField(30);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.panelInfo.add(this.panelInfoCode);
		this.panelInfo.add(this.panelInfoNom);

		this.panelInfoCode.add(new JLabel("Code : "), BorderLayout.NORTH);
		this.panelInfoCode.add(this.txtCode, BorderLayout.CENTER);
	}

	/**
	 * Méthode abstraite pour définir ce qu'on fait lorsqu'on clique sur le bouton "Enregistrer".
	 */
	public abstract boolean enregistrer();
}
