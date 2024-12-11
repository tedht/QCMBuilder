package controleur;

import java.util.List;

import ihm.FrameQCMBuilder;

import metier.Ressource;
import metier.Notion;
import metier.QCMBuilder;
import metier.Question;

public class Controleur
{
	private QCMBuilder metier;
	
	public Controleur()
	{
		this.metier = new QCMBuilder(this);
		new FrameQCMBuilder(this);
	}

	public static void main(String[] args) 
	{
		new Controleur();
	}

	public List<Ressource> getRessources()
	{
		return this.metier.getRessources();
	}

	public List<Notion> getNotions(Ressource ressource)
	{
		return this.metier.getNotions(ressource);
	}

	public List<Question> getQuestions(Ressource ressource, Notion notion)
	{
		return this.metier.getQuestions(ressource, notion);
	}

	public void setRessourceActive(Ressource ressource) 
	{
		this.metier.setRessourceActive(ressource);
	}

	public void setNotionActive(Notion notion) 
	{
		this.metier.setNotionActive(notion);
	}

	public int getNbNotions(Ressource ressource) 
	{
		return this.getNotions(ressource).size();
	}

	public int getNbQuestions(Ressource ressource, Notion notion) 
	{
		return this.getQuestions(ressource, notion).size();
	}

	public Ressource getRessourceActive()
	{
		return this.metier.getRessourceActive();
	}

	public Notion getNotionActive()
	{
		return this.metier.getNotionActive();
	}

	public void creerRessource(String nomRessource, String cheminImg) 
	{
		this.metier.creerRessource(nomRessource, cheminImg);
	}

	public void creerNotion(Ressource ressource, String nomNotion, String cheminImg)  
	{
		this.metier.creerNotion(ressource, nomNotion, cheminImg);
	}

	public void creerQuestion() 
	{
		this.metier.creerQuestion();
	}

	public void retour() 
	{
		this.metier.retour();
	}

	public Ressource getRessource(String nomRessource) 
	{
		return this.metier.getRessource(nomRessource);
	}
}
