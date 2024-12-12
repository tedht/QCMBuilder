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
	/* Attributs */
	private List<PropositionQCM> lstPropositions;

	/*--------------*/
	// Constructeur //
	/*--------------*/

	public QCM(int id, Ressource ressource, Notion notion, Difficulte difficulte, int temps, int note)
	{
		super(id, ressource, notion, difficulte, temps, note);
		this.lstPropositions = new ArrayList<>();
	}

	/*--------------*/
	//    GETTER    //
	/*--------------*/

	public List<PropositionQCM> getProposition() { return this.lstPropositions; }
	@Override
	public TypeQuestion getType() 
	{
		return TypeQuestion.QCM;
	}


	/* Ajouter une proposition */
	public boolean ajouterProposition(String proposition, boolean reponse)
	{
		for (PropositionQCM prop : lstPropositions) {
			if( prop.getTextProposition().equals(proposition) ) { return false; }
		}

		PropositionQCM prop = new PropositionQCM(proposition, reponse);
		this.lstPropositions.add(prop);
		return true;
	}


	/* Supprimer une proposition */
	public boolean supprimerProposition(int id)
	{
		if ( id < 0 || id >= this.lstPropositions.size()) { return false; }
		if ( this.lstPropositions.isEmpty() )             { return false; }

		this.lstPropositions.remove(id);
		return true;
	}

	public String toString()
    {
        String result = "";
        for(PropositionQCM prop : this.lstPropositions)
        {
            result +=	"TextProposition         : " + prop.getTextProposition() + "\n" +
						"Réponse                 : " + prop.getReponse()         + "\n\n";
        }

        return result;
    }
}
