package metier.banque;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metier.entite.Ressource;

/** Classe BanqueDeRessources
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueRessources
{



	/*-----------*/
	// Attributs //
	/*-----------*/

	private List<Ressource> lstRessources;

	private String          cheminFic;
	private String          currentDir;



	/*--------------*/
	// Constructeur //
	/*--------------*/

	/**
	 * Constructeur de la classe BanqueDeRessources.
	 */
	public BanqueRessources()
	{
		this.lstRessources = new ArrayList<Ressource>();
		this.currentDir    = System.getProperty("user.dir");

		this.cheminFic     = this.currentDir + "/data/ressources.csv";

		this.lireRessources(this.cheminFic);
	}



	/*---------*/
	// Getters //
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
	 * @return la ressource correspondante, ou null si elle n'existe pas.
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
	// Autres méthodes //
	/*-----------------*/

	/** 
	 * Lit les ressources d'un fichier CSV.
	 * 
	 * @param cheminFic le nom du fichier à lire.
	 * @see Scanner
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
	 * @param cheminFic le nom du fichier dans lequel sauvegarder les ressources.
	 * @see PrintWriter 
	 */
	private void sauvegarder(String cheminFic)
	{
		PrintWriter pw;

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
	 * @param ressource la ressource à ajouter.
	 */
	public void creerRessource(String code, String nom)
	{
		this.lstRessources.add(new Ressource(code, nom));
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
	 * @param ressource la ressource à supprimer.
	 */
	public void supprimerRessource(String codeRes)
	{
		for(int i = 0; i < this.lstRessources.size(); i++)
		{
			// this.lstRessources n'est pas censé contenir des valeurs null
			if(this.lstRessources.get(i).getCode().equals(codeRes))
				this.lstRessources.remove(i);
		}
	}

/*
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
	*/


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
