package ihm.questionnaire;

import controleur.Controleur;
import ihm.questionnaire.tableau.GrilleNotionsEditor;
import ihm.questionnaire.tableau.GrilleNotionsModel;
import ihm.questionnaire.tableau.GrilleNotionsRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * Classe représentant dédiée à la saisie du nombre de questions à générer par notion
 * et par difficulté d'une évaluation.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-16 Norme ISO-8601
 */
public class PanelAjoutQuestionnaire extends JPanel implements ActionListener
{
	private Controleur                 ctrl;
	private FrameCreationQuestionnaire frame;

	private JPanel             panelInfo, panelAction;
	private GrilleNotionsModel grilleNotionsModel;
	private JTable             tblGrilleNotions;
	private JScrollPane        spGrilleNotions;
	private JButton            btnPrecedent, btnGenerer;

	/**
	 * Constructeur de la classe PanelAjoutQuestionnaire
	 * 
	 * @param ctrl  le contrôleur
	 * @param frame la fenêtre de création d'une évaluation
	 */
	public PanelAjoutQuestionnaire(Controleur ctrl, FrameCreationQuestionnaire frame) 
	{
		this.ctrl  = ctrl;
		this.frame = frame;

		this.setLayout(new BorderLayout());

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		/* INFO */
		this.panelInfo = new JPanel(new BorderLayout());
		this.panelInfo.setBorder(new EmptyBorder(20,20,20,20));

		this.grilleNotionsModel = new GrilleNotionsModel(ctrl, this, frame.getCodeRes());
		this.tblGrilleNotions   = new JTable(this.grilleNotionsModel);
		this.spGrilleNotions    = new JScrollPane(this.tblGrilleNotions);

		/* ACTION */
		this.panelAction = new JPanel();
		this.panelAction.setBackground(new Color(200, 200, 250));
		
		this.btnPrecedent = new JButton("Précédent");
		this.btnGenerer   = new JButton("Générer");   this.btnGenerer.setEnabled(false);
		
		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/

		/* INFO */
		this.add(this.panelInfo, BorderLayout.CENTER);
		this.panelInfo.add(spGrilleNotions, BorderLayout.CENTER);

		/* ACTION */
		this.add(this.panelAction, BorderLayout.SOUTH);

		this.panelAction.add(this.btnPrecedent);
		this.panelAction.add(this.btnGenerer);
		
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/

		/* INFO */

		/* ACTION */
		this.btnPrecedent.addActionListener(this);
		this.btnGenerer  .addActionListener(this);

	}

	/**
	 * Gère les actions des boutons
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnPrecedent)
		{
			this.frame.pagePrecedente();
		}

		if(e.getSource() == this.btnGenerer)
		{
			this.frame.creerQuestionnaire();
		}
	}

	/**
	 * Méthode qui gère les actions des boutons.
	 * 
	 * @param e l'événement qui a déclenché l'action.
	 */
	public void majTabNbQuestions(String codeRes) 
	{
		// Création d'une nouvelle Instance de tblGrilleNotions
		this.grilleNotionsModel = new GrilleNotionsModel(this.ctrl, this, codeRes);
		this.tblGrilleNotions   = new JTable(this.grilleNotionsModel);
		
		// Paramétrage
		this.tblGrilleNotions.getTableHeader().setReorderingAllowed(false); 
		for (int i = 0; i < this.tblGrilleNotions.getColumnModel().getColumnCount(); i++) 
		{
			this.tblGrilleNotions.getColumnModel()
								 .getColumn(i)
								 .setResizable(false);

			this.tblGrilleNotions.getColumnModel()
								 .getColumn(i)
								 .setCellRenderer(new GrilleNotionsRenderer());
			
			if(i > 1)
			this.tblGrilleNotions.getColumnModel()
								 .getColumn(i)
								 .setCellEditor(new GrilleNotionsEditor());
		}

		// Suppression de l'effet de sélection
		this.tblGrilleNotions.setSelectionBackground(this.tblGrilleNotions.getBackground());
		this.tblGrilleNotions.setSelectionForeground(this.tblGrilleNotions.getForeground());
		this.tblGrilleNotions.setShowGrid(true);

		// Gestion de la taille des cellules
		// Première colonne
		this.tblGrilleNotions.getColumnModel()
							 .getColumn(0)
							 .setPreferredWidth(200);

		// Autres colonnes
		for (int i = 1; i < this.tblGrilleNotions.getColumnModel().getColumnCount()-1; i++) 
		{
			this.tblGrilleNotions.getColumnModel()
								 .getColumn(i)
								 .setPreferredWidth(40);
		}

		// Dernière colonne
		this.tblGrilleNotions.getColumnModel()
							 .getColumn(this.tblGrilleNotions.getColumnModel().getColumnCount()-1)
							 .setPreferredWidth(60);
		
		// Barre de déroulement
		this.spGrilleNotions  = new JScrollPane(this.tblGrilleNotions);

		// Remplace le contenu de panelInfo avec le nouveau panel
		this.panelInfo.removeAll();
		this.panelInfo.add(this.spGrilleNotions);

		this.panelInfo.revalidate();
		this.panelInfo.repaint();
	}

	/**
	 * Active ou désactive le bouton "Générer" en fonction de l'état passé en paramètre.
	 * 
	 * @param b Si true, le bouton est activé; si false, il est désactivé.
	 */
	public void setBtnGenererEnabled(boolean b) 
	{
		this.btnGenerer.setEnabled(b);
	}

	/**
	 * Récupère un tableau 2D contenant le nombre de questions à générer en fonction de la notion (la ligne)
	 * et de la difficutlé (la colonne).
	 * 
	 * @return Un tableau 2D d'entiers représentant le nombre de questions à générer.
	 */
	public int[][] getTabNbQuestions()
	{
		return this.grilleNotionsModel.getTabNbQuestions();
	}
	
}
