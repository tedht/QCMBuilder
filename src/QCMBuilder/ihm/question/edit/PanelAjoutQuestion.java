package QCMBuilder.ihm.question.edit;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import QCMBuilder.ihm.IHM;
import QCMBuilder.ihm.question.edit.proposition.PanelProp;
import QCMBuilder.ihm.question.edit.proposition.PanelPropAssoc;
import QCMBuilder.ihm.question.edit.proposition.PanelPropElim;
import QCMBuilder.ihm.question.edit.proposition.PanelPropQCM;
import QCMBuilder.ihm.question.edit.proposition.PanelPropQRM;


/** 
 * Classe JPanel pour ajouter des éléments (intitulé, propositions...) à une nouvelle question.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelAjoutQuestion extends JPanel implements ActionListener, ItemListener
{
	protected FrameEditQuestion frame;
	
	protected JPanel            panelInfoScroll, panelInfoQuestion, panelInfoExpli,  panelInfoPJ, panelInfoAjout;
	protected JTextArea         txtIntitule, txtExpli;
	protected List<PanelProp>   lstPanelProp;
	protected JToggleButton     btnActiverExpli, btnActiverPJ;
	protected JButton           btnAjouterProp, btnAjouterPJ;
	protected JScrollPane	    scrollPanelInfo;
	protected ButtonGroup       btgReponse;
	protected JLabel            lblPJ;

	protected JButton           btnPrecedent, btnEnregistrer;

	/**
	 * Constructeur de la classe PanelAjoutQuestion.
	 * @param frame le frame d'édition de questions.
	 */
	public PanelAjoutQuestion(FrameEditQuestion frame) 
	{
		JPanel panelInfo, panelAction;
		JPanel panelAjout;
		JPanel panelBtnAjouterPJ;


		this.frame = frame;

		this.setLayout(new BorderLayout());

		this.lstPanelProp = new ArrayList<PanelProp>();

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		
		/* INFO */
		panelInfo = new JPanel(new BorderLayout());

		this.panelInfoQuestion = new JPanel(new BorderLayout());
		this.panelInfoQuestion.setBorder   (new EmptyBorder(0, 10, 0, 10));

		this.panelInfoExpli = new JPanel(new BorderLayout());
		this.panelInfoExpli.setBorder   (new EmptyBorder(10, 10, 0, 10));
		
		this.panelInfoPJ = new JPanel(new BorderLayout());
		this.panelInfoPJ.setBorder   (new EmptyBorder(0, 10, 0, 10));
		
		panelBtnAjouterPJ = new JPanel();

		this.panelInfoAjout = new JPanel(new BorderLayout());
		panelAjout          = new JPanel(new FlowLayout(FlowLayout.LEFT));

		this.txtIntitule = new JTextArea(); 
		this.txtIntitule.setRows         (3);
		this.txtIntitule.setLineWrap     (true);     
		this.txtIntitule.setWrapStyleWord(true);
		this.txtIntitule.setBorder       (new LineBorder(Color.GRAY));
		this.txtIntitule.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtIntitule.setCaretColor   (Color.BLACK);
		this.txtIntitule.setMargin       (new Insets(2, 5, 2, 5));
		
		this.txtExpli = new JTextArea(); 
		this.txtExpli.setRows         (3);
		this.txtExpli.setLineWrap     (true);     
		this.txtExpli.setWrapStyleWord(true);
		this.txtExpli.setBorder       (new LineBorder(Color.GRAY));
		this.txtExpli.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtExpli.setCaretColor   (Color.BLACK);
		
		this.txtExpli.setMargin       (new Insets(2, 5, 2, 5));

		this.btnAjouterProp = new JButton(IHM.getImgIconSVG("res/Ajouter.svg", 16, 16));
		this.btnAjouterProp.setPreferredSize(new Dimension(24, 24));

		this.btnActiverExpli = new JToggleButton("Ajouter une explication");

		this.btnActiverPJ = new JToggleButton(IHM.getImgIconSVG("res/AjouterPJ.svg", 16, 16));
		this.btnActiverPJ.setPreferredSize(new Dimension(24, 24));
		this.btnActiverPJ.setFocusable(false);

		this.btnAjouterPJ = new JButton(IHM.getImgIconSVG("res/AjouterPJ.svg", 16, 16));
		this.btnAjouterPJ.setPreferredSize(new Dimension(24, 24));
		this.btnAjouterPJ.setFocusable(false);

		this.panelInfoScroll = new JPanel(new GridBagLayout());
		this.scrollPanelInfo = new JScrollPane(this.panelInfoScroll);
		this.scrollPanelInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPanelInfo.getVerticalScrollBar().setUnitIncrement(16);
		this.scrollPanelInfo.getVerticalScrollBar().setBlockIncrement(32);
		this.scrollPanelInfo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPanelInfo.setBorder(new EmptyBorder(0,0,0,0));

		this.btgReponse = new ButtonGroup();

		this.lblPJ = new JLabel();
		
		/* ACTION */
		panelAction = new JPanel();
		panelAction.setBackground(new Color(200, 200, 250));
		this.btnPrecedent   = new JButton("Précédent");
		this.btnEnregistrer = new JButton("Enregistrer");   

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		
		/* INFO */
		this               .add(panelInfo, BorderLayout.CENTER);
		panelInfo          .add(this.scrollPanelInfo, BorderLayout.CENTER);

		panelInfoQuestion  .add(new JLabel("Question :"), BorderLayout.NORTH);
		panelInfoQuestion  .add(this.txtIntitule, BorderLayout.CENTER);

		panelInfoExpli     .add(new JLabel("Explication :"), BorderLayout.NORTH);
		panelInfoExpli     .add(this.txtExpli, BorderLayout.CENTER);

		panelInfoPJ        .add(new JLabel("Sélectionner une pièce jointe :"), BorderLayout.NORTH);
		panelInfoPJ        .add(this.lblPJ, BorderLayout.CENTER);
		panelInfoPJ        .add(panelBtnAjouterPJ, BorderLayout.WEST); 
		panelBtnAjouterPJ  .add(this.btnAjouterPJ);

		this.panelInfoAjout.add(panelAjout);
		panelAjout         .add(this.btnAjouterProp);
		panelAjout         .add(this.btnActiverPJ);
		panelAjout         .add(this.btnActiverExpli);

		/* ACTION */
		this               .add(panelAction, BorderLayout.SOUTH);
		panelAction        .add(this.btnPrecedent);
		panelAction        .add(this.btnEnregistrer);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/

		/* INFO */
		this.btnAjouterProp .addActionListener(this);
		this.btnActiverExpli.addItemListener  (this);
		this.btnActiverPJ   .addItemListener  (this);
		this.btnAjouterPJ   .addActionListener(this);

		/* ACTION */
		this.btnEnregistrer.addActionListener(this);
		this.btnPrecedent  .addActionListener(this);
	}

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnEnregistrer) { this.enregistrer         (); }
		if(e.getSource() == this.btnPrecedent)   { this.frame.pagePrecedente(); }
		if(e.getSource() == this.btnAjouterProp) { this.ajouterProposition  (); }
		if(e.getSource() == this.btnAjouterPJ)   { this.ajouterPJ  (); }
	}

	/**
	 * Gère les changements d'état des éléments interactifs
	 * 
	 * @param e l'événement qui déclenche l'appel à cette méthode.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.btnActiverExpli)       
		{ 
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				this.txtExpli.setText("");
			}
			this.afficher(); 
		}
		if(e.getSource() == this.btnActiverPJ) 
		{ 
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				this.lblPJ.setText("");
			}
			this.afficher(); 
		}
	}


	/**
	 * Méthode pour enregistrer une nouvelle question.
	 */
	public void enregistrer()
	{
		this.frame.enregistrer();
	}

	/**
	 * Ajoute un panel de proposition en fonction du type de question choisi.
	 */
	public void ajouterProposition()
	{
		PanelProp panelProp;


		panelProp = null;

		switch (this.frame.getIndexTypeQuestion()) {
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
			this.afficher();
		}
	}

	/**
	 * Supprime un panel de proposition
	 * @param panelProp le panel de proposition qu'on veut supprimer
	 */
	public void supprimerProposition(PanelProp panelProp)
	{
		this.lstPanelProp.remove(panelProp);

		this.afficher();
	}

	/**
	 * Permet de choisir et ajouter une pièce jointe
	 */
	public void ajouterPJ()
	{
		JFileChooser fileChooser;

		int result;

		File PJ;


		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choisissez une pièce Jointe");
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) 
			{
				if (file.isDirectory()) 
				{
					return true;
				}

				String fileName = file.getName().toLowerCase();
				return fileName.endsWith(".pdf")  ||
					   fileName.endsWith(".mp3")  ||
					   fileName.endsWith(".mp4")  ||
					   fileName.endsWith(".jpg")  ||
					   fileName.endsWith(".jpeg") ||
					   fileName.endsWith(".png")  ||
					   fileName.endsWith(".gif");
			}

			@Override
			public String getDescription() 
			{
				return "Fichiers PDF, Audio, Vidéo, et Images (*.pdf, *.mp3, *.mp4, *.jpg, *.jpeg, *.png, *.gif)";
			}
		});

		result = fileChooser.showOpenDialog(frame);

		if (result == JFileChooser.APPROVE_OPTION) {
			PJ = fileChooser.getSelectedFile();
			this.lblPJ.setText(PJ.getAbsolutePath());
		}

	}

	/**
	 * Méthode qui gère l'affichage d'une question, c'est-à-dire l'intitulé, l'explication,
	 * la pièce jointe et les propositions
	 */
	public void afficher()
	{
		GridBagConstraints gbc;

		int cpt;


		this.panelInfoScroll.removeAll();
		
		gbc = new GridBagConstraints();
		gbc.insets  = new Insets(0, 5, 5, 25);
		gbc.gridx   = 0;
		gbc.anchor  = GridBagConstraints.NORTH;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;

		cpt   = 0;
		gbc.gridy = cpt++;
		this.panelInfoScroll.add(this.panelInfoQuestion, gbc);

		if(this.btnActiverPJ.isSelected())
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(this.panelInfoPJ, gbc);
		}

		for(PanelProp panelProp : this.lstPanelProp)
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(panelProp, gbc);
		}

		if(this.btnActiverExpli.isSelected())
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

	/**
	 * Ajoute un bouton radio au ButtonGroupe qui gère les réponses correctes.
	 * Cette méthode est utilisée lorsqu'on s'occupe de questons QCM et élimination.
	 * 
	 * @param rbReponse le bouton radio qu'on veut ajouter.
	 */
	public void ajouterRbResponse(JRadioButton rbReponse)
	{
		this.btgReponse.add(rbReponse);
	}
	/**
	 * Supprime un bouton radio du ButtonGroup qui gère les réponses correctes.
	 * Cette méthode est utilisée lorsqu'on s'occupe de questons QCM et élimination.
	 * 
	 * @param rbReponse le bouton radio qu'on veut supprimer.
	 */
	public void supprimerRbResponse(JRadioButton rbReponse) 
	{
		this.btgReponse.remove(rbReponse);
	}

	/**
	 * Récupère l'intitulé.
	 * 
	 * @return l'intitulé
	 */
	public String getIntitule() 
	{
		return this.txtIntitule.getText();
	}

	/**
	 * Vérifie l'option pour saisir une explication est sélectionnée
	 * 
	 * @return true si l'option est sélectionnée, false sinon
	 */
	public boolean explicationSelected()
	{
		return this.btnActiverExpli.isSelected();
	}

	/**
	 * Récupère l'explication.
	 * 
	 * @return l'explication
	 */
	public String getExplication() 
	{
		return this.txtExpli.getText();
	}

	/**
	 * Vérifie l'option pour saisir une explication est sélectionnée
	 * 
	 * @return true si l'option est sélectionnée, false sinon
	 */
	public boolean pieceJointeSelected()
	{
		return this.btnActiverPJ.isSelected();
	}

	/**
	 * Récupère le chemin de la pièce jointe.
	 * 
	 * @return le chemin de la pièce jointe.
	 */
	public String getPieceJointe()
	{
		return this.lblPJ.getText();
	}

	/**
	 * Récupère la liste des panels de proposition
	 * 
	 * @return la liste des panels de proposition
	 */
	public List<PanelProp> getPanelProps() 
	{
		return this.lstPanelProp;
	}

	/**
	 * Supprime toutes les panels de proposition.
	 */
	public void clearPanelProp() 
	{
		this.lstPanelProp.clear();
	}
}
