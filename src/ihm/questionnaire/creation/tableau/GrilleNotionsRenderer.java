package ihm.questionnaire.creation.tableau;

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
    private final JCheckBox  cb ;
	private final JTextField txt;

	public GrilleNotionsRenderer()
	{
		this.cb  = new JCheckBox ();
		this.txt = new JTextField();
	}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int lig, int col) {

        if (value instanceof Boolean) 
		{
            this.cb.setSelected((Boolean) value);
            this.cb.setHorizontalAlignment(SwingConstants.CENTER);
			this.cb.setBackground(table.getBackground());

            return this.cb;
        }

		if (value instanceof Integer) 
        {
            this.txt.setText(Integer.toString((Integer)value));
            this.txt.setHorizontalAlignment(SwingConstants.CENTER);
            this.txt.setBorder(null);

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

            return this.txt;
        }

		if (value instanceof String) 
		{
            this.txt.setText((String) value);

			if (col == 0) 
				this.txt.setHorizontalAlignment(SwingConstants.LEFT);
			else
				this.txt.setHorizontalAlignment(SwingConstants.CENTER);

            this.txt.setBorder(null);

			this.txt.setBackground(table.getBackground());
			this.txt.setForeground(table.getForeground()); 

			return this.txt;
		}

        return super.getTableCellRendererComponent(table, "", isSelected, hasFocus, lig, col);
    }
}
