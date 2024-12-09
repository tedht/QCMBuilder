package ihm.gestionRessourceNotion;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;

public class FrameGestionNotion extends JFrame
{
	private PanelGestionNotion panelGestionNotion;

	public FrameGestionNotion(Controleur ctrl, int xMere, int yMere, Dimension dimMere)
	{
		this.setTitle("Creer Notion");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelGestionNotion = new PanelGestionNotion(ctrl);
		this.add(this.panelGestionNotion);

		this.pack();

		this.setLocation(
			xMere + dimMere.width  / 2 - this.getWidth () / 2,
			yMere + dimMere.height / 2 - this.getHeight() / 2
		);

		this.setVisible(true);
	}
}
