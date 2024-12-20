package ihm.shared;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel générique pour afficher une entité (ressource/notion/question).
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
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
	 * Constructeur de la classe PanelEntite.
	 *
	 * @param ctrl  Le contrôleur
	 */
	public PanelEntite(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		this.ctrl  = ctrl;
		this.ihm   = ihm;

		this.setLayout(new BorderLayout());

		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		this.setBackground(Color.WHITE);

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.panelInfo = new JPanel(); this.panelInfo.setOpaque(false);
		this.panelInfo.setLayout(new BoxLayout(this.panelInfo, BoxLayout.Y_AXIS));
		this.panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		if(titre.length() >= 40) titre = titre.substring(0, 40) + "...";
		this.lblTitre = new JLabel(titre);
		this.lblTitre.setOpaque(false);
		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 16));

		if(sousTitre.length() >= 40) sousTitre = sousTitre.substring(0, 40) + "...";
		this.lblSousTitre = new JLabel(sousTitre);
		this.lblSousTitre.setFont(new Font("Arial", Font.BOLD, 12));

		this.panelAction = new JPanel(); this.panelAction.setOpaque(false);

		this.btnModifier  = new JButton("M");
		this.btnSupprimer = new JButton("S");
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.panelInfo.add(this.lblTitre);
		this.panelInfo.add(this.lblSousTitre);
		this.add(this.panelInfo, BorderLayout.CENTER);

		this.panelAction.add(this.btnModifier);
		this.panelAction.add(this.btnSupprimer);
		this.add(this.panelAction, BorderLayout.EAST);
	}
}
