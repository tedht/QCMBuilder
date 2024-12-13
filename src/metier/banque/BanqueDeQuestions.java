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


	/* Constructeur */
	public BanqueDeQuestions(QCMBuilder qcmBuilder)
	{
		this.qcmBuilder   = qcmBuilder;
		this.lstQuestions = new ArrayList<Question>();

		this.lireQuestions("data/questions.csv");
	}

	public List<Question> getQuestions()
	{
		return lstQuestions;
	}

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

	public boolean ajouterQuestions(Question question)
	{
		if (question == null) return false;

		this.lstQuestions.add(question);
		return true;
	}

	/* Lecture du fichier CSV qui contient les questions */
	public void lireQuestions(String nomFichier)
	{
		Scanner scEnreg, scDonnee;
		String enreg;

		int id  = 0;
		//int ind = 0;

		Ressource    ressource;
		Notion       notion;
		Difficulte   difficulte;
		TypeQuestion typeQuestion;
		int          temps;
		int          note;
		String       intitule;
		String       explication;
		//String       proposition;
		Question     question;

		try
		{
			scEnreg = new Scanner( new FileInputStream(nomFichier), "UTF8");

			scEnreg.nextLine();

			while (scEnreg.hasNextLine())
			{
				enreg = scEnreg.nextLine();

				scDonnee = new Scanner(enreg);
				scDonnee.useDelimiter("\t");

				ressource    = this.qcmBuilder.getRessource(scDonnee.next());
				notion       = ressource.getNotion(scDonnee.next());
				difficulte   = Difficulte.fromInt(scDonnee.nextInt());
				typeQuestion = TypeQuestion.fromInt(scDonnee.nextInt());
				temps        = scDonnee.nextInt();
				note         = scDonnee.nextInt();
				intitule     = scDonnee.next();
				explication  = scDonnee.next();
				
				switch (typeQuestion) 
				{
					case QCM ->
					{
						question = new QCM(id, ressource, notion, difficulte, temps, note);
						/*
						ind = 0;
						while(scDonnee.hasNext())
						{
							proposition = scDonnee.next();
							question.ajouterProposition(proposition.substring(2));
							if(proposition.charAt(0) == 'V')
							{
								((QCM)question).ajouterIndReponse(ind);
							}
							ind++;
						}
						*/
					}
					case ASSOCIATION ->
					{
						question = new Association(id, ressource, notion, difficulte, temps, note);
					}
					case ELIMINATION ->
					{
						question = new Elimination(id, ressource, notion, difficulte, temps, note);
					}
					default ->
					{ 
						question = new QCM(id, ressource, notion, difficulte, temps, note);
					}
				}

				question.setIntitule(intitule);
				question.setExplication(explication);
				this.lstQuestions.add(question);

				scDonnee.close();
				id++;
			}
			scEnreg.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/* Ecriture du fichier CSV qui contient les questions */
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

			pw.println("ressource\tnotion\tdifficulte\ttype\ttemps\tnote\tintitulé\texplication\tproposition 1\tproposition 2\tproposition N");

			for (Question question : this.lstQuestions)
			{   
				pw .print(question.getId          ()             + "\t");
				pw2.print(question.getId          ()             + "\t");
				pw .print(question.getRessource   ().getNom   () + "\t");
				pw .print(question.getNotion      ().getNom   () + "\t");
				pw .print(question.getDifficulte  ().getValeur() + "\t");
				pw .print(question.getType        ().getValeur() + "\t");
				pw .print(question.getTemps       ()             + "\t");
				pw .print(question.getNote        ()             + "\t");
				pw2.print(question.getIntitule    ()             + "\t");
				pw2.print(question.getExplication ()             + "\t");

				switch (question.getType())
				{
					case QCM -> 
					{
						PropositionQCM propQCM;
						qQCM = (QCM) question;
						pw.print(qQCM.isUnique());
						List<Proposition> lstProp = qQCM.getPropositions();
						for (Proposition prop : lstProp)
						{
							 propQCM = (PropositionQCM) prop;

							if (propQCM.estReponse()) pw.print("V:");

							pw.print("F:");
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
							pw.print(propElim.getText       () + " " );
							pw.print(propElim.estReponse    () + " " );
							pw.print(propElim.getOrdreElim  () + " " );
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
							pw.print(propAsso.getTextGauche() + " " );
							pw.print(propAsso.getTextDroite() + "\t");	
						}
					}
				}

				pw.print("\n");
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


	public boolean modifierQuestion(int id, String critere, Object modif)
	{
		Question question;

		question = null;
		for (Question q : this.lstQuestions)
			if (q.getId() == id)
				question = q;

		if (question == null) return false; // N'a pas trouvé la question

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

	public boolean supprimerQuestion(int id)
	{
		Question question;


		question = null;
		for (Question q : this.lstQuestions)
			if (q.getId() == id)
				question = q;

		if (question == null) return false; // N'a pas trouvé la question

		this.lstQuestions.remove(id);
		return true;		
	}

	public static void main(String[] args) {
		
	}
}

