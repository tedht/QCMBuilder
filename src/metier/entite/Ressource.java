package metier.entite;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe Ressource
 * 
 * @author  Equipe03
 * @version 1.0 du 09/12/2024
 */
public class Ressource 
{



	/*-----------*/
	// Attributs //
	/*-----------*/

	private String       nom;
	private String       cheminFichier;
	private List<String> lstNotions;
	private String       code;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe Ressource avec le chemin du fichier.
	 * 
	 * @param  nom                       Le nom de la Ressource (non null).
	 * @param  cheminFichier             Le chemin du fichier associer à la ressource.
	 * @param  code                      Le code de la ressource.
	 * @throws IlleagalArgumentException Si le nom est null.
	 */
	public Ressource(String code,String nom, String cheminFichier)
	{
		if (!nom.equals(null))
		{
			this.code          = code;
			this.nom           = nom;
			this.lstNotions    = new ArrayList<String>();
			this.cheminFichier = cheminFichier;
		} 
		else 
		{
			throw new IllegalArgumentException("Le nom de la ressource ne peut pas être null");
		}
	}

	/**
	 * Constructeur de la classe Ressource avec le chemin du fichier initialisé à null.
	 * 
	 * @param nom Le nom de la Ressource (non null).
	 */
	public Ressource(String code,String nom)
	{
		this(code,nom, null);
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
	 * Retourne le nom de la Ressource.
	 * 
	 * @return nom de la Ressource.
	 */
	public String getNom()
	{
		return this.nom;
	}

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
	 * Retourne le chemin du fichier associée à Ressource.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getCheminFichier()
	{
		return this.cheminFichier;
	}

	/**
	 * Retourne la liste des notions associée à Ressource.
	 * 
	 * @return liste des notions.
	 */
	public List<String> getNotions() 
	{
		return this.lstNotions;
	}

	/**
	 * Retourne une notion de la liste des notions associée à Ressource.
	 * 
	 * @param  nomNotion le nom de la notion recherchée.
	 * @return           la notion.
	 */
	public String getNotion(String nomNotion) 
	{
		for(String notion : this.lstNotions)
		{
			if (notion.equals(nomNotion))
			{
				return notion;
			}
		}

		return null;
	}



	/*---------*/
	// Setters //
	/*---------*/

	/**
	 * Modifie le nom associée à Ressource.
	 * 
	 * @param  nom le nouveau nom à associée à Ressource (non null).
	 * @return     true si le nom à été modifié, false sinon.
	 */
	public boolean setNom(String nom) 
	{
		if (!nom.equals(null) && !nom.equals(this.nom))
		{
			this.nom = nom;

			return true;
		}

		return false;
		// Le nouveau Nom ne peut pas être null ou égal au nom actuel
	}

	/**
	 * Modifie le code associée à Ressource.
	 * 
	 * @param  code le nouveau code à associée à Ressource (non null).
	 * @return     true si le code à été modifié, false sinon.
	 */
	public boolean setCode(String code)
	{
		if (!code.equals(null) && !code.equals(this.code))
		{
			this.code = code;

			return true;
		}

		return false;
		// Le nouveau code ne peut pas être null ou égal au code actuel
	}

	/**
	 * Modifie le chemin du fichier associée à Ressource.
	 * 
	 * @param  cheminFichier le nouveau chemin du fichier à associée à Ressource.
	 * @return               true si le chemin du fichier à été modifié, false sinon.
	 */
	public boolean setCheminFichier(String cheminFichier)
	{
		if (!cheminFichier.equals(null) && !cheminFichier.equals(this.cheminFichier))
		{
			this.cheminFichier = cheminFichier;

			return true;
		}

		return false;
		// Le nouveau cheminFichier ne peux pas être null au nom actuel
	}



	/*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	 * Ajout d'une notion à la liste des notions associées à Ressource.
	 * 
	 * @param  notion la notion (non null).
	 * @return        true si la notion à été ajoutée, false sinon.
	 */
	public boolean ajouterNotion(String notion) 
	{
		if( notion != null ) 
		{
			if (!this.lstNotions.contains(notion)) 
			{
				this.lstNotions.add(notion);

				return true;
			}
			else
			{
				String nom          = notion;
				char   lastNameChar = nom.charAt(nom.length() - 1);

				if (Character.isDigit(lastNameChar)) 
				{
					int lastNumber = Character.getNumericValue(lastNameChar);
					notion = (nom.substring(0, nom.length() - 1) + (lastNumber + 1));
				}
				else
				{
					notion = (nom + "1");
				}

				return ajouterNotion(notion); 
			}
		}

		return false;
	}

	/**
	 * Modifier le nom d'une notion de la liste des notions associées à Ressource.
	 * 
	 * @param notion     la notion à modifier.
	 * @param nouveauNom le nouveau nom (non null).
	 * @return           true si le nom à été modifié, true sinon.
	 */
	public boolean modifierNotion(String notion, String nouveauNom) 
	{
		if (this.lstNotions.contains(notion)  &&
			!nouveauNom.equals(null) &&
			!notion.equals(nouveauNom))
		{
			notion = nouveauNom;

			return true;
		}

		return false;
	}

	/**
	 * Supression d'une notion de la liste des notions associées à Ressource.
	 * 
	 * @param notion la notion à supprimer.
	 * @return       true si la notion à été suprimmée, false sinon.
	 */
	public boolean supprimerNotion(String notion) 
	{
		if (this.lstNotions.contains(notion))
		{
			this.lstNotions.remove(notion);

			return true;
		}

		return false;
	}

	/**
	 * Suppression du chemin du fichier associée à Ressource.
	 * 
	 * @return true.
	 */
	public void supprimerFichier()
	{
		this.cheminFichier = null;
	}
}
