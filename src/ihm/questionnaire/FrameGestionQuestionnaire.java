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
public class FrameGestionQuestionnaire extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelParametresQuestionnaire panelParametresQuestionnaire;
	private PanelAjoutQuestionnaire      panelAjoutQuestionnaire;

	public FrameGestionQuestionnaire(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		
		this.setTitle("Nouvelle Évaluation");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);

		this.panelParametresQuestionnaire = new PanelParametresQuestionnaire(this.ctrl, this);
		this.panelAjoutQuestionnaire      = new PanelAjoutQuestionnaire     (this.ctrl, this);

		this.pagePrecedente();
	}

	public void pagePrecedente()
	{
		this.remove    (this.panelAjoutQuestionnaire); 
		this.add       (this.panelParametresQuestionnaire);
		this.setSize   (IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1);
		this.revalidate();
		this.repaint   ();
	}
	
	public void pageSuivante()
	{
		this.remove    (this.panelParametresQuestionnaire);
		this.add       (this.panelAjoutQuestionnaire);
		this.setSize   (IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_2);
		this.repaint   ();
		this.revalidate();
		this.repaint   ();
	}

	public void creerQuestionnaire()
	{
		String  codeRes        = this.panelParametresQuestionnaire.getCodeRes       ();
		boolean chronometre    = this.panelParametresQuestionnaire.estChronometree  ();
		int[][] tabNbQuestions = this.panelAjoutQuestionnaire     .getTabNbQuestions();
		
		this.ctrl.creerQuestionnaire(codeRes, chronometre, tabNbQuestions);
		this.ihm.finaliserQuestionnaire();
	}

	public String getCodeRes()
	{
		return this.panelParametresQuestionnaire != null ? this.panelParametresQuestionnaire.getCodeRes() : "";
	}

	
	public void maj()
	{
		String codeRes =   this.panelParametresQuestionnaire.getRessource() != null 
		                 ? this.panelParametresQuestionnaire.getRessource().getCode()
						 : "";
						 
		this.panelAjoutQuestionnaire.majTabNotions(codeRes);
	}
}
