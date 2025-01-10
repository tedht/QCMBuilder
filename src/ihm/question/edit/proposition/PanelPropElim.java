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
 * Classe JPanel représentant une proposition de réponse d'une question QRM
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

	/**
	 * Constructeur de la classe PanelPropElim.
	 * @param panelAjoutQuestion le panel qui gère l'ajout de propositions.
	 */
	public PanelPropElim(PanelAjoutQuestion panelAjoutQuestion)
	{
		super(panelAjoutQuestion);

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

	/**
	 * Retourne le texte de la proposition.
	 * 
	 * @return le texte de la proposition.
	 */
	public String  getText() 
	{ 
		return this.txtProposition.getText(); 
	}

	/**
	 * Indique si la proposition est marquée comme réponse correcte.
	 * 
	 * @return true si c'est une réponse correcte, false sinon.
	 */
	public boolean estReponse() 
	{ 
		return this.rbReponse.isSelected();
	}

	/**
	 * Retourne l'ordre d'élimination sous forme de chaine de caractères.
	 * 
	 * @return l'ordre d'élimination sous forme de chaine de caractères.
	 */
	public String getOrdreElim() 
	{ 
		return this.txtOrdreElim.getText(); 
	}

	/**
	 * Retourne le nombre de points perdus sous forme de chaine de caractères.
	 * 
	 * @return le nombre de points perdus sous forme de chaine de caractères.
	 */
	public String  getPointsPerdus() 
	{ 
		return this.txtPointsPerdus.getText(); 
	}

	/*---------*/
	/* Setters */
	/*---------*/

	/**
	 * Définit le texte de la proposition.
	 * 
	 * @param text le texte à afficher dans la zone de texte.
	 */
	public void setText(String  text)       
	{ 
		this.txtProposition.setText(text);       
	}

	/**
	 * Définit si la proposition est une réponse correcte.
	 * 
	 * @param reponse true si c'est une réponse correcte, false sinon.
	 */
	public void setReponse(boolean reponse)
	{ 
		this.rbReponse.setSelected(reponse);
	}

	/**
	 * Définit l'ordre délimination.
	 * 
	 * @param ordreElim l'ordre délimination.
	 */
	public void setOrdreElim(String ordreElim) 
	{ 
		this.txtOrdreElim.setText(ordreElim);  
	}

	/**
	 * Définit le nombre de points perdus.
	 * 
	 * @param ptsPerdus le nombre de points perdus.
	 */
	public void setPointsPerdus(String  ptsPerdus) { this.txtPointsPerdus.setText(ptsPerdus); }

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/
	
	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnSupprimer)
		{
			this.panelAjoutQuestion.supprimerRbResponse(this.rbReponse);
			this.panelAjoutQuestion.supprimerProposition(this);
		}
	}

	/**
	 * Gère les changements d'état des éléments interactifs
	 * 
	 * @param e l'événement qui déclenche l'appel à cette méthode.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.rbReponse)
		{
			// Si c'est une réponse, désactive les champs "Ordre" et "Pts perdus"
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				this.lblOrdreElim    .setForeground(Color.GRAY);
				this.txtOrdreElim    .setEnabled(false);
				this.txtOrdreElim    .setText("");

				this.lblPointsPerdus.setForeground(Color.GRAY);
				this.txtPointsPerdus.setEnabled(false);
				this.txtPointsPerdus.setText("");
			}
			else // Sinon réactive les champs "Ordre" et "Pts perdus"
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

	/**
	 * Classe interne pour gérer ce qui se passe lorsqu'on modifie le texte.
	 */
	private class GereText implements DocumentListener 
	{
		/**
		 * Action déclenchée lorsqu'on insère une caractère dans le champs "Ordre"
		 * ou "Pts perdus".
		 */
		@Override
		public void insertUpdate(DocumentEvent e) 
		{
			// Si un des champs n'est pas vide, on désactive le bouton radio pour sélectionner
			// la proposition en tant que réponse correcte.
			if(   !"".equals(PanelPropElim.this.txtOrdreElim    .getText()) 
			   || !"".equals(PanelPropElim.this.txtPointsPerdus.getText()))
			{
				PanelPropElim.this.rbReponse.setEnabled(false);
			}
		}

		/**
		 * Action déclenchée lorsqu'on upprime une caractère du champs "Ordre"
		 * ou "Pts perdus".
		 */
		@Override
		public void removeUpdate(DocumentEvent e) 
		{
			// Si les deux champs sont vides, on réactive le bouton radio pour sélectionner
			// la proposition en tant que réponse correcte.
			if(   "".equals(PanelPropElim.this.txtOrdreElim    .getText()) 
			   && "".equals(PanelPropElim.this.txtPointsPerdus.getText()))
			{
				PanelPropElim.this.rbReponse.setEnabled(true);
			}
		}

		/**
		 * Méthode inutilisée
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {}
	}
}
