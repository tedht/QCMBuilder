package QCMBuilder.metier.entite.question;


import java.util.List;
import java.util.ArrayList;

/**
 * Classe abstraite Question qui correspond aux questions de façon générique
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class Question
{



	/*-----------*/
	/* Attributs */
	/*-----------*/

	private   String            codeRes;
	private   int               idNot;
	private   int               idQst;
	private   Difficulte        difficulte;
	private   int               temps;
	private   double            note;
	private   String            intitule;
	private   String            explication;
	private   PieceJointe       pieceJointe;

	protected List<Proposition> lstPropositions;



	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe Question.
	 * 
	 * @param ressource  la ressource associée à la question.
	 * @param notion     la notion associée à la question.
	 * @param difficulte la difficulté de la question(TF,F,M,D).
	 * @param temps      le temps de réponse à la question (en secondes).
	 * @param note       la note de la question.
	 */
	public Question(String codeRes, int idNot, int idQst, double note, int temps, Difficulte difficulte)
	{
		this.codeRes     = codeRes;
		this.idNot       = idNot;
		this.idQst       = idQst;
		this.note        = note;
		this.temps       = temps;
		this.difficulte  = difficulte;

		this.intitule    = "";
		this.explication = "";

		this.pieceJointe = null;
		this.lstPropositions = new ArrayList<Proposition>();
	}



	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le code de la ressource associée à la question.
	 * 
	 * @return le code de la ressource associée à la question.
	 */
	public String getCodeRes() { return this.codeRes; }

	/**
	 * Retourne l'id de la notion associée à la question.
	 * 
	 * @return l'id de la notion associée à la question.
	 */
	public int getIdNot() { return this.idNot; }

	/**
	 * Retourne l'id de la question.
	 * 
	 * @return l'id de la question.
	 */
	public int getIdQst() { return this.idQst; }


	/**
	 * Retourne la difficulté de la question.
	 * 
	 * @return la difficulté de la question.
	 */
	public Difficulte getDifficulte () { return this.difficulte; }

	/**
	 * Retourne le temps de réponse à la question.
	 * 
	 * @return le temps de réponse à la question.
	 */
	public int getTemps() { return this.temps; }

	/**
	 * Retourne la note de la question.
	 * 
	 * @return la note de la question.
	 */
	public double getNote() { return this.note; }


	/**
	 * Retourne la pièce jointe associé à la question.
	 * 
	 * @return la pièce jointe associé à la question
	 */
	public PieceJointe getPieceJointe() { return this.pieceJointe; }

	/**
	 * Retourne le type de la question(QCM, ELIMINATION, ASSOCIATION).
	 * 
	 * @return le type de la question.
	 */
	public abstract TypeQuestion getType ();

	/**
	 * Retourne la liste des propositions de la question.
	 * 
	 * @return la liste des propositions de la question.
	 */
	public List<Proposition> getPropositions() { return this.lstPropositions; }
	
	/**
	 * Retourne une proposition de la question en fonction de son indice dans la liste.
	 * 
	 * @param  i l'indice de la proposition dans la liste.
	 * @return   la proposition correspondante, ou null si elle n'existe pas.
	 */
	protected Proposition getProposition(int i) { return this.lstPropositions.get(i); }

	/**
	 * Retourne l'indice d'une proposition dans la liste des propositions de la question.
	 * 
	 * @param  prop la proposition dont on veut l'indice.
	 * @return      l'indice de la proposition, ou -1 si elle n'existe pas.
	 */
	public int getPropositionId(Proposition prop) { return this.lstPropositions.indexOf(prop); }

	/**
	 * Retourne l'intitulé d'une proposition.
	 * 
	 * @return l'intitulé.
	 */
	public String getIntitule   () { return this.intitule;    }

	/**
	 * Retourne l'explication d'une proposition.
	 * 
	 * @return l'explication.
	 */
	public String getExplication() { return this.explication; }



	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie le code de la ressource associée à la question.
	 * 
	 * @param codeRes le nouveau code de la ressource.
	 */
	public void setCodeRes(String codeRes)
	{
		this.codeRes = codeRes;
	}

	/**
	 * Modifie l'id de la notion associée à la question.
	 * 
	 * @param idNot le nouvel id de la notion.
	 */
	public void setIdNot(int idNot)
	{
		this.idNot = idNot;
	}

	/**
	 * Modifie l'id de la question.
	 * 
	 * @param idQst le nouvel id de la question.
	 */
	public void setIdQst(int idQst)
	{
		this.idQst = idQst;
	}

	/**
	 * Modifie la difficulté de la question.
	 * 
	 * @param  difficulte la nouvelle difficulté.
	 * @return            true si la difficulté à été modifiée, false sinon.
	 */
	public boolean setDifficulte(Difficulte difficulte)
	{
		if (difficulte == null) return false;

		this.difficulte = difficulte;
		return true;
	}

	/**
	 * Modifie le temps de réponse à la question.
	 * 
	 * @param  temps le nouveau temps de réponse.
	 * @return       true si le temps à été modifié, false sinon.
	 */
	public boolean setTemps(int temps)
	{
		if (temps < 0) return false;

		this.temps = temps;
		return true;
	}

	/**
	 * Modifie la note de la question.
	 * 
	 * @param  note la nouvelle note.
	 * @return      true si la note à été modifiée, false sinon.
	 */
	public boolean setNote(double note)
	{
		if (note < 0) return false;

		this.note = note;
		return true;
	}

	/**
	 * Modifie l'intitulé de la question.
	 * 
	 * @param intitule le nouvel intitulé.
	 */
	public void setIntitule(String intitule)
	{
		this.intitule = intitule;
	}

	/**
	 * Modifie l'explication de la question.
	 * 
	 * @param explication la nouvelle explication.
	 */
	public void setExplication(String explication)
	{
		this.explication = explication;
	}

	/**
	 * Modifie une proposition dans la liste des propositions associées à la question.
	 * 
	 * @param  i    l'index de la proposition à modifier.
	 * @param  prop la nouvelle proposition.
	 * @return      true si la modification a été effectuée, false sinon.
	 */
	protected boolean setProposition(int i, Proposition prop)
	{
		if(i < 0 || i >= this.lstPropositions.size())  return false; // Si l'indice est en dehors de la liste                                           -> renvoie faux
		if(this.lstPropositions.isEmpty())            return false;  // Si la liste est vide                                                            -> renvoie faux
		if(!this.lstPropositions.get(i).equals(prop)) return false;  // Si la proposition modifié est égale à la même chose que la nouvelle proposition -> renvoie faux

		this.lstPropositions.set(i, prop);                           // Set la proposition
		return true;
	}

	/**
	 * Ajoute une piece jointe à la question.
	 * 
	 * @param pieceJointe la pièce jointe à ajouter.
	 */
	public void setPieceJointe(PieceJointe pieceJointe)
	{
		this.pieceJointe = pieceJointe;
	}



	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Ajoute une proposition à la liste des propositions de la question.
	 * 
	 * @param  prop la proposition à ajouter.
	 * @return      true si la proposition a été ajoutée, false sinon.
	 */
	protected boolean ajouterProposition(Proposition prop)
	{
		if(this.lstPropositions.contains(prop)) return false; // Si la proposition éxiste déjà -> renvoie faux

		this.lstPropositions.add(prop); // Ajoute la proposition
		return true;
	}


	/**
	 * Supprime une proposition de la liste des propositions de la question.
	 * 
	 * @param  i l'index de la proposition à supprimer.
	 * @return   true si la proposition a été supprimée, false sinon.
	 */
	public boolean supprimerProposition(int i)
	{
		if(i < 0 || i >= this.lstPropositions.size()) return false; // Si l'indice est en dehors de la liste -> renvoie faux
		if(this.lstPropositions.isEmpty())            return false; // Si la liste est vide                  -> renvoie faux

		this.lstPropositions.remove(i); // Supprime la proposition
		return true;
	}

	/**
	 * Supprime la pièce jointe de la question.
	 * 
	 * @return  true si la pièce jointe a été supprimée, false sinon.
	 */
	public boolean supprimerPieceJointe()
	{
		if (this.pieceJointe == null) return false; // Si la pièce jointe est null -> renvoie faux

		this.pieceJointe = null; // Supprime la pièce jointe
		return true;
	}

	/**
	 * Supprime toutes les propositions de la question.
	 */
	public void clearPropositions()
	{
		this.lstPropositions.clear();
	}
}