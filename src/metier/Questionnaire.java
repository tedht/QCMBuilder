package metier;

import java.util.List;

public class Questionnaire 
{
    Ressource ressource;
    List<Notion> notions;
    boolean chronometre;
    List<Question> questions;
    
        public Questionnaire(Ressource ressource, List<Notion> notions , boolean chronometre, List<Question> questions) 
        {
            this.ressource = ressource;
            this.chronometre = chronometre;
            this.notions = notions;
            this.questions = questions

        }

        public Ressource getRessource() {return ressource;}

        public List<Notion> getNotions() {return notions;}

        public boolean isChronometre() {return chronometre;}

        public List<Question> getQuestions() {return questions;}

        public void setQuestions(List<Question> questions) {this.questions = questions;}

        public void setChronometre(boolean chronometre) {this.chronometre = chronometre;}

        public void setNotions(List<Notion> notions) {this.notions = notions;}

        public void setRessource(Ressource ressource) {this.ressource = ressource;}

        public void ajouterNotion(Notion notion)
        {
            this.notions.add(Notion notion);
        }

        public void supprimerNotion(Notion autre)
        {

            this.notions.remove(autre);
            
        }

        public void ajouterQuestions(Notion notion, String difficulte, int nbrQuestions)
        {
            
        }
}
