package metier.entite.question;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Classe MaxFileFinder
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class MaxFileFinder extends SimpleFileVisitor<Path>
{
	private String maxFile;
	private int    maxNb;

	// Regex pour matcher "fic00001", "fic00002", etc.
	private final Pattern pattern = Pattern.compile("fic(\\d+)(\\.\\w+)?");

	public MaxFileFinder()
	{
		this.maxFile   = null;
		this.maxNb     = -1;
	}


	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	{
		String nomFichier;
		Matcher matcher;

		int nb;


		nomFichier = file.getFileName().toString();
		matcher    = pattern.matcher(nomFichier);

		if (matcher.matches())
		{
			nb = Integer.parseInt(matcher.group(1)); // Récupère le numéro

			if (nb > this.maxNb)
			{
				this.maxNb = nb;
				this.maxFile = file.toString();
			}
		}
		return FileVisitResult.CONTINUE;
	}

	public String getMaxFile()
	{
		return maxFile;
	}

	public int getMaxNb()
	{
		return maxNb;
	}
}
