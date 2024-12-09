package ihm.composants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;

public abstract class PanelCarte extends JPanel
{
	protected Controleur ctrl;

	protected JLabel lblTitre;
	protected JLabel lblSousTitre;
	protected JPanel panelInfo;

	protected ImageIcon imageIcon;

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

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.panelInfo.add(this.lblTitre);
		this.panelInfo.add(this.lblSousTitre);
		this.add(this.panelInfo, BorderLayout.SOUTH);

		this.add(new JLabel(imageIcon), BorderLayout.CENTER);
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
