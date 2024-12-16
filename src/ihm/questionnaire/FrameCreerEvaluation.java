package ihm.questionnaire;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe représentant la fenêtre de créeation d'une évaluation
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class FrameCreerEvaluation extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private JPanel[]   tabPanel;
	private int        page;

	public FrameCreerEvaluation(Controleur ctrl, IHM ihm)
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		
		this.setTitle("Nouvelle Évaluation");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.tabPanel = new JPanel[2];
		this.page     = 0;

		this.tabPanel[0] = new PanelParametresEvaluation(ctrl, this);
		this.tabPanel[1] = new PanelAjoutEvaluation     (ctrl, this);

		this.setSize(IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1);

		this.add(this.tabPanel[page]);
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
		if(this.page > 0);
		{
			this.remove(this.tabPanel[this.page]); 
			this.page--;
			this.setSize(IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1);
			this.add   (this.tabPanel[this.page]);

		}
	}
	public void pageSuivante()
	{
		if(this.page < this.tabPanel.length-1);
		{
			this.remove(this.tabPanel[this.page]);
			this.page++;
			this.setSize(IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_2);
			this.add   (this.tabPanel[this.page]);
		}
	}

	public void genererEvaluation()
	{
		this.ctrl.genererEvaluation();
	}
}
