package metier.entite.question;

/**
 * Enumération représentant les difficultés.
 * Cette énumération inclut des méthodes utilitaires pour manipuler et convertir les difficultés.
 * 
 * @author Equipe 03
 * @date 2024/12/12
 * @version 1.0
 */
public enum Difficulte
{
	TRES_FACILE(0),
	FACILE     (1),
	MOYEN      (2),
	DIFFICILE  (3);

	private final int valeur;

	/**
     * Constructeur privé de l'énumération.
     * 
     * @param valeur La valeur entière associée à la difficulté.
     */
	private Difficulte(int valeur)
	{
		this.valeur = valeur;
	}

	/**
     * Obtient la valeur entière associée à cette difficulté.
     * 
     * @return La valeur entière de la difficulté.
     */
    public int getValeur() 
    { 
        return this.valeur; 
    }

	/**
     * Convertit une valeur entière en une difficulté.
     * 
     * @param valeur La valeur entière.
     * @return La difficulté correspondante.
     * @throws IllegalArgumentException Si la valeur fournie ne correspond à aucune difficulté.
     */
    public static Difficulte fromInt(int valeur) 
    {
        // Parcours de toutes les valeurs de l'énumération pour trouver une correspondance.
        for (Difficulte difficulte : Difficulte.values()) 
        {
            if (difficulte.getValeur() == valeur) 
            { 
                return difficulte; 
            }
        }

        // Lancer une exception si la valeur n'est pas valide.
        throw new IllegalArgumentException("Valeur difficulté non valide : " + valeur);
    }

	/**
     * Retourne une représentation en chaîne de caractères de la difficulté.
     * 
     * @return une représentation en chaîne de caractères de la difficulté.
     */
	public String toString()
	{
		return switch(this) 
		{
			case TRES_FACILE -> "TF";
			case FACILE      -> "F";
			case MOYEN       -> "M";
			case DIFFICILE   -> "D";

			default -> "";
		};
	}

	public static void main(String[] args)
	{
		Difficulte d = Difficulte.TRES_FACILE;
		System.out.println(d);
	}
}