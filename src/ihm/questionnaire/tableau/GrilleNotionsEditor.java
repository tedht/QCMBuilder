package ihm.questionnaire.tableau;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;

import metier.entite.question.Difficulte;

public class GrilleNotionsEditor extends DefaultCellEditor 
{
    private final JTextField txt;

    public GrilleNotionsEditor() 
	{
        super(new JTextField());
        this.txt = (JTextField) getComponent();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int lig, int col) 
	{
        Component editor = super.getTableCellEditorComponent(table, value, isSelected, lig, col);

		if (value instanceof Integer) 
        {
            this.txt.setText(Integer.toString((Integer)value));
            this.txt.setHorizontalAlignment(SwingConstants.CENTER);
            this.txt.setBorder(null);
			txt.setCaretPosition(txt.getText().length());

			Boolean notionSelect = (Boolean) table.getValueAt(lig, 1);
			if(Boolean.TRUE.equals(notionSelect))
            {
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
                this.txt.setBackground(table.getBackground());
                this.txt.setForeground(table.getForeground()); 
            }

			this.txt.setCaretColor(this.txt.getForeground());
		}

        return editor;
    }

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