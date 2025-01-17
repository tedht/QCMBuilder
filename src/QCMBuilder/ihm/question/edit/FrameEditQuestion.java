package QCMBuilder.ihm.question.edit;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import QCMBuilder.controleur.Controleur;

import QCMBuilder.ihm.IHM;

import QCMBuilder.ihm.question.edit.proposition.PanelProp;
import QCMBuilder.ihm.question.edit.proposition.PanelPropAssoc;
import QCMBuilder.ihm.question.edit.proposition.PanelPropElim;
import QCMBuilder.ihm.question.edit.proposition.PanelPropQCM;
import QCMBuilder.ihm.question.edit.proposition.PanelPropQRM;

/** Classe représentant la fenêtre d'édition (création ou modification) d'une question
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class FrameEditQuestion extends JFrame
{
	private Controleur         ctrl;
	private IHM                ihm;
	private PanelInitQuestion  panelInitQuestion;
	private PanelAjoutQuestion panelAjoutQuestion;
	
	/**
	 * Constructeur de la classe FrameEditQuestion utilisé lorsqu'on veut créer une question.
	 *
	 * @param ctrl le contrôleur.
	 * @param ihm  le gestionnaire des fenêtres de l'application.
	 */
	public FrameEditQuestion(Controleur ctrl, IHM ihm) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		
		this.setTitle("Créer une Question");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		this.panelInitQuestion  = new PanelInitQuestion(ctrl, this);
		this.panelAjoutQuestion = new PanelAjoutQuestion     (this);

		this.pagePrecedente();
	}
	
	/**
	 * Constructeur de la classe FrameEditQuestion utilisé lorsqu'on veut modifier une question.
	 *
	 * @param ctrl  le contrôleur.
	 * @param ihm   le gestionnaire des fenêtres de l'application.
	 * @param idQst l'id de la question qu'on veut modifier
	 */
	public FrameEditQuestion(Controleur ctrl, IHM ihm, Integer idQst) 
	{
		this.ctrl = ctrl;
		this.ihm  = ihm;
		
		this.setTitle("Modifier une Question");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		this.panelInitQuestion = new PanelModifInitQuestion(ctrl, this, idQst);
		this.panelAjoutQuestion      = new PanelModifAjoutQuestion     (ctrl, this, idQst);

		this.pagePrecedente();
	}

	/**
	 * Affiche la première page d'édition de la question (paramètres généraux).
	 */
	public void pagePrecedente()
	{
		this.remove    (this.panelAjoutQuestion); 
		this.setSize   (IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_1);
		this.add       (this.panelInitQuestion);

		this.revalidate();
		this.repaint   ();
	}

	/**
	 * Affiche la seconde page de création du questionnaire (ajout de questions).
	 */
	public void pageSuivante()
	{
		this.remove    (this.panelInitQuestion); 
		this.setSize   (IHM.LARGEUR_EDIT_QUESTION, IHM.HAUTEUR_EDIT_QUESTION_PAGE_2);
		this.add       (this.panelAjoutQuestion);

		this.panelAjoutQuestion.afficher();

		this.revalidate();
		this.repaint   ();
	}

	/**
	 * Méthode pour enregistrer une nouvelle question.
	 * 
	 * @return true si la qustion a été créée avec succès, false sinon.
	 */
	public boolean enregistrer()
	{
		return this.enregistrer(null);
	}

	/**
	 * Méthode pour enregistrer les modifications effectuées sur une question existente.
	 * 
	 * @return true si la qustion a été créée avec succès, false sinon.
	 */
	public boolean enregistrer(Integer idQst) 
	{
		String codeRes    ;

		int    idNot      ;
		int    valDiff    ;
		int    indexType  ;

		String sTemps     ;
		String sPoints    ;
		String intitule   ;
		String explication;
		String pieceJointe;

		List<String> lstErreurs;
		
		double pts;

		int m;
		int s;

		List<PanelProp> lstPanelProp;

		boolean reponse;

		PanelPropQCM   panelPropQCM;
		PanelPropQRM   panelPropQRM;
		PanelPropAssoc panelPropAssoc;
		PanelPropElim  panelPropElim;

		String sReponse  ;
		String sOrdre    ;
		String sPtsPerdus;
		String text      ;


		// Récupération des valeurs saisies par l'utilisateur.
		codeRes     = this.panelInitQuestion.getRessource        ().getCode();
		idNot       = this.panelInitQuestion.getNotion           ().getIdNot();
		valDiff     = this.panelInitQuestion.getDifficulte       (); 
		
		indexType   = this.panelInitQuestion.getIndexTypeQuestion();

		sTemps      = this.panelInitQuestion.getTemps            ();          
		sPoints     = this.panelInitQuestion.getPoints           ();  

		intitule    = this.panelAjoutQuestion     .getIntitule         ();          
		explication = this.panelAjoutQuestion     .getExplication      ();  
		pieceJointe = this.panelAjoutQuestion     .getPieceJointe      (); 
		
		/*--------------------------*/
		/* Vérification des Erreurs */
		/*--------------------------*/

		lstErreurs = new ArrayList<String>();

		// Vérification du champ "Points".
		if(sPoints.isEmpty())
		{
			lstErreurs.add("Le champ 'Points' est vide");
		}
		else
		{
			try 
			{
				pts = Double.parseDouble(sPoints);

				if(pts < 0)
					lstErreurs.add("Valeur invalide pour le champ 'Points'");
			} 
			catch (Exception e) 
			{
				lstErreurs.add("Valeur invalide pour le champ 'Points'");
			}
		}

		// Vérification du champ "Temps".
		if(sTemps.isEmpty())
		{
			lstErreurs.add("Le champ 'Temps' est vide");
		}
		else
		{
			try 
			{
				m = Integer.parseInt(sTemps.substring(0,sTemps.indexOf(':')));
				s = Integer.parseInt(sTemps.substring(sTemps.indexOf(':') + 1));
				
				if(m < 0 || m >= 60 || s < 0 || s >= 60) // durée max -> 59:59
					lstErreurs.add("Valeur invalide pour le champ 'Temps'");
			} 
			catch (Exception e) 
			{
				lstErreurs.add("Valeur invalide pour le champ 'Temps'");
			}
		}		

		// Vérification de l'intitulé.
		if(intitule.isEmpty()) 
			lstErreurs.add("L'intitulé est vide");

		// Vérification de l'explication.
		if(this.panelAjoutQuestion.explicationSelected() && explication.isEmpty())
			lstErreurs.add("L'explication est vide");

		// Vérification de la pièce jointe.
		if(this.panelAjoutQuestion.pieceJointeSelected() && pieceJointe.isEmpty())
			lstErreurs.add("Aucune pièce jointe a été sélectionnée");

		// Vérification des propositions
		lstPanelProp = this.panelAjoutQuestion.getPanelProps();
		if(lstPanelProp.size() == 0)
		{
			lstErreurs.add("Aucune proposition de réponse a été ajouté");
		}
		else
		{
			reponse = false;
			switch (this.panelInitQuestion.getIndexTypeQuestion()) 
			{
				case 0  -> // Question à Choix Multiple à Réponse Unique
				{
					for(PanelProp panelProp : lstPanelProp)
					{
						panelPropQCM = (PanelPropQCM) panelProp;
						
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
						lstErreurs.add("Aucune réponse n'a été sélectionnée.");
				} 
				case 1  -> // Question à Choix Multiple à Réponse Multiple
				{
					for(PanelProp panelProp : lstPanelProp)
					{
						panelPropQRM = (PanelPropQRM) panelProp;
						
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
						panelPropAssoc = (PanelPropAssoc) panelProp;
						if(panelPropAssoc.getTextGauche().isEmpty() || panelPropAssoc.getTextDroite().isEmpty())
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
						panelPropElim = (PanelPropElim) panelProp;
						
						if(panelPropElim.getText().isEmpty())
						{
							lstErreurs.add("Il y a du texte manquant dans au moins une proposition.");
							reponse = true;
							break;
						}
						if(panelPropElim.estReponse()) 
						{
							reponse = true;
						}
						else 
						{
							try 
							{
								if(!"".equals(panelPropElim.getOrdreElim()))
								{
									int ordre = Integer.parseInt(panelPropElim.getOrdreElim());
	
									if(ordre <= 0)
										lstErreurs.add("Valeur invalide pour au moins un des champs 'Ordre'");
								}
							} 
							catch (Exception e) 
							{
								lstErreurs.add("Valeur invalide pour au moins un des champs 'Ordre'");
							}
	
							try 
							{
								if(!"".equals(panelPropElim.getOrdreElim()))
								{
									Double.parseDouble(panelPropElim.getPointsPerdus());
								}
							} 
							catch (Exception e) 
							{
								lstErreurs.add("Valeur invalide pour au moins un des champs 'Pts perdus'");
							}
						}
					}
	
					if(!reponse)
						lstErreurs.add("Aucune réponse a été sélectionnée.");
				} 
				default -> {}
			}
		}

		// Si des erreurs ont été détectées, affichage d'un message d'erreur.
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

		/*------------*/
		/* Traitement */
		/*------------*/
		String       detailsQuestion = "";
		String       detailsProp     = "";
		List<String> lstDetailsProp  = new ArrayList<String>();

		detailsQuestion += codeRes+"\t"+idNot+"\t"+valDiff+"\t"+indexType+"\t"+sTemps+"\t"+sPoints+"\t"+pieceJointe+"\t";

		switch (this.panelInitQuestion.getIndexTypeQuestion()) 
		{
			case 0  -> // Question à Choix Multiple à Réponse Unique
			{
				detailsQuestion += "true\t";
				for(PanelProp panelProp : lstPanelProp)
				{
					panelPropQCM = (PanelPropQCM) panelProp;
					detailsProp =  panelPropQCM.estReponse() ? "V:" : "F:";
					detailsProp += panelPropQCM.getText();
					lstDetailsProp.add(detailsProp);
				}
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				detailsQuestion += "false\t";
				for(PanelProp panelProp : lstPanelProp)
				{
					panelPropQRM = (PanelPropQRM) panelProp;
					detailsProp =  panelPropQRM.estReponse() ? "V:" : "F:";
					detailsProp += panelPropQRM.getText();
					lstDetailsProp.add(detailsProp);
				}
			} 
			case 2  -> // Question à Association d'Eléments
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					panelPropAssoc = (PanelPropAssoc) panelProp;
					lstDetailsProp.add(panelPropAssoc.getTextGauche());
					lstDetailsProp.add(panelPropAssoc.getTextDroite());
				}
			} 
			case 3  -> // Question avec Elimination de Propositions de Réponses
			{
				for(PanelProp panelProp : lstPanelProp)
				{
					panelPropElim = (PanelPropElim) panelProp;
					
					sReponse   = panelPropElim.estReponse      () ? "V:" : "F:";
					sOrdre     = panelPropElim.getOrdreElim    ();
					sPtsPerdus = panelPropElim.getPointsPerdus();
					text       = panelPropElim.getText         ();

					sOrdre     = "".equals(sOrdre)     ? "-1:"  : sOrdre     + ":";
					sPtsPerdus = "".equals(sPtsPerdus) ? "0.0:" : sPtsPerdus + ":";
					sPtsPerdus =   sPtsPerdus.charAt(0) != '-' 
								  ? "-" + sPtsPerdus 
								  : sPtsPerdus;

					detailsProp = sReponse + sOrdre + sPtsPerdus + text;
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

	/**
	 * Récupère l'index du type de question choisi dans la liste déroulante.
	 * @return l'index du type de question choisi dans la liste déroulante.
	 */
	public int getIndexTypeQuestion() 
	{
		return this.panelInitQuestion.getIndexTypeQuestion();
	}

	/**
	 * Supprime toutes les propositions du panle d'ajout de propositions.
	 */
	public void clearPanelProps() 
	{
		if(this.panelAjoutQuestion != null)
			this.panelAjoutQuestion.clearPanelProp();
	}
	
}
