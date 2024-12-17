package controleur;

import java.util.List;

import ihm.IHM;

import metier.entite.Ressource;
import metier.entite.Notion;

import metier.entite.question.Question;

import metier.QCMBuilder;

public class Controleur
{
	private QCMBuilder metier;
	
	public Controleur()
	{
		this.metier = new QCMBuilder();
		new IHM(this);
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

	public void creerRessource(String nomRessource) 
	{
		this.metier.creerRessource(nomRessource);
	}

	public void creerNotion(String nomNotion)  
	{
		this.metier.creerNotion(nomNotion);
	}

	public void creerQuestion() 
	{
		this.metier.creerQuestion();
	}

	public void popHistorique() 
	{
		this.metier.popHistorique();
	}

	public Ressource getRessource(String nomRessource) 
	{
		return this.metier.getRessource(nomRessource);
	}

	public List<String> creerQCM(String detailsQuestion, boolean unique) 
	{
		return this.metier.creerQCM(detailsQuestion, unique);
	}

	public List<String> creerAssociation(String detailsQuestion) 
	{
		return this.metier.creerAssociation(detailsQuestion);
	}

	public List<String> creerElimination(String detailsQuestion) 
	{
		return this.metier.creerElimination(detailsQuestion);
	}

	public void genererEvaluation() 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'genererEvaluation'");
	}
}
