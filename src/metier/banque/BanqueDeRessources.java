package metier.banque;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import metier.entite.Ressource;
import metier.entite.Notion;

/** Classe BanqueDeRessources
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeRessources
{
	/*-----------*/
	/* Attributs */
	/*-----------*/
	private List<Ressource> lstRessources;

	/**
	 * Constructeur de la classe BanqueDeRessources.
	 */
	public BanqueDeRessources()
	{
		this.lstRessources = new ArrayList<Ressource>();

		this.lireRessources("data/ressources.csv");
	}

	/*---------*/
	/* Getters */
	/*---------*/

	/**
	 * Retourne la liste des ressources de la banque de ressources.
	 * 
	 * @return la liste des ressources.
	 */
	public List<Ressource> getRessources()
	{
		return this.lstRessources;
	}

	/**
	 * Retourne les notions d'une ressource.
	 * 
	 * @param ressource
	 * @return la liste des notions de la ressource, ou null si la ressource n'existe pas.
	 */
	public List<Notion> getNotions(Ressource ressource) 
	{
		for(Ressource rsrc : this.lstRessources)
		{
			if(rsrc == ressource)
			{
				return ressource.getNotions();
			}
		}
		return null;
	}

	/**
	 * Retourne les noms des notions d'une ressource.
	 * 
	 * @param ressource
	 * @return la liste des noms des notions de la ressource, ou null si la ressource n'existe pas.
	 */
	public List<String> getNomNotions(Ressource ressource) 
	{
		for(Ressource rsrc : this.lstRessources)
		{
			if(rsrc == ressource)
			{
				return ressource.getNomNotions();
			}
		}
		return null;
	}

	/**
	 * Retourne une ressource de la banque de ressources.
	 * 
	 * @param nomRessource le nom de la ressource à retourner.
	 * @return la ressource correspondante, ou null si elle n'existe pas.
	 */
	public Ressource getRessource(String nomRessource)
	{
		for (Ressource ressource : this.lstRessources)
		{
			if (ressource.getNom().equals(nomRessource))
				return ressource;
		}
		return null;
	}

	/*-----------------*/
	/* Autres méthodes */
	/*-----------------*/


	/** 
	 * Lit les ressources d'un fichier CSV.
	 * 
	 * @param nomFichier le nom du fichier à lire.
	 * @see Scanner
	*/
	public void lireRessources(String nomFichier)
	{
		Scanner   scEnreg, scDonnee;

		String    enreg;
		Ressource ressource;

		String codeRessource;
		String nomRessource;

		try
		{
			scEnreg = new Scanner( new FileInputStream(nomFichier), "UTF8");

			scEnreg.nextLine();

			while (scEnreg.hasNextLine())
			{
				enreg = scEnreg.nextLine();

				scDonnee = new Scanner(enreg);
				scDonnee.useDelimiter("\t");

				codeRessource = scDonnee.next();
				nomRessource  = scDonnee.next();

				System.out.println(nomRessource);
				ressource = new Ressource(codeRessource, nomRessource);

				for (int cpt = 0 ; scDonnee.hasNext() ; cpt++)
				{
					ressource.ajouterNotion(new Notion(scDonnee.next(), cpt, codeRessource));
				}
				
				this.lstRessources.add(ressource);
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/**
	 * Sauvegarde les ressources dans un fichier CSV.
	 * 
	 * @param nomFichier le nom du fichier dans lequel sauvegarder les ressources.
	 * @see PrintWriter 
	 */
	public void sauvegarderRessources(String nomFichier)
	{
		PrintWriter pw;

		try
		{
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), "UTF8" ));

			pw.println("code\tressource\tnotion 1\tnotion 2\tnotion N");

			for (Ressource ressource : this.lstRessources)
			{
				pw.print(ressource.getCode() + "\t");
				pw.print(ressource.getNom () + "\t");
				for(Notion notion : ressource.getNotions())
				{
					pw.print(notion.getNom() + "\t");
				}

				pw.print("\n");
			}

			pw.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute une ressource à la banque de ressources.
	 * 
	 * @param ressource
	 * @return true si la ressource a été ajoutée, false sinon.
	 */
	public boolean ajouterRessource(Ressource ressource)
	{
		if (ressource == null) return false;

		this.lstRessources.add(ressource);
		return true;
	}

	/**
	 * Modifie le nom d'une ressource.
	 * 
	 * @param ressource
	 * @param nouveauNom
	 * @return true si le nom de la ressource a été modifié, false sinon.
	 */
	public boolean modifierRessource(Ressource ressource, String nouveauNom)
	{
		if (this.lstRessources.contains(ressource) &&
			!nouveauNom.equals(null) &&
			!ressource.getNom().equals(nouveauNom))
		{
			ressource.setNom(nouveauNom);
			return true;
		}

		return false;
	}

	/**
	 * Supprime une ressource de la banque de ressources.
	 * 
	 * @param ressource
	 * @return true si la ressource a été supprimée, false sinon.
	 */
	public boolean supprimerRessource(Ressource ressource)
	{
		if (this.lstRessources.contains(ressource))
		{
			this.lstRessources.remove(ressource);
			return true;
		}

		return false;
	}

	/*----------*/
	/* ToString */
	/*----------*/
	public String toString()
	{
		String sRet = "";


		for (int cpt = 0 ; cpt < this.lstRessources.size() ; cpt++)
		{
			sRet += this.lstRessources.get(cpt).getNom() + "\n" + "Notions : ";
			for (Notion notion : this.lstRessources.get(cpt).getNotions())
			{
				sRet += notion + ", ";
			}
			sRet += "\n\n";
		}
			

		return sRet;
	}


	/*
	public static void main(String[] args)
	{
		BanqueDeRessources bqr, bqr2;
		Ressource r1, r2;


		bqr = new BanqueDeRessources();
		r1 = new Ressource("r1.01 Init dev");
		r2 = new Ressource("r1.02 Web");


		r1.ajouterNotion(new Notion("Encapsulation"));
		r1.ajouterNotion(new Notion("Les Tableaux"));
	

		bqr.ajouterRessource(r1);
		bqr.ajouterRessource(r2);

		bqr.sauvegarderRessources("testEcrire.csv");
		
		bqr2 = new BanqueDeRessources();
		bqr2.lireRessources("testLire.csv");
		System.out.println(bqr2);
	}
	*/
}
