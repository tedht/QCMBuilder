package controleur;

import java.util.ArrayList;
import java.util.List;

import ihm.FrameBanqueDeQuestions;

public class Controleur 
{
	//private BanqueDeQuestions metier
	private	FrameBanqueDeQuestions ihm;
	
	public Controleur()
	{
		//this.metier = new BanqueDeQuestions(this);
		this.ihm = new FrameBanqueDeQuestions(this);
	}

	public List<String> getRessources()
	{
		List<String> lst = new ArrayList<String>();

		lst.add("Dévelopement Orienté Objet");
		lst.add("Dévelopement Efficace");
		lst.add("Initiation au Développement");
		/*
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		lst.add("Initiation au Développement");
		*/

		return lst;
	}

	public List<String> getNotions(String ressource)
	{
		List<String> lst = new ArrayList<String>();

		lst.add("Encapsulation");
		lst.add("Héritage");
		lst.add("Polymorphisme");
		lst.add("Abstraction");

		return lst;
	}

	public List<String> getQuestions(String ressource, String notion)
	{
		return new ArrayList<String>();
	}
	
	public static void main(String[] args) 
	{
		new Controleur();
	}

	public void afficherRessources()                 
	{
		this.ihm.afficherRessources();         
	}

	public void afficherNotions(String ressource) 
	{ 
		this.ihm.afficherNotions(ressource); 
	}

	public void afficherQuestions(String ressource, String notion)    
	{ 
		this.ihm.afficherQuestions(ressource, notion);    
	}

	/* tmp */
	public void majHistorique(String action) { this.ihm.majHistorique(action); }

	public void setRessourceActive(String ressource) 
	{
		//this.metier.setRessourceActive(ressource);
		// => DANS LE METIER : On met à jour l'historique et on appel afficherNotions
		
		/* tmp */
		this.ihm.majHistorique("R");
		this.ihm.afficherNotions(ressource);
	}

	public void setNotionActive(String notion) 
	{
		//this.metier.setNotionActive(notion);
		// => DANS LE METIER : On met à jour l'historique et on appel afficherQuestions
		
		/* tmp */
		this.ihm.majHistorique("N"+this.getRessourceActive());
		this.ihm.afficherQuestions(this.getRessourceActive(), notion);
	}

	public int getNbNotions(String ressource) 
	{
		return this.getNotions(ressource).size();
	}

	public int getNbQuestions(String ressource, String notion) 
	{
		return this.getQuestions(ressource, notion).size();
	}

	public String getRessourceActive()
	{
		//return this.metier.getRessourceActive();
		return "Dévelopement Orienté Objet";
		//return null;
	}

	public String getNotionActive()
	{
		//return this.metier.getNotionActive();
		return "Encapsulation";
		//return null; 
	}


	public void ajouterElement() 
	{
		// Tout ça est censé être dans le metier
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
		//this.metier.creerRessource(nomRessource, cheminImg);
	}

	public void creerNotion(String nomNotion, String cheminImg)  
	{
		//this.metier.creerNotion(nomNotion, cheminImg);
	}
}
