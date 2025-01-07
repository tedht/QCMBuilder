package metier.entite.question;

import java.awt.Color;

/**
 * Enumération représentant les difficultés.
 * Cette énumération inclut des méthodes utilitaires pour manipuler et convertir les difficultés.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public enum Difficulte
{
	TRES_FACILE(0),
	FACILE     (1),
	MOYEN      (2),
	DIFFICILE  (3);


	/*-----------*/
	/* Attributs */
	/*-----------*/

	private final int valeur;


	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur privé de l'énumération.
	 * 
	 * @param valeur La valeur entière associée à la difficulté.
	 */
	private Difficulte(int valeur)
	{
		this.valeur = valeur;
	}


	/*---------*/
	/* Getters */
	/*---------*/

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
	 * Retourne la couleur associé à la difficulté.
	 * 
	 * @return la couleur associé à la difficulté.
	 */
	public Color getCouleur()
	{
		return switch(this) 
		{
			case TRES_FACILE -> new Color(186, 221, 194);
			case FACILE      -> new Color(181, 188, 211);
			case MOYEN       -> new Color(202, 107, 119);
			case DIFFICILE   -> new Color(189, 198, 196);

			default -> Color.WHITE;
		};
	}



	/*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	 * Convertit une valeur entière en une difficulté.
	 * 
	 * @param  valeur                   La valeur entière.
	 * @return                          La difficulté correspondante.
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
}