package ihm.edition;

import java.awt.BorderLayout;
import java.awt.Color;
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
public abstract class PanelEditEntite extends JPanel implements ActionListener
{
	protected Controleur ctrl;
	protected IHM        ihm;

	protected JPanel        panelInfo, panelAction;
	protected JButton       btnAnnuler, btnValider;
	protected JLabel        lblNom;
	protected JTextField    txtNom;

	/**
	 * Constructeur de la classe PanelEditEntite.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditEntite(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		// Panel Info
		this.panelInfo = new JPanel(new BorderLayout());
		this.panelInfo.setBorder(new EmptyBorder(10,10,10,10));
		
		// Panel Action
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		this.lblNom = new JLabel("Nom de la " + this.getType() + " : ");

		this.txtNom = new JTextField(30);

		this.btnAnnuler = new JButton("Annuler");
		this.btnValider = new JButton("Valider");

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		// Panel Info
		this.add(this.panelInfo, BorderLayout.CENTER);

		this.panelInfo.add(this.lblNom, BorderLayout.NORTH);
		this.panelInfo.add(this.txtNom, BorderLayout.CENTER);

		// Panel Action
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

	public abstract String  getType();
	public abstract boolean valider();
}
