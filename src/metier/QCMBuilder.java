package metier;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

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


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.QCM);

		qcm = new QCM(details.ressource(), details.notion(), details.difficulte(),
		              details.temps(), details.note(), unique);

		qcm.setIntitule(details.intitule());
		qcm.setExplication(details.explication());

		for (int cpt = 0 ; cpt < details.propQCM().size() ; cpt++)
		{
			qcm.ajouterProposition(details.propQCM().get(cpt));
		}

		this.banqueQuestions.ajouterQuestions(qcm);
		
		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public List<String> creerAssociation(String detailsQuestion) 
	{
		Association asso;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.ASSOCIATION);

		asso = new Association(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());

		asso.setIntitule(details.intitule());
		asso.setExplication(details.explication());
							   
		for (int cpt = 0 ; cpt < details.propAssos().size() ; cpt++)
		{
			asso.ajouterProposition(details.propAssos().get(cpt));
		}
		
		this.banqueQuestions.ajouterQuestions(asso);

		List<String> lstErreurs = new ArrayList<String>();


		return lstErreurs;
	}

	public List<String> creerElimination(String detailsQuestion) 
	{
		Elimination elim;
		DetailsQuestion details;


		details = this.lireDetailsQuestion(detailsQuestion, TypeQuestion.ELIMINATION);

		elim = new Elimination(details.ressource(), details.notion(), details.difficulte(),
		                       details.temps(), details.note());

		elim.setIntitule(details.intitule());
		elim.setExplication(details.explication());

		for (int cpt = 0 ; cpt < details.propElim().size() ; cpt++)
		{
			elim.ajouterProposition(details.propElim().get(cpt));
		}

		this.banqueQuestions.ajouterQuestions(elim);
		
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


		this.verifDetailsQuestion(detailsQuestion);

		try
		{
			sc = new Scanner(detailsQuestion);
		
			sc.useDelimiter("\t");

			System.out.println(prop = sc.next());
			ressource   = this.banqueRessources.getRessource(prop);
			notion      = this.banqueRessources.getNotion(ressource.getNom(), sc.next());
			difficulte  = Difficulte.fromInt(sc.nextInt());
			temps       = this.enSeconde(sc.next());
			note        = sc.nextDouble();
			intitule    = sc.next();
			explication = sc.next();

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

	private List<String> verifDetailsQuestion(String detailsQuestion)
	{
		Scanner sc;
		List<String> lstErreurs;

		String formatTemps;
		String prop;

		Ressource     ressource;
		Notion        notion;
		Difficulte    difficulte;
		String        temps;
		double        note;
		String        intitule;
		String        explication;

		boolean tempsValide;


		lstErreurs = new ArrayList<String>();

		try
		{
			sc = new Scanner(detailsQuestion);
		
			sc.useDelimiter("\t");

			prop = sc.next();
			ressource   = this.banqueRessources.getRessource(prop);
			notion      = this.banqueRessources.getNotion(ressource.getNom(), sc.next());
			difficulte  = Difficulte.fromInt(sc.nextInt());
			temps       = sc.next();
			note        = sc.nextDouble();
			intitule    = sc.next();
			explication = sc.next();

			formatTemps = "^\\d{2}:\\d{2}$";
			tempsValide = Pattern.matches(formatTemps, temps);

			if (tempsValide) lstErreurs.add("Le format du temps n'est pas valide, veuillez entrer un temps au format min:sec (XX:YY)");

			sc.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		// Vérifie si le temps correspond au format XX:YY
		return lstErreurs; 
	}

	private int enSeconde(String temps)
	{
		int m, s;

		m = Integer.parseInt(temps.substring(0, 2));
		s = Integer.parseInt(temps.substring(3, 5));

		return m * 60 + s;
	}


	public static void main(String[] args)
	{
		QCMBuilder qcmBuilder;

		qcmBuilder = new QCMBuilder();

		qcmBuilder.creerArborescence();
	}
}
