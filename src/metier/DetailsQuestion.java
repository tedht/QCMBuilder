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

}