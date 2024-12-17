package metier.banque;

import metier.entite.Ressource;

import java.util.List;
import java.util.ArrayList;

import metier.QCMBuilder;
import metier.entite.Notion;

/**
 * Classe BanqueDeNotion
 * 
 * @author Equipe 03
 * 
 * @version 1.0 du 09/12/2024
 */	
public class BanqueDeNotion {

	private QCMBuilder qcmBuilder;
	private List<Notion> lstNotions;
	
	public BanqueDeNotion(QCMBuilder qcmBuilder)
	{
		this.qcmBuilder = qcmBuilder;
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
	 */
	public void ajouterNotion(Notion notion)
	{
		this.lstNotions.add(notion);
	}

	/**
	 * Supprime une notion de la banque de notions.
	 * 
	 * @param notion la notion à supprimer.
	 */
	public void supprimerNotion(int id)
	{
		for(Notion notion : this.lstNotions)
		{
			if(id == notion.getId())
			{
				this.lstNotions.remove(notion);
				return;
			}
		}
	}

	/**
	 * Modifie une notion de la banque de notions.
	 * 
	 * @param notion la notion à modifier.
	 */
	public void modifierNotion(Notion notion)
	{
		for(Notion n : this.lstNotions)
		{
			if(n.getId() == notion.getId())
			{
				n.setNom(notion.getNom());
				n.setCodeRessource(notion.getCodeRessource());
				return;
			}
		}
	}
}
