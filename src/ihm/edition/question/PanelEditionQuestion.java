package ihm.edition.question;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;

/**
 * Classe JPanel de la fenêtre d'édition d'une question
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelEditionQuestion extends JPanel implements ActionListener
{
	private Controleur ctrl;

	private JPanel            panelInfo, panelAction, panelInfoNiveau;
	private JTextField        txtPoints, txtTemps;
	private JComboBox<String> ddlstRssource, ddlstNotion;
	private JButton           btnAnnuler, btnValider, btnPrecedent, btnSuivant;
	private JRadioButton[]    tabRbNiveau;
	private ButtonGroup       btgNiveau;
	
	/**
	 * Constructeur de la classe PanelEditionQuestion utilisé lorsqu'on veut créer une question.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditionQuestion(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		/* Panel */
		// Info
		this.panelInfo = new JPanel(new GridBagLayout());
		this.panelInfo.setBorder(new EmptyBorder(10,10,10,10));
		//this.panelInfo.setOpaque(false);
		
		// Action
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		// Niveau
		this.panelInfoNiveau = new JPanel();

		/* Champs de texte */
		this.txtPoints = new JTextField(5);
		this.txtTemps  = new JTextField(5);

		/* Listes déroulantes */
		// Ressource
		this.ddlstRssource = new JComboBox<String>();
		for(String ressource : this.ctrl.getRessources())
		{
			this.ddlstRssource.addItem(ressource);
		}
		this.ddlstRssource.setSelectedIndex(-1);

		// Notion
		this.ddlstNotion = new JComboBox<String>();
		this.ddlstNotion.setEnabled(false);

		/* Boutons */
		this.btnAnnuler   = new JButton("Annuler");
		this.btnValider   = new JButton("Valider");
		this.btnPrecedent = new JButton("Précédent");
		this.btnSuivant   = new JButton("Suivant");

		/* Boutons Radio */
		tabRbNiveau = new JRadioButton[4];
		tabRbNiveau[0] = new JRadioButton("TF");
		tabRbNiveau[1] = new JRadioButton("F");
		tabRbNiveau[2] = new JRadioButton("M");
		tabRbNiveau[3] = new JRadioButton("D");

		btgNiveau = new ButtonGroup();
		for(JRadioButton rb : tabRbNiveau)
			btgNiveau.add(rb);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		/* Panel Info */
		this.add(this.panelInfo, BorderLayout.CENTER);
		this.add(this.panelAction, BorderLayout.SOUTH);

		this.pagePrecedente();

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler  .addActionListener(this);
		this.btnValider  .addActionListener(this);
		this.btnPrecedent.addActionListener(this);
		this.btnSuivant  .addActionListener(this);
		for(JRadioButton rb : tabRbNiveau)
			rb.addActionListener(this);
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
				SwingUtilities.getWindowAncestor(this).dispose();
		}
		if(e.getSource() == this.btnSuivant)
		{
			this.pageSuivante();
		}
		if(e.getSource() == this.btnPrecedent)
		{
			this.pagePrecedente();
		}
	}

	private boolean valider()
	{
		return true;
	}

	private void pageSuivante()
	{
		this.panelInfo  .removeAll();
		this.panelAction.removeAll();

		/* Panel Info */



		/* Panel Action */
		this.panelAction.add(this.btnPrecedent);
		this.panelAction.add(this.btnValider);

		this.revalidate();
		this.repaint();
	}

	private void pagePrecedente()
	{
		this.panelInfo  .removeAll();
		this.panelAction.removeAll();

		/* Panel Info */
		GridBagConstraints gbc = new GridBagConstraints();

		// Ligne 0
		gbc.gridy = 0; gbc.gridx = 0; // (0,0)
		gbc.insets  = new Insets(0, 5, 0, 5);
 		gbc.anchor  = GridBagConstraints.WEST;
		this.panelInfo.add(new JLabel("Nombre de Points :"), gbc);

		gbc.gridx = 1; // (0,1)
		this.panelInfo.add(new JLabel("Temps de Réponse (min:sec) :"), gbc);

		gbc.gridx = 2; // (0,2)
		this.panelInfo.add(new JLabel(" "), gbc);

		// Ligne 1 
		gbc.gridy = 1; gbc.gridx = 0; // (1,0)
		gbc.insets  = new Insets(0, 5, 10, 5);
		this.panelInfo.add(this.txtPoints, gbc);

		gbc.gridx = 1;// (1,1)
		this.panelInfo.add(this.txtTemps, gbc);

		// Ligne 2
		gbc.gridy = 2; gbc.gridx = 0; // (2,0)
		gbc.insets  = new Insets(0, 5, 0, 5);
		this.panelInfo.add(new JLabel("Ressource :"), gbc);

		gbc.gridx = 1; // (2,1)
		this.panelInfo.add(new JLabel("Notion :"), gbc);

		gbc.gridx = 2; // (2,1)
		this.panelInfo.add(new JLabel("Niveau :"), gbc);

		// Ligne 3
		gbc.gridy = 3; gbc.gridx = 0; // (3,0)
		gbc.insets  = new Insets(0, 5, 10, 5);
		this.panelInfo.add(this.ddlstRssource, gbc);

		gbc.gridx = 1; // (3,1)
		this.panelInfo.add(this.ddlstNotion, gbc);

		gbc.gridx = 2; // (3,2)
		this.panelInfo.add(this.panelInfoNiveau, gbc);

		for(JRadioButton rb : tabRbNiveau)
			this.panelInfoNiveau.add(rb);
		
		/* Panel Action */
		this.panelAction.add(this.btnAnnuler);
		this.panelAction.add(this.btnSuivant);

		this.revalidate();
		this.repaint();
	}
}
