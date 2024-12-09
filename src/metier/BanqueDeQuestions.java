package metier;

import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.PrintWriter;

import controleur.Controleur;

/** Classe BanqueDeQuestions
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeQuestions 
{
	/* Attributs */
	private Controleur     ctrl;
	private List<Question> questions;


	/* Constructeur */
	public BanqueDeQuestions(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.questions = new ArrayList<Question>();
	}

	/* Lecture du fichier RTF qui contient les questions */
	public void lireQuestions(String nomFichier)
	{
		Scanner sc;

		String enreg;


		try
		{
			sc = new Scanner( new FileInputStream(nomFichier), "UTF-8");


			while (sc.hasNextLine())
			{
				enreg = sc.nextLine();

				// TODO : Lecture du fichier RTF
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/* Ecriture du fichier RTF qui contient les questions */
	public void sauvegarderQuestions(String nomFichier)
	{
		PrintWriter pw;


		try
		{
			pw = new PrintWriter(nomFichier, "UTF-8");


			for (int cpt = 0 ; cpt < this.questions.size() ; cpt++)
			{
				

				// TODO : Ecriture du fichier RTF
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public boolean ajouterQuestions(Question question)
	{
		if (question == null) return false;

		this.questions.add(question);
		return true;
	}

	public boolean modifierQuestion(int id, String critere, Object modif)
	{
		Question question;


		question = null;
		for (Question q : this.questions)
			if (q.getId() == id)
				question = q;

		if (question == null) return false; // N'a pas trouvé la question

		switch (critere)
		{
			case "difficulte" -> question.setDifficulte((String)    modif);
			case "ressource"  -> question.setRessource ((Ressource) modif);
			case "notion"     -> question.setNotion    ((Notion)    modif);
			case "temps"      -> question.setTemps     ((int)       modif);
			case "note"       -> question.setNote      ((int)       modif);
		}
		return true;
	}

	public boolean supprimerQuestion(int id)
	{
		Question question;


		question = null;
		for (Question q : this.questions)
			if (q.getId() == id)
				question = q;

		if (question == null) return false; // N'a pas trouvé la question

		this.questions.remove(id);
		return true;
	}
}
