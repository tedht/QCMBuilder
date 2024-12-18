package metier.banque;

import metier.entite.Ressource;

import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;

import metier.QCMBuilder;
import metier.entite.Notion;

/** Classe BanqueDeNotions
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeNotions {


	private List<Notion> lstNotions;
	
	public BanqueDeNotions()
	{

		this.lstNotions = new ArrayList<Notion>();
	}

	/**
	 * Retourne la liste des notions de la banque de notions.
	 * 
	 * @return la liste des notions.
	 */
	public List<Notion> getNotions()
	{
		return this.lstNotions;
	}


	/**
	 * Retourne la notion de la banque de notions.
	 * 
	 * @param id l'id de la notion
	 * @return la notion grâce à l'id.
	 */
	public Notion getNotion(int id)
	{
		for(Notion notion : this.lstNotions)
		{
			if(id == notion.getId())
			{
				return notion; 
			}
		}
		return null;
	}
	
	/**
	 * Ajoute une notion à la banque de notions.
	 * 
	 * @param notion la notion à ajouter.
	 * @return true si la notion a été ajoutée, false sinon.
	 */
	public boolean ajouterNotion(Notion notion)
	{
		if (notion == null) return false;
		this.lstNotions.add(notion);
		return true;
	}

	/**
	 * Supprime une notion de la banque de notions.
	 * 
	 * @param notion la notion à supprimer.
	 * @return true si la notion a été supprimée, false sinon.
	 */
	public boolean supprimerNotion(int id)
	{
		for(Notion notion : this.lstNotions)
		{
			if(id == notion.getId())
			{
				this.lstNotions.remove(notion);
				return true;
			}
		}

		return false;
	}

	/**
	 * Modifie une notion de la banque de notions.
	 * 
	 * @param notion la notion à modifier.
	 * @return true si la notion a été modifiée, false sinon.
	 */
	public boolean modifierNotion(Notion notion)
	{
		for(Notion n : this.lstNotions)
		{
			if(n.getId() == notion.getId())
			{
				n.setNom(notion.getNom());
				n.setCodeRessource(notion.getCodeRessource());
				return true;
			}
		}
		return false;
	}

	public void sauvegarderNotions()
	{
		PrintWriter pw;
		
		try
		{
			pw = new PrintWriter("data/notions.csv");
			pw.println("ID"+ "\t" + "Nom" + "\t" + "CodeRessource"); ;
			for(Notion notion : this.lstNotions)
			{
				pw.print  (notion.getId    () + "\t"); 
				pw.print  (notion.getNom   () + "\t");
				pw.println(notion.getCodeRessource());
			}
			pw.close();
		}
		catch(Exception e)
		{
			System.out.println("Erreur lors de la sauvegarde des notions");
		}
	}

	public void lireNotions()
	{
		Scanner scLigne,scDonnees;
		String nom;
		int id;
		String codeRessource;
		Notion notion;

		try
		{
			scLigne = new Scanner("data/notions.csv");
			scLigne.nextLine();
			while(scLigne.hasNextLine())
			{
				scDonnees = new Scanner(scLigne.nextLine());
				scDonnees.useDelimiter("\t");

				id            = scDonnees.nextInt();
				nom           = scDonnees.next   ();
				codeRessource = scDonnees.next   ();

				notion        = new Notion(nom, id, codeRessource);

				ajouterNotion(notion);
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur lors de la lecture des notions");
		}
	}

	public static void main(String[] args) {
		
		Notion n1 = new Notion("n1", 1, "code1");
		Notion n2 = new Notion("n2", 2, "code2");
		Notion n3 = new Notion("n3", 3, "code3");
		
		BanqueDeNotions bdn = new BanqueDeNotions();

		bdn.ajouterNotion(n1);
		bdn.ajouterNotion(n2);
		bdn.ajouterNotion(n3);

		for (Notion notion : bdn.getNotions())
		{
			System.out.println(notion);

		}


		bdn.sauvegarderNotions();

		bdn.supprimerNotion(1);
		bdn.supprimerNotion(2);
		bdn.supprimerNotion(3);
		for (Notion notion : bdn.getNotions())
		{
			System.out.println(notion);

		}

		bdn.lireNotions();

		for (Notion notion : bdn.getNotions()) {
			System.out.println(notion);
			
		}
	}
}
