package ihm.entite;

import java.awt.*;
import java.awt.event.*;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelQuestion extends PanelEntite
{
	private boolean detail;
	
	/**
	 * Constructeur de la classe PanelQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelQuestion(Controleur ctrl, IHM ihm, String titre, String sousTitre)
	{
		super(ctrl, ihm, titre, sousTitre);
		
		this.detail = false;

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisQuestion());
	}

	private class GereSourisQuestion extends GereSouris
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelQuestion.this.detail = !PanelQuestion.this.detail;
			PanelQuestion.this.setBackground(Color.LIGHT_GRAY);
		}
	}
}
