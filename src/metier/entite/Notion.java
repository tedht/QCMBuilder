package metier.entite;

/**
 * Classe Notion qui correspond aux notions.
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

	/**
	 * Constructeur de la classe Notion.
	 * 
	 * @param codeRes l'identifiant de la ressource.
	 * @param idNot   l'identifiant de la notion.
	 * @param nom     le nom de la notion.
	 */
	public Notion(String codeRes, int idNot, String nom) 
	{
		this.codeRes = codeRes;
		this.idNot   = idNot;
		this.nom     = nom;
	}


	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne l'identifiant de la ressource.
	 * 
	 * @return l'identifiant de la ressource.
	 */
	public String getCodeRes() { return this.codeRes; }

	/**
	 * Retourne l'identifiant de la notion.
	 * 
	 * @return l'identifiant de la notion.
	 */
	public int getIdNot() { return this.idNot; }

	/**
	 * Retourne le nom de la notion.
	 * 
	 * @return le nom de la notion.
	 */
	public String getNom() { return this.nom; }


	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie l'identifiant de la ressource.
	 * 
	 * @param codeRes le nouvel identifiant de la ressource.
	 */
	public void setCodeRes(String codeRes) { this.codeRes = codeRes; }

	/**
	 * Modifie l'identifiant de la notion.
	 * 
	 * @param idNot le nouvel identifiant de la notion.
	 */
	public void setIdNot(int idNot) { this.idNot = idNot; }

	/**
	 * Modifie le nom de la notion.
	 * 
	 * @param nom le nouveau nom de la notion.
	 */
	public void setNom(String nom) { this.nom = nom; }


	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Retourne une chaine de caractères qui représente la notion.
	 * 
	 * @return une chaine de caractères qui représente la notion.
	 */
	public String toString()
	{
		return this.nom; // ce qui s'affiche dans ddlstNotions
	}
}