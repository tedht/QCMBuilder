package metier.entite.question.association;

import metier.entite.question.Proposition;

/** Classe PropositionAssociation
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PropositionAssociation implements Proposition
{
	private String textGauche;
	private String textDroite;

	/**
	 * Constructeur de la classe PropositionAssociation.
	 * 
	 * @param textGauche le texte de la proposition de gauche
	 * @param textDroite le texte de la proposition de droite
	 * @see #question
	 */
	public PropositionAssociation(String textGauche, String textDroite)
	{
		this.textGauche = textGauche;
		this.textDroite = textDroite;
	}

	/**
	 * Retourne le texte de la proposition de gauche.
	 * 
	 * @return le texte de la proposition de gauche
	 */
	public String getTextGauche() { return textGauche; }

	/**
	 * Retourne le texte de la proposition de droite.
	 * 
	 * @return le texte de la proposition de droite
	 */
	public String getTextDroite() { return textDroite; }

	/**
	 * Modifie le texte de la proposition de gauche.
	 * 
	 * @param textGauche le nouveau texte de la proposition de gauche
	 */
	public void setTextGauche(String textGauche) { this.textGauche = textGauche; }

	/**
	 * Modifie le texte de la proposition de droite.
	 * 
	 * @param textDroite le nouveau texte de la proposition de droite
	 */
	public void setTextDroite(String textDroite) { this.textDroite = textDroite; }

	public String toString()
	{
		return this.textGauche + " --- " + this.textDroite;
	}

	
}
