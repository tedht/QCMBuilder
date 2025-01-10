package QCMBuilder.metier.entite;


import java.io.*;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import java.util.stream.Collectors;

import QCMBuilder.metier.QCMBuilder;

import QCMBuilder.metier.entite.question.PieceJointe;
import QCMBuilder.metier.entite.question.Question;

import QCMBuilder.metier.entite.question.association.*;
import QCMBuilder.metier.entite.question.elimination.*;
import QCMBuilder.metier.entite.question.qcm.*;


/** 
 * Classe Questionnaire qui correspond à un questionnaire (une évaluation).
 * 
 * @author Equipe 03
 * @version 1.0 du 09/12/2024
 */
public class Questionnaire
{
	/*-----------*/
	/* Attributs */
	/*-----------*/

	private QCMBuilder     qcmBuilder;
	private Ressource      ressource;
	private boolean        chronometre;
	private List<Question> lstQuestions;


	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe Questionnaire.
	 * 
	 * @param qcmBuilder  le QCMBuilder.
	 * @param ressource   la ressource associée au Questionnaire.
	 * @param chronometre l'état du chronométre associé au Questionnaire.
	 */
	public Questionnaire(QCMBuilder qcmBuilder, Ressource ressource, boolean chronometre) 
	{
		this.qcmBuilder   = qcmBuilder;
		this.ressource    = ressource;
		this.chronometre  = chronometre;
		this.lstQuestions = new ArrayList<Question>();
	}


	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne la ressource associée au Questionnaire.
	 * 
	 * @return la ressource.
	 */
	public Ressource getRessource() { return this.ressource; }

	/**
	 * Retourne l'état du chronométre associée au Questionnaire.
	 * 
	 * @return true si le chronométre est activé, false sinon.
	 */
	public boolean estChronometre() { return this.chronometre; }

	/**
	 * Retourne la liste des lstQuestions associée au Questionnaire.
	 * 
	 * @return la liste des question.
	 */
	public List<Question> getQuestions() { return this.lstQuestions; }


	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Modifie la ressource associée au Questionnaire.
	 * 
	 * @param  ressource la ressource.
	 * @return           true si la ressource a été modifée, false sinon.
	 */
	public boolean setRessource(Ressource ressource)
	{
		if (ressource == null)
			return false;

		this.ressource = ressource;

		return true;
	}

	/**
	 * Modifie l'état du chronométre.
	 * True si le chronométre était à false, false sinon.
	 */
	public void setChronometre(boolean chronometre) { this.chronometre = chronometre; }

	/*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	 * Ajout d'une question à la liste des questions associées au Questionnaire.
	 * 
	 * @param question la question à ajouter.
	 */
	public void ajouterQuestion(Question question)
	{
		if(question != null)
			this.lstQuestions.add(question);
	}

	/**
	 * Mélange la liste des questions associées au Questionnaire.
	 */
	public void shuffleQuestions() { Collections.shuffle(this.lstQuestions); }

