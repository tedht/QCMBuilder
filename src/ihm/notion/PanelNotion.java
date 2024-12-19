package ihm.notion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

import controleur.Controleur;
import ihm.IHM;
import ihm.notion.PanelNotion;
import ihm.shared.PanelEntite;

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
	public PanelNotion(Controleur ctrl, IHM ihm, int id, String nom)
	{
		super(ctrl, ihm, nom, " ");

		this.id = id;

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnModifier.addActionListener(this);
		this.btnSupprimer.addActionListener(this);
		this.addMouseListener(new GereSourisNotion());
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnModifier)
		{
			
		}

		if(e.getSource() == this.btnSupprimer)
		{
			this.ctrl.supprimerNotion(this.id);
			this.ihm.reinitAffichageNotion();
		}
	}

	private class GereSourisNotion extends MouseAdapter
	{
		@Override
		public void mouseEntered(MouseEvent e) 
		{
			PanelNotion.this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
			PanelNotion.this.setBackground(Color.LIGHT_GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			PanelNotion.this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			PanelNotion.this.setBackground(Color.WHITE);
		}
	}
}
