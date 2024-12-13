package ihm;

import java.awt.event.*;

import controleur.Controleur;
import ihm.edition.notion.FrameEditionNotion;
import ihm.edition.question.FrameEditionQuestion;
import ihm.edition.ressource.FrameEditionRessource;

public class IHM 
{
	public static final int LARGEUR_QCM_BUILDER = 800;
	public static final int HAUTEUR_QCM_BUILDER = 800;

	public static final int LARGEUR_EDIT_QUESTION        = 700;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_1 = 250;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_2 = 500;
	
	private Controleur ctrl;

	private FrameQCMBuilder       frameQCMBuilder;

	private FrameEditionRessource frameEditionRessource;
	private FrameEditionNotion    frameEditionNotion;
	private FrameEditionQuestion  frameEditionQuestion;
	
	public IHM(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.frameQCMBuilder = new FrameQCMBuilder(ctrl, this);
	}

	public void creerRessource() 
	{
		if(this.frameEditionRessource == null)
		{
			// Crée une nouvelle fenêtre d'édition de ressource
			this.frameEditionRessource = new FrameEditionRessource(this.ctrl, this);

			// Positionne frameEditionRessource au centre de la fenêtre principale
			this.frameEditionRessource.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameEditionRessource.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 2 - this.frameEditionRessource.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameEditionRessource.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditionRessource.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) 
				{
                    IHM.this.frameEditionRessource = null;
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
			this.frameEditionNotion = new FrameEditionNotion(this.ctrl, this);

			// Positionne frameEditionNotion au centre de la fenêtre principale
			this.frameEditionNotion.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameEditionNotion.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 2 - this.frameEditionNotion.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameEditionNotion.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditionNotion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    IHM.this.frameEditionNotion = null;
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
			this.frameEditionQuestion = new FrameEditionQuestion(this.ctrl, this);

			// Positionne frameEditionQuestion au centre de la fenêtre principale
			this.frameEditionQuestion.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameEditionQuestion.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 4 - this.frameEditionQuestion.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameEditionQuestion.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameEditionQuestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    IHM.this.frameEditionQuestion = null;
                }
            });
		}
		else
		{
			// Si la frameEditionQuestion existe déjà, elle est mise en avant
			this.frameEditionQuestion.toFront();
		}
	}

	public void reinitAffichage() 
	{
		this.frameQCMBuilder.reinitAffichage();
	}
}
