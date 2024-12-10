package metier;

import java.util.ArrayList;
import java.util.List;

import metier.Notion;
import metier.Ressource;

public class Association extends Question {

	/*---------*/
	/* Données */
	/*---------*/

	private List<List<String>> proposition;

	/*-----------*/
	/* Structure */
	/*-----------*/

	public Association(	int id, String intitule, String explication, String difficulte,
						Ressource ressource, Notion notion, int temps, int note) {

			super(id, intitule, explication, difficulte, ressource, notion, temps, note);
	}

	/*---------*/
	/* Getteur */
	public List<List<String>> getProposition()	{ return this.proposition; }

	/* Ajouter une association (Proposition, Réponse)*/
	public boolean ajouterAssociation(String newProposition, String newReponse) {
		for(List<String> proposition : this.proposition) {
			if(proposition.get(0) == newProposition) { return false; }	// Si la proposition existe déjà 	-> renvoie faux
			if(proposition.get(1) == newReponse)     { return false; }	// Si la réponse existe déjà 		-> renvoie faux
		}

		List<String> association = new ArrayList<>();	//Création de l'association (Proposition, Réponse)
		association.add(newProposition);
		association.add(newReponse);

		this.proposition.add(association);				// Ajout de l'association dans la liste des associations
		return true;
	}


	/* Modifier */
	public boolean modifierProposition(int id, String newProposition) {
		if(this.proposition.get(id).isEmpty()) { return false; }					// Si l'association n'éxiste pas	-> renvoie faux

		for(List<String> proposition : this.proposition) {
			if(!this.proposition.get(id).equals(newProposition)) { return false; }	// Si il n'y a pas de modificatiomn	-> renvoie faux
		}

		List<String> association = this.proposition.get(id);	// Récupération de l'association
		association.set(0, newProposition);				// Modification de Proposition dans l'association
		this.proposition.set(id, association);					// Modifications dans la liste des associations avec l'association modifiée
		return true;
	}

	public boolean modifierReponse(int id, String newReponse) {
		if(this.proposition.get(id).isEmpty()) { return false; }				// Si l'association n'éxiste pas	-> renvoie faux

		for(List<String> proposition : this.proposition) {
			if(!this.proposition.get(id).equals(newReponse)) { return false; }	// Si il n'y a pas de modificatiomn	-> renvoie faux
		}

		List<String> association = this.proposition.get(id);	// Récupération de l'association
		association.set(0, newReponse);					// Modification de Réponse dans l'association
		this.proposition.set(id, association);					// Modifications dans la liste des associations avec l'association modifiée
		return true;
	}


	/* Ajouter une association (Proposition, Réponse) */
	public boolean supprimerAssociation(int id) {
		if(this.proposition.get(id).isEmpty()) { return false; }

		this.proposition.remove(id);
		return true;
	}


	/* toString */
	public String toString() {
		String result = "";

		for (List<String> proposition : this.proposition) {
			result += proposition.get(0) + " --> " + proposition.get(1) + "\n"; 
		}

		return result;
	}

}
