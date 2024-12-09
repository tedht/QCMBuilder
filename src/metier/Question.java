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
	private String    type;
	private int       temps;
	private int       note;


	/* Constructeur */
	public Question(String difficulte, Ressource ressource, Notion notion,
	                String type, int temps, int note)
	{
		this.difficulte = difficulte;
		this.ressource  = ressource;
		this.notion     = notion;
		this.type       = type;
		this.temps      = temps;
		this.note       = note;
	}

	/* Getters */
	public String    getDifficulte() { return difficulte; }
	public Ressource getRessource () { return ressource;  }
	public Notion    getNotion    () { return notion;     }
	public String    getType      () { return type;       }
	public int       getTemps     () { return temps;      }
	public int       getNote      () { return note;       }

	/* Setters */
	public void setDifficulte(String difficulte)   { this.difficulte = difficulte; }
	public void setRessource (Ressource ressource) { this.ressource  = ressource;  }
	public void setNotion    (Notion notion)       { this.notion     = notion;     }
	public void setType      (String type)         { this.type       = type;       }
	public void setTemps     (int temps)           { this.temps      = temps;      }
	public void setNote      (int note)            { this.note       = note;       }

	/* toString */
	public String toString()
	{
		return "Question :\n" +
		       "difficulte : " + this.difficulte + "\n" +
		       " ressource : " + this.ressource  + "\n" +
		       "    notion : " + this.notion     + "\n" +
		       "      type : " + this.type       + "\n" +
		       "     temps : " + this.temps      + "\n" +
		       "      note : " + this.note                ;
	}
}