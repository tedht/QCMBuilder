package metier.entite.question;

import java.util.ArrayList;
import java.util.List;
import metier.entite.Notion;
import metier.entite.Ressource;

public class Elimination extends Question
{
	/* Attributs */
	private List<PropositionElimination> lstPropositions;
	private int                          suppression;

	/*--------------*/
	// Constructeur //
	/*--------------*/

	public Elimination(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
	{
		super(id, ressource, notion, difficulte, temps, note);
		this.lstPropositions = new ArrayList<>();
		this.suppression     = 1;
	}

	/*--------------*/
	//    GETTER    //
	/*--------------*/

	public List<PropositionElimination>  getProposition() { return this.lstPropositions; }
	@Override
	public TypeQuestion getType() 
	{
		return TypeQuestion.ELIMINATION;
	}


	/* Ajouter une proposition */
	public boolean ajouterProposition(String nouvelleProposition, boolean reponse, int numElimination, double nbPtPerdu)
	{
		for(PropositionElimination proposition : this.lstPropositions)
		{
			if( proposition.getTextProposition() == nouvelleProposition ) { return false; }
		}
		PropositionElimination prop = new PropositionElimination(nouvelleProposition, reponse, numElimination, nbPtPerdu);
		this.lstPropositions.add(prop);
		return true;
	}

	/* Supprimer une proposition */
	public boolean supprimerProposition(int id)
	{
		if(id < 0 || id >= this.lstPropositions.size())  { return false; }
		if(this.lstPropositions.isEmpty())               { return false; }

		this.lstPropositions.remove(id);
		return true;
	}

	// Eliminer la premiére proposition qui vient
	public double eliminerProposition()
	{
		double nbPtPerdu = 0;
		int cpt = 0;
		for(PropositionElimination prop : lstPropositions)
		{
			if(prop.getNumElimination() == this.suppression)
			{
				nbPtPerdu = prop.getNbPtPerdu();
				supprimerProposition(cpt);
				this.suppression++;
				cpt++;
				return nbPtPerdu;
			}
		}

		return nbPtPerdu;
	}

	public String toString()
    {
        String result = "";
        for(PropositionElimination prop : this.lstPropositions)
        {
            result +=	"TextProposition         : " + prop.getTextProposition() + "\n" +
						"Réponse                 : " + prop.getReponse()         + "\n" +
						"Numéro d'élimination    : " + prop.getNumElimination()  + "\n" +
						"Nombre de points perdus : " + prop.getNbPtPerdu()       + "\n\n";
        }

        return result;
    }
}
