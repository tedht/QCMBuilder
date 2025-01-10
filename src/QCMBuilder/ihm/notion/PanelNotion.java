package QCMBuilder.ihm.notion;


import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import QCMBuilder.controleur.Controleur;

import QCMBuilder.ihm.IHM;
import QCMBuilder.ihm.shared.PanelEntite;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-10 Norme ISO-8601
 */
public class PanelNotion extends PanelEntite
{	
	private FrameGestionNotion frame;
	
	private int id;

	/**
	 * Constructeur sans paramètre qui crée une instance de PanelNotion vide.
	 */
	public PanelNotion()
	{
		super(null, null, "", "");
		this.id = -1;
	}

	/**
	 * Constructeur de la classe PanelNotion.
	 *
	 * @param ctrl  Le contrôleur
	 * @param ihm   Le gestionnaire des fenêtres de l'application.
	 * @param frame La fenêtre de gestion des notions
	 * @param id    L'id de la notion
	 */
	public PanelNotion(Controleur ctrl, IHM ihm, FrameGestionNotion frame, int id)
	{
		super(ctrl, ihm);

		if(id != -1)
			this.lblTitre.setText(ctrl.getNotion(id).getNom());

		this.frame = frame;
		this.id    = id;

		// Activation des composants
		this.btnModifier .addActionListener(this);
		this.btnSupprimer.addActionListener(this);
		this.addMouseListener(new GereSourisNotion());
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
			this.ihm.editNotion(this.id);
		}

		if(e.getSource() == this.btnSupprimer)
		{
			choix = JOptionPane.showConfirmDialog(
				this.frame,
				"Voulez-vous vraiment supprimer la notion \"" + this.lblTitre.getText() + "\" ?",
				"Confirmation de suppression",
				JOptionPane.YES_NO_OPTION
			);

			if (choix == JOptionPane.YES_OPTION)
			{
				this.ctrl.supprimerNotion(this.id);
				this.ihm.reinitAffichageNotion();
			}
		}
	}

	/**
	 * Classe interne pour gérer les interactions avec la souris sur le panel.
	 */
	private class GereSourisNotion extends MouseAdapter
	{
		/**
		 * Action déclenchée lorsque la souris entre dans le panel.
		 * Change la couleur de fond et ajoute une bordure pour signaler un survol.
		 * 
		 * @param e L'événement de survol.
		 */
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			PanelNotion.this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			PanelNotion.this.setBackground(Color.LIGHT_GRAY);
		}

		/**
		 * Action déclenchée lorsque la souris quitte le panel.
		 * Réinitialise l'apparence du panel à son état initial.
		 * 
		 * @param e L'événement de sortie.
		 */
		@Override
		public void mouseExited(MouseEvent e) 
		{
			PanelNotion.this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			PanelNotion.this.setBackground(Color.WHITE);
		}
	}
}
