package QCMBuilder.ihm.question.edit;


import QCMBuilder.controleur.Controleur;

import QCMBuilder.ihm.question.edit.proposition.PanelPropAssoc;
import QCMBuilder.ihm.question.edit.proposition.PanelPropElim;
import QCMBuilder.ihm.question.edit.proposition.PanelPropQCM;
import QCMBuilder.ihm.question.edit.proposition.PanelPropQRM;

import QCMBuilder.metier.entite.question.Question;
import QCMBuilder.metier.entite.question.TypeQuestion;

import QCMBuilder.metier.entite.question.association.Association;
import QCMBuilder.metier.entite.question.elimination.*;
import QCMBuilder.metier.entite.question.qcm.QCM;

/** 
 * Classe JPanel pour ajouter des éléments (intitulé, propositions...) à une question existente.
 * Cette classe hérite de PanelAjoutQuestion et ne fait que ajouter les données de la question
 * qu'on veut modifier aux champs correspondants.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelModifAjoutQuestion extends PanelAjoutQuestion
{
	private Controleur ctrl;
	private int        idQst;

	/**
	 * Constructeur de la classe PanelModifAjoutQuestion
	 * 
	 * @param ctrl  le contrôleur.
	 * @param frame le frame d'édition de questions.
	 * @param idQst l'id de la question qu'on veut modifier.
	 */
	public PanelModifAjoutQuestion(Controleur ctrl, FrameEditQuestion frame, int idQst) 
	{
		super(frame);

		Question question; 
		
		QCM         qcm;   PanelPropQCM   panelPropQcm;   PanelPropQRM panelPropQrm;
		Association assoc; PanelPropAssoc panelPropAssoc;
		Elimination elim;  PanelPropElim  panelPropElim;

		PropositionElimination propElim;


		this.ctrl  = ctrl;
		this.idQst = idQst;
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

				propElim = elim.getProposition(i);

				panelPropElim.setText        ( propElim.getText       ());
				panelPropElim.setReponse     ( propElim.estReponse    ());
				panelPropElim.setOrdreElim   ((propElim.getOrdreElim  () == -1  ? "" : "" + propElim.getOrdreElim  ()));
				panelPropElim.setPointsPerdus((propElim.getNbPtsPerdus() == 0.0 ? "" : "" + propElim.getNbPtsPerdus()));
			}
		}
	}
	
	/**
	 * Méthode pour enregistrer les modifications effectuées sur une question existente.
	 */
	public void enregistrer()
	{
		this.frame.enregistrer(this.idQst);
	}
	
}
