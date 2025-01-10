package QCMBuilder.metier.entite.question.qcm;


import QCMBuilder.metier.entite.question.Proposition;

/**
 * Classe PropositionQCM qui correspond aux propositions des questions de type QCM (implémente Proposition).
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
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe PropositionQCM.
	 * 
	 * @param text    le texte associée à la proposition.
	 * @param reponse true si la proposition est correcte, false sinon.
	 * 
	 * @see QCM la classe QCM.
	 */
	public PropositionQCM(String text, boolean reponse)
	{
		this.text    = text;
		this.reponse = reponse;
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le texte associée à la proposition QCM.
	 * 
	 * @return le texte.
	 */
	public String getText() { return this.text; }

	/**
	 * Retourne si la proposition QCM est correcte.
	 * 
	 * @return true si la proposition est correcte, false sinon.
	 */
	public boolean estReponse() { return this.reponse; }


	
	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie le texte associée à la proposition QCM.
	 * 
	 * @param text le nouveau texte.
	 */
	public void setText (String  text) { this.text = text; }

	/**
	 * Modifie la réponse associée à la proposition QCM.
	 * 
	 * @param reponse la nouvelle réponse.
	 */
	public void setReponse(boolean reponse) { this.reponse = reponse; }
}