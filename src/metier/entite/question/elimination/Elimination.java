package metier.entite.question.elimination;

import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Difficulte;

import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

public class Elimination extends Question
{
	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Elimination(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
	{
		super(id, ressource, notion, difficulte, temps, note);
	}

	/*---------*/
	/* Getters */
	/*---------*/
	@Override
	public TypeQuestion getType() { return TypeQuestion.ELIMINATION; }

	/*---------*/
	/* Setters */
	/*---------*/
	public boolean setProposition(int i, PropositionElimination prop)
	{
		return super.setProposition(i, prop);
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	/* Ajouter une proposition */
	public boolean ajouterProposition(PropositionElimination prop)
	{
		return super.ajouterProposition(prop);
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
    {
        String                 result = "";
		PropositionElimination prop;
        for(int i = 0; i < this.lstPropositions.size(); i++)
        {
			prop = (PropositionElimination)this.lstPropositions.get(i);

            result +=	"TextProposition         : " + prop.getText       () + "\n" +
						"Réponse                 : " + prop.estReponse    () + "\n" +
						"Numéro d'élimination    : " + prop.getOrdreElim  () + "\n" +
						"Nombre de points perdus : " + prop.getNbPtsPerdus() + "\n\n";
        }

        return result;
    }
}
