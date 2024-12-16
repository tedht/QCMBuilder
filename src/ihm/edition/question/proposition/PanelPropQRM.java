package ihm.edition.question.proposition;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

import ihm.edition.question.PanelEditQuestion;

/**
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/13
 * @version 1.0
 */
public class PanelPropQRM extends PanelProp
{
	private JTextArea txtProposition;
	private JButton   btnSupprimer;
	private JCheckBox cbReponse;

	public PanelPropQRM(PanelEditQuestion panelEditQuestion)
	{
		super(panelEditQuestion);

		JPanel panelBtnSupprimer;

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		panelBtnSupprimer = new JPanel();

		this.txtProposition = new JTextArea();
		this.txtProposition.setRows(2);
		this.txtProposition.setLineWrap(true);     
		this.txtProposition.setWrapStyleWord(true);
		this.txtProposition.setBorder(new LineBorder(Color.GRAY));
		this.txtProposition.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtProposition.setCaretColor(Color.BLACK);
		this.txtProposition.setMargin(new Insets(2, 5, 2, 5));

		this.btnSupprimer = new JButton("Suppr");

		this.cbReponse = new JCheckBox();

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.txtProposition, BorderLayout.CENTER);

		this.add(panelBtnSupprimer, BorderLayout.WEST);
		panelBtnSupprimer.add(this.btnSupprimer);

		this.add(this.cbReponse, BorderLayout.EAST);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnSupprimer.addActionListener(this);
	}

	public String  getText   () { return this.txtProposition.getText   (); }
	public boolean getReponse() { return this.cbReponse     .isSelected(); }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnSupprimer)
		{
			this.panelEditQuestion.supprimerProposition(this);
		}
	}
	
}
