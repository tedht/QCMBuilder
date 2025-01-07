package metier.banque;

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

import metier.entite.Notion;
import metier.entite.Ressource;

import metier.entite.question.*;

import metier.entite.question.association.*;
import metier.entite.question.qcm.*;
import metier.entite.question.elimination.*;


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

	private String         cheminFic;

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

		this.cheminFic        = "data/questions.csv";

		this.lireQuestions(this.cheminFic);
	}



	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le chemin du fichier de la banque de questions.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getCheminFic()
	{
		return this.cheminFic;
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
	* @param cheminFic le chemin du fichier CSV.
	* @see   Scanner   pour lire le fichier.
	*/
	private void lireQuestions(String cheminFic)
	{
		Scanner scEnreg, scDonnees, scIntitule, scExplication, scProp, scElim;

		String       cheminDirQst;
		int          cpt;

		String       codeRes;
		int          idNot;
		int          idQst;
		Difficulte   difficulte;
		TypeQuestion typeQuestion;
		int          temps;
		double       note;
		Question     question;

		String       intitule;
		String       explication;

		int          nbProp;
		String       detailsProp;
		String       textProp;

		String       textGauche, textDroite;

		int          ordreElim;
		double       nbPtsPerdus;

		boolean      estReponse;
		int          cptReponse;


		intitule = "";
		explication = "";

		try
		{
			scEnreg = new Scanner(new FileInputStream(cheminFic), "UTF8");

			if(scEnreg.hasNextLine())
				scEnreg.nextLine();

			cpt = 0;
			while (scEnreg.hasNextLine())
			{
				scDonnees = new Scanner(scEnreg.nextLine());

				scDonnees.useDelimiter("\t");

				codeRes	     = scDonnees.next();
				idNot        = scDonnees.nextInt();
				idQst        = scDonnees.nextInt();
				difficulte   = Difficulte.fromInt(scDonnees.nextInt());
				typeQuestion = TypeQuestion.fromInt(scDonnees.nextInt());
				temps        = scDonnees.nextInt();
				note         = Double.parseDouble(scDonnees.next());
				nbProp       = scDonnees.nextInt();

				cheminDirQst = "data/ressources/" + codeRes + "/notion" + idNot + "/question" + idQst;

				scIntitule    = new Scanner(new FileInputStream(cheminDirQst+"/intitule.txt"), "UTF8");

				if(scIntitule.hasNextLine())
					intitule = scIntitule.nextLine();

				scIntitule.close();


				scExplication = new Scanner(new FileInputStream(cheminDirQst+"/explication.txt"), "UTF8");

				if(scExplication.hasNextLine())
					explication = scExplication.nextLine();

				scExplication.close();


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
							scProp = new Scanner(new FileInputStream(cheminDirQst+"/propositions/prop"+i+".txt"), "UTF8");
							if(scProp.hasNextLine())
							{
								detailsProp = scProp.nextLine();

								estReponse = detailsProp.charAt(0) == 'V';
								if(estReponse) cptReponse++;
								
								textProp = detailsProp.substring(2);

								((QCM) question).ajouterProposition(new PropositionQCM(textProp, estReponse));
							}
							scProp.close();
						}
						((QCM) question).setUnique(cptReponse == 1);
					}
					case ASSOCIATION ->
					{
						question = new Association(codeRes, idNot, idQst, note, temps, difficulte);
						
						for(int i = 1; i <= nbProp; i++)
						{
							scProp     = new Scanner(new FileInputStream(cheminDirQst+"/propositions/prop"+i+".txt"), "UTF8");
							if(scProp.hasNextLine())
							{
								textGauche = scProp.nextLine();
								textDroite = scProp.nextLine();

								((Association) question).ajouterProposition(new PropositionAssociation(textGauche, textDroite));
							}
							scProp.close();
						}
					}
					case ELIMINATION ->
					{
						question = new Elimination(codeRes, idNot, idQst, note, temps, difficulte);
						
						for(int i = 1 ; i <= nbProp ; i++)
						{
							scProp = new Scanner(new FileInputStream(cheminDirQst+"/propositions/prop"+i+".txt"), "UTF8");

							if(scProp.hasNextLine())
							{
								detailsProp = scProp.nextLine();

								scElim = new Scanner(detailsProp);
								scElim.useDelimiter(":");
		
								estReponse  = scElim.next().charAt(0) == 'V';
								ordreElim   = scElim.nextInt();
								nbPtsPerdus = Double.parseDouble(scElim.next());

								textProp = scElim.next();

								((Elimination) question).ajouterProposition(new PropositionElimination(textProp, estReponse, ordreElim, nbPtsPerdus));

								scElim.close();
							}
							scProp.close();
						}
					}
					default ->
					{
						scDonnees.close();
						throw new IllegalArgumentException("Type de question inconnu: " + typeQuestion);
					}
				}

				question.setIntitule   (intitule);
				question.setExplication(explication);
				
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
		this.sauvegarder(this.cheminFic);
	}

	/**
	 * Sauvegarde des questions dans un fichier CSV et des fichiers TXT.
	 * 
	 * @param cheminFic   le chemin du fichier CSV.
	 * @see   PrintWriter pour écrire dans le fichier.
	 */
	private void sauvegarder(String cheminFic)
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
		File   dirQst, dirProp;

		try
		{
			pwCsv = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminFic), "UTF8"));
			pwCsv.println("codeRes\tidNot\tidQst\tvalDiff\tvalType\ttemps\tnote\tnbProp");

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

				pwCsv.println(codeRes+"\t"+idNot+"\t"+idQst+"\t"+valDiff+"\t"+valType+"\t"+temps+"\t"+note+"\t"+nbProp);

				cheminDirQst  = "data/ressources/" + codeRes + "/notion" + idNot + "/question" + idQst;
				dirQst        = new File(cheminDirQst);
				dirProp       = new File(cheminDirQst+"/propositions");
				
				if (!dirQst.exists())
				{
					if (dirQst.mkdirs())
					{
						System.out.println("Le dossier " + dirQst.getPath() + " a été créé avec succès.");
						dirProp.mkdir();
					}
					else
					{
						System.out.println("La création du dossier " + dirQst.getPath() + " a échoué.");
					}
				}

				pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/intitule.txt"), "UTF8"));
				System.out.println(cheminDirQst+"/intitule.txt");
				pwTxt.println(question.getIntitule());
				System.out.println(question.getIntitule());
				pwTxt.close();

				pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/explication.txt"), "UTF8"));
				System.out.println(cheminDirQst+"/explication.txt");
				pwTxt.println(question.getExplication());
				System.out.println(question.getExplication());
				pwTxt.close();

				switch (question.getType())
				{
					case QCM -> 
					{
						qQCM = (QCM) question;

						for(int i = 0; i < qQCM.getPropositions().size(); i++)
						{
							pwTxt   = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
							propQCM = qQCM.getProposition(i);
							
							if (propQCM.estReponse()) 
								pwTxt.print("V:");
							else
								pwTxt.print("F:");

							pwTxt.println(propQCM.getText());

							pwTxt.close();
						}
					}
					case ASSOCIATION -> 
					{
						qAsso = (Association) question;

						for (int i = 0; i < qAsso.getPropositions().size(); i++) 
						{
							pwTxt    = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
							propAsso = qAsso.getProposition(i);

							pwTxt.println(propAsso.getTextGauche());
							pwTxt.println(propAsso.getTextDroite());	

							pwTxt.close();
						}
					}
					case ELIMINATION -> 
					{
						qElim =  (Elimination) question;
	
						for (int i = 0; i < qElim.getPropositions().size(); i++)
						{
							pwTxt    = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
							propElim = qElim.getProposition(i);

							if (propElim.estReponse()) 
								pwTxt.print("V:");
							else 
								pwTxt.print("F:");

							pwTxt.print(propElim.getOrdreElim  () + ":" );
							pwTxt.print(propElim.getNbPtsPerdus() + ":");
							pwTxt.println(propElim.getText());

							pwTxt.close();
						}

					}
				}

				pwTxt.close();
				
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
			this.supprimerDossier(new File("data/ressources/" + question.getCodeRes() + "/notion" + question.getIdNot() + "/question" + question.getIdQst()));
		
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

	/**
	 * Modifie une question
	 * 
	 * @param question
	 * @param critere
	 * @param modif
	 * @return
	 */
	public boolean modifierQuestion(Question question, String critere, Object modif)
	{
		/*if (question == null) return false; // Si la question est null     -> renvoie faux
		if (critere  == null) return false; // Si le critère est null      -> renvoie faux
		if (modif    == null) return false; // Si la modification est null -> renvoie faux

		switch (critere)
		{
			case "difficulte" -> question.setDifficulte((Difficulte)modif);
			case "ressource"  -> question.setRessource ((Ressource) modif);
			case "notion"     -> question.setNotion    ((Notion)    modif);
			case "temps"      -> question.setTemps     ((int)       modif);
			case "note"       -> question.setNote      ((int)       modif);
		}
		return true;*/
		return false;
	}

	/**
	 * Supprime une question
	 * 
	 * @param question
	 * @return boolean si oui ou non la suppression de la question a été effectué
	 */
	public boolean supprimerQuestion(Question question)
	{
		/*
		if (question == null) return false; // N'a pas trouvé la question

		for (int cpt = 0 ; cpt < this.lstQuestions.size() ; cpt++)
		{
			if (this.lstQuestions.get(cpt) == question)
			{
				this.lstQuestions.remove(cpt);
			}
		}

		return true;*/
		return false;
	}

	/**
	 * Créer une pièce jointe et l'associe à la question
	 * 
	 * @param cheminFichierOriginal le chemin du fichier original (en local)
	 * @param question              la question à laquelle la pièce jointe est associée
	 * 
	 * @return si oui ou non la création de la pièce jointe a été effectué
	 */
	public boolean creerPieceJointe(String cheminFichierOriginal, Question question)
	{
		for (int cpt = 0 ; cpt < this.lstQuestions.size() ; cpt ++)
		{
			if (this.lstQuestions.get(cpt) == question)
			{
				question.ajouterPieceJointe(new PieceJointe(cheminFichierOriginal, "ressources/" +  question.getCodeRes() +
				                                                                    "/notion" + question.getIdNot() +
																					"/question " + (cpt + 1) + "/complément"));
				return true;
			}
		}
		return false;
	}

	/**
	 * Main de la classe BanqueQuestions.
	 * 
	 * @param args les arguments de la ligne de commande.
	 */
	public static void main(String[] args)
	{
		BanqueQuestions bq;
		Question q1, q2, q3;


		new Ressource("R1", "Ressource 1");
		new Ressource("R2", "Ressource 2");

		// Création des notions
		new Notion("R1", 1, "Notion 1");
		new Notion("R2", 2, "Notion 2");

		// Création des questions
		q1 = new QCM("R1", 1, 1, 1.0, 10, Difficulte.FACILE);
		q2 = new Association("R1", 1, 2, 1.0, 10, Difficulte.FACILE);
		q3 = new Elimination("R1", 1, 3, 1.0, 10, Difficulte.FACILE);

		// Création de la banque de questions
		bq = new BanqueQuestions();

		// Ajout des questions à la banque
		bq.ajouterQuestion(q1);
		bq.ajouterQuestion(q2);
		bq.ajouterQuestion(q3);

		// Affichage des questions pour vérifier l'ajout
		System.out.println("Questions dans la banque:");
		for (Question q : bq.getQuestions())
		{
			System.out.println(q);
		}
	}
}
