package ihm.notion.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;

public class PanelCreationNotion extends PanelEditNotion
{
	public PanelCreationNotion(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
	}

	@Override
	public boolean enregistrer() 
	{
		String nom = this.txtNom.getText();

		List<String> lstErreurs = new ArrayList<String>();

		if(nom.isEmpty()) 
			lstErreurs.add("Le nom est vide");
		else if(this.ctrl.getNotionParNom(this.ctrl.getRessourceActive().getCode(), nom) != null)
			lstErreurs.add("La notion existe déjà");

		if(lstErreurs.size() != 0)
		{
			String message = "La notion n'a pas été enregistrée pour les raisons suivantes :\n";
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

		if(this.ctrl.getRessourceActive() != null)
			this.ctrl.editNotion(this.ctrl.getRessourceActive().getCode(), this.txtNom.getText());
		
		this.ihm.reinitAffichageRessource();	
		this.ihm.reinitAffichageNotion();
		return true;
	}
}
