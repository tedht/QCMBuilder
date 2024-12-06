package controleur;

import ihm.FrameBaseDeQuestions;

public class Controleur 
{
	//private BaseDeQuestions metier
	private	FrameBaseDeQuestions ihm;
	
	public Controleur()
	{
		//this.metier = new BaseDeQuestions(this);
		this.ihm = new FrameBaseDeQuestions(this);
	}

	public static void main(String[] args) 
	{
		new Controleur();
	}
}
