package controleur;

import java.util.List;

import ihm.IHM;
import metier.entite.Ressource;
import metier.entite.Notion;
import metier.entite.question.Question;
import metier.QCMBuilder;

/**
 * Classe Controleur.
 * 
 * @author  Equipe 3.
 * @version 1.0 17/12/2024.
 */
public class Controleur
{
	private QCMBuilder metier;

	/**
	 * Constructeur de la classe Controleur.
	 */
	public Controleur()
	{
		this.metier = new QCMBuilder();
		new IHM(this);
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne la liste des ressources.
	 * 
	 * @return la liste des ressources.
	 */
	public List<Ressource> getRessources()
	{
		return this.metier.getRessources();
	}

	/**
	 * Retourne une ressource à partir de son code.
	 * 
	 * @param  code le code de la ressource.
	 * @return      la ressource
	 */
	public Ressource getRessource(String code) 
	{
		return this.metier.getRessource(code);
	}

	/**
	 * Retourne la liste de toutes les notions.
	 * 
	 * @return la liste de toutes les notions.
	 */
	public List<Notion> getNotions()
	{
		return this.metier.getNotions();
	}

	/**
	 * Retourne la liste des notions associées à une ressource à partir de son code.
	 * 
	 * @param  codeRes le code de la ressource.
	 * @return la liste des notions associées.
	 */
	public List<Notion> getNotions(String codeRes)
	{
		return this.metier.getNotions(codeRes);
	}

	/**
	 * Retourne une notion à partir de son id.
	 * 
	 * @param idNot l'id de la notion
	 * @return la notion
	 */
	public Notion getNotion(int idNot) 
	{
		return this.metier.getNotion(idNot);
	}

	public Notion getNotionParNom(String codeRes, String nomNot) 
	{
		return this.metier.getNotionParNom(codeRes, nomNot);
	}


	/**
	 * Retourne la liste de toutes questions.
	 * 
	 * @return la liste de toutes les questions.
	 */
	public List<Question> getQuestions() 
	{
		return this.metier.getQuestions();
	}
	
	public List<Question> getQuestions(String codeRes) 
	{
		return this.metier.getQuestions(codeRes);
	}

	public List<Question> getQuestions(String codeRes, int idNot)
	{
		return this.metier.getQuestions(codeRes, idNot);
	}

	public Question getQuestion(int idQst)
	{
		return this.metier.getQuestion(idQst);
	}

	/**
	 * Retourne la ressource active.
	 * 
	 * @return la ressource active.
	 */
	public Ressource getRessourceSelectionnee()
	{
		return this.metier.getRessourceSelectionnee();
	}

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource active.
	 * 
	 * @param ressource la nouvelle ressource active.
	 */
	public void setRessourceSelectionnee(Ressource ressource) 
	{
		this.metier.setRessourceSelectionnee(ressource);
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/**
	 * Creer une nouvelle ressource.
	 * 
	 * @param nomRessource le nom de la nouvelle ressource.
	 */
	public void creerRessource(String codeRessource, String nomRessource) 
	{
		this.metier.creerRessource(codeRessource, nomRessource);
	}

	/**
	 * Creer une nouvelle notion.
	 * 
	 * @param nomNotion le nom de la nouvelle notion.
	 */
	public void creerNotion(String codeRes, String nomNotion)  
	{
		this.metier.creerNotion(codeRes, nomNotion);
	}

	/**
	 * Creer une question.
	 * 
	 * @param  detailsQuestion toutes les informations de la questions.
	 * @param lstDetailsProp 
	 * @param explication 
	 * @param intitule 
	 * @return la liste des érreures ayant pu être trouvé lors de la création de la question.
	 */
	public void creerQuestion(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.metier.creerQuestion(detailsQuestion, intitule, explication, lstDetailsProp);
	}


	public void modifierRessource(String code, String nouveauCode, String nouveauNom) 
	{
		this.metier.modifierRessource(code, nouveauCode, nouveauNom);
	}

	public void modiferNotion(int idNot, String nouveauNom) 
	{
		this.metier.modifierNotion(idNot, nouveauNom);
	}

	public void modifierQuestion(int idQst, String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.metier.modifierQuestion(idQst, detailsQuestion, intitule, explication, lstDetailsProp);
	}

	/**
	 * Supprime une ressource à partir de son code.
	 * @param code le code de la ressource
	 * 
	 */
	public void supprimerRessource(String code) 
	{
		this.metier.supprimerRessource(code);
	}
	
	/**
	 * Supprime une notion à partir de son id.
	 * @param id l'id de la notion
	 */
	public void supprimerNotion(int id) 
	{
		this.metier.supprimerNotion(id);
	}

	/**
	 * Supprime une question à partir de son id.
	 * @param id            l'id de la question
	 */
	public void supprimerQuestion(int id) 
	{
		this.metier.supprimerQuestion(id);
	}

	/**
	 * Creer une pièce jointe dans la partie metier.
	 */
	public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.metier.creerPieceJointe(cheminFichier, question);
	}

	/**
	 * Génére une évaluation.
	 */
	public void genererQuestionnaire(String cheminFichier) 
	{
		this.metier.genererQuestionnaire(cheminFichier);
	}

	public static void main(String[] args) 
	{
		new Controleur();
	}
}
