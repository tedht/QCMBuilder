package metier.entite.question;

import java.util.List;

import metier.entite.Notion;
import metier.entite.Ressource;

public class Elimination extends Question
{
	/* Attributs */
	private List<String>    proposition;
	private String          reponse;
	private List<Integer>   ordreElimination;
	private List<Integer>   nbPtPerdu;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public Elimination(int id, String intitule, String explication, Difficulte difficulte, Ressource ressource, Notion notion, 
	                   int temps,int note ,List<String> proposition, String reponse, List<Integer> ordreElimination, List<Integer> nbPtPerdu)
	{
		super(id, ressource, notion, difficulte, temps, note);

	}


	/*----------*/
	/* Getteurs */
	/*----------*/

	public List<String>  getProposition     () { return this.proposition;      }
	public String        getReponse         () { return this.reponse;          }
	public List<Integer> getOrdreElimination() { return this.ordreElimination; }
	public List<Integer> getNbPtPerdu       () { return this.nbPtPerdu;        }

	@Override
	public TypeQuestion getType() { return TypeQuestion.ELIMINATION; }

	/*----------*/
	/* Setteurs */
	/*----------*/

	public void setReponse(String reponse) { this.reponse = reponse; }
	public void setOrdreElimination(List<Integer> ordreElimination)
	{
		this.ordreElimination = ordreElimination;
	}
	public void setNbPtPerdu(List<Integer> nbPtPerdu)
	{
		this.nbPtPerdu = nbPtPerdu;
	}


	/* Ajouter une proposition */
	public boolean ajouterProposition(String newProposition)
	{
		for(String proposition : this.proposition)
		{
			if (proposition == newProposition) { return false; } // Si la proposition existe déjà -> renvoie faux
		}

		this.proposition.add(newProposition); // Ajoute la proposition
		return true;
	}

	/* Modifie une propoition */
	public boolean modifierProposition(int id, String proposition)
	{
		if ( id > this.proposition.size() || id < 0 )     { return false; } // Si l'id est en dehors de la liste                                                -> renvoie faux
		if (this.proposition.isEmpty())                   { return false; } // Si la liste est vide                                                             -> renvoie faux
		if (this.proposition.get(id).equals(proposition)) { return false; } // Si la proposition modifié est égale à la même chose que la nouvelle proposition  -> renvoie faux

		this.proposition.set(id, proposition); // Modifie la proposition
		return true;
	}


	/* Supprimer une proposition */
	public boolean supprimerProposition(int id)
	{
		if(id>this.proposition.size() || id<0)  { return false; } // Si l'id est en dehors de la liste -> renvoie faux
		if(this.proposition.isEmpty())          { return false; } // Si la liste est vide              -> renvoie faux

		this.proposition.remove(id); // Supprime la proposition
		return true;
	}


	/* toString */
	public String toString()
	{
		return  "Question Elimination :\n" +
		        "Propositions          : " + this.proposition      + "\n" +
		        "Réponse               : " + this.reponse          + "\n" +
		        "Ordre d'élimination   : " + this.ordreElimination + "\n" +
		        "Nombre de point perdu : " + this.nbPtPerdu        + "\n" ;
	}

}
