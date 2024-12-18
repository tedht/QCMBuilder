package metier;

import java.io.File;
import java.io.FileInputStream;
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

import metier.entite.question.association.Association;
import metier.entite.question.qcm.QCM;
import metier.entite.question.elimination.Elimination;

import metier.entite.question.Proposition;

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
		this.ressourceActive = null;
		this.notionActive    = null;

		this.historique = new Stack<String>();


		this.banqueRessources = new BanqueDeRessources(); 
		this.banqueQuestions  = new BanqueDeQuestions(this);
	}

	public List<Ressource> getRessources() 
	{
		return this.banqueRessources.getRessources();
	}

	public List<Notion> getNotions(Ressource ressource) 
	{
		return this.banqueRessources.getNotions(ressource);
	}

		public List<String> getNomNotions(Ressource ressource) 
	{
		return this.banqueRessources.getNomNotions(ressource);
	}

	public List<Question> getQuestions(Ressource ressource, Notion notion) 
	{
		return this.banqueQuestions.getQuestions(ressource, notion);
	}

	public void setRessourceActive(Ressource ressource) 
	{
		this.ressourceActive = ressource;
		this.historique.add("R");
	}

	public void setNotionActive(Notion notion) 
	{
		this.notionActive = notion;
		this.historique.add("N"+this.ressourceActive.getNom());
	}

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

	public Ressource getRessourceActive() 
	{
		return this.ressourceActive;
	}

	public Notion getNotionActive() 
	{
		return this.notionActive;
	}

	public Ressource getRessource(String nomRessource) 
	{
		return this.banqueRessources.getRessource(nomRessource);
	}

	public void creerRessource(String code, String nomRessource) 
	{
		this.banqueRessources.ajouterRessource(new Ressource(code, nomRessource));
		this.banqueRessources.sauvegarderRessources("data/ressources.csv");
	}

	public void creerNotion(String nomNotion) 
	{
		if(this.ressourceActive != null)
		{
			this.ressourceActive.ajouterNotion(new Notion(nomNotion, this.ressourceActive.getNotions().size(), this.ressourceActive.getCode()));
			this.banqueRessources.sauvegarderRessources("data/ressources.csv");
		}
	}


	public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.banqueQuestions.creerPieceJointe(cheminFichier, question);
	}

	public List<String> creerQCM(String detailsQuestion, boolean unique) 
	{
		QCM qcm;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion);

		qcm = new QCM(details.ressource(), details.notion(), details.difficulte(),
		              details.temps(), details.note(), unique);

		this.banqueQuestions.ajouterQuestions(qcm);

		/*for (int cpt = 0 ; cpt < details.propositions().size() ; cpt++)
		{
			qcm.ajouterProposition(details.propositions().get(cpt));
		}*/
		
		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public List<String> creerAssociation(String detailsQuestion) 
	{
		Association asso;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion);

		asso = new Association(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());
		
		this.banqueQuestions.ajouterQuestions(asso);

		/*for (int cpt = 0 ; cpt < details.propositions().size() ; cpt++)
		{
			asso.ajouterProposition(details.propositions().get(cpt));
		}*/

		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public List<String> creerElimination(String detailsQuestion) 
	{
		Elimination elim;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion);

		elim = new Elimination(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());


		this.banqueQuestions.ajouterQuestions(elim);

		/*for (int cpt = 0 ; cpt < details.propositions().size() ; cpt++)
		{
			elim.ajouterProposition(details.propositions().get(cpt));
		}*/
		
		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public void genererQuestionnaire(String cheminFichier)
	{
		this.Questionnaire.genererQuestionnaire(cheminFichier);
	}

	public void creerArborescence()
	{
		File dossier;


		for (Ressource ressource : this.banqueRessources.getRessources())
		{
			dossier = new File("ressources/" + ressource.getCode() + " " + ressource.getNom());
			System.out.println(ressource.getNom());


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

	private DetailsQuestion lireDetailsQuestion(String detailsQuestion)
	{
		Scanner sc;

		Ressource     ressource;
		Notion        notion;
		Difficulte    difficulte;
		int           temps;
		double        note;
		String        intitule;
		String        explication;

		List<Proposition> lstPropositions;


		try
		{
			lstPropositions = new ArrayList<Proposition>();
			sc = new Scanner(new FileInputStream(detailsQuestion), "UTF8");
		
			sc.useDelimiter("\t");

			ressource   = this.banqueRessources.getRessource(sc.next());
			notion      = this.banqueRessources.getNotion(ressource.getNom(), sc.next());
			difficulte  = Difficulte.fromInt(sc.nextInt());
			temps       = sc.nextInt();
			note        = sc.nextDouble();
			intitule    = sc.next();
			explication = sc.next();

			/*while(sc.hasNext())
			{
				
			}*/
			

			sc.close();

			return new DetailsQuestion(ressource, notion, difficulte, temps, note, intitule, explication);
		}
		catch (Exception e)
		{
			System.out.println("Erreur lors de la lecture du détails de la question : " + e.getMessage());
			return null;
		}

		

	}


	public static void main(String[] args)
	{
		QCMBuilder qcmBuilder;

		qcmBuilder = new QCMBuilder();

		qcmBuilder.creerArborescence();
	}
}
