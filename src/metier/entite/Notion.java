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
	private String codeRes;
	private int    idNot;
	private String nom;
	
	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Notion(String codeRes, int idNot, String nom) 
	{
		this.codeRes = codeRes;
		this.idNot   = idNot;
		this.nom     = nom;
	}

	/*---------*/
	/* Getters */
	/*---------*/

	public String getCodeRes() 
	{
		return this.codeRes;
	}

	public int getIdNot() 
	{
		return this.idNot;
	}

	public String getNom()
	{
		return this.nom;
	}

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


	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
	{
		//return this.codeRes + " " + this.idNot + " " + this.nom;
		return this.nom; // ce qui s'affiche dans ddlstNotions
	}
}