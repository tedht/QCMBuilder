package ihm.ressource;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEntite;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelRessource extends PanelEntite
{
	private FrameGestionRessource frame;

	private String  code;
	private boolean select;
	
	/**
	 * Constructeur de la classe PanelRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelRessource(Controleur ctrl, IHM ihm, FrameGestionRessource frame, String code, String nom)
	{
		super(ctrl, ihm, code + " " + nom, " ");
		
		this.frame = frame;

		this.code   = code;
		this.select = this.ctrl.getRessourceSelectionnee() == this.ctrl.getRessource(this.code);

		if(this.select)
		{
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			this.setBackground(Color.LIGHT_GRAY);
		}
		else
		{
			this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			this.setBackground(Color.WHITE);
		}

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisRessource());
		this.btnModifier.addActionListener(this);
		this.btnSupprimer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnModifier)
		{
			this.ihm.editRessource(this.code);
		}

		if(e.getSource() == this.btnSupprimer)
		{
			int choix = JOptionPane.showConfirmDialog(
                this.frame,
                "Voulez-vous vraiment supprimer la ressource \"" + this.lblTitre.getText() + "\" ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
			);

			if (choix == JOptionPane.YES_OPTION) 
			{
				this.ctrl.supprimerRessource(this.code);
				this.ihm.reinitAffichageRessource();
				this.ihm.reinitAffichageNotion();
			}
		}
	}


	private class GereSourisRessource extends MouseAdapter
	{
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			PanelRessource.this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			PanelRessource.this.setBackground(Color.LIGHT_GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			if(PanelRessource.this.select)
			{
				PanelRessource.this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
				PanelRessource.this.setBackground(Color.LIGHT_GRAY);
			}
			else
			{
				PanelRessource.this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
				PanelRessource.this.setBackground(Color.WHITE);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if (!(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e))) return;
			
			if(PanelRessource.this.select)
			{
				PanelRessource.this.ctrl.setRessourceSelectionnee(null);	
			}
			else
			{
				PanelRessource.this.ctrl.setRessourceSelectionnee(
					PanelRessource.this.ctrl.getRessource(PanelRessource.this.code)
				);	
			}

			PanelRessource.this.ihm.reinitAffichageRessource();
			PanelRessource.this.ihm.reinitAffichageNotion(); 
		}
	}
}
