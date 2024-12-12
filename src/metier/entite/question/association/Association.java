 package metier.entite.question.association;


import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

/** Classe Association
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Association extends Question
{
	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public Association(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
	{
		super(id, ressource, notion, difficulte, temps, note);
	}

	/*----------*/
	/* Getteurs */
	/*----------*/
	public TypeQuestion getType() { return TypeQuestion.ASSOCIATION; }

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	/* Ajouter une proposition */
	public boolean ajouterProposition(PropositionAssociation prop)
	{
		if(this.lstPropositions.contains(prop)) return false; // Si la proposition éxiste déjà -> renvoie faux

		this.lstPropositions.add(prop); // Ajoute la proposition
		return true;
	}

	/*
	public static void main(String[] args)
	{
		Association asso, asso2;


		asso = new Association(0, null, null, null, 0, 0);
		asso.ajouterProposition("PropG1", "PropD1");
		System.out.println(asso);

		asso.modifierProposition(null, null, "PropD1", "PropD1Bis");
		asso.ajouterProposition("PropG2", "PropD2");
		System.out.println(asso);

		asso.supprimerProposition("PropG1");
		asso.supprimerProposition("PropD3"); // Ne le fait pas car PropD3 n'existe pas
		System.out.println(asso);

		asso2 = new Association(1, new Ressource("R3.12 Anglais"), new Notion("Vocabulaire"), Difficulte.FACILE, 0, 1);
		asso2.ajouterProposition("Without", "Sans");
		System.out.println(asso2);
	}*/
}
