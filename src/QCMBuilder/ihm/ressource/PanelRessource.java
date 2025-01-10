package QCMBuilder.ihm.ressource;


import QCMBuilder.controleur.Controleur;
import QCMBuilder.ihm.IHM;
import QCMBuilder.ihm.shared.PanelEntite;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Classe JPanel pour afficher une Ressource.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelRessource extends PanelEntite
{
	private FrameGestionRessource frame;

	private String  code;
	private boolean select;

	/**
	 * Constructeur sans paramètre qui crée une instance de PanelRessource vide.
	 */
	public PanelRessource()
	{
		this(null, null, null, "");
	}
	
	/**
	 * Constructeur de la classe PanelRessource.
	 *
	 * @param ctrl  le contrôleur
	 * @param ihm   le gestionnaire des fenêtres de l'application.
	 * @param frame la fenêtre de gestion des ressources
	 * @param code  le code de la ressource
	 */
	public PanelRessource(Controleur ctrl, IHM ihm, FrameGestionRessource frame, String code)
	{
		super(ctrl, ihm);

		if(!"".equals(code))
			this.lblTitre.setText(code + " " + ctrl.getRessource(code).getNom());
		
		this.frame = frame;
		this.code  = code; 

		// Détermine si cette ressource est sélectionnée et applique le style correspondant.
		this.select = this.ctrl != null && this.ctrl.getRessourceSelectionnee() == this.ctrl.getRessource(this.code);
		if(this.select)
		{
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			this.setBackground(Color.LIGHT_GRAY);
		}
		else
		{
			this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			this.setBackground(Color.WHITE);
		}

		// Activation des composants
		this.btnModifier .addActionListener(this);
		this.btnSupprimer.addActionListener(this);
		this.addMouseListener(new GereSourisRessource());
		
	}

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int choix;
		
		if(e.getSource() == this.btnModifier)
		{
			this.ihm.editRessource(this.code);
		}

		if(e.getSource() == this.btnSupprimer)
		{
			choix = JOptionPane.showConfirmDialog(
				this.frame,
				"Voulez-vous vraiment supprimer la ressource \"" + this.lblTitre.getText() + "\" ?",
				"Confirmation de suppression",
				JOptionPane.YES_NO_OPTION
			);

			if (choix == JOptionPane.YES_OPTION) 
			{
				this.ctrl.supprimerRessource(this.code);
				this.ihm.reinitAffichageRessource();
				this.ihm.reinitAffichageNotion();
			}
		}
	}

	/**
	 * Classe interne pour gérer les interactions avec la souris sur le panel.
	 */
	private class GereSourisRessource extends MouseAdapter
	{
		/**
		 * Action déclenchée lorsque la souris entre dans le panel.
		 * Modifie l'apparence pour signaler un survol.
		 * 
		 * @param e l'événement de survol.
		 */
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			PanelRessource.this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			PanelRessource.this.setBackground(Color.LIGHT_GRAY);
		}

		/**
		 * Action déclenchée lorsque la souris quitte le panel.
		 * Réinitialise l'apparence en fonction de l'état de sélection.
		 * 
		 * @param e l'événement de sortie.
		 */
		@Override
		public void mouseExited(MouseEvent e) 
		{
			if(PanelRessource.this.select)
			{
				PanelRessource.this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
				PanelRessource.this.setBackground(Color.LIGHT_GRAY);
			}
			else
			{
				PanelRessource.this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
				PanelRessource.this.setBackground(Color.WHITE);
			}
		}
		
		/**
		 * Action déclenchée lors d'un clic de souris.
		 * Permet de sélectionner ou désélectionner une ressource par un double-clic gauche.
		 * 
		 * @param e l'événement de clic.
		 */
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			 // Vérifie si un double-clic gauche a été effectué.
			if (!(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e))) return;
			
			// Gère la sélection ou désélection de la ressource.
			if(PanelRessource.this.select)
			{
				PanelRessource.this.ctrl.setRessourceSelectionnee(null);	
			}
			else
			{
				PanelRessource.this.ctrl.setRessourceSelectionnee(
					PanelRessource.this.ctrl.getRessource(PanelRessource.this.code)
				);	
			}

			// Réinitialise les affichages liés aux ressources et aux notions.
			PanelRessource.this.ihm.reinitAffichageRessource();
			PanelRessource.this.ihm.reinitAffichageNotion   (); 
		}
	}
}
