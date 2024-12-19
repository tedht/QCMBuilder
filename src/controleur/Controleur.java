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
	 * @return      la ressource
	 */
	public Ressource getRessource(String code) 
	{
		return this.metier.getRessource(code);
	}

	/**
	 * Retourne la liste des notions associées à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @return           la liste des notions.
	 */
	public List<Notion> getNotions(Ressource ressource)
	{
		return this.metier.getNotions(ressource);
	}

	/**
	 * Retourne une notion à partir de son nom et de sa ressource.
	 * 
	 * @param  nomRessource le nom de la notion.
	 * @param  ressource    la ressource
	 * @return              la notion
	 */
	public Notion getNotion(String nomNotion, Ressource ressource) 
	{
		return this.metier.getNotion(nomNotion, ressource);
	}

	/**
	 * Retourne la liste des noms des notions associées à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @return           la liste des noms.
	 */
	public List<String> getNomNotions(Ressource ressource)
	{
		return this.metier.getNomNotions(ressource);
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
	 * Retourne la liste des questions associées à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @return           la liste des questions.
	 */
	public List<Question> getQuestions(Ressource ressource) 
	{
		return this.metier.getQuestions(ressource);
	}

	/**
	 * Retourne la liste des questions associées à une notion associée à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @param  notion    la notion.
	 * @return           la liste des questions.
	 */
	public List<Question> getQuestions(Ressource ressource, Notion notion)
	{
		return this.metier.getQuestions(ressource, notion);
	}

	/**
	 * Retourne le nombre de notions associées à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @return           le nombre de notions.
	 */
	public int getNbNotions(Ressource ressource) 
	{
		return this.getNotions(ressource).size();
	}

	/**
	 * Retourne le nombre de questions associées à une notion associée à une ressource.
	 * 
	 * @param  ressource la ressource.
	 * @param  notion    la notion.
	 * @return           le nombre de questions.
	 */
	public int getNbQuestions(Ressource ressource, Notion notion) 
	{
		return this.getQuestions(ressource, notion).size();
	}

	/**
	 * Retourne la ressource active.
	 * 
	 * @return la ressource active.
	 */
	public Ressource getRessourceActive()
	{
		return this.metier.getRessourceActive();
	}

	/**
	 * Retourne la notion active.
	 * 
	 * @return la notion active.
	 */
	public Notion getNotionActive()
	{
		return this.metier.getNotionActive();
	}



	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource active.
	 * 
	 * @param ressource la nouvelle ressource active.
	 */
	public void setRessourceActive(Ressource ressource) 
	{
		this.metier.setRessourceActive(ressource);
	}

	/**
	 * Modifie la notion active.
	 * 
	 * @param notion la nouvelle notion active.
	 */
	public void setNotionActive(Notion notion) 
	{
		this.metier.setNotionActive(notion);
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
	public void creerNotion(String nomNotion)  
	{
		this.metier.creerNotion(nomNotion);
	}

	/**
	 * Creer une pièce jointe dans la partie metier.
	 */
	public void creerPieceJointe(String cheminFichier, Question question)
	{
		this.metier.creerPieceJointe(cheminFichier, question);
	}

	/**
	 * Vide l'historique
	 */
	public void popHistorique() 
	{
		this.metier.popHistorique();
	}

	/**
	 * Creer une question QCM.
	 * 
	 * @param  detailsQuestion toutes les informations de la questions.
	 * @param  unique          true si le qcm est à réponse unique, false si il est à réponse multiples.
	 * @return                 la liste des érreures ayant pu être trouvé lors de la création de la question.
	 */
	public void creerQCM(String detailsQuestion, boolean unique) 
	{
		this.metier.creerQCM(detailsQuestion, unique);
	}

	/**
	 * Creer une question Association.
	 * 
	 * @param  detailsQuestion toutes les informations de la questions.
	 * @return                 la liste des érreures ayant pu être trouvé lors de la création de la question.
	 */
	public void creerAssociation(String detailsQuestion) 
	{
		this.metier.creerAssociation(detailsQuestion);
	}

	/**
	 * Creer une question Elimination.
	 * 
	 * @param  detailsQuestion toutes les informations de la questions.
	 * @return                 la liste des érreures ayant pu être trouvé lors de la création de la question.
	 */
	public void creerElimination(String detailsQuestion) 
	{
		this.metier.creerElimination(detailsQuestion);
	}

	/**
	 * Génére une évaluation.
	 */
	public void genererQuestionnaire(String cheminFichier) 
	{
		this.metier.genererQuestionnaire(cheminFichier);
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
		this.metier.supprimerNotion(id)
	}

	/**
	 * Supprime une question à partir du code de la ressource associée et de l'id de la notion associée.
	 * @param codeRessource le code de la ressource associée
	 * @param id            l'id de la notion associée
	 */
	public void supprimerQuestion(String codeRessource, int idNotion) 
	{
		this.metier.supprimerQuestion(codeRessource, idNotion);
	}


	public static void main(String[] args) 
	{
		new Controleur();
	}
}
