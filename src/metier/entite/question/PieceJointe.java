package metier.entite.question;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

public class PieceJointe
{
	private static int NUM_PIECE_JOINTE;

	private String nomPieceJointe;
	private String extension;
	private File   fichier;


	/**
	 * Constructeur de la classe PieceJointe.
	 * 
	 * @param cheminFichier le chemin du fichier.
	 */
	public PieceJointe(String cheminFichierOriginal, String cheminFichier)
	{
		this.nomPieceJointe = "fic" + String.format("%05d", ++NUM_PIECE_JOINTE);
		this.extension      = cheminFichierOriginal.substring(cheminFichierOriginal.lastIndexOf('.') + 1);
		this.fichier        = new File(cheminFichier.substring(0, cheminFichier.lastIndexOf('/') + 1) + this.nomPieceJointe + "." + this.extension);

		try
		{
			Files.copy(Paths.get(cheminFichierOriginal), this.fichier.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch (Exception e)
		{
			System.out.println("Erreur lors de la copie du fichier : " + e.getMessage());
		}
	}

	/**
	 * Retourne le fichier.
	 * 
	 * @return le fichier.
	 */
	public File getFichier()
	{
		return this.fichier;
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
	public boolean setFichier(String cheminFichier)
	{
		if (cheminFichier == null) return false;

		this.fichier = new File(cheminFichier.substring(0, cheminFichier.lastIndexOf('/') + 1) + this.nomPieceJointe + "." + this.extension);
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

	/*public static int getNumPieceJointe()
	{
		Path startPath = Paths.get("data/");  // Répertoire à partir duquel commencer le parcours
		
		int max = 0;
		int nb;
		try {
			// Utilisation de Files.walk() pour parcourir l'arborescence de manière récursive
			Files.walk(startPath)
				.forEach(path -> {
					// Afficher chaque chemin de fichier ou répertoire rencontré
					
					nb = Integer.parseInt(path.toString().substring(3,8));
					System.out.println(path);

					if (max < nb) max = nb;
				});
		} catch (IOException e) {
			System.err.println("Erreur lors du parcours de l'arborescence : " + e.getMessage());
		}

		return 1;
	}*/


	/**
	 * Retourne sous forme de texte l'objet PieceJointe.
	 * 
	 * @return l'objet PieceJointe sous forme de texte.
	 */
	public String toString()
	{
		return this.nomPieceJointe + "." + this.extension + " : " + this.fichier.getPath();
	}


	/**
	 * Test de la classe PieceJointe.
	 * 
	 * @param args les arguments de la ligne de commande.
	 */
	public static void main(String[] args)
	{
		PieceJointe pj1, pj2, pj3;


		pj1 = new PieceJointe("data/test1.txt", "data/");
		pj2 = new PieceJointe("data/test2.csv", "data/");
		pj3 = new PieceJointe("data/test3.pdf", "data/");

		System.out.println(pj1);
		System.out.println(pj2);
		System.out.println(pj3);

		// System.out.println(PieceJointe.getNumPieceJointe());
	}
}
