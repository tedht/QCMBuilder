package metier.entite.question;

/**
 * Enumération représentant les types de questions.
 * Cette énumération inclut des méthodes utilitaires pour manipuler et convertir les types de questions.
 * 
 * @author Equipe 03
 * @version 1.0 2024/12/12
 */
public enum TypeQuestion 
{
	QCM        (0),
	ASSOCIATION(1),
	ELIMINATION(2);



    /*-----------*/
	// Attributs //
	/*-----------*/

	private final int valeur;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
     * Constructeur privé de l'énumération.
     * 
     * @param valeur La valeur entière associée au type de question.
     */
	private TypeQuestion(int valeur)
	{
		this.valeur = valeur;
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
     * Obtient la valeur entière associée à ce type de question.
     * 
     * @return La valeur entière du type.
     */
    public int getValeur() 
    { 
        return this.valeur; 
    }


    
    /*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
     * Convertit une valeur entière en un type de question.
     * 
     * @param valeur La valeur entière.
     * @return Le type de question correspondant.
     * @throws IllegalArgumentException Si la valeur fournie ne correspond à aucun type.
     */
    public static TypeQuestion fromInt(int valeur) 
    {
        // Parcours de toutes les valeurs de l'énumération pour trouver une correspondance.
        for (TypeQuestion type : TypeQuestion.values()) 
        {
            if (type.getValeur() == valeur) 
            { 
                return type; 
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
			case    QCM         -> "QCM";
			case    ASSOCIATION -> "Association";
			case 	ELIMINATION -> "Elimination";
			
			default -> "";
		};
	}
}
