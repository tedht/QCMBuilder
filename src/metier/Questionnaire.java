package metier;

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
		public Questionnaire(Ressource ressource, List<Notion> notions , boolean chronometre, List<Question> questions) 
		{
			this.ressource   = ressource;
			this.chronometre = chronometre;
			this.notions     = notions;
			this.questions   = questions;

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
			this.notions.add(autre);
		}

		public void supprimerNotion(Notion autre)
		{

			this.notions.remove(autre);
			
		}

		public void ajouterQuestions(Notion notion, String difficulte, int nbrQuestions)
		{

		}

		public String toString()
		{
			return "Questionnaire :\n" +
				   "   ressource : " + this.ressource   + "\n" +
				   "   questions : " + this.questions   + "\n" + 
				   "     notions : " + this.notions     + "\n" +
				   " chronometre : " + this.chronometre + "\n" ;
		}
}
