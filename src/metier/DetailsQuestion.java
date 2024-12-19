package metier;

import java.util.List;

import metier.entite.*;

import metier.entite.question.Difficulte;

import metier.entite.question.association.PropositionAssociation;
import metier.entite.question.qcm.PropositionQCM;
import metier.entite.question.elimination.PropositionElimination;


/**
 * Record DetailsQuestion
 * 
 * @author  Equipe03
 * @version 1.0 du 09/12/2024
 */
public record DetailsQuestion(Ressource ressource, Notion notion, Difficulte difficulte, int temps, double note, String intitule, String explication,
                              List<PropositionQCM> propQCM, List<PropositionAssociation> propAssos, List<PropositionElimination> propElim)
{
	public DetailsQuestion
	{
		if (ressource == null)             throw new IllegalArgumentException("La ressource ne peut pas être null");
		if (notion == null)                throw new IllegalArgumentException("La notion ne peut pas être null");
		if (difficulte == null)            throw new IllegalArgumentException("La difficulté ne peut pas être null");
		if (temps < 0)                     throw new IllegalArgumentException("Le temps ne peut pas être négatif");
		if (note < 0)                      throw new IllegalArgumentException("La note ne peut pas être négative");
		if (intitule == null)              throw new IllegalArgumentException("L'intitulé ne peut pas être null");
		if (intitule.equals(""))           throw new IllegalArgumentException("L'intitulé ne peut pas être vide");
		if (propQCM   == null &&
		    propAssos == null &&
			propElim  == null   )          throw new IllegalArgumentException("Il doit y avoir au moins une proposition");
	}
}