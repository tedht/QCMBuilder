package ihm.questionnaire;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import ihm.questionnaire.tableau.GrilleNotionsEditor;
import ihm.questionnaire.tableau.GrilleNotionsModel;
import ihm.questionnaire.tableau.GrilleNotionsRenderer;

/**
 * Classe représentant la fenêtre de création d'une évaluation
 * 
 * @author Ted Herambert
 * @date 2024/12/16
 * @version 1.0
 */
public class PanelAjoutQuestionnaire extends JPanel implements ActionListener
{
	private Controleur              ctrl;
	private FrameGestionQuestionnaire frame;

	private JPanel panelInfo, panelAction;
	private JTable tblGrilleNotions;
	private JScrollPane spGrilleNotions;
	private JButton btnPrecedent, btnGenerer;

	public PanelAjoutQuestionnaire(Controleur ctrl, FrameGestionQuestionnaire frame) 
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

		this.tblGrilleNotions = new JTable(new GrilleNotionsModel(ctrl, this, ""));
		this.spGrilleNotions  = new JScrollPane(this.tblGrilleNotions);

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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnPrecedent)
		{
			this.frame.pagePrecedente();
		}

		if(e.getSource() == this.btnGenerer)
		{
			this.frame.genererQuestionnaire();
		}
	}

	public void majTabNotions(String codeRes) 
	{
		// Création d'une nouvelle Instance de tblGrilleNotions
		this.tblGrilleNotions = new JTable(new GrilleNotionsModel(this.ctrl, this, codeRes));
		
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
		this.tblGrilleNotions.getColumnModel()
		                     .getColumn(0)
							 .setPreferredWidth(200);

		for (int i = 1; i < this.tblGrilleNotions.getColumnModel().getColumnCount()-1; i++) 
		{
    		this.tblGrilleNotions.getColumnModel()
			                     .getColumn(i)
								 .setPreferredWidth(40);
		}

		this.tblGrilleNotions.getColumnModel()
		                     .getColumn(this.tblGrilleNotions.getColumnModel().getColumnCount()-1)
							 .setPreferredWidth(60);
		
		this.spGrilleNotions  = new JScrollPane(this.tblGrilleNotions);

		this.panelInfo.removeAll();
		this.panelInfo.add(this.spGrilleNotions);

		this.panelInfo.revalidate();
		this.panelInfo.repaint();
	}

	public void setBtnGenererEnabled(boolean b) 
	{
		this.btnGenerer.setEnabled(b);
	}
	
}
