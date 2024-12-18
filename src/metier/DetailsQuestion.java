package metier;

import metier.entite.*;
import metier.entite.question.Difficulte;

public record DetailsQuestion(Ressource ressource, Notion notion, Difficulte difficulte, int temps, double note, String intitule, String explication)
{
	
}