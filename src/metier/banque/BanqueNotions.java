package metier.banque;

import java.util.List;
import java.util.ArrayList;

import java.util.Queue;
import java.util.LinkedList;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.FileNotFoundException;

import metier.entite.Notion;

/**
 * Classe BanqueDeNotions qui gère les notions (hérite de Banque).
 * 
 * @author  Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueNotions extends Banque
{



	/*-----------*/
	/* Attributs */
	/*-----------*/

	private List<Notion>   lstNotions;
	private Queue<Integer> fileIdUtilisable;

	private String         cheminFic;
	


	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe BanqueNotions.
	 */
	public BanqueNotions()
	{
		this.lstNotions       = new ArrayList <Notion >();
		this.fileIdUtilisable = new LinkedList<Integer>();

		this.cheminFic        = "data/notions.csv";

		this.lireRessources(this.cheminFic);
	}



	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne le chemin du fichier de la banque de notions.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getCheminFic()
	{
		return this.cheminFic;
	}

	/**
	 * Retourne la liste des notions de la banque de notions.
	 * 
	 * @return la liste des notions.
	 */
	public List<Notion> getNotions()
	{
		List<Notion> lstNotions;


		lstNotions = new ArrayList<Notion>();

		for(Notion notion : this.lstNotions)
		{
			if(notion != null)
				lstNotions.add(notion);
		}

		return lstNotions;
	}

	/** 
	 * Retourne la liste des notions de la banque de notions en fonction du code de la ressource.
	 * 
	 * @param codeRes le code de la ressource.
	 * @return        la liste des notions.
	 */
	public List<Notion> getNotions(String codeRes) 
	{
		List<Notion> lstNotions;


		lstNotions = new ArrayList<Notion>();

		for(Notion notion : this.lstNotions)
		{
			if(notion != null && notion.getCodeRes().equals(codeRes))
				lstNotions.add(notion);
		}

		return lstNotions;
	}

	/**
	 * Retourne la notion de la banque de notions à partir de son id.
	 * 
	 * @param id l'id de la notion.
	 * @return   la notion.
	 */
	public Notion getNotion(int idNot)
	{
		return this.lstNotions.get(idNot);
	}

	/**
	 * Retourne la notion de la banque de notions à partir de son code de ressource et de son nom.
	 * 
	 * @param codeRes le code de la ressource.
	 * @param nomNot  le nom de la notion.
	 * @return        la notion.
	 */
	public Notion getNotionParNom(String codeRes, String nomNot) 
	{
		for(Notion notion : this.lstNotions)
		{
			if(notion != null && notion.getCodeRes().equals(codeRes) && notion.getNom().equals(nomNot))
				return notion;
		}

		return null;
	}



	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/

	/** 
	 * Lit les notions d'un fichier CSV.
	 * 
	 * @param cheminFic le nom du fichier à lire.
	 * @see   Scanner   pour lire le fichier.
	*/
	public void lireRessources(String cheminFic)
	{
		Scanner scEnreg, scDonnee;

		String  enreg;
		Notion  notion;

		String  codeRes;
		int     idNot;
		String  nom;

		int     cpt;


		try
		{
			// Scanner appliqué sur le fichier CSV
			scEnreg = new Scanner( new FileInputStream(cheminFic), "UTF8");

			// Pour passer l'entête
			if(scEnreg.hasNextLine())
				scEnreg.nextLine();

			cpt = 0;
			while (scEnreg.hasNextLine())
			{
				enreg = scEnreg.nextLine();

				// Scanner appliqué sur une ligne du fichier CSV utilisant le délimiteur "\t"
				scDonnee = new Scanner(enreg);
				scDonnee.useDelimiter("\t");

				// Récupération des données
				codeRes = scDonnee.next   ();
				idNot   = scDonnee.nextInt();
				nom     = scDonnee.next   ();

				// Création de la notion
				notion = new Notion(codeRes, idNot, nom);

				// Ajout de la notion à la liste des notions
				while(cpt < idNot)
				{
					this.lstNotions.add(null);
					this.fileIdUtilisable.offer(cpt++);
				}
				this.lstNotions.add(notion);

				cpt++;
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + cheminFic + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/** 
	 * Sauvegarde les notions dans un fichier CSV.
	*/
	public void sauvegarder()
	{
		this.sauvegarder(this.cheminFic);
	}

	/** 
	 * Sauvegarde les notions dans un fichier CSV.
	 * 
	 * @param cheminFic   le nom du fichier dans lequel sauvegarder les notions.
	 * @see   PrintWriter pour écrire dans le fichier.
	*/
	private void sauvegarder(String cheminFic)
	{
		File        fichier;
		PrintWriter pw;

		try
		{
			// PrintWriter appliqué sur le fichier CSV
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminFic), "UTF8" ));

			// Entête
			pw.println("codeRes\tidNot\tnom");

			// Données
			for (Notion notion : this.lstNotions)
			{
				if(notion != null)
				{
					pw.print(notion.getCodeRes() + "\t");
					pw.print(notion.getIdNot  () + "\t");
					pw.print(notion.getNom    () + "\n");
				}
			}

			pw.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + cheminFic + "n'a pas été trouvé : " + fnfe.getMessage());

			// Création du fichier si non existant
			System.out.println("Création du fichier " + cheminFic);
			fichier = new File(cheminFic);
			fichier.mkdir();

			// Resauvegarde (Récursif)
			this.sauvegarder(cheminFic);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Créer une notion dans la banque de notions
	 * 
	 * @param codeRes le code de la ressource de la notion.
	 * @param nom     le nom de la notion.
	 */
	public void creerNotion(String codeRes, String nom)
	{
		int idNot;


		idNot = this.recupererId();

		if (idNot < this.lstNotions.size())
			this.lstNotions.set(idNot, new Notion(codeRes, idNot, nom));
		else
			this.lstNotions.add(new Notion(codeRes, idNot, nom));
	}


	/**
	 * Supprime une notion de la banque de notions.
	 * 
	 * @param idNot l'id de la notion à supprimer.
	 */
	public void supprimerNotion(int idNot)
	{
		Notion notion;


		notion = this.getNotion(idNot);

		if(notion != null)
		{
			this.lstNotions.set(idNot, null);
			this.fileIdUtilisable.offer(idNot);
	
			// Supprime le dossier associer à la notion
			BanqueNotions.supprimerDossier(new File("data/ressources/" + notion.getCodeRes() + "/notion" + notion.getIdNot()));

			this.sauvegarder();
		}
	}

	/**
	 * Supprime une (ou des) notion de la banque de notions si la ressource donnée en paramètre contient une ou plusieurs notions.
	 * 
	 * @param codeRes le code de la ressource de la notion à supprimer.
	 */
	public void supprimerNotions(String codeRes)
	{
		for(int i = 0; i < this.lstNotions.size(); i++)
			if(this.lstNotions.get(i) != null && this.lstNotions.get(i).getCodeRes().equals(codeRes))
				this.supprimerNotion(i);
	}

	/**
	 * Recupère l'id de la dernière notion de la liste de notions.
	 * 
	 * @return la taille de la lstNotions (qui donne donc l'id de la dernière notion).
	 */
	private int recupererId()
	{
		if(!this.fileIdUtilisable.isEmpty())
			return this.fileIdUtilisable.poll();

		return this.lstNotions.size();
	}
}
