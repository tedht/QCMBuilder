package ihm.gestionRessourceNotion;

import controleur.Controleur;

public class PanelGestionRessource extends PanelGestionGenerique
{
	public PanelGestionRessource(Controleur ctrl)
	{
		super(ctrl);
	}

	public String getType()
	{
		return "Ressource";
	}

	public void valider()
	{
		this.ctrl.creerRessource(this.txtNom.getText(), this.txtImage.getText());
	}
}
