package metier.entite.question.qcm;

import metier.entite.Notion;
import metier.entite.Ressource;
import metier.entite.question.Difficulte;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;

/** Classe Qcm
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class QCM extends Question
{

	boolean unique;

	/*--------------*/
	/* Constructeur */
	/*--------------*/
	public QCM(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note,boolean unique)
	{
		super(id, ressource, notion, difficulte, temps, note);
		this.unique = unique;
	}

	/*---------*/
	/* Getters */
	/*---------*/
	@Override
	public TypeQuestion getType () { return TypeQuestion.QCM; }
	public boolean      isUnique() { return this.unique     ; }

	/*---------*/
	/* Setters */
	/*---------*/
	public boolean setProposition(int i, PropositionQCM prop)
	{
		return super.setProposition(i, prop);
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	/* Ajouter une proposition */
	public boolean ajouterProposition(PropositionQCM prop)
	{
		return super.ajouterProposition(prop);
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
    {
        String         result = "";
		PropositionQCM prop;
        for(int i = 0; i < this.lstPropositions.size(); i++)
        {
			prop = (PropositionQCM)this.lstPropositions.get(i);

            result +=	"TextProposition         : " + prop.getText   () + "\n" +
						"Réponse                 : " + prop.estReponse() + "\n\n";
        }

        return result;
    }


	
}
