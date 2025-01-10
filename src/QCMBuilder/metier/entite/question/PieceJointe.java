package QCMBuilder.metier.entite.question;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Classe PieceJointe pour représenter une pièce jointe.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PieceJointe
{



	/*-----------*/
	/* Attributs */
	/*-----------*/

	private static int    numPieceJointe;

	private        String cheminFicOrig;
	private        String cheminFic;
	private        String nomPieceJointe;
	private        String extension;



	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe PieceJointe.
	 * 
	 * @param cheminFic le chemin du fichier.
	 */
	public PieceJointe(String cheminFic)
	{
		this(cheminFic, cheminFic);
	}


	/**
	 * Constructeur de la classe PieceJointe.
	 * 
	 * @param cheminFicOrig le chemin du fichier d'origine.
	 * @param cheminFic     le chemin du fichier.
	 */
	public PieceJointe(String cheminFicOrig, String cheminFic)
	{
		this.cheminFicOrig  = cheminFicOrig;
		this.cheminFic      = cheminFic;
		this.nomPieceJointe = this.cheminFic.substring(cheminFic.lastIndexOf('/') + 1, cheminFic.lastIndexOf('.'));
		this.extension      = this.cheminFic.substring(cheminFic.lastIndexOf('.') + 1);
	} 



	/*---------*/
	/* Getters */
	/*---------*/

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

	public String getCheminFicOrig() 
	{
		return this.cheminFicOrig;
	}

	public String getCheminFic()
	{
		return this.cheminFic;
	}

	/**
	 * Retourne le numéro de la dernière pièce jointe dans l'arborescence.
	 * 
	 * @return               le numéro de la dernière pièce jointe dans l'arborescence.
	 * @see    MaxFileFinder MaxFileFinder qui permet de trouver le fichier avec le plus grand numéro.
	 */
	public static int getNumPieceJointe()
	{
		Path repDep;
		MaxFileFinder visiteur;


		// Répertoire de départ pour l'exploration
		repDep = Paths.get("data/ressources/");

		// Création d'une instance de notre visiteur personnalisé
		visiteur = new MaxFileFinder();

		// Parcours de l'arborescence
		try
		{
			Files.walkFileTree(repDep, visiteur);
		}
		catch (IOException e)
		{
			//System.out.println("Erreur lors du walkFileTree : " + e.getMessage());
			e.printStackTrace();
		}
		

		// Affichage du résultat
		if (visiteur.getMaxFile() != null)
		{
			//System.out.println("Le fichier avec le plus grand numéro est : " + visiteur.getMaxFile());
			//System.out.println("Son numéro est : " + visiteur.getMaxNb());
		}
		else
		{
			//System.out.println("Aucun fichier correspondant trouvé.");
		}

		return visiteur.getMaxNb();
	}



	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifier le chemin original
	 * 
	 * @param  cheminFicOrig le nouveau chemin original.
	 * @return               true si le chemin original est modifié, false sinon.
	 */
	public boolean setCheminFicOrig(String cheminFicOrig)
	{
		if (cheminFicOrig == null) return false;

		this.cheminFicOrig = cheminFicOrig;
		return true;
	}

	/**
	 * Modifier le chemin du fichier.
	 * 
	 * @param cheminFic le nouveau chemin du fichier.
	 * @return          true si le chemin du fichier est modifié, false sinon.
	 */
	public boolean setCheminFic(String cheminFic)
	{
		if (cheminFic == null) return false;

		this.cheminFic = cheminFic;
		return true;
	}



	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	public void copierFichier()
	{
		File fichier = new File(this.cheminFic);
		
		try
		{
			Files.copy(Paths.get(this.cheminFicOrig), fichier.toPath(), StandardCopyOption.REPLACE_EXISTING);
			this.cheminFicOrig = this.cheminFic;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String nouveauNomFic(String extension)
	{
		PieceJointe.numPieceJointe = PieceJointe.getNumPieceJointe();

		return "fic" + String.format("%05d", ++PieceJointe.numPieceJointe) + "." + extension;
	}

	/**
	 * Retourne sous forme de texte l'objet PieceJointe.
	 * 
	 * @return l'objet PieceJointe sous forme de texte.
	 */
	public String toString()
	{
		return this.cheminFic;
	}
}