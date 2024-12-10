package metier;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;

/** Classe BanqueDeRessources
 * @author Equipe 03
 * @version 1.0 du 2024-12-09 Norme ISO-8601
 */
public class BanqueDeRessources
{
	/* Attributs */
	// private Controleur      ctrl;
	private List<Ressource> ressources;


	/* Constructeur */
	public BanqueDeRessources(/*Controleur ctrl*/)
	{
		// this.ctrl = ctrl;
		this.ressources = new ArrayList<Ressource>();
	}

	/* Lecture du fichier CSV qui contient les ressources */
	public void lireRessources(String nomFichier)
	{
		Scanner sc;

		String enreg;

		Ressource ressource;
		String[] donnees;


		try
		{
			sc = new Scanner( new FileInputStream(nomFichier), "UTF8");

			while (sc.hasNextLine())
			{
				enreg = sc.nextLine();

				donnees = enreg.split("\t");

				ressource = new Ressource(donnees[0]);
				for (int cpt = 1 ; cpt < donnees.length ; cpt++)
				{
					ressource.ajouterNotion(new Notion(donnees[cpt]));
				}
				
				this.ressources.add(ressource);
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/* Ecriture du fichier CSV qui contient les ressources */
	public void sauvegarderRessources(String nomFichier)
	{
		List<Notion> notions;
		Ressource res;
		PrintWriter pw;


		try
		{
			pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(nomFichier), "UTF8" ) );

			for (int cpt = 0 ; cpt < this.ressources.size() ; cpt++)
			{
				res = this.ressources.get(cpt);
				notions = res.getNotions();

				pw.print(res.getNom() + "\t");
				for (Notion notion : notions)
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


	public boolean ajouterRessource(Ressource ressource)
	{
		if (ressource == null) return false;

		this.ressources.add(ressource);
		return true;
	}

	public boolean modifierRessource(Ressource ressource, String nouveauNom)
	{
		if (this.ressources.contains(ressource) &&
			!nouveauNom.equals(null) &&
			!ressource.getNom().equals(nouveauNom))
		{
			ressource.setNom(nouveauNom);
			return true;
		}

		return false;
	}

	public boolean supprimerRessource(Ressource ressource)
	{
		if (this.ressources.contains(ressource))
		{
			this.ressources.remove(ressource);
			return true;
		}

		return false;
	}

	public String toString()
	{
		String sRet = "";


		for (int cpt = 0 ; cpt < this.ressources.size() ; cpt++)
		{
			sRet += this.ressources.get(cpt).getNom() + "\n" + "Notions : ";
			for (Notion notion : this.ressources.get(cpt).getNotions())
			{
				sRet += notion.getNom() + ", ";
			}
			sRet += "\n\n";
		}
			

		return sRet;
	}

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

		bqr.sauvegarderRessources("test.csv");
		
		bqr2 = new BanqueDeRessources();
		bqr2.lireRessources("test.csv");
		System.out.println(bqr2);
	}
}
