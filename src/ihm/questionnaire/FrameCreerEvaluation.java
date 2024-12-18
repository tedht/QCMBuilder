package ihm.questionnaire;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe représentant la fenêtre de création d'une évaluation
 * 
 * @author Ted Herambert
 * @date 2024/12/16
 * @version 1.0
 */
public class FrameCreerEvaluation extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelParametresEvaluation panelParametresEvaluation;
	private PanelAjoutEvaluation      panelAjoutEvaluation;

	public FrameCreerEvaluation(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		
		this.setTitle("Nouvelle Évaluation");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelParametresEvaluation = new PanelParametresEvaluation(this.ctrl, this);
		this.panelAjoutEvaluation      = new PanelAjoutEvaluation     (this.ctrl, this);

		this.pagePrecedente();
	}

	public void reinitAffichage() 
	{
		this.ihm.reinitAffichage();
	}

	public void pagePrecedente()
	{
		this.remove    (this.panelAjoutEvaluation); 
		this.add       (this.panelParametresEvaluation);
		this.setSize   (IHM.LARGEUR_CREER_EVALUATION, IHM.HAUTEUR_CREER_EVALUATION_PAGE_1);
		this.revalidate();
		this.repaint   ();
	}
	
	public void pageSuivante()
	{
		this.remove    (this.panelParametresEvaluation);
		this.add       (this.panelAjoutEvaluation);
		this.setSize   (IHM.LARGEUR_CREER_EVALUATION, IHM.HAUTEUR_CREER_EVALUATION_PAGE_2);
		this.repaint   ();
		this.revalidate();
		this.repaint   ();
	}

	public void genererEvaluation()
	{
		//this.ctrl.genererEvaluation();
	}

	
	public void majTabNotions() 
	{
		this.panelAjoutEvaluation.majTabNotions(this.panelParametresEvaluation.getRessource());
	}
}
