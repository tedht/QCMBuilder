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
 * Classe JPanel générique utilisé par la frames d'édition de ressource et la frames d'édition de notion
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public abstract class PanelEditNom extends JPanel implements ActionListener
{
	protected Controleur ctrl;
	protected IHM        ihm;

	protected JPanel        panelInfo, panelAction;

	protected JPanel        panelInfoNom;

	protected JButton       btnAnnuler, btnValider;
	protected JLabel        lblNom;
	protected JTextField    txtNom;

	/**
	 * Constructeur de la classe PanelEditEntite.
	 *
	 * @param ctrl Le contrôleur
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
		this.btnValider = new JButton("Valider");

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
		this.panelAction.add(this.btnValider);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAnnuler)
		{
			SwingUtilities.getWindowAncestor(this).dispose();
		}
		if(e.getSource() == this.btnValider)
		{
			if(this.valider())
			{
				SwingUtilities.getWindowAncestor(this).dispose();
			}
		}
	}

	public abstract boolean valider();
}