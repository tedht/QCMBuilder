package metier;

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
import metier.entite.question.Elimination;
import metier.entite.question.Qcm;
import metier.entite.question.Question;

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


	// Méthode utilitaire pour nettoyer une valeur extraite
	private String nettoyerValeur(String valeur)
	{
		return valeur.replaceAll("\\\\[a-zA-Z]+", "").trim();
	}

	public void lireQuestions(String cheminFichier) 
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(cheminFichier));

			String ligne;
			int    cpt = 0;
			BanqueDeRessources banque = new BanqueDeRessources();
			Question question = null;

			while ((ligne = br.readLine()) != null) 
			{
				ligne = nettoyerValeur(ligne);

				if (ligne.startsWith("Type")) 
				{
					if (question != null) 
					{ // Ajouter l'ancienne question
						ajouterQuestions(question);
					}

					// Création de la nouvelle question (QCM, Association, ou Elimination)
					if (ligne.startsWith("Type : QCM")) 
					{
						System.out.println("qcm");
						List<String> liste = new ArrayList<>();

						question = new Qcm(cpt, "", "", null, null, null, 0, 0, liste, liste);
					} 
					else if (ligne.startsWith("Type : Elimination")) 
					{
						System.out.println("elim");
						question = new Elimination(cpt, "", "", null, null, null, 0, 0,  null, "", null, null);

					} 
					else if (ligne.startsWith("Type : Association")) 
					{
						System.out.println("asso");
						question = new Association(cpt, "", "", null, null, null, 0, 0);
					}
					else 
					{
						System.out.println("slt");
					}
					cpt++;

				} 
				else if (ligne.startsWith("Intitule"    ))
				{
					question.setIntitule   (ligne.split(": ")[1].trim());
				} 
				else if (ligne.startsWith("Explication" ))
				{
					question.setExplication(ligne.split(": ")[1].trim());

				} 
				else if (ligne.startsWith("Difficulte"  ))
				{
					question.setDifficulte (ligne.split(": ")[1].trim());
				} 
				else if (ligne.startsWith("Ressource"   ))
				{
					String nomRessource =   ligne.split(": ")[1].trim();
					System.out.println(nomRessource);
					Ressource ressource = new Ressource(nomRessource);
					question.setRessource(ressource);

				} 
				else if (ligne.startsWith("Notion"      )) 
				{
					String nomNotion    =   ligne.split(": ")[1].trim();
					Notion notion = new Notion(nomNotion);
					question.setNotion(notion);

				} 
				else if (ligne.startsWith("Propositions")) 
				{
					List<String> propositions = Arrays.asList(ligne.split(": ")[1].split(";"));
					if (question instanceof Qcm) 
					{
						for (String proposition : propositions) 
						{
							((Qcm) question).ajouterProposition(proposition);
						}
					}
					else if (question instanceof Elimination) 
					{

					}
				} 
				else if (ligne.startsWith("Reponses"))
				{
					List<String> reponses = Arrays.asList(ligne.split(": ")[1].split(";"));
					if (question instanceof Qcm) 
					{
						((Qcm) question).setReponse(reponses);
					}
				} else if (ligne.startsWith("Temps")) 
				{
					question.setTemps(Integer.parseInt(ligne.split(": ")[1].trim()));
				} 
				else if (ligne.startsWith("Note")) 
				{
					question.setNote(Integer.parseInt(ligne.split(": ")[1].trim()));
				} 
				else if (ligne.startsWith("Association")) 
				{
					String[] parts = ligne.split(": ")[1].split(" -> ");
					
					if (question instanceof Association) 
					{
						((Association) question ).ajouterProposition(parts[0]);
						((Association) question ).ajouterReponse    (parts[1]);
					}
				} 
				else if (ligne.startsWith("Ordre d'élimination")) 
				{
					List<Integer> ordre = Arrays
							.stream(ligne.split(": ")[1].replace("[", "").replace("]", "").split(",")).map(String::trim)
							.map(Integer::parseInt).toList();
					if (question instanceof Elimination) 
					{
						((Elimination) question).setOrdreElimination(ordre);
					}
				} 
				else if (ligne.startsWith("Points perdus")) 
				{
					// Nettoyage des crochets et conversion en liste d'entiers
					List<Integer> points = Arrays
							.stream(ligne.split(": ")[1].replace("[", "").replace("]", "").split(",")).map(String::trim)
							.map(Integer::parseInt).toList();
					if (question instanceof Elimination) 
					{
						((Elimination) question).setNbPtPerdu(points);
					}
				}
			}

			if (question != null) 
			{
				ajouterQuestions(question);
			}

			for (Question q : this.lstQuestions)
			{
				System.out.println(q);
				if (q instanceof Qcm) 
				{
					q = (Qcm) q;				
					System.out.println(q);
				}
				else if (q instanceof Elimination)
				{
					q = (Elimination) q;
					System.out.println(q);
				}
				else if (q instanceof Association) 
				{
					q = (Association) q;
					System.out.println(q);
				}
			}
		} catch (IOException e) 
		{
			System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
		}
	}


	/* Ecriture du fichier RTF qui contient les lstQuestions */
	public void sauvegarderQuestions(String cheminFichier) 
	{
		boolean nouveauFichier = !(new File(cheminFichier).exists());

		File fichier = new File(cheminFichier);
		if (fichier.exists()) 
		{
        	
			if (fichier.delete()) 
			{
				nouveauFichier = true;
           		System.out.println("Fichier supprimé : " + cheminFichier);
			}
		}

		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichier, true));

			// Si c'est un nouveau fichier, ajouter l'en-tête RTF
			if (nouveauFichier) 
			{
				bw.write("{\\rtf1\\ansi\\ansicpg65001\\deff0\n");
				bw.write("{\\fonttbl{\\f0\\fnil\\fcharset0 Arial;}\n}");
			}

			for (Question question : lstQuestions) 
			{
				if (question instanceof Qcm)
				{
					bw.write("\n\nType : QCM\\line\n");
				} 
				else if (question instanceof Association) 
				{
					bw.write("\n\nType : Association\\line\n");
				} 
				else if (question instanceof Elimination) 
				{
					bw.write("\n\nType : Elimination\\line\n");
				}
				
				bw.write("ID           : " + question.getId         ()          + "\\line\n");
				bw.write("Ressource    : " + question.getRessource  ().getNom() + "\\line\n");
				bw.write("Notion       : " + question.getNotion     ().getNom() + "\\line\n"); 
				bw.write("Intitule     : " + question.getIntitule   ()          + "\\line\n");
				bw.write("Explication  : " + question.getExplication()          + "\\line\n");
				bw.write("Difficulte   : " + question.getDifficulte ()          + "\\line\n");

				if (question instanceof Qcm) 
				{
					Qcm qcm = (Qcm) question;
					bw.write("Propositions : " + String.join(";", qcm.getProposition()) + "\\line\n");
					bw.write("Reponses     : " + String.join(";", qcm.getReponse    ()) + "\\line\n");
				} 
				else if (question instanceof Association) 
				{
					Association assoc = (Association) question;
					/*for (List<String> pair : assoc.getLiaison()) {
						bw.write("Association : " + pair.get(0) + " -> " + pair.get(1) + "\\line\n");
					}*/
				} 
				else if (question instanceof Elimination) 
				{
					Elimination elim = (Elimination) question;
					bw.write("Propositions        : " + String.join(";", elim.getProposition()) + "\\line\n");
					bw.write("Reponse             : " + elim.getReponse         () + "\\line\n");
					bw.write("Ordre d'élimination : " + elim.getOrdreElimination() + "\\line\n");
					bw.write("Points perdus       : " + elim.getNbPtPerdu       () + "\\line\n");
				}

				bw.write("Temps : " + question.getTemps() + "\\line\n");
				bw.write("Note  : " + question.getNote() + "\\line\n");
				bw.write("");
			}
		} 
		catch (IOException e) 
		{
			System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
		}
	}

	public void fermerRTF(String cheminFichier)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichier, true)))
		{
			bw.write("}");
		} 
		catch (IOException e)
		{
			System.err.println("Erreur lors de la fermeture du fichier RTF : " + e.getMessage());
		}
	}


	public boolean ajouterQuestions(Question question)
	{
		if (question == null) return false;

		this.lstQuestions.add(question);
		return true;
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

		Qcm qcm = new Qcm(0, "Quel est le plus grand océan ?", "Choisissez une réponse.", "F", r1, n1, 30, 5,
				Arrays.asList("Pacifique", "Atlantique", "Arctique", "Indien"), Arrays.asList("Pacifique"));
		banque.ajouterQuestions(qcm);

		Association assoc = new Association(1, "Associez les éléments", "Reliez les pays et leurs capitales.", "M", r1, n1, 60, 10);
		assoc.ajouterProposition("France");
		assoc.ajouterReponse("Paris");
		assoc.ajouterProposition("Allemagne");
		assoc.ajouterReponse( "Berlin");
		banque.ajouterQuestions(assoc);


		Elimination elim = new Elimination(2, "Éliminez les mauvaises réponses",
		"Sélectionnez la bonne réponse après avoir éliminé.", "D", r1, n1, 45, 8,
		Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option B", Arrays.asList(3, 1, 4, 2),
		Arrays.asList(2, 3, 5, 7));
		banque.ajouterQuestions(elim);

		banque.sauvegarderQuestions("lstQuestions.rtf");
		banque.supprimerQuestion(0);
		banque.supprimerQuestion(1);
		banque.supprimerQuestion(2);
		banque.fermerRTF("lstQuestions.rtf");

		System.out.println("Les lstQuestions ont été écrites dans le fichier RTF.");

		banque.lireQuestions("lstQuestions.rtf");
		System.out.println("Les lstQuestions ont été lues depuis le fichier RTF.");
		
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
}

