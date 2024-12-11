package ihm.edition.question;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import controleur.Controleur;
import metier.Notion;
import metier.Ressource;

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

	private JPanel[]          tabPanelInfo;
	private JPanel            panelAction;
	private JPanel            panelInfoProposition, panelInfoAjout;

	private JTextField        txtPoints, txtTemps;
	private JTextArea         txtQuestion;
	private JComboBox<String> ddlstRessource, ddlstNotion, ddlstTypeQuestion;
	private JButton           btnAnnuler, btnValider, btnPrecedent, btnSuivant,
	                          btnAjouterProposition, btnAjouterExplication;
	private JRadioButton[]    tabRbNiveau;
	private ButtonGroup       btgNiveau;
	
	/**
	 * Constructeur de la classe PanelEditionQuestion utilisé lorsqu'on veut créer une question.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditionQuestion(Controleur ctrl)
	{
		JPanel panelInfoPoints, panelInfoTemps, panelInfoRessource, 
		       panelInfoNotion, panelInfoNiveau, panelInfoTypeQuestion,
			   panelInfoQuestion;
		
		JPanel panelPoints, panelTemps, panelNiveau, panelAjout;

		//JScrollPane scrollPanelPropositions;

		this.ctrl = ctrl;

		this.setLayout(new BorderLayout(5, 5));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		/* Panel */
		// Info
		this.tabPanelInfo = new JPanel[2];
		for(int i = 0; i < this.tabPanelInfo.length; i++)
		{
			this.tabPanelInfo[i] = new JPanel();
			this.tabPanelInfo[i].setBorder(new EmptyBorder(10,10,10,10));
		}

		this.tabPanelInfo[0].setLayout(new GridBagLayout());
		this.tabPanelInfo[1].setLayout(new BorderLayout());

		panelInfoPoints       = new JPanel(new BorderLayout());
		panelInfoTemps        = new JPanel(new BorderLayout());
		panelInfoRessource    = new JPanel(new BorderLayout());
		panelInfoNotion       = new JPanel(new BorderLayout());
		panelInfoNiveau       = new JPanel(new BorderLayout());
		panelInfoTypeQuestion = new JPanel(new BorderLayout());

		panelInfoQuestion     = new JPanel(new BorderLayout());
		panelInfoQuestion.setBorder(new EmptyBorder(0, 0, 10, 0));

		this.panelInfoProposition = new JPanel(new GridLayout(0, 1, 5, 5));
		this.panelInfoAjout       = new JPanel(new BorderLayout());

		panelPoints       = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelTemps        = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelNiveau       = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAjout        = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// Action
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		/* Champs de texte */
		this.txtPoints = new JTextField(5);
		this.txtTemps  = new JTextField(5);

		this.txtQuestion = new JTextArea(); 
		this.txtQuestion.setRows(5);
		this.txtQuestion.setLineWrap(true);     
		this.txtQuestion.setWrapStyleWord(true);
		this.txtQuestion.setBorder(new LineBorder(Color.GRAY));
		this.txtQuestion.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtQuestion.setCaretColor(Color.BLACK);
		this.txtQuestion.setMargin(new Insets(2, 5, 2, 5));

		/* Listes déroulantes */
		// Ressource
		this.ddlstRessource = new JComboBox<String>();
		this.ddlstRessource.setPreferredSize(new Dimension(200, 25));
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource.getNom());
		}
		this.ddlstRessource.setSelectedIndex(-1);

		// Notion
		this.ddlstNotion = new JComboBox<String>();
		this.ddlstNotion.setPreferredSize(new Dimension(200, 25));
		for(Notion notion : this.ctrl.getNotions(this.ctrl.getRessourceActive()))
		{
			this.ddlstNotion.addItem(notion.getNom());
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
		
		this.btnAjouterProposition = new JButton("+");
		this.btnAjouterExplication = new JButton("Expl");

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
		
		/* ****** */
		/* Page 1 */
		/* ****** */

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets  = new Insets(0, 5, 10, 5);
 		gbc.anchor  = GridBagConstraints.WEST;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		/* Ligne 0 */
		// Points
		gbc.gridy = 0; gbc.gridx = 0; // (0,0)
		this.tabPanelInfo[0].add(panelInfoPoints, gbc);
		panelInfoPoints     .add(new JLabel("Nombre de Points :"), BorderLayout.NORTH);
		panelInfoPoints     .add(panelPoints, BorderLayout.CENTER);
		panelPoints         .add(this.txtPoints);

		// Temps
		gbc.gridy = 0; gbc.gridx = 1; // (0,1)
		this.tabPanelInfo[0].add(panelInfoTemps, gbc);
		panelInfoTemps      .add(new JLabel("Temps de Réponse (min:sec) :"), BorderLayout.NORTH);
		panelInfoTemps      .add(panelTemps, BorderLayout.CENTER);
		panelTemps          .add(this.txtTemps);

		gbc.gridy = 0; gbc.gridx = 2; // (0,1)
		this.tabPanelInfo[0].add(new JPanel());

		/* Ligne 1 */
		// Ressource
		gbc.gridy = 1; gbc.gridx = 0; // (1,0)
		this.tabPanelInfo[0].add(panelInfoRessource, gbc);
		panelInfoRessource  .add(new JLabel("Ressource :"), BorderLayout.NORTH);
		panelInfoRessource  .add(this.ddlstRessource, BorderLayout.CENTER);

		// Notion
		gbc.gridy = 1; gbc.gridx = 1; // (1,1)
		this.tabPanelInfo[0].add(panelInfoNotion, gbc);
		panelInfoNotion     .add(new JLabel("Notion :"), BorderLayout.NORTH);
		panelInfoNotion     .add(this.ddlstNotion, BorderLayout.CENTER);

		// Niveau
		gbc.gridy = 1; gbc.gridx = 2; // (1,2)
		this.tabPanelInfo[0].add(panelInfoNiveau, gbc);
		panelInfoNiveau     .add(new JLabel("Nombre de Niveau :"), BorderLayout.NORTH);
		panelInfoNiveau     .add(panelNiveau, BorderLayout.CENTER);
		for(JRadioButton rb : tabRbNiveau)
			panelNiveau.add(rb);

		/* Ligne 2 */
		// Type de Question
		gbc.gridy = 2; gbc.gridx = 0; // (2,2)
		gbc.gridwidth = 2;
		this.tabPanelInfo[0] .add(panelInfoTypeQuestion, gbc);
		panelInfoTypeQuestion.add(new JLabel("Type de Question :"), BorderLayout.NORTH);
		panelInfoTypeQuestion.add(this.ddlstTypeQuestion, BorderLayout.CENTER);

		/* ****** */
		/* Page 2 */
		/* ****** */

		this.tabPanelInfo[1].add(panelInfoQuestion, BorderLayout.NORTH);
		panelInfoQuestion   .add(new JLabel("Question :"), BorderLayout.NORTH);
		panelInfoQuestion   .add(this.txtQuestion, BorderLayout.CENTER);


		this.tabPanelInfo[1]     .add(this.panelInfoProposition, BorderLayout.CENTER);
		this.panelInfoProposition.add(this.panelInfoAjout);
		this.panelInfoAjout      .add(panelAjout);
		panelAjout               .add(this.btnAjouterProposition);
		panelAjout               .add(this.btnAjouterExplication);
		panelAjout               .add(new JPanel(), BorderLayout.WEST);
		panelAjout               .add(new JPanel(), BorderLayout.EAST);

		/* ************ */
		/* Panel Action */
		/* ************ */

		this.add(this.panelAction, BorderLayout.SOUTH);

		this.pagePrecedente();

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler           .addActionListener(this);
		this.btnValider           .addActionListener(this);
		this.btnPrecedent         .addActionListener(this);
		this.btnSuivant           .addActionListener(this);
		this.btnAjouterProposition.addActionListener(this);
		this.btnAjouterExplication.addActionListener(this);

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
		if(e.getSource() == this.btnAjouterProposition)
		{
			this.ajouterProposition();
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

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		
	}

	private boolean valider()
	{
		return true;
	}

	private void pageSuivante()
	{
		// Panel Info
		this.remove(this.tabPanelInfo[0]);
		this.add(this.tabPanelInfo[1], BorderLayout.CENTER);

		// Panel Action
		this.panelAction.removeAll();
		this.panelAction.add(this.btnPrecedent);
		this.panelAction.add(this.btnValider);

		this.revalidate();
		this.repaint();
	}

	private void pagePrecedente()
	{
		// Panel Info
		this.remove(this.tabPanelInfo[1]);
		this.add(this.tabPanelInfo[0], BorderLayout.CENTER);
		
		// Panel Action
		this.panelAction.removeAll();
		this.panelAction.add(this.btnAnnuler);
		this.panelAction.add(this.btnSuivant);

		this.revalidate();
		this.repaint();
	}

	private void ajouterProposition()
	{
		this.panelInfoProposition.remove(this.panelInfoAjout);

		PanelProposition panelProposition = new PanelProposition(this.panelInfoProposition);
		this.panelInfoProposition.add(panelProposition);
		this.panelInfoProposition.add(this.panelInfoAjout);

		revalidate();
	}

	private void ajouterExplication()
	{
		
	}
}
