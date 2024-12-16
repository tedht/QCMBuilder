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
public class PanelPropAssoc extends PanelProp
{
	private JTextArea[] tabTxtProposition;
	private JButton  [] tabBtnSupprimer;
	
	public PanelPropAssoc(PanelEditQuestion panelEditQuestion)
	{
		super(panelEditQuestion);

		JPanel[] tabPanelBtnSupprimer;
		
		JPanel panelTxt;

		this.tabTxtProposition = new JTextArea[2];
		this.tabBtnSupprimer   = new JButton  [2];
		tabPanelBtnSupprimer   = new JPanel   [2];

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		panelTxt = new JPanel(new GridLayout(1, 2, 5 ,0));

		for(int i = 0; i < 2; i++)
		{
			tabPanelBtnSupprimer[i] = new JPanel();

			this.tabTxtProposition[i] = new JTextArea();
			this.tabTxtProposition[i].setRows(2);
			this.tabTxtProposition[i].setLineWrap(true);     
			this.tabTxtProposition[i].setWrapStyleWord(true);
			this.tabTxtProposition[i].setBorder(new LineBorder(Color.GRAY));
			this.tabTxtProposition[i].setFont(new Font("Arial", Font.PLAIN, 12));
			this.tabTxtProposition[i].setCaretColor(Color.BLACK);
			this.tabTxtProposition[i].setMargin(new Insets(2, 5, 2, 5));
	
			this.tabBtnSupprimer[i] = new JButton("Suppr");

		}

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(tabPanelBtnSupprimer[0], BorderLayout.WEST);
		tabPanelBtnSupprimer[0].add(this.tabBtnSupprimer[0]);

		this.add(tabPanelBtnSupprimer[1], BorderLayout.EAST);
		tabPanelBtnSupprimer[1].add(this.tabBtnSupprimer[1]);

		this    .add(panelTxt, BorderLayout.CENTER);
		panelTxt.add(this.tabTxtProposition[0]);
		panelTxt.add(this.tabTxtProposition[1]);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.tabBtnSupprimer[0].addActionListener(this);
		this.tabBtnSupprimer[1].addActionListener(this);
	}

	public String getTextGauche () { return this.tabTxtProposition[0].getText(); }
	public String getTextDroite () { return this.tabTxtProposition[1].getText(); }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.tabBtnSupprimer[0] || e.getSource() == this.tabBtnSupprimer[1])
		{
			this.panelEditQuestion.supprimerProposition(this);
		}
	}
	
}