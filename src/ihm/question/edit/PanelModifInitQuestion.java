package ihm.question.edit;

import controleur.Controleur;
import metier.entite.question.Question;
import metier.entite.question.TypeQuestion;
import metier.entite.question.qcm.QCM;

public class PanelModifInitQuestion extends PanelInitQuestion
{	
	private int idQst;

	public PanelModifInitQuestion(Controleur ctrl, FrameEditQuestion frame, int idQst) 
	{
		super(ctrl, frame);
		this.idQst = idQst;

		Question question = this.ctrl.getQuestion(this.idQst);

		this.txtPoints.setText(Double.toString(question.getNote()));

		int m = question.getTemps() / 60;
		int s = question.getTemps() % 60;
		this.txtTemps.setText(String.format("%02d", m) + ":" + String.format("%02d", s));

		for(int i = 0; i < this.ddlstRessource.getItemCount(); i++)
		{
			if(this.ddlstRessource.getItemAt(i).getCode().equals(question.getCodeRes()))
			{
				this.ddlstRessource.setSelectedIndex(i);
				break;
			}
		}
		
		for(int i = 0; i < this.ddlstNotion.getItemCount(); i++)
		{
			if(this.ddlstNotion.getItemAt(i).getIdNot() == question.getIdNot())
			{
				this.ddlstNotion.setSelectedIndex(i);
				break;
			}
		}

		this.tabRbDifficulte[question.getDifficulte().getValeur()].setSelected(true);

		if(question.getType() == TypeQuestion.QCM)
		{
			if(((QCM)question).estUnique())
				this.ddlstTypeQuestion.setSelectedIndex(0);
			else
				this.ddlstTypeQuestion.setSelectedIndex(1);
		}
		else if(question.getType() == TypeQuestion.ASSOCIATION)
		{
			this.ddlstTypeQuestion.setSelectedIndex(2);
		}
		else
		{
			this.ddlstTypeQuestion.setSelectedIndex(3);
		}


	}
	
}
