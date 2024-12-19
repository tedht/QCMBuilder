package ihm.question;

import java.awt.event.*;

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
	private String  codeRessource;
	private int     idNotion;
	private boolean detail;
	
	/**
	 * Constructeur de la classe PanelQuestion.
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelQuestion(Controleur ctrl, IHM ihm, String intitule, String info, String codeRessource, int idNotion)
	{
		super(ctrl, ihm, intitule, info);

		this.codeRessource = codeRessource;
		this.idNotion      = idNotion;

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
			
		}

		if(e.getSource() == this.btnSupprimer)
		{
			this.ctrl.supprimerQuestion(this.codeRessource, this.idNotion);
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
