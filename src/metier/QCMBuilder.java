package metier;

import java.util.List;
import java.util.Stack;

import controleur.Controleur;
import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Question;

public class QCMBuilder 
{
	private Controleur ctrl;
	
	private BanqueDeQuestions  banqueQuestion;
	private BanqueDeRessources banqueRessource;

	private Ressource ressourceActive;
	private Notion    notionActive;

	private Stack<String> historique;
	
	public QCMBuilder(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.banqueQuestion  = new BanqueDeQuestions(ctrl);
		this.banqueRessource = new BanqueDeRessources(); 

		this.ressourceActive = null;
		this.notionActive    = null;

		this.historique = new Stack<String>();

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
		//this.ctrl.afficherNotions(this.ressourceActive);
	}

	public void setNotionActive(Notion notion) 
	{
		this.notionActive = notion;
		this.historique.add("N"+this.ressourceActive.getNom());
		//this.ctrl.afficherQuestions(this.ressourceActive, this.notionActive);
	}

	public void retour()
	{
		if(!this.historique.empty())
		{
			String retour = this.historique.pop();
			switch(retour.charAt(0))
			{
				case 'R' : 
					//this.ctrl.afficherRessources(); 
					this.ressourceActive = null;
				break;
				case 'N' : 
					/*this.ctrl.afficherNotions(
						this.banqueRessource.getRessource(retour.substring(1))
					);*/
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

	public void creerRessource(String nomRessource, String cheminImg) 
	{
		this.banqueRessource.ajouterRessource(new Ressource(nomRessource));
		//this.ctrl.afficherRessources();
	}

	public void creerNotion(Ressource ressource, String nomNotion, String cheminImg) 
	{
		ressource.ajouterNotion(new Notion(nomNotion));
		//this.ctrl.afficherNotions(ressource);
	}

	public void creerQuestion() 
	{
		this.banqueQuestion.ajouterQuestions(new Question(0, "A quoi sert le chiffrement ?", "", "Facile", ressourceActive, notionActive, 30, 1));
		//this.ctrl.afficherQuestions(this.ressourceActive, this.notionActive);
	}

}
