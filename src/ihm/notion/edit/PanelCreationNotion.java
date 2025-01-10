package ihm.notion.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;

/** 
 * Classe JPanel utilisée lorsqu'on crée une notion.
 * Cette classe hérite de 'PanelEditNotion' pour réutiliser la structure de formulaire
 * et ajoute la logique spécifique à la création de nouvelles notions.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelCreationNotion extends PanelEditNotion
{
	/**
	 * Constructeur de la classe PanelCreationNotion.
	 * 
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public PanelCreationNotion(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
	}

	/**
	* Méthode pour enregistrer une nouvelle notion.
	* 
	* @return true si la notion a été créée avec succès, false sinon.
	 */
	@Override
	public boolean enregistrer() 
	{
		// Récupération des valeurs saisies par l'utilisateur.
		String nom = this.txtNom.getText();

		/*--------------------------*/
		/* Vérification des Erreurs */
		/*--------------------------*/
		List<String> lstErreurs = new ArrayList<String>();

		// Vérification du champ "nom".
		if(nom.isEmpty()) 
			lstErreurs.add("Le nom est vide");
		else if(this.ctrl.getNotionParNom(this.ctrl.getRessourceSelectionnee().getCode(), nom) != null)
			lstErreurs.add("La notion existe déjà");

		// Si des erreurs ont été détectées, affichage d'un message d'erreur.
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

		if(this.ctrl.getRessourceSelectionnee() != null)
			this.ctrl.creerNotion(this.ctrl.getRessourceSelectionnee().getCode(), this.txtNom.getText());
		
		this.ihm.reinitAffichageRessource();	
		this.ihm.reinitAffichageNotion();
		return true;
	}
}
