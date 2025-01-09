package ihm.question.edit.proposition;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ihm.IHM;
import ihm.question.edit.PanelAjoutQuestion;

/** 
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelPropElim extends PanelProp implements ItemListener
{
	private JTextArea    txtProposition;
	private JButton      btnSupprimer;
	private JRadioButton rbReponse;
	private JLabel       lblOrdreElim, lblPointsPerdus;
	private JTextField   txtOrdreElim, txtPointsPerdus;

	public PanelPropElim(PanelAjoutQuestion panelEditQuestion)
	{
		super(panelEditQuestion);

		JPanel panelBtnSupprimer, panelElim, panelTxtOrdreElim, panelCbReponse, panelTxtPointsPerdus;

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		panelBtnSupprimer     = new JPanel();
		panelElim             = new JPanel(new GridLayout(2,2,1,1));
		panelTxtOrdreElim     = new JPanel(new BorderLayout());
		panelCbReponse        = new JPanel();
		panelTxtPointsPerdus = new JPanel(new BorderLayout());

		this.txtProposition = new JTextArea ();
		this.txtProposition.setRows         (2);
		this.txtProposition.setLineWrap     (true);     
		this.txtProposition.setWrapStyleWord(true);
		this.txtProposition.setBorder       (new LineBorder(Color.GRAY));
		this.txtProposition.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtProposition.setCaretColor   (Color.BLACK);
		this.txtProposition.setMargin       (new Insets(2, 5, 2, 5));

		this.btnSupprimer = new JButton(IHM.getImgIconSVG("res/Supprimer.svg", 16, 16));
		this.btnSupprimer.setPreferredSize(new Dimension(24, 24));
		
		this.rbReponse    = new JRadioButton();

		this.lblOrdreElim = new JLabel("Ordre :");
		this.txtOrdreElim = new JTextField(5);

		this.lblPointsPerdus = new JLabel("Pts perdus :");
		this.txtPointsPerdus = new JTextField(5);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.txtProposition, BorderLayout.CENTER);

		this.add(panelBtnSupprimer, BorderLayout.WEST);
		panelBtnSupprimer.add(this.btnSupprimer);

		this.add(panelElim, BorderLayout.EAST);

		panelElim            .add(panelTxtOrdreElim);
		panelTxtOrdreElim    .add(this.lblOrdreElim, BorderLayout.NORTH);
		panelTxtOrdreElim    .add(this.txtOrdreElim, BorderLayout.CENTER);

		panelElim            .add(panelCbReponse);
		panelCbReponse       .add(this.rbReponse);

		panelElim            .add(panelTxtPointsPerdus);
		panelTxtPointsPerdus.add(this.lblPointsPerdus, BorderLayout.NORTH);
		panelTxtPointsPerdus.add(this.txtPointsPerdus, BorderLayout.CENTER);
   
		this.panelAjoutQuestion.ajouterRbResponse(this.rbReponse);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnSupprimer    .addActionListener(this);

		this.rbReponse       .addItemListener  (this);

		this.txtOrdreElim    .getDocument().addDocumentListener(new GereText());
		this.txtPointsPerdus.getDocument().addDocumentListener(new GereText());
	}

	/*---------*/
	/* Getters */
	/*---------*/
	public String  getText         () { return this.txtProposition  .getText   (); }
	public boolean estReponse      () { return this.rbReponse       .isSelected(); }
	public String  getOrdreElim    () { return this.txtOrdreElim    .getText   (); }
	public String  getPointsPerdus() { return this.txtPointsPerdus.getText   (); }

	/*---------*/
	/* Setters */
	/*---------*/
	public void setText         (String  text)       { this.txtProposition  .setText    (text);       }
	public void setReponse      (boolean reponse)    { this.rbReponse       .setSelected(reponse);    }
	public void setOrdreElim    (String  ordreElim)  { this.txtOrdreElim    .setText    (ordreElim);  }
	public void setPointsPerdus(String  ptsPerdus) { this.txtPointsPerdus.setText    (ptsPerdus); }

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

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.rbReponse)
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				this.lblOrdreElim    .setForeground(Color.GRAY);
				this.txtOrdreElim    .setEnabled(false);
				this.txtOrdreElim    .setText("");

				this.lblPointsPerdus.setForeground(Color.GRAY);
				this.txtPointsPerdus.setEnabled(false);
				this.txtPointsPerdus.setText("");
			}
			else
			{
				this.lblOrdreElim    .setForeground(Color.BLACK);
				this.txtOrdreElim    .setEnabled(true);
				this.txtOrdreElim    .setText("");
				this.txtOrdreElim    .setForeground(Color.GRAY);

				this.lblPointsPerdus.setForeground(Color.BLACK);
				this.txtPointsPerdus.setEnabled(true);
				this.txtPointsPerdus.setText("");
				this.txtPointsPerdus.setForeground(Color.GRAY);
			}
		}
	}

	private class GereText implements DocumentListener 
	{
		@Override
		public void insertUpdate(DocumentEvent e) 
		{
			if(   !"".equals(PanelPropElim.this.txtOrdreElim    .getText()) 
			   || !"".equals(PanelPropElim.this.txtPointsPerdus.getText()))
			{
				PanelPropElim.this.rbReponse.setEnabled(false);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) 
		{
			if(   "".equals(PanelPropElim.this.txtOrdreElim    .getText()) 
			   && "".equals(PanelPropElim.this.txtPointsPerdus.getText()))
			{
				PanelPropElim.this.rbReponse.setEnabled(true);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {}
	}
}
