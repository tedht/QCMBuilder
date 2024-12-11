package ihm.edition;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import controleur.Controleur;
import ihm.FrameQCMBuilder;

/**
 * Classe JPanel générique utilisé par la frames d'édition de ressource et la frames d'édition de notion
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public abstract class PanelEditionEntite extends JPanel implements ActionListener
{
	protected Controleur      ctrl;
	protected FrameQCMBuilder framePrincipale;

	protected JPanel        panelInfo, panelAction;
	protected JButton       btnAnnuler, btnValider, btnOuvrirExplorateur;
	protected JLabel        lblNom;
	protected JTextField    txtNom, txtImage;
	protected JToggleButton toggleImage;

	/**
	 * Constructeur de la classe PanelEditionEntite.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditionEntite(Controleur ctrl, FrameQCMBuilder framePrincipale)
	{
		this.ctrl            = ctrl;
		this.framePrincipale = framePrincipale;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		// Panel Info
		this.panelInfo = new JPanel(new GridBagLayout());
		this.panelInfo.setBorder(new EmptyBorder(10,10,10,10));
		
		// Panel Action
		this.panelAction   = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));

		this.lblNom   = new JLabel("Nom de la " + this.getType() + " : ");

		this.txtNom   = new JTextField(30);
		this.txtImage = new JTextField(30); 
		this.txtImage.setVisible(false);
		this.txtImage.setEditable(false);

		this.toggleImage = new JToggleButton("Image");

		this.btnAnnuler           = new JButton("Annuler");
		this.btnValider           = new JButton("Valider");
		this.btnOuvrirExplorateur = new JButton("Fic");
		this.btnOuvrirExplorateur.setVisible(false);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		// Panel Info
		this.add(this.panelInfo, BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 10, 0);
		
        gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx   = 0;
		gbc.anchor    = GridBagConstraints.WEST;
		gbc.fill      = GridBagConstraints.NONE;
        this.panelInfo.add(this.lblNom, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
		gbc.weightx   = 1.0;
		gbc.anchor    = GridBagConstraints.WEST;
		gbc.fill      = GridBagConstraints.HORIZONTAL;
        this.panelInfo.add(this.txtNom, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx   = 0;
		gbc.anchor    = GridBagConstraints.WEST;
		gbc.fill      = GridBagConstraints.NONE;
        this.panelInfo.add(this.toggleImage, gbc);

		gbc.gridx = 1; gbc.gridy = 2;
		gbc.anchor    = GridBagConstraints.EAST;
		gbc.fill      = GridBagConstraints.NONE;
		this.panelInfo.add(this.btnOuvrirExplorateur, gbc);

		gbc.gridx = 0; gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0; 
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.panelInfo.add(this.txtImage, gbc);

		// Panel Action
		this.add(this.panelAction, BorderLayout.SOUTH);
		this.panelAction.add(this.btnAnnuler);
		this.panelAction.add(this.btnValider);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAnnuler          .addActionListener(this);
		this.btnValider          .addActionListener(this);
		this.btnOuvrirExplorateur.addActionListener(this);
		this.toggleImage         .addActionListener(this);
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
		if(e.getSource() == this.toggleImage)
		{
			System.out.println(toggleImage.isSelected());
			if(toggleImage.isSelected())
			{
				this.btnOuvrirExplorateur.setVisible(true);
			}
			else
			{
				this.btnOuvrirExplorateur.setVisible(false);
				this.txtImage            .setVisible(false);
			}
			this.revalidate();
			this.repaint();
			SwingUtilities.getWindowAncestor(this).pack();
		}
		if(e.getSource() == this.btnOuvrirExplorateur)
		{
			JFileChooser explorateurFichiers = new JFileChooser();
			explorateurFichiers.setFileSelectionMode(JFileChooser.FILES_ONLY);

			explorateurFichiers.setFileFilter(new FileFilter(){
				@Override
				public boolean accept(File f) 
				{
					if (f.isDirectory()) return true;
					
					String nomFichier = f.getName().toLowerCase();
					
					return    nomFichier.endsWith(".png" ) 
					       || nomFichier.endsWith(".jpg" ) 
						   || nomFichier.endsWith(".jpeg") 
						   || nomFichier.endsWith(".gif" );
				}

				@Override
				public String getDescription() 
				{
					return "Fichiers d'images (*.png, *.jpg, *.jpeg, *.gif)";
				}
			});

			int retour = explorateurFichiers.showOpenDialog(this);

			if (retour == JFileChooser.APPROVE_OPTION) {
				File fichierSelectionne = explorateurFichiers.getSelectedFile();
				JOptionPane.showMessageDialog(this,
						"Image sélectionnée : " + fichierSelectionne.getAbsolutePath(),
						"Image Sélectionnée",
						JOptionPane.INFORMATION_MESSAGE);
				
				this.txtImage.setVisible(true);
				this.txtImage.setText(fichierSelectionne.getAbsolutePath());
				this.revalidate();
				this.repaint();
				SwingUtilities.getWindowAncestor(this).pack();
			}
		}
	}

	public abstract String  getType();
	public abstract boolean valider();
}
