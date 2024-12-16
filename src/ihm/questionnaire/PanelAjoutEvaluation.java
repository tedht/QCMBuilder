package ihm.questionnaire;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controleur.Controleur;

public class PanelAjoutEvaluation extends JPanel implements ActionListener
{
	private Controleur           ctrl;
	private FrameCreerEvaluation frame;

	public PanelAjoutEvaluation(Controleur ctrl, FrameCreerEvaluation frame) 
	{
		this.ctrl  = ctrl;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{

	}
	
}
