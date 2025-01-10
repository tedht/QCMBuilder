package QCMBuilder.ihm.questionnaire.tableau;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import QCMBuilder.controleur.Controleur;
import QCMBuilder.ihm.questionnaire.PanelAjoutQuestionnaire;
import QCMBuilder.metier.entite.Notion;
import QCMBuilder.metier.entite.question.Difficulte;

/**
 * Classe pour gérer l'affichage du tableau des nombres de question à générer 
 * ainsi que les modifications éventuelles
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-18 Norme ISO-8601
 */
public class GrilleNotionsModel extends AbstractTableModel
{
	private Controleur              ctrl;
	private PanelAjoutQuestionnaire panel;

	private String[]   tabEntetes;
	private Object[][] tabDonnees;

	private int[] tabNbQuestionsDiff;

	/**
	 * Constructeur de la classe GrilleNotionsModel.
	 * 
	 * @param ctrl    le contrôleur.
	 * @param panel   le panel qui contient le tableau
	 * @param codeRes le code de la ressource sélectionnée
	 */
	public GrilleNotionsModel(Controleur ctrl, PanelAjoutQuestionnaire panel, String codeRes)
	{
		this.ctrl    = ctrl;
		this.panel   = panel;
		
		this.tabNbQuestionsDiff = new int[4];

		List<Notion> lstNotions =   this.ctrl.getNotions(codeRes) != null 
								  ? this.ctrl.getNotions(codeRes)
								  : new ArrayList<Notion>();

		this.tabDonnees = new Object[lstNotions.size()+2][7];

		for ( int lig = 0; lig < lstNotions.size(); lig++)
		{
			this.tabDonnees[lig][0] = lstNotions.get(lig);
			this.tabDonnees[lig][1] = false;
			this.tabDonnees[lig][2] = null;
			this.tabDonnees[lig][3] = null;
			this.tabDonnees[lig][4] = null;
			this.tabDonnees[lig][5] = null;
			this.tabDonnees[lig][6] = null;
		}

		this.tabDonnees[this.tabDonnees.length-2] = new Object[]{ null, null , null, null, null, null, null };
		this.tabDonnees[this.tabDonnees.length-1] = new Object[]{ null, null , 0, 0, 0, 0, "Σ = 0" };

		this.tabEntetes = new String[]{ "Notion", " " , "TF", "F", "M", "D", " " };

	}

	/**
	 * Retourne le nombre de colonnes.
	 * 
	 * @return le nombre de colonnes.
	 */
	@Override
	public int getColumnCount()                 
	{ 
		return this.tabEntetes.length;      
	}

	/**
	 * Retourne le nombre de lignes.
	 * 
	 * @return le nombre de lignes.
	 */
	@Override
	public int getRowCount()                 
	{ 
		return this.tabDonnees.length;      
	}

	/**
	 * Retourne le nom de la colonne.
	 * 
	 * @return le nom de la colonne.
	 */
	@Override
	public String getColumnName(int col)          
	{ 
		return this.tabEntetes[col];        
	}

	/**
	 * Retourne la valeur de la cellule.
	 * 
	 * @param lig la ligne de la cellule.
	 * @param col la colonne de la cellule.
	 * @return la valeur de la cellule.
	 */
	@Override
	public Object getValueAt(int lig, int col) 
	{ 
		return this.tabDonnees[lig][col];   
	}

	/**
	 * Retourne la classe de la colonne.
	 * 
	 * @return la classe de la colonne.
	 */
	@Override
	public Class<?> getColumnClass(int col)            
	{  
		Object value = getValueAt(0, col);
		return (value != null) ? value.getClass() : Object.class; 
	}

	/**
	 * Vérifie si une cellule est modifiable
	 * 
	 * @return true si la cellule est modifiable, false sinon
	 */
	@Override
	public boolean isCellEditable(int lig, int col)
	{
		return (col == 1 || (2 <= col && col <= 5 && this.getValueAt(lig, 1) == Boolean.TRUE))
			   && lig < tabDonnees.length-2;
	}

