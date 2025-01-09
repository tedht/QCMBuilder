package ihm.shared;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel générique utilisée par les fenêtres d'édition.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-06 Norme ISO-8601
 */
public abstract class PanelEditNom extends JPanel implements ActionListener
{
	protected Controleur ctrl;
	protected IHM        ihm;

	protected JPanel        panelInfo, panelAction;

	protected JPanel        panelInfoNom;

	protected JButton       btnAnnuler, btnEnregistrer;
	protected JLabel        lblNom;
	protected JTextField    txtNom;

	/**
	 * Constructeur e la classe PanelEditNom.
	 *
	 * @param ctrl le contrôleur
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public PanelEditNom(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		/* INFO */
		this.panelInfo = new JPanel(new GridLayout(0,1,5,5));
		this.panelInfo.setBorder(new EmptyBorder(10,10,10,10));
		
		this.panelInfoNom = new JPanel(new BorderLayout());

		this.lblNom = new JLabel("Nom :");
		this.txtNom = new JTextField(30);

		/* ACTION */
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		this.btnAnnuler = new JButton("Annuler");
		this.btnEnregistrer = new JButton("Enregistrer");

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		/* INFO */
		this.add(this.panelInfo, BorderLayout.CENTER);

		this.panelInfoNom.add(this.lblNom, BorderLayout.NORTH);
		this.panelInfoNom.add(this.txtNom, BorderLayout.CENTER);

		/* ACTION */
		this.add(this.panelAction, BorderLayout.SOUTH);
		this.panelAction.add(this.btnAnnuler);
		this.panelAction.add(this.btnEnregistrer);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler.addActionListener(this);
		this.btnEnregistrer.addActionListener(this);
	}

	/**
	 * Méthode qui gère les actions des boutons.
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAnnuler)
		{
			SwingUtilities.getWindowAncestor(this).dispose();
		}
		if(e.getSource() == this.btnEnregistrer)
		{
			if(this.enregistrer())
			{
				SwingUtilities.getWindowAncestor(this).dispose();
			}
		}
	}

	/**
	 * Méthode abstraite à définir dans les classes filles. Définit le comprtement 
	 * lorsqu'on clique sur le bouton "Enregistrer".
	 * 
	 * @return true si l'enregistrement a réussi, false sinon
	 */
	public abstract boolean enregistrer();
}
