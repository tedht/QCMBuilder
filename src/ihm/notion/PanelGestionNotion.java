package ihm.notion;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelGestion;
import metier.entite.Notion;

public class PanelGestionNotion extends PanelGestion
{
	private JPanel panelInstruction;

	public PanelGestionNotion(Controleur ctrl, IHM ihm) 
	{
		super(ctrl, ihm);
		
		this.lblTitre  .setText("Notions :");
		this.btnAjouter.setText("Nouvelle Notion");
		this.btnAjouter.setEnabled(false);

		this.panelInstruction = new JPanel();
		this.panelInstruction.add(new JLabel("Double-cliquez sur une ressource afin d'afficher les notions associées."));

		this.afficher();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnAjouter)
		{
			this.ihm.creerNotion();
		}
	}

	@Override
	public void afficher() 
	{
		this.panelContenu.removeAll();

		PanelNotion panelCarte;

		if(this.ctrl.getRessourceActive() == null)
		{
			this.btnAjouter.setEnabled(false);
			
			this.panelContenu.add(this.panelInstruction);

			for(int i = 9; i > 0; i--)
			{
				panelCarte = new PanelNotion(null, null, " ");
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}
		else
		{
			this.btnAjouter.setEnabled(true);

			for(Notion notion : this.ctrl.getNotions(this.ctrl.getRessourceActive()))
			{
				panelCarte = new PanelNotion(this.ctrl, this.ihm, notion.getNom());
				this.panelContenu.add(panelCarte);
			}

			for(int i = 10 - this.ctrl.getNotions(this.ctrl.getRessourceActive()).size(); i > 0; i--)
			{
				panelCarte = new PanelNotion(null, null, " ");
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}

		this.revalidate();
		this.repaint();
	}
	
}
