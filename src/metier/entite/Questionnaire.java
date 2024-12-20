package metier.entite;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import metier.banque.BanqueQuestions;

import metier.entite.question.Question;
import metier.entite.question.Difficulte;

import metier.entite.question.qcm.QCM;
import metier.entite.question.association.Association;
import metier.entite.question.elimination.Elimination;


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

	private Ressource       ressource;
	private List<Notion>    notions;
	private boolean         chronometre;
	private BanqueQuestions banqueQuestions;
	private List<Question>  questions;


	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe Questionnaire.
	 * 
	 * @param ressource   la ressource associée au Questionnaire.
	 * @param notions     la liste des notions associées au Questionnaire.
	 * @param chronometre chronometre indiquant si le Questionnaire est chronométré ou non.
	 */
	public Questionnaire(BanqueQuestions banqueQuestions, Ressource ressource, List<Notion> notions, boolean chronometre) 
	{
		this.ressource   = ressource;
		this.chronometre = chronometre;
		this.notions     = notions;
		this.banqueQuestions = banqueQuestions;
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
		return this.ressource;
	}

	/**
	 * Retourne la liste des notions associée au Questionnaire.
	 * 
	 * @return la liste des notions.
	 */
	public List<Notion> getNotions()
	{
		return this.notions;
	}

	/**
	 * Retourne l'état du chronométre associée au Questionnaire.
	 * 
	 * @return true si le chronométre est activé, false sinon.
	 */
	public boolean isChronometre()
	{
		return this.chronometre;
	}

	/**
	 * Retourne la liste des questions associée au Questionnaire.
	 * 
	 * @return la liste des question.
	 */
	public List<Question> getQuestions()
	{
		return this.questions;
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
	 * @return              true si l'ajout de question s'est bien passé, false sinon.
	 */
	public boolean ajouterQuestions(Notion notion, Difficulte difficulte, int nbrQuestions)
	{
		Question question;


		if (notion == null || difficulte == null || nbrQuestions <= 0)
		{
			return false;
		}

		for (int i = 0; i < nbrQuestions; i++)
		{
			question = this.banqueQuestions.getQuestion((int) (Math.random() * this.banqueQuestions.getTaille()));

			if (question.getDifficulte() == difficulte)
			{
				if (question.getIdNot() == notion.getIdNot())
				{
					this.questions.add(question);
				}
				else
				{
					i--;
				}
			}
			else
			{
				i--;
			}
		}

		return true;
	}

	/**
	 * Génère un fichier HTML contenant une structure de base pour un Questionnaire.
	 * Le fichier est créé dans le chemin spécifié, dans un dossier approprié si nécessaire.
	 *
	 * @param  cheminDossier            Le chemin du dossier où le fichier HTML sera créé (non null).
	 * @return                          Un boolean renvoyant true si le contenu HTML est généré.
	 * @throws IllegalArgumentException Si le chemin fourni est null.
	 * @throws IOException              Si une erreur survient lors de la création ou de l'écriture dans le fichier.
	 */
	 public boolean genererQuestionnaire(String cheminDossier)
	 {
		String cheminCompletFichier, repCourant;

		String jsPath ;
		String cssPath;

		String dataChrono, nomRessource, sScoreTotal;
		int nbrQuestions, dureeTotale;


		if (cheminDossier == null)
		{
			throw new IllegalArgumentException("Le chemin du fichier ne peut pas être null.");
		}

		dataChrono   = this.chronometre ? "true" : "false";
		nomRessource = this.ressource.getCode() + " " + this.ressource.getNom();
		nbrQuestions = this.questions.size();

		dureeTotale = 0;
		for (Question q : this.questions)
			dureeTotale += q.getTemps();

		int scoreTotal = 0;
		for (Question q : this.questions)
			scoreTotal += q.getNote();

		sScoreTotal = "X/" + scoreTotal;
        // Générer le contenu HTML
        String contenuHTML = String.format("""
                <!DOCTYPE html>
                <html lang="fr">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>QCM-Builder</title>
                    <link rel="stylesheet" href="style.css">
                </head>
                <body>
                    <div id="appli" data-chrono="%s" class="container">
                        <p id="progress-text"><strong>Question 0 sur 0 (0%%)</strong></p>
                        <div id="progress-container">
                            <div id="progress-bar"></div>
                        </div>
                        <!-- Accueil -->
                        <div id="accueil" class="accueil">
                            <header class="header">
                                <h1 id="titre-page">Auto-Évaluation <span id="titre-chrono"></span></h1>
                            </header>
                            <div class="content">
                                <div class="accueil-content">
                                    <p><strong>Ressource :</strong> %s</p>
                                    <p><strong>Notion(s) : </strong><span id="notions"></span></p>
                                    <p><strong>Nombre de questions : </strong><span id="question-nombre">%d</span></p>
                                    <p id="p-temps"><strong>Durée totale prévue : </strong><span id="temps-total">%d</span> seconde(s)</p>
                                    <p id="p-score" style="display: none;">
                                        <strong>Score total : </strong> <span id="score-total">%s</span>
                                    </p>
                                </div>
                                <button id="start-button" class="start-button">Commencer l'évaluation</button>
                            </div>
                        </div>
                        <!-- Questionnaire -->
                        <div id="questionnaire" class="section" style="display: none;">
                            <!-- Contenu du questionnaire -->
                        </div>
                        <div id="feedback-popup" class="feedback-popup">
                            <h2 id="popup-message"></h2>
                            <p id="popup-feedback"></p>
                            <button id="btn-feedback">Fermer</button>
                        </div>
                    </div>
                    <script src="main.js" defer></script>
                </body>
                </html>
                """, dataChrono, nomRessource, nbrQuestions, dureeTotale, sScoreTotal);

		// Le nom du fichier HTML
		cheminCompletFichier = cheminDossier + "/questionnaire.html";

		try
		{
			// Obtenir le répertoire de travail actuel
			repCourant = System.getProperty("user.dir");

			// Créer le répertoire de destination si nécessaire
			Files.createDirectories(Paths.get(cheminDossier));
			System.out.println("Répertoire créé à l'emplacement : " + cheminDossier);

			// Définir les chemins complets en combinant le répertoire actuel et les sous-dossiers
			jsPath  = repCourant + "/src/metier/entite/srcWeb/main.js"  ;
			cssPath = repCourant + "/src/metier/entite/srcWeb/style.css";

			// Copier les fichiers JavaScript et CSS avec les nouveaux chemins
			this.copierFichier(jsPath , cheminDossier + "/main.js"  );
			this.copierFichier(cssPath, cheminDossier + "/style.css");

			// Écrire le contenu HTML dans le fichier
			try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminCompletFichier), "UTF8")))
			{
				writer.print(contenuHTML);
				System.out.println("Fichier HTML généré avec succès à l'emplacement : " + cheminCompletFichier);
			}

			return true;
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors de la génération du fichier HTML ou de la copie des fichiers : " + e.getMessage());
		}

		return false;
	}
	
	// Méthode pour copier un fichier
	private void copierFichier(String cheminSource, String cheminDestination) throws IOException
	{
		Path source, destination;


		System.out.println("Copie du fichier : " + cheminSource + " vers " + cheminDestination);

		source      = Paths.get(cheminSource);
		destination = Paths.get(cheminDestination);
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
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
		Ressource     r1, r2;
		Notion        n1, n2, n3;
		List<Notion>  l1;

		Question q1, q2, q3;

		BanqueQuestions banqueQuestions;
		Questionnaire quest1;

		r1 = new Ressource("R1.01","Init_Dev");
		r2 = new Ressource("R1.05","BDD");

		n1 = new Notion("Algorithmique",1,"R1.01");
		n2 = new Notion("Programmation",2,"R1.01");
		n3 = new Notion("SQL",3,"R1.05");

		q1 = new QCM(r1.getCode(), n1.getIdNot(), 1, 0.5, 20, Difficulte.DIFFICILE);
		q2 = new Association(r1.getCode(), n2.getIdNot(), 2, 1.0, 30, Difficulte.MOYEN);
		q3 = new Elimination(r2.getCode(), n3.getIdNot(), 3, 1.5, 40, Difficulte.TRES_FACILE);

		l1 = new ArrayList<Notion>();
		l1.add(n1);
		l1.add(n2);

		banqueQuestions = new BanqueQuestions();

		banqueQuestions.ajouterQuestion(q1);
		banqueQuestions.ajouterQuestion(q2);
		banqueQuestions.ajouterQuestion(q3);

		quest1 = new Questionnaire(banqueQuestions, r1, l1, false);

		System.out.println(quest1);

		quest1.ajouterNotion(n3);
		System.out.println(quest1);

		// q1 = new QCM(r1.getCode(), n1.getIdNot(), 1, 0.5, 20, Difficulte.DIFFICILE);
		// q2 = new Association(r1.getCode(), n2.getIdNot(), 2, 1.0, 30, Difficulte.MOYEN);
		// q3 = new Elimination(r2.getCode(), n3.getIdNot(), 3, 1.5, 40, Difficulte.TRES_FACILE);


		quest1.ajouterQuestions(n1, Difficulte.DIFFICILE, 2);
		quest1.ajouterQuestions(n2, Difficulte.MOYEN, 2);
		quest1.ajouterQuestions(n3, Difficulte.TRES_FACILE, 2);

		quest1.genererQuestionnaire("./test");
	}
}