	/**
	 * Génère un fichier HTML contenant une structure de base pour un Questionnaire.
	 * Le fichier est créé dans le chemin spécifié, dans un dossier approprié si nécessaire.
	 *
	 * @param  filePath                 Le chemin du dossier où le fichier HTML sera créé (non null).
	 * 
	 * @return                          Un boolean renvoyant true si le contenu HTML est généré.
	 * @throws IllegalArgumentException Si le chemin fourni est null.
	 */
	public boolean genererQuestionnaire(String filePath) throws IllegalArgumentException
	{
		String dataChrono, resourceName;
		String contenuHTML;
		String fullFilePath;
		String currentDir;

		String jsPath, cssPath;
		


		if (filePath == null) 
			throw new IllegalArgumentException("Le chemin du fichier ne peut pas être null.");

		dataChrono   = chronometre ? "true" : "false";
		resourceName = ressource.getCode() + " " + ressource.getNom();

		// Générer le contenu HTML
		contenuHTML = String.format("""
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
						<p id="progress-text"><strong>Question 0 sur %d (0%%)</strong></p>
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
									
									<p><strong>Nombre de questions : </strong><span id="question-nombre"></span></p>
									<p id="p-temps"><strong>Durée totale prévue : </strong><span id="temps-total"></span></p>
									<p id="p-score"style="display: none;">
										<strong>Score total : </strong> <span id="score-total"></span>
									</p>
								</div>
								<!--AJOUTER UN DIV FEEDBACK EN DISPLAY NONE POUR LA FIN DU QUESTIONNAIRE SI IL Y EN A UN-->
								<button id="start-button" class="start-button">Commencer l'évaluation</button>
							</div>
					

						</div>

						<!-- Questionnaire -->
						<div id="questionnaire" class="section" style="display: none;">
							<div class="question-header">
								<span id= "difficulte"></span>
								<div class="question-header-center">
									<span id="question-number"></span>
									<span id="question-notion"></span>
								</div>
								<span id="question-time"></span>
							</div>
							<button id="btn-info" class="nav-button" style="display: none;">+ d'info</button>
							<div class="question-title">
								<span id="question-title"></span>
							</div>
							<div class="question-section">
								<!-- Les réponses possibles seront insérées ici -->
							</div>
							<div class="question-footer">
								<button id="btn-precedent" class="nav-button">Précédent</button>
								<button id="btn-feedback" class="nav-button">Feedback</button>
								<button id="btn-valider" class="nav-button validate-button">Valider</button>
								<button id="btn-suivant" class="nav-button">Suivant</button>
							</div>
						</div>

						<div id="feedback-popup" class="feedback-popup">
							<h2 id="popup-message"></h2>
							<p id="popup-feedback"></p>
							<button id="btn-feedback">Fermer</button>
						</div>
					</div>
					
					<script src="questions.js"></script>
					<script src="main.js" defer></script>
				</body>
				</html>
				""", dataChrono, lstQuestions.size(), resourceName);

		// Le nom du fichier HTML
		fullFilePath = filePath + "/questionnaire.html";

		try 
		{
			// Obtenir le répertoire de travail actuel
			currentDir = System.getProperty("user.dir");

			// Créer le répertoire de destination si nécessaire
			Files.createDirectories(Paths.get(filePath));
			System.out.println("Répertoire créé à l'emplacement : " + filePath);

			// Définir les chemins complets en combinant le répertoire actuel et les sous-dossiers
			jsPath  = currentDir + "/src/QCMBuilder/metier/entite/srcWeb/main.js";
			cssPath = currentDir + "/src/QCMBuilder/metier/entite/srcWeb/style.css";

			// Copier les fichiers JavaScript et CSS avec les nouveaux chemins
			copyFile(jsPath , filePath + "/main.js"  );
			copyFile(cssPath, filePath + "/style.css");

			// genererDossierComplementaire(filePath);

			genererQuestionnaireEnJs(filePath, lstQuestions);

			for (Question q : lstQuestions) 
				if(q.getPieceJointe() != null) copyToFichiersComplementaires(filePath, q);

			// Écrire le contenu HTML dans le fichier
			try (PrintWriter writer = new PrintWriter(new FileWriter(fullFilePath))) 
			{
				writer.print(contenuHTML);
				System.out.println("Fichier HTML généré avec succès à l'emplacement : " + fullFilePath);
			}


			return true;
		} 
		catch (IOException e)
		{
			System.err.println("Erreur lors de la génération du fichier HTML ou de la copie des fichiers : " + e.getMessage());
		}

		return false;
	}
	
	/**
	 * Génère un fichier JS contenant les questions du Questionnaire.
	 * 
	 * @param lstQuestions la liste des questions à inclure dans le fichier JS.
	 * @return la chaîne JS générée.
	 */
	private String genererQuestionsEnJs(List<Question> lstQuestions) {
		StringBuilder jsBuilder;
	
		Question question;
		String jsQuestion;
	
		jsBuilder = new StringBuilder();
		jsBuilder.append("const questions = [");
	
		for (int i = 0; i < lstQuestions.size(); i++) {
			question = lstQuestions.get(i);
	
			jsQuestion = convertirQuestionEnJs(question, i + 1);
	
			jsBuilder.append(jsQuestion);
	
			if (i < lstQuestions.size() - 1) 
				jsBuilder.append(",");
		}
	
		jsBuilder.append("];"); // Fermer la déclaration JS
		return jsBuilder.toString();
	}
	

