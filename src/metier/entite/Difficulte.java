package metier.entite;

public enum Difficulte
{
	TRES_FACILE("TF"),
	FACILE     ("F" ),
	MOYEN      ("M" ),
	DIFFICILE  ("D" );

	private String difficulte;

	// Constructeur
	Difficulte(String difficulte)
	{
		this.difficulte = difficulte;
	}

	// Getters
	public String getDifficulte() { return this.difficulte; }

	public String toString()
	{
		return "Difficulte : " + this.difficulte;
	}

	public static void main(String[] args)
	{
		Difficulte d = Difficulte.TRES_FACILE;
		System.out.println(d.getDifficulte());
		System.out.println(d);
	}
}