package ihm.entite;

import java.awt.*;
import java.awt.event.*;

import controleur.Controleur;
import ihm.IHM;
import metier.entite.Notion;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelQuestion extends PanelEntite
{
	private int     id;
	private boolean detail;
	
	/**
	 * Constructeur de la classe PanelQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelQuestion(Controleur ctrl, IHM ihm, String titre, String sousTitre, int id)
	{
		super(ctrl, ihm, titre, sousTitre);
		
		this.detail = false;

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisQuestion());
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
			//this.ctrl.supprimerQuestion(id);
			this.ihm.reinitAffichage();
		}
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
