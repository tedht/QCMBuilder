package QCMBuilder.metier.banque;


import java.io.File;

/**
 * Classe abstraite Banque.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class Banque 
{	
	/**
	 * Supprime le dossier et son contenu.
	 * 
	 * @param dossier le dossier à supprimer.
	 */
	public static boolean supprimerDossier(File dossier)
	{
		if (dossier.isDirectory())
			for (File file : dossier.listFiles())
				if (!Banque.supprimerDossier(file))
					return false;


		return dossier.delete();
	}
}
