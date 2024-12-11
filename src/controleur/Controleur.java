package controleur;

import java.util.ArrayList;
import java.util.List;

import ihm.FrameBanqueDeQuestions;
import metier.BanqueDeQuestions;
import metier.BanqueDeRessources;

import metier.Ressource;
import metier.Notion;

public class Controleur
{
	private BanqueDeQuestions      banqueQuestion;
	private BanqueDeRessources     banqueRessource;
	private	FrameBanqueDeQuestions ihm;
	
	public Controleur()
	{
		this.banqueQuestion  = new BanqueDeQuestions(this);
		this.banqueRessource = new BanqueDeRessources(); 
		this.ihm = new FrameBanqueDeQuestions(this);
	}

	public List<String> getRessources()
	{
		List<Ressource> lstRessource;
		List<String>    lstNomRessource;


		lstRessource = this.banqueRessource.getRessources();

		lstNomRessource = new ArrayList<String>();
		for (Ressource ressource : lstRessource)
			lstNomRessource.add(ressource.getNom());

		return lstNomRessource;
	}

	public List<String> getNotions(Ressource ressource)
	{
		List<String>    lstNomNotion;


		lstNomNotion = new ArrayList<String>();
		for (Notion notion : ressource.getNotions())
			lstNomNotion.add(notion.getNom());


		/*lst.add("Encapsulation");
		lst.add("Héritage");
		lst.add("Polymorphisme");
		lst.add("Abstraction");*/

		return lstNomNotion;
	}

	public List<String> getQuestions(Ressource ressource, Notion notion)
	{
		return new ArrayList<String>();
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

	/* tmp */
	public void majHistorique(String action) { this.ihm.majHistorique(action); }

	public void setRessourceActive(String ressource) 
	{
		//this.banqueQuestion.setRessourceActive(ressource);
		// => DANS LE banqueQuestion : On met à jour l'historique et on appel afficherNotions
		
		/* tmp */
		this.ihm.majHistorique("R");
		this.ihm.afficherNotions(ressource);
	}

	public void setNotionActive(String notion) 
	{
		//this.banqueQuestion.setNotionActive(notion);
		// => DANS LE banqueQuestion : On met à jour l'historique et on appel afficherQuestions
		
		/* tmp */
		this.ihm.majHistorique("N"+this.getRessourceActive());
		this.ihm.afficherQuestions(this.getRessourceActive(), notion);
	}

	public int getNbNotions(Ressource ressource) 
	{
		return this.getNotions(ressource).size();
	}

	public int getNbQuestions(Ressource ressource, Notion notion) 
	{
		return this.getQuestions(ressource, notion).size();
	}

	public String getRessourceActive()
	{
		// return this.banqueQuestion.getRessourceActive();
		return "Dévelopement Orienté Objet";
		// return null;
	}

	public String getNotionActive()
	{
		//return this.banqueQuestion.getNotionActive();
		return "Encapsulation";
		//return null;
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
		//this.banqueQuestion.creerRessource(nomRessource, cheminImg);
	}

	public void creerNotion(String nomNotion, String cheminImg)  
	{
		//this.banqueQuestion.creerNotion(nomNotion, cheminImg);
	}


	public static void main(String[] args) 
	{
		new Controleur();
	}
}
