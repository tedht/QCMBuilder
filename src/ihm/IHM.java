package ihm;

import java.awt.Dimension;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import com.kitfox.svg.app.beans.SVGIcon;

import controleur.Controleur;

import ihm.ressource.FrameGestionRessource;
import ihm.ressource.edit.FrameEditRessource;

import ihm.notion.FrameGestionNotion;
import ihm.notion.edit.FrameEditNotion;

import ihm.question.FrameGestionQuestion;
import ihm.question.edit.FrameEditQuestion;

import ihm.questionnaire.FrameCreationQuestionnaire;
import ihm.questionnaire.export.FrameExport;


/**
 * Classe qui gère l'affichage des fenêtres différentes.
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-06 Norme ISO-8601
 */
public class IHM 
{
	/* Constantes */
	public static final int MARGE = 5;

	// Dimensions des fenêtres de gestion
	public static final int LARGEUR_GESTION = 600;
	public static final int HAUTEUR_GESTION = 680;

	// Dimensions de la fenêtre de création de questionnaire
	public static final int LARGEUR_CREER_QUESTIONNAIRE        = 500;
	public static final int HAUTEUR_CREER_QUESTIONNAIRE_PAGE_1 = 150;
	public static final int HAUTEUR_CREER_QUESTIONNAIRE_PAGE_2 = 300;

	// Dimensions de la fenêtre d'édition de question
	public static final int LARGEUR_EDIT_QUESTION        = 800;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_1 = 250;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_2 = 400;
	
	/* Attributs */
	private Controleur                ctrl;

	// Fenêtre principale
	private FrameQCMBuilder           frameQCMBuilder;

	// Ressource/Notion
	private FrameGestionRessource     frameGestionRessource;
	private FrameGestionNotion        frameGestionNotion;
	private FrameEditRessource        frameEditRessource;
	private FrameEditNotion           frameEditNotion;

	// Question
	private FrameGestionQuestion      frameGestionQuestion;
	private FrameEditQuestion         frameEditQuestion;

	// Questionnaire
	private FrameCreationQuestionnaire frameCreationQuestionnaire;
	private FrameExport                frameExport; 
	
	// Coordonnées par défaut des fénêtres à part la fenêtre principale ()
	private int defaultX, defaultY;
	
