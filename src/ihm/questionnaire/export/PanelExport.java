package ihm.questionnaire.export;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;

/**
 * Classe JPanel de la fenêtre d'édition d'une notion
 * 
 * @author Ted Herambert
 * @date 2024/12/18
 * @version 1.0
 */
public class PanelExport extends PanelEditNom
{
	private JPanel     panelInfoChemin;

	private JTextField txtChemin;
	private JButton    btnNavig;
	
	/**
	 * Constructeur de la classe PanelEditRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelExport(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);

		JPanel panelNavig;

		this.panelInfo.setLayout(new GridBagLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.lblNom.setText("Nom de l'évaluation :");

		this.panelInfoChemin = new JPanel(new BorderLayout());
		panelNavig           = new JPanel();
		this.txtChemin       = new JTextField(30);
		this.btnNavig        = new JButton("N");

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

		this.panelInfoChemin.add(new JLabel("Chemin :"), BorderLayout.NORTH);
		this.panelInfoChemin.add(this.txtChemin, BorderLayout.CENTER);
		this.panelInfoChemin.add(panelNavig, BorderLayout.EAST);
		panelNavig          .add(this.btnNavig);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnNavig.addActionListener(this);
	}

	public String getType()
	{
		return "Notion";
	}

	public boolean valider()
	{
		if(this.ctrl.getRessourceActive().getNotion(this.txtNom.getText()) == null)
		{
			this.ctrl.creerNotion(this.txtNom.getText());
			//this.ihm.reinitAffichage();
			return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		super.actionPerformed(e);

		if(e.getSource() == this.btnNavig)
		{
			// navig
		}
	}
}