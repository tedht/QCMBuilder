package metier.entite;

/**
 * Classe Ressource
 * 
 * @author  Equipe03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Ressource 
{
	/*-----------*/
	/* Attributs */
	/*-----------*/

	private String code;
	private String nom;

	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe Ressource avec le chemin du fichier.
	 * 
	 * @param  idRes L'id de la ressource.
	 * @param  code        Le code de la ressource.
	 * @param  nom         Le nom de la Ressource.
	 */
	public Ressource(String code,String nom)
	{
		this.code = code;
		this.nom  = nom;
	}

	/*---------*/
	/* Getters */
	/*---------*/


	/**
	 * Retourne le code de la Ressource.
	 * 
	 * @return code de la Ressource.
	 */
	public String getCode()
	{
		return this.code;
	}

	/**
	 * Retourne le nom de la Ressource.
	 * 
	 * @return nom de la Ressource.
	 */
	public String getNom()
	{
		return this.nom;
	}

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie le nom de la Ressource.
	 * 
	 * @param nom le nouveau nom à de la Ressource.
	 */
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	/**
	 * Modifie le code de la Ressource.
	 * 
	 * @param code le nouveau code à de la Ressource.
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	 * Retourne sous forme textuel l'objet Ressource
	 * 
	 * @return l'objet Ressource sous forme textuel (code + nom)
	 */
	public String toString()
	{
		return this.code + " " + this.nom;
	}
}