package metier.entite.question;

import java.util.List;
import java.util.ArrayList;

/** Classe Question
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class Question
{
	/*-----------*/
	/* Attributs */
	/*-----------*/

	private String            codeRes;
	private int               idNot;
	private int               idQst;
	private Difficulte        difficulte;
	private int               temps;
	private double            note;
	private String            intitule;
	private String            explication;
	private List<PieceJointe> piecesJointes;

	protected List<Proposition> lstPropositions;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe Question
	 * 
	 * @param ressource la ressource associée à la question.
	 * @param notion la notion associée à la question.
	 * @param difficulte la difficulté de la question(TF,F,M,D).
	 * @param temps le temps de réponse à la question (en secondes).
	 * @param note la note de la question.
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

		this.piecesJointes   = new ArrayList<PieceJointe>();
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
	 * Retourne le chemin de la pièce jointe associée à la question.
	 * 
	 * @return le chemin de la pièce jointe associée à la question.
	 */
	public List<PieceJointe> getPiecesJointes() { return this.piecesJointes; }

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
	 * @param i l'indice de la proposition à retourner.
	 * @return la proposition correspondante, ou null si elle n'existe pas.
	 */
	protected Proposition getProposition(int i) { return this.lstPropositions.get(i); }

	/**
	 * Retourne l'indice d'une proposition dans la liste des propositions de la question.
	 * 
	 * @param prop la proposition dont on veut l'indice.
	 * @return l'indice de la proposition, ou -1 si elle n'existe pas.
	 */
	public int getPropositionId(Proposition prop) { return this.lstPropositions.indexOf(prop); }

	public String getIntitule   () { return this.intitule;    }
	public String getExplication() { return this.explication; }
	
	/*---------*/
	/* Setters */
	/*---------*/

	public void setCodeRes(String codeRes)
	{
		this.codeRes = codeRes;
	}

	public void setIdNot(int idNot)
	{
		this.idNot = idNot;
	}

	public void setIdQst(int idQst)
	{
		this.idQst = idQst;
	}

	/**
	 * Modifie la difficulté de la question.
	 * 
	 * @param difficulte la nouvelle difficulté.
	 * @return true si la difficulté à été modifiée, false sinon.
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
	 * @param temps le nouveau temps de réponse.
	 * @return true si le temps à été modifié, false sinon.
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
	 * @param note la nouvelle note.
	 * @return true si la note à été modifiée, false sinon.
	 */
	public boolean setNote(int note)
	{
		if (note < 0) return false;

		this.note = note;
		return true;
	}

	public void setIntitule(String intitule)
	{
		this.intitule = intitule;
	}

	public void setExplication(String explication)
	{
		this.explication = explication;
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Ajoute une piece jointe à la liste des pièces jointes de la question.
	 * 
	 * @param pieceJointe la pièce jointe à ajouter.
	 * @return true si la pièce jointe a été ajoutée, false sinon.
	 */
	public boolean ajouterPieceJointe(PieceJointe pieceJointe)
	{
		if (pieceJointe == null) return false;

		this.piecesJointes.add(pieceJointe);
		return true;
	}

	/**
	 * Supprime une pièce jointe de la liste des pièces jointes de la question.
	 * 
	 * @param i l'index de la pièce jointe à supprimer.
	 * @return true si la pièce jointe a été supprimée, false sinon.
	 */
	public boolean supprimerPieceJointe(int i)
	{
		if(i < 0 || i >= this.piecesJointes.size()) return false; // Si l'indice est en dehors de la liste -> renvoie faux
		if(this.piecesJointes.isEmpty())            return false; // Si la liste est vide                  -> renvoie faux

		this.piecesJointes.remove(i); // Supprime la pièce jointe
		return true;
	}

	/**
	 * Modifie une proposition dans la liste des propositions associées à la question.
	 * 
	 * @param i l'index de la proposition à modifier.
	 * @param prop la nouvelle proposition.
	 * @return true si la modification a été effectuée, false sinon.
	 */
	protected boolean setProposition(int i, Proposition prop)
	{
		if(i < 0 || i >= this.lstPropositions.size())  return false; // Si l'indice est en dehors de la liste                                           -> renvoie faux
		if(this.lstPropositions.isEmpty())            return false; // Si la liste est vide                                                            -> renvoie faux
		if(!this.lstPropositions.get(i).equals(prop)) return false; // Si la proposition modifié est égale à la même chose que la nouvelle proposition -> renvoie faux

		this.lstPropositions.set(i, prop);                          // Set la proposition
		return true;
	}
	
	/**
	 * Ajoute une proposition à la liste des propositions de la question.
	 * 
	 * @param prop la proposition à ajouter.
	 * @return true si la proposition a été ajoutée, false sinon.
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
	 * @param i l'index de la proposition à supprimer.
	 * @return true si la proposition a été supprimée, false sinon.
	 */
	public boolean supprimerProposition(int i)
	{
		if(i < 0 || i >= this.lstPropositions.size()) return false; // Si l'indice est en dehors de la liste -> renvoie faux
		if(this.lstPropositions.isEmpty())            return false; // Si la liste est vide                  -> renvoie faux

		this.lstPropositions.remove(i); // Supprime la proposition
		return true;
	}

	/*----------*/
	/* toString */
	/*----------*/
	/*
	public String toString()
	{
		String sRet;


		if (this.ressource == null || this.notion == null) return "";
		sRet =  "Question :\n" +
		        "\t   intitulé : " + this.intitule              + "\n" +
		        "\texplication : " + this.explication           + "\n" +
		        "\t difficulte : " + this.difficulte            + "\n";

		if (this.ressource == null) sRet += "\t  ressource : Ne possède pas de ressource\n";
		else                        sRet += "\t  ressource : " + this.ressource.getNom() + "\n";
		if (this.notion == null)    sRet += "\t     notion : Ne possède pas de notion\n";
		else                        sRet += "\t     notion : " + this.notion    + "\n";
		
		sRet += "\t      temps : " + this.temps + " seconde(s)" + "\n" +
		        "\t       note : " + this.note                  + "\n"   ;

		for (int cpt = 0 ; cpt < this.piecesJointes.size() ; cpt++)
			sRet += this.piecesJointes.get(cpt) + "\n";

		return sRet;
	}*/

	
	/**
	 * Test de la classe Question
	 * 
	 * @param args
	 */
	/*
	public static void main(String[] args)
	{
		Question q1, q2, q3;


		q1 = new QCM(new Ressource("R1.01", "Init dev"               ), new Notion("Encapsulation", 0, "R1.01"), Difficulte.TRES_FACILE,  0, 2, true);
		q2 = new QCM(new Ressource("R1.05","Init bd"                 ), new Notion("Jointure"     , 1, "R1.05"), Difficulte.DIFFICILE  , 30, 2, true);
		q3 = new QCM(new Ressource("R3.06","Architecture des réseaux"), new Notion("Modele OSI"   , 2, "R3.06"), Difficulte.MOYEN      , 50, 2, true);


		System.out.println(q1);
		System.out.println(q2);
		System.out.println(q3);
	}*/
}