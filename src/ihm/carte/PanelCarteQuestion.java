package ihm.carte;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelCarteQuestion extends PanelCarte
{
	private boolean detail;

	private JPanel panelAction;

	private JButton btnModifier, btnDupliquer, btnSupprimer;
	
	/**
	 * Constructeur de la classe PanelCarteQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelCarteQuestion(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		super(ctrl, ihm, titre, sousTitre);
		
		this.detail = false;

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.btnModifier  = new JButton("M");
		this.btnDupliquer = new JButton("D");
		this.btnSupprimer = new JButton("S");

		this.panelAction = new JPanel();

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.panelInfo.add(this.lblTitre);
		this.panelInfo.add(this.lblSousTitre);
		this.add(this.panelInfo, BorderLayout.CENTER);

		this.panelAction.add(this.btnModifier);
		this.panelAction.add(this.btnDupliquer);
		this.panelAction.add(this.btnSupprimer);
		this.add(this.panelAction, BorderLayout.EAST);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisQuestion());
	}

	private class GereSourisQuestion extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelCarteQuestion.this.detail = !PanelCarteQuestion.this.detail;
			PanelCarteQuestion.this.setBackground(Color.LIGHT_GRAY);
		}
	}
}
