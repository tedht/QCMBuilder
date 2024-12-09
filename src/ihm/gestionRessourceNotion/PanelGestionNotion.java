package ihm.gestionRessourceNotion;

import controleur.Controleur;

public class PanelGestionNotion extends PanelGestionGenerique
{
	public PanelGestionNotion(Controleur ctrl)
	{
		super(ctrl);
	}

	public String getType()
	{
		return "Notion";
	}

	public void valider()
	{
		this.ctrl.creerNotion(this.txtNom.getText(), this.txtImage.getText());
	}
}