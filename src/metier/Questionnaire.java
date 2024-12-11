package metier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;


/** Classe Questionnaire
 * @author Equipe 03
 * @version 1.0 du 09/12/2024
 */
public class Questionnaire
{
	/* Attributs */
	private Ressource      ressource;
	private List<Notion>   notions;
	private boolean        chronometre;
	private List<Question> questions;

	/* Constructeur */
	public Questionnaire(Ressource ressource, List<Notion> notions , boolean chronometre) 
	{
		this.ressource   = ressource;
		this.chronometre = chronometre;
		this.notions     = notions;
		this.questions   = new ArrayList<Question>();
	}

	/* Getters */
	public Ressource      getRessource () { return ressource;  }
	public List<Notion>   getNotions   () { return notions;    }
	public boolean        isChronometre() { return chronometre;}
	public List<Question> getQuestions () { return questions;  }

	/* Setters */
	public boolean setRessource  (Ressource ressource) 
	{
		if (ressource == null) return false;

		this.ressource = ressource;
		return true;
	}

	public boolean setNotions (List<Notion> notions) 
	{
		if (notions == null) return false;

		this.notions = notions;
		return true;
	}

	public void setChronometre() 
	{
		this.chronometre = !this.chronometre;
	}

	public boolean setQuestions  (List<Question> questions  ) 
	{
		if (questions == null) return false;

		this.questions = questions;
		return true;
	}

	public boolean ajouterNotion(Notion autre)
	{
		if (notions.contains(autre)) return false;

		this.notions.add(autre);
		return true;
	}

	public boolean supprimerNotion(Notion autre)
	{
		if (!notions.contains(autre)) return false;

		this.notions.remove(autre);
		return true;
	}

	public boolean ajouterQuestions(Notion notion, String difficulte, int nbrQuestions)
	{
		for (int i = 0; i < nbrQuestions; i++) {
			//trop de trucs avec fichier rtf
		}

		return true;
	}


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