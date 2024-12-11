package metier.entite.question;

public enum TypeQuestion 
{
	QCM        ("QCM"),
	ASSOCIATION("Association" ),
	ELIMINATION("Elimination" );

	private String type;

	// Constructeur
	private TypeQuestion(String type)
	{
		this.type = type;
	}

	public String toString()
	{
		return this.type;
	}
}
