package ihm;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import controleur.Controleur;
import ihm.edition.notion.FrameEditionNotion;
import ihm.edition.question.FrameEditionQuestion;
import ihm.edition.ressource.FrameEditionRessource;
import metier.Notion;
import metier.Ressource;

/**
 * Classe représentant la fenêtre de la Banque de lstQuestions de l'application.
 * 
 * @author Ted Herambert
 * @date 2024/12/06
 * @version 1.0
 */
public class FrameBanqueDeQuestions extends JFrame
{
	private Controleur ctrl;

	private FrameEditionRessource frameEditionRessource;
	private FrameEditionNotion    frameEditionNotion;
	private FrameEditionQuestion  frameEditionQuestion;
	
	private PanelBanqueDeQuestions panelBanqueDeQuestions;

	/**
     * Constructeur de la classe FrameBanqueDeQuestions.
     * 
     * @param ctrl Le contrôleur
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
        int x = (screenSize.width  - this.getWidth ()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

		this.setVisible(true);
	}

	public void afficherRessources()
	{ 
		this.panelBanqueDeQuestions.afficherRessources();          
	}

	public void afficherNotions(Ressource ressource) 
	{ 
		this.panelBanqueDeQuestions.afficherNotions(ressource);
	}
	
	public void afficherQuestions(Ressource ressource, Notion notion)    
	{ 
		this.panelBanqueDeQuestions.afficherQuestions(ressource, notion);   
	}

	public void creerRessource() 
	{
		if(this.frameEditionRessource == null)
		{
			// Crée une nouvelle fenêtre d'édition de ressource
			this.frameEditionRessource = new FrameEditionRessource(this.ctrl);

			// Positionne frameEditionRessource au centre de la fenêtre principale
			this.frameEditionRessource.setLocation(
				this.getX() + this.getWidth () / 2 - this.frameEditionRessource.getWidth () / 2,
				this.getY() + this.getHeight() / 2 - this.frameEditionRessource.getHeight() / 2
			);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditionRessource.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) 
				{
                    FrameBanqueDeQuestions.this.frameEditionRessource = null;
                }
            });
		}
		else
		{
			// Si la frameEditionRessource existe déjà, elle est mise en avant
			this.frameEditionRessource.toFront();
		}
	}

	public void creerNotion() 
	{
		if(this.frameEditionNotion == null)
		{
			// Crée une nouvelle fenêtre d'édition de notion
			this.frameEditionNotion = new FrameEditionNotion(this.ctrl);

			// Positionne frameEditionNotion au centre de la fenêtre principale
			this.frameEditionNotion.setLocation(
				this.getX() + this.getWidth () / 2 - this.frameEditionNotion.getWidth () / 2,
				this.getY() + this.getHeight() / 2 - this.frameEditionNotion.getHeight() / 2
			);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditionNotion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    FrameBanqueDeQuestions.this.frameEditionNotion = null;
                }
            });
		}
		else
		{
			// Si la frameEditionNotion existe déjà, elle est mise en avant
			this.frameEditionNotion.toFront();
		}
	}

	public void creerQuestion() 
	{
		if(this.frameEditionQuestion == null)
		{
			// Crée une nouvelle fenêtre d'édition de question
			this.frameEditionQuestion = new FrameEditionQuestion(this.ctrl);

			// Positionne frameEditionQuestion au centre de la fenêtre principale
			this.frameEditionQuestion.setLocation(
				this.getX() + this.getWidth () / 2 - this.frameEditionQuestion.getWidth () / 2,
				this.getY() + this.getHeight() / 4 - this.frameEditionQuestion.getHeight() / 2
			);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditionQuestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    FrameBanqueDeQuestions.this.frameEditionQuestion = null;
                }
            });
		}
		else
		{
			// Si la frameEditionQuestion existe déjà, elle est mise en avant
			this.frameEditionQuestion.toFront();
		}
	}
}