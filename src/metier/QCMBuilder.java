package metier;


import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

import metier.banque.BanqueNotions;
import metier.banque.BanqueQuestions;
import metier.banque.BanqueRessources;

import metier.entite.Ressource;
import metier.entite.Notion;
import metier.entite.Questionnaire;

import metier.entite.question.Difficulte;
import metier.entite.question.PieceJointe;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

import metier.entite.question.association.*;
import metier.entite.question.qcm.*;
import metier.entite.question.elimination.*;


/**
 * Classe QCMBuilder, classe principale métier de l'application QCMBuilder.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCMBuilder 
{
	/*-----------*/
	/* Attributs */
	/*-----------*/

	private BanqueQuestions  banqueQuestions;
	private BanqueNotions    banqueNotions;
	private BanqueRessources banqueRessources;

	private Ressource        ressourceSelectionnee;

	private Questionnaire    questionnaire;


	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe QCMBuilder.
	 */
	public QCMBuilder()
	{
		this.banqueRessources = new BanqueRessources(); 
		this.banqueNotions    = new BanqueNotions   ();
		this.banqueQuestions  = new BanqueQuestions ();

		this.ressourceSelectionnee = null;

		this.questionnaire = null;
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne la liste des ressources.
	 * 
	 * @return la liste des ressources.
	 */
	public List<Ressource> getRessources() { return this.banqueRessources.getRessources(); }

	/**
	 * Retourne une ressource à partir de son code.
	 * 
	 * @param  code le code de la ressource.
	 * @return      la ressource.
	 */
	public Ressource getRessource(String code) { return this.banqueRessources.getRessource(code); }

	/**
	 * Retourne la liste de toutes les notions.
	 * 
	 * @return la liste de toutes les notions.
	 */
	public List<Notion> getNotions() { return this.banqueNotions.getNotions(); }

	/**
	 * Retourne la liste des notions associées à une ressource à partir de son code.
	 * 
	 * @param  codeRes le code de la ressource.
	 * @return         la liste des notions associées.
	 */
	public List<Notion> getNotions(String codeRes) { return this.banqueNotions.getNotions(codeRes); }

	/**
	 * Retourne une notion à partir de son id.
	 * 
	 * @param idNot l'id de la notion.
	 * @return      la notion.
	 */
	public Notion getNotion(int idNot)  { return this.banqueNotions.getNotion(idNot); }

	/**
	 * Retourne une notion à partir de son nom et son code de ressource.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param nomNot  le nom de la notion.
	 * @return        la notion.
	 */
	public Notion getNotionParNom(String codeRes, String nomNot)  { return this.banqueNotions.getNotionParNom(codeRes, nomNot); }

	/**
	 * Retourne la liste de toutes questions.
	 * 
	 * @return la liste de toutes les questions.
	 */
	public List<Question> getQuestions()  { return this.banqueQuestions.getQuestions(); }

	/**
	 * Retourne la liste des questions associées à une ressource à partir de son code.
	 * 
	 * @param  codeRes le code de la ressource.
	 * @return         la liste des questions associées.
	 */
	public List<Question> getQuestions(String codeRes)  { return this.banqueQuestions.getQuestions(codeRes); }

	/**
	 * Retourne la liste des questions associées à une ressource à partir de son code et de l'id de la notion.
	 * 
	 * @param  codeRes le code de la ressource.
	 * @param  idNot   l'id de la notion.
	 * @return         la liste des questions associées.
	 */
	public List<Question> getQuestions(String codeRes, int idNot) 
	{ 
		return this.banqueQuestions.getQuestions(codeRes, idNot); 
	}
	
	public List<Question> getQuestions(String codeRes, int idNot, Difficulte diff)
	{
		return this.banqueQuestions.getQuestions(codeRes, idNot, diff);
	}

	/**
	 * Retourne une question à partir de son id.
	 * 
	 * @param idQst l'id de la question.
	 * @return      la question.
	 */
	public Question getQuestion(int idQst) { return this.banqueQuestions.getQuestion(idQst); }

	/**
	 * Retourne la ressource active.
	 * 
	 * @return la ressource active.
	 */
	public Ressource getRessourceSelectionnee() { return this.ressourceSelectionnee; }


	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource active.
	 * 
	 * @param ressource la nouvelle ressource active.
	 */
	public void setRessourceSelectionnee(Ressource ressource) { this.ressourceSelectionnee = ressource; }


	/*-----------------*/
	/* Autres Méthodes */
	/*-----------------*/

	/**
	 * Crée une ressource
	 * 
	 * @param code le code de la ressource.
	 * @param nom  le nom de la ressource.
	 */
	public void creerRessource(String code, String nom)
	{
		this.banqueRessources.creerRessource(code, nom);
		this.banqueRessources.sauvegarder();
	}

	/**
	 * Crée une notion
	 * 
	 * @param codeRes   le code de la ressource.
	 * @param nomNotion le nom de la notion.
	 */
	public void creerNotion(String codeRes, String nomNotion) 
	{
		this.banqueNotions.creerNotion(codeRes, nomNotion);
		this.banqueNotions.sauvegarder();
	}

	/**
	 * Crée une question.
	 * 
	 * @param detailsQuestion les détails de la question.
	 * @param explication     l'explication.
	 * @param intitule        l'intitulé.
	 * @param lstDetailsProp  la liste des détails des propositions.
	 */
	public void creerQuestion(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.editQuestion(null, detailsQuestion, intitule, explication, lstDetailsProp);
	}

	/**
	 * Edite une question.
	 * 
	 * @param idQst           l'id de la question.
	 * @param detailsQuestion les détails de la question.
	 * @param explication     l'explication.
	 * @param intitule        l'intitulé.
	 * @param lstDetailsProp  la liste des détails des propositions.
	 */
	private void editQuestion(Integer idQst, String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp)
	{
		Scanner scDetails, scElim;

		String     codeRes     ;
		int        idNot       ;
		int        valDiff     ;
		int        indexType   ;
		String     sTemps      ;
		double     note        ;
		String     cheminPJOrig;

		Difficulte   difficulte;
		TypeQuestion type      ;
		int          temps     ;


		Question question;

		boolean  estReponse;
		int      cptReponse;

		String   textGauche, textDroite;

		int      ordreElim;
		double   nbPtsPerdus;
		String   text;


		// Scanner appliqué aux détails de la question utilisant le délimiteur "\t"
		scDetails = new Scanner(detailsQuestion);
		scDetails.useDelimiter("\t");

		// Récupération des données
		codeRes      = scDetails.next();
		idNot        = scDetails.nextInt();
		valDiff      = scDetails.nextInt();
		indexType    = scDetails.nextInt();
		sTemps       = scDetails.next();
		note         = Double.parseDouble(scDetails.next());
		cheminPJOrig = scDetails.next();

		scDetails.close();


		difficulte = Difficulte.fromInt(valDiff);
		type       = TypeQuestion.fromInt(indexType == 0 ? 0 : indexType-1);
		temps      = this.enSeconde(sTemps);

		question   = null;
		
		if(idQst != null)
		{
			if(this.banqueQuestions.getQuestion(idQst).getType() != type)
			{
				this.banqueQuestions.supprimerQuestion(idQst);
				idQst = null;
			}
			else
			{
				question = this.banqueQuestions.getQuestion(idQst);
				question.setCodeRes   (codeRes);
				question.setIdNot     (idNot);
				question.setDifficulte(difficulte);
				question.setTemps     (temps);
				question.setNote      (note);
			}
		}
	
		switch (type) 
		{
			case QCM ->
			{
				// Création de la question QCM
				if(idQst == null)
					question = this.banqueQuestions.creerQCM(codeRes, idNot, difficulte, temps, note);

				// Ajout des propositions
				question.clearPropositions();
				cptReponse = 0;
				for(String detailsProp : lstDetailsProp)
				{
					estReponse = detailsProp.charAt(0) == 'V';
					if(estReponse) cptReponse++;
					((QCM)question).ajouterProposition(new PropositionQCM(detailsProp.substring(2), estReponse));
				}

				// Définition de l'unicité
				((QCM)question).setUnique(cptReponse == 1);

				question.setIntitule   (intitule);
				question.setExplication(explication);
			}
			case ASSOCIATION ->
			{
				// Création de la question Association
				if(idQst == null)
					question = this.banqueQuestions.creerAssociation(codeRes, idNot, difficulte, temps, note);

				// Ajout des propositions
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
				// Création de la question Elimination
				if(idQst == null)
					question = this.banqueQuestions.creerElimination(codeRes, idNot, difficulte, temps, note);

				// Ajout des propositions
				question.clearPropositions();
				for(String detailsProp : lstDetailsProp)
				{
					// Scanner appliqué aux détails de la proposition utilisant le délimiteur ":"
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

		// Ajout de la pièce jointe
		if("".equals(cheminPJOrig))
		{
			question.setPieceJointe(null);
		}
		else
		{
			PieceJointe pj = question.getPieceJointe();
			
			String cheminPJ = "data/ressources/" 
			                  + question.getCodeRes()
			                  + "/notion" + question.getIdNot()
			                  + "/question" + question.getIdQst()
			                  + "/complément/";

			String extension = cheminPJOrig.substring(cheminPJOrig.lastIndexOf('.')+1);
			
			if(pj == null)
				cheminPJ += PieceJointe.nouveauNomFic(extension);
			else
				cheminPJ += pj.getNomPieceJointe() + "." + extension;

			question.setPieceJointe(new PieceJointe(cheminPJOrig, cheminPJ));
		}


		this.banqueQuestions.sauvegarder();
	}

	/**
	 * Convertit un temps en seconde.
	 * 
	 * @param sTemps le temps.
	 * @return       le temps en seconde.
	 */
	private int enSeconde(String sTemps)
	{
		int m, s;

		m = Integer.parseInt(sTemps.substring(0, sTemps.indexOf(':')));
		s = Integer.parseInt(sTemps.substring(sTemps.indexOf(':') + 1));

		return m * 60 + s;
	}

	/**
	 * Modifie une ressource par le code de la ressource.
	 * 
	 * @param code        le code de la ressource.
	 * @param nouveauCode le nouveau code de la ressource.
	 * @param nouveauNom  le nouveau nom de la ressource.
	 */
	public void modifierRessource(String code, String nouveauCode, String nouveauNom) 
	{
		Ressource ressource;


		ressource = this.banqueRessources.getRessource(code);
		ressource.setCode(nouveauCode);
		ressource.setNom (nouveauNom);

		this.banqueRessources.sauvegarder();
	}

	/**
	 * Modifie une notion par son id.
	 * 
	 * @param idNot      l'id de la notion.
	 * @param nouveauNom le nouveau nom de la notion.
	 */
	public void modifierNotion(int idNot, String nouveauNom) 
	{
		Notion notion;


		notion = this.banqueNotions.getNotion(idNot);
		notion.setNom(nouveauNom);

		this.banqueNotions.sauvegarder();
	}
	
	/**
	 * Modifie une question par son id.
	 * 
	 * @param idQst           l'id de la question.
	 * @param detailsQuestion les détails de la question.
	 * @param intitule        l'intitulé.
	 * @param explication     l'explication.
	 * @param lstDetailsProp  la liste des détails des propositions.
	 */
	public void modifierQuestion(int idQst, String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.editQuestion(idQst, detailsQuestion, intitule, explication, lstDetailsProp);
	}

	/**
	 * Supprime une ressource à partir de son code.
	 * 
	 * @param code le code de la ressource.
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
	 * 
	 * @param id l'id de la notion.
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
	 * 
	 * @param id l'id de la question.
	 */
	public void supprimerQuestion(int id) 
	{
		// Supprime la question
		this.banqueQuestions.supprimerQuestion(id);
	}

	/**
	 * Crée une pièce jointe et l'associe à la question.
	 * 
	 * @param cheminFichier le chemin du fichier.
	 * @param question      la question.
	 */
	/*public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.banqueQuestions.creerPieceJointe(cheminFichier, question);
	}*/

	/**
	 * Génère un questionnaire.
	 * 
	 * @param codeRes        le code de la ressource.
	 * @param chronometre    true si le questionnaire est chronométré, false sinon.
	 * @param tabNbQuestions le tableau du nombre de questions par difficulté et notions.
	 */
	public void creerQuestionnaire(String codeRes, boolean chronometre, int[][] tabNbQuestions)
	{
		List<Question> lstQuestions;
		Question       question;
		int            nbQuestions;

		this.questionnaire = new Questionnaire(this, this.banqueRessources.getRessource(codeRes), chronometre);

		// Ajout des questions au questionnaire
		for(int i = 0; i < tabNbQuestions.length; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				lstQuestions = new ArrayList<Question>(this.banqueQuestions.getQuestions(codeRes, i, Difficulte.fromInt(j)));
				nbQuestions = tabNbQuestions[i][j] > lstQuestions.size() ? lstQuestions.size() : tabNbQuestions[i][j];
				for(int k = 0; k < nbQuestions; k++)
				{
					question = this.banqueQuestions.getQuestions(codeRes, i).get((int)(Math.random() * lstQuestions.size()));
					this.questionnaire.ajouterQuestion(question);
				}
			}
		}

		// Mélange les questions
		this.questionnaire.shuffleQuestions();
	}

	/**
	 * Exporte le questionnaire.
	 * 
	 * @param dossierSauvegarde le dossier de sauvegarde.
	 */
	public void exporterQuestionnaire(String dossierSauvegarde)
	{
		if(this.questionnaire != null)
			this.questionnaire.genererQuestionnaire(dossierSauvegarde);
	}
}