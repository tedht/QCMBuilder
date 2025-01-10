package ihm.notion;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelGestion;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import metier.entite.Notion;

/**
 * Classe JPanel dédiée à la gestion des notions.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-10 Norme ISO-8601
 */
public class PanelGestionNotion extends PanelGestion
{
	private FrameGestionNotion frame;

	private JPanel panelInstruction;
	private JPanel panelAucuneNotion;

	/**
	 * Constructeur de la classe PanelGestionNotion
	 * 
	 * @param ctrl  Le contrôleur.
	 * @param ihm   Le gestionnaire des fenêtres de l'application.
	 * @param frame La fenêtre de gestion des notions.
	 */
	public PanelGestionNotion(Controleur ctrl, IHM ihm, FrameGestionNotion frame) 
	{
		super(ctrl, ihm);

		this.frame = frame;
		
		this.lblTitre  .setText("Notions :");
		this.btnAjouter.setText("Nouvelle notion");
		this.btnAjouter.setEnabled(false);

		this.panelInstruction = new JPanel();
		this.panelInstruction.add(new JLabel("Double-cliquez sur une ressource afin d'afficher les notions associées."));

		this.panelAucuneNotion = new JPanel();
		this.panelAucuneNotion.add(new JLabel("Cette ressource ne comporte aucune notion."));

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
			this.ihm.editNotion();
		}
	}

	/**
	 * Gère l'affichage des notions.
	 * 
	 * Cette méthode est responsable de l'affichage des notions. S'il n'y a pas de notions,
	 * un panel avec un texte d'instruction est affiché.
	 */
	@Override
	public void afficher() 
	{
		// Suppression de tous les composants existants pour redessiner
		this.panelContenu.removeAll();

		// Création du panel pour chaque notion
		PanelNotion panelNotion;
		if(this.ctrl.getRessources().isEmpty())
		{
			// S'il n'y a pas de ressources, afficher des panels vides
			for(int i = 10; i > 0; i--)
			{
				panelNotion = new PanelNotion();
				panelNotion.setVisible(false);
				this.panelContenu.add(panelNotion);
			}
		}
		else if(this.ctrl.getRessourceSelectionnee() == null)
		{
			// Si aucune ressource n'est sélectionnée, afficher les instructions
			this.btnAjouter.setEnabled(false);
			this.panelContenu.add(this.panelInstruction);

			// Ajouter des panels vides pour combler l'espace
			for(int i = 9; i > 0; i--)
			{
				panelNotion = new PanelNotion();
				panelNotion.setVisible(false);
				this.panelContenu.add(panelNotion);
			}
		}
		else if(this.ctrl.getNotions(this.ctrl.getRessourceSelectionnee().getCode()).isEmpty())
		{
			// Si la ressource sélectionnée n'a pas de notions, afficher un message et activer le bouton d'ajout
			this.btnAjouter.setEnabled(true);
			this.panelContenu.add(this.panelAucuneNotion);

			// Ajouter des panels vides pour combler l'espace
			for(int i = 9; i > 0; i--)
			{
				panelNotion = new PanelNotion();
				panelNotion.setVisible(false);
				this.panelContenu.add(panelNotion);
			}
		}
		else
		{
			// Si des notions sont disponibles, afficher chaque notion
			this.btnAjouter.setEnabled(true);
			
			for(Notion notion : this.ctrl.getNotions(this.ctrl.getRessourceSelectionnee().getCode()))
			{
				panelNotion = new PanelNotion(this.ctrl, this.ihm, this.frame, notion.getIdNot());
				this.panelContenu.add(panelNotion);
			}

			// Ajoute des panels vides si nécessaire pour combler l'espace restant
			for(int i = 10 - this.ctrl.getNotions(this.ctrl.getRessourceSelectionnee().getCode()).size(); i > 0; i--)
			{
				panelNotion = new PanelNotion();
				panelNotion.setVisible(false);
				this.panelContenu.add(panelNotion);
			}
		}

		// Redessine le panel après modification
		this.revalidate();
		this.repaint();
	}
	
}
