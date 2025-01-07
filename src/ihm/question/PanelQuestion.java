package ihm.question;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JPanel;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEntite;

/**
 * Classe JPanel pour afficher une Notion.
 * 
 * @author Ted Herambert
 * @date 2024/12/10
 * @version 1.0
 */
public class PanelQuestion extends PanelEntite
{
	private int     id;
	private boolean detail;

	private JPanel  panelDiff;
	
	public PanelQuestion()
	{
		super(null, null, "", "");
		this.id = 0;
	}

	/**
	 * Constructeur de la classe PanelQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelQuestion(Controleur ctrl, IHM ihm, String intitule, String info, int id)
	{
		super(ctrl, ihm, intitule, info);

		this.id = id;

		panelDiff = new JPanel();
		panelDiff.setBackground(this.ctrl.getQuestion(this.id).getDifficulte().getCouleur());

		this.add(panelDiff, BorderLayout.WEST);
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.addMouseListener(new GereSourisQuestion());
		this.btnModifier.addActionListener(this);
		this.btnSupprimer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnModifier)
		{
			this.ihm.editQuestion(this.id);
		}

		if(e.getSource() == this.btnSupprimer)
		{
			this.ctrl.supprimerQuestion(this.id);
			this.ihm.reinitAffichageQuestion();
		}
	}

	private class GereSourisQuestion extends MouseAdapter
	{
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			PanelQuestion.this.detail = !PanelQuestion.this.detail;
		}
	}
}
