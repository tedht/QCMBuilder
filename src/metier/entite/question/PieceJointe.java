package metier.entite.question;

public class PieceJointe
{
	private static int NUM_PIECE_JOINTE;

	private String nomPieceJointe;
	private String extension;


	/**
	 * Constructeur de la classe PieceJointe.
	 * 
	 * @param nomPieceJointe le nom de la pièce jointe.
	 * @param extension      l'extension de la pièce jointe.
	 */
	public PieceJointe(String nomPieceJointe, String extension)
	{
		this.nomPieceJointe = "fic" + String.format("%05d", NUM_PIECE_JOINTE++);
		this.extension      = extension;
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
		return this.nomPieceJointe + "." + this.extension;
	}
}
