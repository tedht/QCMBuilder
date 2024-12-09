package metier;

import java.util.ArrayList;
import java.util.List;

public class Ressource 
{
	private String nom;
	private List<Notion> notions;

	public Ressource(String nom)
	{
		if(!nom.equals(null))
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

	public List<Notion> GetNotions() 
	{
		return this.notions;
	}

	/*--------------*/
	//    SETTER    //
	/*--------------*/

	public boolean setNom(String nom) 
	{
		if(!nom.equals(null) && !nom.equals(this.nom))
		{
			this.nom = nom;
			return true;
		}

		return false;
	}


	/*--------------*/
	//   MÉTHODES   //
	/*--------------*/

	public boolean ajouterNotion(Notion notion) 
	{
		if(notion != null && !notions.contains(notion))
		{
			this.notions.add(notion);
			return true;
		}
		return false;
	}

	public boolean modifierNotion(int id, String nouveauNom) 
	{
		if((id >= 0 && id <= this.notions.size())	&&
			!nouveauNom.equals(null) 		&& 
			!nouveauNom.equals(this.nom))
		{
			this.notions.get(id).setNom(nouveauNom);
			return true;
		}

		return false;
	}

	public boolean supprimerNotion(int id) 
	{
		if(id >= 0 && id <= this.notions.size())
		{
			this.notions.remove(id);
		}

		return false;
	}
}
