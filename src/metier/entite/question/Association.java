 package metier.entite.question;

import java.util.ArrayList;
import java.util.List;

import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.Difficulte;

import java.util.HashMap;
import java.util.Collection;

/** Classe Association
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Association extends Question
{
	/* Attributs */
	private List<String> lstPropositons;
	private List<String> lstReponses;
	private HashMap<String, String> liaisons;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public Association(	int id, String intitule, String explication, Difficulte difficulte,
						Ressource ressource, Notion notion, int temps, int note)
	{
		super(id, intitule, explication, difficulte, ressource, notion, temps, note);

		this.lstPropositons = new ArrayList<String>();
		this.lstReponses    = new ArrayList<String>();
		this.liaisons       = new HashMap<String, String>();
	}

	/*----------*/
	/* Getteurs */
	/*----------*/

	public List<String>            getPropositions() { return this.lstPropositons; }
	public List<String>            getReponses    () { return this.lstReponses;    }
	public HashMap<String, String> getLiaison     () { return this.liaisons;       }

	/* Ajouter une proposition */
	public boolean ajouterProposition(String proposition)
	{
		if (proposition == null)    return false;
		if (proposition.equals("")) return false;
		this.lstPropositons.add(proposition);
		return true;
	}

	/* Ajouter une réponse */
	public boolean ajouterReponse(String reponse)
	{
		if (reponse  == null)       return false;
		if (reponse.equals(""))     return false;

		this.lstReponses.add(reponse);
		return true;
	}


	/* Modifier */
	public boolean modifierProposition(String propositionActuelle, String nouvelleProposition)
	{
		if(this.lstPropositons.isEmpty()) { return false; } // Si la liste est vide -> renvoie faux

		if (propositionActuelle == null) return false;
		if (propositionActuelle.equals("")) return false;

		if (nouvelleProposition == null) return false;
		if (nouvelleProposition.equals("")) return false;

		for(int cpt = 0; cpt < this.lstPropositons.size(); cpt++)
		{
			if(this.lstPropositons.get(cpt).equals(propositionActuelle))
			{
				this.lstPropositons.set(cpt, nouvelleProposition);
			}
		}

		
		return true;
	}

	public boolean modifierReponse(String reponseActuelle, String nouvelleReponse)
	{
		if(this.lstReponses.isEmpty()) return false; // Si la liste est vide -> renvoie faux

		if (reponseActuelle == null) return false;
		if (reponseActuelle.equals("")) return false;

		if (nouvelleReponse == null) return false;
		if (nouvelleReponse.equals("")) return false;

		for (int cpt = 0 ; cpt < this.lstReponses.size() ; cpt++)
			if (this.lstReponses.get(cpt).equals(reponseActuelle))
				this.lstReponses.set(cpt, nouvelleReponse);

		return true;
	}


	/* Supprimer une proposition */
	public boolean supprimerProposition(String proposition)
	{
		if(this.lstPropositons.isEmpty()) { return false; } // Si la liste est vide -> renvoie faux
		
		this.lstPropositons.remove(proposition);
		return true;
	}

	/* Supprimer une réponse */
	public boolean supprimerReponse(String reponse)
	{
		if(this.lstReponses.isEmpty()) return false; // Si la liste est vide -> renvoie faux

		this.lstReponses.remove(reponse);

		return true;
	}


	/* toString */
	/*public String toString()
	{
		String result = "";

		if(this.proposition.isEmpty()) { return "Il n'y a pas de propostions"; }

		for (List<String> proposition : this.proposition)
		{
			result += proposition.get(0) + " --> " + proposition.get(1) + "\n"; 
		}

		return result;
	}*/

	public static void main(String[] args)
	{
		/*Association asso, asso2;


		asso = new Association(0, null, null, null, null, null, 0, 0);
		asso.ajouterAssociation("Prop1", "Rep1");
		asso.ajouterAssociation("Prop2", "Rep2");
		System.out.println(asso);

		asso.modifierReponse(0, "Rep3");
		asso.ajouterAssociation("Prop3", "Rep1");
		System.out.println(asso);

		asso.supprimerAssociation(1);
		asso.supprimerAssociation(-1);
		asso.supprimerAssociation(4); // Ne le fait pas car en dehors de la liste
		System.out.println(asso);

		asso2 = new Association(0, null, null, null, null, null, 0, 0);
		asso2.supprimerAssociation(0);*/
	}

}
