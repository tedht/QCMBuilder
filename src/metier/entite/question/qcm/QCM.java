package metier.entite.question.qcm;

import metier.entite.Ressource;
import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

/** Classe Qcm
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCM extends Question
{

	private boolean unique;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/** 
	 * Constructeur d'une question QCM.
	 * 
	 * @param ressource  la ressource associée
	 * @param notion     la notion associée.
	 * @param difficulte la difficultée associée.
	 * @param temps      le temps associé.
	 * @param note       la note associée.
	 * @param unique     si la question est unique.
	 * @param pieceJointe le chemin de la pièce jointe.
	 */
	public QCM(Ressource ressource, String notion, Difficulte difficulte, int temps, double note, boolean unique, String pieceJointe)
	{
		super(ressource, notion, difficulte, temps, note, pieceJointe);
		this.unique = unique;
	}

	/**
	 * Constructeur d'une question QCM sans pièce jointe.
	 * 
	 * @param ressource  la ressource associée
	 * @param notion     la notion associée.
	 * @param difficulte la difficultée associée.
	 * @param temps      le temps associé.
	 * @param note       la note associée.
	 * @param unique     si la question est unique.
	 */
	public QCM(Ressource ressource, String notion, Difficulte difficulte, int temps, double note, boolean unique)
	{
		super(ressource, notion, difficulte, temps, note);
		this.unique = unique;
	}

	

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le type de la question
	 * 
	 * @return le type de la question
	 */
	@Override
	public TypeQuestion getType () { return TypeQuestion.QCM; }

	/**
	 * Permet de définir si la question est unique.
	 * 
	 * @param unique
	 *            true si la question est unique, false sinon.
	 * 
	 * @return true si la modification a été effectuée, false sinon.
	 */
	public boolean      isUnique() { return this.unique     ; }

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Setter de proposition
	 * 
	 * @param i indice de la proposition à modifier
	 * @param prop la proposition qui va modifier l'ancienne
	 * @return true si la modification a été effectuée, false sinon
	 */
	public boolean setProposition(int i, PropositionQCM prop)
	{
		return super.setProposition(i, prop);
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	
	/**
	 * Ajout d'une proposition à la liste des propositions associées à la Question.
	 * 
	 * @param  prop la proposition à ajouter.
	 * @return      true si la proposition à été ajoutée, false sinon.
	 */
	public boolean ajouterProposition(PropositionQCM prop)
	{
		return super.ajouterProposition(prop);
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
    {
        String         result = "";
		PropositionQCM prop;
        for(int i = 0; i < this.lstPropositions.size(); i++)
        {
			prop = (PropositionQCM)this.lstPropositions.get(i);

            result +=	"TextProposition         : " + prop.getText   () + "\n" +
						"Réponse                 : " + prop.estReponse() + "\n\n";
        }

        return result;
    }


	
}
