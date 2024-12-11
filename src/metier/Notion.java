package metier;

public class Notion 
{
	private String nom;
	
	public Notion(String nom)
	{
		if(nom != null)
		{
			this.nom = nom;
		}
	}

	/*--------------*/
	//    GETTER    //
	/*--------------*/

	public String getNom()
	{
		return this.nom;
	}

	/*--------------*/
	//    SETTER    //
	/*--------------*/

	public boolean setNom(String nom) 
	{
		if(nom != null && !nom.equals(this.nom))
		{
			this.nom = nom;
			return true;
		}

		return false;
	}
}
