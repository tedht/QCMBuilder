package QCMBuilder.metier.entite.question;


import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Classe MaxFileFinder pour trouver le fichier ayant le plus grand numéro dans un répertoire (hérite de SimpleFileVisitor<Path>).
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class MaxFileFinder extends SimpleFileVisitor<Path>
{



	/*-----------*/
	/* Attributs */
	/*-----------*/

	private String maxFile;
	private int    maxNb;

	// Regex pour matcher "fic00001", "fic00002", etc.
	private final Pattern pattern = Pattern.compile("fic(\\d+)(\\.\\w+)?");



	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe MaxFileFinder.
	 */
	public MaxFileFinder()
	{
		this.maxFile   = null;
		this.maxNb     = -1;
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
	 * Retourne le chemin du fichier ayant le plus grand numéro trouvé.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getMaxFile() { return maxFile; }

	/**
	 * Retourne le plus grand numéro trouvé dans les fichiers.
	 * 
	 * @return le numéro.
	 */
	public int getMaxNb() { return maxNb; }


	
	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Méthode appelée pour chaque fichier visité lors du parcours d'un répertoire.
	 * Si le fichier correspond au motif (pattern) et contient un numéro, 
	 * cette méthode met à jour le fichier et le numéro maximum trouvés si le numéro est plus grand 
	 * que le précédent maximum.
	 * 
	 * @param  file  le chemin du fichier actuellement visité.
	 * @param  attrs les attributs basiques du fichier.
	 * @return       FileVisitResult.CONTINUE pour continuer le parcours des fichiers.
	 */
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	{
		String nomFichier;
		Matcher matcher;

		int nb;


		// Récupère le nom du fichier et le compare au motif
		nomFichier = file.getFileName().toString();
		matcher    = this.pattern.matcher(nomFichier);

		// Si le fichier correspond au motif "fic00001", "fic00002", etc.
		if (matcher.matches())
		{
			nb = Integer.parseInt(matcher.group(1)); // Récupère le numéro

			// Maximum
			if (nb > this.maxNb)
			{
				this.maxNb = nb;
				this.maxFile = file.toString();
			}
		}

		// Continue le parcours
		return FileVisitResult.CONTINUE;
	}
}
