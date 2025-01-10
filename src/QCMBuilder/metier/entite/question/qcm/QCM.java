package QCMBuilder.metier.entite.question.qcm;


import QCMBuilder.metier.entite.question.Difficulte;
import QCMBuilder.metier.entite.question.Question;
import QCMBuilder.metier.entite.question.TypeQuestion;

/**
 * Classe QCM qui correspond aux questions de type QCM (hérite de Question).
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCM extends Question
{



	/*-----------*/
	/* Attributs */
	/*-----------*/
	
	private boolean unique;



	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe QCM.
	 * 
	 * @param codeRes    l'identifiant de la ressource.
	 * @param idNot      l'identifiant de la notion.
	 * @param idQst      l'identifiant de la question.
	 * @param note       la note.
	 * @param temps      le temps associée à la question.
	 * @param difficulte la difficultée.
	 * 
	 * @see   Question la classe Question parente de QCM.
	 */
	public QCM(String codeRes, int idNot, int idQst, double note, int temps, Difficulte difficulte)
	{
		super(codeRes, idNot, idQst, note, temps, difficulte);
		this.unique = false;
	}



	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le type de la question.
	 * 
	 * @return le type de question QCM.
	 */
	@Override
	public TypeQuestion getType () { return TypeQuestion.QCM; }

	/**
	 * Retourne si la question est unique.
	 * 
	 * @return true si la question est unique, false sinon.
	 */
	public boolean estUnique() { return this.unique; }

	/**
	 * Retourne une proposition de type QCM.
	 * 
	 * @return la proposition.
	 */
	public PropositionQCM getProposition(int i) { return (PropositionQCM) super.getProposition(i); }



	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie l'état d'unique.
	 * 
	 * @param unique le nouvelle état d'unique (true si unique, false sinon).
	 */
	public void setUnique(boolean unique) { this.unique = unique; }

	/**
	 * Modifie une proposition QCM.
	 * 
	 * @param  i    indice de la proposition à modifier.
	 * @param  prop la proposition qui va modifier l'ancienne.
	 * 
	 * @return      true si la modification a été effectuée, false sinon.
	 * @see         super#{@link #setProposition(int, Proposition)}.
	 */
	public boolean setProposition(int i, PropositionQCM prop) { return super.setProposition(i, prop); }


	
	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	
	/**
	 * Ajout d'une proposition QCM à la liste des propositions associées à la Question.
	 * 
	 * @param  prop la proposition à ajouter.
	 * @return      true si la proposition à été ajoutée, false sinon.
	 * 
	 * @see         super#{@link #ajouterProposition(PropositionQCM)}.
	 */
	public boolean ajouterProposition(PropositionQCM prop)
	{
		return super.ajouterProposition(prop);
	}

	/**
	 * Retourne une représentation en chaîne de caractères d'une question QCM.
	 * 
	 * @return une représentation en chaîne de caractères d'une question QCM.
	 * @see    super#{@link #toString()}.
	 */
	public String toString()
	{
		String         result;
		PropositionQCM prop;


		result = super.toString();
		for(int i = 0; i < this.lstPropositions.size(); i++)
		{
			prop = (PropositionQCM)this.lstPropositions.get(i);

			result +=	"TextProposition         : " + prop.getText   () + "\n" +
						"Réponse                 : " + prop.estReponse() + "\n\n";
		}

		return result;
	}
}