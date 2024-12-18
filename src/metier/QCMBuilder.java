package metier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import metier.banque.BanqueDeQuestions;
import metier.banque.BanqueDeRessources;

import metier.entite.Ressource;
import metier.entite.Notion;
import metier.entite.Questionnaire;

import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.qcm.QCM;

/** Classe QCMBuilder
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCMBuilder 
{
	private BanqueDeQuestions  banqueQuestions;
	private BanqueDeRessources banqueRessources;

	private Questionnaire questionnaire;

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

	public void creerQuestion() 
	{
		QCM qcm;


		qcm = new QCM(this.ressourceActive, this.notionActive, Difficulte.FACILE,  30, 1.0, true);
		qcm.setIntitule("A quoi sert le chiffrement ?");
		this.banqueQuestions.ajouterQuestions(qcm);
		this.banqueQuestions.sauvegarderQuestions("data/questions.csv");
	}

	public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.banqueQuestions.creerPieceJointe(cheminFichier, question);
	}

	public List<String> creerQCM(String detailsQuestion, boolean unique) 
	{
		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public List<String> creerAssociation(String detailsQuestion) 
	{
		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public List<String> creerElimination(String detailsQuestion) 
	{
		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public void genererEvaluation(String cheminFichier)
	{
		this.questionnaire.genererEvaluation(cheminFichier);
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


	public static void main(String[] args)
	{
		QCMBuilder qcmBuilder;

		qcmBuilder = new QCMBuilder();

		qcmBuilder.creerArborescence();
	}
}
