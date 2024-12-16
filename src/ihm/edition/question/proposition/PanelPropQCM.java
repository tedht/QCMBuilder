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
public class PanelPropQCM extends PanelProp
{
	private ButtonGroup btgQCM;

	private JTextArea    txtProposition;
	private JButton      btnSupprimer;
	private JRadioButton rbReponse;

	public PanelPropQCM(PanelEditQuestion panelEditQuestion)
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

		this.rbReponse = new JRadioButton();

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.txtProposition, BorderLayout.CENTER);

		this.add(panelBtnSupprimer, BorderLayout.WEST);
		panelBtnSupprimer.add(this.btnSupprimer);

		this.add(this.rbReponse, BorderLayout.EAST);

		this.panelEditQuestion.ajouterRbResponse(this.rbReponse);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnSupprimer.addActionListener(this);
	}

	public String  getText   () { return this.txtProposition.getText   (); }
	public boolean getReponse() { return this.rbReponse     .isSelected(); }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnSupprimer)
		{
			this.btgQCM.remove(this.rbReponse);
			this.panelEditQuestion.supprimerProposition(this);
		}
	}
	
}
