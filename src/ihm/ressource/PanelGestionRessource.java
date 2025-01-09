package ihm.ressource;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelGestion;
import metier.entite.Ressource;

/**
 * Classe JPanel dédiée à la gestion des ressources.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelGestionRessource extends PanelGestion
{
	private FrameGestionRessource frame;

	private JPanel panelInstruction;

	/**
	 * Constructeur de la classe PanelGestionRessource
	 * 
	 * @param ctrl  le contrôleur.
	 * @param ihm   le gestionnaire des fenêtres de l'application.
	 * @param frame la fenêtre de gestion des ressources.
	 */
	public PanelGestionRessource(Controleur ctrl, IHM ihm, FrameGestionRessource frame) 
	{
		super(ctrl, ihm);

		this.frame = frame;
		
		this.lblTitre  .setText("Ressources :");
		this.btnAjouter.setText("Nouvelle ressource");

		this.panelInstruction = new JPanel();
		this.panelInstruction.add(new JLabel("Cliquez sur le bouton \"Nouvelle ressource\" pour créer une ressource"));

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
			this.ihm.editRessource();
		}
	}

    /**
     * Méthode qui gère l'affichage  des ressources, S'il n'y a pas de ressources,
     * un panel avec un texte d'instruction est affiché.
     */
	@Override
	public void afficher() 
	{
		// Suppression de tous les composants existants pour redessiner
		this.panelContenu.removeAll();

		// Création du panel pour chaque ressource
		PanelRessource panelRessource;
		if(this.ctrl.getRessources().isEmpty())
		{
			// Si aucune ressource n'est disponible, afficher un message d'instruction
			this.panelContenu.add(this.panelInstruction);
	
			// Ajout de panels vides pour combler l'espace
			for(int i = 9; i > 0; i--)
			{
				panelRessource = new PanelRessource();
				panelRessource.setVisible(false);
				this.panelContenu.add(panelRessource);
			}
	
		}
		else
		{
			// Si des ressources sont disponibles, afficher chaque ressource
			for(Ressource ressource : this.ctrl.getRessources())
			{
				panelRessource = new PanelRessource(this.ctrl, this.ihm, this.frame, ressource.getCode());
				this.panelContenu.add(panelRessource);
			}
	
			// Ajouter des panels vides si nécessaire pour combler l'espace restant
			for(int i = 10 - this.ctrl.getRessources().size(); i > 0; i--)
			{
				panelRessource = new PanelRessource();
				panelRessource.setVisible(false);
				this.panelContenu.add(panelRessource);
			}
		}

		// Redessiner le panel après modification
		this.revalidate();
		this.repaint();
	}
}
