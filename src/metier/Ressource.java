package metier;

import java.util.ArrayList;
import java.util.List;

public class Ressource 
{
	private String nom;
	private List<Notion> notions;

	public Ressource(String nom)
	{
		if (!nom.equals(null))
		{
			this.nom = nom;
			this.notions = new ArrayList<Notion>();
		}
		// else
		// {
		// 	System.out.println("Le nom de la ressource ne peut pas être null");
		// }
	}

	/*--------------*/
	//    GETTER    //
	/*--------------*/

	public String getNom()
	{
		return this.nom;
	} 

	public List<Notion> getNotions() 
	{
		return this.notions;
	}

	/*--------------*/
	//    SETTER    //
	/*--------------*/

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


	/*--------------*/
	//   MÉTHODES   //
	/*--------------*/

	public boolean ajouterNotion(Notion notion) 
	{
		if (notion != null) 
		{
			if (!notions.contains(notion)) 
			{
				this.notions.add(notion);
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
		if (this.notions.contains(notion) &&
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
		if (this.notions.contains(notion))
		{
			this.notions.remove(notion);
			return true;
		}

		return false;
	}
}
