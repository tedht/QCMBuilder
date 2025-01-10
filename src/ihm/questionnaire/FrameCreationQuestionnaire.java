package ihm.questionnaire;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe représentant la fenêtre de création d'une évaluation
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-16 Norme ISO-8601
 */
public class FrameCreationQuestionnaire extends JFrame
{
	private Controleur ctrl;
	private IHM        ihm;

	private PanelParametresQuestionnaire panelParametresQuestionnaire;
	private PanelAjoutQuestionnaire      panelAjoutQuestionnaire;

	/**
	 * Constructeur de la classe FrameCreationQuestionnaire
	 * 
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public FrameCreationQuestionnaire(Controleur ctrl, IHM ihm)
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

	/**
	 * Affiche la première page de création du questionnaire (paramètres généraux).
	 */
	public void pagePrecedente()
	{
		this.remove    (this.panelAjoutQuestionnaire); 
		this.add       (this.panelParametresQuestionnaire);

		this.setSize   (IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1);
		this.revalidate();
		this.repaint   ();
	}
	
	/**
	 * Affiche la seconde page de création du questionnaire (ajout de questions).
	 */
	public void pageSuivante()
	{
		// Reinitialise le tableau
		this.panelAjoutQuestionnaire.majTabNbQuestions(this.panelParametresQuestionnaire.getCodeRes());
		
		this.remove    (this.panelParametresQuestionnaire);
		this.add       (this.panelAjoutQuestionnaire);

		this.setSize   (IHM.LARGEUR_CREER_QUESTIONNAIRE, IHM.HAUTEUR_CREER_QUESTIONNAIRE_PAGE_2);
		this.revalidate();
		this.repaint   ();
	}

	/**
	 * Crée le questionnaire en récupérant les informations saisies par l'utilisateur.
	 */
	public void creerQuestionnaire()
	{
		// Récupération des valeurs saisies par l'utilisateur.
		String  codeRes        = this.panelParametresQuestionnaire.getCodeRes       ();
		boolean chronometre    = this.panelParametresQuestionnaire.estChronometre   ();
		int[][] tabNbQuestions = this.panelAjoutQuestionnaire     .getTabNbQuestions();
		
		// Traitement
		this.ctrl.creerQuestionnaire(codeRes, chronometre, tabNbQuestions);
		this.ihm.exporterQuestionnaire();
	}

	/**
	 * Récupère le code de la ressource sélectionnée dans la page des paramètres.
	 * 
	 * @return Le code de la ressource ou une chaîne vide si le panneau n'est pas initialisé
	 */
	public String getCodeRes()
	{
		return this.panelParametresQuestionnaire.getCodeRes();
	}

	/**
	 * Réinitialise la liste déroulante des ressources et désactive les boutons radio.
	 */
	public void reinitAffichage()
	{
		this.panelParametresQuestionnaire.reinitDdlstRessources();
		this.panelParametresQuestionnaire.reinitRbChronometre  ();
	}
}