	/**
     * Constructeur de la classe IHM.
     * Initialise l'interface graphique, crée les fenêtres et gère le 
	 * positionnement de la fenêtre principale.
     * 
     * @param ctrl le contrôleur.
     */
	public IHM(Controleur ctrl)
	{
		this.ctrl = ctrl;

		// Crée les différentes fenêtres
		this.frameQCMBuilder            = new FrameQCMBuilder          (this);
		this.frameGestionRessource      = new FrameGestionRessource    (this.ctrl, this);
		this.frameGestionNotion         = new FrameGestionNotion       (this.ctrl, this);
		this.frameGestionQuestion       = new FrameGestionQuestion     (this.ctrl, this);
		this.frameCreationQuestionnaire = new FrameCreationQuestionnaire(this.ctrl, this);

		 // Positionne et affiche la fenêtre principale
		this.frameQCMBuilder.setLocation(IHM.MARGE, IHM.MARGE);
		this.frameQCMBuilder.setVisible (true);

		// Définit les coordonnées par défaut pour les autres fenêtres
		this.defaultX = this.frameQCMBuilder.getX() + this.frameQCMBuilder.getWidth () + IHM.MARGE;
		this.defaultY = this.frameQCMBuilder.getY();

		// Ajoute les comportements pour fermer correctement la fenêtres de gestion des ressources 
		this.frameGestionNotion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) 
			{
				// On ferme aussi la fenêtre de gestion des notions
                IHM.this.frameGestionRessource.setVisible(false);
            }
        });

		// Ajoute les comportements pour fermer correctement la fenêtres de gestion des notions
		this.frameGestionRessource.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) 
			{
				// On ferme aussi la fenêtre de gestion des ressources
                IHM.this.frameGestionNotion.setVisible(false);
            }
        });
	}

	/**
	 * Réinitialise la visibilité des fenêtres de gestion
	 */
	private void resetVisible()
	{
		if(this.frameGestionRessource     != null) this.frameGestionRessource    .setVisible(false);
		if(this.frameGestionNotion        != null) this.frameGestionNotion       .setVisible(false);
		if(this.frameGestionQuestion      != null) this.frameGestionQuestion     .setVisible(false);
		if(this.frameCreationQuestionnaire != null) this.frameCreationQuestionnaire.setVisible(false);
	}

	/**
	 * Affiche une fenêtre donnée à une position spécifique
	 * 
	 * @param frame la fenêtre qu'on souhaite afficher.
	 * @param posX  la position horizontale (en pixels) où la fenêtre frame sera affichée.
	 * @param posY  la position verticale (en pixels) où la fenêtre frame sera affichée.
	 */
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
			// Met la fenêtre au premier plan si elle est déjà visible
			frame.toFront();
		}

		// Assure que la fenêtre principale reste au premier plan
		this.frameQCMBuilder.toFront();
	}

	/**
	 * Affiche les fenêtres de gestion des ressources et des notions côte à côte
	 */
	public void afficherRessourceNotion() 
	{ 
		this.resetVisible();
		this.afficher(this.frameGestionRessource, this.defaultX, this.defaultY); 
		this.afficher(this.frameGestionNotion, 
		              this.defaultX + IHM.LARGEUR_GESTION + IHM.MARGE, 
					  this.defaultY); 
	}

	/**
	 * Affiche la fenêtre de gestion des questions
	 */
	public void afficherQuestion() 
	{ 
		this.resetVisible();
		this.frameGestionQuestion.reinitAffichage();
		this.afficher(this.frameGestionQuestion, this.defaultX, this.defaultY);        
	}
	
	/**
	 * Afficher la fenêtre de création de questionnaires
	 */
	public void afficherQuestionnaire () 
	{ 
		this.resetVisible();
		this.frameCreationQuestionnaire.reinitAffichage();
		this.frameCreationQuestionnaire.pagePrecedente();
		this.afficher(this.frameCreationQuestionnaire, this.defaultX, this.defaultY);   
	}

	/**
	 * Quitter l'application
	 */
	public void quitter() { System.exit(0); }

	/**
	 * Réinitialiser l'affichage de la fenêtre de gestion des ressources
	 */
	public void reinitAffichageRessource() 
	{ 
		this.frameGestionRessource.reinitAffichage(); 
	}

	/**
	 * Réinitialiser l'affichage de la fenêtre de gestion des notions
	 */
	public void reinitAffichageNotion() 
	{ 
		this.frameGestionNotion.reinitAffichage(); 
	}

	/**
	 * Réinitialise l'affichage de la fenêtre de gestion des questions
	 */
	public void reinitAffichageQuestion() 
	{ 
		this.frameGestionQuestion.reinitAffichage(); 
	}

	/**
	 * Réinitialise l'affichage de la fenêtre de création de questionnaires
	 */
	public void reinitAffichageQuestionnaire() 
	{ 
		this.frameCreationQuestionnaire.reinitAffichage();
	}

	/**
	 * Affiche la fenêtre d'édition de ressources. Cette méthode est appelée lorqu'on cherche 
	 * uniquement à créer une nouvelle ressource.
	 */
	public void editRessource()
	{
		this.editRessource(null);
	}

	/**
	 * Affiche la fenêtre d'édition de ressources. Cette méthode est appelée lorqu'on cherche 
	 * à créer ou modifier une ressource.
	 * 
	 * @param code le code de la ressource. Si code est null, on crée une nouvelle ressource, sinon
	 *             on modifie une ressource existente.
	 */
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

	/**
	 * Affiche la fenêtre d'édition de notions. Cette méthode est appelée lorqu'on cherche 
	 * uniquement à créer une nouvelle notion.
	 */
	public void editNotion()
	{
		this.editNotion(null);
	}

	/**
	 * Affiche la fenêtre d'édition de notions. Cette méthode est appelée lorqu'on cherche 
	 * à créer ou modifier une notion.
	 * 
	 * @param idNot l'id de la notion. Si idNot est null, on crée une nouvelle notion, sinon
	 *              on modifie une notion existente.
	 */
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

	/**
	 * Affiche la fenêtre d'édition de questions. Cette méthode est appelée lorqu'on cherche 
	 * uniquement à créer une nouvelle question.
	 */
	public void editQuestion()
	{
		this.editQuestion(null);
	}

	/**
	 * Affiche la fenêtre d'édition de questions. Cette méthode est appelée lorqu'on cherche 
	 * à créer ou modifier une question.
	 * 
	 * @param idQst l'id de la question. Si idQst est null, on crée une nouvelle question, sinon
	 *              on modifie une question existente.
	 */
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

	/**
	 * Affiche la fenêtre pour exporter la questionnaire générée.
	 */
	public void exporterQuestionnaire() 
	{
		if(this.frameExport == null)
		{
			// Crée une nouvelle frame
			this.frameExport = new FrameExport(this.ctrl, this);

			// Positionne frameExport au centre de la fenêtre principale
			this.frameExport.setLocation(
				this.frameCreationQuestionnaire.getX() + this.frameCreationQuestionnaire.getWidth () / 2 - this.frameExport.getWidth () / 2,
				this.frameCreationQuestionnaire.getY() + this.frameCreationQuestionnaire.getHeight() / 2 - this.frameExport.getHeight() / 2
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

	/**
	 * Ferme la fenêtre de création de fenêtre.
	 */
	public void fermerCreationQuestionnaire()
	{
		if(this.frameCreationQuestionnaire != null)
		{
			this.frameCreationQuestionnaire.dispose();;
		}
	}

	/**
	 * Méthode statique pour récupérer une image au format svg, utilisé pour 
	 * afficher les icônes des boutons.
	 * 
	 * @param chemin  Le chemin de l'image svg.
	 * @param largeur La largeur souhaitée.
	 * @param hauteur La hauteur souhaitée.
	 * 
	 * @return L'image sous forme de SVGIcon.
	 */
	@SuppressWarnings("deprecation")
	public static SVGIcon getImgIconSVG(String chemin, int largeur, int hauteur)
	{
		SVGIcon svgIcon = new SVGIcon();
		try {
			svgIcon.setSvgURI(new URI("file:" + chemin));
			svgIcon.setPreferredSize(new Dimension(largeur, hauteur));

			svgIcon.setScaleToFit(true); // setScaleToFit est deprecated

			svgIcon.setAntiAlias(true);
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}

		return svgIcon;
	}
}
