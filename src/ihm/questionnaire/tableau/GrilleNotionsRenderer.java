package ihm.questionnaire.tableau;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import metier.entite.question.Difficulte;

public class GrilleNotionsRenderer extends DefaultTableCellRenderer 
{
    private final JCheckBox  cb  = new JCheckBox();
	private final JTextField txt = new JTextField();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int lig, int col) {

        if (value instanceof Boolean) 
		{
            cb.setSelected((Boolean) value);
            cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.setBackground(table.getBackground());

            return cb;
        }

		if (value instanceof Integer) 
        {
            txt.setText(Integer.toString((Integer)value));
            txt.setHorizontalAlignment(SwingConstants.CENTER);
            txt.setBorder(null);

			Boolean notionSelect = (Boolean) table.getValueAt(lig, 1);
			if(Boolean.TRUE.equals(notionSelect))
            {
				switch (col) 
				{
					case 2  -> txt.setBackground(Difficulte.TRES_FACILE.getCouleur());
					case 3  -> txt.setBackground(Difficulte.FACILE     .getCouleur());
					case 4  -> txt.setBackground(Difficulte.MOYEN      .getCouleur());
					case 5  -> txt.setBackground(Difficulte.DIFFICILE  .getCouleur());
					default -> txt.setBackground(table.getBackground());
				}
                txt.setForeground(Color.BLACK);
            } 
            else 
            {
                txt.setBackground(table.getBackground());
                txt.setForeground(table.getForeground()); 
            }

			txt.setCaretColor(txt.getForeground());

            return txt;
        }

		if (value instanceof String) 
		{
            txt.setText((String) value);
            txt.setHorizontalAlignment(SwingConstants.LEFT);
            txt.setBorder(null);

			txt.setBackground(table.getBackground());
			txt.setForeground(table.getForeground()); 

			return txt;
		}

        return super.getTableCellRendererComponent(table, "", isSelected, hasFocus, lig, col);
    }
}
