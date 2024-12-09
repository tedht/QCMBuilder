package ihm.gestionRessourceNotion;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;

public class FrameGestionRessource extends JFrame
{
	private PanelGestionRessource panelGestionRessource;

	public FrameGestionRessource(Controleur ctrl, int xMere, int yMere, Dimension dimMere)
	{
		this.setTitle("Creer Ressource");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelGestionRessource = new PanelGestionRessource(ctrl);
		this.add(this.panelGestionRessource);

		this.pack();

		this.setLocation(
			xMere + dimMere.width  / 2 - this.getWidth () / 2,
			yMere + dimMere.height / 2 - this.getHeight() / 2
		);

		this.setVisible(true);
	}
}
