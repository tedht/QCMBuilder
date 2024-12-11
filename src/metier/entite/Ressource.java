package metier.entite;

import java.util.ArrayList;
import java.util.List;

public class Ressource 
{
	private String nom;
	private String cheminFichier;
	private List<Notion> lstNotions;

	public Ressource(String nom, String cheminFichier)
	{
		if (!nom.equals(null))
		{
			this.nom        = nom;
			this.lstNotions = new ArrayList<Notion>();
			this.cheminFichier = cheminFichier;
		} 
		else 
		{
			throw new IllegalArgumentException("Le nom de la ressource ne peut pas être null");
		}
	}
	public Ressource(String nom)
	{
		this(nom,null);
	}

	/*==============*/
	/*    GETTER    */
	/*==============*/

	public String getNom()
	{
		return this.nom;
	} 

	public String getCheminFichier()
	{
		return this.cheminFichier;
	}

	public List<Notion> getNotions() 
	{
		return this.lstNotions;
	}

	/*==============*/
	/*    SETTER    */
	/*==============*/

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

	public boolean setCheminFichier(String cheminFichier)
	{
		if (!cheminFichier.equals(null) && !cheminFichier.equals(this.cheminFichier)) {
			this.cheminFichier = cheminFichier;
			return true;
		}

		return false;
		// Le nouveau cheminFichier ne peux pas être null au nom actuel
	}


	/*==============*/
	/*   MÉTHODES   */
	/*==============*/

	public boolean ajouterNotion(Notion notion) 
	{
		if (notion != null) 
		{
			if (!this.lstNotions.contains(notion)) 
			{
				this.lstNotions.add(notion);
				return true;
			} else {
				String nom = notion.getNom();
				char lastNameChar = nom.charAt(nom.length() - 1);
	
				if (Character.isDigit(lastNameChar)) 
				{
					int lastNumber = Character.getNumericValue(lastNameChar);
					notion.setNom(nom.substring(0, nom.length() - 1) + (lastNumber + 1));
				} else {
					notion.setNom(nom + "1");
				}
	
				return ajouterNotion(notion); 
			}
		}
		return false;
	}
	

	public boolean modifierNotion(Notion notion, String nouveauNom) 
	{
		if (this.lstNotions.contains(notion) &&
			!nouveauNom.equals(null) &&
			!notion.getNom().equals(nouveauNom))
		{
			notion.setNom(nouveauNom);
			return true;
		}

		return false;
	}

	public boolean supprimerNotion(Notion notion) 
	{
		if (this.lstNotions.contains(notion))
		{
			this.lstNotions.remove(notion);
			return true;
		}

		return false;
	}

	public boolean supprimerFichier()
	{
		this.cheminFichier = null;
		return true;
	}

	public Notion getNotion(String nomNotion) 
	{
		for(Notion notion : this.lstNotions)
		{
			if(notion.getNom().equals(nomNotion)) return notion;
		}
		return null;
	}
}
