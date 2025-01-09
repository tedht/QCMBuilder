package ihm.shared;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import ihm.IHM;

/**
 * Classe JPanel générique utilisée par les fenêtre de gestion.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class PanelGestion extends JPanel implements ActionListener
{
	protected Controleur ctrl;
	protected IHM        ihm;
	protected JLabel     lblTitre;
	protected JButton    btnAjouter;
	protected JPanel     panelEntete, panelContenu, panelAction;

	/**
	 * Constructeur de la classe PanelGestion.
	 * 
	 * @param ctrl Le controleur
	 * @param ihm  L'IHM
	 */
	public PanelGestion(Controleur ctrl, IHM ihm)
	{
		JScrollPane spContenu;
		
		this.ctrl = ctrl;
		this.ihm  = ihm;

		this.setLayout(new BorderLayout());


		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		this.panelContenu = new JPanel(new GridLayout(0,1,5,5));
		this.panelContenu.setBorder(new EmptyBorder(10,10,10,10));

		this.panelEntete = new JPanel();
		this.panelEntete.setLayout(new BorderLayout());
		this.panelEntete.setBorder(new EmptyBorder(10,10,10,10));
		this.panelAction  = new JPanel();

		spContenu = new JScrollPane(this.panelContenu);
        spContenu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		spContenu.getVerticalScrollBar().setUnitIncrement(16);
		spContenu.getVerticalScrollBar().setBlockIncrement(32);
		spContenu.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.lblTitre   = new JLabel("Eléments :");
		this.lblTitre.setFont(new Font("Arial", Font.BOLD, 24));

		this.btnAjouter = new JButton("Ajouter");

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.add(this.panelEntete, BorderLayout.NORTH);
		this.panelEntete.add(this.lblTitre, BorderLayout.NORTH);

		this.add(spContenu, BorderLayout.CENTER);

		this.add(this.panelAction, BorderLayout.SOUTH);
		this.panelAction.add(this.btnAjouter); 

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnAjouter.addActionListener(this);
	}


	/**
	 * Méthode abstraite à définir dans les classes filles. Pemet d'afficher le
	 * contenu du panel.
	 */
	public abstract void afficher();
	
}
