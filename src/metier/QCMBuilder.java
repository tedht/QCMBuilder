package metier;

import java.util.List;
import java.util.Scanner;

import metier.banque.BanqueNotions;
import metier.banque.BanqueQuestions;
import metier.banque.BanqueRessources;

import metier.entite.Ressource;
import metier.entite.Notion;
import metier.entite.Questionnaire;

import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;
import metier.entite.question.association.Association;
import metier.entite.question.qcm.QCM;
import metier.entite.question.elimination.Elimination;

import metier.entite.question.qcm.PropositionQCM;
import metier.entite.question.association.PropositionAssociation;
import metier.entite.question.elimination.PropositionElimination;

/** Classe QCMBuilder
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCMBuilder 
{
	private BanqueQuestions  banqueQuestions;
	private BanqueNotions    banqueNotions;
	private BanqueRessources banqueRessources;

	private Ressource        ressourceSelectionnee;

	private Questionnaire    questionnaire;

	public QCMBuilder()
	{
		this.banqueRessources = new BanqueRessources(); 
		this.banqueNotions    = new BanqueNotions   ();
		this.banqueQuestions  = new BanqueQuestions ();

		this.ressourceSelectionnee  = null;

		this.questionnaire    = null;
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne la liste des ressources.
	 * 
	 * @return la liste des ressources.
	 */
	public List<Ressource> getRessources()
	{
		return this.banqueRessources.getRessources();
	}

	/**
	 * Retourne une ressource à partir de son code.
	 * 
	 * @param  code le code de la ressource.
	 * @return      la ressource
	 */
	public Ressource getRessource(String code) 
	{
		return this.banqueRessources.getRessource(code);
	}

	/**
	 * Retourne la liste de toutes les notions.
	 * 
	 * @return la liste de toutes les notions.
	 */
	public List<Notion> getNotions()
	{
		return this.banqueNotions.getNotions();
	}

	/**
	 * Retourne la liste des notions associées à une ressource à partir de son code.
	 * 
	 * @param  codeRes le code de la ressource.
	 * @return la liste des notions associées.
	 */
	public List<Notion> getNotions(String codeRes)
	{
		return this.banqueNotions.getNotions(codeRes);
	}

	/**
	 * Retourne une notion à partir de son id.
	 * 
	 * @param idNot l'id de la notion
	 * @return la notion
	 */
	public Notion getNotion(int idNot) 
	{
		return this.banqueNotions.getNotion(idNot);
	}

	public Notion getNotionParNom(String codeRes, String nomNot) 
	{
		return this.banqueNotions.getNotionParNom(codeRes, nomNot);
	}

	/**
	 * Retourne la liste de toutes questions.
	 * 
	 * @return la liste de toutes les questions.
	 */
	public List<Question> getQuestions() 
	{
		return this.banqueQuestions.getQuestions();
	}
	
	public List<Question> getQuestions(String codeRes) 
	{
		return this.banqueQuestions.getQuestions(codeRes);
	}

	public List<Question> getQuestions(String codeRes, int idNot)
	{
		return this.banqueQuestions.getQuestions(codeRes, idNot);
	}

	public Question getQuestion(int idQst)
	{
		return this.banqueQuestions.getQuestion(idQst);
	}

	/**
	 * Retourne la ressource active
	 * 
	 * @return la ressource active
	 */
	public Ressource getRessourceSelectionnee() 
	{
		return this.ressourceSelectionnee;
	}

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource active
	 * 
	 * @param ressource la ressource active
	 */
	public void setRessourceSelectionnee(Ressource ressource) 
	{
		this.ressourceSelectionnee = ressource;
	}

	/*-----------------*/
	/* Autres Méthodes */
	/*-----------------*/

	/**
	 * Crée une ressource
	 * 
	 * @param code le code de la ressource
	 * @param nom le nom de la ressource
	 */
	public void creerRessource(String code, String nom)
	{
		this.banqueRessources.creerRessource(code, nom);
		this.banqueRessources.sauvegarder();
	}

	/**
	 * Crée une notion
	 * 
	 * @param nomNotion le nom de la notion
	 * @param nomNotion2 
	 */
	public void creerNotion(String codeRes, String nomNotion) 
	{
		this.banqueNotions.creerNotion(codeRes, nomNotion);
		this.banqueNotions.sauvegarder();
	}

	/**
	 * Crée une question
	 * 
	 * @param detailsQuestion les détails de la question
	 * @param explication 
	 * @param intitule 
	 * @param lstDetailsProp 
	 */
	public void creerQuestion(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.editQuestion(null, detailsQuestion, intitule, explication, lstDetailsProp);
	}

	private void editQuestion(Integer idQst, String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp)
	{
		Scanner scDetails, scElim;
		
		scDetails = new Scanner(detailsQuestion);
		scDetails.useDelimiter("\t");

		String     codeRes      = scDetails.next();
		int        idNot        = scDetails.nextInt();
		int        valDiff      = scDetails.nextInt();
		int        indexType    = scDetails.nextInt();
		String     sTemps       = scDetails.next();
		double     note         = Double.parseDouble(scDetails.next());

		scDetails.close();

		Difficulte   difficulte = Difficulte.fromInt(valDiff);
		TypeQuestion type       = TypeQuestion.fromInt(indexType == 0 ? 0 : indexType-1);
		int          temps      = this.enSeconde(sTemps);

		Question question;

		boolean  estReponse;
		int      cptReponse;

		String   textGauche, textDroite;

		int      ordreElim;
		double   nbPtsPerdus;
		String   text;

		question = null;
		if(idQst != null)
		{
			question = this.banqueQuestions.getQuestion(idQst);
			question.setCodeRes(codeRes);
			question.setIdNot(idNot);
			question.setDifficulte(difficulte);
			question.setTemps(temps);
			question.setNote(note);
		}
	
		switch (type) 
		{
			case QCM ->
			{
				if(idQst == null)
					question = this.banqueQuestions.creerQCM(codeRes, idNot, difficulte, temps, note);

				question.clearPropositions();
				cptReponse = 0;
				for(String detailsProp : lstDetailsProp)
				{
					estReponse = detailsProp.charAt(0) == 'V';
					if(estReponse) cptReponse++;
					((QCM)question).ajouterProposition(new PropositionQCM(detailsProp.substring(2), estReponse));
				}

				((QCM)question).setUnique(cptReponse == 1);

				question.setIntitule   (intitule);
				question.setExplication(explication);
			}
			case ASSOCIATION ->
			{
				if(idQst == null)
					question = this.banqueQuestions.creerAssociation(codeRes, idNot, difficulte, temps, note);

				question.clearPropositions();
				for(int i = 0; i < lstDetailsProp.size(); i+=2)
				{
					textGauche = lstDetailsProp.get(i);
					textDroite = lstDetailsProp.get(i+1);
					
					((Association)question).ajouterProposition(new PropositionAssociation(textGauche, textDroite));
				}

				question.setIntitule   (intitule);
				question.setExplication(explication);
			}
			case ELIMINATION ->
			{
				if(idQst == null)
					question = this.banqueQuestions.creerElimination(codeRes, idNot, difficulte, temps, note);

				question.clearPropositions();
				for(String detailsProp : lstDetailsProp)
				{
					scElim = new Scanner(detailsProp);
					scElim.useDelimiter(":");

					estReponse  = scElim.next().charAt(0) == 'V';
					ordreElim   = scElim.nextInt();
					nbPtsPerdus = Double.parseDouble(scElim.next());

					text = scElim.next ();
					
					((Elimination) question)
							.ajouterProposition(new PropositionElimination(text, estReponse, ordreElim, nbPtsPerdus));

					scElim.close();
				}
				
				question.setIntitule   (intitule);
				question.setExplication(explication);
			}
			default -> {}
		}
		
		this.banqueQuestions.sauvegarder();
	}

	/**
	 * Convertit un temps en seconde
	 * 
	 * @param sTemps le temps
	 * @return le temps en seconde
	 */
	private int enSeconde(String sTemps)
	{
		int m, s;

		m = Integer.parseInt(sTemps.substring(0, sTemps.indexOf(':')));
		s = Integer.parseInt(sTemps.substring(sTemps.indexOf(':') + 1));

		return m * 60 + s;
	}

	public void modifierRessource(String code, String nouveauCode, String nouveauNom) 
	{
		Ressource ressource = this.banqueRessources.getRessource(code);
		ressource.setCode(nouveauCode);
		ressource.setNom (nouveauNom);

		this.banqueRessources.sauvegarder();
	}

	public void modifierNotion(int idNot, String nouveauNom) 
	{
		Notion notion = this.banqueNotions.getNotion(idNot);
		notion.setNom(nouveauNom);

		this.banqueNotions.sauvegarder();
	}
	
	public void modifierQuestion(int idQst, String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.editQuestion(idQst, detailsQuestion, intitule, explication, lstDetailsProp);
	}

	/**
	 * Supprime une ressource à partir de son code.
	 * @param code le code de la ressource
	 * 
	 */
	public void supprimerRessource(String code) 
	{
		// Supprime les questions associées à la ressource
		this.banqueQuestions.supprimerQuestions(code);
		this.banqueQuestions.sauvegarder();
		
		// Supprime les notions associées à la ressource
		this.banqueNotions.supprimerNotions(code);
		this.banqueNotions.sauvegarder();
		
		// Supprime la ressource
		if(this.banqueRessources.getRessource(code) == this.ressourceSelectionnee) 
			this.ressourceSelectionnee = null;
		this.banqueRessources.supprimerRessource(code);
		this.banqueRessources.sauvegarder();
	}
	
	/**
	 * Supprime une notion à partir de son id.
	 * @param id l'id de la notion
	 */
	public void supprimerNotion(int id) 
	{
		// Supprime les questions associées à la notion
		this.banqueQuestions.supprimerQuestions(id);
		this.banqueQuestions.sauvegarder();

		// Supprime la notion
		this.banqueNotions.supprimerNotion(id);
		this.banqueNotions.sauvegarder();
	}

	/**
	 * Supprime une question à partir de son id.
	 * @param id l'id de la question
	 */
	public void supprimerQuestion(int id) 
	{
		// Supprime la question
		this.banqueQuestions.supprimerQuestion(id);
	}

	/**
	 * Crée une pièce jointe
	 * 
	 * @param cheminFichier
	 * @param question
	 */
	public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.banqueQuestions.creerPieceJointe(cheminFichier, question);
	}


	/**
	 * Génère un questionnaire
	 * 
	 * @param cheminFichier le chemin du fichier
	 */
	public void genererQuestionnaire(String cheminFichier)
	{
		this.questionnaire.genererQuestionnaire(cheminFichier);
	}
}
