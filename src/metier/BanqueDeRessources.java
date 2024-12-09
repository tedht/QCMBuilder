package metier;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private Controleur      ctrl;
	private List<Ressource> ressources;


	/* Constructeur */
	public BanqueDeRessources(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.ressources = new ArrayList<Ressource>();
	}

	/* Lecture du fichier RTF qui contient les questions */
	public void lireQuestions(String nomFichier)
	{
		Scanner sc;

		String enreg;


		try
		{
			sc = new Scanner( new FileInputStream(nomFichier), "UTF-8");


			while (sc.hasNextLine())
			{
				enreg = sc.nextLine();

				// TODO : Lecture du fichier CSV
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Le fichier " + nomFichier + "n'a pas été trouvé : " + fnfe.getMessage());
		}
	}

	/* Ecriture du fichier RTF qui contient les questions */
	public void sauvegarderQuestions(String nomFichier)
	{
		PrintWriter pw;


		try
		{
			pw = new PrintWriter(nomFichier, "UTF-8");


			for (int cpt = 0 ; cpt < this.ressources.size() ; cpt++)
			{
				

				// TODO : Ecriture du fichier CSV
			}
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
}
