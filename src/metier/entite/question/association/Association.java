 package metier.entite.question.association;


import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;
import metier.entite.question.Proposition;

/** Classe Association
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Association extends Question
{
	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Association(Ressource ressource, Notion notion, Difficulte difficulte, int temps, double note)
	{
		super(ressource, notion, difficulte, temps, note);
	}

	/*---------*/
	/* Getters */
	/*---------*/
	public TypeQuestion getType() { return TypeQuestion.ASSOCIATION; }

	/*---------*/
	/* Setters */
	/*---------*/
	public boolean setProposition(int i, PropositionAssociation prop)
	{
		return super.setProposition(i, prop);
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	/* Ajouter une proposition */
	public boolean ajouterProposition(PropositionAssociation prop)
	{
		return super.ajouterProposition(prop);
	}

	/* toString */
	public String toString()
	{
		String sRet;


		if (this.lstPropositions.isEmpty()) { return "Il n'y a pas de propositions"; }

		sRet = "";
		for (Proposition proposition : this.lstPropositions)
			 sRet += proposition + "\n";

		return sRet;
	}

	public static void main(String[] args)
	{
		Association asso, asso2;
		PropositionAssociation prop1, prop2, prop3, propAnglais;


		asso = new Association(null, null, null, 0, 0.0);
		prop1 = new PropositionAssociation("PropG1", "PropD1");
		asso.ajouterProposition(prop1);
		System.out.println(asso);

		prop1.setTextDroite("PropD1Bis");
		prop2 = new PropositionAssociation("ProgG2", "PropD2");
		asso.ajouterProposition(prop2);
		System.out.println(asso);

		prop3 = new PropositionAssociation("PropG3", "PropD3");
		asso.supprimerProposition(asso.getPropositionId(prop1));
		asso.supprimerProposition(asso.getPropositionId(prop3)); // Ne le fait pas car PropD3 n'existe pas
		System.out.println(asso);

		asso2 = new Association(new Ressource("R3.12 Anglais"), new Notion("Vocabulaire"), Difficulte.FACILE, 0, 1.0);
		propAnglais = new PropositionAssociation("Without", "Sans");
		asso2.ajouterProposition(propAnglais);
		System.out.println(asso2);
	}
}
