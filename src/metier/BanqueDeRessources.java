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
	private List<Ressource> lstRessources;


	/* Constructeur */
	public BanqueDeRessources(/*Controleur ctrl*/)
	{
		// this.ctrl = ctrl;
		this.lstRessources = new ArrayList<Ressource>();
	}

	/* Getters */
	public List<Ressource> getRessources()
	{
		return this.lstRessources;
	}

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

	/* Lecture du fichier CSV qui contient les lstRessources */
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
				
				this.lstRessources.add(ressource);
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/* Ecriture du fichier CSV qui contient les lstRessources */
	public void sauvegarderRessources(String nomFichier)
	{
		List<Notion> notions;
		Ressource res;
		PrintWriter pw;


		try
		{
			pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(nomFichier), "UTF8" ) );

			for (int cpt = 0 ; cpt < this.lstRessources.size() ; cpt++)
			{
				res = this.lstRessources.get(cpt);
				notions = res.getNotions();

				pw.print(res.getNom() + "\t");
				if (res.getCheminFichier() != null) {
					pw.print("Image associée : " + res.getCheminFichier());
				}
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

		this.lstRessources.add(ressource);
		return true;
	}

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

	public boolean supprimerRessource(Ressource ressource)
	{
		if (this.lstRessources.contains(ressource))
		{
			this.lstRessources.remove(ressource);
			return true;
		}

		return false;
	}

	public String toString()
	{
		String sRet = "";


		for (int cpt = 0 ; cpt < this.lstRessources.size() ; cpt++)
		{
			sRet += this.lstRessources.get(cpt).getNom() + "\n" + "Notions : ";
			for (Notion notion : this.lstRessources.get(cpt).getNotions())
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

	public Ressource getRessource(String nomRessource) 
	{
		for(Ressource ressource : this.lstRessources)
		{
			if(ressource.getNom().equals(nomRessource)) return ressource;
		}
		return null;
	}
}
