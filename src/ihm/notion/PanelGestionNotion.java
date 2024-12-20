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
	private FrameGestionNotion frame;

	private JPanel panelInstruction;
	private JPanel panelAucuneNotion;

	public PanelGestionNotion(Controleur ctrl, IHM ihm, FrameGestionNotion frame) 
	{
		super(ctrl, ihm);

		this.frame = frame;
		
		this.lblTitre  .setText("Notions :");
		this.btnAjouter.setText("Nouvelle Notion");
		this.btnAjouter.setEnabled(false);

		this.panelInstruction = new JPanel();
		this.panelInstruction.add(new JLabel("Double-cliquez sur une ressource afin d'afficher les notions associées."));

		this.panelAucuneNotion = new JPanel();
		this.panelAucuneNotion.add(new JLabel("Cette ressource ne comporte aucune notion."));

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
				panelCarte = new PanelNotion(this.ctrl, this.ihm, this.frame, 0, "");
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}
		else if(this.ctrl.getNotions(this.ctrl.getRessourceActive().getCode()).size() == 0)
		{
			this.btnAjouter.setEnabled(true);
			
			this.panelContenu.add(this.panelAucuneNotion);

			for(int i = 9; i > 0; i--)
			{
				panelCarte = new PanelNotion(this.ctrl, this.ihm, this.frame, 0, "");
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}
		else
		{
			this.btnAjouter.setEnabled(true);
			
			for(Notion notion : this.ctrl.getNotions(this.ctrl.getRessourceActive().getCode()))
			{
				panelCarte = new PanelNotion(this.ctrl, this.ihm, this.frame, notion.getIdNot(), notion.getNom());
				this.panelContenu.add(panelCarte);
			}

			for(int i = 10 - this.ctrl.getNotions(this.ctrl.getRessourceActive().getCode()).size(); i > 0; i--)
			{
				panelCarte = new PanelNotion(this.ctrl, this.ihm, this.frame, 0, "");
				panelCarte.setVisible(false);
				this.panelContenu.add(panelCarte);
			}
		}

		this.revalidate();
		this.repaint();
	}
	
}
