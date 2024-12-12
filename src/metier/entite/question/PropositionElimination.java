package metier.entite.question;

public class PropositionElimination
{

    private String  textProposition;
    private boolean reponse;
    private int     numElimination;
    private double  nbPtPerdu;

    /*--------------*/
	// Constructeur //
	/*--------------*/

    public PropositionElimination(String textProposition, boolean reponse, int numElimination, double nbPtPerdu)
    {
        this.textProposition  = textProposition;
        this.reponse          = reponse;
        this.numElimination   = numElimination;
        this.nbPtPerdu        = nbPtPerdu;
    }

	/*--------------*/
	//    GETTER    //
	/*--------------*/

    public String  getTextProposition() { return this.textProposition; }
    public boolean getReponse()         { return this.reponse;         }
    public int     getNumElimination()  { return this.numElimination;  }
    public double  getNbPtPerdu()       { return this.nbPtPerdu;       }

    /*--------------*/
	//    SETTER    //
	/*--------------*/

    public void    setTextProposition(String text)       { this.textProposition = text;           }
    public void    setNumElimination(int numElimination) { this.numElimination  = numElimination; }
    public void    setNbPtPerdu(double nbPtPerdu)        { this.nbPtPerdu       = nbPtPerdu;      }

    public boolean changerReponse()
    {
        if(this.reponse)  { this.reponse = false; }
        if(!this.reponse) { this.reponse = true;  }
        return this.reponse;
    }
}