package ihm.ressource.edit;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;


/** Classe JPanel de la fenêtre d'édition d'une ressource.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelEditRessource extends PanelEditNom
{
	private JTextField txtCode;

	private JPanel     panelInfoCode;
	
	/**
	 * Constructeur de la classe PanelEditRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditRessource(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
		
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.lblNom.setText("Nom de la ressource :");

		this.panelInfoCode = new JPanel(new BorderLayout());
		this.txtCode       = new JTextField(30);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.panelInfo.add(this.panelInfoCode);
		this.panelInfo.add(this.panelInfoNom);

		this.panelInfoCode.add(new JLabel("Code : "), BorderLayout.NORTH);
		this.panelInfoCode.add(this.txtCode, BorderLayout.CENTER);
	}

	public String getType()
	{
		return "Ressource";
	}

	public boolean enregistrer()
	{
		String code = this.txtCode.getText();
		String nom  = this.txtNom .getText();

		List<String> lstErreurs = new ArrayList<String>();

		if (code.isEmpty())
			lstErreurs.add("Le code est vide.");
		else if(this.ctrl.getRessource(code) != null)
			lstErreurs.add("Le code existe déjà.");

		if (nom.isEmpty()) 
			lstErreurs.add("Le nom est vide");

		if(lstErreurs.size() != 0)
		{
			String message = "La ressource n'a pas été enregistrée pour les raisons suivantes :\n";
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

		this.ctrl.creerRessource(code, nom);
		this.ihm.reinitAffichageRessource();
		return true;
	}
}
