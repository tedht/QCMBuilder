package ihm.question.edit.proposition;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

import ihm.IHM;
import ihm.question.edit.PanelAjoutQuestion;

/** 
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelPropQCM extends PanelProp
{
	private JTextArea    txtProposition;
	private JButton      btnSupprimer;
	private JRadioButton rbReponse;

	public PanelPropQCM(PanelAjoutQuestion panelEditQuestion)
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

		this.btnSupprimer = new JButton(IHM.getImgIconSVG("res/Supprimer.svg", 16, 16));
		this.btnSupprimer.setPreferredSize(new Dimension(24, 24));

		this.rbReponse = new JRadioButton();

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.txtProposition, BorderLayout.CENTER);

		this.add(panelBtnSupprimer, BorderLayout.WEST);
		panelBtnSupprimer.add(this.btnSupprimer);

		this.add(this.rbReponse, BorderLayout.EAST);

		this.panelAjoutQuestion.ajouterRbResponse(this.rbReponse);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnSupprimer.addActionListener(this);
	}

	/*---------*/
	/* Getters */
	/*---------*/

	public String  getText   () { return this.txtProposition.getText   (); }
	public boolean estReponse() { return this.rbReponse     .isSelected(); }

	/*---------*/
	/* Setters */
	/*---------*/

	public void setText   (String  text)    { this.txtProposition.setText    (text);    }
	public void setReponse(boolean reponse) { this.rbReponse     .setSelected(reponse); }

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnSupprimer)
		{
			this.panelAjoutQuestion.supprimerRbResponse(this.rbReponse);
			this.panelAjoutQuestion.supprimerProposition(this);
		}
	}
	
}
