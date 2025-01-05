package metier.banque;

import java.io.File;

public abstract class Banque 
{
	public boolean supprimerDossier(File dossier)
	{
		if (dossier.isDirectory()) {
            for (File file : dossier.listFiles()) 
			{
                if (!this.supprimerDossier(file)) 
				{
                    return false;
                }
            }
        }

        return dossier.delete();
	}
}
