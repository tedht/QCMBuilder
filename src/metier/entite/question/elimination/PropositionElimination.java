package metier.entite.question.elimination;

import metier.entite.question.Proposition;

/**
 * Classe PropositionElimination.
 * 
 * @author Equipe 03
 * @version 1.0 2024/16/12
 */
public class PropositionElimination implements Proposition
{



	/*-----------*/
	// Attributs //
	/*-----------*/

	private String  text;
	private boolean reponse;
	private int     ordreElim;
	private double  nbPtsPerdus;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur d'une proposition d'une question Elimination.
	 * 
	 * @param text        le text associé.
	 * @param reponse     true si la reponse est vraie, false sinon.
	 * @param ordreElim   l'ordre dans lequel éliminer la proposition.
	 * @param nbPtsPerdus le nombre de points perdus.
	 */
	public PropositionElimination(String text, boolean reponse, int ordreElim, double nbPtsPerdus)
	{
		this.text        = text;
		this.reponse     = reponse;
		this.ordreElim   = ordreElim;
		this.nbPtsPerdus = nbPtsPerdus;
	}



	/*--------*/
	// GETTER //
	/*--------*/

	/**
	 * Retourne le text associé à la proposition.
	 * 
	 * @return le text.
	 */
	public String getText () { return this.text; }

	/**
	 * Retourne la reponse associée à la proposition.
	 * 
	 * @return true si la reponse est vrais, false sinon.
	 */
	public boolean estReponse() { return this.reponse; }

	/**
	 * Retourne le numéro dans l'ordre d'Elimination des propositions.
	 * Exemple : Si l'ordre d'élimination = 2, la proposition sera la seconde supprimer de la question.
	 * 
	 * @return l'ordre d'élimination.
	 */
	public int getOrdreElim() { return this.ordreElim; }

	/**
	 * Retourne le nombre de points perdus lors de l'élimination de la proposition.
	 * 
	 * @return le nombre de points perdus.
	 */
	public double getNbPtsPerdus() { return this.nbPtsPerdus; }



	/*--------*/
	// SETTER //
	/*--------*/

	/**
	 * Modifie le text associé à la Proposition.
	 * 
	 * @param text le nouveau text.
	 */
	public void setText(String text) { this.text = text; }

	/**
	 * Modifie la réponse associée à la Proposition.
	 * 
	 * @param reponse la nouvelle réponse.
	 */
	public void setReponse(boolean reponse) { this.reponse = reponse; }

	/**
	 * Modifie l'ordre d'élimination associé à la Proposition.
	 * 
	 * @param ordreElim le nouvelle ordre.
	 */
	public void setOrdreElim(int ordreElim ) { this.ordreElim = ordreElim; }

	/**
	 * Modifie le nombre de point perdus associés à la Proposition.
	 * 
	 * @param nbPtsPerdus le nouveau nombre de points perdus.
	 */
	public void setNbPtsPerdus(double nbPtsPerdus) { this.nbPtsPerdus = nbPtsPerdus; }
}