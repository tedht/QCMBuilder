package metier.entite;

public class Notion 
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private String nom;
	
	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Notion(String nom)
	{
		if(nom != null)
		{
			this.nom = nom;
		}
	}

	/*---------*/
	/* Getters */
	/*---------*/
	public String getNom()
	{
		return this.nom;
	}

	/*---------*/
	/* Setters */
	/*---------*/
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
