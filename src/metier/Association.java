package metier;

import java.util.ArrayList;
import java.util.List;

public class Association extends Question {

	private List<List<String>> proposition;

	public Association(	int id, String intitule, String explication, String difficulte,
						Ressource ressource, Notion notion, int temps, int note)
	{
			super(id, intitule, explication, difficulte, ressource, notion, temps, note);
	}

	public List<List<String>> getProposition()	{ return this.proposition; }


	/* Ajouter */
	public boolean ajouterAssociation(String newProposition, String newReponse) {
		for(List<String> proposition : this.proposition) {
			// Si la proposition existe déjà -> renvoie faux
			if(proposition.get(0) == newProposition) { return false; }
			// Si la réponse existe déjà -> renvoie faux
			if(proposition.get(1) == newReponse)     { return false; }
		}

		List<String> association = new ArrayList<>();
		association.add(newProposition);
		association.add(newReponse);

		this.proposition.add(association);
		return true;
	}

	/* Modifier */
	public boolean modifierProposition(int id, String newProposition) {
		for(List<String> proposition : this.proposition) {
			if(!this.proposition.get(id).equals(newProposition)) { return false; }
		}

		List<String> association = this.proposition.get(id);
		association.set(0, newProposition);
		this.proposition.set(id, association);
		return true;
	}

	public boolean modifierReponse(int id, String newReponse) {
		for(List<String> proposition : this.proposition) {
			if(!this.proposition.get(id).equals(newReponse)) { return false; }
		}

		List<String> association = this.proposition.get(id);
		association.set(0, newReponse);
		this.proposition.set(id, association);
		return true;
	}

	/* Supprimer une association P */
	public boolean supprimerAssociation(int id) {
		if(this.proposition.get(id).isEmpty()) { return false; }

		this.proposition.remove(id);
		return true;
	}

	/* toString */
	public String toString()
	{
		String result = "";

		for (List<String> proposition : this.proposition)
		{
			result += proposition.get(0) + " --> " + proposition.get(1) + "\n"; 
		}

		return result;
	}

}
