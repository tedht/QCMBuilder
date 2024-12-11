package ihm.edition.question;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;

/**
 * Classe représentant la fenêtre d'édition (création ou modification) d'une question
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class FrameEditionQuestion extends JFrame
{
	private PanelEditionQuestion panelEditionQuestion;
	
	/**
	 * Constructeur de la classe FrameEditionQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditionQuestion(Controleur ctrl) 
	{
		this.setTitle("Créer une Question");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditionQuestion = new PanelEditionQuestion(ctrl);
		this.add(this.panelEditionQuestion);

		this.pack();
	}
	
}
