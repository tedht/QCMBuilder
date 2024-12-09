package metier;

import java.util.ArrayList;
import java.util.List;


/** Classe Questionnaire
 * @author Equipe 03
 * @version 1.0 du 09/12/2024
 */
public class Questionnaire 
{
	private Ressource      ressource;
	private List<Notion>   notions;
	private boolean        chronometre;
	private List<Question> questions;
	
		/* Constructeur */
		public Questionnaire(Ressource ressource, List<Notion> notions , boolean chronometre) 
		{
			this.ressource   = ressource;
			this.chronometre = chronometre;
			this.notions     = notions;
			this.questions = new ArrayList<Question>();
		}

		/* Getter */
		public Ressource      getRessource () { return ressource;  }
		public List<Notion>   getNotions   () { return notions;    }
		public boolean        isChronometre() { return chronometre;}
		public List<Question> getQuestions () { return questions;  }

		/* Setter */
		public boolean setQuestions  (List<Question> questions  ) 
			{
				if (questions == null) return false;

				this.questions = questions;
				return true;
			}
		public boolean setChronometre(boolean chronometre) 
		{
			if (this.chronometre == chronometre) return false;
			this.chronometre = chronometre;
			return true;
		}

		public boolean setNotions (List<Notion> notions) 
		{
			if (notions == null) return false;
			this.notions = notions;
			return true;
		}

		public boolean setRessource  (Ressource ressource) 
		{
			if (ressource == null) return false;
			this.ressource = ressource;
			return true;
		}

		public void ajouterNotion(Notion autre)
		{
			if (!notions.contains(autre)) 
			{
				this.notions.add(autre);	
				return;
			}
			System.out.println("Notion déjà présente.");
			
		}

		public void supprimerNotion(Notion autre)
		{
			if (notions.contains(autre)) 
			{
				this.notions.remove(autre);	
			}
		}

		public void ajouterQuestions(Notion notion, String difficulte, int nbrQuestions)
		{
			for (int i = 0; i < nbrQuestions; i++) {
				//trop de trucs avec fichier rtf
			}
		}


		public String toString()
		{
			return "Questionnaire :\n" +
				   "   ressource : " + this.ressource   + "\n" +
				   "   questions : " + this.questions   + "\n" + 
				   "     notions : " + this.notions     + "\n" +
				   " chronometre : " + this.chronometre + "\n" ;
		}

		public static void main(String[] args) {
			Ressource r1 = new Ressource("r3.01");
			Notion n1 = new Notion("algo");
			List<Notion> l1 = new ArrayList<>();
			l1.add(n1);

			Questionnaire q1 = new Questionnaire(r1, l1,false);
			System.out.println(q1);
			Notion n2 = new Notion("ide");
			q1.ajouterNotion(n2);
			System.out.println(q1);
		}
}
