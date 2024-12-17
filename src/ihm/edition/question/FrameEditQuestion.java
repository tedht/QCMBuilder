package ihm.edition.question;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;
import ihm.edition.question.proposition.PanelProp;
import ihm.edition.question.proposition.PanelPropAssoc;
import ihm.edition.question.proposition.PanelPropElim;
import ihm.edition.question.proposition.PanelPropQCM;

/**
 * Classe représentant la fenêtre d'édition (création ou modification) d'une question
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class FrameEditQuestion extends JFrame
{
	private Controleur              ctrl;
	private IHM                     ihm;
	private PanelParametresQuestion panelParametresQuestion;
	private PanelAjoutQuestion      panelAjoutQuestion;
	
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

		this.panelParametresQuestion = new PanelParametresQuestion(ctrl, this);
		this.panelAjoutQuestion      = new PanelAjoutQuestion     (this);

		this.pagePrecedente();
	}

	public void reinitAffichage() 
	{
		this.ihm.reinitAffichage();
	}
	
	public void pagePrecedente()
	{
		this.remove    (this.panelAjoutQuestion); 
		this.setSize   (IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_1);
		this.add       (this.panelParametresQuestion);
		this.revalidate();
		this.repaint   ();
	}

	public void pageSuivante()
	{
		this.remove    (this.panelParametresQuestion); 
		this.setSize   (IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_2);
		this.add       (this.panelAjoutQuestion);

		this.panelAjoutQuestion.afficherPropositions();

		this.revalidate();
		this.repaint   ();
	}

	public void enregistrer() 
	{
		String detailsQuestion = "";
		String sProp           = "";

		detailsQuestion += this.panelParametresQuestion.getRessource  () + '\t';
		detailsQuestion += this.panelParametresQuestion.getNotion     () + '\t';
		detailsQuestion += this.panelParametresQuestion.getDifficulte () + '\t';
		detailsQuestion += this.panelParametresQuestion.getTemps      () + '\t';
		detailsQuestion += this.panelParametresQuestion.getPoints     () + '\t';
		detailsQuestion += this.panelAjoutQuestion     .getIntitule   () + '\t';
		detailsQuestion += this.panelAjoutQuestion     .getExplication() + '\t';

		// On vérifie DANS LE METIER que les valeurs saisies sont valides
		List<String>    lstErreurs   = new ArrayList<String>();
		List<PanelProp> lstPanelProp = this.panelAjoutQuestion.getPanelProps();

		switch (this.panelParametresQuestion.getIndexTypeQuestion()) 
		{
			case 0  -> // Question à Choix Multiple à Réponse Unique
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropQCM panelPropQCM = (PanelPropQCM) panelProp;
					sProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					sProp += panelPropQCM.getText() + '\t';
					detailsQuestion += sProp;
				}
				lstErreurs = this.ctrl.creerQCM(detailsQuestion, true);
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropQCM panelPropQCM = (PanelPropQCM) panelProp;
					sProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					sProp += panelPropQCM.getText() + '\t';
					detailsQuestion += sProp;
				}
				lstErreurs = this.ctrl.creerQCM(detailsQuestion, false);
			} 
			case 2  -> // Question à Association d'Eléments
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropAssoc panelPropAssoc = (PanelPropAssoc) panelProp;
					sProp =  panelPropAssoc.getTextGauche() + '\t';
					sProp += panelPropAssoc.getTextDroite() + '\t';
					detailsQuestion += sProp;
				}
				lstErreurs = this.ctrl.creerAssociation(detailsQuestion);
			} 
			case 3  -> // Question avec Elimination de Propositions de Réponses
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropElim panelPropElim = (PanelPropElim) panelProp;
					sProp = panelPropElim.estReponse() ? "V:" : "F:";
					if(!panelPropElim.estReponse()) 
					{
						sProp += panelPropElim.getOrdreElim    () + ":";
						sProp += panelPropElim.getPointsEnMoins() + ":";
					}
					detailsQuestion += sProp;
					sProp = panelPropElim.getText() + '\t';
				}
				lstErreurs = this.ctrl.creerElimination(detailsQuestion);
			} 
			default -> {}
		}
		
		if(lstErreurs.size() == 0)
		{
			JOptionPane.showMessageDialog(
				this,
				"La question a été enregistrée avec succès !",
				"Enregistrement Réussi",
				JOptionPane.INFORMATION_MESSAGE
			);

			this.dispose();
		}
		else
		{
			String message = "La question n'a pas été enregistrée pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this,
				message,
				"Échec de l'Enregistrement",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	public int getIndexTypeQuestion() 
	{
		return this.panelParametresQuestion.getIndexTypeQuestion();
	}

	public void clearPanelProps() 
	{
		this.panelAjoutQuestion.clearPanelProp();
	}
	
}
