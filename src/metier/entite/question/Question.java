package metier.entite.question;

import metier.entite.Notion;
import metier.entite.Ressource;

import metier.entite.Difficulte;

/** Classe Question
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Question
{
	/* Attributs */
	private int        id;
	private String     intitule;
	private String     explication;
	private Difficulte difficulte;
	private Ressource  ressource;
	private Notion     notion;
	private int        temps;
	private int        note;


	/* Constructeur */
	public Question(int id, String intitule, String explication,
	                Difficulte difficulte, Ressource ressource, Notion notion,
	                int temps, int note)
	{
		this.id          = id;
		this.intitule    = intitule;
		this.explication = explication;
		this.difficulte  = difficulte;
		this.ressource   = ressource;
		this.notion      = notion;
		this.temps       = temps;
		this.note        = note;
	}

	/* Getters */
	public int        getId         () { return this.id;          }
	public String     getIntitule   () { return this.intitule;    }
	public String     getExplication() { return this.explication; }
	public Difficulte getDifficulte () { return this.difficulte;  }
	public Ressource  getRessource  () { return this.ressource;   }
	public Notion     getNotion     () { return this.notion;      }
	public int        getTemps      () { return this.temps;       }
	public int        getNote       () { return this.note;        }

	/* Setters */
	public boolean setIntitule (String intitule)
	{
		if (intitule.equals("")) return false;

		this.intitule = intitule;
		return true;
	}

	public void setExplication (String explication)
	{
		this.explication = explication;
	}

	public void setDifficulte (Difficulte difficulte)
	{
		this.difficulte = difficulte;
	}

	public boolean setRessource (Ressource ressource)
	{
		if (ressource == null) return false;

		this.ressource  = ressource;
		return true;
	}

	public boolean setNotion (Notion notion)
	{
		if (notion == null) return false;

		this.notion = notion;
		return true;
	}

	public boolean setTemps (int temps)
	{
		if (temps < 0) return false;

		this.temps = temps;
		return true;
	}

	public boolean setNote (int note)
	{
		if (note < 0) return false;

		this.note = note;
		return true;
	}

	/* toString */
	public String toString()
	{
		return "Question " + this.id + " :\n" +
		       "\t   intitulé : " + this.intitule              + "\n" +
		       "\texplication : " + this.explication           + "\n" +
		       "\t difficulte : " + this.difficulte            + "\n" +
		       "\t  ressource : " + this.ressource.getNom()    + "\n" +
		       "\t     notion : " + this.notion   .getNom()    + "\n" +
		       "\t      temps : " + this.temps + " seconde(s)" + "\n" +
		       "\t       note : " + this.note                  + "\n"   ;
	}


	/* Main */
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
	}
}