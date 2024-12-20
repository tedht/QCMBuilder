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
	// Attributs //
	/*-----------*/

	private String  text;
	private boolean reponse;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe PropositionQCM.
	 * 
	 * @param text    le texte associée à la proposition.
	 * @param reponse true si la proposition est correcte, false sinon.
	 */
	public PropositionQCM(String text, boolean reponse)
	{
		this.text    = text;
		this.reponse = reponse;
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
	 * Retourne le texte associée à la proposition.
	 * 
	 * @return le texte.
	 */
	public String getText() { return this.text; }

	/**
	 * Retourne l'état de la proposition.
	 * 
	 * @return true si la proposition est correcte, false sinon.
	 */
	public boolean estReponse() { return this.reponse; }



	/*---------*/
	// Setters //
	/*---------*/

	/**
	 * Modifie le texte associée à la proposition.
	 * 
	 * @param text le nouveau texte.
	 */
	public void setText (String  text) { this.text = text; }

	/**
	 * Modifie l'état de la réponse associée à la proposition.
	 * 
	 * @param reponse la nouvelle réponse.
	 */
	public void setReponse(boolean reponse) { this.reponse = reponse; }
}