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
                Question question = null;
                switch (question.getType()) {
					case QCM:
				
					case ELIMINATION:

					case ASSOCIATION:

					default:
						break;
				}

                if (question != null) {
                    ajouterQuestion(question);
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

			bw.write("ID;Ressource;Notion;Type;Intitule;Explication;temps;note;Prop1...propn \n");
			

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
				/*for (Proposition propositon : question.getPropositions()) {
					bw.write(proposition.getTexte() + ";");
				}*/
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

		QCM qcm = new QCM(0, "Quel est le plus grand océan ?", "En effet, de par sa superficie, l'océan Pacifique est le plus grand.", Difficulte.FACILE, r1, n1, 30, 5,
				Arrays.asList("Pacifique", "Atlantique", "Arctique", "Indien"), Arrays.asList("Pacifique"));
		banque.ajouterQuestions(qcm);

		Association assoc = new Association(1, "Associez les éléments", "France -> Paris / Allemagne -> Berlin.", Difficulte.MOYEN, r1, n1, 60, 10);
		assoc.ajouterProposition("France");
		assoc.ajouterReponse("Paris");
		assoc.ajouterProposition("Allemagne");
		assoc.ajouterReponse( "Berlin");
		banque.ajouterQuestions(assoc);


		Elimination elim = new Elimination(2, "Quel est le plus grand océan ?",
		"Toujours l'océan Pacifique.", Difficulte.DIFFICILE, r1, n1, 45, 8,Arrays.asList("Pacifique", "Atlantique", "Indien", "Arctique"), "Pacifique", Arrays.asList(3,2),Arrays.asList(2, 3));
		banque.ajouterQuestions(elim);

		banque.sauvegarderQuestions("lstQuestions.csv","lstQuestions.txt");
		banque.supprimerQuestion(0);
		banque.supprimerQuestion(1);
		banque.supprimerQuestion(2);

		System.out.println("Les lstQuestions ont été écrites dans le fichier RTF.");

		banque.lireQuestions("lstQuestions.rtf");
		System.out.println("Les lstQuestions ont été lues depuis le fichier RTF.");
		
	}
}

