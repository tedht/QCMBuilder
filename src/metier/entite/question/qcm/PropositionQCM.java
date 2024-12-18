package metier.entite.question.qcm;

import metier.entite.question.Proposition;

/** Classe PropositionQCM
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
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