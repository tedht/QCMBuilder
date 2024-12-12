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

<<<<<<< HEAD
	public Association(int id, String intitule, String explication, Difficulte difficulte,
	                   Ressource ressource, Notion notion, int temps, int note)
=======
	public Association(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
>>>>>>> 94c7fa203a65d01ba3c32dcdf7aa97965994da65
	{
		super(id, ressource, notion, difficulte, temps, note);

		this.lstPropositionsGauche = new ArrayList<String  >();
		this.lstPropositionsDroite = new ArrayList<String  >();
		this.lstLiaisons           = new ArrayList<String[]>();
	}

	/*----------*/
	/* Getteurs */
	/*----------*/

	public List<String  > getPropositionsGauche() { return this.lstPropositionsGauche; }
	public List<String  > getPropositionsDroite() { return this.lstPropositionsDroite; }
	public List<String[]> getLiaisons          () { return this.lstLiaisons;           }

	@Override
	public TypeQuestion getType() 
	{
		return TypeQuestion.ASSOCIATION;
	}

	/* Ajouter une proposition gauche et droite */
	public boolean ajouterProposition(String propositionGauche, String propositionDroite)
	{
		if (propositionGauche == null || propositionDroite == null) return false;
		if (propositionGauche.equals("") || propositionDroite.equals("")) return false;

		for (String prop : this.lstPropositionsGauche)
			if (prop == propositionGauche)
				return false; // Proposition Gauche déja existante

		for (String prop : this.lstPropositionsDroite)
			if (prop == propositionDroite)
				return false; // Proposition Droite déja existante

		this.lstPropositionsGauche.add(propositionGauche);
		this.lstPropositionsDroite.add(propositionDroite);
		this.ajouterLiaison(propositionGauche, propositionDroite);
		return true;
	}

	/* Ajoute la liaison entre la propositionGauche et propositionDroite */
	public boolean ajouterLiaison(String propositionGauche, String propositionDroite)
	{
		if (propositionGauche == null || propositionDroite == null) return false;
		if (propositionGauche.equals("") || propositionDroite.equals("")) return false;

		for (String[] liaison : this.lstLiaisons)
			if (liaison[0] == propositionGauche && liaison[1] == propositionDroite)
				return false; // Liaison déja existante

		this.lstLiaisons.add(new String[] {propositionGauche, propositionDroite});
		return true;
	}


	/* Modifier */
	/*public boolean modifierProposition(String propositionGaucheActuelle, String nouvellePropositionGauche)
	{
		if (this.lstPropositionsGauche.isEmpty() && // Si les listes sont vides -> renvoie faux
		    this.lstPropositionsDroite.isEmpty())

		if (propositionGaucheActuelle == null) return false;
		if (propositionGaucheActuelle.equals("")) return false;

		if (nouvellePropositionGauche == null) return false;
		if (nouvellePropositionGauche.equals("")) return false;

		for(int cpt = 0; cpt < this.lstPropositionsGauche.size(); cpt++)
		{
			if(this.lstPropositionsGauche.get(cpt).equals(propositionGaucheActuelle))
			{
				this.lstPropositionsGauche.set(cpt, nouvellePropositionGauche);
			}
		}

		for (String[] liaison : this.lstLiaisons)
			if (liaison[0] == propositionGaucheActuelle)
				liaison[0] = nouvellePropositionGauche;
		
		return true;
	}*/

	public boolean modifierLiaison(char modif, String proposition, String propositionDroite, String nouvelleProposition, String nouvellepropositionDroite)
	{
		this.supprimerLiaison(proposition, propositionDroite);

		if (modif == 'P')
		{
			this.ajouterLiaison(nouvelleProposition, propositionDroite);
		}
		else
		{
			if (modif == 'R')
			{
				this.ajouterLiaison(proposition, nouvellepropositionDroite);
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
		if (this.lstPropositionsGauche.isEmpty() && // Si les listes sont vides -> renvoie faux
		    this.lstPropositionsDroite.isEmpty())
		
		this.lstPropositionsGauche.remove(proposition);
		this.lstPropositionsDroite.remove(proposition);

		for (int cpt = 0 ; cpt < this.lstLiaisons.size() ; cpt++)
			if (this.lstLiaisons.get(cpt)[0] == proposition || this.lstLiaisons.get(cpt)[1] == proposition)
				this.lstLiaisons.remove(cpt);

		return true;
	}

	public boolean supprimerLiaison(String propositionGauche, String propositionDroite)
	{
		if (this.lstLiaisons.isEmpty()) return false;

		if (propositionGauche == null || propositionDroite == null) return false;
		if (propositionGauche.equals("") || propositionDroite.equals("")) return false;

		this.lstLiaisons.remove(new String[] {propositionGauche, propositionDroite});
		return true;
	}


	/* toString */
	public String toString()
	{
		String sRet;


		if (this.lstPropositionsGauche.isEmpty()) { return "Il n'y a pas de propositions gauche"; }
		if (this.lstPropositionsDroite.isEmpty()) { return "Il n'y a pas de propositions droite";    }

		sRet = "";
		for (String proposition : this.lstPropositionsGauche)
			 sRet += proposition + "\t";

		sRet += "\n\n";

		for (String propositionDroite : this.lstPropositionsDroite)
			sRet += propositionDroite + "\t";

		sRet += "\n\nLiaisons :\n";

		for (String[] liaison : this.lstLiaisons)
			sRet += liaison[0] + " --- " + liaison[1] + "\n";
		System.out.println("\n------------------");

		return sRet;
	}

	/*
	public static void main(String[] args)
	{
		Association asso, asso2;


		asso = new Association(0, null, null, null, null, null, 0, 0);
		asso.ajouterPropositionGauche("PropG1");
		asso.ajouterPropositionDroite("PropD1");
		asso.ajouterLiaison("PropG1", "PropD1");
		System.out.println(asso);

		asso.modifierPropositionDroite("PropD1", "PropD1Bis");
		asso.ajouterPropositionGauche("PropG2");
		asso.ajouterLiaison("PropG2", "PropD1Bis");
		System.out.println(asso);

		asso.supprimerPropositionGauche("PropG1");
		asso.supprimerpropositionDroite("PropD1Bis");
		asso.supprimerpropositionDroite("PropD3"); // Ne le fait pas car PropD3 n'existe pas
		System.out.println(asso);

		asso2 = new Association(1, "Relier les mots français à leurs traductions anglaises", "with veut dire avec donc withOUT veut dire sans", Difficulte.FACILE, new Ressource("R3.12 Anglais"), new Notion("Vocabulaire"), 0, 1);
		asso2.ajouterPropositionGauche("Without");
		asso2.ajouterPropositionDroite("Sans");
		asso2.ajouterLiaison("Without", "Sans");
		System.out.println(asso2);
	} */
}