	/**
	 * Génère un fichier JS contenant les questions du Questionnaire.
	 * 
	 * @param filePath le chemin du dossier où le fichier JS sera créé.
	 * @param lstQuestions la liste des questions à inclure dans le fichier JS.
	 * @return true si le fichier JS a été généré avec succès, false sinon.
	 */
	public boolean genererQuestionnaireEnJs(String filePath, List<Question> lstQuestions) throws IllegalArgumentException {
		String lstQuestionsJs;
		String jsFilePath;
	
		if (filePath == null) 
			throw new IllegalArgumentException("Le chemin du fichier ne peut pas être null.");
	
		lstQuestionsJs = genererQuestionsEnJs(lstQuestions);
	
		// Générer le fichier JS
		jsFilePath = filePath + "/questions.js";
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(jsFilePath), StandardCharsets.UTF_8))) {
			writer.print(lstQuestionsJs);
			System.out.println("Fichier JS généré avec succès à l'emplacement : " + jsFilePath);
			return true;
		} catch (IOException e) {
			System.err.println("Erreur lors de la génération du fichier JS : " + e.getMessage());
			return false;
		}
	}
	

	/**
	 * Copie un fichier source vers un fichier de destination.
	 * 
	 * @param sourcePath      le chemin du fichier source.
	 * @param destinationPath le chemin du fichier de destination.
	 * @throws IOException    si une erreur survient lors de la copie du fichier.
	 */
	private void copyFile(String sourcePath, String destinationPath) throws IOException 
	{
		Path source, destination;


		System.out.println("Copie du fichier : " + sourcePath + " vers " + destinationPath);

		source      = Paths.get(sourcePath);
		destination = Paths.get(destinationPath);

		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Génère un fichier JS contenant une question.
	 * 
	 * @param question   la question à convertir en JS.
	 * @param idQuestion l'identifiant de la question.
	 * @return           la chaîne JS générée.
	 */
	private String convertirQuestionEnJs(Question question, int idQuestion) 
	{
		StringBuilder jsBuilder;


		jsBuilder = new StringBuilder();
		jsBuilder.append("\n\t{\n");
	
		appendBasicInfo(jsBuilder, question, idQuestion);
		appendQuestion (jsBuilder, question            );
		appendFinalInfo(jsBuilder, question            );

		jsBuilder.append("\n\t}");

		return jsBuilder.toString();
	}


	/**
     * Formate une chaîne pour JavaScript en supprimant les sauts de ligne (\n)
     * et en échappant les guillemets (").
     *
     * @param input La chaîne d'entrée à formater.
     * @return La chaîne formatée.
     */
    public static String formatForJavaScript(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replace("\n", " ")        // Remplace les sauts de ligne par un espace
                .replace("\"", "\\\"");    // Échappe les guillemets
    }

	/**
	 * Ajoute les informations de base d'une question au JS.
	 * 
	 * @param jsBuilder le StringBuilder contenant le JS.
	 * @param question    la question à convertir en JS.
	 * @param idQuestion  l'identifiant de la question.
	 */
	private void appendBasicInfo(StringBuilder jsBuilder, Question question, int idQuestion) 
	{
		String type;
		String difficulte, difficulteAffichee;


		jsBuilder.append(String.format("\t\t\"id\": %d,\n", idQuestion));

		type = "" + question.getType();
		if ("QCM".equalsIgnoreCase(type)) 
			if(((QCM) question).estUnique()) 
				type = "choix-unique";

		jsBuilder.append(String.format("\t\t\"type\": \"%s\",\n", type));
		
		jsBuilder.append(String.format("\t\t\"intitule\": \"%s\",\n", formatForJavaScript(question.getIntitule())));

		// Gestion de la difficulté
		difficulte = "" + question.getDifficulte();
		difficulteAffichee = "";
		switch (difficulte) 
		{
			case "TF" -> { difficulteAffichee = "tres-facile"; }
			case "F"  -> { difficulteAffichee = "facile";      }
			case "M"  -> { difficulteAffichee = "moyen";       }
			case "D"  -> { difficulteAffichee = "difficile";   }
			default   -> { difficulteAffichee = "inconnue";    } // En cas de valeur non valide
		}
		jsBuilder.append(String.format("\t\t\"difficulte\": \"%s\",\n", difficulteAffichee));
	}

	/**
	 * Ajoute les informations spécifiques d'une question au JS.
	 * 
	 * @param jsBuilder le StringBuilder contenant le JS.
	 * @param question    la question à convertir en JS.
	 */
	private void appendQuestion(StringBuilder jsBuilder, Question question) 
	{
		if ("Association".equalsIgnoreCase(question.getType().toString())) 
			this.appendAssociation(jsBuilder, (Association) question);
		else
			if ("Elimination".equalsIgnoreCase(question.getType().toString())) 
				this.appendElimination(jsBuilder, (Elimination) question);
		else
			this.appendQCM(jsBuilder, question);
	}

	/**
	 * Ajoute les informations spécifiques d'une question de type Elimination au JS.
	 * 
	 * @param jsBuilder le StringBuilder contenant le JS.
	 * @param question    la question de type Elimination à convertir en JS.
	 */
	private void appendElimination(StringBuilder jsBuilder, Elimination question) 
	{
		List<String> propositionsList;
		List<String> eliminationList ;
		List<String> reponsesList    ;
		List<Double> ptsPerdusList   ;

		PropositionElimination prop;


		propositionsList = new ArrayList<String>();
		eliminationList  = new ArrayList<String>();
		reponsesList     = new ArrayList<String>();
		ptsPerdusList    = new ArrayList<Double>();

		// Parcourt toutes les propositions et organise les réponses, éliminations et points perdus
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			prop = question.getProposition(i);

			propositionsList   .add("\"" + formatForJavaScript(prop.getText()) + "\"");
			
			if (prop.estReponse()) 
			{
				reponsesList   .add("\"" + formatForJavaScript(prop.getText()) + "\"");
			} 
			else 
			{
				eliminationList.add("\"" + formatForJavaScript(prop.getText()) + "\"");
				ptsPerdusList  .add(prop.getNbPtsPerdus());
			}
		}
	
		// Ajouter les propositions
		jsBuilder.append("\t\t\"propositions\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", propositionsList) + "\n");
		jsBuilder.append("\t\t],\n");
	
		// Ajouter les réponses
		jsBuilder.append("\t\t\"reponses\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", reponsesList) + "\n");
		jsBuilder.append("\t\t],\n");
	
		// Ajouter les éliminations
		jsBuilder.append("\t\t\"elimination\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", eliminationList) + "\n");
		jsBuilder.append("\t\t],\n");
	
		// Ajouter les points perdus
		jsBuilder.append("\t\t\"pointsPerdus\": [\n");
		jsBuilder.append("\t\t\t" + ptsPerdusList.stream().map(String::valueOf).collect(Collectors.joining(",\n\t\t\t")) + "\n");
		jsBuilder.append("\t\t],\n");
	}

	/**
	 * Ajoute les informations spécifiques d'une question de type Association au JS.
	 * 
	 * @param jsBuilder le StringBuilder contenant le JS.
	 * @param question    la question de type Association à convertir en JS.
	 */
	private void appendAssociation(StringBuilder jsBuilder, Association question)
	{
		List<String> gaucheList  ;
		List<String> droiteList  ;
		List<String> reponsesList;

		String pair;

		PropositionAssociation prop;


		gaucheList   = new ArrayList<String>();
		droiteList   = new ArrayList<String>();
		reponsesList = new ArrayList<String>();
		
		// On parcourt les propositions et on sépare les éléments à gauche et à droite
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			prop = (PropositionAssociation) question.getProposition(i);

			gaucheList.add("\"" + formatForJavaScript(prop.getTextGauche()) + "\"");  // Texte gauche
			droiteList.add("\"" + formatForJavaScript(prop.getTextDroite()) + "\"");  // Texte droite
	
			// Formater les réponses sous forme de tableau de tableaux [gauche, droite]
			pair = String.format("[\"%s\", \"%s\"]", formatForJavaScript(prop.getTextGauche()), formatForJavaScript(prop.getTextDroite()));
			reponsesList.add(pair);
		}
	
		// Ajouter les propositions gauche et droite
		jsBuilder.append("\t\t\"propositionsGauche\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", gaucheList) + "\n");
		jsBuilder.append("\t\t],\n");
	
		jsBuilder.append("\t\t\"propositionsDroite\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", droiteList) + "\n");
		jsBuilder.append("\t\t],\n");
	
		// Ajouter les réponses sous forme de tableau de tableaux
		jsBuilder.append("\t\t\"reponses\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", reponsesList) + "\n");
		jsBuilder.append("\t\t],\n");
	}

	/**
	 * Ajoute les informations finales d'une question de type QCM au JS.
	 * 
	 * @param jsBuilder le StringBuilder contenant le JS.
	 * @param question    la question de type QCM à convertir en JS.
	 */
	private void appendQCM(StringBuilder jsBuilder, Question question) 
	{
		PropositionQCM prop;

		List<String> reponsesList;


		// Ajouter les propositions
		jsBuilder.append("\t\t\"propositions\": [\n");
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			prop = (PropositionQCM) question.getPropositions().get(i);

			jsBuilder.append("\t\t\t\"" + formatForJavaScript(prop.getText()) + "\"");

			if (i < question.getPropositions().size() - 1)
				jsBuilder.append(",");

			jsBuilder.append("\n");
		}
		jsBuilder.append("\t\t],\n");

		// Ajouter les réponses
		reponsesList = new ArrayList<String>();
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			prop = (PropositionQCM) question.getPropositions().get(i);

			if (prop.estReponse())
				reponsesList.add("\"" + formatForJavaScript(prop.getText()) + "\"");
		}

		jsBuilder.append("\t\t\"reponses\": [\n");
		jsBuilder.append("\t\t\t" + String.join(",\n\t\t\t", reponsesList));
		jsBuilder.append("\n\t\t],\n");
	}

	/**
	 * Ajoute les informations finales d'une question au JS.
	 * 
	 * @param jsBuilder le StringBuilder contenant le JS.
	 * @param question    la question à convertir en JS.
	 */
	private void appendFinalInfo(StringBuilder jsBuilder, Question question)
	{
		String      fichier;
		PieceJointe PJ;


		jsBuilder.append(String.format(          "\t\t\"temps\": %d,\n"       , question.getTemps      ()));
		jsBuilder.append(String.format(Locale.US,"\t\t\"note\": %f,\n"        , question.getNote       ()));
		jsBuilder.append(String.format(          "\t\t\"feedback\": \"%s\",\n", formatForJavaScript(question.getExplication())));
	
		jsBuilder.append(String.format("\t\t\"notion\": \"%s\",\n"  , this.qcmBuilder.getNotion(question.getIdNot()).getNom()));

		fichier = "";
		if(question.getPieceJointe() != null)
		{
			PJ      = question.getPieceJointe();
			fichier = PJ      .getNomPieceJointe() + "." + PJ.getExtension(); 
		}

		jsBuilder.append(String.format("\t\t\"fichierComplementaire\": \"%s\"\n", fichier));

	}

	/**
	 * Copie un fichier source vers un dossier "fichiersComplementaires" dans le dossier de destination.
	 * 
	 * @param filePath le chemin du dossier de destination.
	 * @param question la question contenant le fichier à copier.
	 */
	private static void copyToFichiersComplementaires(String filePath, Question question) 
	{
		String cheminFichierSource, cheminDossierCible;
		File fichierSource, dossierCible, fichierCible;
		boolean dossierCree;


		cheminDossierCible = filePath + "/fichiersComplementaires";

		cheminFichierSource = question.getPieceJointe().getCheminFic();


		fichierSource = new File(cheminFichierSource);           
		dossierCible  = new File(cheminDossierCible );

		System.out.println(fichierSource);
		// Vérifier que le fichier source existe et est un fichier valide
		if (!fichierSource.exists() || !fichierSource.isFile()) 
		{
			System.err.println("Erreur : Le fichier source n'existe pas ou n'est pas un fichier valide !");
			return;
		}

		// Créer le dossier cible "fichiersComplementaires" s'il n'existe pas déjà
		if (!dossierCible.exists()) 
		{
			dossierCree = dossierCible.mkdir();
			if (!dossierCree) 
			{
				System.err.println("Erreur : Impossible de créer le dossier 'fichiersComplementaires' !");
				return;
			}
		}

		// Déterminer le chemin complet du fichier dans le dossier cible
		fichierCible = new File(dossierCible, fichierSource.getName());

		try 
		{
			// Copier le fichier source vers le dossier cible
			Files.copy(fichierSource.toPath(), fichierCible.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Succès : Le fichier a été copié vers " + fichierCible.getPath());
		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de la copie du fichier : " + e.getMessage());
		}
	}
	
	/**
	 * Retourne une représentation en chaîne de caractères du Questionnaire.
	 * 
	 * @return une représentation en chaîne de caractères du Questionnaire.
	 */
	public String toString()
	{
		return "Questionnaire :\n"   +
				"   ressource : "    + this.ressource    + "\n" +
				"   lstQuestions : " + this.lstQuestions + "\n" +
				" chronometre : "    + this.chronometre  + "\n"   ;
	}
}