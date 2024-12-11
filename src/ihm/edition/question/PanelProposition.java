package ihm.edition.question;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class PanelProposition extends JPanel implements ActionListener
{
	private JPanel    panelParent;

	private JTextArea txtProposition;
	private JButton   btnSupprimer;
	private JCheckBox cbReponseCorrecte;

	public PanelProposition(JPanel panelParent)
	{
		JPanel panelBtnSupprimer;

		this.panelParent = panelParent;

		this.setLayout(new BorderLayout(5,5));

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

		this.cbReponseCorrecte = new JCheckBox();

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.txtProposition, BorderLayout.CENTER);

		this.add(panelBtnSupprimer, BorderLayout.WEST);
		panelBtnSupprimer.add(this.btnSupprimer);

		this.add(this.cbReponseCorrecte, BorderLayout.EAST);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnSupprimer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnSupprimer)
		{
			this.panelParent.remove(this);

			this.panelParent.revalidate();
		}
	}
	
}
