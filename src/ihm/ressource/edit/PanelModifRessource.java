package ihm.ressource.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;

public class PanelModifRessource extends PanelEditRessource
{
	private String code;
	
	public PanelModifRessource(Controleur ctrl, IHM ihm, String code) 
	{
		super(ctrl, ihm);
		this.code = code;

		this.txtCode.setText(code);
		this.txtNom .setText(this.ctrl.getRessource(code).getNom());
	}

	@Override
	public boolean enregistrer() 
	{
		String nouveauCode = this.txtCode.getText();
		String nouveauNom  = this.txtNom .getText();

		List<String> lstErreurs = new ArrayList<String>();

		if (nouveauCode.isEmpty())
			lstErreurs.add("Le code est vide.");
		else if(!nouveauCode.equals(this.code) && this.ctrl.getRessource(nouveauCode) != null)
			lstErreurs.add("Le code est déjà utilisé par une autre ressource.");

		if (nouveauNom.isEmpty()) 
			lstErreurs.add("Le nom est vide");

		if(lstErreurs.size() != 0)
		{
			String message = "La ressource n'a pas été modifée pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this,
				message,
				"Échec de la modification",
				JOptionPane.ERROR_MESSAGE
			);
			return false;
		}

		this.ctrl.modiferRessource(code, nouveauCode, nouveauNom);
		this.ihm.reinitAffichageRessource();
		this.ihm.reinitAffichageNotion();
		return true;
	}

	
}
