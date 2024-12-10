package metier;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import controleur.Controleur;
import metier.Notion;
import metier.Ressource;
import metier.Question;

/** Classe BanqueDeQuestions
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeQuestions 
{
	/* Attributs */
	private Controleur     ctrl;
	private List<Question> questions;


	/* Constructeur */
	public BanqueDeQuestions(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.questions = new ArrayList<Question>();
	}

	/* Lecture du fichier RTF qui contient les questions */
	public void lireQuestions(String nomFichier)
	{
		Scanner sc;

		String enreg;


		try
		{
			sc = new Scanner( new FileInputStream(nomFichier), "UTF-8");


			while (sc.hasNextLine())
			{
				enreg = sc.nextLine();

				// TODO : Lecture du fichier RTF
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/* Ecriture du fichier RTF qui contient les questions */
	public void sauvegarderQuestions(String cheminFichier) {
        boolean nouveauFichier = !(new File(cheminFichier).exists());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichier, true))) {
            // Si c'est un nouveau fichier, ajouter l'en-tête RTF
            if (nouveauFichier) {
                bw.write("{\\rtf1\\ansi\\ansicpg65001\\deff0\n");
                bw.write("{\\fonttbl{\\f0\\fnil\\fcharset0 Arial;}\n}");
            }

            for (Question question : questions) {
                bw.write("\n\nIntitule : " + question.getIntitule() + "\\line\n");
                bw.write("Explication : " + question.getExplication() + "\\line\n");
                bw.write("Difficulte : " + question.getDifficulte() + "\\line\n");

                if (question instanceof Qcm) {
                    Qcm qcm = (Qcm) question;
                    bw.write("Propositions : " + String.join(";", qcm.getProposition()) + "\\line\n");
                    bw.write("Reponses : " + String.join(";", qcm.getReponse()) + "\\line\n");
                } else if (question instanceof Association) {
                    Association assoc = (Association) question;
                    for (List<String> pair : assoc.getProposition()) {
                        bw.write("Association : " + pair.get(0) + " -> " + pair.get(1) + "\\line\n");
                    }
                } else if (question instanceof Elimination) {
                    Elimination elim = (Elimination) question;
                    bw.write("Propositions : " + String.join(";", elim.getProposition()) + "\\line\n");
                    bw.write("Reponse : " + elim.getReponse() + "\\line\n");
                    bw.write("Ordre d'élimination : " + elim.getOrdreElimination() + "\\line\n");
                    bw.write("Points perdus : " + elim.getNbPtPerdu() + "\\line\n");
                }

                bw.write("Temps : " + question.getTemps() + "\\line\n");
                bw.write("Note : " + question.getNote() + "\\line\n");
				bw.write("");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

	public void fermerRTF(String cheminFichier)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichier, true)))
		{
			bw.write("}");
		} catch (IOException e)
		{
			System.err.println("Erreur lors de la fermeture du fichier RTF : " + e.getMessage());
		}
	}


	public boolean ajouterQuestions(Question question)
	{
		if (question == null) return false;

		this.questions.add(question);
		return true;
	}

	public boolean modifierQuestion(int id, String critere, Object modif)
	{
		Question question;


		question = null;
		for (Question q : this.questions)
			if (q.getId() == id)
				question = q;

		if (question == null) return false; // N'a pas trouvé la question

		switch (critere)
		{
			case "difficulte" -> question.setDifficulte((String)    modif);
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
		for (Question q : this.questions)
			if (q.getId() == id)
				question = q;

		if (question == null) return false; // N'a pas trouvé la question

		this.questions.remove(id);
		return true;		
	}

	    public static void main(String[] args) {
		
		Controleur ctrl = new Controleur();
        BanqueDeQuestions banque = new BanqueDeQuestions(ctrl);

        Ressource r1 = new Ressource("R3.01");
        Notion n1 = new Notion("Algorithmique");

        Qcm qcm = new Qcm(1, "Quel est le plus grand océan ?", "Choisissez une réponse.", "Facile", r1, n1, 30, 5,
                Arrays.asList("Pacifique", "Atlantique", "Arctique", "Indien"), Arrays.asList("Pacifique"));
        banque.ajouterQuestions(qcm);

        Association assoc = new Association(2, "Associez les éléments", "Reliez les pays et leurs capitales.", "Moyen", r1, n1, 60, 10);
        assoc.ajouterAssociation("France", "Paris");
        assoc.ajouterAssociation("Allemagne", "Berlin");
        banque.ajouterQuestions(assoc);


        Elimination elim = new Elimination(3, "Éliminez les mauvaises réponses",
		"Sélectionnez la bonne réponse après avoir éliminé.", "Difficile", r1, n1, 45, 8,
		Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option B", Arrays.asList(3, 1, 4, 2),
		Arrays.asList(2, 3, 5, 7));
		banque.ajouterQuestions(elim);

		banque.sauvegarderQuestions("questions.rtf");
		banque.fermerRTF("questions.rtf");

        System.out.println("Les questions ont été écrites dans le fichier RTF.");
    }
}

