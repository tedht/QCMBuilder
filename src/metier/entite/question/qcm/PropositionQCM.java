package metier.entite.question.qcm;

import metier.entite.question.Proposition;

public class PropositionQCM implements Proposition
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private String  text;
	private boolean reponse;

	/*--------------*/
	// Constructeur //
	/*--------------*/
	public PropositionQCM(String text, boolean reponse)
	{
		this.text    = text;
		this.reponse = reponse;
	}

	/*---------*/
	/* Getters */
	/*---------*/

	public String  getText   () { return this.text;    }
	public boolean estReponse() { return this.reponse; }

	/*---------*/
	/* Setters */
	/*---------*/
	public void setText   (String  text)   { this.text    = text;    }
	public void setReponse(boolean reponse){ this.reponse = reponse; }
}