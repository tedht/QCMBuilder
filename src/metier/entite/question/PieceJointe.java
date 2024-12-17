package metier.entite.question;

public class PieceJointe
{
	private static int NUM_PIECE_JOINTE;

	private String cheminFichier;
	private String nomPieceJointe;
	private String extension;


	/**
	 * Constructeur de la classe PieceJointe.
	 * 
	 * @param nomPieceJointe le nom de la pièce jointe.
	 * @param extension      l'extension de la pièce jointe.
	 */
	public PieceJointe(String cheminFichier)
	{
		this.cheminFichier  = cheminFichier;
		this.nomPieceJointe = "fic" + String.format("%05d", ++NUM_PIECE_JOINTE);
		this.extension      = this.cheminFichier.substring(this.cheminFichier.lastIndexOf('.') + 1);
	}

	/**
	 * Retourne le chemin du fichier.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getCheminFichier()
	{
		return this.cheminFichier;
	}

	/**
	 * Retourne le nom de la pièce jointe.
	 * 
	 * @return le nom de la pièce jointe.
	 */
	public String getNomPieceJointe()
	{
		return this.nomPieceJointe;
	}

	/**
	 * Retourne l'extension de la pièce jointe.
	 * 
	 * @return l'extension de la pièce jointe.
	 */
	public String getExtension()
	{
		return this.extension;
	}


	/**
	 * Modifier le chemin du fichier.
	 * 
	 * @param cheminFichier le chemin du fichier.
	 * @return              true si le chemin du fichier est modifié, false sinon.
	 */
	public boolean setCheminFichier(String cheminFichier)
	{
		if (cheminFichier == null) return false;

		this.cheminFichier = cheminFichier;
		return true;
	}

	/**
	 * Modifier le nom de la pièce jointe.
	 * 
	 * @param nomPieceJointe le nom de la pièce jointe.
	 * @return               true si le nom de la pièce jointe est modifié, false sinon.
	 */
	public boolean setNomPieceJointe(String nomPieceJointe)
	{
		if (nomPieceJointe == null) return false;

		this.nomPieceJointe = nomPieceJointe;
		return true;
	}

	/**
	 * Modifier l'extension de la pièce jointe.
	 * 
	 * @param extension l'extension de la pièce jointe.
	 * @return          true si l'extension de la pièce jointe est modifiée, false sinon.
	 */
	public boolean setExtension(String extension)
	{
		if (extension == null) return false;

		this.extension = extension;
		return true;
	}


	/**
	 * Retourne sous forme de texte l'objet PieceJointe.
	 * 
	 * @return l'objet PieceJointe sous forme de texte.
	 */
	public String toString()
	{
		return this.nomPieceJointe + "." + this.extension + " : " + this.cheminFichier;
	}


	/**
	 * Test de la classe PieceJointe.
	 * 
	 * @param args les arguments de la ligne de commande.
	 */
	public static void main(String[] args)
	{
		PieceJointe pj1, pj2, pj3, pj;

		for (int cpt = 0 ; cpt < 1000 ; cpt++)
		{
			pj = new PieceJointe("data/test.png");
			System.out.println(pj);
		}

		pj1 = new PieceJointe("data/test1.txt");
		pj2 = new PieceJointe("data/test2.csv");
		pj3 = new PieceJointe("data/test3.pdf");

		System.out.println(pj1);
		System.out.println(pj2);
		System.out.println(pj3);
	}
}
