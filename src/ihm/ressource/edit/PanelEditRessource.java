package ihm.ressource.edit;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.Controleur;
import ihm.IHM;
import ihm.shared.PanelEditNom;


/** Classe JPanel de la fenêtre d'édition d'une ressource.
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public abstract class PanelEditRessource extends PanelEditNom
{
	protected JTextField txtCode;

	private JPanel     panelInfoCode;
	
	/**
	 * Constructeur de la classe PaneleditRessource().
	 *
	 * @param ctrl Le contrôleur
	 */
	public PanelEditRessource(Controleur ctrl, IHM ihm)
	{
		super(ctrl, ihm);
		
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.lblNom.setText("Nom de la ressource :");

		this.panelInfoCode = new JPanel(new BorderLayout());
		this.txtCode       = new JTextField(30);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/

		this.panelInfo.add(this.panelInfoCode);
		this.panelInfo.add(this.panelInfoNom);

		this.panelInfoCode.add(new JLabel("Code : "), BorderLayout.NORTH);
		this.panelInfoCode.add(this.txtCode, BorderLayout.CENTER);
	}

	public String getType()
	{
		return "Ressource";
	}

	public abstract boolean enregistrer();
}
