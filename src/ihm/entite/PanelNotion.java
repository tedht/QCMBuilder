package ihm.entite;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelNotion extends PanelEntite
{	
	private int id;
	
	/**
	 * Constructeur de la classe PanelNotion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelNotion(Controleur ctrl, IHM ihm, String titre, String sousTitre, int id)
	{
		super(ctrl, ihm, titre, sousTitre);

		this.id = id;

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisNotion());
		this.btnModifier.addActionListener(this);
		this.btnSupprimer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnModifier)
		{
			
		}

		if(e.getSource() == this.btnSupprimer)
		{
			//this.ctrl.supprimerNotion(id);
			this.ihm.reinitAffichage();
		}
	}

	private class GereSourisNotion extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelNotion.this.ctrl.setNotionActive(
				PanelNotion.this.ctrl.getRessourceActive().getNotion(PanelNotion.this.lblTitre.getText())
			);
			PanelNotion.this.ihm.reinitAffichage();
		}
	}
}
