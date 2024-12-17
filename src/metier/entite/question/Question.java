package metier.entite.question;

import java.util.List;
import java.util.ArrayList;

import metier.entite.Ressource;
import metier.entite.Notion;

/** Classe Question
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class Question
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private Ressource         ressource;
	private Notion            notion;
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
	 * @param pieceJointe le chemin de la pièce jointe associée à la question.
	 */
	public Question(Ressource ressource, Notion notion, Difficulte difficulte, int temps, double note,String pieceJointe)
	{
		this.ressource   = ressource;
		this.notion      = notion;
		this.difficulte  = difficulte;
		this.temps       = temps;
		this.note        = note;
		this.piecesJointes = new ArrayList<PieceJointe>();

		this.intitule    = "";
		this.explication = "";

		this.lstPropositions = new ArrayList<Proposition>();
	}

	/**
	 * Constructeur de la classe Question sans pièce jointe.
	 * 
	 * @param ressource la ressource associée à la question.
	 * @param notion la notion associée à la question.
	 * @param difficulte la difficulté de la question(TF,F,M,D).
	 * @param temps le temps de réponse à la question (en secondes). 
	 * @param note la note de la question.
	 */
	public Question(Ressource ressource, Notion notion, Difficulte difficulte, int temps, double note)
	{
		this(ressource, notion, difficulte, temps, note, "");
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne la ressource associée à la question.
	 * 
	 * @return la ressource associée à la question.
	 */
	public Ressource    getRessource  () { return this.ressource;   }

	/**
	 * Retourne la notion associée à la question.
	 * 
	 * @return la notion associée à la question.
	 */
	public Notion       getNotion     () { return this.notion;      }

	/**
	 * Retourne la difficulté de la question.
	 * 
	 * @return la difficulté de la question.
	 */
	public Difficulte   getDifficulte () { return this.difficulte;  }

	/**
	 * Retourne le temps de réponse à la question.
	 * 
	 * @return le temps de réponse à la question.
	 */
	public int          getTemps      () { return this.temps;       }

	/**
	 * Retourne la note de la question.
	 * 
	 * @return la note de la question.
	 */
	public double       getNote       () { return this.note;        }

	/**
	 * Retourne l'intitulé de la question.
	 * 
	 * @return l'intitulé de la question.
	 */
	public String       getIntitule   () { return this.intitule;    }

	/**
	 * Retourne le chemin de la pièce jointe associée à la question.
	 * 
	 * @return le chemin de la pièce jointe associée à la question.
	 */
	public List<PieceJointe> getPiecesJointes() { return this.piecesJointes; }

	/**
	 * Retourne l'explication de la question.
	 * 
	 * @return l'explication de la question.
	 */
	public String       getExplication() { return this.explication; }

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

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource associée à la question.
	 * 
	 * @param ressource la nouvelle ressource.
	 * @return true si la ressource à été modifiée, false sinon.
	 */
	public boolean setRessource(Ressource ressource)
	{
		if (ressource == null) return false;

		this.ressource  = ressource;
		return true;
	}

	/**
	 * Modifie la notion associée à la question.
	 * 
	 * @param notion la nouvelle notion.
	 * @return true si la notion à été modifiée, false sinon.
	 */
	public boolean setNotion(Notion notion)
	{
		if (notion == null) return false;

		this.notion = notion;
		return true;
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

	/**
	 * Modifie l'intitulé de la question.
	 * 
	 * @param intitule le nouvel intitulé.
	 * @return true si l'intitulé à été modifié, false sinon.
	 */
	public boolean setIntitule(String intitule)
	{
		if ("".equals(intitule)) return false;

		this.intitule = intitule;
		return true;
	}

	/**
	 * Modifie l'explication de la question.
	 * 
	 * @param explication la nouvelle explication.
	 * @return true si l'explication à été modifiée, false sinon.
	 */
	public boolean setExplication(String explication)
	{
		if ("".equals(explication)) return false;

		this.explication = explication;
		return true;
	}


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

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	
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

		return sRet;
	}

	/*------*/
	/* Main */
	/*------*/
	/*
	public static void main(String[] args)
	{
		Question q1, q2, q3;


		q1 = new Question(0, "C'est quoi le principe d'encapsulation ?", "c'est de limiter l'accès", Difficulte.TRES_FACILE, new Ressource("Init dev"), new Notion("Encapsulation"),  0, 1);
		q2 = new Question(1, "A quoi sert une jointure ?", "Lier des tables entre elles",Difficulte.DIFFICILE, new Ressource("Init bd" ), new Notion("Jointure"     ), 30, 2);
		q3 = new Question(2, "Quels sont les différentes couches du modèle OSI ?", "", Difficulte.MOYEN, new Ressource("Réseaux" ), new Notion("Modele OSI"   ),  0, 3);

		System.out.println(q1);
		System.out.println(q2);
		System.out.println(q3);

		System.out.println(q1.getIntitule());
		System.out.println(q2.getNote());
		System.out.println(q3.getTemps());
	}*/
}