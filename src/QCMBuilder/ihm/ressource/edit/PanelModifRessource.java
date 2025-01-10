package QCMBuilder.ihm.ressource.edit;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import QCMBuilder.controleur.Controleur;
import QCMBuilder.ihm.IHM;

/** 
 * Classe JPanel utilisée lorsqu'on modifie une ressource.
 * Cette classe hérite de 'PanelEditRessource' pour réutiliser la structure de formulaire
 * et ajoute la logique spécifique à la modification de ressources existentes.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelModifRessource extends PanelEditRessource
{
	private String code;
	
	/**
	 * Constructeur de la classe PanelModifRessource
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 * @param code le code de la ressource qu'on veut modifier.
	 */
	public PanelModifRessource(Controleur ctrl, IHM ihm, String code) 
	{
		super(ctrl, ihm);
		this.code = code;

		this.txtCode.setText(code);
		this.txtNom .setText(this.ctrl.getRessource(code).getNom());
	}

	/**
	 * Méthode pour enregistrer les modifications effectuées sur une ressource existente.
	 * 
	 * @return true si la modification a réussi, false sinon.
	 */
	@Override
	public boolean enregistrer() 
	{
		// Récupération des valeurs saisies par l'utilisateur.
		String nouveauCode = this.txtCode.getText();
		String nouveauNom  = this.txtNom .getText();
		
		/*--------------------------*/
		/* Vérification des Erreurs */
		/*--------------------------*/
		List<String> lstErreurs = new ArrayList<String>();

		// Vérification du champ "code".
		if (nouveauCode.isEmpty())
			lstErreurs.add("Le code est vide.");
		else if(!nouveauCode.equals(this.code) && this.ctrl.getRessource(nouveauCode) != null)
			lstErreurs.add("Le code est déjà utilisé par une autre ressource.");

		// Vérification du champ "nom".
		if (nouveauNom.isEmpty()) 
			lstErreurs.add("Le nom est vide");

		// Si des erreurs ont été détectées, affichage d'un message d'erreur.
		if(lstErreurs.size() != 0)
		{
			String message = "La ressource n'a pas été modifée pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this,
				message,
				"Échec de la modification",
				JOptionPane.ERROR_MESSAGE
			);
			return false;
		}

		/*------------*/
		/* Traitement */
		/*------------*/
		this.ctrl.modifierRessource(code, nouveauCode, nouveauNom);

		this.ihm.reinitAffichageRessource();
		this.ihm.reinitAffichageNotion();

		return true;
	}

	
}
