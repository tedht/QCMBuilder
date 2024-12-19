package ihm.questionnaire;

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

import controleur.Controleur;
import metier.entite.Ressource;

/**
 * Classe représentant la fenêtre de création d'une évaluation
 * 
 * @author Ted Herambert
 * @date 2024/12/16
 * @version 1.0
 */
public class PanelParametresQuestionnaire extends JPanel implements ActionListener, ItemListener
{
	private Controleur                ctrl;
	private FrameGestionQuestionnaire frame;

	private JPanel                    panelInfo, panelAction;

	private JComboBox<Ressource>      ddlstRessource;

	private JRadioButton[]            tabRbChronometre;
	private ButtonGroup               btgChronometre;

	private JButton                   btnAnnuler, btnSuivant;

	public PanelParametresQuestionnaire(Controleur ctrl, FrameGestionQuestionnaire frame) 
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

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.ddlstRessource);
		{
			for(JRadioButton rb : this.tabRbChronometre)
				rb.setEnabled(true);

			this.frame.majTabNotions();
		}

		if(e.getSource() == this.tabRbChronometre[0] || e.getSource() ==  this.tabRbChronometre[1])
		{
			this.btnSuivant.setEnabled(true);
		}
	}

	public Ressource getRessource() 
	{
		return (Ressource)this.ddlstRessource.getSelectedItem();
	}

	public boolean estChronometree() 
	{ 
		return this.tabRbChronometre[0].isSelected();         
	}
}
