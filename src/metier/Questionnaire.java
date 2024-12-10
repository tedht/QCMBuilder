package metier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import metier.Qcm;
import metier.Question;


/** Classe Questionnaire
 * @author Equipe 03
 * @version 1.0 du 09/12/2024
 */
public class Questionnaire 
{
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
		this.questions = new ArrayList<Question>();
	}

	/* Getter */
	public Ressource      getRessource () { return ressource;  }
	public List<Notion>   getNotions   () { return notions;    }
	public boolean        isChronometre() { return chronometre;}
	public List<Question> getQuestions () { return questions;  }

	/* Setter */
	public boolean setQuestions  (List<Question> questions  ) 
		{
			if (questions == null) return false;

			this.questions = questions;
			return true;
		}
	public boolean setChronometre(boolean chronometre) 
	{
		if (this.chronometre == chronometre) return false;
		this.chronometre = chronometre;
		return true;
	}

	public boolean setNotions (List<Notion> notions) 
	{
		if (notions == null) return false;
		this.notions = notions;
		return true;
	}

	public boolean setRessource  (Ressource ressource) 
	{
		if (ressource == null) return false;
		this.ressource = ressource;
		return true;
	}

	public void ajouterNotion(Notion autre)
	{
		if (!notions.contains(autre)) 
		{
			this.notions.add(autre);	
			return;
		}
		System.out.println("Notion déjà présente.");
		
	}

	public void supprimerNotion(Notion autre)
	{
		if (notions.contains(autre)) 
		{
			this.notions.remove(autre);	
			return; 	
		}
		System.out.println("Notion non trouvée.");
	}

	public void ajouterQuestions(Notion notion, String difficulte, int nbrQuestions)
	{
		for (int i = 0; i < nbrQuestions; i++) {
			//trop de trucs avec fichier rtf
		}
	}

	// Méthode utilitaire pour nettoyer une valeur extraite
	private String nettoyerValeur(String valeur)
	{
		return valeur.replaceAll("\\\\[a-zA-Z]+", "").trim();
	}

	// Méthodes pour manipuler les questions
	public void ajouterQuestionQCM(String cheminFichier)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier)))
		{
			String ligne;
			while ((ligne = br.readLine()) != null)
			{
				if (ligne.startsWith("Intitule"))
				{
					String intitule = nettoyerValeur(ligne.split(": ")[1]);
					String explication = nettoyerValeur(br.readLine().split(": ")[1]);
					String difficulteLue = nettoyerValeur(br.readLine().split(": ")[1]);
					String[] propositions = nettoyerValeur(br.readLine().split(": ")[1]).split(";");
					String[] reponses = nettoyerValeur(br.readLine().split(": ")[1]).split(";");
					int temps = Integer.parseInt(nettoyerValeur(br.readLine().split(": ")[1]));
					int note = Integer.parseInt(nettoyerValeur(br.readLine().split(": ")[1]));

					Qcm nouvelleQuestion = new Qcm(questions.size() + 1, intitule, explication, difficulteLue,
							ressource, notions.isEmpty() ? null : notions.get(0), temps, note,
							Arrays.asList(propositions), Arrays.asList(reponses));
					questions.add(nouvelleQuestion);
				}
			}
		} catch (IOException e)
		{
			System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
		}
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
		Ressource r1 = new Ressource("R3.01");
		Notion n1 = new Notion("Algorithmique");
		List<Notion> l1 = new ArrayList<>();
		l1.add(n1);

		Questionnaire q1 = new Questionnaire(r1, l1, false);
		System.out.println(q1);

		Notion n2 = new Notion("Programmation");
		q1.ajouterNotion(n2);
		System.out.println(q1);

		q1.ajouterQuestionQCM("questions_qcm.rtf");
		System.out.println(q1);
	}
}
