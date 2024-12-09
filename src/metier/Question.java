package metier;

/** Classe Question
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Question
{
	private String    difficulte;
	private Ressource ressource;
	private Notion    notion;
	private int       temps;
	private int       note;


	/* Constructeur */
	public Question(String difficulte, Ressource ressource, Notion notion,
	                int temps, int note)
	{
		this.difficulte = difficulte;
		this.ressource  = ressource;
		this.notion     = notion;
		this.temps      = temps;
		this.note       = note;
	}

	/* Getters */
	public String    getDifficulte() { return difficulte; }
	public Ressource getRessource () { return ressource;  }
	public Notion    getNotion    () { return notion;     }
	public int       getTemps     () { return temps;      }
	public int       getNote      () { return note;       }

	/* Setters */
	public boolean setDifficulte (String difficulte)
	{
		if (!difficulte.equals("TF") && !difficulte.equals("F") &&
		    !difficulte.equals("M")  && !difficulte.equals("D")    )
			return false;

		this.difficulte = difficulte;
		return true;
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
		return "Question :\n" +
		       "difficulte : " + this.difficulte + "\n" +
		       " ressource : " + this.ressource  + "\n" +
		       "    notion : " + this.notion     + "\n" +
		       "     temps : " + this.temps      + "\n" +
		       "      note : " + this.note                ;
	}
}