package ihm.notion.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;

/** 
 * Classe JPanel utilisée lorsqu'on modifie une notion.
 * Cette classe hérite de 'PanelEditNotion' pour réutiliser la structure de formulaire
 * et ajoute la logique spécifique à la modification de notions existentes.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelModifNotion extends PanelEditNotion
{
	private int idNot;

	/**
	 * Constructeur de la classe PanelModifRessource
	 * @param ctrl  le contrôleur.
	 * @param ihm   le gestionnaire des fenêtres de l'application.
	 * @param idNot l'id de la notion qu'on veut modifier'.
	 */
	public PanelModifNotion(Controleur ctrl, IHM ihm, int idNot)
	{
		super(ctrl, ihm);
		this.idNot = idNot;

		this.txtNom.setText(this.ctrl.getNotion(this.idNot).getNom());
	}

	/**
     * Méthode pour enregistrer les modifications effectuées sur une notion existente.
     * 
     * @return true si la modification a réussi, false sinon.
     */
	@Override
	public boolean enregistrer() 
	{
		// Récupération des valeurs saisies par l'utilisateur.
		String nouveauNom = this.txtNom.getText();

		/*--------------------------*/
		/* Vérification des Erreurs */
		/*--------------------------*/

		List<String> lstErreurs = new ArrayList<String>();

		// Vérification du champ "nom".
		if(nouveauNom.isEmpty()) 
			lstErreurs.add("Le nom est vide");
		else if(this.ctrl.getNotionParNom(this.ctrl.getRessourceSelectionnee().getCode(), nouveauNom) != null)
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

		/*------------*/
		/* Traitement */
		/*------------*/
		this.ctrl.modiferNotion(this.idNot, nouveauNom);
		
		this.ihm.reinitAffichageRessource();	
		this.ihm.reinitAffichageNotion();
		
		return true;
	}
}
