package QCMBuilder.ihm.questionnaire;


import java.awt.BorderLayout;
import java.awt.Color;
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

import QCMBuilder.controleur.Controleur;
import QCMBuilder.metier.entite.Ressource;

/**
 * Classe JPanel dédièe à la saisie des paramètres généraux d'un questionnaire.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-16 Norme ISO-8601
 */
public class PanelParametresQuestionnaire extends JPanel implements ActionListener, ItemListener
{
	private Controleur                ctrl;
	private FrameCreationQuestionnaire frame;

	private JPanel                    panelInfo, panelAction;

	private JComboBox<Ressource>      ddlstRessource;

	private JRadioButton[]            tabRbChronometre;
	private ButtonGroup               btgChronometre;

	private JButton                   btnAnnuler, btnSuivant;

	/**
	 * Constructeur de la classe PanelParametresQuestionnaire
	 * 
	 * @param ctrl  le contrôleur
	 * @param frame la fenêtre de création d'une évaluation
	 */
	public PanelParametresQuestionnaire(Controleur ctrl, FrameCreationQuestionnaire frame) 
	{
		JPanel panelInfoRessource, panelInfoChronometre;
		JPanel panelChronometre;


		this.ctrl  = ctrl;
		this.frame = frame;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		/* INFO */
		this.panelInfo       = new JPanel(new GridBagLayout());

		panelInfoRessource   = new JPanel(new BorderLayout(5,5));
		panelInfoChronometre = new JPanel(new BorderLayout(5, 5));

		panelChronometre     = new JPanel();

		// Ressources
		this.ddlstRessource = new JComboBox<Ressource>();
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource);
		}
		this.ddlstRessource.setSelectedIndex(-1);
		this.ddlstRessource.setFocusable(false);
		this.ddlstRessource.setPrototypeDisplayValue(new Ressource("",String.format("%70s"," ")));

		// Chronomètre
		tabRbChronometre    = new JRadioButton[2];
		tabRbChronometre[0] = new JRadioButton("Oui");
		tabRbChronometre[1] = new JRadioButton("Non");

		btgChronometre = new ButtonGroup();
		for(JRadioButton rb : tabRbChronometre)
		{
			btgChronometre.add(rb);
			rb.setEnabled(false);
		}

		/* ACTION */
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		this.btnAnnuler = new JButton("Annuler");
		this.btnSuivant = new JButton("Suivant"); this.btnSuivant.setEnabled(false);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		
		/* INFO */
		this.add(this.panelInfo, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets  = new Insets(0, 5, 10, 5);
		gbc.anchor  = GridBagConstraints.WEST;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.weightx = 1.0;

		/* Ligne 0 */
		// Resources
		gbc.gridy = 0; gbc.gridx = 0; // (0,0)
		this.panelInfo    .add(panelInfoRessource, gbc);
		panelInfoRessource.add(new JLabel("Ressource :"), BorderLayout.NORTH);
		panelInfoRessource.add(this.ddlstRessource, BorderLayout.CENTER);

		// Chonomètre
		gbc.gridy = 0; gbc.gridx = 1; // (0,1)
		this.panelInfo      .add(panelInfoChronometre, gbc);
		panelInfoChronometre.add(new JLabel("Chronomètre :"), BorderLayout.NORTH);
		panelInfoChronometre.add(panelChronometre, BorderLayout.CENTER);
		for(JRadioButton rb : tabRbChronometre)
			panelChronometre.add(rb);

		/* ACTION */
		this.add(this.panelAction, BorderLayout.SOUTH);
		this.panelAction.add(this.btnAnnuler);
		this.panelAction.add(this.btnSuivant);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/

		/* INFO */
		this.ddlstRessource.addItemListener(this);
		for(JRadioButton rb : this.tabRbChronometre)
		{
			rb.addItemListener(this);
		}

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
		if(e.getSource() == this.btnAnnuler)
		{
			if(this.frame != null)
				this.frame.dispose();
		}

		if(e.getSource() == this.btnSuivant)
		{
			if(this.frame != null)
				this.frame.pageSuivante();
		}
	}

	/**
	 * Gère les changements d'état des éléments interactifs
	 * 
	 * @param e l'événement qui déclenche l'appel à cette méthode.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.ddlstRessource);
		{
			for(JRadioButton rb : this.tabRbChronometre)
				rb.setEnabled(true);
		}

		if(e.getSource() == this.tabRbChronometre[0] || e.getSource() ==  this.tabRbChronometre[1])
		{
			this.btnSuivant.setEnabled(true);
		}
	}

	/**
	 * Récupère la ressource sélectionnée
	 * 
	 * @return la ressource sélectionnée
	 */
	public Ressource getRessource() 
	{
		return (Ressource)this.ddlstRessource.getSelectedItem();
	}

	/**
	 * Récupère le code de la ressource sélectionnée
	 * 
	 * @return le code de la ressource sélectionnée
	 */
	public String getCodeRes()
	{
		Ressource ressource = (Ressource)this.ddlstRessource.getSelectedItem();

		return ressource != null ? ressource.getCode() : "";
	}

	/**
	 * Vérifie si le questionnare aura un chronomètre en fonction de l'état du bouton radio.
	 *
	 * @return true si le premier bouton radio (oui) est sélectionné, sinon (non) false.
	 */
	public boolean estChronometre() 
	{ 
		return this.tabRbChronometre[0].isSelected();         
	}

	/**
	 * Reinitialise la liste déroulantes des ressources
	 */
	public void reinitDdlstRessources()
	{
		this.ddlstRessource.removeAllItems();
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource);
		}
		this.ddlstRessource.setSelectedIndex(-1);
		this.ddlstRessource.setFocusable(false);
		this.ddlstRessource.setPrototypeDisplayValue(new Ressource("",String.format("%70s"," ")));
	}

	/**
	 * Désactive les boutons radio.
	 */
	public void reinitRbChronometre()
	{
		btgChronometre.clearSelection();
		for(JRadioButton rb : this.tabRbChronometre)
		{
			rb.setEnabled(false);
		}
		this.btnSuivant.setEnabled(false);
	}
}
