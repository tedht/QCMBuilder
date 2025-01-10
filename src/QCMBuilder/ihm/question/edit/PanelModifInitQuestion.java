package QCMBuilder.ihm.question.edit;


import QCMBuilder.controleur.Controleur;
import QCMBuilder.metier.entite.question.Question;
import QCMBuilder.metier.entite.question.TypeQuestion;
import QCMBuilder.metier.entite.question.qcm.QCM;

/** 
 * Classe JPanel pour saisir les paramètres intitiales lorsqu'on mdoifie une question existente.
 * Cette classe hérite de PanelInitQuestion et ne fait que ajouter les données de la question
 * qu'on veut modifier aux champs correspondants.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelModifInitQuestion extends PanelInitQuestion
{	
	private int idQst;

	/**
	 * Constructeur de la classe PanelModifInitQuestion
	 * @param ctrl  le contrôleur.
	 * @param frame le frame d'édition de questions.
	 * @param idQst l'id de la question qu'on veut modifier.
	 */
	public PanelModifInitQuestion(Controleur ctrl, FrameEditQuestion frame, int idQst) 
	{
		super(ctrl, frame);

		Question question;

		int m;
		int s;


		this.idQst = idQst;

		question = this.ctrl.getQuestion(this.idQst);

		this.txtPoints.setText(Double.toString(question.getNote()));

		m = question.getTemps() / 60;
		s = question.getTemps() % 60;
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
