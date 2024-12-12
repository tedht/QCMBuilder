package metier.entite.question.elimination;

import metier.entite.question.Proposition;

public class PropositionElimination implements Proposition
{
    private String  text;
    private boolean reponse;
    private int     ordreElim;
    private double  nbPtsPerdus;

    /*--------------*/
	// Constructeur //
	/*--------------*/

    public PropositionElimination(String text, boolean reponse, int ordreElim, double nbPtsPerdus)
    {
        this.text        = text;
        this.reponse     = reponse;
        this.ordreElim   = ordreElim;
        this.nbPtsPerdus = nbPtsPerdus;
    }

	/*--------------*/
	//    GETTER    //
	/*--------------*/

    public String  getText       () { return this.text;        }
    public boolean estReponse    () { return this.reponse;     }
    public int     getOrdreElim  () { return this.ordreElim;   }
    public double  getNbPtsPerdus() { return this.nbPtsPerdus; }

    /*--------------*/
	//    SETTER    //
	/*--------------*/
    public void setText       (String  text       ) { this.text        = text;        }
	public void setReponse    (boolean reponse    ) { this.reponse     = reponse;     }
    public void setOrdreElim  (int     ordreElim  ) { this.ordreElim   = ordreElim;   }
    public void setNbPtsPerdus(double  nbPtsPerdus) { this.nbPtsPerdus = nbPtsPerdus; }
}