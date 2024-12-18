package metier.entite;

/** Classe Notion
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Notion 
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private String nom;
	private int id;
	private String codeRessource;
	
	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Notion(String nom, int id, String codeRessource) 
	{
		this.nom = nom;
		this.id = id;
		this.codeRessource = codeRessource;
		if(nom != null)
		{
			this.nom = nom;
		}
	}

	/*---------*/
	/* Getters */
	/*---------*/

	public int getId() 
	{
		return this.id;
	}

	public String getCodeRessource() 
	{
		return this.codeRessource;
	}

	public String getNom()
	{
		return this.nom;
	}

	/*---------*/
	/* Setters */
	/*---------*/

	public boolean setId(int id) 
	{
		if(id != this.id)
		{
			this.id = id;
			return true;
		}

		return false;
	}

	public boolean setCodeRessource(String codeRessource) 
	{
		if(codeRessource != null && !codeRessource.equals(this.codeRessource))
		{
			this.codeRessource = codeRessource;
			return true;
		}

		return false;
	}

	public boolean setNom(String nom) 
	{
		if(nom != null && !nom.equals(this.nom))
		{
			this.nom = nom;
			return true;
		}

		return false;
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
	{
		return "Notion [nom=" + this.nom + ", id=" + this.id + ", codeRessource=" + this.codeRessource + "]";
	}
}