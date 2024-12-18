package metier.banque;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import metier.QCMBuilder;

import metier.entite.Ressource;
import metier.entite.Notion;

import metier.entite.question.*;
import metier.entite.question.association.*;
import metier.entite.question.qcm.*;
import metier.entite.question.elimination.*;


/** Classe BanqueDeQuestions
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeQuestions 
{
	/* Attributs */
	private List<Question> lstQuestions;


	/**
	 * Constructeur de la classe BanqueDeQuestions
	 * 
	 * @param qcmBuilder le constructeur de QCM
	 */
	public BanqueDeQuestions(QCMBuilder qcmBuilder)
	{
		this.lstQuestions = new ArrayList<Question>();

		//this.lireQuestions("data/questions.csv", "data/questions.txt");
	}

	/** 
	 * Retourne la liste des questions
	 * 
	 * @return List<Question> la liste des questions
	 */
	public List<Question> getQuestions()
	{
		return lstQuestions;
	}

	/**
	 * Retourne les questions associées à une ressource
	 * 
	 * @param ressource la ressource associée aux questions
	 * @return List<Question> la liste des questions associées à la ressource
	 */
	public List<Question> getQuestions(Ressource ressource, Notion notion) 
	{
		List<Question> lstQuestions;


		lstQuestions = new ArrayList<Question>();
		for(Question question : this.lstQuestions)
		{
			if(question.getRessource() == ressource && question.getNotion() == notion)
			{
				lstQuestions.add(question);
			}
		}
		return lstQuestions;
	}

	/**
	* Lecture du fichier CSV qui contient les questions
	*
	* @param nomFichierCSV le chemin du fichier CSV
	* @param nomFichierTXT le chemin du fichier TXT
	*/
	public void lireQuestions(String nomFichierCSV)
	{
		Scanner scEnreg, scDonnees, scTexte, scElim;

		Ressource    ressource;
		Notion       notion;
		Difficulte   difficulte;
		TypeQuestion typeQuestion;
		int          temps;
		double       note;
		String       cheminFichierTXT;
		String       intitule;
		String       explication;
		Question     question;
		boolean      unique;
		String codeRessource;
		String nomRessource;

		try
		{
			scEnreg = new Scanner(new FileInputStream(nomFichierCSV), "UTF8");
			scEnreg.nextLine();

			while (scEnreg.hasNextLine())
			{
				scDonnees = new Scanner(scEnreg.nextLine());

				scDonnees.useDelimiter("\t");

				//ressource        = this.qcmBuilder.getRessource(scEnreg.next());
				codeRessource	 = scDonnees.next();
				nomRessource	 = scDonnees.next();
				ressource        = new Ressource       (codeRessource, nomRessource);
				notion           = ressource.getNotion (scDonnees.next());
				difficulte       = Difficulte.  fromInt(scDonnees.nextInt());
				typeQuestion     = TypeQuestion.fromInt(scDonnees.nextInt());
				temps            =                      scDonnees.nextInt();
				note             = Double.parseDouble  (scDonnees.next());
				cheminFichierTXT =                      scDonnees.next();

				// Lire l'intitulé et l'explication à partir du fichier texte
				scTexte = new Scanner(new FileInputStream(cheminFichierTXT), "UTF8");

				intitule    = scTexte.nextLine();
				explication = scTexte.nextLine();
				scTexte.close();

				switch (typeQuestion)
				{
				case QCM ->
				{
					unique = scDonnees.nextBoolean();

					question = new QCM(ressource, notion, difficulte, temps, note, unique);
					while (scDonnees.hasNext())
					{
						
						String proposition = scDonnees.next();

						boolean estReponse      = proposition.startsWith("V:");
						String texteProposition = proposition.substring(2);
						((QCM) question).ajouterProposition(new PropositionQCM(texteProposition, estReponse));
					}
				}
				case ASSOCIATION ->
				{
					question = new Association(ressource, notion, difficulte, temps, note);
					while (scDonnees.hasNext())
					{
						String gauche = scDonnees.next();
						String droite = scDonnees.next();
						((Association) question).ajouterProposition(new PropositionAssociation(gauche, droite));
					}
				}
				case ELIMINATION ->
				{
					question = new Elimination(ressource, notion, difficulte, temps, note);
					
					while (scDonnees.hasNext())
					{
						scElim = new Scanner(scDonnees.next());
						scElim.useDelimiter(":");

						boolean reponse     =                    scElim.next().equals("V");
						int     ordreElim   =                    scElim.nextInt    ();
						double  nbPtsPerdus = Double.parseDouble(scElim.next       ());
						scElim.useDelimiter("\t");
						String  texte       =                    scElim.next       ();
						((Elimination) question)
								.ajouterProposition(new PropositionElimination(texte, reponse, ordreElim, nbPtsPerdus));

						scElim.close();
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
	 * Ajoute une question à la banque de questions
	 * 
	 * @param question la question à ajouter
	 * @return         true si la question a été ajoutée, false sinon
	 */
	public boolean ajouterQuestions(Question question)
	{
		if (question == null) return false;

		this.lstQuestions.add(question);
		return true;
	}

	/**
	 * Sauvegarde des questions dans un fichier CSV et un fichier TXT
	 * 
	 * @param nomFichierCSV le chemin du fichier CSV
	 * @param nomFichierTXT le chemin du fichier TXT
	 */
	public void sauvegarderQuestions(String nomFichierCSV)
	{
		PrintWriter pw;
		PrintWriter pw2;
		QCM         qQCM;
		Elimination qElim;
		Association qAsso;

		try
		{
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichierCSV), "UTF8"));
			pw.println("code\tressource\tnotion\tdifficulte\ttype\ttemps\tnote\tcheminfichiertxt\tproposition 1\tproposition 2\tproposition N");

			for (Question question : this.lstQuestions)
			{   
				String nomFichierTXT = "ressources/" + question.getRessource().getCode() + " " 
													 + question.getRessource().getNom () + "/" 
				                                     + question.getNotion().   getNom () + "/" 
													 +"question.txt";
				System.out.println(nomFichierCSV);
				System.out.println(nomFichierTXT);

				pw2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichierTXT), "UTF8"));



				pw .print(question.getRessource   ().getCode  () + "\t");
				pw .print(question.getRessource   ().getNom   () + "\t");
				pw .print(question.getNotion      ().getNom   () + "\t");
				pw .print(question.getDifficulte  ().getValeur() + "\t");
				pw .print(question.getType        ().getValeur() + "\t");
				pw .print(question.getTemps       ()             + "\t");
				pw .print(question.getNote        ()             + "\t");
				pw .print(nomFichierTXT                          + "\t");
				pw2.print(question.getIntitule    ()             + "\n");
				pw2.print(question.getExplication ()                   );

				switch (question.getType())
				{
					case QCM -> 
					{
						PropositionQCM propQCM;
						qQCM = (QCM) question;
						pw.print(qQCM.isUnique() + "\t");
						List<Proposition> lstProp = qQCM.getPropositions();
						for (Proposition prop : lstProp)
						{
							 propQCM = (PropositionQCM) prop;

							if (propQCM.estReponse()) pw.print("V:");

							else pw.print("F:");

							pw.print(propQCM.getText() + "\t" );
							

						}


					}

					case ELIMINATION -> 
					{
						PropositionElimination propElim;

						qElim =  (Elimination) question;
						List<Proposition> lstProp = qElim.getPropositions();
						for (Proposition prop : lstProp)
						{
							propElim = (PropositionElimination) prop;

							if (propElim.estReponse()) pw.print("V:");

							else pw.print("F:");
							pw.print(propElim.getOrdreElim  () + ":" );
							pw.print(propElim.getNbPtsPerdus() + ":");
							pw.print(propElim.getText		() + "\t");
						}

					}

					case ASSOCIATION -> 
					{
						PropositionAssociation propAsso;
						qAsso = (Association) question;
						List<Proposition> lstProp = qAsso.getPropositions();
						for (Proposition prop : lstProp) 
						{
							propAsso = (PropositionAssociation) prop;
							pw.print(propAsso.getTextGauche() + "\t" );
							pw.print(propAsso.getTextDroite() + "\t");	
						}
					}
				}

				pw.print("\n");
				pw2.print("\n");

				pw2.close();
				
			}

			pw.close();
			

			
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
	 * Modifie une question
	 * 
	 * @param question
	 * @param critere
	 * @param modif
	 * @return
	 */
	public boolean modifierQuestion(Question question, String critere, Object modif)
	{
		if (question == null) return false; // Si la question est null     -> renvoie faux
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
		return true;
	}

	/**
	 * Supprime une question
	 * 
	 * @param question
	 * @return boolean si oui ou non la suppression de la question a été effectué
	 */
	public boolean supprimerQuestion(Question question)
	{
		if (question == null) return false; // N'a pas trouvé la question

		for (int cpt = 0 ; cpt < this.lstQuestions.size() ; cpt++)
		{
			if (this.lstQuestions.get(cpt) == question)
			{
				this.lstQuestions.remove(cpt);
			}
		}

		return true;
	}

	/**
	 * Créer une pièce jointe
	 */
	public void creerPieceJointe(String cheminFichierOriginal, Question question)
	{
		for (int cpt = 0 ; cpt < this.lstQuestions.size() ; cpt++)
		{
			if (this.lstQuestions.get(cpt) == question)
				question.ajouterPieceJointe(new PieceJointe(cheminFichierOriginal, "ressources/" + question.getRessource() +
				                                                                   "/question " + (cpt + 1) + "/complément"));
		}
	}

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

		BanqueDeQuestions banque;
		BanqueDeRessources banqueRessources;
		QCMBuilder qcmBuilder;

		banqueRessources = new BanqueDeRessources();




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
		banque = new BanqueDeQuestions(new QCMBuilder());


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
	}
}
