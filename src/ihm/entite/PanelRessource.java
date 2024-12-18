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
public class PanelRessource extends PanelEntite
{
	/**
	 * Constructeur de la classe PanelRessource.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelRessource(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		super(ctrl, ihm, titre, sousTitre);
		
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
			
		}

		if(e.getSource() == this.btnSupprimer)
		{
			//this.ctrl.supprimerRessource(this.lblSousTitre.getText());
			this.ihm.reinitAffichage();
		}
	}


	private class GereSourisRessource extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelRessource.this.ctrl.setRessourceActive(
				PanelRessource.this.ctrl.getRessource(PanelRessource.this.lblTitre.getText())
			);
			PanelRessource.this.ihm.reinitAffichage();
		}
	}
}
