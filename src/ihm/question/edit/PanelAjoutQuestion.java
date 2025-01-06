package ihm.question.edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import ihm.IHM;
import ihm.question.edit.proposition.PanelProp;
import ihm.question.edit.proposition.PanelPropAssoc;
import ihm.question.edit.proposition.PanelPropElim;
import ihm.question.edit.proposition.PanelPropQCM;
import ihm.question.edit.proposition.PanelPropQRM;


/** Classe JPanel pour saisir les paramètres intitiales d'une question
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class PanelAjoutQuestion extends JPanel implements ActionListener, ItemListener
{
	protected FrameEditQuestion frame;
	
	protected JPanel            panelInfoScroll, panelInfoQuestion, panelInfoExpli,  panelInfoPJ, panelInfoAjout;
	protected JTextArea         txtIntitule, txtExpli;
	protected List<PanelProp>   lstPanelProp;
	protected JToggleButton     btnActiverExpli, btnActiverPJ;
	protected JButton           btnAjouterProp, btnAjouterPJ;
	protected JScrollPane	    scrollPanelInfo;
	protected ButtonGroup       btgReponse;
	protected JLabel            lblPJ;

	protected JButton           btnPrecedent, btnEnregistrer;

	public PanelAjoutQuestion(FrameEditQuestion frame) 
	{
		JPanel panelInfo, panelAction;
		JPanel panelAjout;
		JPanel panelBtnAjouterPJ;
		
		this.frame = frame;

		this.setLayout(new BorderLayout());

		this.lstPanelProp = new ArrayList<PanelProp>();

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		
		/* INFO */
		panelInfo = new JPanel(new BorderLayout());

		this.panelInfoQuestion = new JPanel(new BorderLayout());
		this.panelInfoQuestion.setBorder   (new EmptyBorder(0, 10, 0, 10));

		this.panelInfoExpli = new JPanel(new BorderLayout());
		this.panelInfoExpli.setBorder   (new EmptyBorder(10, 10, 0, 10));
		
		this.panelInfoPJ = new JPanel(new BorderLayout());
		this.panelInfoPJ.setBorder   (new EmptyBorder(0, 10, 0, 10));
		
		panelBtnAjouterPJ = new JPanel();

		this.panelInfoAjout = new JPanel(new BorderLayout());
		panelAjout          = new JPanel(new FlowLayout(FlowLayout.LEFT));

		this.txtIntitule = new JTextArea(); 
		this.txtIntitule.setRows         (3);
		this.txtIntitule.setLineWrap     (true);     
		this.txtIntitule.setWrapStyleWord(true);
		this.txtIntitule.setBorder       (new LineBorder(Color.GRAY));
		this.txtIntitule.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtIntitule.setCaretColor   (Color.BLACK);
		this.txtIntitule.setMargin       (new Insets(2, 5, 2, 5));
		
		this.txtExpli = new JTextArea(); 
		this.txtExpli.setRows         (3);
		this.txtExpli.setLineWrap     (true);     
		this.txtExpli.setWrapStyleWord(true);
		this.txtExpli.setBorder       (new LineBorder(Color.GRAY));
		this.txtExpli.setFont         (new Font("Arial", Font.PLAIN, 12));
		this.txtExpli.setCaretColor   (Color.BLACK);
		this.txtExpli.setMargin       (new Insets(2, 5, 2, 5));

		this.btnAjouterProp = new JButton(IHM.getImgIconSVG("res/Ajouter.svg", 16, 16));
		this.btnAjouterProp.setPreferredSize(new Dimension(24, 24));

		this.btnActiverExpli = new JToggleButton("Ajouter une Explication");

		this.btnActiverPJ = new JToggleButton(IHM.getImgIconSVG("res/AjouterPJ.svg", 16, 16));
		this.btnActiverPJ.setPreferredSize(new Dimension(24, 24));

		this.btnAjouterPJ = new JButton("Sélectionner une Pièce Jointe");

		this.panelInfoScroll = new JPanel(new GridBagLayout());
		this.scrollPanelInfo = new JScrollPane(this.panelInfoScroll);
        this.scrollPanelInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPanelInfo.getVerticalScrollBar().setUnitIncrement(16);
		this.scrollPanelInfo.getVerticalScrollBar().setBlockIncrement(32);
		this.scrollPanelInfo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPanelInfo.setBorder(new EmptyBorder(0,0,0,0));

		this.btgReponse = new ButtonGroup();

		this.lblPJ = new JLabel();
		
		/* ACTION */
		panelAction = new JPanel();
		panelAction.setBackground(new Color(200, 200, 250));
		this.btnPrecedent   = new JButton("Précédent");
		this.btnEnregistrer = new JButton("Enregistrer");   

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		
		/* INFO */
		this               .add(panelInfo, BorderLayout.CENTER);
		panelInfo          .add(this.scrollPanelInfo, BorderLayout.CENTER);

		panelInfoQuestion  .add(new JLabel("Question :"), BorderLayout.NORTH);
		panelInfoQuestion  .add(this.txtIntitule, BorderLayout.CENTER);

		panelInfoExpli     .add(new JLabel("Explication :"), BorderLayout.NORTH);
		panelInfoExpli     .add(this.txtExpli, BorderLayout.CENTER);

		panelInfoPJ        .add(new JLabel("Pièce Jointe :"), BorderLayout.NORTH);
		panelInfoPJ        .add(this.lblPJ, BorderLayout.CENTER);
		panelInfoPJ        .add(panelBtnAjouterPJ, BorderLayout.WEST); 
		panelBtnAjouterPJ  .add(this.btnAjouterPJ);

		this.panelInfoAjout.add(panelAjout);
		panelAjout         .add(this.btnAjouterProp);
		panelAjout         .add(this.btnActiverPJ);
		panelAjout         .add(this.btnActiverExpli);

		/* ACTION */
		this               .add(panelAction, BorderLayout.SOUTH);
		panelAction        .add(this.btnPrecedent);
		panelAction        .add(this.btnEnregistrer);

		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/

		/* INFO */
		this.btnAjouterProp .addActionListener(this);
		this.btnActiverExpli.addItemListener  (this);
		this.btnActiverPJ   .addItemListener  (this);
		this.btnAjouterPJ   .addActionListener(this);

		/* ACTION */
		this.btnEnregistrer.addActionListener(this);
		this.btnPrecedent  .addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.btnEnregistrer) { this.enregistrer         (); }
		if(e.getSource() == this.btnPrecedent)   { this.frame.pagePrecedente(); }
		if(e.getSource() == this.btnAjouterProp) { this.ajouterProposition  (); }
		if(e.getSource() == this.btnAjouterPJ)   { this.ajouterPJ  (); }
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource() == this.btnActiverExpli)       
		{ 
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				this.txtExpli.setText("");
			}
			this.afficher(); 
		}
		if(e.getSource() == this.btnActiverPJ) 
		{ 
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				this.lblPJ.setText("");
			}
			this.afficher(); 
		}
	}

	public void enregistrer()
	{
		this.frame.enregistrer();
	}

	public void ajouterProposition()
	{
		PanelProp panelProp = null;

		switch (this.frame.getIndexTypeQuestion()) {
			case 0  -> // Question à Choix Multiple à Réponse Unique
			{
				panelProp = new PanelPropQCM(this);
			} 
			case 1  -> // Question à Choix Multiple à Réponse Multiple
			{
				panelProp = new PanelPropQRM(this);
			} 
			case 2  -> // Question à Association d'Eléments
			{
				panelProp = new PanelPropAssoc(this);
			} 
			case 3  -> // Question avec Elimination de Propositions de Réponses
			{
				panelProp = new PanelPropElim(this);
			} 
			default -> {}
		}

		if(panelProp != null) 
		{
			this.lstPanelProp.add(panelProp);
			this.afficher();
		}
	}

	public void supprimerProposition(PanelProp panelProp)
	{
		this.lstPanelProp.remove(panelProp);

		this.afficher();
	}

	public void ajouterPJ()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choisissez une pièce Jointe");
        fileChooser.setFileFilter(new FileFilter() 
		{
            @Override
            public boolean accept(File file) 
			{
                // Allow directories to be navigable
                if (file.isDirectory()) 
				{
                    return true;
                }
                // Allow files with a ".pdf" extension
                return file.getName().toLowerCase().endsWith(".pdf");
            }

            @Override
            public String getDescription() 
			{
                return "fichiers PDF (*.pdf)";
            }
        });

		int result = fileChooser.showOpenDialog(frame);

		if (result == JFileChooser.APPROVE_OPTION) 
		{
			File PJ = fileChooser.getSelectedFile();
			this.lblPJ.setText(PJ.getAbsolutePath());
		}
	}

	public void afficher()
	{
		this.panelInfoScroll.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets  = new Insets(0, 5, 5, 25);
		gbc.gridx   = 0;
		gbc.anchor  = GridBagConstraints.NORTH;
	    gbc.fill    = GridBagConstraints.HORIZONTAL;
	    gbc.weightx = 1.0;
		gbc.weighty = 0.0;

		int cpt   = 0;
		gbc.gridy = cpt++;
		this.panelInfoScroll.add(this.panelInfoQuestion, gbc);

		if(this.btnActiverPJ.isSelected())
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(this.panelInfoPJ, gbc);
		}

		for(PanelProp panelProp : this.lstPanelProp)
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(panelProp, gbc);
		}

		if(this.btnActiverExpli.isSelected())
		{
			gbc.gridy = cpt++;
			this.panelInfoScroll.add(this.panelInfoExpli, gbc);
		}

		gbc.gridy = cpt;
		this.panelInfoScroll.add(this.panelInfoAjout, gbc);

		gbc.weighty = 1.0;
		this.panelInfoScroll.add(new JPanel(), gbc);

		this.revalidate();
		this.repaint();
	}

	public void ajouterRbResponse(JRadioButton rbReponse)
	{
		this.btgReponse.add(rbReponse);
	}
	public void supprimerRbResponse(JRadioButton rbReponse) 
	{
		this.btgReponse.remove(rbReponse);
	}

	public String getIntitule() 
	{
		return this.txtIntitule.getText();
	}

	public boolean explicationSelected()
	{
		return this.btnActiverExpli.isSelected();
	}

	public String getExplication() 
	{
		return this.txtExpli.getText();
	}

	public boolean PieceJointeSelected()
	{
		return this.btnActiverPJ.isSelected();
	}

	public String getPieceJointe()
	{
		return this.lblPJ.getText();
	}

	public List<PanelProp> getPanelProps() 
	{
		return this.lstPanelProp;
	}

	public void clearPanelProp() 
	{
		this.lstPanelProp.clear();
	}
}
