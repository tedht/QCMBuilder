package ihm.ressource.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;

public class PanelCreationRessource extends PanelEditRessource
{
	public PanelCreationRessource(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
	}

	@Override
	public boolean enregistrer() 
	{
		String code = this.txtCode.getText();
		String nom  = this.txtNom .getText();

		List<String> lstErreurs = new ArrayList<String>();

		if (code.isEmpty())
			lstErreurs.add("Le code est vide.");
		else if(this.ctrl.getRessource(code) != null)
			lstErreurs.add("Le code existe déjà.");

		if (nom.isEmpty()) 
			lstErreurs.add("Le nom est vide");

		if(lstErreurs.size() != 0)
		{
			String message = "La ressource n'a pas été enregistrée pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this,
				message,
				"Échec de l'Enregistrement",
				JOptionPane.ERROR_MESSAGE
			);
			return false;
		}

		this.ctrl.creerRessource(code, nom);
		this.ihm.reinitAffichageRessource();
		this.ihm.reinitAffichageNotion();
		return true;
	}
}
