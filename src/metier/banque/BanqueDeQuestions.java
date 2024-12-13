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
import metier.entite.Notion;
import metier.entite.Ressource;

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
	private QCMBuilder     qcmBuilder;
	private List<Question> lstQuestions;


	/**
	 * Constructeur de la classe BanqueDeQuestions
	 * 
	 * @param qcmBuilder le constructeur de QCM
	 */
	public BanqueDeQuestions(QCMBuilder qcmBuilder)
	{
		this.qcmBuilder   = qcmBuilder;
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
		List<Question> lstQuestions = new ArrayList<Question>();

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
	 * Ajoute une question à la banque de questions
	 * 
	 * @param question la question à ajouter
	 * @return boolean
	 */
	public boolean ajouterQuestions(Question question)
	{
		if (question == null) return false;

		this.lstQuestions.add(question);
		return true;
	}

	/**
	* Lecture du fichier CSV qui contient les questions
	*
	* @param nomFichierCSV le chemin du fichier CSV
	* @param nomFichierTXT le chemin du fichier TXT
	*/
	public void lireQuestions(String nomFichierCSV, String nomFichierTXT)
	{
		Scanner scEnreg, scDonnees, scTexte;

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

		try
		{
			scEnreg = new Scanner(new FileInputStream(nomFichierCSV), "UTF8");
			scEnreg.nextLine();

			while (scEnreg.hasNextLine())
			{
				scDonnees = new Scanner(scEnreg.nextLine());

				scDonnees.useDelimiter("\t");

				//ressource        = this.qcmBuilder.getRessource(scEnreg.next());
				ressource        = new Ressource       (scDonnees.next());
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
				case QCM -> {
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
				case ASSOCIATION -> {
					question = new Association(ressource, notion, difficulte, temps, note);
					while (scDonnees.hasNext())
					{
						String gauche = scDonnees.next();
						String droite = scDonnees.next();
						((Association) question).ajouterProposition(new PropositionAssociation(gauche, droite));
					}
				}
				case ELIMINATION -> {
					question = new Elimination(ressource, notion, difficulte, temps, note);
					while (scDonnees.hasNext())
					{
						String  texte       =                    scDonnees.next       ();
						boolean reponse     =                    scDonnees.nextBoolean();
						int     ordreElim   =                    scDonnees.nextInt    ();
						double  nbPtsPerdus = Double.parseDouble(scDonnees.next       ());
						((Elimination) question)
								.ajouterProposition(new PropositionElimination(texte, reponse, ordreElim, nbPtsPerdus));
					}
				}
				default -> throw new IllegalArgumentException("Type de question inconnu: " + typeQuestion);
				}
				default -> throw new IllegalArgumentException("Type de question inconnu: " + typeQuestion);
				}

				question.setIntitule   (intitule);
				question.setExplication(explication);
				
				this.lstQuestions.add(question);

				scDonnees.close();

			}
			scEnreg.close();
		} catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier n'a pas été trouvé : " + fnfe.getMessage());
		}
	}
	/**
	 * Sauvegarde des questions dans un fichier CSV et un fichier TXT
	 * 
	 * @param nomFichierCSV le chemin du fichier CSV
	 * @param nomFichierTXT le chemin du fichier TXT
	 */
	public void sauvegarderQuestions(String nomFichierCSV,String nomFichierTXT)
	{
		PrintWriter pw;
		PrintWriter pw2;
		QCM         qQCM;
		Elimination qElim;
		Association qAsso;

		try
		{
			pw  = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichierCSV), "UTF8" ));
			pw2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichierTXT), "UTF8" ));

			pw.println("ressource\tnotion\tdifficulte\ttype\ttemps\tnote\tcheminfichiertxt\tproposition 1\tproposition 2\tproposition N");

			for (Question question : this.lstQuestions)
			{   
				pw .print(question.getRessource   ().getNom   () + "\t");
				pw .print(question.getNotion      ().getNom   () + "\t");
				pw .print(question.getDifficulte  ().getValeur() + "\t");
				pw .print(question.getType        ().getValeur() + "\t");
				pw .print(question.getTemps       ()             + "\t");
				pw .print(question.getNote        ()             + "\t");
				pw .print(nomFichierTXT                          + "\t");
				pw2.print(question.getIntitule    ()             + "\t");
				pw2.print(question.getExplication ()             + "\t");

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
							pw.print(propElim.getText       () + "\t" );
							pw.print(propElim.estReponse    () + "\t" );
							pw.print(propElim.getOrdreElim  () + "\t" );
							pw.print(propElim.getNbPtsPerdus() + "\t");
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

				pw. print("\n");
				pw2.print("\n");
			}

			pw. close();
			pw2.close();
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
	 * @return boolean
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

	public static void main(String[] args)
	{
		// Initialisation des ressources et notions
		Ressource r1 = new Ressource("Ressource 1");
		Ressource r2 = new Ressource("Ressource 2");

		Notion n1 = new Notion("Notion 1");
		Notion n2 = new Notion("Notion 2");
		Notion n3 = new Notion("Notion 3");

		r1.ajouterNotion(n1);
		r1.ajouterNotion(n2);
		r2.ajouterNotion(n3);

		// Création de questions de type QCM
		QCM q1 = new QCM(r1, n1, Difficulte.FACILE, 10, 10, true);
		q1.setIntitule("Quelle est la capitale de la France ?");
		q1.setExplication("La capitale de la France est Paris.");
		q1.ajouterProposition(new PropositionQCM("Paris", true));
		q1.ajouterProposition(new PropositionQCM("Londres", false));
		q1.ajouterProposition(new PropositionQCM("Berlin", false));

		// Création de questions de type Élimination
		Elimination q2 = new Elimination(r2, n3, Difficulte.MOYEN, 20, 15);
		q2.setIntitule("De quelle couleur est le cheval blanc d'Henri IV ?");
		q2.setExplication("La réponse est évidente, mais il est intéressant de poser la question.");
		q2.ajouterProposition(new PropositionElimination("Noir", false, 1, 2.0));
		q2.ajouterProposition(new PropositionElimination("Gris", false, 2, 1.0));
		q2.ajouterProposition(new PropositionElimination("Blanc", true, 0, 0.0));

		// Création de questions de type Association
		Association q3 = new Association(r1, n2, Difficulte.DIFFICILE, 30, 20);
		q3.setIntitule("Associez les pays à leurs capitales.");
		q3.setExplication("Exercice sur les capitales européennes.");
		q3.ajouterProposition(new PropositionAssociation("France", "Paris"));
		q3.ajouterProposition(new PropositionAssociation("Allemagne", "Berlin"));
		q3.ajouterProposition(new PropositionAssociation("Espagne", "Madrid"));

		// Initialisation de la banque de questions
		BanqueDeQuestions banque = new BanqueDeQuestions(new QCMBuilder());

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
		String cheminCSV = "data/questions.csv";
		String cheminTXT = "data/questions.txt";
		banque.sauvegarderQuestions(cheminCSV, cheminTXT);
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
		banque.lireQuestions(cheminCSV, cheminTXT);
		System.out.println("=== Questions après lecture des fichiers ===");
		for (Question question : banque.getQuestions())
		{
			System.out.println(question);
		}
	}
}
