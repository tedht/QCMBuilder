package QCMBuilder.ihm.question.edit;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import QCMBuilder.controleur.Controleur;
import QCMBuilder.metier.entite.Notion;
import QCMBuilder.metier.entite.Ressource;
import QCMBuilder.metier.entite.question.Difficulte;

/** 
 * Classe JPanel pour saisir les paramètres intitiales lorsqu'on crée une question.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelInitQuestion extends JPanel implements ActionListener, ItemListener
{
	protected Controleur           ctrl;
	protected FrameEditQuestion    frame;

	protected JTextField           txtPoints, txtTemps;
	protected JComboBox<Ressource> ddlstRessource; 
	protected JComboBox<Notion>    ddlstNotion;
	protected JComboBox<String>    ddlstTypeQuestion;

	protected JRadioButton[]       tabRbDifficulte;
	protected ButtonGroup          btgDifficulte;

	protected JButton              btnAnnuler, btnSuivant;

	/**
	 * Constructeur de la classe PanelInitQuestion.
	 * 
	 * @param ctrl  le contrôleur.
	 * @param frame le frame d'édition de questions.
	 */
	public PanelInitQuestion(Controleur ctrl, FrameEditQuestion frame) 
	{
		JPanel panelInfo, panelAction;

		JPanel panelInfoPoints, panelInfoTemps, panelInfoRessource, 
			   panelInfoNotion, panelInfoNiveau, panelInfoTypeQuestion;

 		JPanel panelPoints, panelTemps, panelNiveau;

		Difficulte diff;

		GridBagConstraints gbc;


		this.ctrl  = ctrl;
		this.frame = frame;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		/* INFO */
		panelInfo = new JPanel(new GridBagLayout());
		panelInfo.setBorder(new EmptyBorder(10,10,10,10));

		panelInfoPoints       = new JPanel(new BorderLayout());
		panelInfoTemps        = new JPanel(new BorderLayout());
		panelInfoRessource    = new JPanel(new BorderLayout()); 
		panelInfoNotion       = new JPanel(new BorderLayout());
		panelInfoNiveau       = new JPanel(new BorderLayout());
		panelInfoTypeQuestion = new JPanel(new BorderLayout());

		panelPoints = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelTemps  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelNiveau = new JPanel(new FlowLayout(FlowLayout.LEFT));

		/* Champs de texte */
		this.txtPoints = new JTextField(5);
		this.txtPoints.setHorizontalAlignment(SwingConstants.CENTER);

		this.txtTemps  = new JTextField(5);
		this.txtTemps.setHorizontalAlignment(SwingConstants.CENTER);

		/* Listes déroulantes */
		// Ressource
		this.ddlstRessource = new JComboBox<Ressource>();
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource);
		}
		this.ddlstRessource.setSelectedIndex(-1);
		this.ddlstRessource.setFocusable(false);
		this.ddlstRessource.setPrototypeDisplayValue(new Ressource("", String.format("%70s", " ")));

		// Notion
		this.ddlstNotion = new JComboBox<Notion>();
		this.ddlstNotion.setEnabled(false);
		this.ddlstNotion.setSelectedIndex(-1);
		this.ddlstNotion.setFocusable(false);
		this.ddlstNotion.setPrototypeDisplayValue(new Notion("", 0, String.format("%50s", " ")));

		// Type de Question 
		this.ddlstTypeQuestion = new JComboBox<String>();
		this.ddlstTypeQuestion.addItem("Question à choix multiple à réponse unique");
		this.ddlstTypeQuestion.addItem("Question à choix multiple à réponse multiple");
		this.ddlstTypeQuestion.addItem("Question à association d'éléments");
		this.ddlstTypeQuestion.addItem("Question avec élimination de propositions de réponses");
		this.ddlstTypeQuestion.setEnabled(false);
		this.ddlstTypeQuestion.setSelectedIndex(-1);

		/* Boutons Radio */
		btgDifficulte   = new ButtonGroup();
		tabRbDifficulte = new JRadioButton[4];
		for(int i = 0; i < 4; i++)
		{
			diff = Difficulte.fromInt(i);
			
			tabRbDifficulte[i] = new JRadioButton(diff.toString());
			tabRbDifficulte[i].setForeground(diff.getCouleur());
			tabRbDifficulte[i].setEnabled(false);

			btgDifficulte.add(tabRbDifficulte[i]);
		}

		/* ACTION */
		panelAction = new JPanel();
		panelAction.setBackground(new Color(200, 200, 250));

		this.btnAnnuler     = new JButton("Annuler");
		this.btnSuivant     = new JButton("Suivant");     this.btnSuivant.setEnabled(false);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/

		/* INFO */
		this.add(panelInfo, BorderLayout.CENTER);

		gbc = new GridBagConstraints();
		gbc.insets  = new Insets(0, 5, 10, 5);
		gbc.anchor  = GridBagConstraints.WEST;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.weightx = 1.0;

		/* Ligne 0 */
		// Points
		gbc.gridy = 0; gbc.gridx = 0; // (0,0)
		panelInfo      .add(panelInfoPoints, gbc);
		panelInfoPoints.add(new JLabel("Nombre de points :"), BorderLayout.NORTH);
		panelInfoPoints.add(panelPoints, BorderLayout.CENTER);
		panelPoints    .add(this.txtPoints);

		// Temps
		gbc.gridy = 0; gbc.gridx = 1; // (0,1)
		panelInfo     .add(panelInfoTemps, gbc);
		panelInfoTemps.add(new JLabel("Temps de réponse (min:sec) :"), BorderLayout.NORTH);
		panelInfoTemps.add(panelTemps, BorderLayout.CENTER);
		panelTemps    .add(this.txtTemps);

		gbc.gridy = 0; gbc.gridx = 2; // (0,2)
		panelInfo.add(new JPanel());

		/* Ligne 1 */
		// Ressource
		gbc.gridy = 1; gbc.gridx = 0; // (1,0)
		panelInfo         .add(panelInfoRessource, gbc);
		panelInfoRessource.add(new JLabel("Ressource :"), BorderLayout.NORTH);
		panelInfoRessource.add(this.ddlstRessource, BorderLayout.CENTER);

		// Notion
		gbc.gridy = 1; gbc.gridx = 1; // (1,1)
		panelInfo      .add(panelInfoNotion, gbc);
		panelInfoNotion.add(new JLabel("Notion :"), BorderLayout.NORTH);
		panelInfoNotion.add(this.ddlstNotion, BorderLayout.CENTER);

		// Niveau
		gbc.gridy = 1; gbc.gridx = 2; // (1,2)
		panelInfo      .add(panelInfoNiveau, gbc);
		panelInfoNiveau.add(new JLabel("Niveau :"), BorderLayout.NORTH);
		panelInfoNiveau.add(panelNiveau, BorderLayout.CENTER);
		for(JRadioButton rb : tabRbDifficulte)
			panelNiveau.add(rb);

		/* Ligne 2 */
		// Type de Question
		gbc.gridy = 2; gbc.gridx = 0; // (2,2)
		gbc.gridwidth = 3;
		panelInfo       .add(panelInfoTypeQuestion, gbc);
		panelInfoTypeQuestion.add(new JLabel("Type de question :"), BorderLayout.NORTH);
		panelInfoTypeQuestion.add(this.ddlstTypeQuestion, BorderLayout.CENTER);

		/* ACTION */
		this       .add(panelAction, BorderLayout.SOUTH);
		panelAction.add(this.btnAnnuler);
		panelAction.add(this.btnSuivant);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/

		/* INFO */
		for(JRadioButton rb : tabRbDifficulte)
			rb.addItemListener(this);

		this.ddlstRessource   .addItemListener(this);
		this.ddlstNotion      .addItemListener(this);
		this.ddlstTypeQuestion.addItemListener(this);

		/* ACTION */
		this.btnAnnuler.addActionListener(this);
		this.btnSuivant.addActionListener(this);
	}

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAnnuler) { this.frame.dispose     (); }
		if(e.getSource() == this.btnSuivant) { this.frame.pageSuivante(); }
	}

	/**
	 * Gère les changements d'état des éléments interactifs
	 * 
	 * @param e l'événement qui déclenche l'appel à cette méthode.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.ddlstRessource) 
		{  
			this.ddlstNotion.removeAllItems();
			for(Notion notion : this.ctrl.getNotions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode()))
			{
				this.ddlstNotion.addItem(notion);
			}
			this.ddlstNotion.setSelectedIndex(-1);
			this.ddlstNotion.setEnabled(true);
		}

		if(e.getSource() == this.ddlstNotion && this.ddlstNotion.isEnabled())
		{
			for(JRadioButton rb : tabRbDifficulte)
				rb.setEnabled(true);
		}

		if(e.getSource() == this.ddlstTypeQuestion)
		{
			this.btnSuivant.setEnabled(true);
			this.frame.clearPanelProps();
		}

		if(e.getSource() instanceof JRadioButton)
		{
			this.ddlstTypeQuestion.setEnabled(true);
		}
	}

	/**
	 * Récupère l'index du type de question choisi dans la liste déroulante.
	 * 
	 * @return l'index du type de question choisi dans la liste déroulante.
	 */
	public int getIndexTypeQuestion() 
	{
		return this.ddlstTypeQuestion.getSelectedIndex();
	}

	/**
	 * Récupère la ressource sélectionnée.
	 * 
	 * @return la ressource sélectionnée.
	 */
	public Ressource getRessource() 
	{
		return (Ressource)this.ddlstRessource.getSelectedItem();
	}

	/**
	 * Récupère la notion sélectionnée.
	 * 
	 * @return la notion sélectionnée.
	 */
	public Notion getNotion() 
	{
		return (Notion)this.ddlstNotion.getSelectedItem();
	}

	/**
	 * Récupère la difficultée sélectionnée.
	 * 
	 * @return la difficultée sélectionnée.
	 */
	public int getDifficulte() 
	{
		for(int i = 0; i < this.tabRbDifficulte.length; i++)
			if(this.tabRbDifficulte[i].isSelected())
				return i;
				
		return -1;
	}

	/**
	 * Récupère le temps saisi.
	 * @return le temps saisi.
	 */
	public String getTemps() 
	{
		return this.txtTemps.getText();
	}

	/**
	 * Récupère le nombre de points saisi.
	 * 
	 * @return le nombre de points saisi.
	 */
	public String getPoints() 
	{
		return this.txtPoints.getText();
	}
	
}
