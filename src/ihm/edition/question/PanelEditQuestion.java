package ihm.edition.question;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.List;
import java.util.ArrayList;

import controleur.Controleur;
import ihm.IHM;
import ihm.edition.question.proposition.PanelProp;
import ihm.edition.question.proposition.PanelPropAssoc;
import ihm.edition.question.proposition.PanelPropElim;
import ihm.edition.question.proposition.PanelPropQRM;
import ihm.edition.question.proposition.PanelPropQCM;

import metier.entite.Ressource;
import metier.entite.Notion;

/**
 * Classe JPanel de la fenêtre d'édition d'une question
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelEditQuestion extends JPanel implements ActionListener
{
	private Controleur        ctrl;
	private FrameEditQuestion frame;

	private JPanel[]          tabPanelInfo;
	private JPanel            panelAction;

	/* Page 1 */
	private JTextField        txtPoints, txtTemps;
	private JComboBox<String> ddlstRessource, ddlstNotion, ddlstTypeQuestion;

	private JRadioButton[]    tabRbDifficulte;
	private ButtonGroup       btgDifficulte;
	private ButtonGroup       btgQCM;

	/* Page 2 */
	private JPanel            panelInfoScroll, panelInfoQuestion, panelInfoExpli, panelInfoAjout;
	private JTextArea         txtQuestion, txtExpli;
	private List<PanelProp>   lstPanelProp;
	private JToggleButton     btnAjouterExpli;
	private JButton           btnAjouterProp;
	private JScrollPane	      scrollPanelInfo;

	/* Panel Action */
	private JButton           btnAnnuler, btnEnregistrer, btnPrecedent, btnSuivant;

	/**
	 * Constructeur de la classe PanelEditQuestion utilisé lorsqu'on veut créer une question.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditQuestion(Controleur ctrl, FrameEditQuestion frame)
	{
		JPanel panelInfoPoints, panelInfoTemps, panelInfoRessource, 
               panelInfoNotion, panelInfoNiveau, panelInfoTypeQuestion;
 
 		JPanel panelPoints, panelTemps, panelNiveau, panelAjout;
		
		this.ctrl   = ctrl;
		this.frame  = frame;

		this.tabPanelInfo = new JPanel[2];
		this.setLayout(new BorderLayout());

		this.lstPanelProp = new ArrayList<PanelProp>();

		/**************/
		/*   PAGE 1   */
		/**************/

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.tabPanelInfo[0] = new JPanel(new GridBagLayout());
		this.tabPanelInfo[0].setBorder(new EmptyBorder(10,10,10,10));

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
		this.txtTemps  = new JTextField(5);

		/* Listes déroulantes */
		// Ressource
		this.ddlstRessource = new JComboBox<String>();
		this.ddlstRessource.setPrototypeDisplayValue("R1.01 Initiation au développement");
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource.getNom());
		}
		this.ddlstRessource.setSelectedIndex(-1);

		// Notion
		this.ddlstNotion = new JComboBox<String>();
		this.ddlstRessource.setPrototypeDisplayValue("R1.01 Initiation au développement");
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

		/* Boutons Radio */
		tabRbDifficulte = new JRadioButton[4];
		tabRbDifficulte[0] = new JRadioButton("TF");
		tabRbDifficulte[1] = new JRadioButton("F");
		tabRbDifficulte[2] = new JRadioButton("M");
		tabRbDifficulte[3] = new JRadioButton("D");

		btgDifficulte = new ButtonGroup();
		for(JRadioButton rb : tabRbDifficulte)
		{
			btgDifficulte.add(rb);
			rb.setEnabled(false);
		}

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/

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
		for(JRadioButton rb : tabRbDifficulte)
			panelNiveau.add(rb);

		/* Ligne 2 */
		// Type de Question
		gbc.gridy = 2; gbc.gridx = 0; // (2,2)
		gbc.gridwidth = 2;
		this.tabPanelInfo[0] .add(panelInfoTypeQuestion, gbc);
		panelInfoTypeQuestion.add(new JLabel("Type de Question :"), BorderLayout.NORTH);
		panelInfoTypeQuestion.add(this.ddlstTypeQuestion, BorderLayout.CENTER);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		for(JRadioButton rb : tabRbDifficulte)
			rb.addActionListener(this);

		this.ddlstRessource   .addActionListener(this);
		this.ddlstNotion      .addActionListener(this);
		this.ddlstTypeQuestion.addActionListener(this);

		/**************/
		/*   PAGE 2   */
		/**************/

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.tabPanelInfo[1] = new JPanel(new BorderLayout());

		panelInfoQuestion = new JPanel(new BorderLayout());
		panelInfoQuestion.setBorder(new EmptyBorder(10, 10, 10, 10));

		panelInfoExpli = new JPanel(new BorderLayout());
		panelInfoExpli.setBorder(new EmptyBorder(10, 10, 10, 10));

		this.panelInfoAjout = new JPanel(new BorderLayout());
		panelAjout          = new JPanel(new FlowLayout(FlowLayout.LEFT));

		this.txtQuestion = new JTextArea(); 
		this.txtQuestion.setRows         (3);
		this.txtQuestion.setLineWrap     (true);     
		this.txtQuestion.setWrapStyleWord(true);
		this.txtQuestion.setBorder       (new LineBorder(Color.GRAY));
		this.txtQuestion.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtQuestion.setCaretColor   (Color.BLACK);
		this.txtQuestion.setMargin       (new Insets(2, 5, 2, 5));
		
		this.txtExpli = new JTextArea(); 
		this.txtExpli.setRows         (3);
		this.txtExpli.setLineWrap     (true);     
		this.txtExpli.setWrapStyleWord(true);
		this.txtExpli.setBorder       (new LineBorder(Color.GRAY));
		this.txtExpli.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtExpli.setCaretColor   (Color.BLACK);
		this.txtExpli.setMargin       (new Insets(2, 5, 2, 5));

		this.btnAjouterProp  = new JButton("+");
		this.btnAjouterExpli = new JToggleButton("Expl");

		this.panelInfoScroll = new JPanel(new GridBagLayout());
		this.scrollPanelInfo = new JScrollPane(this.panelInfoScroll);
        this.scrollPanelInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPanelInfo.getVerticalScrollBar().setUnitIncrement(16);
		this.scrollPanelInfo.getVerticalScrollBar().setBlockIncrement(32);
		this.scrollPanelInfo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPanelInfo.setBorder(new EmptyBorder(0,0,0,0));

		this.btgQCM = new ButtonGroup();

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.tabPanelInfo[1].add(this.scrollPanelInfo, BorderLayout.CENTER);

		panelInfoQuestion   .add(new JLabel("Question :"), BorderLayout.NORTH);
		panelInfoQuestion   .add(this.txtQuestion, BorderLayout.CENTER);

		panelInfoExpli      .add(new JLabel("Explication :"), BorderLayout.NORTH);
		panelInfoExpli      .add(this.txtExpli, BorderLayout.CENTER);

		this.panelInfoAjout .add(panelAjout);
		panelAjout          .add(this.btnAjouterProp);
		panelAjout          .add(this.btnAjouterExpli);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAjouterProp.addActionListener(this);
		this.btnAjouterExpli.addActionListener(this);

		/**************/
		/*   ACTION   */
		/**************/

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		this.btnAnnuler     = new JButton("Annuler");
		this.btnEnregistrer = new JButton("Enregistrer");   
		this.btnPrecedent   = new JButton("Précédent");
		this.btnSuivant     = new JButton("Suivant");       this.btnSuivant.setEnabled(false);
  
		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.panelAction, BorderLayout.SOUTH);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler    .addActionListener(this);
		this.btnEnregistrer.addActionListener(this);
		this.btnPrecedent  .addActionListener(this);
		this.btnSuivant    .addActionListener(this);

		/* Affiche la 1ère page */
		this.pagePrecedente();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAnnuler)
		{
			this.frame.dispose();
		}

		if(e.getSource() == this.btnEnregistrer)
		{
			this.enregistrer();
		}

		if(e.getSource() == this.btnSuivant)     { this.pageSuivante        (); }
		if(e.getSource() == this.btnPrecedent)   { this.pagePrecedente      (); }
		if(e.getSource() == this.btnAjouterProp) { this.ajouterProposition  (); }

		if(e.getSource() == this.btnAjouterExpli) 
		{
			this.afficherPropositions();
		}

		if(e.getSource() instanceof JComboBox)
		{
			if(e.getSource() == this.ddlstRessource)
			{
				this.ddlstNotion.removeAllItems();
				for(Notion notion : this.ctrl.getNotions(this.ctrl.getRessource((String)this.ddlstRessource.getSelectedItem())))
				{
					this.ddlstNotion.addItem(notion.getNom());
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
				this.lstPanelProp.clear();
			}
		}

		if(e.getSource() instanceof JRadioButton)
		{
			this.ddlstTypeQuestion.setEnabled(true);
		}
	
	}

	private void enregistrer()
	{
		String detailsQuestion = "";
		String sProp           = "";

		detailsQuestion += (String)this.ddlstRessource .getSelectedItem() + '\t';
		detailsQuestion += (String)this.ddlstNotion    .getSelectedItem() + '\t';
		
		for(int i = 0; i < this.tabRbDifficulte.length; i++)
			if(tabRbDifficulte[i].isSelected()) 
				detailsQuestion += i + "\t";
		
		detailsQuestion += this.txtTemps   .getText() + '\t';
		detailsQuestion += this.txtPoints  .getText() + '\t';
		detailsQuestion += this.txtQuestion.getText() + '\t';
		detailsQuestion += this.txtExpli   .getText() + '\t';

		// On vérifie DANS LE METIER que les valeurs saisies sont valides
		List<String> lstErreurs = new ArrayList<String>();


		switch (this.ddlstTypeQuestion.getSelectedIndex()) 
		{
			case 0  -> // Question à Choix Multiple à Réponse Unique
			{
				for(PanelProp panelProp : this.lstPanelProp)
				{
					PanelPropQCM panelPropQCM = (PanelPropQCM) panelProp;
					sProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					sProp += panelPropQCM.getText() + '\t';
					detailsQuestion += sProp;
				}
				lstErreurs = this.ctrl.creerQCM(detailsQuestion, true);
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				for(PanelProp panelProp : this.lstPanelProp)
				{
					PanelPropQCM panelPropQCM = (PanelPropQCM) panelProp;
					sProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					sProp += panelPropQCM.getText() + '\t';
					detailsQuestion += sProp;
				}
				lstErreurs = this.ctrl.creerQCM(detailsQuestion, false);
			} 
			case 2  -> // Question à Association d'Eléments
			{
				for(PanelProp panelProp : this.lstPanelProp)
				{
					PanelPropAssoc panelPropAssoc = (PanelPropAssoc) panelProp;
					sProp =  panelPropAssoc.getTextGauche() + '\t';
					sProp += panelPropAssoc.getTextDroite() + '\t';
					detailsQuestion += sProp;
				}
				lstErreurs = this.ctrl.creerAssociation(detailsQuestion);
			} 
			case 3  -> // Question avec Elimination de Propositions de Réponses
			{
				for(PanelProp panelProp : this.lstPanelProp)
				{
					PanelPropElim panelPropElim = (PanelPropElim) panelProp;
					sProp = panelPropElim.estReponse() ? "V:" : "F:";
					if(!panelPropElim.estReponse()) 
					{
						sProp += panelPropElim.getOrdreElim    () + ":";
						sProp += panelPropElim.getPointsEnMoins() + ":";
					}
					detailsQuestion += sProp;
					sProp = panelPropElim.getText() + '\t';
				}
				lstErreurs = this.ctrl.creerElimination(detailsQuestion);
			} 
			default -> {}
		}
		
		//lstErreurs.add("raison 1");
		//lstErreurs.add("raison 2");
		if(lstErreurs.size() == 0)
		{
			JOptionPane.showMessageDialog(
				this.frame,
				"La question a été enregistrée avec succès !",
				"Enregistrement Réussi",
				JOptionPane.INFORMATION_MESSAGE
			);

			this.frame.dispose();
		}
		else
		{
			String message = "La question n'a pas été enregistrée pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this.frame,
				message,
				"Échec de l'Enregistrement",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	public void pagePrecedente()
	{
		if(this.frame != null)
			this.frame.setSize(IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_1);
		
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

	public void pageSuivante()
	{	
		if(this.frame != null)
			this.frame.setSize(IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_2);

		// Panel Info
		this.remove(this.tabPanelInfo[0]);
		this.add(this.tabPanelInfo[1], BorderLayout.CENTER);
		this.afficherPropositions();

		// Panel Action
		this.panelAction.removeAll();
		this.panelAction.add(this.btnPrecedent);
		this.panelAction.add(this.btnEnregistrer);

		this.revalidate();
		this.repaint();
	}

	public void ajouterProposition()
	{
		this.ddlstTypeQuestion.getSelectedItem();

		PanelProp panelProp = null;

		switch (this.ddlstTypeQuestion.getSelectedIndex()) {
			case 0  -> // Question à Choix Multiple à Réponse Unique
			{
				panelProp = new PanelPropQCM(this);
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				panelProp = new PanelPropQRM(this);
			} 
			case 2  -> // Question à Association d'Eléments
			{
				panelProp = new PanelPropAssoc(this);
			} 
			case 3  -> // Question avec Elimination de Propositions de Réponses
			{
				panelProp = new PanelPropElim(this);
			} 
			default -> {}
		}

		if(panelProp != null) 
		{
			this.lstPanelProp.add(panelProp);
			this.afficherPropositions();
		}
	}

	public void supprimerProposition(PanelProp panelProp)
	{
		this.lstPanelProp.remove(panelProp);

		this.afficherPropositions();
	}

	public void afficherPropositions()
	{
		this.panelInfoScroll.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets  = new Insets(0, 5, 5, 25);
		gbc.gridx   = 0;
		gbc.anchor  = GridBagConstraints.NORTH;
	    gbc.fill    = GridBagConstraints.HORIZONTAL;
	    gbc.weightx = 1.0;
		gbc.weighty = 0.0;

		int cpt   = 0;
		gbc.gridy = cpt++;
		this.panelInfoScroll.add(this.panelInfoQuestion, gbc);

		for(PanelProp panelProp : this.lstPanelProp)
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(panelProp, gbc);
		}

		if(this.btnAjouterExpli.isSelected())
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(this.panelInfoExpli, gbc);
		}

		gbc.gridy = cpt;
		this.panelInfoScroll.add(this.panelInfoAjout, gbc);

		gbc.weighty = 1.0;
		this.panelInfoScroll.add(new JPanel(), gbc);

		this.revalidate();
		this.repaint();
	}

	public void ajouterRbResponse(JRadioButton rbReponse)
	{
		this.btgQCM.add(rbReponse);
	}
}
