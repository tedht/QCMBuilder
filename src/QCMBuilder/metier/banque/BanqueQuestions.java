package QCMBuilder.metier.banque;


import java.util.List;
import java.util.ArrayList;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.FileNotFoundException;

import QCMBuilder.metier.entite.question.*;

import QCMBuilder.metier.entite.question.association.*;
import QCMBuilder.metier.entite.question.qcm.*;
import QCMBuilder.metier.entite.question.elimination.*;


/**
 * Classe BanqueDeQuestions qui gère les questions (hérite de Banque).
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueQuestions extends Banque
{
	/*-----------*/
	/* Attributs */
	/*-----------*/

	private List<Question> lstQuestions;
	private Queue<Integer> fileIdUtilisable;

	private String         cheminFicCSV;

	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe BanqueQuestions.
	 */
	public BanqueQuestions()
	{
		this.lstQuestions     = new ArrayList<Question>();
		this.fileIdUtilisable = new LinkedList<Integer>();

		this.cheminFicCSV     = "data/questions.csv";

		this.lireQuestions(this.cheminFicCSV);
	}



	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le chemin du fichier de la banque de questions.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getCheminFicCSV()
	{
		return this.cheminFicCSV;
	}

	/** 
	 * Retourne la liste des questions.
	 * 
	 * @return la liste des questions.
	 */
	public List<Question> getQuestions()
	{
		return this.lstQuestions;
	}

	/**
	 * Retourne la liste des questions d'une ressource à partir de son code.
	 * 
	 * @param codeRes le code de la ressource.
	 * @return        la liste des questions de la ressource.
	 */
	public List<Question> getQuestions(String codeRes) 
	{
		List<Question> lstQuestions;


		lstQuestions = new ArrayList<Question>();

		for(Question question : this.lstQuestions)
		{
			if(question != null && question.getCodeRes().equals(codeRes))
				lstQuestions.add(question);
		}

		return lstQuestions;
	}

	/**
	 * Retourne la liste des questions d'une ressource et d'une notion à partir de son code et de son identifiant.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param idNot   l'identifiant de la notion.
	 * @return        la liste des questions de la ressource et de la notion.
	 */
	public List<Question> getQuestions(String codeRes, int idNot) 
	{
		List<Question> lstQuestions;


		lstQuestions = new ArrayList<Question>();

		for(Question question : this.lstQuestions)
		{
			if(question != null && question.getCodeRes().equals(codeRes) && question.getIdNot() == idNot)
			{
				lstQuestions.add(question);
			}
		}
		return lstQuestions;
	}

	/**
	 * Retourne la liste des questions d'une ressource, d'une notion et d'une difficulté à partir de son code, de son identifiant et de sa difficulté.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param idNot   l'identifiant de la notion.
	 * @param diff    la difficulté de la question.
	 * @return        la liste des questions de la ressource, de la notion et de la difficulté.
	 */
	public List<Question> getQuestions(String codeRes, int idNot, Difficulte diff) 
	{
		List<Question> lstQuestions;

		lstQuestions = new ArrayList<Question>();

		for(Question question : this.lstQuestions)
		{
			if(   question != null 
			   && question.getCodeRes   ().equals(codeRes) 
			   && question.getIdNot     () == idNot
			   && question.getDifficulte() == diff)
			{
				lstQuestions.add(question);
			}
		}
		return lstQuestions;
	}

	/**
	 * Retourne une question de la banque de questions à partir de son identifiant.
	 * 
	 * @param idQst l'identifiant de la question.
	 * @return      la question.
	 */
	public Question getQuestion(int idQst)
	{
		if (idQst < 0 || idQst >= this.lstQuestions.size())
			return null;

		return this.lstQuestions.get(idQst);
	}

	/**
	 * Retourne la taille de la liste des questions
	 * 
	 * @return la taille de la liste des questions
	 */
	public int getTaille()
	{
		return this.lstQuestions.size();
	}



	/*-----------------*/
	// Autres méthodes //
	/*-----------------*/

	/**
	* Lecture du fichier CSV qui contient les questions.
	*
	* @param cheminFicCSV le chemin du fichier CSV.
	* @see   Scanner   pour lire le fichier.
	*/
	private void lireQuestions(String cheminFicCSV)
	{
		Scanner scEnreg, scDonnees, scFicTxt;

		String        cheminDirQst;
		int           cpt;

		String        codeRes;
		int           idNot;
		int           idQst;
		Difficulte    difficulte;
		TypeQuestion  typeQuestion;
		int           temps;
		double        note;
		Question      question;

		String        intitule;
		String        explication;

		String        ficComp;
		PieceJointe   pj;

		int           nbProp;
		String        textProp;

		String        textGauche, textDroite;

		int           ordreElim;
		double        nbPtsPerdus;

		boolean       estReponse;
		int           cptReponse;

		StringBuilder stringBuilder;
		
		pj = null;

		try
		{
			// Scanner appliqué sur le fichier CSV
			scEnreg = new Scanner(new FileInputStream(cheminFicCSV), "UTF8");

			// Pour passer l'entête
			if(scEnreg.hasNextLine())
				scEnreg.nextLine();

			cpt = 0;
			while (scEnreg.hasNextLine())
			{
				// Scanner des données appliqué sur la ligne du CSV utilisant le délimiteur "\t"
				scDonnees = new Scanner(scEnreg.nextLine());
				scDonnees.useDelimiter("\t");

				// Récupération des données
				codeRes	     = scDonnees.next();
				idNot        = scDonnees.nextInt();
				idQst        = scDonnees.nextInt();
				difficulte   = Difficulte.fromInt(scDonnees.nextInt());
				typeQuestion = TypeQuestion.fromInt(scDonnees.nextInt());
				temps        = scDonnees.nextInt();
				note         = Double.parseDouble(scDonnees.next());
				nbProp       = scDonnees.nextInt();
				ficComp      = scDonnees.next();

				cheminDirQst = "data/ressources/" + codeRes + "/notion" + idNot + "/question" + idQst;

				// Scanner pour lire le fichier TXT de l'intitulé (intitule.txt)
				scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/intitule.txt"), "UTF8");

				// Récupération de l'intitulé
				stringBuilder = new StringBuilder();
				while(scFicTxt.hasNextLine())
				{
					stringBuilder.append(scFicTxt.nextLine()).append('\n');
				}
				intitule = stringBuilder.toString().substring(0, stringBuilder.length()-1);
				scFicTxt.close();

				// Scanner pour lire le fichier TXT de l'explication (explication.txt)
				scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/explication.txt"), "UTF8");

				// Récupération de l'explication
				stringBuilder.setLength(0);
				while(scFicTxt.hasNextLine())
				{
					stringBuilder.append(scFicTxt.nextLine()).append('\n');
				}
				explication = stringBuilder.toString().substring(0, stringBuilder.length()-1);
				scFicTxt.close();


				while(cpt < idQst)
				{
					this.lstQuestions.add(null);
					this.fileIdUtilisable.offer(cpt++);
				}

				switch (typeQuestion)
				{
					case QCM ->
					{
						question = new QCM(codeRes, idNot, idQst, note, temps, difficulte);
						
						cptReponse = 0;
						for(int i = 1; i <= nbProp; i++)
						{
							// Scanner pour lire le fichier TXT de la proposition (propX.txt)
							scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/propositions/prop"+i+".txt"), "UTF8");

							// Récupération de la proposition
							estReponse = scFicTxt.nextLine().charAt(0) == 'V';

							if(estReponse)
								cptReponse++;

							stringBuilder.setLength(0);
							while(scFicTxt.hasNextLine())
							{
								stringBuilder.append(scFicTxt.nextLine()).append('\n');
							}
							textProp = stringBuilder.toString().substring(0, stringBuilder.length()-1);

							((QCM) question).ajouterProposition(new PropositionQCM(textProp, estReponse));
							scFicTxt.close();
						}

						if(new File(cheminDirQst+"/propositions/unique.txt").exists())
						{
							scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/propositions/unique.txt"), "UTF8");
							((QCM) question).setUnique(scFicTxt.nextBoolean());
							scFicTxt.close();
						}
						else
						{
							((QCM) question).setUnique(cptReponse == 1);
						}
					}
					case ASSOCIATION ->
					{
						question = new Association(codeRes, idNot, idQst, note, temps, difficulte);
						
						for(int i = 1; i <= nbProp; i++)
						{
							// Scanner pour lire le fichier TXT de la proposition de gauche (propGX.txt)
							scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/propositions/propG"+i+".txt"), "UTF8");

							// Récupération de la proposition de gauche
							stringBuilder.setLength(0);
							while(scFicTxt.hasNextLine())
							{
								stringBuilder.append(scFicTxt.nextLine()).append('\n');
							}
							textGauche = stringBuilder.toString().substring(0, stringBuilder.length()-1);

							// Scanner pour lire le fichier TXT de la proposition de droite (propDX.txt)
							scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/propositions/propD"+i+".txt"), "UTF8");

							// Récupération de la proposition de droite
							stringBuilder.setLength(0);
							while(scFicTxt.hasNextLine())
							{
								stringBuilder.append(scFicTxt.nextLine()).append('\n');
							}
							textDroite = stringBuilder.toString().substring(0, stringBuilder.length()-1);

							((Association) question).ajouterProposition(new PropositionAssociation(textGauche, textDroite));
						}
						scFicTxt.close();
					}
					case ELIMINATION ->
					{
						question = new Elimination(codeRes, idNot, idQst, note, temps, difficulte);
						
						for(int i = 1 ; i <= nbProp ; i++)
						{
							// Scanner pour lire le fichier TXT de la proposition (propX.txt)
							scFicTxt = new Scanner(new FileInputStream(cheminDirQst+"/propositions/prop"+i+".txt"), "UTF8");

							// Récupération de la proposition
							if(scFicTxt.hasNextLine())
							{
								estReponse  = scFicTxt.nextLine().charAt(0) == 'V'; 
								ordreElim   = scFicTxt.nextInt(); scFicTxt.nextLine();
								nbPtsPerdus = Double.parseDouble(scFicTxt.nextLine());

								stringBuilder.setLength(0);
								while(scFicTxt.hasNextLine())
								{
									stringBuilder.append(scFicTxt.nextLine()).append('\n');
								}
								textProp = stringBuilder.toString().substring(0, stringBuilder.length()-1);

								((Elimination) question).ajouterProposition(new PropositionElimination(textProp, estReponse, ordreElim, nbPtsPerdus));
							}
							scFicTxt.close();
						}
					}
					default ->
					{
						scDonnees.close();
						throw new IllegalArgumentException("Type de question inconnu: " + typeQuestion);
					}
				}

				question.setIntitule   (intitule   );
				question.setExplication(explication);
				
				if(!"".equals(ficComp))
				{
					pj = new PieceJointe(cheminDirQst+"/complément/"+ficComp);
					question.setPieceJointe(pj);
				}

				this.lstQuestions.add(question);
				cpt++;

				scDonnees.close();

			}
			scEnreg.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/**
	 * Sauvegarde des questions dans un fichier CSV et des fichiers TXT
	 */
	public void sauvegarder()
	{
		this.sauvegarder(this.cheminFicCSV);
	}

	/**
	 * Sauvegarde des questions dans un fichier CSV et des fichiers TXT.
	 * 
	 * @param cheminFicCSV   le chemin du fichier CSV.
	 * @see   PrintWriter pour écrire dans le fichier.
	 */
	private void sauvegarder(String cheminFicCSV)
	{
		PrintWriter pwCsv, pwTxt;

		QCM         qQCM;  PropositionQCM         propQCM;
		Elimination qElim; PropositionElimination propElim;
		Association qAsso; PropositionAssociation propAsso;

		String codeRes;
		int    idNot;
		int    idQst;
		int    valDiff;
		int    valType;
		int    temps;
		double note;
		int    nbProp;

		String cheminDirQst;
		File   dirQst, dirProp, dirComp;

		PieceJointe pj;
		String      ficComp;

		try
		{
			// PrintWriter appliqué sur le fichier CSV
			pwCsv = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminFicCSV), "UTF8"));

			// Entête
			pwCsv.println("codeRes\tidNot\tidQst\tvalDiff\tvalType\ttemps\tnote\tnbProp\tficComp\t");

			// Données
			for (Question question : this.lstQuestions)
			{   
				if(question == null) continue;
				
				codeRes = question.getCodeRes     ();
				idNot   = question.getIdNot       ();
				idQst   = question.getIdQst       ();
				valDiff = question.getDifficulte  ().getValeur();
				valType = question.getType        ().getValeur();
				temps   = question.getTemps       ();
				note    = question.getNote        ();
				nbProp  = question.getPropositions().size();
				pj      = question.getPieceJointe ();

				ficComp = pj == null ? "" : pj.getNomPieceJointe() + "." + pj.getExtension();

				pwCsv.println(codeRes+"\t"+idNot+"\t"+idQst+"\t"+valDiff+"\t"+valType+"\t"+temps+"\t"+note+"\t"+nbProp+"\t"+ficComp+"\t");

				cheminDirQst  = "data/ressources/" + codeRes + "/notion" + idNot + "/question" + idQst;

				// Création des dossiers
				dirQst        = new File(cheminDirQst);
				dirProp       = new File(cheminDirQst+"/propositions");
				dirComp       = new File(cheminDirQst+"/complément"  );
				
				BanqueQuestions.creerDossier(dirQst);

				// PrintWriter appliqué sur le fichier TXT de l'intitulé (intitule.txt)
				pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/intitule.txt"), "UTF8"));
				pwTxt.println(question.getIntitule());
				pwTxt.close();

				// PrintWriter appliqué sur le fichier TXT de l'explication (explication.txt)
				pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/explication.txt"), "UTF8"));
				pwTxt.println(question.getExplication());
				pwTxt.close();

				BanqueQuestions.supprimerDossier(dirProp);
				BanqueQuestions.creerDossier    (dirProp);

				switch (question.getType())
				{
					case QCM ->
					{
						qQCM = (QCM) question;

						for(int i = 0; i < qQCM.getPropositions().size(); i++)
						{
							// PrintWriter appliqué sur le fichier TXT de la proposition (propX.txt)
							pwTxt   = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
							propQCM = qQCM.getProposition(i);
							
							if (propQCM.estReponse()) 
								pwTxt.println("V");
							else
								pwTxt.println("F");

							pwTxt.println(propQCM.getText());

							pwTxt.close();
						}
						
						pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/unique.txt"), "UTF8"));
						pwTxt.println(qQCM.estUnique());
						pwTxt.close();
					}
					case ASSOCIATION -> 
					{
						qAsso = (Association) question;

						for (int i = 0; i < qAsso.getPropositions().size(); i++) 
						{
							// PrintWriter appliqué sur le fichier TXT de la proposition de gauche (propGX.txt)
							propAsso = qAsso.getProposition(i);

							pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/propG"+(i+1)+".txt"), "UTF8"));
							pwTxt.println(propAsso.getTextGauche());
							pwTxt.close();

							// PrintWriter appliqué sur le fichier TXT de la proposition de droite (propDX.txt)
							pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/propD"+(i+1)+".txt"), "UTF8"));
							pwTxt.println(propAsso.getTextDroite());
							pwTxt.close();
						}
					}
					case ELIMINATION -> 
					{
						qElim =  (Elimination) question;
	
						for (int i = 0; i < qElim.getPropositions().size(); i++)
						{
							// PrintWriter appliqué sur le fichier TXT de la proposition (propX.txt)
							pwTxt    = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
							propElim = qElim.getProposition(i);

							if (propElim.estReponse()) 
								pwTxt.println("V");
							else 
								pwTxt.println("F");

							pwTxt.println(propElim.getOrdreElim  ());
							pwTxt.println(propElim.getNbPtsPerdus());
							pwTxt.println(propElim.getText       ());

							pwTxt.close();
						}
					}
				}
				pwTxt.close();

				if(pj == null)
				{
					BanqueQuestions.supprimerDossier(dirComp);
				}
				else if(pj != null && !pj.getCheminFicOrig().equals(pj.getCheminFic()))
				{
					BanqueQuestions.supprimerDossier(dirComp);
					BanqueQuestions.creerDossier    (dirComp);
					pj.copierFichier();
				}
			}
			pwCsv.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier n'a pas été trouvé : " + fnfe.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creer un dossier
	 * 
	 * @param dossier le dossier à créer.
	 * @return        true si le dossier a été créer, false sinon.
	 */
	public static boolean creerDossier(File dossier) 
	{
		if (!dossier.exists()) 
		{	
			return dossier.mkdirs(); 
		}
		return true;
	}

	/**
	 * Créer une question de type QCM et l'ajoute à la banque de questions.
	 * 
	 * @param codeRes    le code de la ressource
	 * @param idNot      l'identifiant de la notion
	 * @param difficulte la difficulté de la question
	 * @param temps      le temps pour répondre à la question
	 * @param note       la note de la question
	 * @return           la question de type QCM
	 */
	public QCM creerQCM(String codeRes, int idNot, Difficulte difficulte, int temps, double note) 
	{
		int idQst;
		QCM qcm;


		idQst = this.recupererId();
		qcm   = new QCM(codeRes, idNot, idQst, note, temps, difficulte);
		this.ajouterQuestion(qcm);

		return qcm;
	}

	/**
	 * Créer une question de type Association et l'ajoute à la banque de questions.
	 * 
	 * @param codeRes    le code de la ressource
	 * @param idNot      l'identifiant de la notion
	 * @param difficulte la difficulté de la question
	 * @param temps      le temps pour répondre à la question
	 * @param note       la note de la question
	 * @return           la question de type Association
	 */
	public Association creerAssociation(String codeRes, int idNot, Difficulte difficulte, int temps, double note) 
	{
		int idQst;
		Association asso;


		idQst = this.recupererId();
		asso  = new Association(codeRes, idNot, idQst, note, temps, difficulte);
		this.ajouterQuestion(asso);

		return asso;
	}

	/**
	 * Créer une question de type Elimination et l'ajoute à la banque de questions.
	 * 
	 * @param codeRes    le code de la ressource
	 * @param idNot      l'identifiant de la notion
	 * @param difficulte la difficulté de la question
	 * @param temps      le temps pour répondre à la question
	 * @param note       la note de la question
	 * @return           la question de type Elimination
	 */
	public Elimination creerElimination(String codeRes, int idNot, Difficulte difficulte, int temps, double note) 
	{
		int idQst;
		Elimination elim;


		idQst = this.recupererId();
		elim  = new Elimination(codeRes, idNot, idQst, note, temps, difficulte);
		this.ajouterQuestion(elim);

		return elim;
	}

	/**
	 * Récupère l'identifiant de la dernière question.
	 * 
	 * @return l'identifiant de la dernière question.
	 */
	private int recupererId()
	{
		if (!this.fileIdUtilisable.isEmpty())
			return this.fileIdUtilisable.poll();

		return this.lstQuestions.size();
	}

	/**
	 * Ajoute une question à la banque de questions.
	 * 
	 * @param question la question à ajouter à la liste.
	 */
	public void ajouterQuestion(Question question)
	{
		if (question.getIdQst() < this.lstQuestions.size())
			this.lstQuestions.set(question.getIdQst(), question);
		else
			this.lstQuestions.add(question);
	}

	/**
	 * Modiie le type d'une question en la remplaçant avec un nouvel objet Question du type 
	 * correspondant.
	 * 
	 * @param idQst l'id de la question à modifier.
	 * @param type  le nouveau type.
	 */
	public void modifierType(int idQst, TypeQuestion type)
	{
		if(this.lstQuestions.get(idQst) != null)
		{
			Question   oldQuestion = this.lstQuestions.get(idQst);

			String     codeRes     = oldQuestion.getCodeRes   ();
			int        idNot       = oldQuestion.getIdNot     ();
			double     note        = oldQuestion.getNote      ();
			int        temps       = oldQuestion.getTemps     ();
			Difficulte diff        = oldQuestion.getDifficulte();

			Question   newQuestion = switch (type) {
				case    QCM         -> new QCM        (codeRes, idNot, idQst, note, temps, diff);
				case    ASSOCIATION -> new Association(codeRes, idNot, idQst, note, temps, diff);
				case    ELIMINATION -> new Elimination(codeRes, idNot, idQst, note, temps, diff);
				default             -> null;
			};

			if(newQuestion != null)
			{
				newQuestion.setIntitule   (oldQuestion.getIntitule   ());
				newQuestion.setExplication(oldQuestion.getExplication());
				newQuestion.setPieceJointe(oldQuestion.getPieceJointe());

				this.lstQuestions.set(idQst, newQuestion);
			}
		}
	}

	/**
	 * Supprime une question de la banque de questions grâce à son identifiant.
	 * 
	 * @param id l'identifiant de la question à supprimer.
	 */
	public void supprimerQuestion(int id)
	{
		Question question;


		question = this.getQuestion(id);

		if(question != null)
		{
			// Supprime la question de la banque
			this.lstQuestions.set(id, null);
			this.fileIdUtilisable.offer(id);

			// Supprime les dossiers et fichiers associés à la question
			BanqueQuestions.supprimerDossier(new File("data/ressources/" + question.getCodeRes() + "/notion" + question.getIdNot() + "/question" + question.getIdQst()));
		
			this.sauvegarder();
		}
	}

	/**
	 * Supprime les questions d'une ressource.
	 * 
	 * @param codeRes le code de la ressource.
	 */
	public void supprimerQuestions(String codeRes)
	{
		for(int i = 0; i < this.lstQuestions.size(); i++)
		{
			if(this.lstQuestions.get(i) != null && this.lstQuestions.get(i).getCodeRes().equals(codeRes))
			{
				this.supprimerQuestion(i);
			}
		}
	}

	/**
	 * Supprime les questions d'une notion.
	 * 
	 * @param idNot l'identifiant de la notion.
	 */
	public void supprimerQuestions(int idNot)
	{
		for(int i = 0; i < this.lstQuestions.size(); i++)
		{
			if(this.lstQuestions.get(i) != null && this.lstQuestions.get(i).getIdNot() == idNot)
			{
				this.supprimerQuestion(i);
			}
		}
	}
}
