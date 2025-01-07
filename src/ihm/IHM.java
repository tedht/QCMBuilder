package ihm;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/*
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.XMLAbstractTranscoder;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.batik.util.SVGConstants;*/

import com.kitfox.svg.app.beans.SVGIcon;

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

	public static final int LARGEUR_EDIT_QUESTION        = 800;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_1 = 250;
	public static final int HAUTEUR_EDIT_QUESTION_PAGE_2 = 400;
	
	private Controleur                ctrl;

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

		this.frameGestionNotion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) 
			{
                IHM.this.frameGestionRessource.setVisible(false);
            }
        });

		this.frameGestionRessource.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) 
			{
                IHM.this.frameGestionNotion.setVisible(false);
            }
        });
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

	public static SVGIcon getImgIconSVG(String chemin, int largeur, int hauteur)
	{
        SVGIcon svgIcon = new SVGIcon();
        try {
            svgIcon.setSvgURI(new URI("file:" + chemin));
            svgIcon.setPreferredSize(new Dimension(largeur, hauteur));
			//svgIcon.setScaleToFit(true);
            svgIcon.setAntiAlias(true);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return  svgIcon;
	}

	/*
	public static Icon getImgIconSVG(String chemin, int largeur, int hauteur)
	{
		SvgTranscoder t = new SvgTranscoder();
		t.setTranscodingHints(IHM.getHints((float)largeur, (float)hauteur));

		try 
		{
			TranscoderInput input = new TranscoderInput(new File(chemin).toURI().toString());
			t.transcode(input, null);
		} 
		catch (TranscoderException e) 
		{
			e.printStackTrace();
		}

		return new ImageIcon(t.getImage());
	}

	private static TranscodingHints getHints(float largeur, float hauteur) 
	{
		TranscodingHints hints = new TranscodingHints();
		hints.put(XMLAbstractTranscoder.KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
		hints.put(XMLAbstractTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
		hints.put(XMLAbstractTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);

		hints.put(SVGAbstractTranscoder.KEY_WIDTH, largeur);
		hints.put(SVGAbstractTranscoder.KEY_HEIGHT, hauteur);
		return hints;
	}

	private static class SvgTranscoder extends ImageTranscoder 
	{
		private BufferedImage image = null;
	
		@Override
		public BufferedImage createImage(int width, int height) 
		{
		  	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		  	return image;
		}
	
		@Override
		public void writeImage(BufferedImage img, TranscoderOutput out) {}
	
		BufferedImage getImage() 
		{
		  	return image;
		}
	}*/
}
