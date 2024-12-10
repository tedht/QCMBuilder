package metier;

import java.util.ArrayList;
import java.util.List;

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
			proposition = new ArrayList<>();
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
		if(id>this.proposition.size() || id<0)	{ return false; }	// Si l'id est en dehors de la liste				-> renvoie faux
		if(this.proposition.isEmpty())			{ return false; }	// Si la liste est vide								-> renvoie faux
		if(this.proposition.get(id).isEmpty())	{ return false; }	// Si l'association n'éxiste pas					-> renvoie faux

		for(List<String> proposition : this.proposition) {
			if(!this.proposition.get(id).equals(newProposition)) { return false; }	// Si il n'y a pas de modificatiomn	-> renvoie faux
		}

		List<String> association = this.proposition.get(id);	// Récupération de l'association
		association.set(0, newProposition);				// Modification de Proposition dans l'association
		this.proposition.set(id, association);					// Modifications dans la liste des associations avec l'association modifiée
		return true;
	}

	public boolean modifierReponse(int id, String newReponse) {
		if(id>this.proposition.size() || id<0)	{ return false; }	// Si l'id est en dehors de la liste				-> renvoie faux
		if(this.proposition.isEmpty())			{ return false; }	// Si la liste est vide					-> renvoie faux
		if(this.proposition.get(id).isEmpty())	{ return false; }	// Si l'association n'éxiste pas					-> renvoie faux

		for(List<String> proposition : this.proposition) {
			if(this.proposition.get(id).equals(newReponse)) { return false; }	// Si il n'y a pas de modification		-> renvoie faux
		}

		List<String> association = this.proposition.get(id);	// Récupération de l'association
		association.set(1, newReponse);					// Modification de Réponse dans l'association
		this.proposition.set(id, association);					// Modifications dans la liste des associations avec l'association modifiée
		return true;
	}


	/* Ajouter une association (Proposition, Réponse) */
	public boolean supprimerAssociation(int id) {
		if(id>this.proposition.size() || id<0)	{ return false; }	// Si l'id est en dehors de la liste	-> renvoie faux
		if(this.proposition.isEmpty())			{ return false; }	// Si la liste est vide					-> renvoie faux

		this.proposition.remove(id);
		return true;
	}


	/* toString */
	public String toString() {
		String result = "";

		if(this.proposition.isEmpty()) { return "Il n'y a pas de propostions"; }

		for (List<String> proposition : this.proposition) {
			result += proposition.get(0) + " --> " + proposition.get(1) + "\n"; 
		}

		return result;
	}

	public static void main(String[] args) {
		Association asso = new Association(0, null, null, null, null, null, 0, 0);
		asso.ajouterAssociation("Prop1", "Rep1");
		asso.ajouterAssociation("Prop2", "Rep2");
		System.out.println(asso);

		asso.modifierReponse(0, "Rep3");
		asso.ajouterAssociation("Prop3", "Rep1");
		System.out.println(asso);

		asso.supprimerAssociation(1);
		asso.supprimerAssociation(-1);
		asso.supprimerAssociation(4);	// Ne le fait pas car en dehors de la liste
		System.out.println(asso);

		Association asso2 = new Association(0, null, null, null, null, null, 0, 0);
		asso2.supprimerAssociation(0);

	}

}
