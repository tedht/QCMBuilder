package ihm.questionnaire.tableau;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import controleur.Controleur;
import ihm.questionnaire.PanelAjoutQuestionnaire;
import metier.entite.Notion;
import metier.entite.Ressource;

/**
 * Classe représentant la fenêtre de création d'une évaluation
 * 
 * @author Ted Herambert
 * @date 2024/12/17
 * @version 1.0
 */
public class GrilleNotionsModel extends AbstractTableModel
{
	private Controleur           ctrl;
	private PanelAjoutQuestionnaire panel;

	private String[]   tabEntetes;
	private Object[][] tabDonnees;

	private int[]      tabNbQuestionsDiff;

	public GrilleNotionsModel(Controleur ctrl, PanelAjoutQuestionnaire panel, Ressource ressource)
	{
		this.ctrl  = ctrl;
		this.panel = panel;

		this.tabNbQuestionsDiff = new int[4];

		String nomNotion;
		List<Notion> lstNotions = this.ctrl.getNotions(ressource) != null ? this.ctrl.getNotions(ressource) : new ArrayList<Notion>();

		tabDonnees = new Object[lstNotions.size()+2][7];

		for ( int lig = 0; lig < lstNotions.size(); lig++)
		{
			nomNotion = lstNotions.get(lig).getNom();

			tabDonnees[lig][0] = nomNotion;
			tabDonnees[lig][1] = false;
			tabDonnees[lig][2] = null;
			tabDonnees[lig][3] = null;
			tabDonnees[lig][4] = null;
			tabDonnees[lig][5] = null;
			tabDonnees[lig][6] = null;
		}

		tabDonnees[tabDonnees.length-2] = new Object[]{ null, null , null, null, null, null, null };
		tabDonnees[tabDonnees.length-1] = new Object[]{ null, null , 0, 0, 0, 0, "Σ = 0" };

		this.tabEntetes = new String[]{ "Notion", " " , "TF", "F", "M", "D", " " };

	}

	public int      getColumnCount()                 { return this.tabEntetes.length;      }
	public int      getRowCount   ()                 { return this.tabDonnees.length;      }
	public String   getColumnName (int col)          { return this.tabEntetes[col];        }
	public Object   getValueAt    (int lig, int col) { return this.tabDonnees[lig][col];   }
	public Class<?> getColumnClass(int col)            
	{  
		Object value = getValueAt(0, col);
		return (value != null) ? value.getClass() : Object.class; 
	}

	public boolean isCellEditable(int lig, int col)
	{
		
		return (col == 1 || (2 <= col && col <= 5 && this.getValueAt(lig, 1) == Boolean.TRUE))
		       && lig < tabDonnees.length-2;
	}

	public void setValueAt(Object value, int lig, int col)
	{
		if (col == 1)
		{
			this.tabDonnees[lig][col] = value;
			this.fireTableCellUpdated(lig, col);

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
		if (2 <= col && col <= 5)
		{
			if (value instanceof Integer && 0 <= (Integer)value && (Integer)value <= 999)
			{
				Integer oldVal = (Integer)this.tabDonnees[lig][col];
				Integer newVal = (Integer)value;

				this.tabDonnees[lig][col] = newVal;
				this.fireTableCellUpdated(lig, col);

				this.tabNbQuestionsDiff[col-2] = this.tabNbQuestionsDiff[col-2] - oldVal + newVal;
				this.tabDonnees[this.getRowCount()-1][col] = this.tabNbQuestionsDiff[col-2];
				this.fireTableCellUpdated(this.getRowCount()-1, col);

				int nbQuestionsTotales = this.getNbQuestionsTotales();

				this.tabDonnees[this.getRowCount()-1][this.getColumnCount()-1] = "Σ = " + nbQuestionsTotales;
				this.fireTableCellUpdated(this.getRowCount()-1, this.getColumnCount()-1);

				if(nbQuestionsTotales > 0)
					this.panel.setBtnGenererEnabled(true);
				else
					this.panel.setBtnGenererEnabled(false);


			}
		}
	}

	public int getNbQuestionsTotales()
	{
		return   this.tabNbQuestionsDiff[0] 
			   + this.tabNbQuestionsDiff[1] 
			   + this.tabNbQuestionsDiff[2] 
			   + this.tabNbQuestionsDiff[3];
	}


}
