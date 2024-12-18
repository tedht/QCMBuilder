package ihm;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import controleur.Controleur;
import ihm.entite.PanelEntite;
import ihm.entite.PanelNotion;
import ihm.entite.PanelQuestion;
import ihm.entite.PanelRessource;
import metier.entite.Ressource;
import metier.entite.Notion;
import metier.entite.question.Question;

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
	private IHM        ihm;

	private List<PanelEntite> lstPanelEntites;

	private JPanel panelEntete;
	private JPanel panelContenu;
	private JPanel panelBtnAjouter;

	private JButton btnRetour, btnNouvelleQuestionnaire, btnAjouter;
	private JLabel  lblTitre, lblSousTitre, lblFilAriane;

	private JScrollPane scrollPanelContenu;

	/**
	 * Constructeur de la classe PanelQCMBuilder.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelQCMBuilder(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setLayout(new BorderLayout());
		
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		/* Panels */
		this.panelEntete     = new JPanel(new GridBagLayout());
		this.panelContenu    = new JPanel(new GridLayout(0, 1, 5, 10));
		this.panelBtnAjouter = new JPanel(new GridBagLayout());

		this.panelEntete .setBorder(new EmptyBorder(10,20,10,20));
		this.panelContenu.setBorder(new EmptyBorder(20,20,20,20));

		this.lstPanelEntites = new ArrayList<PanelEntite>();

		this.scrollPanelContenu = new JScrollPane(this.panelContenu);
        this.scrollPanelContenu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPanelContenu.getVerticalScrollBar().setUnitIncrement(16);
		this.scrollPanelContenu.getVerticalScrollBar().setBlockIncrement(32);
		this.scrollPanelContenu.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		/* Boutons */
		this.btnRetour             = new JButton("Retour");
		this.btnNouvelleQuestionnaire = new JButton("Nouvelle Évaluation");
		this.btnAjouter            = new JButton(" ");

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
        this.panelEntete.add(this.btnNouvelleQuestionnaire, gbc);

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
		this.btnRetour            .addActionListener(this); 
		this.btnAjouter           .addActionListener(this); 
		this.btnNouvelleQuestionnaire.addActionListener(this); 
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnRetour)
		{
			this.ctrl.popHistorique();
			this.ihm.reinitAffichage();
		}
		if(e.getSource() == this.btnNouvelleQuestionnaire)
		{
			this.ihm.nouvelleQuestionnaire();
		}
		if(e.getSource() == this.btnAjouter)
		{
			if(this.ctrl.getRessourceActive() == null)
			{
				this.ihm.creerRessource();
			}
			else if(this.ctrl.getNotionActive() == null)
			{
				this.ihm.creerNotion();
			}
			else
			{
				this.ihm.creerQuestion();
			}
		}
	}

	public void reinitAffichage()
	{
		for(PanelEntite panelCarte : this.lstPanelEntites)
		{
			this.panelContenu.remove(panelCarte);
		}
		this.lstPanelEntites.clear();
		this.panelContenu.remove(this.panelBtnAjouter);

		if(this.ctrl.getRessourceActive() == null)
		{
			this.afficherRessources();
		}
		else if(this.ctrl.getNotionActive() == null)
		{
			this.afficherNotions();
		}
		else
		{
			this.afficherQuestions();
		}

		this.revalidate();
		this.repaint();
	}


	private void afficherRessources() 
	{
		/* Entête */
		this.btnRetour.setEnabled(false);

		this.lblFilAriane.setText("Ressources");
		this.lblTitre    .setText("Ressources");
		this.lblSousTitre.setText(this.ctrl.getRessources().size() + " ressource(s)");
		
		/* Contenu */
		for(Ressource ressource : this.ctrl.getRessources())
		{
			PanelEntite panelCarte = new PanelRessource(this.ctrl, this.ihm, ressource.getNom(), ressource.getCode());
			this.lstPanelEntites.add(panelCarte);
			this.panelContenu   .add(panelCarte);
		}

		this.btnAjouter.setText("Créer une Nouvelle Ressource");
		this.panelContenu.add(this.panelBtnAjouter);

		for(int i = 8 - this.lstPanelEntites.size(); i > 0; i--)
		{
			PanelEntite panelCarte = new PanelRessource(null, null, " ", " ");
			panelCarte.setVisible(false);
			this.lstPanelEntites.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}
	}

	private void afficherNotions() 
	{
		Ressource ressource = this.ctrl.getRessourceActive();
		
		/* Entête */
		this.btnRetour.setEnabled(true);

		this.lblFilAriane.setText("Ressources >> " + ressource.getNom());
		this.lblTitre    .setText(ressource.getNom());
		this.lblSousTitre.setText(this.ctrl.getNbNotions(ressource) + " notion(s)");

		/* Contenu */
		for(Notion notion : this.ctrl.getNotions(ressource))
		{
			PanelEntite panelCarte = new PanelNotion(this.ctrl, this.ihm, notion.getNom(), this.ctrl.getNbQuestions(ressource, notion) + " question(s)", notion.getId());
			this.lstPanelEntites.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}

		this.btnAjouter.setText("Créer une Nouvelle Notion");
		this.panelContenu.add(this.panelBtnAjouter);

		for(int i = 8 - this.lstPanelEntites.size(); i > 0; i--)
		{
			PanelEntite panelCarte = new PanelQuestion(null, null, "", "", 0);
			panelCarte.setVisible(false);
			this.lstPanelEntites.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}
	}

	private void afficherQuestions() 
	{
		Ressource ressource = this.ctrl.getRessourceActive();
		Notion    notion    = this.ctrl.getNotionActive();
		
		/* Entête */
		this.btnRetour.setEnabled(true);

		this.lblFilAriane.setText("Ressources >> " + ressource.getNom() + " >> " + notion.getNom());
		this.lblTitre    .setText(notion.getNom());
		this.lblSousTitre.setText(this.ctrl.getNbQuestions(ressource, notion) + " question(s)");

		/* Contenu */
		for(Question question : this.ctrl.getQuestions(ressource, notion))
		{
			PanelEntite panelCarte = new PanelQuestion(this.ctrl, this.ihm, question.getIntitule(), 
			                                            question.getNote() + " point(s), " + question.getTemps() + "s", 0);
			this.lstPanelEntites.add(panelCarte);

			this.panelContenu.add(panelCarte);
		}

		this.btnAjouter.setText("Créer une Nouvelle Question");
		this.panelContenu.add(this.panelBtnAjouter);

		for(int i = 8 - this.lstPanelEntites.size(); i > 0; i--)
		{
			PanelEntite panelCarte = new PanelQuestion(null, null, "", "", 0);
			panelCarte.setVisible(false);
			this.lstPanelEntites.add(panelCarte);
			this.panelContenu.add(panelCarte);
		}
	}
}