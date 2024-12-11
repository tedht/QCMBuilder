package metier.entite.question;

import java.util.List;

import metier.entite.Notion;
import metier.entite.Ressource;

/** Classe Qcm
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCM extends Question
{
	/* Attributs */
	private List<String> proposition;
	private List<String> reponse;

	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public QCM(	int id, String intitule, String explication, String difficulte,
	            Ressource ressource, Notion notion, int temps, int note,
	            List<String> proposition, List<String> reponse)
	{
		super(id, intitule, explication, difficulte, ressource, notion, temps, note);
		this.proposition = proposition;
		this.reponse     = reponse;
	}

	/*----------*/
	/* Getteurs */
	/*----------*/

	public List<String> getProposition() { return this.proposition; }
	public List<String> getReponse()     { return this.reponse;     }

	/*----------*/
	/* Setteurs */
	/*----------*/

	public void setReponse(List<String> reponse)	{ this.reponse = reponse; }

	/* Ajouter une proposition */
	public boolean ajouterProposition(String newProposition)
	{
		for(String proposition : this.proposition)
		{
			if (proposition == newProposition) { return false; } // Si la proposition éxiste déjà -> renvoie faux
		}

		this.proposition.add(newProposition); // Ajoute la proposition
		return true;
	}

	/* Modifier une proposition */
	public boolean modifierProposition(int id, String proposition)
	{
		if ( id > this.proposition.size() || id < 0 )        { return false; } // Si l'id est en dehors de la liste                                               -> renvoie faux
		if ( this.proposition.isEmpty() )                    { return false; } // Si la liste est vide                                                            -> renvoie faux
		if ( !this.proposition.get(id).equals(proposition) ) { return false; } // Si la proposition modifié est égale à la même chose que la nouvelle proposition -> renvoie faux

		this.proposition.set(id, proposition);                                 // Modifie la proposition
		return true;
	}

	/* Supprimer une proposition */
	public boolean supprimerProposition(int id)
	{
		if ( id > this.proposition.size() || id < 0) { return false; } // Si l'id est en dehors de la liste -> renvoie faux
		if ( this.proposition.isEmpty() )            { return false; } // Si la liste est vide              -> renvoie faux

		this.proposition.remove(id); // Supprime la proposition
		return true;
	}

	/* toString */
	public String toString()
	{
		return	"Question QCM :\n" +
				"Propositions : "  + this.proposition + "\n" +
				"Réponse      : "  + this.reponse     + "\n"   ;
	}
}
