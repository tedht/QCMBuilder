package ihm.question;

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
	private JComboBox<String>   ddlstRessource, ddlstNotion;

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

		this.ddlstRessource = new JComboBox<String>();
		for(Ressource ressource : this.ctrl.getRessources())
		{
			this.ddlstRessource.addItem(ressource.getNom());
		}
		this.ddlstRessource.setSelectedIndex(-1);
		this.ddlstRessource.setFocusable(false);
		this.ddlstRessource.setPrototypeDisplayValue(String.format("%70s", " "));

		this.ddlstNotion = new JComboBox<String>();
		this.ddlstNotion.setEnabled(false);
		this.ddlstNotion.setSelectedIndex(-1);
		this.ddlstNotion.setFocusable(false);
		this.ddlstNotion.setPrototypeDisplayValue(String.format("%70s", " "));

		this.btnAjouter.setText("Nouvelle Question");
		this.btnAjouter.setEnabled(false);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.panelEntete   .add(panelDdlst, BorderLayout.CENTER);
		panelDdlst         .add(panelDdlstRessource);
		panelDdlstRessource.add(new JLabel("Ressource :"), BorderLayout.NORTH);
		panelDdlstRessource.add(this.ddlstRessource, BorderLayout.CENTER);
		panelDdlst         .add(panelDdlstNotion);
		panelDdlstNotion   .add(new JLabel("Notion :"), BorderLayout.NORTH);
		panelDdlstNotion   .add(this.ddlstNotion, BorderLayout.CENTER);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.ddlstRessource.addItemListener(this);
		this.ddlstNotion   .addItemListener(this);
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
		if(e.getSource() == this.ddlstRessource) 
		{  
			this.ddlstNotion.removeAllItems();
			this.ddlstNotion.setEnabled(false);
			this.btnAjouter.setEnabled(false);

			for(Notion notion : this.ctrl.getNotions(this.ctrl.getRessource((String)this.ddlstRessource.getSelectedItem())))
			{
				this.ddlstNotion.addItem(notion.getNom());
			}
			this.ddlstNotion.setSelectedIndex(-1);
			this.ddlstNotion.setEnabled(true);
		}

		if(e.getSource() == this.ddlstNotion && this.ddlstNotion.isEnabled())
		{
			this.btnAjouter.setEnabled(true);
			this.afficher();
		}
	}

	public void afficher()
	{
		this.panelContenu.removeAll();

		Ressource ressource = this.ctrl.getRessource((String)this.ddlstRessource.getSelectedItem());
		Notion    notion    = this.ctrl.getNotion   ((String)this.ddlstNotion   .getSelectedItem(), ressource);

		PanelQuestion panelCarte;
		for(Question question : this.ctrl.getQuestions(ressource, notion))
		{
			panelCarte = new PanelQuestion(this.ctrl, this.ihm, question.getIntitule(), question.getNote() + " point(s), " + question.getTemps() + "s", 0);
			this.panelContenu.add(panelCarte);
		}

		for(int i = 8 - this.ctrl.getQuestions(ressource, notion).size(); i > 0; i--)
		{
			panelCarte = new PanelQuestion(null, null, "", "", 0);
			panelCarte.setVisible(false);
			this.panelContenu.add(panelCarte);
		}

		this.revalidate();
		this.repaint();
	}
	
}
