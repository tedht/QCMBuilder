package metier;

import java.util.Objects;

public class Notion 
{
	private String nom;
	
	public Notion(String nom) 
	{
		if(!nom.equals(null))
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
		if(!nom.equals(null) && !nom.equals(this.nom))
		{
			this.nom = nom;
			return true;
		}

		return false;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notion notion = (Notion) o;
        return Objects.equals(nom, notion.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
