package metier.entite.question;

public class PropositionQCM {
    
    private String  textProposition;
    private boolean reponse;

	/*--------------*/
	// Constructeur //
	/*--------------*/

    public PropositionQCM(String textProposition, boolean reponse)
    {
        this.textProposition = textProposition;
        this.reponse         = reponse;
    }

	/*--------------*/
	//    GETTER    //
	/*--------------*/

    public String  getTextProposition() { return this.textProposition; }
    public boolean getReponse()         { return this.reponse;         }

	/*--------------*/
	//    SETTER    //
	/*--------------*/

    public void setTextProposition(String textProposition) { this.textProposition = textProposition; }

    public boolean changerReponse()
    {
        if(this.reponse)  { this.reponse = false; }
        if(!this.reponse) { this.reponse = true;  }
        return this.reponse;
    }
}