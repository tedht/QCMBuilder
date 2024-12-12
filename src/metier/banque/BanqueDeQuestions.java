package metier.banque;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import controleur.Controleur;
import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Association;
import metier.entite.question.Difficulte;
import metier.entite.question.Elimination;
import metier.entite.question.PropositionElimination;
import metier.entite.question.PropositionQCM;
import metier.entite.question.QCM;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

/** Classe BanqueDeQuestions
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeQuestions 
{
	/* Attributs */
	private List<Question> lstQuestions;


	/* Constructeur */
	public BanqueDeQuestions()
	{
		this.lstQuestions = new ArrayList<Question>();
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


	public void lireQuestions(String cheminFichierCSV) {
		try (BufferedReader brCSV = new BufferedReader(new FileReader(cheminFichierCSV))) {
			String ligneCSV;

			// Lecture de l'en-tête CSV pour l'ignorer
			brCSV.readLine();

			// Lecture des questions à partir du fichier CSV
			while ((ligneCSV = brCSV.readLine()) != null) {
				String[] elements = ligneCSV.split(";");
				int          id               = Integer.parseInt                     (elements[0]) ;
				Ressource    ressource        = new Ressource                        (elements[1]) ;
				Notion       notion           = new Notion                           (elements[2]) ;
				TypeQuestion type             = TypeQuestion.fromInt(Integer.parseInt(elements[3]));
				Difficulte   difficulte       = Difficulte.fromInt  (Integer.parseInt(elements[4]));
				String       cheminFichierTXT =                                       elements[5]  ;
				int          temps            = Integer.parseInt                     (elements[6]) ;
				int          note             = Integer.parseInt                     (elements[7]) ;

				// Lecture du fichier TXT pour récupérer l'intitulé et l'explication
				String intitule    = "";
				String explication = "";

				try (BufferedReader brTXT = new BufferedReader(new FileReader(cheminFichierTXT))) {
					String ligneTXT;
					while ((ligneTXT = brTXT.readLine()) != null) {

						String[] elementsTXT = ligneTXT.split(" | ");

						if (Integer.parseInt(elementsTXT[0]) == id) {
							intitule    = elementsTXT[1];
							explication = elementsTXT[2];
							break;
						}
					}
				} catch (IOException e) {
					System.err.println("Erreur lors de la lecture du fichier TXT : " + cheminFichierTXT);
				}

				// Créer l'objet Question en fonction du type
				switch (type) {
					case QCM:
						QCM qQcm = new QCM(id, ressource, notion, difficulte, temps, note);

						for (int i = 8; i < elements.length; i++)
						{
							String[] prop = elements[i].split(",");
							if (prop[1]== "true") {qQcm.ajouterProposition(prop[0], true );}
							else                  {qQcm.ajouterProposition(prop[0], false);}
						}
						qQcm.setIntitule(intitule);
						qQcm.setExplication(explication);

						ajouterQuestions(qQcm);
						break;
				
					case ELIMINATION:
						Elimination qElim = new Elimination(id, ressource, notion, difficulte, temps, note);

						for (int i = 8; i < elements.length; i++) {

							String[] prop      = elements[i].split(",");

							String enonce      = prop[0];
							String reponse     = prop[1];
							int numElimination = Integer.parseInt(prop[2]);
							int nbPtPerdu      = Integer.parseInt(prop[3]);

							if (enonce == "true"){qElim.ajouterProposition(enonce, true, numElimination, nbPtPerdu );}
							else                 {qElim.ajouterProposition(enonce, false, numElimination, nbPtPerdu);}	
							
						}

						qElim.setIntitule(intitule);
						qElim.setExplication(explication);

						ajouterQuestions(qElim);
						break;

					case ASSOCIATION:

					default:
						break;
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier CSV : " + cheminFichierCSV);
		} catch (NumberFormatException e) {
			System.err.println("Erreur de formatage dans le fichier CSV : " + e.getMessage());
		}
	}


	/* Ecriture des fichiers csv et txt qui contiennent la lstQuestions */
	public void sauvegarderQuestions(String cheminFichierCSV,String cheminFichierTXT) 
	{
		boolean nouveauFichier = !(new File(cheminFichierCSV).exists());

		File fichier = new File(cheminFichierCSV);
		if (fichier.exists()) 
		{
        	
			if (fichier.delete()) 
			{
				nouveauFichier = true;
           		System.out.println("Fichier supprimé : " + cheminFichierCSV);
			}
		}
		File fichier2 = new File(cheminFichierTXT);
		if (fichier2.exists())
		{

			if (fichier2.delete())
			{
				nouveauFichier = true;
				System.out.println("Fichier supprimé : " + cheminFichierTXT);
			}
		}

		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichierCSV, true));
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(cheminFichierTXT, true));

			bw.write("ID;Ressource;Notion;Type;Dificulte;Intitule & Explication;temps;note;Prop1...propn \n");
			

			for (Question question : lstQuestions) 
			{
				bw.write(question.getId                      () + ";");
				bw.write(question.getRessource   ().getNom   () + ";");
				bw.write(question.getNotion      ().getNom   () + ";");
				bw.write(question.getType().getValeur() + ";");
				bw.write(question.getDifficulte  ().getValeur() + ";");
				bw.write(cheminFichierTXT                       + ";");
				
				bw.write(question.getTemps                   () + ";");
				bw.write(question.getNote                    () + ";");
				switch (question.getType()) {
					case QCM:
						QCM qQCM = (QCM) question;
						List<PropositionQCM> lstPropQCM = qQCM.getProposition();
						for (PropositionQCM propositionQCM : lstPropQCM) {
							bw.write(propositionQCM.getTextProposition() +",");
							if (propositionQCM.getReponse()) { bw.write("true" + ";");}
							else { bw.write("false" + ";");}
						}
						break;
						
					case ELIMINATION:
						Elimination qElim = (Elimination) question;
						List<PropositionElimination> lstPropELIM = qElim.getProposition();
						for (PropositionElimination propositionElim : lstPropELIM)
						{
							bw.write(propositionElim.getTextProposition() + ",");
							if (propositionElim.getReponse())
							{
								bw.write("true" + ",");
							}
							else
							{
								bw.write("false" + ",");
							}
							bw.write(propositionElim.getNumElimination() + ",");
							bw.write(propositionElim.getNbPtPerdu() + ";");
						}
						break;

					case ASSOCIATION:

						Association qAsso = (Association) question;
						/*List<PropositionQCM> lstProp = qAsso.getProposition();
						for (PropositionQCM propositionQCM : lstProp)
						{
							bw.write("(" + propositionQCM.getTextProposition() + ",");
							if (propositionQCM.getReponse())
							{
								bw.write("true" + ");");
							}
							else
							{
								bw.write("false" + ")");
							}
						}*/
				
					default:
						break;
				}
				bw.write("\n");

				bw2.write(question.getId         () + " | ");
				bw2.write(question.getIntitule   () + " | ");
				bw2.write(question.getExplication() + "\n");
				
			}

			bw.close();
			bw2.close();
			
		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
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
		BanqueDeQuestions banque = new BanqueDeQuestions();

		Ressource r1 = new Ressource("R3.01");
		Notion n1 = new Notion("Algorithmique");

		QCM qcm = new QCM(0,r1,n1, Difficulte.FACILE, 30, 5);
		qcm.setIntitule("Quel est l'océan le plus grand du monde ?");
		qcm.setExplication("Le Pacifique de par sa superficie gargantuesque.");
		qcm.ajouterProposition("Pacifique", true);
		qcm.ajouterProposition("Atlantique", false);
		qcm.ajouterProposition("Arctique", false);
		qcm.ajouterProposition("Indien", false);


		banque.ajouterQuestions(qcm);

		/*Association assoc = new Association(1, "Associez les éléments", "France -> Paris / Allemagne -> Berlin.", Difficulte.MOYEN, r1, n1, 60, 10);
		assoc.ajouterProposition("France");
		assoc.ajouterReponse("Paris");
		assoc.ajouterProposition("Allemagne");
		assoc.ajouterReponse( "Berlin");
		banque.ajouterQuestions(assoc);*/


		Elimination elim = new Elimination(1,r1,n1,Difficulte.DIFFICILE,20,5);
		elim.setIntitule("De couleur est le cheval blanc d'Henri IV ?");
		elim.setExplication("C'était écrit dans le question !!");

		elim.ajouterProposition("Noir", false, 1, 2);
		elim.ajouterProposition("Rouge", false, 2, 5);
		elim.ajouterProposition("Blanc", true, 0, 0);

		banque.ajouterQuestions(elim);

		banque.sauvegarderQuestions("lstQuestions.csv","lstQuestions.txt");
		banque.supprimerQuestion(0);
		banque.supprimerQuestion(2);


		System.out.println("Les lstQuestions ont été écrites dans le fichier RTF.");

		banque.lireQuestions("lstQuestions.csv");
		System.out.println("Les lstQuestions ont été lues depuis le fichier RTF.");
		
		banque.sauvegarderQuestions("lst2.csv", "lst2.txt");
		
	}
}

