package ihm.edition.question;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controleur.Controleur;

/**
 * Classe JPanel de la fenêtre d'édition d'une question
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelEditionQuestion extends JPanel implements ActionListener, ItemListener
{
	private Controleur ctrl;

	private JPanel            panelInfo, panelAction, panelInfoNiveau;
	private JTextField        txtPoints, txtTemps;
	private JTextArea         txtQuestion;
	private JComboBox<String> ddlstRessource, ddlstNotion, ddlstTypeQuestion;
	private JButton           btnAnnuler, btnValider, btnPrecedent, btnSuivant;
	private JRadioButton[]    tabRbNiveau;
	private ButtonGroup       btgNiveau;
	
	/**
	 * Constructeur de la classe PanelEditionQuestion utilisé lorsqu'on veut créer une question.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditionQuestion(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		/* Panel */
		// Info
		this.panelInfo = new JPanel(new GridBagLayout());
		this.panelInfo.setBorder(new EmptyBorder(10,10,10,10));
		//this.panelInfo.setOpaque(false);
		
		// Action
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		// Niveau
		this.panelInfoNiveau = new JPanel();

		/* Champs de texte */
		this.txtPoints = new JTextField(5);
		this.txtTemps  = new JTextField(5);

		this.txtQuestion = new JTextArea();
		this.txtQuestion.setBorder(new LineBorder(Color.GRAY));
		this.txtQuestion.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtQuestion.setCaretColor(Color.BLACK);
		this.txtQuestion.setMargin(new Insets(2, 5, 2, 5));

		/* Listes déroulantes */
		// Ressource
		this.ddlstRessource = new JComboBox<String>();
		this.ddlstRessource.setPreferredSize(new Dimension(200, 25));
		for(String ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource);
		}
		this.ddlstRessource.setSelectedIndex(-1);

		// Notion
		this.ddlstNotion = new JComboBox<String>();
		this.ddlstNotion.setPreferredSize(new Dimension(200, 25));
		for(String notion : this.ctrl.getNotions(this.ctrl.getRessourceActive()))
		{
			this.ddlstNotion.addItem(notion);
		}
		this.ddlstNotion.setEnabled(false);
		this.ddlstNotion.setSelectedIndex(-1);

		// Type de Question 
		this.ddlstTypeQuestion = new JComboBox<String>();
		this.ddlstTypeQuestion.addItem("Question à Choix Multiple à Réponse Unique");
		this.ddlstTypeQuestion.addItem("Question à Choix Multiple à Réponse Multiple");
		this.ddlstTypeQuestion.addItem("Question à Association d'Eléments");
		this.ddlstTypeQuestion.addItem("Question avec Elimination de Propositions de Réponses");
		this.ddlstTypeQuestion.setEnabled(false);
		this.ddlstTypeQuestion.setSelectedIndex(-1);

		/* Boutons */
		this.btnAnnuler   = new JButton("Annuler");
		this.btnValider   = new JButton("Valider");   //this.btnValider.setEnabled(false);
		this.btnPrecedent = new JButton("Précédent");
		this.btnSuivant   = new JButton("Suivant");   //this.btnSuivant.setEnabled(false);

		/* Boutons Radio */
		tabRbNiveau = new JRadioButton[4];
		tabRbNiveau[0] = new JRadioButton("TF");
		tabRbNiveau[1] = new JRadioButton("F");
		tabRbNiveau[2] = new JRadioButton("M");
		tabRbNiveau[3] = new JRadioButton("D");

		btgNiveau = new ButtonGroup();
		for(JRadioButton rb : tabRbNiveau)
		{
			btgNiveau.add(rb);
			rb.setEnabled(false);
		}

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		/* Panel Info */
		this.add(this.panelInfo, BorderLayout.CENTER);
		this.add(this.panelAction, BorderLayout.SOUTH);

		this.pagePrecedente();

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler  .addActionListener(this);
		this.btnValider  .addActionListener(this);
		this.btnPrecedent.addActionListener(this);
		this.btnSuivant  .addActionListener(this);

		for(JRadioButton rb : tabRbNiveau)
			rb.addActionListener(this);

		this.ddlstRessource   .addActionListener(this);
		this.ddlstNotion      .addActionListener(this);
		this.ddlstTypeQuestion.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAnnuler)
		{
			SwingUtilities.getWindowAncestor(this).dispose();
		}
		if(e.getSource() == this.btnValider)
		{
			if(this.valider()) 
				SwingUtilities.getWindowAncestor(this).dispose();
		}
		if(e.getSource() == this.btnSuivant)
		{
			this.pageSuivante();
		}
		if(e.getSource() == this.btnPrecedent)
		{
			this.pagePrecedente();
		}

		if(e.getSource() instanceof JComboBox)
		{
			if(e.getSource() == this.ddlstRessource)
			{
				this.ddlstNotion.setEnabled(true);
			}
	
			if(e.getSource() == this.ddlstNotion)
			{
				for(JRadioButton rb : tabRbNiveau)
					rb.setEnabled(true);
			}

			if(e.getSource() == this.ddlstTypeQuestion)
			{
				this.btnSuivant.setEnabled(true);
			}
		}

		if(e.getSource() instanceof JRadioButton)
		{
			this.ddlstTypeQuestion.setEnabled(true);
		}
	}

	private boolean valider()
	{
		return true;
	}

	private void pageSuivante()
	{
		this.panelInfo  .removeAll();
		this.panelAction.removeAll();

		/* Panel Info */
		GridBagConstraints gbc = new GridBagConstraints();

		// Ligne 0
		gbc.gridy = 0; gbc.gridx = 0; // (0,0)
		gbc.insets    = new Insets(0, 5, 0, 5);
 		gbc.anchor    = GridBagConstraints.WEST;
		gbc.fill      = GridBagConstraints.HORIZONTAL;
		gbc.weightx   = 1.0;
		gbc.gridwidth = 3;
		this.panelInfo.add(new JLabel("Question :"), gbc);

		// Ligne 1
		gbc.gridy = 1; gbc.gridx = 0; // (1,0)
		gbc.insets = new Insets(0, 5, 10, 5);
		this.panelInfo.add(this.txtQuestion, gbc);

		/* Panel Action */
		this.panelAction.add(this.btnPrecedent);
		this.panelAction.add(this.btnValider);

		this.revalidate();
		this.repaint();
	}

	private void pagePrecedente()
	{
		this.panelInfo  .removeAll();
		this.panelAction.removeAll();

		/* Panel Info */
		GridBagConstraints gbc = new GridBagConstraints();

		// Ligne 0
		gbc.gridy = 0; gbc.gridx = 0; // (0,0)
		gbc.insets  = new Insets(0, 5, 0, 5);
 		gbc.anchor  = GridBagConstraints.WEST;
		this.panelInfo.add(new JLabel("Nombre de Points :"), gbc);

		gbc.gridx = 1; // (0,1)
		this.panelInfo.add(new JLabel("Temps de Réponse (min:sec) :"), gbc);

		gbc.gridx = 2; // (0,2)
		this.panelInfo.add(new JLabel(" "), gbc);

		// Ligne 1 
		gbc.gridy = 1; gbc.gridx = 0; // (1,0)
		gbc.insets  = new Insets(0, 5, 10, 5);
		this.panelInfo.add(this.txtPoints, gbc);

		gbc.gridx = 1;// (1,1)
		this.panelInfo.add(this.txtTemps, gbc);

		// Ligne 2
		gbc.gridy = 2; gbc.gridx = 0; // (2,0)
		gbc.insets  = new Insets(0, 5, 0, 5);
		this.panelInfo.add(new JLabel("Ressource :"), gbc);

		gbc.gridx = 1; // (2,1)
		this.panelInfo.add(new JLabel("Notion :"), gbc);

		gbc.gridx = 2; // (2,1)
		this.panelInfo.add(new JLabel("Niveau :"), gbc);

		// Ligne 3
		gbc.gridy = 3; gbc.gridx = 0; // (3,0)
		gbc.insets  = new Insets(0, 5, 10, 5);
		this.panelInfo.add(this.ddlstRessource, gbc);

		gbc.gridx = 1; // (3,1)
		this.panelInfo.add(this.ddlstNotion, gbc);

		gbc.gridx = 2; // (3,2)
		this.panelInfo.add(this.panelInfoNiveau, gbc);

		for(JRadioButton rb : tabRbNiveau)
			this.panelInfoNiveau.add(rb);

		// Ligne 4
		gbc.gridy = 4; gbc.gridx = 0; // (4,0)
		gbc.insets    = new Insets(0, 5, 0, 5);
		gbc.gridwidth = 2;
		this.panelInfo.add(new JLabel("Type de Question :"), gbc);

		// Ligne 5
		gbc.gridy = 5; gbc.gridx = 0; // (5,0)
		gbc.insets  = new Insets(0, 5, 10, 5);
		this.panelInfo.add(this.ddlstTypeQuestion, gbc);
		
		/* Panel Action */
		this.panelAction.add(this.btnAnnuler);
		this.panelAction.add(this.btnSuivant);

		this.revalidate();
		this.repaint();
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		
	}
}
