package QCMBuilder.ihm.questionnaire.tableau;


import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import QCMBuilder.metier.entite.question.Difficulte;

/**
 * Classe qui personnalise l'éditeur de cellule du tableau pour afficher des 
 * couleurs et gérer l'alignement du texte.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-18 Norme ISO-8601
 */
public class GrilleNotionsEditor extends DefaultCellEditor 
{
	private final JTextField txt;

	/**
	 * Constructeur de la classe GrilleNotionsEditor
	 */
	public GrilleNotionsEditor() 
	{
		super(new JTextField());
		this.txt = (JTextField) getComponent();
	}

	/**
	 * Retourne le composant utilisé pour éditer une cellule.
	 * 
	 * @param table      la JTable contenant la cellule.
	 * @param value      la valeur actuelle de la cellule.
	 * @param isSelected indique si la cellule est sélectionnée.
	 * @param lig        la ligne de la cellule.
	 * @param col        la clonne de la cellule.
	 * @return le composant configuré pour éditer la cellule.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int lig, int col) 
	{
		Component editor;
		Boolean notionSelect;


		editor = super.getTableCellEditorComponent(table, value, isSelected, lig, col);

		// Vérifie si la valeur est un entier pour configurer correctement le champ de texte.
		if (value instanceof Integer) 
		{
			this.txt.setText(Integer.toString((Integer)value));
			this.txt.setHorizontalAlignment(SwingConstants.CENTER);
			this.txt.setBorder(null);
			txt.setCaretPosition(txt.getText().length());

			// Vérifie si la notion correspondante est sélectionnée.
			notionSelect = (Boolean) table.getValueAt(lig, 1);
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
		}

		return editor;
	}

	/**
	 * Récupère la valeur actuelle du champ de texte en entieren le convertissant en Integer.
	 * 
	 * @return La valeur entière de la cellule ou 0 en cas d'erreur.
	 */
	@Override
	public Object getCellEditorValue() 
	{
		try 
		{
			return Integer.parseInt(this.txt.getText());
		} 
		catch (NumberFormatException e) 
		{
			return 0;
		}
	}
}