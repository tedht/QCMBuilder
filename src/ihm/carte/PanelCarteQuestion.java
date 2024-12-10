package ihm.carte;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import controleur.Controleur;

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
	public PanelCarteQuestion(Controleur ctrl, String titre, String sousTitre, String cheminImg)
	{
		super(ctrl, titre, sousTitre, cheminImg);
		
		this.ctrl = ctrl;
		this.detail = false;

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

		this.imageIcon = new ImageIcon(cheminImg);

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

		//this.add(new JLabel(imageIcon), BorderLayout.WEST);

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
			setBackground(Color.LIGHT_GRAY);
		}
	}
}
