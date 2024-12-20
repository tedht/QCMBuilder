package ihm;

import java.awt.event.*;

import javax.swing.JFrame;

import controleur.Controleur;
import ihm.ressource.FrameGestionRessource;
import ihm.ressource.edit.FrameEditRessource;
import ihm.notion.FrameGestionNotion;
import ihm.notion.edit.FrameEditNotion;
import ihm.question.FrameGestionQuestion;
import ihm.question.edit.FrameEditQuestion;
import ihm.questionnaire.FrameGestionQuestionnaire  ;
import ihm.questionnaire.export.FrameExport;

public class IHM 
{
	public static final int MARGE = 5;

	public static final int LARGEUR_GESTION = 600;
	public static final int HAUTEUR_GESTION = 680;

	public static final int LARGEUR_CREER_QUESTIONNAIRE        = 500;
	public static final int HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1 = 150;
	public static final int HAUTEUR_CREER_QUESTIONNAIRE_PAGE_2 = 300;

	public static final int LARGEUR_EDIT_QUESTION        = 700;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_1 = 250;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_2 = 400;
	
	private Controleur ctrl;

	private FrameQCMBuilder           frameQCMBuilder;

	private FrameGestionRessource     frameGestionRessource;
	private FrameGestionNotion        frameGestionNotion;
	private FrameEditRessource        frameEditRessource;
	private FrameEditNotion           frameEditNotion;

	private FrameGestionQuestion      frameGestionQuestion;
	private FrameEditQuestion         frameEditQuestion;

	private FrameGestionQuestionnaire frameGestionQuestionnaire;
	private FrameExport               frameExport; 
	
	private int defaultX, defaultY;
	
	public IHM(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.frameQCMBuilder           = new FrameQCMBuilder          (this);
		this.frameGestionRessource     = new FrameGestionRessource    (this.ctrl, this);
		this.frameGestionNotion        = new FrameGestionNotion       (this.ctrl, this);
		this.frameGestionQuestion      = new FrameGestionQuestion     (this.ctrl, this);
		this.frameGestionQuestionnaire = new FrameGestionQuestionnaire(this.ctrl, this);

		this.frameQCMBuilder.setLocation(IHM.MARGE,IHM.MARGE);
		this.frameQCMBuilder.setVisible (true);

		this.defaultX = this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () + IHM.MARGE;
		this.defaultY = this.frameQCMBuilder.getY();
	}

	private void resetVisible()
	{
		this.frameGestionRessource    .setVisible(false);
		this.frameGestionNotion       .setVisible(false);
		this.frameGestionQuestion     .setVisible(false);
		this.frameGestionQuestionnaire.setVisible(false);
	}

	private void afficher(JFrame frame, int posX, int posY)
	{
		if(frame == null) return;
		
		if(!frame.isVisible())
		{
			frame.setLocation(posX, posY);
			frame.setVisible(true);
		}
		else
		{
			frame.toFront();
		}

		this.frameQCMBuilder.toFront();
	}

	public void afficherRessourceNotion() 
	{ 
		this.resetVisible();
		this.afficher(this.frameGestionRessource, this.defaultX, this.defaultY); 
		this.afficher(this.frameGestionNotion, 
		              this.defaultX + IHM.LARGEUR_GESTION + IHM.MARGE, 
					  this.defaultY); 
	}
	public void afficherQuestion() 
	{ 
		this.resetVisible();
		this.frameGestionQuestion.maj();
		this.afficher(this.frameGestionQuestion, this.defaultX, this.defaultY);        
	}
	public void afficherQuestionnaire () 
	{ 
		this.resetVisible();
		//this.frameGestionQuestionnaire.maj();
		this.afficher(this.frameGestionQuestionnaire, this.defaultX, this.defaultY);   
	}
	public void quitter() { System.exit(0); }

	public void reinitAffichageRessource() { this.frameGestionRessource.reinitAffichage(); }
	public void reinitAffichageNotion   () { this.frameGestionNotion   .reinitAffichage(); }
	public void reinitAffichageQuestion () { this.frameGestionQuestion .reinitAffichage(); }

	public void editRessource()
	{
		this.editRessource(null);
	}

	public void editRessource(String code) 
	{
		if(this.frameEditRessource == null)
		{
			// Crée une nouvelle fenêtre d'édition de ressource
			if(code == null)
				this.frameEditRessource = new FrameEditRessource(this.ctrl, this);
			else
				this.frameEditRessource = new FrameEditRessource(this.ctrl, this, code);

			// Positionne frameEditRessource au centre de la fenêtre principale
			this.frameEditRessource.setLocation(
				this.frameGestionRessource.getX() + this.frameGestionRessource.getWidth () / 2 - this.frameEditRessource.getWidth () / 2,
				this.frameGestionRessource.getY() + this.frameGestionRessource.getHeight() / 2 - this.frameEditRessource.getHeight() / 2
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

	public void editNotion()
	{
		this.editNotion(null);
	}

	public void editNotion(Integer idNot) 
	{
		if(this.frameEditNotion == null)
		{
			// Crée une nouvelle fenêtre d'édition de notion
			if(idNot == null)
				this.frameEditNotion = new FrameEditNotion(this.ctrl, this);
			else
				this.frameEditNotion = new FrameEditNotion(this.ctrl, this, idNot);

			// Positionne frameEditNotion au centre de la fenêtre principale
			this.frameEditNotion.setLocation(
				this.frameGestionNotion.getX() + this.frameGestionNotion.getWidth () / 2 - this.frameEditNotion.getWidth () / 2,
				this.frameGestionNotion.getY() + this.frameGestionNotion.getHeight() / 2 - this.frameEditNotion.getHeight() / 2
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

	public void editQuestion()
	{
		this.editQuestion(null);
	}

	public void editQuestion(Integer idQst) 
	{
		if(this.frameEditQuestion == null)
		{
			// Crée une nouvelle fenêtre d'édition de question
			if(idQst == null)
				this.frameEditQuestion = new FrameEditQuestion(this.ctrl, this);
			else
				this.frameEditQuestion = new FrameEditQuestion(this.ctrl, this, idQst);

			// Positionne frameEditQuestion au centre de la fenêtre principale
			this.frameEditQuestion.setLocation(
				this.frameGestionQuestion.getX() + this.frameGestionQuestion.getWidth () / 2 - this.frameEditQuestion.getWidth () / 2,
				this.frameGestionQuestion.getY() + this.frameGestionQuestion.getHeight() / 4 - this.frameEditQuestion.getHeight() / 2
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

	public void finaliserQuestionnaire() 
	{
		if(this.frameExport == null)
		{
			// Crée une nouvelle frame
			this.frameExport = new FrameExport(this.ctrl, this);

			// Positionne frameExport au centre de la fenêtre principale
			this.frameExport.setLocation(
				this.frameGestionQuestionnaire.getX() + this.frameGestionQuestionnaire.getWidth () / 2 - this.frameExport.getWidth () / 2,
				this.frameGestionQuestionnaire.getY() + this.frameGestionQuestionnaire.getHeight() / 2 - this.frameExport.getHeight() / 2
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

}
