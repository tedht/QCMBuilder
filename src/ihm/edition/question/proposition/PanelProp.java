package ihm.edition.question.proposition;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ihm.edition.question.PanelEditQuestion;

/**
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/13
 * @version 1.0
 */
public abstract class PanelProp extends JPanel implements ActionListener
{
	protected PanelEditQuestion panelEditQuestion;

	public PanelProp(PanelEditQuestion panelEditQuestion)
	{
		this.panelEditQuestion = panelEditQuestion;

		this.setLayout(new BorderLayout(5,5));
	}

}
