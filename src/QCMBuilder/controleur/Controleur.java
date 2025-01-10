package QCMBuilder.controleur;


import java.util.List;

import QCMBuilder.ihm.IHM;

import QCMBuilder.metier.QCMBuilder;

import QCMBuilder.metier.entite.Notion;
import QCMBuilder.metier.entite.Ressource;

import QCMBuilder.metier.entite.question.Difficulte;
import QCMBuilder.metier.entite.question.Question;

/**
 * Classe Controleur qui gère les interactions entre Le gestionnaire des fenêtres de l'application. et le métier.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Controleur
{
	/*-----------*/
	/* Attributs */
	/*-----------*/

	private QCMBuilder metier;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

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
	 * @return      la ressource.
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
	 * @return         la liste des notions associées.
	 */
	public List<Notion> getNotions(String codeRes)
	{
		return this.metier.getNotions(codeRes);
	}

	/**
	 * Retourne une notion à partir de son id.
	 * 
	 * @param idNot l'id de la notion.
	 * @return      la notion.
	 */
	public Notion getNotion(int idNot) 
	{
		return this.metier.getNotion(idNot);
	}

	/**
	 * Retourne une notion à partir de son nom et son code de ressource.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param nomNot  le nom de la notion.
	 * @return        la notion.
	 */
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
	
	/**
	 * Retourne la liste des questions associées à une ressource à partir de son code.
	 * 
	 * @param codeRes le code de la ressource.
	 * @return        la liste des questions associées.
	 */
	public List<Question> getQuestions(String codeRes) 
	{
		return this.metier.getQuestions(codeRes);
	}

	/**
	 * Retourne la liste des questions associées à une ressource à partir de son code et de l'id de la notion.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param idNot   l'id de la notion.
	 * @return        la liste des questions associées.
	 */
	public List<Question> getQuestions(String codeRes, int idNot)
	{
		return this.metier.getQuestions(codeRes, idNot);
	}

	/**
	 * Retourne une question à partir de son id.
	 * 
	 * @param idQst l'id de la question.
	 * @return      la question.
	 */
	public Question getQuestion(int idQst)
	{
		return this.metier.getQuestion(idQst);
	}

	/**
	 * Retourne la liste des questions associées à une ressource à partir de son code, de l'id de la notion et d'une difficulté donnée.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param idNot   l'id de la notion.
	 * @param diff    la difficulté de la question.
	 * @return        la liste des questions associées.
	 */
	public List<Question> getQuestions(String codeRes, int idNot, Difficulte diff)
	{
		return this.metier.getQuestions(codeRes, idNot, diff);
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
	 * @param codeRessource le code de la nouvelle ressource.
	 * @param nomRessource  le nom de la nouvelle ressource.
	 */
	public void creerRessource(String codeRessource, String nomRessource) 
	{
		this.metier.creerRessource(codeRessource, nomRessource);
	}

	/**
	 * Creer une nouvelle notion.
	 * 
	 * @param codeRes   le code de la ressource à laquelle la notion est associée.
	 * @param nomNotion le nom de la nouvelle notion.
	 */
	public void creerNotion(String codeRes, String nomNotion)  
	{
		this.metier.creerNotion(codeRes, nomNotion);
	}

	/**
	 * Creer une question.
	 * 
	 * @param detailsQuestion toutes les informations de la questions.
	 * @param intitule        l'intitulé de la question.
	 * @param explication     l'explication de la question.
	 * @param lstDetailsProp  la liste des informations des propositions.
	 */
	public void creerQuestion(String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.metier.creerQuestion(detailsQuestion, intitule, explication, lstDetailsProp);
	}

	/**
	 * Modifie une ressource à partir de son code.
	 * 
	 * @param code        le code de la ressource à modifier.
	 * @param nouveauCode le nouveau code de la ressource.
	 * @param nouveauNom  le nouveau nom de la ressource.
	 */
	public void modifierRessource(String code, String nouveauCode, String nouveauNom) 
	{
		this.metier.modifierRessource(code, nouveauCode, nouveauNom);
	}

	/**
	 * Modifie une notion à partir de son id.
	 * 
	 * @param idNot       l'id de la notion à modifier.
	 * @param nouveauNom  le nouveau nom de la notion.
	 */
	public void modiferNotion(int idNot, String nouveauNom) 
	{
		this.metier.modifierNotion(idNot, nouveauNom);
	}

	/**
	 * Modifie une question à partir de son id.
	 * 
	 * @param idQst           l'id de la question à modifier.
	 * @param detailsQuestion toutes les informations de la questions.
	 * @param intitule        l'intitulé de la question.
	 * @param explication     l'explication de la question.
	 * @param lstDetailsProp  la liste des informations des propositions.
	 */
	public void modifierQuestion(int idQst, String detailsQuestion, String intitule, String explication, List<String> lstDetailsProp) 
	{
		this.metier.modifierQuestion(idQst, detailsQuestion, intitule, explication, lstDetailsProp);
	}



	/**
	 * Supprime une ressource à partir de son code.
	 * 
	 * @param code le code de la ressource.
	 */
	public void supprimerRessource(String code) 
	{
		this.metier.supprimerRessource(code);
	}
	
	/**
	 * Supprime une notion à partir de son id.
	 * 
	 * @param id l'id de la notion
	 */
	public void supprimerNotion(int id) 
	{
		this.metier.supprimerNotion(id);
	}

	/**
	 * Supprime une question à partir de son id.
	 * 
	 * @param id l'id de la question.
	 */
	public void supprimerQuestion(int id) 
	{
		this.metier.supprimerQuestion(id);
	}


	
	/**
	 * Génére une évaluation.
	 * 
	 * @param cheminFichier le chemin du fichier à générer.
	 */
	public void creerQuestionnaire(String codeRes, boolean chronometre, int[][] tabNbQuestions) 
	{
		this.metier.creerQuestionnaire(codeRes, chronometre, tabNbQuestions);
	}

	public void exporterQuestionnaire(String dossierSauvegarde)
	{
		this.metier.exporterQuestionnaire(dossierSauvegarde);
	}

	/**
	 * Main de la classe Controleur.
	 * 
	 * @param args les arguments de la ligne de commande.
	 */
	public static void main(String[] args) 
	{
		new Controleur();
	}
}
