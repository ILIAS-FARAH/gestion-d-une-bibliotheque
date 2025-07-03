package Modeles;

import java.sql.Date;

public class Raport {
	private String titre;
    private Date dateCreation;
    private String contenu;
    
    public Raport(String titre, Date dateCreation, String contenu) {
		super();
		this.titre = titre;
		this.dateCreation = dateCreation;
		this.contenu = contenu;
	}
    
	@Override
	public String toString() {
		return "Raport [titre=" + titre + ", dateCreation=" + dateCreation + ", contenu=" + contenu + "]";
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Raport() {
		super();
		
	}
	
}
