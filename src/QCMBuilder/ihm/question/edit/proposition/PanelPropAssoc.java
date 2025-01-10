package QCMBuilder.ihm.question.edit.proposition;


import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

import QCMBuilder.ihm.IHM;
import QCMBuilder.ihm.question.edit.PanelAjoutQuestion;

/** 
 * Classe JPanel représentant une proposition de réponse d'une question Association
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelPropAssoc extends PanelProp
{
	private JTextArea[] tabTxtProposition;
	private JButton  [] tabBtnSupprimer;
	
	/**
	 * Constructeur de la classe PanelPropAssoc.
	 * @param panelAjoutQuestion le panel qui gère l'ajout de propositions.
	 */
	public PanelPropAssoc(PanelAjoutQuestion panelAjoutQuestion)
	{
		super(panelAjoutQuestion);

		JPanel[] tabPanelBtnSupprimer;
		
		JPanel panelTxt;

		this.tabTxtProposition = new JTextArea[2];
		this.tabBtnSupprimer   = new JButton  [2];
		tabPanelBtnSupprimer   = new JPanel   [2];

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		panelTxt = new JPanel(new GridLayout(1, 2, 5 ,0));

		for(int i = 0; i < 2; i++)
		{
			tabPanelBtnSupprimer[i] = new JPanel();

			this.tabTxtProposition[i] = new JTextArea();
			this.tabTxtProposition[i].setRows(2);
			this.tabTxtProposition[i].setLineWrap(true);     
			this.tabTxtProposition[i].setWrapStyleWord(true);
			this.tabTxtProposition[i].setBorder(new LineBorder(Color.GRAY));
			this.tabTxtProposition[i].setFont(new Font("Arial", Font.PLAIN, 12));
			this.tabTxtProposition[i].setCaretColor(Color.BLACK);
			this.tabTxtProposition[i].setMargin(new Insets(2, 5, 2, 5));
	
			this.tabBtnSupprimer[i] = new JButton(IHM.getImgIconSVG("res/Supprimer.svg", 16, 16));
			this.tabBtnSupprimer[i].setPreferredSize(new Dimension(24, 24));

		}

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(tabPanelBtnSupprimer[0], BorderLayout.WEST);
		tabPanelBtnSupprimer[0].add(this.tabBtnSupprimer[0]);

		this.add(tabPanelBtnSupprimer[1], BorderLayout.EAST);
		tabPanelBtnSupprimer[1].add(this.tabBtnSupprimer[1]);

		this    .add(panelTxt, BorderLayout.CENTER);
		panelTxt.add(this.tabTxtProposition[0]);
		panelTxt.add(this.tabTxtProposition[1]);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.tabBtnSupprimer[0].addActionListener(this);
		this.tabBtnSupprimer[1].addActionListener(this);
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le texte gauche.
	 * 
	 * @return le texte gauche.
	 */
	public String getTextGauche() 
	{ 
		return this.tabTxtProposition[0].getText(); 
	}

	/**
	 * Retourne le texte droite.
	 * 
	 * @return le texte droite.
	 */
	public String getTextDroite() 
	{ 
		return this.tabTxtProposition[1].getText(); 
	}

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Définit le texte gauche de la proposition.
	 * 
	 * @param text le texte à afficher dans la zone de texte gauche.
	 */
	public void setTextGauche(String text) 
	{ 
		this.tabTxtProposition[0].setText(text); 
	}
		
	/**
	 * Définit le texte droite de la proposition.
	 * 
	 * @param text le texte à afficher dans la zone de texte droite.
	 */
	public void setTextDroite(String text) 
	{ 
		this.tabTxtProposition[1].setText(text); 
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.tabBtnSupprimer[0] || e.getSource() == this.tabBtnSupprimer[1])
		{
			this.panelAjoutQuestion.supprimerProposition(this);
		}
	}
	
}
