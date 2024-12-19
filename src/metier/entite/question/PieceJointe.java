package metier.entite.question;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/** Classe PieceJointe
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PieceJointe
{
	private static int numPieceJointe;

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
		PieceJointe.numPieceJointe = PieceJointe.getNumPieceJointe();

		this.nomPieceJointe = "fic" + String.format("%05d", ++PieceJointe.numPieceJointe);
		this.extension      = cheminFichierOriginal.substring(cheminFichierOriginal.lastIndexOf('.') + 1);
		this.fichier        = new File(cheminFichier.substring(0, cheminFichier.lastIndexOf('/') + 1) +
		                      this.nomPieceJointe + "." + this.extension);

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

	public static int getNumPieceJointe()
	{
		Path repDep;
		MaxFileFinder visiteur;


		// Répertoire de départ pour l'exploration
		repDep = Paths.get("ressources/");

		// Création d'une instance de notre visiteur personnalisé
		visiteur = new MaxFileFinder();

		// Parcours de l'arborescence
		try
		{
			Files.walkFileTree(repDep, visiteur);
		}
		catch (IOException e)
		{
			System.out.println("Erreur lors du walkFileTree : " + e.getMessage());
		}
		

		// Affichage du résultat
		if (visiteur.getMaxFile() != null)
		{
			System.out.println("Le fichier avec le plus grand numéro est : " + visiteur.getMaxFile());
			System.out.println("Son numéro est : " + visiteur.getMaxNb());
		}
		else
		{
			System.out.println("Aucun fichier correspondant trouvé.");
		}

		return visiteur.getMaxNb();
	}


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


		pj1 = new PieceJointe("data/test1.txt", "ressources/R1.01 Initiation au développement/Abstraction/");
		pj2 = new PieceJointe("data/test2.csv", "ressources/R1.02 Développement interfaces Web/CSS/");
		pj3 = new PieceJointe("data/test3.pdf", "ressources/R3.06 Architecture des réseaux/Sockets/");

		System.out.println(pj1);
		System.out.println(pj2);
		System.out.println(pj3);

		System.out.println(PieceJointe.getNumPieceJointe());
	}
}