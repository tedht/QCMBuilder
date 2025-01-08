package metier.entite;


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

import metier.QCMBuilder;
import metier.banque.BanqueQuestions;

import metier.entite.question.Difficulte;
import metier.entite.question.PieceJointe;
import metier.entite.question.Question;

import metier.entite.question.association.*;
import metier.entite.question.elimination.*;
import metier.entite.question.qcm.*;


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
	 * @param banqueQuestions la banque de questions dans laquelle seront puisées les questions.
	 * @param ressource       la ressource associée au Questionnaire.
	 * @param notions         la liste des notions associées au Questionnaire.
	 * @param chronometre     chronometre indiquant si le Questionnaire est chronométré ou non.
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
	public Ressource getRessource()
	{
		return this.ressource;
	}

	/**
	 * Retourne l'état du chronométre associée au Questionnaire.
	 * 
	 * @return true si le chronométre est activé, false sinon.
	 */
	public boolean estChronometree()
	{
		return this.chronometre;
	}

	/**
	 * Retourne la liste des lstQuestions associée au Questionnaire.
	 * 
	 * @return la liste des question.
	 */
	public List<Question> getQuestions()
	{
		return this.lstQuestions;
	}


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
		{
			return false;
		}

		this.ressource = ressource;

		return true;
	}

	/**
	 * Modifie l'état du chronométre.
	 * True si le chronométre était à false, false sinon.
	 */
	public void setChronometre(boolean chronometre) 
	{
		this.chronometre = chronometre;
	}

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
		{
			this.lstQuestions.add(question);
		}
	}

	/**
	 * Mélange la liste des questions associées au Questionnaire.
	 */
	public void shuffleQuestions()
	{
		Collections.shuffle(this.lstQuestions);
	}

	/**
	 * Ajout de lstQuestions à la liste des lstQuestions associées au Questionnaire.
	 * 
	 * @param  notion	    la notion.
	 * @param  difficulte   la difficultée.
	 * @param  nbrQuestions le nombre de lstQuestions.
	 * @return              true ........, false sinon.
	 */
	public boolean ajouterQuestions(Notion notion, Difficulte difficulte, int nbrQuestions)
	{
		for (int i = 0; i < nbrQuestions; i++) 
		{
			//trop de trucs avec fichier rtf
		}

		return true;
	}

	/**
	 * Génère un fichier HTML contenant une structure de base pour un Questionnaire.
	 * Le fichier est créé dans le chemin spécifié, dans un dossier approprié si nécessaire.
	 *
	 * @param  filePath                 Le chemin du dossier où le fichier HTML sera créé (non null).
	 * 
	 * @return                          Un boolean renvoyant true si le contenu HTML est généré.
	 * @throws IllegalArgumentException Si le chemin fourni est null.
	 * @throws IOException              Si une erreur survient lors de la création ou de l'écriture dans le fichier.
	 */

	
	public boolean genererQuestionnaire(String filePath) 
	{
		if (filePath == null) 
		{
			throw new IllegalArgumentException("Le chemin du fichier ne peut pas être null.");
		}

		String dataChrono = chronometre ? "true" : "false";
		String resourceName = ressource.getCode() + " " + ressource.getNom();

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
									
									<p><strong>Nombre de lstQuestions : </strong><span id="question-nombre"></span></p>
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

					<script src="main.js" defer></script>
				</body>
				</html>
				""", dataChrono, lstQuestions.size(), resourceName);

		// Le nom du fichier HTML
		String fullFilePath = filePath + "/questionnaire.html";

		try 
		{
			// Obtenir le répertoire de travail actuel
			String currentDir = System.getProperty("user.dir");

			// Créer le répertoire de destination si nécessaire
			Files.createDirectories(Paths.get(filePath));
			System.out.println("Répertoire créé à l'emplacement : " + filePath);

			// Définir les chemins complets en combinant le répertoire actuel et les sous-dossiers
			String jsPath = currentDir + "/src/metier/entite/srcWeb/main.js";
			String cssPath = currentDir + "/src/metier/entite/srcWeb/style.css";

			// Copier les fichiers JavaScript et CSS avec les nouveaux chemins
			copyFile(jsPath, filePath + "/main.js");
			copyFile(cssPath, filePath + "/style.css");

			// genererDossierComplementaire(filePath);

			genererQuestionnaireAvecJson(filePath, lstQuestions);

			for (Question q : lstQuestions) 
			{
				if(q.getPieceJointe() != null) copyToFichiersComplementaires(filePath, q);
			}

			// Écrire le contenu HTML dans le fichier
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullFilePath))) 
			{
				writer.write(contenuHTML);
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
	 * Génère un fichier JSON contenant les questions du Questionnaire.
	 * 
	 * @param lstQuestions la liste des questions à inclure dans le fichier JSON.
	 * @return la chaîne JSON générée.
	 */
	private String genererQuestionsEnJson(List<Question> lstQuestions) 
	{
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("[");
	
		for (int i = 0; i < lstQuestions.size(); i++) 
		{
			Question question = lstQuestions.get(i);
			String jsonQuestion = convertirQuestionEnJson(question, i+1);
			jsonBuilder.append(jsonQuestion);
			if (i < lstQuestions.size() - 1) 
			{
				jsonBuilder.append(",");
			}
		}
	
		jsonBuilder.append("]");
		return jsonBuilder.toString();
	}

	/**
	 * Génère un fichier JSON contenant les questions du Questionnaire.
	 * 
	 * @param filePath le chemin du dossier où le fichier JSON sera créé.
	 * @param lstQuestions la liste des questions à inclure dans le fichier JSON.
	 * @return true si le fichier JSON a été généré avec succès, false sinon.
	 */
	public boolean genererQuestionnaireAvecJson(String filePath, List<Question> lstQuestions) 
	{
		if (filePath == null) 
		{
			throw new IllegalArgumentException("Le chemin du fichier ne peut pas être null.");
		}
	
		String lstQuestionsJson = genererQuestionsEnJson(lstQuestions);
	
		// Générer le fichier JSON
		String jsonFilePath = filePath + "/questions.json";
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jsonFilePath), StandardCharsets.UTF_8))) {
			writer.write(lstQuestionsJson);
			System.out.println("Fichier JSON généré avec succès à l'emplacement : " + jsonFilePath);
			return true;
		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de la génération du fichier JSON : " + e.getMessage());
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
		System.out.println("Copie du fichier : " + sourcePath + " vers " + destinationPath);
		Path source = Paths.get(sourcePath);
		Path destination = Paths.get(destinationPath);
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Génère un fichier JSON contenant une question.
	 * 
	 * @param question   la question à convertir en JSON.
	 * @param idQuestion l'identifiant de la question.
	 * @return           la chaîne JSON générée.
	 */
	private String convertirQuestionEnJson(Question question, int idQuestion) 
	{
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("\n\t{\n");
	
		appendBasicInfo(jsonBuilder, question, idQuestion);
		appendQuestion(jsonBuilder, question);
		appendFinalInfo(jsonBuilder, question);
		
	
		jsonBuilder.append("\n\t}");
		return jsonBuilder.toString();
	}

	/**
	 * Ajoute les informations de base d'une question au JSON.
	 * 
	 * @param jsonBuilder le StringBuilder contenant le JSON.
	 * @param question    la question à convertir en JSON.
	 * @param idQuestion  l'identifiant de la question.
	 */
	private void appendBasicInfo(StringBuilder jsonBuilder, Question question, int idQuestion) 
	{
		jsonBuilder.append(String.format("\t\t\"id\": %d,\n", idQuestion));

		String type = "" + question.getType();
		if ("QCM".equalsIgnoreCase(type)) 
			if(((QCM) question).estUnique()) 
				type = "choix-unique";
		
		jsonBuilder.append(String.format("\t\t\"type\": \"%s\",\n", type));
		
		jsonBuilder.append(String.format("\t\t\"intitule\": \"%s\",\n", question.getIntitule()));// Gestion de la difficulté
		String difficulte = "" + question.getDifficulte();
		String difficulteAffichee = "";
		switch (difficulte) 
		{
			case "TF" -> { difficulteAffichee = "très-facile"; }
			case "F"  -> { difficulteAffichee = "facile";      }
			case "M"  -> { difficulteAffichee = "moyen";       }
			case "D"  -> { difficulteAffichee = "difficile";   }
			default   -> { difficulteAffichee = "inconnue";    } // En cas de valeur non valide
		}
		jsonBuilder.append(String.format("\t\t\"difficulte\": \"%s\",\n", difficulteAffichee));
	}

	/**
	 * Ajoute les informations spécifiques d'une question au JSON.
	 * 
	 * @param jsonBuilder le StringBuilder contenant le JSON.
	 * @param question    la question à convertir en JSON.
	 */
	private void appendQuestion(StringBuilder jsonBuilder, Question question) 
	{
		if ("Association".equalsIgnoreCase(question.getType().toString())) 
		{
			appendAssociation(jsonBuilder, (Association) question);
		} 
		else if ("Elimination".equalsIgnoreCase(question.getType().toString())) 
		{
			appendElimination(jsonBuilder, (Elimination) question);
		} 
		else 
		{
			appendQCM(jsonBuilder, question);
		}
	}

	/**
	 * Ajoute les informations spécifiques d'une question de type Elimination au JSON.
	 * 
	 * @param jsonBuilder le StringBuilder contenant le JSON.
	 * @param question    la question de type Elimination à convertir en JSON.
	 */
	private void appendElimination(StringBuilder jsonBuilder, Elimination question) 
	{
		List<String> propositionsList = new ArrayList<>();
		List<String> eliminationList  = new ArrayList<>();
		List<String> reponsesList     = new ArrayList<>();
		List<Double> ptsPerdusList    = new ArrayList<>();
		
		// Parcourt toutes les propositions et organise les réponses, éliminations et points perdus
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			PropositionElimination prop = question.getProposition(i);
			propositionsList.add("\"" + prop.getText() + "\"");
			
			if (prop.estReponse()) 
			{
				reponsesList.add("\"" + prop.getText() + "\"");
			} 
			else 
			{
				eliminationList.add("\"" + prop.getText() + "\"");
				ptsPerdusList.add(prop.getNbPtsPerdus());
			}
		}
	
		// Ajouter les propositions
		jsonBuilder.append("\t\t\"propositions\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", propositionsList) + "\n");
		jsonBuilder.append("\t\t],\n");
	
		// Ajouter les réponses
		jsonBuilder.append("\t\t\"reponses\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", reponsesList) + "\n");
		jsonBuilder.append("\t\t],\n");
	
		// Ajouter les éliminations
		jsonBuilder.append("\t\t\"elimination\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", eliminationList) + "\n");
		jsonBuilder.append("\t\t],\n");
	
		// Ajouter les points perdus
		jsonBuilder.append("\t\t\"pointsPerdus\": [\n");
		jsonBuilder.append("\t\t\t" + ptsPerdusList.stream().map(String::valueOf).collect(Collectors.joining(",\n\t\t\t")) + "\n");
		jsonBuilder.append("\t\t],\n");
	}

	/**
	 * Ajoute les informations spécifiques d'une question de type Association au JSON.
	 * 
	 * @param jsonBuilder le StringBuilder contenant le JSON.
	 * @param question    la question de type Association à convertir en JSON.
	 */
	private void appendAssociation(StringBuilder jsonBuilder, Association question) {
		List<String> gaucheList   = new ArrayList<>();
		List<String> droiteList   = new ArrayList<>();
		List<String> reponsesList = new ArrayList<>();
		
		// On parcourt les propositions et on sépare les éléments à gauche et à droite
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			PropositionAssociation prop = (PropositionAssociation) question.getProposition(i);

			gaucheList.add("\"" + prop.getTextGauche() + "\"");  // Texte gauche
			droiteList.add("\"" + prop.getTextDroite() + "\"");  // Texte droite
	
			// Formater les réponses sous forme de tableau de tableaux [gauche, droite]
			String pair = String.format("[\"%s\", \"%s\"]", prop.getTextGauche(), prop.getTextDroite());
			reponsesList.add(pair);
		}
	
		// Ajouter les propositions gauche et droite
		jsonBuilder.append("\t\t\"propositionsGauche\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", gaucheList) + "\n");
		jsonBuilder.append("\t\t],\n");
	
		jsonBuilder.append("\t\t\"propositionsDroite\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", droiteList) + "\n");
		jsonBuilder.append("\t\t],\n");
	
		// Ajouter les réponses sous forme de tableau de tableaux
		jsonBuilder.append("\t\t\"reponses\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", reponsesList) + "\n");
		jsonBuilder.append("\t\t],\n");
	}

	/**
	 * Ajoute les informations finales d'une question de type QCM au JSON.
	 * 
	 * @param jsonBuilder le StringBuilder contenant le JSON.
	 * @param question    la question de type QCM à convertir en JSON.
	 */
	private void appendQCM(StringBuilder jsonBuilder, Question question) 
	{
		// Ajouter les propositions
		jsonBuilder.append("\t\t\"propositions\": [\n");
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			PropositionQCM prop = (PropositionQCM) question.getPropositions().get(i);

			jsonBuilder.append("\t\t\t\"" + prop.getText() + "\"");
			if (i < question.getPropositions().size() - 1) 
			{
				jsonBuilder.append(",");
			}
			jsonBuilder.append("\n");
		}
		jsonBuilder.append("\t\t],\n");

		// Ajouter les réponses
		List<String> reponsesList = new ArrayList<>();
		for (int i = 0; i < question.getPropositions().size(); i++) 
		{
			PropositionQCM prop = (PropositionQCM) question.getPropositions().get(i);
			if (prop.estReponse()) 
			{
				reponsesList.add("\"" + prop.getText() + "\"");
			}
		}

		jsonBuilder.append("\t\t\"reponses\": [\n");
		jsonBuilder.append("\t\t\t" + String.join(",\n\t\t\t", reponsesList));
		jsonBuilder.append("\n\t\t],\n");
	}

	private void appendFinalInfo(StringBuilder jsonBuilder, Question question) {
		jsonBuilder.append(String.format("\t\t\"temps\": %d,\n", question.getTemps()));
		jsonBuilder.append(String.format(Locale.US,"\t\t\"note\": %f,\n", question.getNote()));
		jsonBuilder.append(String.format("\t\t\"feedback\": \"%s\",\n", question.getExplication()));
	
		jsonBuilder.append(String.format("\t\t\"notion\": \"%s\",\n", this.qcmBuilder.getNotion(question.getIdNot()).getNom()));

		String fichier = "";
		if(question.getPieceJointe() != null) 
		{
			PieceJointe PJ = question.getPieceJointe();
			fichier = PJ.getNomPieceJointe() + "." + PJ.getExtension(); 
		}

		jsonBuilder.append(String.format("\t\t\"fichierComplementaire\": \"%s\"\n", fichier));

	}

	/**
	 * Copie un fichier source vers un dossier "fichiersComplementaires" dans le dossier de destination.
	 * 
	 * @param filePath le chemin du dossier de destination.
	 * @param question la question contenant le fichier à copier.
	 */
	private static void copyToFichiersComplementaires(String filePath, Question question) 
	{
		String fichier = question.getPieceJointe().getNomPieceJointe() + "."+ question.getPieceJointe().getExtension();
		String cheminDossierCible = filePath + "/fichiersComplementaires";

		File fichierSource = new File(fichier);
		File dossierCible = new File(cheminDossierCible);  

		// Vérifier que le fichier source existe et est un fichier valide
		if (!fichierSource.exists() || !fichierSource.isFile()) 
		{
			System.err.println("Erreur : Le fichier source n'existe pas ou n'est pas un fichier valide !");
			return;
		}

		// Créer le dossier cible "fichiersComplementaires" s'il n'existe pas déjà
		if (!dossierCible.exists()) 
		{
			boolean dossierCree = dossierCible.mkdir();
			if (!dossierCree) 
			{
				System.err.println("Erreur : Impossible de créer le dossier 'fichiersComplementaires' !");
				return;
			}
		}

		// Déterminer le chemin complet du fichier dans le dossier cible
		File fichierCible = new File(dossierCible, fichierSource.getName());

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
		return "Questionnaire :\n" +
				"   ressource : " + this.ressource   + "\n" +
				"   lstQuestions : " + this.lstQuestions   + "\n" +
				" chronometre : " + this.chronometre + "\n" ;
	}

	/*
	public static void main(String[] args) 
	{
		Ressource     r1, r2;
		Notion        n1, n2, n3;
		List<Notion>  l1;

		QCM q1;
		Association q2;
		Elimination q3;

		BanqueQuestions banqueQuestions;
		Questionnaire quest1;


		r1 = new Ressource("R1.01","Init_Dev");
		r2 = new Ressource("R1.05","BDD");

		n1 = new Notion("Algorithmique",1,"R1.01");
		n2 = new Notion("Programmation",2,"R1.01");
		n3 = new Notion("SQL",3,"R1.05");

		q1 = new QCM(r1.getCode(), n1.getIdNot(), 1, 0.5, 20, Difficulte.DIFFICILE);
		q2 = new Association(r1.getCode(), n2.getIdNot(), 2, 1.0, 30, Difficulte.MOYEN);
		q3 = new Elimination(r2.getCode(), n3.getIdNot(), 3, 4, 40, Difficulte.TRES_FACILE);

		q1.setUnique(true);
		q1.setIntitule("bah choisis la bonne réponse");
		q1.setExplication("bien joué frérot");
		q1.ajouterProposition(new PropositionQCM("prop1", false));
		q1.ajouterProposition(new PropositionQCM("prop2", true));
		q1.ajouterProposition(new PropositionQCM("prop3", false));
		q1.setPieceJointe(new PieceJointe("data/oceans.pdf", "./test"));

		q2.ajouterProposition(new PropositionAssociation("prop1", "prop1"));
		q2.ajouterProposition(new PropositionAssociation("prop2", "prop2"));
		q2.setPieceJointe(new PieceJointe("data/portugal.png", "./test"));

		q3.ajouterProposition(new PropositionElimination("prop1", true, 0, 0));
		q3.ajouterProposition(new PropositionElimination("prop2",false, 1, 1.0));
		q3.ajouterProposition(new PropositionElimination("prop3",false, 2, 1.5));

		l1 = new ArrayList<Notion>();
		l1.add(n1);
		l1.add(n2);

		banqueQuestions = new BanqueQuestions();

		banqueQuestions.ajouterQuestion(q1);
		banqueQuestions.ajouterQuestion(q2);
		banqueQuestions.ajouterQuestion(q3);

	// quest1 = new Questionnaire(banqueQuestions, r1, l1, false);

		//quest1.ajouterNotion(n3);

		quest1.ajouterQuestions(n1, Difficulte.DIFFICILE, 2);
		quest1.ajouterQuestions(n2, Difficulte.MOYEN, 1);
		quest1.ajouterQuestions(n3, Difficulte.TRES_FACILE, 1);

		quest1.genererQuestionnaire("./test");
	}*/
}