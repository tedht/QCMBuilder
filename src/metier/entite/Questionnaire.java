package metier.entite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import metier.entite.question.Question;


/** 
 * Classe Questionnaire
 * 
 * @author Equipe 03
 * @version 1.0 du 09/12/2024
 */
public class Questionnaire
{



	/*-----------*/
	// Attributs //
	/*-----------*/

	private Ressource      ressource;
	private List<Notion>   notions;
	private boolean        chronometre;
	private List<Question> questions;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe Questionnaire.
	 * 
	 * @param ressource   la ressource associée au Questionnaire.
	 * @param notions     la liste des notions associées au Questionnaire.
	 * @param chronometre chronometre indiquant si le questionnaire est chronométré ou non.
	 */
	public Questionnaire(Ressource ressource, List<Notion> notions, boolean chronometre) 
	{
		this.ressource   = ressource;
		this.chronometre = chronometre;
		this.notions     = notions;
		this.questions   = new ArrayList<Question>();
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
	 * Retourne la ressource associée au Questionnaire.
	 * 
	 * @return la ressource.
	 */
	public Ressource getRessource()
	{
		return ressource;
	}

	/**
	 * Retourne la liste des notions associée au Questionnaire.
	 * 
	 * @return la liste des notions.
	 */
	public List<Notion> getNotions()
	{
		return notions;
	}

	/**
	 * Retourne l'état du chronométre associée au Questionnaire.
	 * 
	 * @return true si le chronométre est activé, false sinon.
	 */
	public boolean isChronometre()
	{
		return chronometre;
	}

	/**
	 * Retourne la liste des questions associée au Questionnaire.
	 * 
	 * @return la liste des question.
	 */
	public List<Question> getQuestions()
	{
		return questions;
	}



	/*---------*/
	// Setters //
	/*---------*/

	/**
	 * Modifie la ressource associée au Questionnaire.
	 * 
	 * @param  ressource la ressource.
	 * @return           true si la ressource a été modifée, false sinon.
	 */
	public boolean setRessource  (Ressource ressource) 
	{
		if (ressource == null)
		{
			return false;
		}

		this.ressource = ressource;

		return true;
	}

	/**
	 * Modifie la liste des notions associée au Questionnaire. 
	 * 
	 * @param  notions la liste des notions.
	 * @return         true si la liste des notions a été modifiée, false sinon.
	 */
	public boolean setNotions(List<Notion> notions) 
	{
		if (notions == null)
		{
			return false;
		}

		this.notions = notions;

		return true;
	}

	/**
	 * Modifie l'état du chronométre.
	 * True si le chronométre était à false, false sinon.
	 */
	public void setChronometre() 
	{
		this.chronometre = !this.chronometre;
	}

	/**
	 * Modifie la liste des questions associée au Questionnaire.
	 * 
	 * @param  questions la liste des questions.
	 * @return           true si la liste des questions a été mofifiée, false sinon.
	 */
	public boolean setQuestions(List<Question> questions) 
	{
		if (questions == null)
		{
			return false;
		}

		this.questions = questions;

		return true;
	}



	/*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	 * Ajout d'une notion à la liste des notions associées au Questionnaire.
	 * 
	 * @param  notion la notion.
	 * @return        true si la notion à été ajoutée, false sinon.
	 */
	public boolean ajouterNotion(Notion autre)
	{
		if (notions.contains(autre))
		{
			return false;
		}

		this.notions.add(autre);

		return true;
	}

	/**
	 * Supression d'une notion de la liste des notions associées au Questionnaire.
	 * 
	 * @param  notion la notion à supprimer.
	 * @return        true si la notion à été suprimmée, false sinon.
	 */
	public boolean supprimerNotion(Notion autre)
	{
		if (!notions.contains(autre)) return false;

		this.notions.remove(autre);
		return true;
	}

	/**
	 * Ajout de questions à la liste des questions associées au Questionnaire.
	 * 
	 * @param  notion	    la notion.
	 * @param  difficulte   la difficultée.
	 * @param  nbrQuestions le nombre de questions.
	 * @return              true ........, false sinon.
	 */
	public boolean ajouterQuestions(Notion notion, String difficulte, int nbrQuestions)
	{
		for (int i = 0; i < nbrQuestions; i++) {
			//trop de trucs avec fichier rtf
		}

		return true;
	}

	/**
	 * Génère un fichier HTML contenant une structure de base pour un questionnaire.
	 * Le fichier est créé dans le chemin spécifié, dans un dossier approprié si nécessaire.
	 *
	 * @param  filePath                 Le chemin du dossier où le fichier HTML sera créé (non null).
	 * @return                          Une chaîne de caractères représentant le contenu HTML généré.
	 * @throws IllegalArgumentException Si le chemin fourni est null.
	 * @throws IOException              Si une erreur survient lors de la création ou de l'écriture dans le fichier.
	 */
	public String generer(String filePath) 
	{
		String head, body, htmlContent;
		File file, parentDir;

		PrintWriter writer;


		head = "<!DOCTYPE html>\n"                                                                +
		       "<html lang=\"fr\">\n"                                                             +
		       "\t<head>\n"                                                                       +
		       "\t\t<meta charset=\"UTF-8\">\n"                                                   +
		       "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
		       "\t\t<title>Titre de la page</title>\n"                                            +
		       "\t\t<link rel=\"stylesheet\" href=\"style/style.css\">\n"                         +
		       "\t</head>";

		body = "\t<body>\n"                                                             +
		       "\t\t<header>\n"                                                         +
		       "\t\t\t<h1>Question X</h1>\n"                                            +
		       "\t\t</header>\n\n\n"                                                    +
		       "\t\t<main>\n"                                                           +
		       "\t\t\t<p>Intitule</p>\n"                                                +
		       "\t\t</main>\n\n\n"                                                      +
		       "\t\t<footer>"                                                           +
		       "\t\t\t<p>&copy; QCM Builder 2024 Equipe 03. Tous droits réservés</p>\n" +
		       "\t\t</footer>\n"                                                        +
		       "\t\t<script src=\"script/main.js\"></script>\n"                         +
		       "\t</body>\n"                                                            +
		       "</html>";

		htmlContent = head + body;

		try
		{
			file      = new File(filePath, "questionnaire.html");
			parentDir = file.getParentFile();

			if (parentDir != null && !parentDir.exists())
			{
				parentDir.mkdirs();
			}

			writer = new PrintWriter(new FileWriter(file));
			writer.write(htmlContent);

			System.out.println("Le fichier " + file.getAbsolutePath() + " a été créé avec succès !");
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
		}


		return htmlContent;
	}

	/**
     * Retourne une représentation en chaîne de caractères du Questionnaire.
     * 
     * @return une représentation en chaîne de caractères du Questionnaire.
     */
	public String toString()
	{
		return "Questionnaire :\n" +
				"   ressource : " + this.ressource   + "\n" +
				"   questions : " + this.questions   + "\n" + 
				"     notions : " + this.notions     + "\n" +
				" chronometre : " + this.chronometre + "\n" ;
	}

	public static void main(String[] args) 
	{
		Ressource r1;
		Notion    n1, n2;
		List<Notion> l1;
		Questionnaire q1;

		r1 = new Ressource("R3.01");

		n1 = new Notion("Algorithmique");
		n2 = new Notion("Programmation");

		l1 = new ArrayList<Notion>();
		l1.add(n1);

		q1 = new Questionnaire(r1, l1, false);

		System.out.println(q1);

		q1.ajouterNotion(n2);
		System.out.println(q1);

		q1.generer("./");
	}
}
