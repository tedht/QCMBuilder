package ihm.question;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelGestion;
import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Question;

/**
 * Classe JPanel dédiée à la gestion des questionss.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-10 Norme ISO-8601
 */
public class PanelGestionQuestion extends PanelGestion implements ItemListener
{
	private JComboBox<Ressource> ddlstRessource;
	private JComboBox<Notion>    ddlstNotion;

	private JPanel panelAucuneQuestion;

	/**
	 * Constructeur de la classe PanelGestionNotion
	 * 
	 * @param ctrl  Le contrôleur.
	 * @param ihm   Le gestionnaire des fenêtres de l'application.
	 */
	public PanelGestionQuestion(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);

		JPanel panelDdlst, panelDdlstRessource, panelDdlstNotion;

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.lblTitre.setText("Questions :");

		panelDdlst = new JPanel(new GridLayout(1, 2, 5, 5));

		panelDdlstRessource = new JPanel(new BorderLayout());
		panelDdlstNotion    = new JPanel(new BorderLayout());

		this.ddlstRessource = new JComboBox<Ressource>();
		this.ddlstNotion    = new JComboBox<Notion>();

		this.btnAjouter.setText("Nouvelle question");

		this.panelAucuneQuestion = new JPanel();
		this.panelAucuneQuestion.add(new JLabel("Aucune question n'a été trouvée."));

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.panelEntete   .add(panelDdlst, BorderLayout.CENTER);

		panelDdlst         .add(panelDdlstRessource);
		panelDdlstRessource.add(new JLabel("Ressource :"), BorderLayout.NORTH);
		panelDdlstRessource.add(this.ddlstRessource,            BorderLayout.CENTER);

		panelDdlst         .add(panelDdlstNotion);
		panelDdlstNotion   .add(new JLabel("Notion :"), BorderLayout.NORTH);
		panelDdlstNotion   .add(this.ddlstNotion,            BorderLayout.CENTER);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.ddlstRessource.addItemListener(this);
		this.ddlstNotion   .addItemListener(this);

		this.afficher();
	}

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.btnAjouter)
		{
			this.ihm.editQuestion();
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
		if(e.getSource() == this.ddlstRessource) 
		{  
			this.ddlstNotion.removeAllItems();
			if(this.ddlstRessource.getSelectedItem() != null)
			{
				for(Notion notion : this.ctrl.getNotions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode()))
				{
					this.ddlstNotion.addItem(notion);
				}
				this.ddlstNotion.setEnabled(true);
				this.ddlstNotion.setSelectedIndex(-1);
			}
			this.afficher();
		}

		if(e.getSource() == this.ddlstNotion)
		{
			this.afficher();
		}
	}

	/**
     * Méthode qui gère l'affichage des questions.
     * 
     * Cette méthode est responsable de l'affichage des questions. S'il n'y a pas de questions,
     * un panel avec un texte d'instruction est affiché.
     */
	public void afficher()
	{
		// Suppression de tous les composants existants pour redessiner
		this.panelContenu.removeAll();

		// Récupère les éléments sélectionnés
		Ressource ressource = (Ressource)this.ddlstRessource.getSelectedItem();
		Notion    notion    = (Notion)   this.ddlstNotion   .getSelectedItem();

		// Récupère la liste des questions à afficher
		List<Question> lstQuestions;
		if(ressource == null)
		{
			// Toutes les questions
			lstQuestions = this.ctrl.getQuestions(); 
		}
		else if(notion == null)
		{
			// Toutes les questions associées à une ressource
			lstQuestions = this.ctrl.getQuestions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode());
		}
		else
		{
			// Toutes les questions associées à une ressource et une notion
			lstQuestions = this.ctrl.getQuestions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode(), notion.getIdNot());
		}

		PanelQuestion panelCarte;
		if(lstQuestions.isEmpty())
		{
			// Affiche un message si aucune question n'est trouvée
			this.panelContenu.add(this.panelAucuneQuestion);

			// Ajouter des panels vides pour combler l'espace
			for(int i = 9 - lstQuestions.size(); i > 0; i--)
			{
				panelCarte = new PanelQuestion();
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}
		else
		{
			// Affiche les questions
			for(Question question : lstQuestions)
			{
				if(question != null)
				{
					double note = question.getNote ();
					int    tps  = question.getTemps();

					String info =   note + " point" + (note == 1.0 ? ", " : "s, ")
					              + Math.round(tps / 60) + "min "
								  + (tps % 60 == 0 ? "" : tps % 60 + "s");

					panelCarte = new PanelQuestion(this.ctrl, this.ihm, 
					                               question.getIntitule(), 
					                               info,
					                               question.getIdQst());
					this.panelContenu.add(panelCarte);
				}
			}
	
			// Ajoute des panels vides si nécessaire pour combler l'espace restant
			for(int i = 8 - lstQuestions.size(); i > 0; i--)
			{
				panelCarte = new PanelQuestion();
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}

		// Redessiner le panel après modification
		this.revalidate();
		this.repaint();
	}

	/**
     * Réinitialise les listes déroulantes des ressources et notions.
     */
	public void reinitDdlsts()
	{
		// Réinitialise le menu déroulant des ressources
		this.ddlstRessource.removeAllItems();
		this.ddlstRessource.addItem(null);
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource);
		}
		this.ddlstRessource.setFocusable(false);
		this.ddlstRessource.setPrototypeDisplayValue(new Ressource("", String.format("%70s", " ")));
		
		// Réinitialise le menu déroulant des notions
		this.ddlstNotion.removeAllItems();
		this.ddlstNotion.setEnabled(false);
		this.ddlstNotion.setFocusable(false);
		this.ddlstNotion.setPrototypeDisplayValue(new Notion("", 0, String.format("%70s", " ")));

		// Aucune ressource sélectionnée par défaut
		this.ddlstRessource.setSelectedIndex(-1);
	}
	
}
