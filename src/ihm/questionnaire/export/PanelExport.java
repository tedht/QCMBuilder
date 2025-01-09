package ihm.questionnaire.export;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;

/**
 * Classe JPanel dédiée à l'exportation de questionnaire
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-18 Norme ISO-8601
 */
public class PanelExport extends PanelEditNom
{
	private FrameExport frame;
	
	private JPanel      panelInfoChemin;

	private JLabel      lblChemin;
	private JButton     btnNavig;
	
	/**
	 * Constructeur de la classe PanelExport.
	 *
	 * @param ctrl  le contrôleur.
	 * @param ihm   le gestionnaire des fenêtres de l'application.
	 * @param frame la fenêtre qui permet d'exporter un questionnaire.
	 */
	public PanelExport(Controleur ctrl, IHM ihm, FrameExport frame)
	{
		super(ctrl, ihm);
		this.frame = frame;

		JPanel panelBtnNavig;

		this.panelInfo.setLayout(new GridBagLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.lblNom.setText("Nom de l'évaluation :");

		this.panelInfoChemin = new JPanel(new BorderLayout());
		panelBtnNavig        = new JPanel();
		this.lblChemin       = new JLabel("");

		this.btnNavig = new JButton(IHM.getImgIconSVG("res/Dossier.svg", 16, 16));
		this.btnNavig.setPreferredSize(new Dimension(24, 24));

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets  = new Insets(5, 5, 5, 5);
		gbc.gridx   = 1;
		gbc.anchor  = GridBagConstraints.NORTH;
	    gbc.fill    = GridBagConstraints.HORIZONTAL;

		gbc.gridy = 0;
		this.panelInfo.add(this.panelInfoNom, gbc);

		gbc.gridy = 1;
		this.panelInfo.add(this.panelInfoChemin, gbc);

		this.panelInfoChemin.add(new JLabel("Répertoire de sauvegarde :"), BorderLayout.NORTH);
		this.panelInfoChemin.add(panelBtnNavig, BorderLayout.WEST);
		this.panelInfoChemin.add(this.lblChemin, BorderLayout.CENTER);
		panelBtnNavig       .add(this.btnNavig);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnNavig.addActionListener(this);
	}

	/**
	 * Méthode appelée lorsqu'on clique sur le bouton "Enregistrer". 
	 */
	public boolean enregistrer()
	{
		String nomEval = this.txtNom   .getText();
		String chemin  = this.lblChemin.getText();

		List<String> lstErreurs = new ArrayList<String>();

		/*--------------------------*/
		/* Vérification des erreurs */
		/*--------------------------*/

		// Vérification des champs vides
		if (nomEval.isEmpty())
			lstErreurs.add("Le nom de l'évaluation est vide.");

		if (chemin.isEmpty()) 
			lstErreurs.add("Aucun chemin n'a été sélectionné.");

		if(lstErreurs.size() != 0)
		{
			String message = "L'évaluation n'a pas pu être générée pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this,
				message,
				"Échec de l'Enregistrement",
				JOptionPane.ERROR_MESSAGE
			);
			return false;
		}

		/*------------*/
		/* Traitement */
		/*------------*/

		// On ferme la fenêtre de création de questionnaire
		this.ihm.fermerCreationQuestionnaire();
		// On exporte le questionnaire au format web
		this.ctrl.exporterQuestionnaire(chemin+"/"+nomEval);

		return true;
	}

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		super.actionPerformed(e);

		if(e.getSource() == this.btnNavig)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choisissez l'emplacement de sauvegarde de votre évaluation.");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);
	
			int result = fileChooser.showSaveDialog(frame);
	
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				File dir = fileChooser.getSelectedFile();
				this.lblChemin.setText(dir.getAbsolutePath());
			}
		}
	}
}