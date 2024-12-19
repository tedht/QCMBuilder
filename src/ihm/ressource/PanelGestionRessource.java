package ihm.ressource;

import java.awt.event.ActionEvent;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelGestion;
import metier.entite.Ressource;

public class PanelGestionRessource extends PanelGestion
{
	public PanelGestionRessource(Controleur ctrl, IHM ihm) 
	{
		super(ctrl, ihm);
		
		this.lblTitre  .setText("Ressources :");
		this.btnAjouter.setText("Nouvelle Ressource");

		this.afficher();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAjouter)
		{
			this.ihm.creerRessource();
		}
	}

	@Override
	public void afficher() 
	{
		this.panelContenu.removeAll();

		System.out.println(this.ctrl.getRessourceActive());

		PanelRessource panelRessource;
		for(Ressource ressource : this.ctrl.getRessources())
		{
			panelRessource = new PanelRessource(this.ctrl, this.ihm, 
			                                ressource.getCode(), 
											ressource.getNom());
			this.panelContenu.add(panelRessource);
		}

		for(int i = 10 - this.ctrl.getRessources().size(); i > 0; i--)
		{
			panelRessource = new PanelRessource(this.ctrl, this.ihm, " ", " ");
			panelRessource.setVisible(false);
			this.panelContenu.add(panelRessource);
		}

		this.revalidate();
		this.repaint();
	}
}
