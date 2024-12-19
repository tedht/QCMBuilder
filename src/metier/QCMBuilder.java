package metier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import metier.banque.BanqueDeQuestions;
import metier.banque.BanqueDeRessources;

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
	private BanqueDeQuestions  banqueQuestions;
	private BanqueDeRessources banqueRessources;

	private Questionnaire Questionnaire;

	private Ressource ressourceActive;

	private Notion    notionActive;

	private Stack<String> historique;

	
	public QCMBuilder()
	{
		this.banqueRessources = new BanqueDeRessources(); 
		this.banqueQuestions  = new BanqueDeQuestions(this);

		this.ressourceActive = null;
		this.notionActive    = null;

		this.historique = new Stack<String>();
	}

	/**
	 * Retourne la liste des ressources
	 * 
	 * @return la liste des ressources
	 */
	public List<Ressource> getRessources() 
	{
		return this.banqueRessources.getRessources();
	}

	/**
	 * Retourne la liste des notions
	 * 
	 * @param ressource la ressource
	 * @return la liste des notions
	 */
	public List<Notion> getNotions(Ressource ressource) 
	{
		return this.banqueRessources.getNotions(ressource);
	}

	/**
	 * Retourne la liste des noms des notions
	 * 
	 * @param ressource la ressource
	 * @return la liste des noms des notions
	 */
	public List<String> getNomNotions(Ressource ressource) 
	{
		return this.banqueRessources.getNomNotions(ressource);
	}

	
	/**
	 * Retourne la liste de toutes questions.
	 * 
	 * @return           la liste de toutes les questions.
	 */
	public List<Question> getQuestions() 
	{
		return this.banqueQuestions.getQuestions();
	}
	
	/**
	 * Retourne la liste des questions associées à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @return           la liste des questions.
	 */
	public List<Question> getQuestions(Ressource ressource) 
	{
		return this.banqueQuestions.getQuestions(ressource);
	}

	/**
	 * Retourne la liste des questions associées à une notion associée à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @param  notion    la notion.
	 * @return           la liste des questions.
	 */
	public List<Question> getQuestions(Ressource ressource, Notion notion)
	{
		return this.banqueQuestions.getQuestions(ressource, notion);
	}

	/**
	 * Modifie la ressource active
	 * 
	 * @param ressource la ressource active
	 */
	public void setRessourceActive(Ressource ressource) 
	{
		this.ressourceActive = ressource;
		this.historique.add("R");
	}

	/**
	 * Modifie la notion active
	 *
	 * @param notion la notion
	 */
	public void setNotionActive(Notion notion) 
	{
		this.notionActive = notion;
		this.historique.add("N"+this.ressourceActive.getNom());
	}

	/**
	 * Reset l'historique
	 */
	public void popHistorique()
	{
		if(!this.historique.empty())
		{
			String action = this.historique.pop();
			switch(action.charAt(0))
			{
				case 'R' : 
					this.ressourceActive = null;
				break;
				case 'N' : 
					this.notionActive = null; 
				break;
				default : break;
			}
		}
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

	/**
	 * Retourne une ressource
	 * 
	 * @param nomRessource le nom de la ressource
	 * @return la ressource
	 */
	public Ressource getRessource(String code) 
	{
		return this.banqueRessources.getRessource(code);
	}

	/**
	 * Crée une ressource
	 * 
	 * @param code le code de la ressource
	 * @param nomRessource le nom de la ressource
	 */
	public void creerRessource(String code, String nom)
	{
		this.banqueRessources.ajouterRessource(new Ressource(code, nom));
		this.banqueRessources.sauvegarderRessources("data/ressources.csv");
	}

	/**
	 * Crée une notion
	 * 
	 * @param nomNotion le nom de la notion
	 */
	public void creerNotion(String nomNotion) 
	{
		if(this.ressourceActive != null)
		{
			this.ressourceActive.ajouterNotion(new Notion(nomNotion, this.ressourceActive.getNotions().size(), this.ressourceActive.getCode()));
			this.banqueRessources.sauvegarderRessources("data/ressources.csv");
		}
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
	 * @param unique si la question est à réponse unique
	 */
	public void creerQCM(String detailsQuestion, boolean unique) 
	{
		QCM qcm;
		DetailsQuestion details;

		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.QCM);

		qcm = new QCM(details.ressource(), details.notion(), details.difficulte(),
		              details.temps(), details.note(), unique);

		this.banqueQuestions.ajouterQuestions(qcm);

		
		for (int cpt = 0 ; cpt < details.propQCM().size() ; cpt++)
		{
			qcm.ajouterProposition(details.propQCM().get(cpt));
		}

		this.banqueQuestions.ajouterQuestions(qcm);
	}

	public void creerAssociation(String detailsQuestion) 
	{
		Association asso;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.ASSOCIATION);

		asso = new Association(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());

		for (int cpt = 0 ; cpt < details.propAssos().size() ; cpt++)
		{
			asso.ajouterProposition(details.propAssos().get(cpt));
		}
		
		this.banqueQuestions.ajouterQuestions(asso);

	}

	/**
	 * Crée une question d'élimination
	 * 
	 * @param detailsQuestion les détails de la question
	 */
	public void creerElimination(String detailsQuestion) 
	{
		Elimination elim;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.ELIMINATION);

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
		}
	}

	/**
	 * Génère un questionnaire
	 * 
	 * @param cheminFichier le chemin du fichier
	 */
	public void genererQuestionnaire(String cheminFichier)
	{
		this.Questionnaire.genererQuestionnaire(cheminFichier);
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
			}
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
			notion      = this.banqueRessources.getNotion(ressource.getNom(), sc.next());
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

			return new DetailsQuestion(ressource, notion, difficulte, temps, note, intitule, explication, lstPropositionsQCM, lstPropositionsAssociation, lstPropositionsElimination);
		
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
		// Supprimer les questions associées à la ressource
		// Supprimer les notions associées à la ressource
		// Supprimer la ressource
	}
	
	/**
	 * Supprime une notion à partir de son id.
	 * @param id l'id de la notion
	 */
	public void supprimerNotion(int id) 
	{
		// Supprimer les questions associées à la ressource
		// Supprimer la notion
	}

	/**
	 * Supprime une question à partir du code de la ressource associée et de l'id de la notion associée.
	 * @param codeRessource le code de la ressource associée
	 * @param id            l'id de la notion associée
	 */
	public void supprimerQuestion(String codeRessource, int idNotion) 
	{
		// Supprimer la question
		//this.banqueQuestions.supprimerQuestion(codeRessource, idNotion);
	}

	public static void main(String[] args)
	{
		QCMBuilder qcmBuilder;

		qcmBuilder = new QCMBuilder();

		qcmBuilder.creerArborescence();

	}

	public Notion getNotion(String nomNotion, Ressource ressource) 
	{
		return this.banqueRessources.getNotion(nomNotion, ressource.getNom());
	}
}
