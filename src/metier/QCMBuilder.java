package metier;

import java.io.File;
import java.util.ArrayList;
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

	private Ressource        ressourceActive;
	private Notion           notionActive;

	private Questionnaire    questionnaire;

	public QCMBuilder()
	{
		this.banqueRessources = new BanqueRessources(); 
		this.banqueNotions    = new BanqueNotions   ();
		this.banqueQuestions  = new BanqueQuestions ();

		this.ressourceActive  = null;
		this.notionActive     = null;

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
	public Ressource getRessourceActive() 
	{
		return this.ressourceActive;
	}

	/**
	 * Retourne la notion active
	 * 
	 * @return la notion active
	 */
	public Notion getNotionActive() 
	{
		return this.notionActive;
	}

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource active
	 * 
	 * @param ressource la ressource active
	 */
	public void setRessourceActive(Ressource ressource) 
	{
		this.ressourceActive = ressource;
	}

	/**
	 * Modifie la notion active
	 *
	 * @param notion la notion
	 */
	public void setNotionActive(Notion notion) 
	{
		this.notionActive = notion;
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
	 * Crée une question de type QCM
	 * 
	 * @param cheminFichier
	 * @param question
	 */
	public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.banqueQuestions.creerPieceJointe(cheminFichier, question);
	}

	/**
	 * Crée une question de type QCM
	 * 
	 * @param detailsQuestion les détails de la question
	 * @param explication 
	 * @param intitule 
	 * @param lstDetailsProp 
	 */
	public void creerQuestion(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp, boolean unique) 
	{
		Scanner scDetails, scDetailsProp;
		
		scDetails = new Scanner(detailsQuestion);
		scDetails.useDelimiter("\t");

		String     codeRes    = scDetails.next();
		int        idNot      = scDetails.nextInt();
		int        valDiff    = scDetails.nextInt();
		String     sTemps     = scDetails.next();
		int        note       = scDetails.nextInt();

		Difficulte difficulte = Difficulte.fromInt(valDiff);
		int        temps      = Integer.parseInt(sTemps.substring(0, 2)) * 60 
		                        + Integer.parseInt(sTemps.substring(4, 6));

		Question question = this.banqueQuestions.creerQuestion(codeRes, idNot, note, temps, difficulte)


		
		qcm = new QCM(details.ressource(), details.notion(), details.difficulte(),details.temps(), details.note(), unique);
					  
		for (int cpt = 0 ; cpt < details.propQCM().size() ; cpt++)
		{
			qcm.ajouterProposition(details.propQCM().get(cpt));
		}

		this.banqueQuestions.ajouterQuestions(qcm);
	}

	public void creerAssociation(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		Association asso;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.ASSOCIATION);

		/*
		asso = new Association(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());

		for (int cpt = 0 ; cpt < details.propAssos().size() ; cpt++)
		{
			asso.ajouterProposition(details.propAssos().get(cpt));
		}
		
		this.banqueQuestions.ajouterQuestions(asso);*/

	}

	/**
	 * Crée une question d'élimination
	 * 
	 * @param detailsQuestion les détails de la question
	 * @param lstDetailsProp 
	 * @param explication 
	 * @param intitule 
	 */
	public void creerElimination(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		Elimination elim;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.ELIMINATION);

		/*
		elim = new Elimination(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());

		for (int cpt = 0 ; cpt < details.propElim().size() ; cpt++)
		{
			elim.ajouterProposition(details.propElim().get(cpt));
		}

		this.banqueQuestions.ajouterQuestions(elim);

		for (int cpt = 0 ; cpt < details.propElim().size() ; cpt++)
		{
			elim.ajouterProposition(details.propElim().get(cpt));
		}*/
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

	/**
	 * Crée l'arborescence des ressources
	 */
	public void creerArborescence()
	{
		File dossier;


		for (Ressource ressource : this.banqueRessources.getRessources())
		{
			dossier = new File("ressources/" + ressource.getCode() + " " + ressource.getNom());


			// Si pas de notion, ignore simplement la boucle
			/*
			for (Notion notion : ressource.getNotions())
			{
				dossier = new File("ressources/" + ressource.getCode() + " " + ressource.getNom() + "/" + notion.getNom());

				// Si pas de question, ignore simplement la boucle
				for (int cpt = 1 ; cpt < this.banqueQuestions.getQuestions().size() ; cpt++)
				{
					if (this.banqueQuestions.getQuestions().get(cpt).getRessource().equals(ressource))
					{
						dossier = new File("ressources/" + ressource.getCode() + " " + ressource.getNom() + "/" + notion.getNom() + "/question " + cpt + "/complément");
					}

					// Vérifier si le dossier existe déjà, sinon le créer -- Question
					if (!dossier.exists())
					{
						if (dossier.mkdirs())
						{
							System.out.println("Le dossier " + dossier.getPath() + " a été créé avec succès.");
						}
						else
						{
							System.out.println("La création du dossier " + dossier.getPath() + " a échoué.");
						}
					}
					else
					{
						System.out.println("Le dossier " + dossier.getPath() + " existe déjà.");
					}
				}

				// Vérifier si le dossier existe déjà, sinon le créer --  Notion
				if (!dossier.exists())
				{
					if (dossier.mkdirs())
					{
						System.out.println("Le dossier " + dossier.getPath() + " a été créé avec succès.");
					}
					else
					{
						System.out.println("La création du dossier " + dossier.getPath() + " a échoué.");
					}
				}
				else
				{
					System.out.println("Le dossier " + dossier.getPath() + " existe déjà.");
				}
			}

			// Vérifier si le dossier existe déjà, sinon le créer -- Ressource
			if (!dossier.exists())
			{
				if (dossier.mkdirs())
				{
					System.out.println("Le dossier " + dossier.getPath() + " a été créé avec succès.");
				}
				else
				{
					System.out.println("La création du dossier " + dossier.getPath() + " a échoué.");
				}
			}
			else
			{
				System.out.println("Le dossier " + dossier.getPath() + " existe déjà.");
			}*/
		}
	}

	/**
	 * Lit les détails d'une question
	 * 
	 * @param detailsQuestion les détails de la question
	 * @param type le type de question
	 * @return les détails de la question
	 */
	private DetailsQuestion lireDetailsQuestion(String detailsQuestion, TypeQuestion type)
	{
		Scanner sc;

		Ressource     ressource;
		Notion        notion;
		Difficulte    difficulte;
		int           temps;
		double        note;
		String        intitule;
		String        explication;

		List<PropositionQCM>         lstPropositionsQCM;
		List<PropositionAssociation> lstPropositionsAssociation;
		List<PropositionElimination> lstPropositionsElimination;

		String prop;

		int ordreElim, nbPtPerdu;


		lstPropositionsAssociation = null;
		lstPropositionsElimination = null;
		lstPropositionsQCM         = null;

		try
		{
			sc = new Scanner(detailsQuestion);
		
			sc.useDelimiter("\t");

			ressource   = this.banqueRessources.getRessource(sc.next());
			//notion      = this.banqueRessources.getNotion(ressource.getNom(), sc.next());
			difficulte  = Difficulte.fromInt(sc.nextInt());
			temps       = this.enSeconde(sc.next());
			note        = sc.nextDouble();
			intitule    = sc.next();
			explication = sc.next();

			System.out.println(ressource);

			if (type == TypeQuestion.QCM)
			{
				lstPropositionsQCM = new ArrayList<PropositionQCM>();
				
				while (sc.hasNext())
				{
					prop = sc.next();
					lstPropositionsQCM.add(new PropositionQCM(prop.substring(2),
					prop.substring(0,2).equals("V:")));
				}
			}
				
			if (type == TypeQuestion.ASSOCIATION)
			{
				lstPropositionsAssociation = new ArrayList<PropositionAssociation>();
				
				while (sc.hasNext())
				{
					lstPropositionsAssociation.add(new PropositionAssociation(sc.next(), sc.next()));
				}
			}
				
			if (type == TypeQuestion.ELIMINATION)
			{
				lstPropositionsElimination = new ArrayList<PropositionElimination>();
				
				while (sc.hasNext())
				{
					prop = sc.next();

					if(prop.startsWith("F:"))
					{
						ordreElim = Integer.parseInt(prop.substring(prop.indexOf(":") + 1, prop.indexOf    (":") + 3));
						nbPtPerdu = Integer.parseInt(prop.substring(prop.indexOf(":") + 4, prop.indexOf    (":") + 5));
					}
					else
					{
						ordreElim = 0;
						nbPtPerdu = 0;
					}

					lstPropositionsElimination.add(new PropositionElimination(prop.substring(prop.lastIndexOf(":") + 1), prop.startsWith("V:"), ordreElim, nbPtPerdu));
				}
			}
			

			sc.close();

			//return new DetailsQuestion(ressource, notion, difficulte, temps, note, intitule, explication, lstPropositionsQCM, lstPropositionsAssociation, lstPropositionsElimination);
			return null;
		}
		catch (Exception e)
		{
			System.out.println("Erreur lors de la lecture du détails de la question : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Convertit un temps en seconde
	 * 
	 * @param temps le temps
	 * @return le temps en seconde
	 */
	private int enSeconde(String temps)
	{
		int m, s;

		m = Integer.parseInt(temps.substring(0, 2));
		s = Integer.parseInt(temps.substring(3, 5));

		return m * 60 + s;
	}
	
		/**
	 * Supprime une ressource à partir de son code.
	 * @param code le code de la ressource
	 * 
	 */
	public void supprimerRessource(String code) 
	{
		// Supprime les questions associées à la ressource

		
		// Supprime les notions associées à la ressource
		this.banqueNotions.supprimerNotions(code);
		this.banqueNotions.sauvegarder();

		
		// Supprime la ressource
		this.banqueRessources.supprimerRessource(code);
		this.banqueRessources.sauvegarder();
	}
	
	/**
	 * Supprime une notion à partir de son id.
	 * @param id l'id de la notion
	 */
	public void supprimerNotion(int id) 
	{
		// Supprime les questions associées à la ressource
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
		//this.banqueQuestions.supprimerQuestion(codeRessource, idNot);
	}

}
