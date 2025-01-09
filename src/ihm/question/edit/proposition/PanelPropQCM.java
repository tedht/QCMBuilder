package ihm.question.edit.proposition;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

import ihm.IHM;
import ihm.question.edit.PanelAjoutQuestion;

/** 
 * Classe JPanel représentant une proposition de réponse d'une question QCM
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelPropQCM extends PanelProp
{
	private JTextArea    txtProposition;
	private JButton      btnSupprimer;
	private JRadioButton rbReponse;

	/**
	 * Constructeur de la classe PanelPropQCM.
	 * @param panelAjoutQuestion le panel qui gère l'ajout de propositions.
	 */
	public PanelPropQCM(PanelAjoutQuestion panelAjoutQuestion)
	{
		super(panelAjoutQuestion);

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
		this.txtProposition.setText    (text);    
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
	
}
