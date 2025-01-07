package metier.banque;

import java.io.File;

/**
 * Classe abstraite Banque.
 * 
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class Banque 
{
	public boolean supprimerDossier(File dossier)
	{
		if (dossier.isDirectory())
			for (File file : dossier.listFiles())
				if (!this.supprimerDossier(file))
					return false;


		return dossier.delete();
	}
}
