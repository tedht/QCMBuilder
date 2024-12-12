package metier.entite.question;

import java.util.ArrayList;
import java.util.List;

import metier.entite.Notion;
import metier.entite.Ressource;

/** Classe Qcm
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCM extends Question
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private List<String> lstPropositions;
	private List<String> lstReponses;

	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public QCM(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
	{
		super(id, ressource, notion, difficulte, temps, note);
		this.lstPropositions = new ArrayList<String>();
		this.lstReponses     = new ArrayList<String>();

	}

	/*---------*/
	/* Getters */
	/*---------*/
	public List<String> getReponses () { return this.lstReponses; }

	@Override
	public TypeQuestion getType() { return TypeQuestion.QCM; }


	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/* Ajouter une proposition */
	public boolean ajouterProposition(String prop)
	{
		if(!this.lstPropositions.contains(prop)) return false; // Si la proposition éxiste déjà -> renvoie faux

		this.lstPropositions.add(prop); // Ajoute la proposition
		return true;
	}

	/* Modifier une proposition */
	public boolean modifierProposition(int i, String prop)
	{
		if(i > this.lstPropositions.size() || i < 0)  { return false; } // Si l'indice est en dehors de la liste                                           -> renvoie faux
		if(this.lstPropositions.isEmpty())            { return false; } // Si la liste est vide                                                            -> renvoie faux
		if(!this.lstPropositions.get(i).equals(prop)) { return false; } // Si la proposition modifié est égale à la même chose que la nouvelle proposition -> renvoie faux

		this.lstPropositions.set(i, prop);                              // Modifie la proposition
		return true;
	}

	/* Supprimer une proposition */
	public boolean supprimerProposition(int i)
	{
		if(i > this.lstPropositions.size() || i < 0) { return false; } // Si l'indice est en dehors de la liste -> renvoie faux
		if(this.lstPropositions.isEmpty())           { return false; } // Si la liste est vide                  -> renvoie faux

		this.lstPropositions.remove(i); // Supprime la proposition
		return true;
	}

	/* Ajouter une réponse correcte */
	public boolean ajouterIndReponse(String reponse)
	{
		if(!this.lstReponses.contains(reponse)) return false; // Si l'indice est déjà dans la liste -> renvoie faux

		this.lstReponses.add(reponse); // Ajoute l'indice
		return true;
	}

	/* Supprimer  une réponse correcte */
	public boolean supprimerProposition(String reponse)
	{
		if(this.lstReponses.isEmpty()) { return false; } // Si la liste est vide -> renvoie faux

		this.lstReponses.remove(reponse); // Supprime l'indice
		return true;
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
	{
		return "Question QCM :\n" + "Propositions : "  + this.lstPropositions + "\n" + "Réponses : "  + this.lstReponses + "\n";
	}
}
