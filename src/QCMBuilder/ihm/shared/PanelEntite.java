package QCMBuilder.ihm.shared;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import QCMBuilder.controleur.Controleur;
import QCMBuilder.ihm.IHM;

/**
 * Classe JPanel générique pour afficher une entité (ressource/notion/question).
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-10 Norme ISO-8601
 */
public abstract class PanelEntite extends JPanel implements ActionListener
{
	protected Controleur ctrl;
	protected IHM        ihm;

	protected JLabel lblTitre;
	protected JLabel lblSousTitre;
	
	protected JPanel panelInfo, panelAction;

	protected JButton btnModifier, btnSupprimer;

	/**
	 * Constructeur qui crée une instance de PanelEntite sans texte.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public PanelEntite(Controleur ctrl, IHM ihm)
	{
		this(ctrl, ihm, "", "");
	}

	/**
	 * Constructeur de la classe PanelEntite.
	 *
	 * @param ctrl      le contrôleur.
	 * @param ihm       le gestionnaire des fenêtres de l'application.
	 * @param titre     le titre.
	 * @param sousTitre le sous-titre.
	 */
	public PanelEntite(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setLayout(new BorderLayout());

		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		this.setBackground(Color.WHITE);

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.panelInfo = new JPanel(); this.panelInfo.setOpaque(false);
		this.panelInfo.setLayout(new BoxLayout(this.panelInfo, BoxLayout.Y_AXIS));
		this.panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Gestion de la longueur du titre (troncature si nécessaire)
		if(titre.length() >= 40) titre = titre.substring(0, 40) + "...";
		this.lblTitre = new JLabel(titre);
		this.lblTitre.setOpaque(false);
		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 16));

		// Gestion de la longueur du sous-titre (troncature si nécessaire)
		if(sousTitre.length() >= 40) sousTitre = sousTitre.substring(0, 40) + "...";
		this.lblSousTitre = new JLabel(sousTitre);
		this.lblSousTitre.setFont(new Font("Arial", Font.BOLD, 12));

		this.panelAction = new JPanel(); this.panelAction.setOpaque(false);
		
        this.btnModifier = new JButton(IHM.getImgIconSVG("res/Modifier.svg", 16, 16));
        this.btnModifier.setPreferredSize(new Dimension(24, 24));
		
		this.btnSupprimer = new JButton(IHM.getImgIconSVG("res/Supprimer.svg", 16, 16));
		this.btnSupprimer.setPreferredSize(new Dimension(24, 24));

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.panelInfo.add(this.lblTitre);
		this.panelInfo.add(this.lblSousTitre);
		this.add(this.panelInfo, BorderLayout.CENTER);

		this.panelAction.add(this.btnModifier);
		this.panelAction.add(this.btnSupprimer);
		this.add(this.panelAction, BorderLayout.EAST);
	}
}
