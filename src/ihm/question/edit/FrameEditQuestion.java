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
	private Controleur         ctrl;
	private IHM                ihm;
	private PanelInitQuestion  panelParametresQuestion;
	private PanelAjoutQuestion panelAjoutQuestion;
	
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

		this.panelParametresQuestion = new PanelInitQuestion(ctrl, this);
		this.panelAjoutQuestion      = new PanelAjoutQuestion     (this);

		this.pagePrecedente();
	}
	
	public FrameEditQuestion(Controleur ctrl, IHM ihm, Integer idQst) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		
		this.setTitle("Modifier une Question");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

		this.panelParametresQuestion = new PanelModifInitQuestion(ctrl, this, idQst);
		this.panelAjoutQuestion      = new PanelModifAjoutQuestion     (ctrl, this, idQst);

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

		this.panelAjoutQuestion.afficher();

		this.revalidate();
		this.repaint   ();
	}

	public boolean enregistrer()
	{
		return this.enregistrer(null);
	}

	public boolean enregistrer(Integer idQst) 
	{
		String codeRes     = this.panelParametresQuestion.getRessource        ().getCode();
		int    idNot       = this.panelParametresQuestion.getNotion           ().getIdNot();
		int    valDiff     = this.panelParametresQuestion.getDifficulte       (); 
		      
		int    indexType   = this.panelParametresQuestion.getIndexTypeQuestion();

		String sTemps      = this.panelParametresQuestion.getTemps            ();          
		String sPoints     = this.panelParametresQuestion.getPoints           ();  

		String intitule    = this.panelAjoutQuestion     .getIntitule         ();          
		String explication = this.panelAjoutQuestion     .getExplication      ();  
		String pieceJointe = this.panelAjoutQuestion     .getPieceJointe      (); 
		
		List<String> lstErreurs = new ArrayList<String>();

		if(sPoints.isEmpty())
		{
			lstErreurs.add("Le champ 'Points' est vide");
		}
		else
		{
			try 
			{
				Double.parseDouble(sPoints);
			} 
			catch (Exception e) 
			{
				lstErreurs.add("Valeur invalide pour le champ 'Points'");
			}
		}

		if(sTemps.isEmpty())
		{
			lstErreurs.add("Le champ 'Temps' est vide");
		}
		else
		{
			try 
			{
				int m = Integer.parseInt(sTemps.substring(0,sTemps.indexOf(':')));
				int s = Integer.parseInt(sTemps.substring(sTemps.indexOf(':') + 1));
				
				if(m < 0 || m >= 60 || s < 0 || s >= 60) // durée max -> 59:59
					lstErreurs.add("Valeur invalide pour le champ 'Temps'");
			} 
			catch (Exception e) 
			{
				lstErreurs.add("Valeur invalide pour le champ 'Temps'");
			}
		}		

		if(intitule.isEmpty()) 
			lstErreurs.add("L'intitulé est vide");

		if(this.panelAjoutQuestion.explicationSelected() && explication.isEmpty())
			lstErreurs.add("L'explication est vide");

		if(this.panelAjoutQuestion.PieceJointeSelected() && pieceJointe.isEmpty())
			lstErreurs.add("Aucune pièce jointe a été sélectionnée");

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
			String message = "La question n'a pas " + (idQst == null ? "été enregistrée" : "pu être modifiée") + " pour les raisons suivantes :\n";
			for(String msgErr : lstErreurs)
				message += " • " + msgErr + '\n';
	
			JOptionPane.showMessageDialog(
				this,
				message,
				"Échec de " + (idQst == null ? "l'Enregistrement" : "la Modification"),
				JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
		
		String       detailsQuestion = "";
		String       detailsProp     = "";
		List<String> lstDetailsProp = new ArrayList<String>();

		detailsQuestion += codeRes+"\t"+idNot+"\t"+valDiff+"\t"+indexType+"\t"+sTemps+"\t"+sPoints+"\t"+pieceJointe;

		switch (this.panelParametresQuestion.getIndexTypeQuestion()) 
		{
			case 0  -> // Question à Choix Multiple à Réponse Unique
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropQCM panelPropQCM = (PanelPropQCM) panelProp;
					detailsProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					detailsProp += panelPropQCM.getText() + '\t';
					lstDetailsProp.add(detailsProp);
				}
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropQRM panelPropQCM = (PanelPropQRM) panelProp;
					detailsProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					detailsProp += panelPropQCM.getText();
					lstDetailsProp.add(detailsProp);
				}
			} 
			case 2  -> // Question à Association d'Eléments
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropAssoc panelPropAssoc = (PanelPropAssoc) panelProp;
					lstDetailsProp.add(panelPropAssoc.getTextGauche());
					lstDetailsProp.add(panelPropAssoc.getTextDroite());
				}
			} 
			case 3  -> // Question avec Elimination de Propositions de Réponses
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					PanelPropElim panelPropElim = (PanelPropElim) panelProp;
					detailsProp = panelPropElim.estReponse() ? "V:" : "F:";
					if(!panelPropElim.estReponse()) 
					{
						detailsProp += panelPropElim.getOrdreElim    () + ":";
						detailsProp += panelPropElim.getPointsEnMoins() + ":";
					}
					detailsProp = panelPropElim.getText();
					lstDetailsProp.add(detailsProp);
				}
			} 
			default -> {}
		}

		if(idQst == null)
			this.ctrl.creerQuestion(detailsQuestion, intitule, explication, lstDetailsProp);
		else
			this.ctrl.modifierQuestion(idQst, detailsQuestion, intitule, explication, lstDetailsProp);
		
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
		if(this.panelAjoutQuestion != null)
			this.panelAjoutQuestion.clearPanelProp();
	}
	
}
