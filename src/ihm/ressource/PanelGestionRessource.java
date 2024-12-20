package ihm.ressource;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelGestion;
import metier.entite.Ressource;

public class PanelGestionRessource extends PanelGestion
{
	private FrameGestionRessource frame;

	private JPanel panelInstruction;

	public PanelGestionRessource(Controleur ctrl, IHM ihm, FrameGestionRessource frame) 
	{
		super(ctrl, ihm);

		this.frame = frame;
		
		this.lblTitre  .setText("Ressources :");
		this.btnAjouter.setText("Nouvelle Ressource");


		this.panelInstruction = new JPanel();
		this.panelInstruction.add(new JLabel("Cliquez sur le bouton \"Nouvelle Ressource\" pour créer une ressource"));

		this.afficher();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAjouter)
		{
			this.ihm.editRessource();
		}
	}

	@Override
	public void afficher() 
	{
		this.panelContenu.removeAll();

		PanelRessource panelRessource;
		if(this.ctrl.getRessources().isEmpty())
		{
			this.panelContenu.add(this.panelInstruction);
	
			for(int i = 9; i > 0; i--)
			{
				panelRessource = new PanelRessource(this.ctrl, this.ihm, this.frame, "", "");
				panelRessource.setVisible(false);
				this.panelContenu.add(panelRessource);
			}
	
		}
		else
		{
			for(Ressource ressource : this.ctrl.getRessources())
			{
				panelRessource = new PanelRessource(this.ctrl, this.ihm, this.frame,
													ressource.getCode(), 
													ressource.getNom());
				this.panelContenu.add(panelRessource);
			}
	
			for(int i = 10 - this.ctrl.getRessources().size(); i > 0; i--)
			{
				panelRessource = new PanelRessource(this.ctrl, this.ihm, this.frame, "", "");
				panelRessource.setVisible(false);
				this.panelContenu.add(panelRessource);
			}
	
		}

		this.revalidate();
		this.repaint();
	}
}
