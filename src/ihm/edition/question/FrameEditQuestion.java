package ihm.edition.question;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe représentant la fenêtre d'édition (création ou modification) d'une question
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class FrameEditQuestion extends JFrame
{
	private IHM               ihm;
	private PanelEditQuestion panelEditQuestion;
	
	/**
	 * Constructeur de la classe FrameEditQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public FrameEditQuestion(Controleur ctrl, IHM ihm) 
	{
		this.ihm = ihm;
		
		this.setTitle("Créer une Question");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelEditQuestion = new PanelEditQuestion(ctrl, this);
		this.add(this.panelEditQuestion);

		this.setSize(IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_1);
	}

	public void reinitAffichage() 
	{
		this.ihm.reinitAffichage();
	}
	
}
