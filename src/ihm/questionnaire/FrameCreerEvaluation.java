package ihm.questionnaire;

import javax.swing.JFrame;
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

		this.panelParametresEvaluation = new PanelParametresEvaluation(ctrl, this);
		this.panelAjoutEvaluation      = new PanelAjoutEvaluation     (ctrl, this);

		this.pagePrecedente();
	}

	public void reinitAffichage() 
	{
		this.ihm.reinitAffichage();
	}

	/*
	 * Il n'y a que 2 pages, donc on simplifie la gestion de la taille de la fenêtre
	 * 
	 */

	public void pagePrecedente()
	{
		this.remove    (this.panelAjoutEvaluation); 
		this.setSize   (IHM.LARGEUR_CREER_EVALUATION, IHM.HAUTEUR_CREER_EVALUATION_PAGE_1);
		this.add       (this.panelParametresEvaluation);
		this.revalidate();
		this.repaint   ();
	}
	public void pageSuivante()
	{
		this.remove    (this.panelParametresEvaluation); 
		this.setSize   (IHM.LARGEUR_CREER_EVALUATION, IHM.HAUTEUR_CREER_EVALUATION_PAGE_2);
		this.add       (this.panelAjoutEvaluation);
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
