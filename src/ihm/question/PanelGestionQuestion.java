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

public class PanelGestionQuestion extends PanelGestion implements ItemListener
{
	private JComboBox<Ressource> ddlstRessource;
	private JComboBox<Notion>    ddlstNotion;

	private JPanel panelAucuneQuestion;

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
		this.maj();

		this.btnAjouter.setText("Nouvelle Question");

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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.btnAjouter)
		{
			this.ihm.creerQuestion();
		}
	}


	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.ddlstRessource && this.ddlstRessource.getSelectedIndex() != -1) 
		{  
			this.ddlstNotion.removeAllItems();
			this.ddlstNotion.setEnabled(false);

			for(Notion notion : this.ctrl.getNotions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode()))
			{
				this.ddlstNotion.addItem(notion);
			}
			this.ddlstNotion.setSelectedIndex(-1);
			this.ddlstNotion.setEnabled(true);
			this.afficher();
		}

		if(e.getSource() == this.ddlstNotion && this.ddlstNotion.isEnabled())
		{
			this.afficher();
		}
	}

	public void afficher()
	{
		this.panelContenu.removeAll();

		Ressource ressource = (Ressource)this.ddlstRessource.getSelectedItem();
		Notion    notion    = (Notion)   this.ddlstNotion   .getSelectedItem();

		List<Question> lstQuestions;
		if(ressource == null)
		{
			lstQuestions = this.ctrl.getQuestions();
		}
		else if(notion == null)
		{
			lstQuestions = this.ctrl.getQuestions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode());
		}
		else
		{
			lstQuestions = this.ctrl.getQuestions(((Ressource)this.ddlstRessource.getSelectedItem()).getCode(), notion.getIdNot());
		}

		PanelQuestion panelCarte;

		if(lstQuestions.isEmpty())
		{
			this.panelContenu.add(this.panelAucuneQuestion);

			for(int i = 9 - lstQuestions.size(); i > 0; i--)
			{
				panelCarte = new PanelQuestion(null, null, "", "", 0);
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}
		else
		{
			for(Question question : lstQuestions)
			{
				panelCarte = new PanelQuestion(this.ctrl, this.ihm, 
											   "", 
											   question.getNote() + " point(s), " + question.getTemps() + "s",
											   0);
				this.panelContenu.add(panelCarte);
			}
	
			for(int i = 8 - lstQuestions.size(); i > 0; i--)
			{
				panelCarte = new PanelQuestion(null, null, "", "", 0);
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}

		this.revalidate();
		this.repaint();
	}

	public void maj()
	{
		this.ddlstRessource.removeAllItems();
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource);
		}
		this.ddlstRessource.setSelectedIndex(-1);
		this.ddlstRessource.setFocusable(false);
		this.ddlstRessource.setPrototypeDisplayValue(new Ressource("", String.format("%70s", " ")));

		this.ddlstNotion.removeAllItems();
		this.ddlstNotion.setEnabled(false);
		this.ddlstNotion.setSelectedIndex(-1);
		this.ddlstNotion.setFocusable(false);
		this.ddlstNotion.setPrototypeDisplayValue(new Notion("", 0, String.format("%70s", " ")));
	}
	
}
