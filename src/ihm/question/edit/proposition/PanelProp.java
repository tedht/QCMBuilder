package ihm.question.edit.proposition;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ihm.question.edit.PanelAjoutQuestion;


/** 
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class PanelProp extends JPanel implements ActionListener
{
	protected PanelAjoutQuestion panelAjoutQuestion;

	public PanelProp(PanelAjoutQuestion panelAjoutQuestion)
	{
		this.panelAjoutQuestion = panelAjoutQuestion;

		this.setLayout(new BorderLayout(5,5));
	}

}
