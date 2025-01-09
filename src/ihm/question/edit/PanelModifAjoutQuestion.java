package ihm.question.edit;

import controleur.Controleur;
import ihm.question.edit.proposition.PanelPropAssoc;
import ihm.question.edit.proposition.PanelPropElim;
import ihm.question.edit.proposition.PanelPropQCM;
import ihm.question.edit.proposition.PanelPropQRM;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;
import metier.entite.question.association.Association;
import metier.entite.question.elimination.Elimination;
import metier.entite.question.elimination.PropositionElimination;
import metier.entite.question.qcm.QCM;

public class PanelModifAjoutQuestion extends PanelAjoutQuestion
{
	private Controleur ctrl;
	private int        idQst;

	public PanelModifAjoutQuestion(Controleur ctrl, FrameEditQuestion frame, int idQst) 
	{
		super(frame);
		this.ctrl  = ctrl;
		this.idQst = idQst;

		Question question; 
		
		QCM         qcm;   PanelPropQCM   panelPropQcm;   PanelPropQRM panelPropQrm;
		Association assoc; PanelPropAssoc panelPropAssoc;
		Elimination elim;  PanelPropElim  panelPropElim;

		question = this.ctrl.getQuestion(this.idQst);

		this.txtIntitule.setText(question.getIntitule());
		if(!"".equals(question.getExplication()))
		{
			this.btnActiverExpli.setSelected(true);
			this.txtExpli.setText(question.getExplication());
		}

		if(question.getPieceJointe() != null)
		{
			this.btnActiverPJ.setSelected(true);
			this.lblPJ.setText(question.getPieceJointe().getCheminFic());
		}

		if(question.getType() == TypeQuestion.QCM)
		{
			qcm = (QCM)question;
			if(qcm.estUnique())
			{
				for(int i = 0; i < qcm.getPropositions().size(); i++)
				{
					this.ajouterProposition();
					panelPropQcm = (PanelPropQCM)this.lstPanelProp.get(i);
					panelPropQcm.setText   (qcm.getProposition(i).getText   ());
					panelPropQcm.setReponse(qcm.getProposition(i).estReponse());
				}
			}
			else
			{
				for(int i = 0; i < qcm.getPropositions().size(); i++)
				{
					this.ajouterProposition();
					panelPropQrm = (PanelPropQRM)this.lstPanelProp.get(i);
					panelPropQrm.setText   (qcm.getProposition(i).getText   ());
					panelPropQrm.setReponse(qcm.getProposition(i).estReponse());
				}
			}
		}
		else if(question.getType() == TypeQuestion.ASSOCIATION)
		{
			assoc = (Association)question;

			for(int i = 0; i < assoc.getPropositions().size(); i++)
			{
				this.ajouterProposition();
				panelPropAssoc = (PanelPropAssoc)this.lstPanelProp.get(i);
				panelPropAssoc.setTextGauche(assoc.getProposition(i).getTextGauche());
				panelPropAssoc.setTextDroite(assoc.getProposition(i).getTextDroite());
			}
		}
		else
		{
			elim = (Elimination)question;

			for(int i = 0; i < elim.getPropositions().size(); i++)
			{
				this.ajouterProposition();
				panelPropElim = (PanelPropElim)this.lstPanelProp.get(i);

				PropositionElimination propElim = elim.getProposition(i);

				panelPropElim.setText        ( propElim.getText       ());
				panelPropElim.setReponse     ( propElim.estReponse    ());
				panelPropElim.setOrdreElim   ((propElim.getOrdreElim  () == -1  ? "" : "" + propElim.getOrdreElim  ()));
				panelPropElim.setPointsPerdus((propElim.getNbPtsPerdus() == 0.0 ? "" : "" + propElim.getNbPtsPerdus()));
			}
		}
	}
	
	public void enregistrer()
	{
		this.frame.enregistrer(this.idQst);
	}
	
}
