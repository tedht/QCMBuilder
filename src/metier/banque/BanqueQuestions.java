package metier.banque;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import metier.entite.question.*;
import metier.entite.question.association.*;
import metier.entite.question.qcm.*;
import metier.entite.question.elimination.*;


/** Classe BanqueDeQuestions
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueQuestions 
{



	/*-----------*/
	// Attributs //
	/*-----------*/

	private List<Question> lstQuestions;
	private Queue<Integer> fileIdUtilisable;

	private String         cheminFic;
	private String         currentDir;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe BanqueDeQuestions
	 */
	public BanqueQuestions()
	{
		this.lstQuestions     = new ArrayList<Question>();
		this.fileIdUtilisable = new LinkedList<Integer>();
		this.currentDir       = System.getProperty("user.dir");

		this.cheminFic        = this.currentDir + "/data/questions.csv";

		this.lireQuestions(this.cheminFic);
	}



	/*---------*/
	// Getters //
	/*---------*/

	/**
	 * Retourne le chemin du fichier de la banque de questions
	 * 
	 * @return le chemin du fichier
	 */
	public String getCheminFic()
	{
		return this.cheminFic;
	}

	/** 
	 * Retourne la liste des questions
	 * 
	 * @return List<Question> la liste des questions
	 */
	public List<Question> getQuestions()
	{
		return this.lstQuestions;
	}

	/**
	 * Retourne la liste des questions d'une ressource
	 * 
	 * @param codeRes le code de la ressource
	 * @return List<Question> la liste des questions de la ressource
	 */
	public List<Question> getQuestions(String codeRes) 
	{
		List<Question> lstQuestions = new ArrayList<Question>();

		for(Question question : this.lstQuestions)
		{
			if(question != null && question.getCodeRes().equals(codeRes))
				lstQuestions.add(question);
		}

		return lstQuestions;
	}

	/**
	 * Retourne la liste des questions d'une ressource et d'une notion
	 * 
	 * @param codeRes le code de la ressource
	 * @param idNot   l'identifiant de la notion
	 * @return List<Question> la liste des questions de la ressource et de la notion
	 */
	public List<Question> getQuestions(String codeRes, int idNot) 
	{
		List<Question> lstQuestions = new ArrayList<Question>();

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
	 * Retourne une question de la banque de questions
	 * 
	 * @param idQst l'identifiant de la question
	 * @return la question grâce à l'identifiant
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
	* Lecture du fichier CSV qui contient les questions
	*
	* @param cheminFic le chemin du fichier CSV
	*/
	public void lireQuestions(String cheminFic)
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

		String       intitule    = "";
		String       explication = "";

		int          nbProp;
		String       detailsProp;
		String       textProp;

		String       textGauche, textDroite;
		
		int          ordreElim;
		double       nbPtsPerdus;

		boolean      estReponse;
		int          cptReponse;

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

				cheminDirQst = currentDir + "data/ressources/" + codeRes + "/notion" + idNot + "/question" + idQst;

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
					
					for(int i = 1; i <= nbProp; i++)
					{
						scProp      = new Scanner(new FileInputStream(cheminDirQst+"/propositions/prop"+i+".txt"), "UTF8");
						if(scProp.hasNextLine())
						{
							detailsProp = scProp.nextLine();

							scElim = new Scanner(detailsProp);
							scElim.useDelimiter(":");
	
							estReponse  = scElim.next().charAt(0) == 'V';
							ordreElim   = scElim.nextInt();
							nbPtsPerdus = Double.parseDouble(scElim.next());
	
							textProp = scElim.next();
							
							((Elimination) question)
								.ajouterProposition(new PropositionElimination(textProp, estReponse, ordreElim, nbPtsPerdus));
							
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
	 * Sauvegarde des questions dans un fichier CSV
	 */
	public void sauvegarder()
	{
		this.sauvegarder(this.cheminFic);
	}

	/**
	 * Sauvegarde des questions dans un fichier CSV et un fichier TXT
	 * 
	 * @param cheminFic le chemin du fichier CSV
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

		String cheminDirQst, cheminDirProp;
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
				cheminDirProp = cheminDirQst + "/propositions";
				dirQst        = new File(cheminDirQst);
				dirProp       = new File(cheminDirProp);
				
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
				pwTxt.println(question.getIntitule());
				pwTxt.close();

				pwTxt = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirQst+"/explication.txt"), "UTF8"));
				pwTxt.println(question.getExplication());
				pwTxt.close();

				switch (question.getType())
				{
					case QCM -> 
					{
						qQCM = (QCM) question;

						for(int i = 0; i < qQCM.getPropositions().size(); i++)
						{
							pwTxt   = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirProp+"/prop"+(i+1)+".txt"), "UTF8"));
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
							pwTxt    = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirProp+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
							propAsso = qAsso.getProposition(i);

							pwTxt.print(propAsso.getTextGauche() + "\t" );
							pwTxt.println(propAsso.getTextDroite());	

							pwTxt.close();
						}
					}
					case ELIMINATION -> 
					{
						qElim =  (Elimination) question;
	
						for (int i = 0; i < qElim.getPropositions().size(); i++)
						{
							pwTxt    = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminDirProp+"/propositions/prop"+(i+1)+".txt"), "UTF8"));
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
	 * Créer une question de type QCM
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
		int idQst = this.recupererId();
		QCM qcm = new QCM(codeRes, idNot, idQst, note, temps, difficulte);
		this.ajouterQuestion(qcm);
		return qcm;
	}

	/**
	 * Créer une question de type Association
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
		int idQst = this.recupererId();
		Association asso = new Association(codeRes, idNot, idQst, note, temps, difficulte);
		this.ajouterQuestion(asso);
		return asso;
	}

	/**
	 * Créer une question de type Elimination
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
		int idQst = this.recupererId();
		Elimination elim = new Elimination(codeRes, idNot, idQst, note, temps, difficulte);
		this.ajouterQuestion(elim);
		return elim;
	}

	/**
	 * Récupère l'identifiant de la dernière question
	 * 
	 * @return l'identifiant de la dernière question
	 */
	private int recupererId()
	{
		if(!this.fileIdUtilisable.isEmpty())
			return this.fileIdUtilisable.poll();

		return this.lstQuestions.size();
	}

	/**
	 * Ajoute une question à la banque de questions.
	 * 
	 * @param question la question à ajouter à la liste.
	 */
	private void ajouterQuestion(Question question)
	{
		if(question.getIdQst() < this.lstQuestions.size())
			this.lstQuestions.set(question.getIdQst(), question);
		else
			this.lstQuestions.add(question);

		this.sauvegarder();
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
	 * Créer une pièce jointe
	 * 
	 * @param cheminFichierOriginal le chemin du fichier original
	 * @param question la question à laquelle la pièce jointe est associée
	 * 
	 * @return boolean si oui ou non la création de la pièce jointe a été effectué
	 */
	public boolean creerPieceJointe(String cheminFichierOriginal, Question question)
	{
		/*for (int cpt = 0 ; cpt < this.lstQuestions.size() ; cpt ++)
		{
			if (this.lstQuestions.get(cpt) == question)
			{
				question.ajouterPieceJointe(new PieceJointe(cheminFichierOriginal, "ressources/" + question.getRessource() +
				                                                                    "/" +  question.getNotion().getNom()   +
																					"/question " + (cpt + 1) + "/complément"));
				return true;
			}
		}
		return false;*/
		return false;
	}

	/*
	public static void main(String[] args)
	{
		Ressource r1;
		Ressource r2;

		Notion n1;
		Notion n2;
		Notion n3;

		QCM q1;
		Elimination q2;
		Association q3;

		BanqueQuestions banque;
		BanqueRessources banqueRessources;
		QCMBuilder qcmBuilder;

		banqueRessources = new BanqueRessources();




		// Initialisation des ressources et notions
		r1 = new Ressource("R1","Ressource 1");
		r2 = new Ressource("R2","Ressource 2");

		banqueRessources.ajouterRessource(r1);
		banqueRessources.ajouterRessource(r2);

		n1 = new Notion("Notion 1" , 1, r1.getCode());
		n2 = new Notion("Notion 2" , 2, r1.getCode());
		n3 = new Notion("Notion 3" , 3, r2.getCode());

		r1.ajouterNotion(n1);
		r1.ajouterNotion(n2);
		r2.ajouterNotion(n3);

		banqueRessources.sauvegarderRessources("data/ressources.csv");

		qcmBuilder = new QCMBuilder();
		qcmBuilder.creerArborescence();

		// Création de questions de type QCM
		q1 = new QCM(r1, n1, Difficulte.FACILE, 10, 10, true);
		q1.setIntitule("Quelle est la capitale de la France ?");
		q1.setExplication("La capitale de la France est Paris.");
		q1.ajouterProposition(new PropositionQCM("Paris", true));
		q1.ajouterProposition(new PropositionQCM("Londres", false));
		q1.ajouterProposition(new PropositionQCM("Berlin", false));

		// Création de questions de type Élimination
		q2 = new Elimination(r2, n3, Difficulte.MOYEN, 20, 15);
		q2.setIntitule("De quelle couleur est le cheval blanc d'Henri IV ?");
		q2.setExplication("La réponse est évidente, mais il est intéressant de poser la question.");
		q2.ajouterProposition(new PropositionElimination("Noir:d:d:d", false, 1, 2.0));
		q2.ajouterProposition(new PropositionElimination("Gris", false, 2, 1.0));
		q2.ajouterProposition(new PropositionElimination("Blanc", true, 0, 0.0));

		// Création de questions de type Association
		q3 = new Association(r1, n2, Difficulte.DIFFICILE, 30, 20);
		q3.setIntitule("Associez les pays à leurs capitales.");
		q3.setExplication("Exercice sur les capitales européennes.");
		q3.ajouterProposition(new PropositionAssociation("France", "Paris"));
		q3.ajouterProposition(new PropositionAssociation("Allemagne", "Berlin"));
		q3.ajouterProposition(new PropositionAssociation("Espagne", "Madrid"));


		// Initialisation de la banque de questions
		banque = new BanqueQuestions(new QCMBuilder());


		// Ajout des questions
		banque.ajouterQuestions(q1);
		banque.ajouterQuestions(q2);
		banque.ajouterQuestions(q3);

		// Affichage des questions ajoutées
		System.out.println("=== Questions Ajoutées ===");
		for (Question question : banque.getQuestions())
		{
			System.out.println(question);
		}

		// Sauvegarde des questions dans des fichiers
		banque.sauvegarderQuestions("data/questions.csv");
		System.out.println("=== Questions sauvegardées dans les fichiers CSV et TXT ===");

		for (Question question : banque.getQuestions())
		{
			System.out.println(question);
		}

		// Suppression des questions
		banque.supprimerQuestion(q1);
		banque.supprimerQuestion(q2);
		banque.supprimerQuestion(q3);

		System.out.println("=== Questions après suppression ===");
		for (Question question : banque.getQuestions())
		{
			System.out.println(question);
		}

		// Lecture des questions sauvegardées
		banque.lireQuestions("data/questions.csv");
		System.out.println("=== Questions après lecture des fichiers ===");
		for (Question question : banque.getQuestions())
		{
			System.out.println(question);
		}
	}*/
}
