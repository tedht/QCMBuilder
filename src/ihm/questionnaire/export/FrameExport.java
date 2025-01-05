package ihm.questionnaire.export;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe
 * 
 * @author Ted Herambert
 * @date 2024/12/18
 * @version 1.0
 */
public class FrameExport extends JFrame
{
	private PanelExport panelGeneration;

	/**
	 * Constructeur
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameExport(Controleur ctrl, IHM ihm)
	{
		this.setTitle("Générer une Evaluation");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelGeneration = new PanelExport(ctrl, ihm, this);
		this.add(this.panelGeneration);

		this.pack();
	}
}
