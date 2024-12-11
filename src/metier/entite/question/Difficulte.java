package metier.entite.question;

public enum Difficulte
{
	TRES_FACILE("TF"),
	FACILE     ("F" ),
	MOYEN      ("M" ),
	DIFFICILE  ("D" );

	private String difficulte;

	// Constructeur
	private Difficulte(String difficulte)
	{
		this.difficulte = difficulte;
	}

	public String toString()
	{
		return this.difficulte;
	}

	public static void main(String[] args)
	{
		Difficulte d = Difficulte.TRES_FACILE;
		System.out.println(d);
	}
}