 package metier.entite.question;

import java.util.ArrayList;
import java.util.List;

import metier.entite.Notion;
import metier.entite.Ressource;

/** Classe Association
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class Association extends Question
{
	/* Attributs */
	private List<String  > lstPropositionsGauche;
	private List<String  > lstPropositionsDroite;
	private List<String[]> lstLiaisons;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public Association(int id, String intitule, String explication, Difficulte difficulte,
						Ressource ressource, Notion notion, int temps, int note)
	{
		super(id, intitule, explication, difficulte, ressource, notion, temps, note);

		this.lstPropositionsGauche = new ArrayList<String  >();
		this.lstPropositionsDroite    = new ArrayList<String  >();
		this.lstLiaisons       = new ArrayList<String[]>();
	}

	/*----------*/
	/* Getteurs */
	/*----------*/

	public List<String  > getPropositions() { return this.lstPropositionsGauche; }
	public List<String  > getReponses    () { return this.lstPropositionsDroite;    }
	public List<String[]> getLiaison     () { return this.lstLiaisons;       }

	public TypeQuestion getTypeQuestion() { return TypeQuestion.ASSOCIATION; }

	/* Ajouter une proposition */
	public boolean ajouterProposition(String proposition)
	{
		if (proposition == null)    return false;
		if (proposition.equals("")) return false;

		for (String prop : this.lstPropositionsGauche)
			if (prop == proposition)
				return false; // Proposition déja existante

		this.lstPropositionsGauche.add(proposition);
		return true;
	}

	/* Ajouter une réponse */
	public boolean ajouterReponse(String reponse)
	{
		if (reponse  == null)       return false;
		if (reponse.equals(""))     return false;

		for (String rep : this.lstPropositionsDroite)
			if (rep == reponse)
				return false; // Réponse déja existante

		this.lstPropositionsDroite.add(reponse);
		return true;
	}

	public boolean ajouterLiaison(String proposition, String reponse)
	{
		if (proposition == null || reponse == null) return false;
		if (proposition.equals("") || reponse.equals("")) return false;

		for (String[] liaison : this.lstLiaisons)
			if (liaison[0] == proposition && liaison[1] == reponse)
				return false; // Liaison déja existante

		this.lstLiaisons.add(new String[] {proposition, reponse});
		return true;
	}


	/* Modifier */
	public boolean modifierProposition(String propositionActuelle, String nouvelleProposition)
	{
		if(this.lstPropositionsGauche.isEmpty()) { return false; } // Si la liste est vide -> renvoie faux

		if (propositionActuelle == null) return false;
		if (propositionActuelle.equals("")) return false;

		if (nouvelleProposition == null) return false;
		if (nouvelleProposition.equals("")) return false;

		for(int cpt = 0; cpt < this.lstPropositionsGauche.size(); cpt++)
		{
			if(this.lstPropositionsGauche.get(cpt).equals(propositionActuelle))
			{
				this.lstPropositionsGauche.set(cpt, nouvelleProposition);
			}
		}

		for (String[] liaison : this.lstLiaisons)
			if (liaison[0] == propositionActuelle)
				liaison[0] = nouvelleProposition;
		
		return true;
	}

	public boolean modifierReponse(String reponseActuelle, String nouvelleReponse)
	{
		if(this.lstPropositionsDroite.isEmpty()) return false; // Si la liste est vide -> renvoie faux

		if (reponseActuelle == null) return false;
		if (reponseActuelle.equals("")) return false;

		if (nouvelleReponse == null) return false;
		if (nouvelleReponse.equals("")) return false;

		for (int cpt = 0 ; cpt < this.lstPropositionsDroite.size() ; cpt++)
			if (this.lstPropositionsDroite.get(cpt).equals(reponseActuelle))
				this.lstPropositionsDroite.set(cpt, nouvelleReponse);

		for (String[] liaison : this.lstLiaisons)
			if (liaison[1] == reponseActuelle)
				liaison[1] = nouvelleReponse;

		return true;
	}

	public boolean modifierLiaison(char modif, String proposition, String reponse, String nouvelleProposition, String nouvelleReponse)
	{
		this.supprimerLiaison(proposition, reponse);

		if (modif == 'P')
		{
			this.ajouterLiaison(nouvelleProposition, reponse);
		}
		else
		{
			if (modif == 'R')
			{
				this.ajouterLiaison(proposition, nouvelleReponse);
			}
			else
			{
				return false;
			}
		}

		return true;
	}


	/* Supprimer une proposition */
	public boolean supprimerProposition(String proposition)
	{
		if(this.lstPropositionsGauche.isEmpty()) { return false; } // Si la liste est vide -> renvoie faux
		
		this.lstPropositionsGauche.remove(proposition);

		for (int cpt = 0 ; cpt < this.lstLiaisons.size() ; cpt++)
			if (this.lstLiaisons.get(cpt)[0] == proposition || this.lstLiaisons.get(cpt)[1] == proposition)
				this.lstLiaisons.remove(cpt);

		return true;
	}

	/* Supprimer une réponse */
	public boolean supprimerReponse(String reponse)
	{
		if(this.lstPropositionsDroite.isEmpty()) return false; // Si la liste est vide -> renvoie faux

		this.lstPropositionsDroite.remove(reponse);

		for (int cpt = 0 ; cpt < this.lstLiaisons.size() ; cpt++)
			if (this.lstLiaisons.get(cpt)[0] == reponse || this.lstLiaisons.get(cpt)[1] == reponse)
				this.lstLiaisons.remove(cpt);

		return true;
	}

	public boolean supprimerLiaison(String proposition, String reponse)
	{
		if (proposition == null || reponse == null) return false;
		if (proposition.equals("") || reponse.equals("")) return false;

		this.lstLiaisons.remove(new String[] {proposition, reponse});
		return true;
	}


	/* toString */
	public String toString()
	{
		String sRet;


		if (this.lstPropositionsGauche.isEmpty()) { return "Il n'y a pas de propostions"; }
		if (this.lstPropositionsDroite    .isEmpty()) { return "Il n'y a pas de réponses";    }

		sRet = "";
		for (String proposition : this.lstPropositionsGauche)
			 sRet += proposition + "\t";

		sRet += "\n\n";

		for (String reponse : this.lstPropositionsDroite)
			sRet += reponse + "\t";

		sRet += "\n\nLiaisons :\n";

		for (String[] liaison : this.lstLiaisons)
			sRet += liaison[0] + " --- " + liaison[1] + "\n";
		System.out.println("\n------------------");

		return sRet;
	}

	public static void main(String[] args)
	{
		Association asso, asso2;


		asso = new Association(0, null, null, null, null, null, 0, 0);
		asso.ajouterProposition("Prop1");
		asso.ajouterReponse("Rep1");
		asso.ajouterLiaison("Prop1", "Rep1");
		System.out.println(asso);

		asso.modifierReponse("Rep1", "Rep1Bis");
		asso.ajouterProposition("Prop2");
		asso.ajouterLiaison("Prop2", "Rep1Bis");
		System.out.println(asso);

		asso.supprimerProposition("Prop1");
		asso.supprimerReponse("Rep1Bis");
		asso.supprimerReponse("Rep3"); // Ne le fait pas car Rep3 n'existe pas
		System.out.println(asso);

		asso2 = new Association(1, "Relier les mots français à leurs traductions anglaises", "with veut dire avec donc withOUT veut dire sans", Difficulte.FACILE, new Ressource("R3.12 Anglais"), new Notion("Vocabulaire"), 0, 1);
		asso2.ajouterProposition("Without");
		asso2.ajouterReponse("Sans");
		asso2.ajouterLiaison("Without", "Sans");
		System.out.println(asso2);
	}

}
