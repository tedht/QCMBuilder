package metier.entite.question;

import java.util.List;
import java.util.ArrayList;

import metier.entite.Notion;
import metier.entite.Ressource;

/** Classe Question
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class Question
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private int          id;
	private Ressource    ressource;
	private Notion       notion;
	private Difficulte   difficulte;
	private int          temps;
	private int          note;
	private String       intitule;
	private String       explication;

	protected List<Proposition> lstPropositions;

	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Question(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
	{
		this.id          = id;
		this.ressource   = ressource;
		this.notion      = notion;
		this.difficulte  = difficulte;
		this.temps       = temps;
		this.note        = note;

		this.intitule    = "";
		this.explication = "";

		this.lstPropositions = new ArrayList<Proposition>();
	}

	/*---------*/
	/* Getters */
	/*---------*/
	public int          getId         () { return this.id;          }
	public Ressource    getRessource  () { return this.ressource;   }
	public Notion       getNotion     () { return this.notion;      }
	public Difficulte   getDifficulte () { return this.difficulte;  }
	public int          getTemps      () { return this.temps;       }
	public int          getNote       () { return this.note;        }

	public String       getIntitule   () { return this.intitule;    }
	public String       getExplication() { return this.explication; }

	public abstract TypeQuestion getType ();

	public List<Proposition> getPropositions() { return this.lstPropositions; }
	
	protected Proposition getProposition(int i) { return this.lstPropositions.get(i); }

	public int getPropositionId(Proposition prop) { return this.lstPropositions.indexOf(prop); }

	/*---------*/
	/* Setters */
	/*---------*/
	public boolean setRessource(Ressource ressource)
	{
		if (ressource == null) return false;

		this.ressource  = ressource;
		return true;
	}

	public boolean setNotion(Notion notion)
	{
		if (notion == null) return false;

		this.notion = notion;
		return true;
	}

	public void setDifficulte(Difficulte difficulte)
	{
		this.difficulte = difficulte;
	}

	public boolean setTemps(int temps)
	{
		if (temps < 0) return false;

		this.temps = temps;
		return true;
	}

	public boolean setNote(int note)
	{
		if (note < 0) return false;

		this.note = note;
		return true;
	}

	public boolean setIntitule(String intitule)
	{
		if ("".equals(this.intitule)) return false;

		this.intitule = intitule;
		return true;
	}

	public boolean setExplication(String explication)
	{
		if ("".equals(this.explication)) return false;

		this.explication = explication;
		return true;
	}

	
	protected boolean setProposition(int i, Proposition prop)
	{
		if(i < 0 || i < this.lstPropositions.size())  return false; // Si l'indice est en dehors de la liste                                           -> renvoie faux
		if(this.lstPropositions.isEmpty())            return false; // Si la liste est vide                                                            -> renvoie faux
		if(!this.lstPropositions.get(i).equals(prop)) return false; // Si la proposition modifié est égale à la même chose que la nouvelle proposition -> renvoie faux

		this.lstPropositions.set(i, prop);                          // Set la proposition
		return true;
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	/* Ajouter une proposition */
	protected boolean ajouterProposition(Proposition prop)
	{
		if(this.lstPropositions.contains(prop)) return false; // Si la proposition éxiste déjà -> renvoie faux

		this.lstPropositions.add(prop); // Ajoute la proposition
		return true;
	}

	/* Supprimer une proposition */
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
		sRet =  "Question " + this.id + " :\n" +
		        "\t   intitulé : " + this.intitule              + "\n" +
		        "\texplication : " + this.explication           + "\n" +
		        "\t difficulte : " + this.difficulte            + "\n";

		if (this.ressource == null) sRet += "\t  ressource : Ne possède pas de ressource\n";
		else                        sRet += "\t  ressource : " + this.ressource.getNom() + "\n";
		if (this.notion == null)    sRet += "\t     notion : Ne possède pas de notion\n";
		else                        sRet += "\t     notion : " + this.notion   .getNom()    + "\n";
		
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