package QCMBuilder.ihm.questionnaire.tableau;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import QCMBuilder.metier.entite.Notion;
import QCMBuilder.metier.entite.question.Difficulte;

/**
 * Classe qui personnalise l'affichage des cellules du tableau en fonction du 
 * type de données et de la colonne.
 */
public class GrilleNotionsRenderer extends DefaultTableCellRenderer 
{
	private final JCheckBox  cb ;
	private final JTextField txt;

	/**
	 * Constructeur de la classe GrilleNotions Renderer
	 */
	public GrilleNotionsRenderer()
	{
		this.cb  = new JCheckBox ();
		this.txt = new JTextField();
	}

	/**
	 * Retourne le composant utilisé pour afficher une cellule. Personnalise le 
	 * rendu en fonction du type de données et de la colonne.
	 * 
	 * @param table      la JTable contenant la cellule.
	 * @param value      la valeur de la cellule.
	 * @param isSelected indique si la cellule est sélectionnée.
	 * @param hasFocus   indique si la cellule a le focus.
	 * @param lig        ligne de la cellule.
	 * @param col        colonne de la cellule.
	 * @return le composant configuré pour afficher la cellule.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int lig, int col)
	{
		// Rendu pour les valeurs booléennes avec une case à cocher.
		if (value instanceof Boolean) 
		{
			this.cb.setSelected((Boolean) value);
			this.cb.setHorizontalAlignment(SwingConstants.CENTER);
			this.cb.setBackground(table.getBackground());

			return this.cb;
		}

		// Rendu pour les valeurs entières.
		if (value instanceof Integer) 
		{
			this.txt.setText(Integer.toString((Integer)value));
			this.txt.setHorizontalAlignment(SwingConstants.CENTER);
			this.txt.setBorder(null);

			// Vérifie si la notion correspondante est sélectionnée.
			Boolean notionSelect = (Boolean) table.getValueAt(lig, 1);
			if(Boolean.TRUE.equals(notionSelect))
			{
				// Applique la couleur de fond associée à la difficulté (colonne).
				switch (col) 
				{
					case 2  -> this.txt.setBackground(Difficulte.TRES_FACILE.getCouleur());
					case 3  -> this.txt.setBackground(Difficulte.FACILE     .getCouleur());
					case 4  -> this.txt.setBackground(Difficulte.MOYEN      .getCouleur());
					case 5  -> this.txt.setBackground(Difficulte.DIFFICILE  .getCouleur());
					default -> this.txt.setBackground(table.getBackground());
				}
				this.txt.setForeground(Color.BLACK);
			} 
			else 
			{
				// Applique les couleurs par défaut si la notion n'est pas sélectionnée.
				this.txt.setBackground(table.getBackground());
				this.txt.setForeground(table.getForeground()); 
			}

			// Configure la couleur du curseur.
			this.txt.setCaretColor(this.txt.getForeground());

			return this.txt;
		}

		// Rendu pour les chaînes de caractères.
		if (value instanceof String) 
		{
			this.txt.setText((String) value);

			// Aligne le texte à gauche pour la première colonne, sinon centré.
			if (col == 0) 
				this.txt.setHorizontalAlignment(SwingConstants.LEFT);
			else
				this.txt.setHorizontalAlignment(SwingConstants.CENTER);

			this.txt.setBorder(null);

			this.txt.setBackground(table.getBackground());
			this.txt.setForeground(table.getForeground()); 

			return this.txt;
		}

		// Rendu pour les objets de type Notion.
		if (value instanceof Notion) 
		{
			// Affiche la valeur de l'objet Notion.
			this.txt.setText(((Notion)value).toString());
			this.txt.setBorder(null);

			return this.txt;
		}

		// Appel à l'implémentation par défaut pour les autres types.
		return super.getTableCellRendererComponent(table, "", isSelected, hasFocus, lig, col);
	}
}
