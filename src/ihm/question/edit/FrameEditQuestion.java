package ihm.question.edit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.IHM;
import ihm.question.edit.proposition.PanelProp;
import ihm.question.edit.proposition.PanelPropAssoc;
import ihm.question.edit.proposition.PanelPropElim;
import ihm.question.edit.proposition.PanelPropQCM;
import ihm.question.edit.proposition.PanelPropQRM;


/** Classe représentant la fenêtre d'édition (création ou modification) d'une question
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
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
		this.ctrl = ctrl;
		this.ihm = ihm;
		
		this.setTitle("Créer une Question");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelParametresQuestion = new PanelParametresQuestion(ctrl, this);
		this.panelAjoutQuestion      = new PanelAjoutQuestion     (this);

		this.pagePrecedente();
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

	public boolean enregistrer() 
	{
		String codeRessource = this.panelParametresQuestion.getRessource  ().getCode();
		int    idNotion      = this.panelParametresQuestion.getNotion     ().getId  ();
		int    indDiff       = this.panelParametresQuestion.getDifficulte ();         
		String sTemps        = this.panelParametresQuestion.getTemps      ();          
		String sPoints       = this.panelParametresQuestion.getPoints     ();          
		String intitule      = this.panelAjoutQuestion     .getIntitule   ();          
		String explication   = this.panelAjoutQuestion     .getExplication();          

		List<String> lstErreurs = new ArrayList<String>();

		if(intitule.isEmpty()) 
			lstErreurs.add("L'intitulé est vide");

		if(this.panelAjoutQuestion.explicationSelected() && explication.isEmpty())
			lstErreurs.add("L'explication est vide");

		List<PanelProp> lstPanelProp = this.panelAjoutQuestion.getPanelProps();

		if(lstPanelProp.size() == 0)
		{
			lstErreurs.add("Aucune proposition de réponse a été ajouté");
		}
		else
		{
			boolean reponse = false;
			switch (this.panelParametresQuestion.getIndexTypeQuestion()) 
			{
				case 0  -> // Question à Choix Multiple à Réponse Unique
				{
					for(PanelProp panelProp : lstPanelProp)
					{
						PanelPropQCM panelPropQCM = (PanelPropQCM) panelProp;
						
						if(panelPropQCM.estReponse()) 
							reponse = true;
	
						if(panelPropQCM.getText().isEmpty())
						{
							lstErreurs.add("Il y a du texte manquant dans au moins une proposition de réponse.");
							reponse = true;
							break;
						}
					}
					
					if(!reponse)
						lstErreurs.add("Aucune réponse a été sélectionnée.");
				} 
				case 1  -> // Question à Choix Multiple à Réponse Multiple
				{
					for(PanelProp panelProp : lstPanelProp)
					{
						PanelPropQRM panelPropQRM = (PanelPropQRM) panelProp;
						
						if(panelPropQRM.estReponse()) 
							reponse = true;
	
					if(panelPropQRM.getText().isEmpty())
					{
						lstErreurs.add("Il y a du texte manquant dans au moins une proposition de réponse.");
						reponse = true;
						break;
					}
				}
				
				if(!reponse)
					lstErreurs.add("Aucune réponse a été sélectionnée.");
				} 
				case 2  -> // Question à Association d'Eléments
				{
					for(PanelProp panelProp : lstPanelProp)
					{
						PanelPropAssoc panelPropAssoc = (PanelPropAssoc) panelProp;
						if(panelPropAssoc.getTextGauche().isEmpty() || panelPropAssoc.getTextDroite().isEmpty());
						{
							lstErreurs.add("Il y a du texte manquant dans au moins une proposition.");
							break;
						}
					}
				} 
				case 3  -> // Question avec Elimination de Propositions de Réponses
				{
					for(PanelProp panelProp : lstPanelProp)
					{
						PanelPropElim panelPropElim = (PanelPropElim) panelProp;
						if(panelPropElim.estReponse()) 
						{
							reponse = true;
						}
						else
						{
							if(panelPropElim.getOrdreElim().isEmpty() || panelPropElim.getPointsEnMoins().isEmpty())
							{
								lstErreurs.add("Il y a du texte manquant dans au moins une proposition.");
								break;
							}
						}
	
						if(panelPropElim.getText().isEmpty())
						{
							lstErreurs.add("Il y a du texte manquant dans au moins une proposition.");
							reponse = true;
							break;
						}
					}
	
					if(!reponse)
						lstErreurs.add("Aucune réponse a été sélectionnée.");
				} 
				default -> {}
			}
		}

		if(lstErreurs.size() != 0)
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
			return false;
		}
		
		String detailsQuestion = "";
		String sProp           = "";

		detailsQuestion += codeRessource + '\t';
		detailsQuestion += idNotion      + '\t';
		detailsQuestion += indDiff       + "\t";
		detailsQuestion += sTemps        + '\t';
		detailsQuestion += sPoints       + '\t';
		detailsQuestion += intitule      + '\t';
		detailsQuestion += explication   + '\t';

		System.out.println(detailsQuestion);

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
				this.ctrl.creerQCM(detailsQuestion, true);
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropQRM panelPropQCM = (PanelPropQRM) panelProp;
					sProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					sProp += panelPropQCM.getText() + '\t';
					detailsQuestion += sProp;

					System.out.println(detailsQuestion);
				}
				this.ctrl.creerQCM(detailsQuestion, false);
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
				this.ctrl.creerAssociation(detailsQuestion);
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
				this.ctrl.creerElimination(detailsQuestion);
			} 
			default -> {}
		}
		
		this.dispose();
		this.ihm.reinitAffichageQuestion();
		return true;
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
