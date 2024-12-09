package metier;

import java.util.List;

public class Qcm {

	private List<String>	proposition;
	private List<String>	reponse;

	public Qcm(List<String> proposition, List<String> reponse) {
		this.proposition	= proposition;
		this.reponse		= reponse;
	}

	/*---------*/
	/* Getteur */
	public List<String> getProposition()	{ return this.proposition;	}
	public List<String> getReponse()		{ return this.reponse;		}

	/*---------*/
	/* Setteur */
	public void setReponse(List<String> reponse)	{ this.reponse = reponse;	}

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
		return	"Question QCM :\n" +
				"Propositions : " + this.proposition + "\n" +
				"Réponse      : " + this.reponse     + "\n" ;
	}

}
