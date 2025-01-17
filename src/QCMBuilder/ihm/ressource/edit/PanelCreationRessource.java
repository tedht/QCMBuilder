package QCMBuilder.ihm.ressource.edit;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import QCMBuilder.controleur.Controleur;
import QCMBuilder.ihm.IHM;

/** 
 * Classe JPanel utilisée lorsqu'on crée une ressource.
 * Cette classe hérite de 'PanelEditRessource' pour réutiliser la structure de formulaire
 * et ajoute la logique spécifique à la création de nouvelles ressources.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelCreationRessource extends PanelEditRessource
{
	/**
	 * Constructeur de la classe PanelCreationRessource.
	 * 
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public PanelCreationRessource(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
	}

	/**
	 * Méthode pour enregistrer une nouvelle ressource.
	 * 
	 * @return true si la ressource a été créée avec succès, false sinon.
	 */
	@Override
	public boolean enregistrer() 
	{
		String code;
		String nom;

		List<String> lstErreurs;

		String message;
		
		// Récupération des valeurs saisies par l'utilisateur.
		code = this.txtCode.getText();
		nom  = this.txtNom .getText();

		/*--------------------------*/
		/* Vérification des Erreurs */
		/*--------------------------*/

		lstErreurs = new ArrayList<String>();

		// Vérification du champ "code".
		if (code.isEmpty())
			lstErreurs.add("Le code est vide.");
		else if(this.ctrl.getRessource(code) != null)
			lstErreurs.add("Le code existe déjà.");

		// Vérification du champ "nom".
		if (nom.isEmpty()) 
			lstErreurs.add("Le nom est vide");

		// Si des erreurs ont été détectées, affichage d'un message d'erreur.
		if(lstErreurs.size() != 0)
		{
			message = "La ressource n'a pas été enregistrée pour les raisons suivantes :\n";
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
		this.ctrl.creerRessource(code, nom);

		this.ihm.reinitAffichageRessource();
		this.ihm.reinitAffichageNotion();

		return true;
	}
}
