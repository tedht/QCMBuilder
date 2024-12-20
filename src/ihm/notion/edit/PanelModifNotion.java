package ihm.notion.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controleur.Controleur;
import ihm.IHM;

public class PanelModifNotion extends PanelEditNotion
{
	private int idNot;

	public PanelModifNotion(Controleur ctrl, IHM ihm, int idNot)
	{
		super(ctrl, ihm);
		this.idNot = idNot;

		this.txtNom.setText(this.ctrl.getNotion(this.idNot).getNom());
	}

	@Override
	public boolean enregistrer() 
	{
		String nouveauNom = this.txtNom.getText();

		List<String> lstErreurs = new ArrayList<String>();

		if(nouveauNom.isEmpty()) 
			lstErreurs.add("Le nom est vide");
		else if(this.ctrl.getNotionParNom(this.ctrl.getRessourceSelectionnee().getCode(), nouveauNom) != null)
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

		this.ctrl.modiferNotion(this.idNot, nouveauNom);
		
		this.ihm.reinitAffichageRessource();	
		this.ihm.reinitAffichageNotion();
		return true;
	}
}
