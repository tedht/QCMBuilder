package QCMBuilder.metier.entite.question.association;


import QCMBuilder.metier.entite.question.Difficulte;
import QCMBuilder.metier.entite.question.Proposition;
import QCMBuilder.metier.entite.question.Question;
import QCMBuilder.metier.entite.question.TypeQuestion;

/**
 * Classe Association qui correspond aux questions de type Association (hérite de Question).
 *
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Association extends Question
{



	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe Association.
	 * 
	 * @param codeRes    l'identifiant de la ressource.
	 * @param idNot      l'identifiant de la notion.
	 * @param idQst      l'identifiant de la question.
	 * @param note       la note.
	 * @param temps      le temps associée à la question.
	 * @param difficulte la difficultée.
	 * 
	 * @see Question la classe Question parente de Association.
	 */
	public Association(String codeRes, int idNot, int idQst, double note, int temps, Difficulte difficulte)
	{
		super(codeRes, idNot, idQst, note, temps, difficulte);
	}



	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le type de la question.
	 * 
	 * @return  le type de question ASSOCIATION.
	 */
	@Override
	public TypeQuestion getType() { return TypeQuestion.ASSOCIATION; }

	/**
	 * Retourne une proposition association.
	 * 
	 * @param  i indice de la proposition.
	 * @return   la proposition.
	 */
	public PropositionAssociation getProposition(int i) { return (PropositionAssociation) super.getProposition(i); }



	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie une proposition Association.
	 * 
	 * @param  i    indice de la proposition à modifier.
	 * @param  prop la proposition qui va modifier l'ancienne.
	 * @return      true si la modification a été effectuée, false sinon.
	 */
	public boolean setProposition(int i, PropositionAssociation prop) { return super.setProposition(i, prop); }


	
	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Ajoute une proposition Association.
	 * 
	 * @param  prop la proposition à ajouter.
	 * @return true si la proposition a été ajoutée, false sinon.
	 */
	public boolean ajouterProposition(PropositionAssociation prop)
	{
		return super.ajouterProposition(prop);
	}

	/**
	 * Retourne une représentation en chaîne de caractères d'une proposition Association.
	 * 
	 * @return une représentation en chaîne de caractères d'une proposition Association.
	 */
	public String toString()
	{
		String sRet;


		if (this.lstPropositions.isEmpty()) { return "Il n'y a pas de propositions"; }

		sRet = "";
		for (Proposition proposition : this.lstPropositions)
			 sRet += proposition + "\n";

		return sRet;
	}
}