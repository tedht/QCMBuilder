package metier;

import java.util.List;

public class Elimination {

	private List<String>	proposition;
	private String			reponse;
	private List<Integer>	ordreElimination;
	private List<Integer>	nbPtPerdu;

	public Elimination(List<String> proposition, String reponse, List<Integer> ordreElimination, List<Integer> nbPtPerdu) {
		this.proposition		= proposition;
		this.reponse			= reponse;
		this.ordreElimination	= ordreElimination;
		this.nbPtPerdu			= nbPtPerdu;
	}

	/*---------*/
	/* Getteur */
	public List<String> getProposition()			{ return this.proposition;		}
	public String getReponse()						{ return this.reponse;			}
	public List<Integer> getOrdreElimination()		{ return this.ordreElimination;	}
	public List<Integer> getNbPtPerdu()				{ return this.nbPtPerdu;		}

	/*---------*/
	/* Setteur */
	public void setReponse(String reponse)	{ this.reponse = reponse;		}

	public boolean ajouterProposition(String newProposition) {
		for(String proposition : this.proposition) {
			if(proposition == newProposition) { return false; }
		}

		this.proposition.add(newProposition);
		return true;
	}

	public boolean modifierProposition(int id, String proposition) {
		if(!this.proposition.get(id).equals(proposition)) { return false; }

		this.proposition.set(id, proposition);
		return true;
	}

	public boolean supprimerProposition(int id) {
		if(this.proposition.get(id).isEmpty()) { return false; }

		this.proposition.remove(id);
		return true;
	}

	/* toString */
	public String toString()
	{
		return	"Question Elimination :\n" +
				"Propositions          : " + this.proposition      + "\n" +
				"Réponse               : " + this.reponse          + "\n" +
				"Ordre d'élimination   : " + this.ordreElimination + "\n" +
				"Nombre de point perdu : " + this.nbPtPerdu        + "\n" ;
	}
}
