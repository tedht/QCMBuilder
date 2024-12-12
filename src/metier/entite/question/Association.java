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

	public Association(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
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
	private boolean ajouterLiaison(String propositionGauche, String propositionDroite)
	{
		if (propositionGauche == null    || propositionDroite == null)    return false;
		if (propositionGauche.equals("") || propositionDroite.equals("")) return false;

		for (String[] liaison : this.lstLiaisons)
			if (liaison[0] == propositionGauche &&
			    liaison[1] == propositionDroite    )
				return false; // Liaison déja existante

		this.lstLiaisons.add(new String[] {propositionGauche, propositionDroite});
		return true;
	}


	/* Modifier */
	public boolean modifierProposition(String propositionGaucheActuelle, String nouvellePropositionGauche,
	                                   String propositionDroiteActuelle, String nouvellePropositionDroite)
	{
		if (this.lstPropositionsGauche.isEmpty() &&   // Si les listes sont vides -> renvoie faux
		    this.lstPropositionsDroite.isEmpty()    ) return false;


		if ( propositionGaucheActuelle != null    &&  nouvellePropositionGauche != null &&
		    !propositionGaucheActuelle.equals("") && !nouvellePropositionGauche.equals("")) // Vérification des paramètres pour la gauche
		{
			for(int cpt = 0; cpt < this.lstPropositionsGauche.size(); cpt++)
			{
				if(this.lstPropositionsGauche.get(cpt).equals(propositionGaucheActuelle))
				{
					this.lstPropositionsGauche.set(cpt, nouvellePropositionGauche);
				}
			}

			for (String[] liaison : this.lstLiaisons)
				if (liaison[0] == propositionDroiteActuelle)
					liaison[0] =  nouvellePropositionDroite;

			/*this.modifierLiaison('G', propositionGaucheActuelle, nouvellePropositionGauche,
			                                propositionDroiteActuelle, nouvellePropositionDroite);*/  // Modification de la liaison à gauche
		}

		if ( propositionDroiteActuelle != null    &&  nouvellePropositionDroite != null &&
			!propositionDroiteActuelle.equals("") && !nouvellePropositionDroite.equals("")) // Vérification des paramètres pour la droite
		{
			for(int cpt = 0; cpt < this.lstPropositionsDroite.size(); cpt++)
			{
				if(this.lstPropositionsDroite.get(cpt).equals(propositionDroiteActuelle))
				{
					this.lstPropositionsDroite.set(cpt, nouvellePropositionDroite);
				}
			}

			for (String[] liaison : this.lstLiaisons)
				if (liaison[1] == propositionDroiteActuelle)
					liaison[1] =  nouvellePropositionDroite;

			/*this.modifierLiaison('D', propositionGaucheActuelle, nouvellePropositionGauche,
											propositionDroiteActuelle, nouvellePropositionDroite);*/ // Modification de la liaison à droite
		}
		
		return true;
	}


	/* Supprimer une proposition */
	public boolean supprimerProposition(String proposition)
	{
		if (this.lstPropositionsGauche.isEmpty() && // Si les listes sont vides -> renvoie faux
		    this.lstPropositionsDroite.isEmpty()) return false;
		
		this.lstPropositionsGauche.remove(proposition);
		this.lstPropositionsDroite.remove(proposition); // Retire la proposition des listes de propositions

		for (int cpt = 0 ; cpt < this.lstLiaisons.size() ; cpt++)
		{
			if (this.lstLiaisons.get(cpt)[0] == proposition)
			{
				this.lstPropositionsDroite.remove(this.lstLiaisons.get(cpt)[1]);
			}
			else
			{
				if (this.lstLiaisons.get(cpt)[1] == proposition)
				{
					this.lstPropositionsDroite.remove(this.lstLiaisons.get(cpt)[0]);
				}
			}

			if (this.lstLiaisons.get(cpt)[0] == proposition || this.lstLiaisons.get(cpt)[1] == proposition)
				this.lstLiaisons.remove(cpt);
		}

		return true;
	}

	private boolean supprimerLiaison(String propositionGauche, String propositionDroite)
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


		if (this.lstPropositionsGauche.isEmpty()) return "Il n'y a pas de propositions gauche";
		if (this.lstPropositionsDroite.isEmpty()) return "Il n'y a pas de propositions droite";

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
	}
}
