package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import metier.banque.BanqueDeQuestions;
import metier.banque.BanqueDeRessources;

import metier.entite.Ressource;
import metier.entite.Notion;

import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.qcm.QCM;

public class QCMBuilder 
{
	private BanqueDeQuestions  banqueQuestions;
	private BanqueDeRessources banqueRessources;

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
		this.banqueQuestions.sauvegarderQuestions();
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
}
