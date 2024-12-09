package metier;

import java.util.List;

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
		public void setQuestions  (List<Question> questions  ) {this.questions   = questions;  }
		public void setChronometre(boolean        chronometre) {this.chronometre = chronometre;}
		public void setNotions    (List<Notion>   notions    ) {this.notions     = notions;    }
		public void setRessource  (Ressource      ressource  ) {this.ressource   = ressource;  }

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
