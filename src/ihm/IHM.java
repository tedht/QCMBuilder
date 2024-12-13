package ihm;

import java.awt.event.*;

import controleur.Controleur;
import ihm.edition.notion.FrameEditNotion;
import ihm.edition.question.FrameEditQuestion;
import ihm.edition.ressource.FrameEditRessource;

public class IHM 
{
	public static final int LARGEUR_QCM_BUILDER = 800;
	public static final int HAUTEUR_QCM_BUILDER = 800;

	public static final int LARGEUR_EDIT_QUESTION        = 700;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_1 = 250;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_2 = 400;
	
	private Controleur ctrl;

	private FrameQCMBuilder       frameQCMBuilder;

	private FrameEditRessource frameEditRessource;
	private FrameEditNotion    frameEditNotion;
	private FrameEditQuestion  frameEditQuestion;
	
	public IHM(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.frameQCMBuilder = new FrameQCMBuilder(ctrl, this);
	}

	public void creerRessource() 
	{
		if(this.frameEditRessource == null)
		{
			// Crée une nouvelle fenêtre d'édition de ressource
			this.frameEditRessource = new FrameEditRessource(this.ctrl, this);

			// Positionne frameEditRessource au centre de la fenêtre principale
			this.frameEditRessource.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameEditRessource.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 2 - this.frameEditRessource.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameEditRessource.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditRessource.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) 
				{
                    IHM.this.frameEditRessource = null;
                }
            });
		}
		else
		{
			// Si la frameEditRessource existe déjà, elle est mise en avant
			this.frameEditRessource.toFront();
		}
	}

	public void creerNotion() 
	{
		if(this.frameEditNotion == null)
		{
			// Crée une nouvelle fenêtre d'édition de notion
			this.frameEditNotion = new FrameEditNotion(this.ctrl, this);

			// Positionne frameEditNotion au centre de la fenêtre principale
			this.frameEditNotion.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameEditNotion.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 2 - this.frameEditNotion.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameEditNotion.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditNotion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    IHM.this.frameEditNotion = null;
                }
            });
		}
		else
		{
			// Si la frameEditNotion existe déjà, elle est mise en avant
			this.frameEditNotion.toFront();
		}
	}

	public void creerQuestion() 
	{
		if(this.frameEditQuestion == null)
		{
			// Crée une nouvelle fenêtre d'édition de question
			this.frameEditQuestion = new FrameEditQuestion(this.ctrl, this);

			// Positionne frameEditQuestion au centre de la fenêtre principale
			this.frameEditQuestion.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameEditQuestion.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 4 - this.frameEditQuestion.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameEditQuestion.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditQuestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    IHM.this.frameEditQuestion = null;
                }
            });
		}
		else
		{
			// Si la frameEditQuestion existe déjà, elle est mise en avant
			this.frameEditQuestion.toFront();
		}
	}

	public void reinitAffichage() 
	{
		this.frameQCMBuilder.reinitAffichage();
	}
}
