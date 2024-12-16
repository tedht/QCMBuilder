package metier.entite.question.elimination;

import metier.entite.Ressource;
import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

/**
 * Classe Elimination héritant de Question
 * 
 * @author Equipe 03
 * @version 1.0 2024/12/12
 */
public class Elimination extends Question
{



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur d'une question Elimination.
	 * 
	 * @param ressource  la ressource associée
	 * @param notion     la notion associée.
	 * @param difficulte la difficultée associée.
	 * @param temps      le temps associé.
	 * @param note       la note associée.
	 * @param pieceJointe le chemin de la pièce jointe.
	 */
	public Elimination(Ressource ressource, String notion, Difficulte difficulte, int temps, double note, String pieceJointe)
	{
		super(ressource, notion, difficulte, temps, note, pieceJointe);
	}

	/**
	 * Constructeur d'une question Elimination sans pièce jointe.
	 * 
	 * @param ressource  la ressource associée
	 * @param notion     la notion associée.
	 * @param difficulte la difficultée associée.
	 * @param temps      le temps associé.
	 * @param note       la note associée.
	 */
	public Elimination(Ressource ressource, String notion, Difficulte difficulte, int temps, double note)
	{
		super(ressource, notion, difficulte, temps, note);
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
	 * Retourne le type de la question, étant défini comme une question d'élimination.
	 * 
	 * @return le type de question ELIMINATION.
	 */
	@Override
	public TypeQuestion getType()
	{
		return TypeQuestion.ELIMINATION;
	}



	/*---------*/
	// Setters //
	/*---------*/

	/**
	 * Modifie la proposition dans la liste des propositions associées à la Question.
	 * 
	 * 
	 * @param  i    l'index de la proposition à définir.
	 * @param  prop la nouvelle proposition.
	 * @return      true si la proposition à été modifiée, false sinon.
	 * @see         super#setProposition(int, PropositionElimination).
	 */
	public boolean setProposition(int i, PropositionElimination prop)
	{
		return super.setProposition(i, prop);
	}



    /*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	 * Ajout d'une proposition à la liste des propositions de Question.
	 * 
	 * @param  prop la proposition à ajouter.
	 * @return      true si la proposition à été ajouter, false sinon.
	 * @see         super#ajouterProposition(PropositionElimination).
	 */
	public boolean ajouterProposition(PropositionElimination prop)
	{
		return super.ajouterProposition(prop);
	}

	/**
     * Retourne une représentation en chaîne de caractères d'une question élimination.
     * 
     * @return une représentation en chaîne de caractères d'une question élimination.
     */
	public String toString()
    {
        String                 result = "";
		PropositionElimination prop;
        for(int i = 0; i < this.lstPropositions.size(); i++)
        {
			prop = (PropositionElimination)this.lstPropositions.get(i);

            result +=	"TextProposition         : " + prop.getText       () + "\n" +
						"Réponse                 : " + prop.estReponse    () + "\n" +
						"Numéro d'élimination    : " + prop.getOrdreElim  () + "\n" +
						"Nombre de points perdus : " + prop.getNbPtsPerdus() + "\n\n";
        }

        return result;
    }
}
