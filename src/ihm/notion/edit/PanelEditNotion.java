package ihm.notion.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;


/** Classe JPanel de la fenêtre d'édition d'une notion
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelEditNotion extends PanelEditNom
{
	/**
	 * Constructeur de la classe PanelEditRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditNotion(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.lblNom.setText("Nom de la notion :");

		this.panelInfo.add(this.panelInfoNom);
	}

	public boolean enregistrer()
	{
		String nom = this.txtNom.getText();

		List<String> lstErreurs = new ArrayList<String>();

		if(nom.isEmpty()) 
			lstErreurs.add("Le nom est vide");
		else if(this.ctrl.getNotionParNom(this.ctrl.getRessourceActive().getCode(), nom) != null)
			lstErreurs.add("La notion existe déjà");

		if(lstErreurs.size() != 0)
		{
			String message = "La notion n'a pas été enregistrée pour les raisons suivantes :\n";
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

		if(this.ctrl.getRessourceActive() != null)
			this.ctrl.creerNotion(this.ctrl.getRessourceActive().getCode(), this.txtNom.getText());
		this.ihm.reinitAffichageNotion();
		return true;
	}
}