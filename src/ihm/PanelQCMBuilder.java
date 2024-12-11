package ihm;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import controleur.Controleur;
import ihm.carte.PanelCarte;
import ihm.carte.PanelCarteNotion;
import ihm.carte.PanelCarteQuestion;
import ihm.carte.PanelCarteRessource;
import metier.Notion;
import metier.Question;
import metier.Ressource;

/**
 * Classe JPanel qui contient les composents de la fenêtre de l'application.
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class PanelQCMBuilder extends JPanel implements ActionListener
{
	private Controleur ctrl;

	private List<PanelCarte> lstPanelCartes;

	private JPanel panelEntete;
	private JPanel panelContenu;
	private JPanel panelBtnAjouter;

	private JButton btnRetour, btnGenererQuestionnaire, btnAjouter;
	private JLabel  lblTitre, lblSousTitre, lblFilAriane;

	private JScrollPane scrollPanelContenu;

	/**
	 * Constructeur de la classe PanelQCMBuilder.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelQCMBuilder(Controleur ctrl)
	{
		this.ctrl = ctrl;



		this.setLayout(new BorderLayout());
		
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		/* Panels */
		this.panelEntete     = new JPanel(new GridBagLayout());
		this.panelContenu    = new JPanel();
		this.panelBtnAjouter = new JPanel(new GridBagLayout());

		this.panelEntete .setBorder(new EmptyBorder(10,20,10,20));
		this.panelContenu.setBorder(new EmptyBorder(20,20,20,20));

		this.lstPanelCartes = new ArrayList<PanelCarte>();

		this.scrollPanelContenu = new JScrollPane(this.panelContenu);
        this.scrollPanelContenu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPanelContenu.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPanelContenu.getVerticalScrollBar().setUnitIncrement(16);
		this.scrollPanelContenu.getVerticalScrollBar().setBlockIncrement(32);
		
		/* Boutons */
		this.btnRetour               = new JButton("Retour");
		this.btnGenererQuestionnaire = new JButton("Générer une Questionnaire");
		this.btnAjouter              = new JButton(" ");

		/* Labels */
		this.lblFilAriane = new JLabel(" ");
		this.lblTitre     = new JLabel(" ");
		this.lblSousTitre = new JLabel(" ");

		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 32));
		
		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		
		/*
		 * /!\
		 * gbc.gridx -> LIGNE
		 * gbc.gridy -> COLONNE
		 * 
		 */

		/* Entête */
		this.add(this.panelEntete,  BorderLayout.NORTH);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 20, 0);

		gbc.gridx = 0; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
        this.panelEntete.add(this.btnRetour, gbc);

		gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		this.panelEntete.add(new JLabel(" "), gbc);

        gbc.gridx = 2; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
        this.panelEntete.add(this.btnGenererQuestionnaire, gbc);

		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 0, 0, 0);

		gbc.gridx = 0; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
		this.panelEntete.add(this.lblFilAriane, gbc);

		gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
		this.panelEntete.add(this.lblTitre, gbc);

		gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
		this.panelEntete.add(this.lblSousTitre, gbc);

		/* Contenu */
		this.add(this.scrollPanelContenu, BorderLayout.CENTER);

		// Bouton Ajouter
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		this.panelBtnAjouter.add(this.btnAjouter, gbc);

		//Affichage des Ressources
		this.afficherRessources();

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnRetour.addActionListener(this); 
		this.btnAjouter.addActionListener(this); 
		this.btnGenererQuestionnaire.addActionListener(this); 
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnRetour)
		{
			this.ctrl.retour();
		}
		if(e.getSource() == this.btnGenererQuestionnaire)
		{

		}
		if(e.getSource() == this.btnAjouter)
		{
			this.ctrl.ajouterElement();
		}
	}

	private void reinitContenu()
	{
		for(PanelCarte panelCarte : this.lstPanelCartes)
		{
			this.panelContenu.remove(panelCarte);
		}
		this.lstPanelCartes.clear();
		this.panelContenu.remove(this.panelBtnAjouter);

		this.revalidate();
		this.repaint();
	}


	public void afficherRessources() 
	{
		System.out.println("afficher lstRessources");
		/* Entête */
		this.btnRetour.setEnabled(false);

		this.lblFilAriane.setText("Ressources");
		this.lblTitre    .setText("Ressources");
		this.lblSousTitre.setText(this.ctrl.getRessources().size() + " ressource(s)");

		this.btnAjouter.setText("Créer une Nouvelle Ressource");
		
		/* Contenu */
		this.reinitContenu();

		this.panelContenu.setLayout(new GridLayout(0,2, 30,30));
		for(Ressource ressource : this.ctrl.getRessources())
		{
			PanelCarte panelCarte = new PanelCarteRessource(this.ctrl, ressource.getNom(), this.ctrl.getNbNotions(ressource) + " notion(s)", "tmp.png");
			this.lstPanelCartes.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}
		this.panelContenu.add(this.panelBtnAjouter);

		for(int i = this.lstPanelCartes.size(); i >= 0; i--)
		{
			PanelCarte panelCarte = new PanelCarteRessource(this.ctrl, " ", " ", " ");
			panelCarte.setVisible(false);
			this.lstPanelCartes.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}
	}

	public void afficherNotions(Ressource ressource) 
	{
		System.out.println("afficher notions");
		/* Entête */
		this.btnRetour.setEnabled(true);

		this.lblFilAriane.setText("Ressources >> " + ressource.getNom());
		this.lblTitre    .setText(ressource.getNom());
		this.lblSousTitre.setText(this.ctrl.getNbNotions(ressource) + " notion(s)");

		/* Contenu */
		this.reinitContenu();

		this.panelContenu.setLayout(new GridLayout(0,3, 30,30));
		for(Notion notion : this.ctrl.getNotions(ressource))
		{
			PanelCarte panelCarte = new PanelCarteNotion(this.ctrl, notion.getNom(), this.ctrl.getNbQuestions(ressource, notion) + " question(s)", "tmp.png");
			this.lstPanelCartes.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}

		this.btnAjouter.setText("Créer une Nouvelle Notion");
		this.panelContenu.add(this.panelBtnAjouter);
	}

	public void afficherQuestions(Ressource ressource, Notion notion) 
	{
		System.out.println("afficher lstQuestions");
		/* Entête */
		this.btnRetour.setEnabled(true);

		this.lblFilAriane.setText("Ressources >> " + ressource.getNom() + " >> " + notion.getNom());
		this.lblTitre    .setText(notion.getNom());
		this.lblSousTitre.setText(this.ctrl.getNbQuestions(ressource, notion) + " question(s)");

		/* Contenu */
		this.reinitContenu();

		this.panelContenu.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0; 
		 
		for(Question question : this.ctrl.getQuestions(ressource, notion))
		{
			PanelCarte panelCarte = new PanelCarteQuestion(this.ctrl, question.getIntitule(), " ", "tmp.png");
			this.lstPanelCartes.add(panelCarte);

			gbc.gridy = this.lstPanelCartes.size()-1;
			this.panelContenu.add(panelCarte, gbc);
		}

		this.btnAjouter.setText("Créer une Nouvelle Question");
		gbc.weighty = 1;
		gbc.gridy = 1;
		this.panelContenu.add(this.panelBtnAjouter, gbc);
	}
}