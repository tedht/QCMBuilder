package ihm.questionnaire.tableau;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import controleur.Controleur;
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
	private Controleur ctrl;

	private String[]   tabEntetes;
	private Object[][] tabDonnees;

	public GrilleNotionsModel (Controleur ctrl, Ressource ressource)
	{
		this.ctrl = ctrl;

		String notion;

		List<String> lstNotions = this.ctrl.getNotions(ressource) != null ? this.ctrl.getNotions(ressource) : new ArrayList<String>();

		tabDonnees = new Object[lstNotions.size()+2][7];

		for ( int lig = 0; lig < lstNotions.size(); lig++)
		{
			notion = lstNotions.get(lig);

			tabDonnees[lig][0] = notion;
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
				this.tabDonnees[lig][col] = value;
				this.fireTableCellUpdated(lig, col);

				this.tabDonnees[this.getRowCount()-1][col] = (Integer)this.tabDonnees[this.getRowCount()-1][col] + (Integer)value;
				this.fireTableCellUpdated(this.getRowCount()-1, col);

				int somTotal = Integer.parseInt(((String)this.tabDonnees[this.getRowCount()-1][this.getColumnCount()-1]).substring(4)) 
				               + ((Integer) value);
				this.tabDonnees[this.getRowCount()-1][this.getColumnCount()-1] = "Σ = " + somTotal;
				this.fireTableCellUpdated(this.getRowCount()-1, this.getColumnCount()-1);
			}
		}
	}


}
