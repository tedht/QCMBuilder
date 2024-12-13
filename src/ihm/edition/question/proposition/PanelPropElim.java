package ihm.edition.question.proposition;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ihm.edition.question.PanelEditQuestion;

/**
 * 
 * 
 * @author Ted Herambert
 * @date 2024/12/13
 * @version 1.0
 */
public class PanelPropElim extends PanelProp
{
	private JTextArea  txtProposition;
	private JButton    btnSupprimer;
	private JCheckBox  cbResponse;
	private JTextField txtOrdreElim, txtPointsEnMoins;

	public PanelPropElim(PanelEditQuestion panelEditQuestion)
	{
		super(panelEditQuestion);

		JPanel panelBtnSupprimer, panelElim, panelTxtOrdreElim, panelCbReponse, panelTxtPointsEnMoins;

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		panelBtnSupprimer     = new JPanel();
		panelElim             = new JPanel(new GridLayout(2,2,1,1));
		panelTxtOrdreElim     = new JPanel();
		panelCbReponse        = new JPanel();
		panelTxtPointsEnMoins = new JPanel();

		this.txtProposition = new JTextArea();
		this.txtProposition.setRows(2);
		this.txtProposition.setLineWrap(true);     
		this.txtProposition.setWrapStyleWord(true);
		this.txtProposition.setBorder(new LineBorder(Color.GRAY));
		this.txtProposition.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtProposition.setCaretColor(Color.BLACK);
		this.txtProposition.setMargin(new Insets(2, 5, 2, 5));

		this.btnSupprimer     = new JButton("Suppr");

		this.cbResponse       = new JCheckBox();

		this.txtOrdreElim     = new JTextField(5);
		this.txtPointsEnMoins = new JTextField(5);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.txtProposition, BorderLayout.CENTER);

		this.add(panelBtnSupprimer, BorderLayout.WEST);
		panelBtnSupprimer.add(this.btnSupprimer);

		this.add(panelElim, BorderLayout.EAST);

		panelElim            .add(panelTxtOrdreElim);
		panelTxtOrdreElim    .add(this.txtOrdreElim);

		panelElim            .add(panelCbReponse);
		panelCbReponse       .add(this.cbResponse);

		panelElim            .add(panelTxtPointsEnMoins);
		panelTxtPointsEnMoins.add(this.txtPointsEnMoins);
   
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnSupprimer    .addActionListener(this);
		this.cbResponse      .addActionListener(this);

		this.txtOrdreElim    .getDocument().addDocumentListener(new GereText());
		this.txtPointsEnMoins.getDocument().addDocumentListener(new GereText());
	}

	public String  getText         () { return this.txtProposition  .getText   (); }
	public boolean getReponse      () { return this.cbResponse      .isSelected(); }
	public String  getOrdreElim    () { return this.txtOrdreElim    .getText   (); }
	public String  getPointsEnMoins() { return this.txtPointsEnMoins.getText   (); }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnSupprimer)
		{
			this.panelEditQuestion.supprimerProposition(this);
		}
		if(e.getSource() == this.cbResponse)
		{
			boolean estReponse = this.cbResponse.isSelected();
			this.txtOrdreElim    .setEnabled(!estReponse);
			this.txtPointsEnMoins.setEnabled(!estReponse);

			if(estReponse)
			{
				this.txtOrdreElim    .setText("");
				this.txtPointsEnMoins.setText("");
			}
		}
		/*
		if(e.getSource() == this.txtOrdreElim || e.getSource() == this.txtPointsEnMoins)
		{
			if("".equals(this.txtOrdreElim.getText()) && "".equals(this.txtPointsEnMoins.getText())) 
			{
				this.cbResponse.setEnabled(true);
			}
			else
			{
				this.cbResponse.setSelected(false);
				this.cbResponse.setEnabled(false);
			}
		}*/
	}

	private class GereText implements DocumentListener 
	{
		@Override
		public void insertUpdate(DocumentEvent e) 
		{
			PanelPropElim.this.cbResponse.setEnabled(false);
		}

		@Override
		public void removeUpdate(DocumentEvent e) 
		{
			if(   "".equals(PanelPropElim.this.txtOrdreElim    .getText()) 
			   && "".equals(PanelPropElim.this.txtPointsEnMoins.getText()))
			{
				PanelPropElim.this.cbResponse.setEnabled(true);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {}
	}
}
