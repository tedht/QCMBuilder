package ihm.carte;

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
public abstract class PanelCarte extends JPanel
{
	protected Controleur ctrl;
	protected IHM        ihm;

	protected JLabel lblTitre;
	protected JLabel lblSousTitre;
	
	protected JPanel panelInfo;


	/**
	 * Constructeur de la classe PanelCarte.
	 *
	 * @param ctrl  Le contrôleur
	 */
	public PanelCarte(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.lblTitre = new JLabel(titre);
		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 20));

		this.lblSousTitre = new JLabel(sousTitre);
		this.lblSousTitre.setFont(new Font("Arial", Font.BOLD, 12));

		this.panelInfo = new JPanel();
		this.panelInfo.setLayout(new BoxLayout(this.panelInfo, BoxLayout.Y_AXIS));
		this.panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.panelInfo.add(this.lblTitre);
		this.panelInfo.add(this.lblSousTitre);
		this.add(this.panelInfo, BorderLayout.CENTER);
	}

	protected class GereSouris extends MouseAdapter
	{
		
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			PanelCarte.this.setBackground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			PanelCarte.this.setBackground(Color.WHITE);
		}
	}
}
