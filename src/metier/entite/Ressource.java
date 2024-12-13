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
	private List<Notion> lstNotions;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe Ressource avec le chemin du fichier.
	 * 
	 * @param  nom                       Le nom de la Ressource (non null).
	 * @param  cheminFichier             Le chemin du fichier associer à la ressource.
	 * @throws IlleagalArgumentException Si le nom est null.
	 */
	public Ressource(String nom, String cheminFichier)
	{
		if (!nom.equals(null))
		{
			this.nom           = nom;
			this.lstNotions    = new ArrayList<Notion>();
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
	public Ressource(String nom)
	{
		this(nom,null);
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
	public List<Notion> getNotions() 
	{
		return this.lstNotions;
	}

	/**
	 * Retourne une notion de la liste des notions associée à Ressource.
	 * 
	 * @param  nomNotion le nom de la notion recherchée.
	 * @return           la notion.
	 */
	public Notion getNotion(String nomNotion) 
	{
		for(Notion notion : this.lstNotions)
		{
			if (notion.getNom().equals(nomNotion))
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
	public boolean ajouterNotion(Notion notion) 
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
				String nom          = notion.getNom();
				char   lastNameChar = nom.charAt(nom.length() - 1);

				if (Character.isDigit(lastNameChar)) 
				{
					int lastNumber = Character.getNumericValue(lastNameChar);
					notion.setNom(nom.substring(0, nom.length() - 1) + (lastNumber + 1));
				}
				else
				{
					notion.setNom(nom + "1");
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
	public boolean modifierNotion(Notion notion, String nouveauNom) 
	{
		if (this.lstNotions.contains(notion)  &&
			!nouveauNom.equals(null) &&
			!notion.getNom().equals(nouveauNom))
		{
			notion.setNom(nouveauNom);

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
	public boolean supprimerNotion(Notion notion) 
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
