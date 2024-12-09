package ihm;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controleur.Controleur;
import ihm.gestionRessourceNotion.FrameGestionNotion;
import ihm.gestionRessourceNotion.FrameGestionRessource;

/**
 * Classe représentant la fenêtre de la Banque de questions de l'application.
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class FrameBanqueDeQuestions extends JFrame
{
	private Controleur ctrl;
	
	private PanelBanqueDeQuestions panelBanqueDeQuestions;

	/**
     * Constructeur du frame.
     * 
     * @param ctrl Le contrôleur principal de l'application.
     */
	public FrameBanqueDeQuestions(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Banque de Questions");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Création et ajout du panneau de menu.
        this.panelBanqueDeQuestions = new PanelBanqueDeQuestions(this.ctrl);
        this.add(this.panelBanqueDeQuestions);

        // Définition des dimensions.
        this.setSize(new Dimension(800, 800));

		// Positionnement de la fenêtre au centre de l'écran.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

		this.setVisible(true);
	}

	public void afficherRessources()
	{ 
		this.panelBanqueDeQuestions.afficherRessources();          
	}

	public void afficherNotions(String ressource) 
	{ 
		this.panelBanqueDeQuestions.afficherNotions(ressource);
	}
	
	public void afficherQuestions (String ressource, String notion)    
	{ 
		this.panelBanqueDeQuestions.afficherQuestions(ressource, notion);   
	}

	public void majHistorique(String action) { this.panelBanqueDeQuestions.majHistorique(action); }

	public void creerRessource() 
	{
		new FrameGestionRessource(this.ctrl, this.getX(), this.getY(), this.getSize());
	}

	public void creerNotion() 
	{
		new FrameGestionNotion(this.ctrl, this.getX(), this.getY(), this.getSize());
	}

	public void creerQuestion() 
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'creerQuestion'");
	}
}