package controleur;

import java.util.ArrayList;
import java.util.List;

import ihm.FrameBanqueDeQuestions;
import metier.BanqueDeQuestions;
import metier.BanqueDeRessources;

import metier.Ressource;
import metier.Notion;
import metier.QCMBuilder;
import metier.Question;

public class Controleur
{
	private QCMBuilder             metier;
	private	FrameBanqueDeQuestions ihm;
	
	public Controleur()
	{
		this.metier = new QCMBuilder(this);
		this.ihm    = new FrameBanqueDeQuestions(this);
	}

	public List<Ressource> getRessources()
	{
		return this.metier.getRessources();
	}

	public List<Notion> getNotions(Ressource ressource)
	{
		/*List<String>    lstNomNotion;
		lstNomNotion = new ArrayList<String>();
		lst.add("Encapsulation");
		lst.add("Héritage");
		lst.add("Polymorphisme");
		lst.add("Abstraction");*/

		return this.metier.getNotions(ressource);
	}

	public List<Question> getQuestions(Ressource ressource, Notion notion)
	{
		return this.metier.getQuestions(ressource, notion);
	}

	public void afficherRessources()
	{
		this.ihm.afficherRessources();
	}

	public void afficherNotions(Ressource ressource)
	{ 
		this.ihm.afficherNotions(ressource);
	}

	public void afficherQuestions(Ressource ressource, Notion notion)
	{
		this.ihm.afficherQuestions(ressource, notion);
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


	public void ajouterElement() 
	{
		// Tout ça est censé être dans le banqueQuestion
		if(this.getRessourceActive() == null)
		{
			this.ihm.creerRessource();
		}
		else if(this.getNotionActive() == null)
		{
			this.ihm.creerNotion();
		}
		else
		{
			this.ihm.creerQuestion();
		}
	}

	public void creerRessource(String nomRessource, String cheminImg) 
	{
		this.metier.creerRessource(nomRessource, cheminImg);
	}

	public void creerNotion(Ressource ressource, String nomNotion, String cheminImg)  
	{
		this.metier.creerNotion(ressource, nomNotion, cheminImg);
	}


	public static void main(String[] args) 
	{
		new Controleur();
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
