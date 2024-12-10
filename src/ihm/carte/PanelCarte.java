package ihm.carte;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;

/**
 * Classe JPanel générique pour afficher une entité (ressource/notion/question).
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public abstract class PanelCarte extends JPanel
{
	protected Controleur ctrl;

	protected JLabel lblTitre;
	protected JLabel lblSousTitre;
	protected JLabel lblImage;
	
	protected JPanel panelInfo;

	protected ImageIcon imageIcon;

	/**
	 * Constructeur de la classe PanelCarte.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelCarte(Controleur ctrl, String titre, String sousTitre, String cheminImg)
	{
		this.ctrl = ctrl;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.lblTitre = new JLabel(titre);
		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 20));

		this.lblSousTitre = new JLabel(sousTitre);
		this.lblSousTitre.setFont(new Font("Arial", Font.BOLD, 12));

		this.panelInfo = new JPanel();
		this.panelInfo.setLayout(new BoxLayout(this.panelInfo, BoxLayout.Y_AXIS));
		this.panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.imageIcon = new ImageIcon(cheminImg);
		this.lblImage  = new JLabel(imageIcon);
		this.lblImage.setPreferredSize(new Dimension(200, 200));

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.panelInfo.add(this.lblTitre);
		this.panelInfo.add(this.lblSousTitre);
		this.add(this.panelInfo, BorderLayout.SOUTH);

		this.add(this.lblImage, BorderLayout.CENTER);
	}

	public void setImage(String cheminImg)
	{
		this.imageIcon = new ImageIcon(cheminImg); 
	}

	protected class GereSouris extends MouseAdapter
	{
		
		@Override
		public void mouseEntered(MouseEvent e) {
			setBackground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBackground(Color.WHITE);
		}
	}
}