package ihm;

import java.awt.event.*;

import controleur.Controleur;
import ihm.edition.notion.FrameEditNotion;
import ihm.edition.question.FrameEditQuestion;
import ihm.edition.ressource.FrameEditRessource;
import ihm.questionnaire.creation.FrameCreerQuestionnaire;
import ihm.questionnaire.generation.FrameExport;

public class IHM 
{
	public static final int LARGEUR_QCM_BUILDER = 800;
	public static final int HAUTEUR_QCM_BUILDER = 800;

	public static final int LARGEUR_EDIT_QUESTION        = 700;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_1 = 250;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_2 = 400;

	public static final int LARGEUR_CREER_QUESTIONNAIRE        = 500;
	public static final int HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1 = 150;
	public static final int HAUTEUR_CREER_QUESTIONNAIRE_PAGE_2 = 300;
	
	private Controleur ctrl;

	private FrameQCMBuilder      frameQCMBuilder;

	private FrameEditRessource   frameEditRessource;
	private FrameEditNotion      frameEditNotion;
	private FrameEditQuestion    frameEditQuestion;

	private FrameCreerQuestionnaire frameCreerQuestionnaire;
	private FrameExport             frameExport;
	
	public IHM(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.frameQCMBuilder = new FrameQCMBuilder(ctrl, this);
	}

	public void reinitAffichage() 
	{
		if(this.frameQCMBuilder != null)
			this.frameQCMBuilder.reinitAffichage();
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
                public void windowClosed(WindowEvent e) 
				{
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

	public void nouvelleQuestionnaire() 
	{
		if(this.frameCreerQuestionnaire == null)
		{
			// Crée une nouvelle fenêtre de création de'évaluation
			this.frameCreerQuestionnaire = new FrameCreerQuestionnaire(this.ctrl, this);

			// Positionne frameCreerQuestionnaire au centre de la fenêtre principale
			this.frameCreerQuestionnaire.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameCreerQuestionnaire.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 4 - this.frameCreerQuestionnaire.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameCreerQuestionnaire.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameCreerQuestionnaire.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    IHM.this.frameCreerQuestionnaire = null;
                }
            });
		}
		else
		{
			this.frameCreerQuestionnaire.toFront();
		}
	}

	public void finaliserQuestionnaire() 
	{
		if(this.frameExport == null)
		{
			// Crée une nouvelle frame
			this.frameExport = new FrameExport(this.ctrl, this);

			// Positionne frameExport au centre de la fenêtre principale
			this.frameExport.setLocation(
				this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () / 2 - this.frameExport.getWidth () / 2,
				this.frameQCMBuilder.getY() + this.frameQCMBuilder.getHeight() / 2 - this.frameExport.getHeight() / 2
			);

			// Rend la fenêtre visible
			this.frameExport.setVisible(true);

			// Ajoute un WindowListener pour fermer la fenêtre correctement
			this.frameExport.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) 
				{
                    IHM.this.frameExport = null;
                }
            });
		}
		else
		{
			// Si frameExport existe déjà, elle est mise en avant
			this.frameExport.toFront();
		}
	}

	public static boolean estInt(String valeur)
	{
		try 
		{
			Integer.parseInt(valeur); 
			return true;
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
	}

	public static boolean estDouble(String valeur)
	{
		try 
		{
			Double.parseDouble(valeur); 
			return true;
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
	}
}
