package ihm.entite;

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
public abstract class PanelEntite extends JPanel
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
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		if(titre.length() >= 50) titre = titre.substring(0, 50) + "...";
		this.lblTitre = new JLabel(titre);
		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 20));

		if(sousTitre.length() >= 50) sousTitre = sousTitre.substring(0, 50) + "...";
		this.lblSousTitre = new JLabel(sousTitre);
		this.lblSousTitre.setFont(new Font("Arial", Font.BOLD, 12));

		this.panelInfo = new JPanel();
		this.panelInfo.setLayout(new BoxLayout(this.panelInfo, BoxLayout.Y_AXIS));
		this.panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.btnModifier  = new JButton("M");
		this.btnSupprimer = new JButton("S");

		this.panelAction = new JPanel();

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

	protected class GereSouris extends MouseAdapter
	{
		
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			PanelEntite.this.setBackground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			PanelEntite.this.setBackground(Color.WHITE);
		}
	}
}
