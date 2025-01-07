package metier.banque;


import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.FileNotFoundException;

import metier.entite.Ressource;

/**
 * Classe BanqueDeRessources qui gère les ressources (hérite de Banque).
 * 
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueRessources extends Banque
{

	/*-----------*/
	/* Attributs */
	/*-----------*/

	private List<Ressource> lstRessources;

	private String          cheminFic;



	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Constructeur de la classe BanqueRessources.
	 */
	public BanqueRessources()
	{
		this.lstRessources = new ArrayList<Ressource>();

		this.cheminFic     = "data/ressources.csv";

		this.lireRessources(this.cheminFic);
	}



	/*---------*/
	/* Getters */
	/*---------*/


	/**
	 * Retourne le chemin du fichier de la banque de ressources.
	 * 
	 * @return le chemin du fichier.
	 */
	public String getCheminFic()
	{
		return this.cheminFic;
	}

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
	 * Retourne une ressource de la banque de ressources.
	 * 
	 * @param code le code de la ressource à retourner.
	 * @return     la ressource correspondante, ou null si elle n'existe pas.
	 */
	public Ressource getRessource(String code)
	{
		for (Ressource ressource : this.lstRessources)
		{
			if (ressource.getCode().equals(code))
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
	 * @param cheminFic le nom du fichier à lire.
	 * @see   Scanner   pour lire le fichier.
	*/
	public void lireRessources(String cheminFic)
	{
		Scanner   scEnreg, scDonnee;

		String    enreg;
		Ressource ressource;

		String code;
		String nom;

		try
		{
			scEnreg = new Scanner( new FileInputStream(cheminFic), "UTF8");

			if(scEnreg.hasNextLine())
				scEnreg.nextLine();

			while (scEnreg.hasNextLine())
			{
				enreg = scEnreg.nextLine();

				scDonnee = new Scanner(enreg);
				scDonnee.useDelimiter("\t");

				code = scDonnee.next();
				nom  = scDonnee.next();

				ressource = new Ressource(code, nom);
				
				this.lstRessources.add(ressource);
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + cheminFic + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/**
	 * Sauvegarde les ressources dans un fichier CSV.
	 */
	public void sauvegarder()
	{
		this.sauvegarder(this.cheminFic);
	}

	/**
	 * Sauvegarde les ressources dans un fichier CSV.
	 * 
	 * @param cheminFic   le nom du fichier dans lequel sauvegarder les ressources.
	 * @see   PrintWriter pour écrire dans le fichier.
	 */
	private void sauvegarder(String cheminFic)
	{
		PrintWriter pw;
		File fichier;

		try
		{
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(cheminFic), "UTF8" ));

			pw.println("code\tressource");

			for (Ressource ressource : this.lstRessources)
			{
				pw.print(ressource.getCode() + "\t");
				pw.print(ressource.getNom () + "\n");
			}

			pw.close();
		}
		catch (FileNotFoundException fnfe)
		{
			fichier = new File(cheminFic);
			fichier.mkdir();
			this.sauvegarder(cheminFic);
			System.out.println("Le fichier " + cheminFic + "n'a pas été trouvé : " + fnfe.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute une ressource à la banque de ressources.
	 * 
	 * @param code le code de la ressource.
	 * @param nom  le nom de la ressource.
	 */
	public void creerRessource(String code, String nom)
	{
		this.lstRessources.add(new Ressource(code, nom));
	}

	/**
	 * Modifie le nom d'une ressource.
	 * 
	 * @param ressource  la ressource à modifier.
	 * @param nouveauNom le nouveau nom de la ressource.
	 * @return           true si le nom de la ressource a été modifié, false sinon.
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
	 * @param codeRes le code de la ressource à supprimer.
	 */
	public void supprimerRessource(String codeRes)
	{
		for(int i = 0; i < this.lstRessources.size(); i++)
		{
			// this.lstRessources n'est pas censé contenir des valeurs null
			if(this.lstRessources.get(i).getCode().equals(codeRes))
			{
				this.lstRessources.remove(i);
				this.supprimerDossier(new File("data/ressources/" + codeRes));
			}
		}
	}


	/**
	 * Retourne une chaîne de caractères représentant la banque de ressources.
	 * 
	 * @return la chaîne de caractères.
	 */
	public String toString()
	{
		String sRet;


		sRet = "";
		for (int cpt = 0 ; cpt < this.lstRessources.size() ; cpt++)
		{
			sRet += this.lstRessources.get(cpt).getNom() + "\n";
			sRet += "\n\n";
		}

		return sRet;
	}
	

	/**
	 * Main de la classe BanqueRessources.
	 */
	public static void main(String[] args)
	{
		BanqueRessources bqr, bqr2;


		bqr = new BanqueRessources();

		bqr.sauvegarder("test.csv");
		
		bqr2 = new BanqueRessources();
		System.out.println(bqr2);
	}
}
