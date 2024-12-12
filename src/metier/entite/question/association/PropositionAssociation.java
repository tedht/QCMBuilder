package metier.entite.question.association;

import metier.entite.question.Proposition;

public class PropositionAssociation implements Proposition
{
	private String textGauche;
	private String textDroite;

	public PropositionAssociation(String textGauche, String textDroite)
	{
		this.textGauche = textGauche;
		this.textDroite = textDroite;
	}

	public String getTextGauche() { return textGauche; }
	public String getTextDroite() { return textDroite; }

	public void setTextGauche(String textGauche) { this.textGauche = textGauche; }
	public void setTextDroite(String textDroite) { this.textDroite = textDroite; }

	
}
