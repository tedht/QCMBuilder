package metier;

import java.util.List;
import java.util.Stack;

import metier.banque.BanqueDeQuestions;
import metier.banque.BanqueDeRessources;
import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Difficulte;
import metier.entite.question.Question;

public class QCMBuilder 
{
	private BanqueDeQuestions  banqueQuestion;
	private BanqueDeRessources banqueRessource;

	private Ressource ressourceActive;
	private Notion    notionActive;

	private Stack<String> historique;
	
	public QCMBuilder()
	{
		this.ressourceActive = null;
		this.notionActive    = null;

		this.historique = new Stack<String>();

		this.banqueQuestion  = new BanqueDeQuestions();
		this.banqueRessource = new BanqueDeRessources(); 
	}

	public List<Ressource> getRessources() 
	{
		return this.banqueRessource.getRessources();
	}

	public List<Notion> getNotions(Ressource ressource) 
	{
		return this.banqueRessource.getNotions(ressource);
	}

	public List<Question> getQuestions(Ressource ressource, Notion notion) 
	{
		return this.banqueQuestion.getQuestions(ressource, notion);
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
		return this.banqueRessource.getRessource(nomRessource);
	}

	public void creerRessource(String nomRessource) 
	{
		this.banqueRessource.ajouterRessource(new Ressource(nomRessource));
	}

	public void creerNotion(String nomNotion) 
	{
		if(this.ressourceActive != null)
			this.ressourceActive.ajouterNotion(new Notion(nomNotion));
	}

	public void creerQuestion() 
	{
		this.banqueQuestion.ajouterQuestions(new Question(0, "A quoi sert le chiffrement ?", "", Difficulte.FACILE, ressourceActive, notionActive, 30, 1));
	}
}