	/**
	 * Gère la mise à jour d'une cellule.
	 */
	@Override
	public void setValueAt(Object value, int lig, int col)
	{
		if (col == 1) // Colonne de sélection
		{
			this.tabDonnees[lig][col] = value;
			this.fireTableCellUpdated(lig, col);

			// Activation ou désactivation des colonnes de difficulté
			if(Boolean.TRUE.equals(value))
			{
				for(int i = 1; i <= 4; i++)
				{
					this.tabDonnees[lig][col+i] = 0;
					this.fireTableCellUpdated(lig, col+i);
				}
			}
			else 
			{
				for(int i = 1; i <= 4; i++)
				{
					this.tabDonnees[lig][col+i] = null;
					this.fireTableCellUpdated(lig, col+i);
				}
			}
		}
		if (2 <= col && col <= 5) // Colonnes associées aux difficultés
		{
			if (value instanceof Integer)
			{
				Integer oldVal = (Integer)this.tabDonnees[lig][col];
				Integer newVal = (Integer)value;

				// Récupération du nombre maximum de questions disponibles pour cette difficulté
				String codeRes = ((Notion)this.tabDonnees[lig][0]).getCodeRes(); 
				int    idNot   = ((Notion)this.tabDonnees[lig][0]).getIdNot();
				int    nbQst   = this.ctrl.getQuestions(codeRes, idNot, Difficulte.fromInt(col-2)).size();
					
				// Si la nouvelle valeur est supérieure au nombre de questions disponibles, on utilise
				// le nombre de question
				if(newVal > nbQst)
					newVal = nbQst;
				
				// Validation et ajustement de la nouvelle valeur
				newVal = Math.max(0, Math.min(newVal, Math.min(nbQst, 99)));

				// Met à jour de la cellule
				this.tabDonnees[lig][col] = newVal;
				this.fireTableCellUpdated(lig, col);

				// Met à jour du total de la difficultée
				this.tabNbQuestionsDiff[col-2] = this.tabNbQuestionsDiff[col-2] - oldVal + newVal;
				this.tabDonnees[this.getRowCount()-1][col] = this.tabNbQuestionsDiff[col-2];
				this.fireTableCellUpdated(this.getRowCount()-1, col);

				int nbQuestionsTotales = this.getNbQuestionsTotales();

				// Met à jour du total général
				this.tabDonnees[this.getRowCount()-1][this.getColumnCount()-1] = "Σ = " + nbQuestionsTotales;
				this.fireTableCellUpdated(this.getRowCount()-1, this.getColumnCount()-1);

				// Activation/désactivation du bouton "Générer"
				this.panel.setBtnGenererEnabled(nbQuestionsTotales > 0);
			}
		}
	}

	/**
	 * Calcule et retourne le nombre total de questions à générer.
	 * 
	 * @return Le nombre total de questions à générer.
	 */
	public int getNbQuestionsTotales()
	{
		return   this.tabNbQuestionsDiff[0] 
			   + this.tabNbQuestionsDiff[1] 
			   + this.tabNbQuestionsDiff[2] 
			   + this.tabNbQuestionsDiff[3];
	}

	/**
	 * Récupère les données du tableau. Chaque cellule du tableau contient le nombre de questions
	 * à générer associées à une notion (la ligne) et une difficulté (la colonne)
	 * 
	 * @return Un tableau 2D contenant le nombre de questions à générer.
	 */
	public int[][] getTabNbQuestions()
	{
		int[][] tabNbQuestions = new int[this.getRowCount()-2][4];
		
		for(int i = 0; i < this.getRowCount()-2; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				tabNbQuestions[i][j] = this.tabDonnees[i][j+2] == null ? 0 : (Integer)this.tabDonnees[i][j+2];
			}
		}

		return tabNbQuestions;
	}
}
